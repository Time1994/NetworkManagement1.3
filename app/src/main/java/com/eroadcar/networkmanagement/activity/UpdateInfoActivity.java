package com.eroadcar.networkmanagement.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.view.ClearEditText;

import java.util.HashMap;

public class UpdateInfoActivity extends BaseActivity implements OnClickListener {
    private Button other_btn, back_btn;
    private ClearEditText name_cet;

    private TextView title_tv;

    private CommonBean commobBean;

    private String from = "", name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        other_btn = (Button) findViewById(R.id.other_btn);
        name_cet = (ClearEditText) findViewById(R.id.name_cet);
        back_btn = (Button) findViewById(R.id.back_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);

        from = getIntent().getStringExtra("FROM");

        if (from.equals("name")) {
            name = getString(R.string.name);
        } else if (from.equals("mobile")) {
            name = getString(R.string.moble);
        }

        title_tv.setText(name);
        name_cet.setHint("请输入您的" + name);

        other_btn.setText(getString(R.string.done));

        back_btn.setOnClickListener(this);
        other_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {

            case R.id.other_btn:
                if (name_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请输入您的" + name);
                    return;
                }

                if (from.equals("name")) {
                    modifyUser(name_cet.getText().toString(), "", "");
                } else if (from.equals("mobile")) {
                    modifyUser("", name_cet.getText().toString(), "");
                }
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
                        if (from.equals("name")) {
                            application.userBean.setMg_name(name_cet.getText().toString());
                        } else if (from.equals("mobile")) {
                            application.userBean.setMg_cellphone(name_cet.getText().toString());
                        }

                        onBackPressed();
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


    private void modifyUser(String mg_add_name, String mg_add_cellphone, String mg_add_pwd) {
        loadingDialog.setMessage("正在新建员工账号...");
        loadingDialog.dialogShow();
        String url = Constant.MODIFYUSER;
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

        map.put("mg_mdf_name", mg_add_name);
        map.put("mg_mdf_cellphone", mg_add_cellphone);
        map.put("mg_mdf_pwd", mg_add_pwd);

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
