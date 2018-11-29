package com.eroadcar.networkmanagement.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.DialogItemAdapter;
import com.eroadcar.networkmanagement.adapter.ZhengxAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.EmployeeBean;
import com.eroadcar.networkmanagement.bean.OrderSalesBean;
import com.eroadcar.networkmanagement.bean.RoleBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.view.ClearEditText;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class EmployeeDetailsActivity extends BaseActivity implements OnClickListener {
    private Button other_btn, back_btn;
    private TextView title_tv;

    private TextView name_tv, sex_tv, phone_tv, tit_tv, store_tv, time_tv, state_tv;
    private Button submit_btn;

    private TextView saley_tv, renty_tv, sales_tv, rents_tv;
    private ClearEditText sale_cet, rent_cet;

    private CommonBean<EmployeeBean> commonBean;

    private EmployeeBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        title_tv = (TextView) findViewById(R.id.title_tv);
        other_btn = (Button) findViewById(R.id.other_btn);
        back_btn = (Button) findViewById(R.id.back_btn);

        name_tv = (TextView) findViewById(R.id.name_tv);
        sex_tv = (TextView) findViewById(R.id.sex_tv);
        phone_tv = (TextView) findViewById(R.id.phone_tv);
        tit_tv = (TextView) findViewById(R.id.tit_tv);
        store_tv = (TextView) findViewById(R.id.store_tv);
        time_tv = (TextView) findViewById(R.id.time_tv);

        state_tv = (TextView) findViewById(R.id.state_tv);

        submit_btn = (Button) findViewById(R.id.submit_btn);

        saley_tv = (TextView) findViewById(R.id.saley_tv);
        renty_tv = (TextView) findViewById(R.id.renty_tv);
        sales_tv = (TextView) findViewById(R.id.sales_tv);
        rents_tv = (TextView) findViewById(R.id.rents_tv);

        sale_cet = (ClearEditText) findViewById(R.id.sale_cet);
        rent_cet = (ClearEditText) findViewById(R.id.rent_cet);

        back_btn.setOnClickListener(this);
        other_btn.setOnClickListener(this);
        submit_btn.setOnClickListener(this);
        state_tv.setOnClickListener(this);

        title_tv.setText("人员详情");
        back_btn.setText("");
        other_btn.setText("");

        bean = (EmployeeBean) getIntent().getSerializableExtra("BEAN");

        if (bean != null) {
            other_btn.setText("数据统计");

            setInterface();
        } else {
//            other_btn.setText("任务列表");

            getWorkersDetail();
        }
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.other_btn:
                if (other_btn.getText().toString().equals("任务列表")) {
                    intent(TaskActivity.class);
                } else {
                    try {
                        Intent intent = new Intent(EmployeeDetailsActivity.this, StatisticsActivity.class);
                        intent.putExtra("CODE", "employee");
                        intent.putExtra("BEAN", bean);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.submit_btn:
                if (sale_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请输入销售目标");
                    return;
                }
                if (rent_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请输入租赁目标");
                    return;
                }
                Calendar cd = Calendar.getInstance();
                String s = cd.get(Calendar.YEAR) + "-" + (cd.get(Calendar.MONTH) + 1);

                submitWorkerInfo(sale_cet.getText().toString(), rent_cet.getText().toString(), s);

                break;

            case R.id.state_tv:
//                intent(OrderSalesActivity.class);

                Intent intent = new Intent(EmployeeDetailsActivity.this, OrderSalesActivity.class);
                intent.putExtra("BEAN", bean);
                startActivity(intent);

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
                        bean = commonBean.getData();

                        setInterface();
                    }
                    ToastUtils.showShort(commonBean.getMsg());
                    break;
                case 2:
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


    private void getWorkersDetail() {
        loadingDialog.setMessage("正在获取数据...");
        loadingDialog.dialogShow();
        String url = Constant.GETWORKERSDETAIL;

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

        GsonRequest<CommonBean<EmployeeBean>> requtst = new GsonRequest<CommonBean<EmployeeBean>>(
                Request.Method.POST, url, listener_reqZxInfo);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<EmployeeBean>> listener_reqZxInfo = new RequesListener<CommonBean<EmployeeBean>>() {
        @Override
        public void onResponse(CommonBean<EmployeeBean> arg0) {
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

    private void submitWorkerInfo(String st_expect_xsnum, String st_expect_zlnum, String st_target_month) {
        loadingDialog.setMessage("正在提交数据...");
        loadingDialog.dialogShow();
        String url = Constant.SUBMITWORKERINFO;

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

        map.put("st_expect_xsnum", st_expect_xsnum);
        map.put("st_expect_zlnum", st_expect_zlnum);
        map.put("st_target_month", st_target_month);
        try {
            map.put("mg_sex", bean.getMg_sex());
            map.put("mg_cellphone", bean.getMg_cellphone());
            map.put("mg_entry_date", bean.getMg_entry_date());

            map.put("st_worker_id", bean.getMg_id());
            map.put("st_shopsid", bean.getMg_shopsid());
            map.put("st_worker_code", bean.getMg_code());
            map.put("st_worker_name", bean.getMg_name());
        } catch (Exception e) {
            e.printStackTrace();
        }

        GsonRequest<CommonBean> requtst = new GsonRequest<CommonBean>(
                Request.Method.POST, url, listener_submitWorkerInfo);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean> listener_submitWorkerInfo = new RequesListener<CommonBean>() {
        @Override
        public void onResponse(CommonBean arg0) {
            // TODO Auto-generated method stub
            commonBean = arg0;
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


    private void setInterface() {
        name_tv.setText("姓名：" + bean.getMg_name());
        sex_tv.setText("性别：" + bean.getMg_sex());
        phone_tv.setText("联系方式：" + bean.getMg_cellphone());
        tit_tv.setText("职位：" + bean.getMg_role_ids());
        store_tv.setText("门店：" + bean.getMg_shopname());
        time_tv.setText("入职时间：" + bean.getMg_entry_date());

        if (bean.getMg_role_ids().contains("3")) {
            tit_tv.setText("职位：销售顾问");
        }
        if (bean.getMg_role_ids().contains("4")) {
            tit_tv.setText("职位：财务经理");
        }
        if (bean.getMg_role_ids().contains("2")) {
            tit_tv.setText("职位：销售经理");
        }
        if (bean.getMg_role_ids().contains("1")) {
            tit_tv.setText("职位：店长");
        }

        if (bean.getIs_jobing() != null && bean.getIs_jobing().equals("1")) {
            state_tv.setText("订单进行中");
        } else {
            state_tv.setText("暂无订单进行中");
        }
        if (bean.getSt_finish_xsnum() == null) {
            saley_tv.setText("销售：0");
            renty_tv.setText("租赁：0");
        } else {
            saley_tv.setText("销售：" + bean.getSt_finish_xsnum());
            renty_tv.setText("租赁：" + bean.getSt_finish_zlnum());
        }

        if (bean.getSt_expect_xsnum() != null && !bean.getSt_expect_xsnum().equals("0")) {
            sale_cet.setVisibility(View.GONE);
            rent_cet.setVisibility(View.GONE);
            submit_btn.setVisibility(View.GONE);

            sales_tv.setText("销售：" + bean.getSt_expect_xsnum());
            rents_tv.setText("租赁：" + bean.getSt_expect_zlnum());
        }

        if (application.userBean.getMg_role_ids().contains("3")) {
            sale_cet.setVisibility(View.GONE);
            rent_cet.setVisibility(View.GONE);
            submit_btn.setVisibility(View.GONE);

            if (bean.getSt_expect_xsnum() == null || bean.getSt_expect_xsnum().equals("0")) {
                sales_tv.setText("销售：0");
                rents_tv.setText("租赁：0");
            }
        }
    }
}
