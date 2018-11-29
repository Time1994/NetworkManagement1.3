package com.eroadcar.networkmanagement.activity;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.RoleAdapter;
import com.eroadcar.networkmanagement.adapter.RoleManagerAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.RoleBean;
import com.eroadcar.networkmanagement.bean.UserBean;
import com.eroadcar.networkmanagement.utils.Constant;

import java.util.ArrayList;
import java.util.HashMap;


public class RolesManagerActivity extends BaseActivity implements View.OnClickListener {

    private Button back_btn;
    private TextView title_tv;

    private ListView user_lv;

    private CommonBean<ArrayList<UserBean>> commobBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roles_manager);

        back_btn = (Button) findViewById(R.id.back_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);

        title_tv.setText(R.string.role);

        back_btn.setOnClickListener(this);

        user_lv = (ListView) findViewById(R.id.user_lv);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getUsers();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
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
                        user_lv.setAdapter(new RoleManagerAdapter(RolesManagerActivity.this, commobBean.getData()));
                    }

                    break;
                case -1:
                    break;
                default:
                    break;
            }
        }
    };

    private void getUsers() {
        loadingDialog.setMessage("正在获取数据...");
        loadingDialog.dialogShow();
        String url = Constant.GETUSERS;
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

        GsonRequest<CommonBean<ArrayList<UserBean>>> requtst = new GsonRequest<CommonBean<ArrayList<UserBean>>>(
                Request.Method.POST, url, listener_getInshopCars);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<UserBean>>> listener_getInshopCars = new RequesListener<CommonBean<ArrayList<UserBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<UserBean>> arg0) {
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
