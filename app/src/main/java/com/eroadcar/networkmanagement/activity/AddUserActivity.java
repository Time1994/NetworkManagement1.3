package com.eroadcar.networkmanagement.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.UserBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.view.ClearEditText;

import java.util.HashMap;


public class AddUserActivity extends BaseActivity implements View.OnClickListener {

    private Button login_btn, back_btn;
    private TextView title_tv;
    private ClearEditText pwd_cet, store_cet, employee_cet;

    private CommonBean commobBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        login_btn = (Button) findViewById(R.id.login_btn);
        back_btn = (Button) findViewById(R.id.back_btn);

        title_tv = (TextView) findViewById(R.id.title_tv);

        pwd_cet = (ClearEditText) findViewById(R.id.pwd_cet);
        store_cet = (ClearEditText) findViewById(R.id.store_cet);
        employee_cet = (ClearEditText) findViewById(R.id.employee_cet);

        login_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);

        title_tv.setText(R.string.add_user);

        employee_cet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    if (store_cet.getText().toString().equals("")) {
                        ToastUtils.showShort("请输入员工姓名");
                        store_cet.requestFocus();
                    }
                }
            }
        });

        pwd_cet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    if (employee_cet.getText().toString().equals("")) {
                        ToastUtils.showShort("请输入员工手机号");
                        employee_cet.requestFocus();

                    } else if (employee_cet.getText().toString().length() != 11) {
                        ToastUtils.showShort("请输入正确的手机号");
                        employee_cet.requestFocus();
                    }
                }
            }
        });

        pwd_cet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() > 0) {
                    login_btn.setEnabled(true);
                } else {
                    login_btn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.login_btn:
                addUser(store_cet.getText().toString(), employee_cet.getText().toString(), pwd_cet.getText().toString());
                break;
            case R.id.back_btn:
                onBackPressed();
                break;
            default:
                break;
        }
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (commobBean.getState().equals("success")) {
                        intent(RolesManagerActivity.class);
                        finish();
                    }
                    ToastUtils.showShort(commobBean.getMsg());

                    break;
                case -1:
                    break;
                default:
                    break;
            }
        }
    };


    private void addUser(String mg_add_name, String mg_add_cellphone, String mg_add_pwd) {
        loadingDialog.setMessage("正在新建员工账号...");
        loadingDialog.dialogShow();
        String url = Constant.ADDUSER;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appcode", IMEI);
        map.put("apptype", Constant.APPTYPE);
        map.put("mg_id", application.userBean.getMg_id());
        map.put("mg_name", application.userBean.getMg_name());
        map.put("mg_shopsid", application.userBean.getMg_shopsid());
        map.put("mg_groupid", application.userBean.getMg_groupid());
        map.put("mg_shopname", application.userBean.getMg_shopname());
        map.put("mg_shopcode", application.userBean.getMg_shopcode());
        map.put("mg_code", application.userBean.getMg_code());
        map.put("mg_groupname", application.userBean.getMg_groupname());
        map.put("mg_posid", application.userBean.getMg_posid());
        map.put("mg_posname", application.userBean.getMg_posname());

        map.put("mg_add_name", mg_add_name);
        map.put("mg_add_cellphone", mg_add_cellphone);
        map.put("mg_add_pwd", mg_add_pwd);

        GsonRequest<CommonBean> requtst = new GsonRequest<CommonBean>(
                Request.Method.POST, url, listener_getInshopCars);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean> listener_getInshopCars = new RequesListener<CommonBean>() {
        @Override
        public void onResponse(CommonBean arg0) {
            // TODO Auto-generated method stub
            commobBean = arg0;
            mHandler.sendEmptyMessage(1);
            loadingDialog.dismiss();
        }

        @Override
        public void onErrorResponse(VolleyError arg0) {
            // TODO Auto-generated method stub
            mHandler.sendEmptyMessage(-1);
            loadingDialog.dismiss();
        }

    };

}
