package com.eroadcar.networkmanagement.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.OrderSalesBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.view.ClearEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskPublicActivity extends BaseActivity implements OnClickListener {
    private Button other_btn, back_btn, sure_btn;
    private TextView title_tv;

    private RelativeLayout select_rl;

    private ClearEditText details_cet, zhuti_cet;

    private TextView tvc;

    private TextView jieshou_tv;

    private CommonBean<ArrayList<OrderSalesBean>> commonBean;

    private String wj_employee_ids = "", wj_employee_names = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskpublic);

        title_tv = (TextView) findViewById(R.id.title_tv);
        other_btn = (Button) findViewById(R.id.other_btn);
        back_btn = (Button) findViewById(R.id.back_btn);
        sure_btn = (Button) findViewById(R.id.sure_btn);

        select_rl = (RelativeLayout) findViewById(R.id.select_rl);

        details_cet = (ClearEditText) findViewById(R.id.details_cet);
        zhuti_cet = (ClearEditText) findViewById(R.id.zhuti_cet);

        jieshou_tv = (TextView) findViewById(R.id.jieshou_tv);

        tvc = (TextView) findViewById(R.id.tvc);

        back_btn.setOnClickListener(this);
        other_btn.setOnClickListener(this);
        sure_btn.setOnClickListener(this);
        select_rl.setOnClickListener(this);

        title_tv.setText("发布任务");
        back_btn.setText("");
        other_btn.setText("");
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.select_rl:
                Intent intent = new Intent(TaskPublicActivity.this, ChooseEmployeeActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.sure_btn:
                if (zhuti_cet.getText().toString().equals("")) {
                    ToastUtils.showLong("请输入任务主题");
                    return;
                }
                if (details_cet.getText().toString().equals("")) {
                    ToastUtils.showLong("请输入任务详情");
                    return;
                }
                if (wj_employee_ids.equals("")) {
                    ToastUtils.showLong("请选择任务接收人");
                    return;
                }

                distributeJob(zhuti_cet.getText().toString(), details_cet.getText().toString(), wj_employee_ids, wj_employee_names);
                break;
            default:
                break;
        }
    }

    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (commonBean.getState().equals("success")) {
                        onBackPressed();
                    }
                    ToastUtils.showShort(commonBean.getMsg());
                    break;

                default:
                    break;
            }
        }
    };

    private void distributeJob(String wj_job_name, String wj_job_context,
                               String wj_employee_ids, String wj_employee_names) {
        loadingDialog.setMessage("正在提交数据...");
        loadingDialog.dialogShow();
        String url = Constant.DISTRIBUTEJOB;

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appcode", IMEI);
        map.put("apptype", Constant.APPTYPE);
        map.put("mg_id", application.userBean.getMg_id());
        map.put("mg_name", application.userBean.getMg_name());
        map.put("mg_shopsid", application.userBean.getMg_shopsid());
        map.put("mg_groupid", application.userBean.getMg_groupid());
        map.put("mg_shopname", application.userBean.getMg_shopname());
        map.put("mg_code", application.userBean.getMg_code());
        map.put("mg_groupname", application.userBean.getMg_groupname());
        map.put("mg_posid", application.userBean.getMg_posid());
        map.put("mg_posname", application.userBean.getMg_posname());
        map.put("mg_shopcode", application.userBean.getMg_shopcode());

        map.put("wj_job_name", wj_job_name);
        map.put("wj_job_context", wj_job_context);
        map.put("wj_employee_ids", wj_employee_ids);
        map.put("wj_employee_names", wj_employee_names);

        GsonRequest<CommonBean<ArrayList<OrderSalesBean>>> requtst = new GsonRequest<CommonBean<ArrayList<OrderSalesBean>>>(
                Request.Method.POST, url, listener_reqZxInfo);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<OrderSalesBean>>> listener_reqZxInfo = new RequesListener<CommonBean<ArrayList<OrderSalesBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<OrderSalesBean>> arg0) {
            // TODO Auto-generated method stub
            commonBean = arg0;
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                wj_employee_ids = data.getExtras().getString("wj_employee_ids");
                wj_employee_names = data.getExtras().getString("wj_employee_names");

                tvc.setText("接收人\n" + wj_employee_names);
            }
        }
    }
}
