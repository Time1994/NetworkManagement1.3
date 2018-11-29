package com.eroadcar.networkmanagement.activity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.RoleAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.RoleBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;


public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private Button back_btn, exit_btn;
    private TextView title_tv;

    private CommonBean commobBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        back_btn = (Button) findViewById(R.id.back_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);

        exit_btn = (Button) findViewById(R.id.exit_btn);


        title_tv.setText("设置");

        if (application.userBean.getMg_role_ids() != null &&
                !application.userBean.getMg_role_ids().equals("")) {
            if (application.userBean.getMg_role_ids().equals("1") || application.userBean.getMg_role_ids().contains("1,")) {
                ((LinearLayout) findViewById(R.id.quan_ll)).setVisibility(View.VISIBLE);

                if (application.userBean.getMg_code().equals("9001001")){
                    ((LinearLayout) findViewById(R.id.quan_ll)).setVisibility(View.GONE);
                }
            }
        }

        back_btn.setOnClickListener(this);
        exit_btn.setOnClickListener(this);
        ((RelativeLayout)
                findViewById(R.id.personal_rl)).
                setOnClickListener(this);
        ((RelativeLayout)
                findViewById(R.id.add_rl)).
                setOnClickListener(this);
        ((RelativeLayout)
                findViewById(R.id.role_rl)).
                setOnClickListener(this);
        ((RelativeLayout)
                findViewById(R.id.tel_rl)).
                setOnClickListener(this);
        ((RelativeLayout)
                findViewById(R.id.update_rl)).
                setOnClickListener(this);
        ((RelativeLayout)
                findViewById(R.id.tool_rl)).
                setOnClickListener(this);
        ((RelativeLayout)
                findViewById(R.id.about_rl)).
                setOnClickListener(this);
        ((RelativeLayout)
                findViewById(R.id.feedback_rl)).
                setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.exit_btn:
                showDialogMessage(SettingActivity.this, "确认退出账号吗？",
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                dialogMessage.dismiss();

                                logOut();
                            }
                        });
                break;
            case R.id.personal_rl:
                intent(PersonalActivity.class);
                break;
            case R.id.add_rl:
                intent(AddUserActivity.class);
                break;
            case R.id.role_rl:
                intent(RolesManagerActivity.class);
                break;
            case R.id.tel_rl:
                intentCall("4009200665");
                break;
            case R.id.update_rl:
                intent(UpdateActivity.class);
                break;
            case R.id.tool_rl:
                intent(MoreServiceActivity.class);
                break;
            case R.id.about_rl:
                intent(AboutActivity.class);
                break;
            case R.id.feedback_rl:
                intent(FeedbackActivity.class);
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
                        SharedPreferences mySharedPreferences = getSharedPreferences("eroadcar", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mySharedPreferences.edit();
                        editor.putString("pwd", "");
                        editor.commit();

                        intent(LoginActivity.class);
                        finish();
                    }
                    ToastUtils.showShort(commobBean.getMsg());
                    break;
                case 2:
                    if (commobBean.getState().equals("success")) {
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

    private void logOut() {
        loadingDialog.setMessage("正在退出登录...");
        loadingDialog.dialogShow();
        String url = Constant.LOGOUT;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appcode", IMEI);
        map.put("apptype", Constant.APPTYPE);
        map.put("mg_id", application.userBean
                .getMg_id());
        map.put("mg_name", application.userBean
                .getMg_name());
        map.put("mg_shopsid", application.userBean
                .getMg_shopsid());
        map.put("mg_groupid", application.userBean
                .getMg_groupid());
        map.put("mg_shopname", application.userBean
                .getMg_shopname());
        map.put("mg_shopcode", application.userBean
                .getMg_shopcode());
        map.put("mg_code", application.userBean
                .getMg_code());
        map.put("mg_groupname", application.userBean
                .getMg_groupname());
        map.put("mg_posid", application.userBean
                .getMg_posid());
        map.put("mg_posname", application.userBean
                .getMg_posname());

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


    private void distributeRole(String mg_distri_id, String mg_distri_shopsid, String mg_role_ids) {
        loadingDialog.setMessage("正在保存权限数据...");
        loadingDialog.dialogShow();
        String url = Constant.DISTRIBUTEROLE;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appcode", IMEI);
        map.put("apptype", Constant.APPTYPE);
        map.put("mg_id", application.userBean
                .getMg_id());
        map.put("mg_name", application.userBean
                .getMg_name());
        map.put("mg_shopsid", application.userBean
                .getMg_shopsid());
        map.put("mg_groupid", application.userBean
                .getMg_groupid());
        map.put("mg_shopname", application.userBean
                .getMg_shopname());
        map.put("mg_shopcode", application.userBean
                .getMg_shopcode());
        map.put("mg_code", application.userBean
                .getMg_code());
        map.put("mg_groupname", application.userBean
                .getMg_groupname());
        map.put("mg_posid", application.userBean
                .getMg_posid());
        map.put("mg_posname", application.userBean
                .getMg_posname());
        map.put("mg_distri_id", mg_distri_id);
        map.put("mg_distri_shopsid", mg_distri_shopsid);
        map.put("mg_role_ids", mg_role_ids);

        GsonRequest<CommonBean<ArrayList<RoleBean>>> requtst = new GsonRequest<CommonBean<ArrayList<RoleBean>>>(
                Request.Method.POST, url, listener_distributeRole);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<RoleBean>>> listener_distributeRole = new RequesListener<CommonBean<ArrayList<RoleBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<RoleBean>> arg0) {
            // TODO Auto-generated method stub
            commobBean = arg0;
            mHandler.sendEmptyMessage(2);
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
