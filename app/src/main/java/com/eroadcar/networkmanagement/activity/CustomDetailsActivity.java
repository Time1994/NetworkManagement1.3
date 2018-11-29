package com.eroadcar.networkmanagement.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.QiankeHuiAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.QianKeBean;
import com.eroadcar.networkmanagement.bean.QianKeHuiBean;
import com.eroadcar.networkmanagement.bean.RoleBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;


public class CustomDetailsActivity extends BaseActivity implements View.OnClickListener {

    private Button back_btn, sure_btn,other_btn;
    private TextView title_tv;

    private ImageView custom_iv, tel_iv;
    private TextView name_tv, mobile_tv, dengji_tv, leix_tv, yix_tv, time_tv, rem_tv;

    private RadioGroup title_rg;

    private RelativeLayout hui_rl;
    private LinearLayout info_ll, addh_ll;

    private ListView hui_lv;

    private CommonBean<ArrayList<QianKeHuiBean>> commobBean;

    private QianKeBean qianKeBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_details);

        back_btn = (Button) findViewById(R.id.back_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);
        other_btn =(Button)findViewById(R.id.other_btn);

        sure_btn = (Button) findViewById(R.id.sure_btn);

        custom_iv = (ImageView) findViewById(R.id.custom_iv);
        tel_iv = (ImageView) findViewById(R.id.tel_iv);

        name_tv = (TextView) findViewById(R.id.name_tv);
        mobile_tv = (TextView) findViewById(R.id.mobile_tv);

        dengji_tv = (TextView) findViewById(R.id.dengji_tv);
        leix_tv = (TextView) findViewById(R.id.leix_tv);
        yix_tv = (TextView) findViewById(R.id.yix_tv);
        time_tv = (TextView) findViewById(R.id.time_tv);
        rem_tv = (TextView) findViewById(R.id.rem_tv);

        title_rg = (RadioGroup) findViewById(R.id.title_rg);

        hui_rl = (RelativeLayout) findViewById(R.id.hui_rl);
        info_ll = (LinearLayout) findViewById(R.id.info_ll);
        addh_ll = (LinearLayout) findViewById(R.id.addh_ll);

        hui_lv = (ListView) findViewById(R.id.hui_lv);

        title_tv.setText("客户信息");

        back_btn.setOnClickListener(this);
        addh_ll.setOnClickListener(this);
        tel_iv.setOnClickListener(this);
        sure_btn.setOnClickListener(this);
        other_btn.setOnClickListener(this);

        qianKeBean = (QianKeBean) getIntent().getSerializableExtra("BEAN");

        setInterface();

        title_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.info_rb) {
                    info_ll.setVisibility(View.VISIBLE);
                    hui_rl.setVisibility(View.GONE);
                } else {
                    info_ll.setVisibility(View.GONE);
                    hui_rl.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        getFollowRecord(qianKeBean.getPtc_id());
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.addh_ll:
                Intent intent = new Intent(CustomDetailsActivity.this, AddHuiActivity.class);
                intent.putExtra("BEAN", qianKeBean);
                startActivity(intent);
                break;
            case R.id.tel_iv:
                intentCall(qianKeBean.getPtc_mobile());
                break;
            case R.id.sure_btn:
                Intent intent1 = new Intent(CustomDetailsActivity.this, AddCustomActivity.class);
                intent1.putExtra("BEAN", qianKeBean);
                startActivity(intent1);

                finish();
                break;

            case  R.id.other_btn:
                Intent intentz = new Intent(CustomDetailsActivity.this, ZhengxActivity.class);
                intentz.putExtra("QBEAN", qianKeBean);
                startActivity(intentz);
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
                        hui_lv.setAdapter(new QiankeHuiAdapter(CustomDetailsActivity.this, commobBean.getData()));
                    }
                    break;
                case -1:
                    break;
                default:
                    break;
            }
        }
    };

    private void getFollowRecord(String frd_id) {
        loadingDialog.setMessage("正在获取数据...");
        loadingDialog.dialogShow();
        String url = Constant.GETFOLLOWRECORDO;
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


        map.put("frd_id", frd_id);


        GsonRequest<CommonBean<ArrayList<QianKeHuiBean>>> requtst = new GsonRequest<CommonBean<ArrayList<QianKeHuiBean>>>(
                Request.Method.POST, url, listener_getInshopCars);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<QianKeHuiBean>>> listener_getInshopCars = new RequesListener<CommonBean<ArrayList<QianKeHuiBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<QianKeHuiBean>> arg0) {
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

    private void setInterface() {
        if (qianKeBean.getPtc_sex() != null && !qianKeBean.getPtc_sex().equals("女")) {
            custom_iv.setImageResource(R.mipmap.icon_custom_man);
            name_tv.setText(qianKeBean.getPtc_name() + "  先生");
        } else {
            custom_iv.setImageResource(R.mipmap.icon_custom_woman);
            name_tv.setText(qianKeBean.getPtc_name() + "  女士");
        }

        mobile_tv.setText(qianKeBean.getPtc_mobile());
        dengji_tv.setText("客户等级：" + qianKeBean.getPtc_level());
        leix_tv.setText("客户类型：" + qianKeBean.getPtc_client_type());
        yix_tv.setText("意向车型：" + qianKeBean.getPtc_yx_cartype());
        time_tv.setText("登记时间：" + qianKeBean.getPtc_upload_date());
        rem_tv.setText("备注：" + qianKeBean.getPtc_remark());

        if (qianKeBean.getPtc_client_type().equals("签约")){
            other_btn.setText("新增销售");
        }
    }

}
