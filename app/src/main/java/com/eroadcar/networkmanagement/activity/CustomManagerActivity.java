package com.eroadcar.networkmanagement.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.QiankeAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.QianKeBean;
import com.eroadcar.networkmanagement.utils.Constant;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomManagerActivity extends BaseActivity implements View.OnClickListener {
    private Button back_btn;
    private ImageView add_iv;

    private ListView main_lv, city_lv;
    private RelativeLayout city_rl, city_pop_rl, you_rl;
    private ImageView city_iv, you_iv;

    private LinearLayout none_ll;

    private String ptc_level = "", ptc_client_type = "", type = "";
    private ArrayList<String> customClasss, customTypes;

    private CommonBean<ArrayList<QianKeBean>> qianKeBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        back_btn = (Button) findViewById(R.id.back_btn);
        add_iv = (ImageView) findViewById(R.id.add_iv);


        main_lv = (ListView) findViewById(R.id.main_lv);
        city_lv = (ListView) findViewById(R.id.city_lv);

        city_pop_rl = (RelativeLayout) findViewById(R.id.city_pop_rl);
        city_rl = (RelativeLayout) findViewById(R.id.city_rl);
        you_rl = (RelativeLayout) findViewById(R.id.you_rl);
        you_iv = (ImageView) findViewById(R.id.you_iv);
        city_iv = (ImageView) findViewById(R.id.city_iv);

        none_ll = (LinearLayout) findViewById(R.id.none);


        city_rl.setOnClickListener(this);
        you_rl.setOnClickListener(this);
        city_pop_rl.setOnClickListener(this);

        back_btn.setOnClickListener(this);
        add_iv.setVisibility(View.GONE);

        customClasss = new ArrayList<String>();
        customClasss.add(0, "全部");
        customClasss.add(1, "A");
        customClasss.add(2, "B");
        customClasss.add(3, "C");
        customClasss.add(4, "D");

        customTypes = new ArrayList<String>();
        customTypes.add(0, "全部");
        customTypes.add(1, "意向");
        customTypes.add(2, "认购");
        customTypes.add(3, "签约");


        main_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CustomManagerActivity.this, CustomDetailsActivity.class);
                intent.putExtra("BEAN", qianKeBeans.getData().get(position));
                startActivity(intent);
            }
        });

        city_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (type.equals("c")) {
                    ptc_client_type = customTypes.get(i);
                } else {
                    ptc_level = customClasss.get(i);
                }

                getPotentialClient(ptc_client_type, ptc_level);

                city_pop_rl.setVisibility(View.GONE);
                city_iv.setImageResource(R.mipmap.icon_sanjiao_down);
                you_iv.setImageResource(R.mipmap.icon_sanjiao_down);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getPotentialClient(ptc_client_type, ptc_level);
    }

    private void getPotentialClient(String ptc_client_type, String ptc_level) {
        loadingDialog.setMessage("正在获取数据...");
        loadingDialog.dialogShow();
        String url = Constant.GETPOTENTIALCLIENTFORMANAGER;
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

        map.put("ptc_client_type", ptc_client_type.replace("全部", ""));
        map.put("ptc_level", ptc_level.replace("全部", ""));

        GsonRequest<CommonBean<ArrayList<QianKeBean>>> requtst = new GsonRequest<CommonBean<ArrayList<QianKeBean>>>(
                Request.Method.POST, url, listener_getPotentialClient);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<QianKeBean>>> listener_getPotentialClient = new RequesListener<CommonBean<ArrayList<QianKeBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<QianKeBean>> arg0) {
            // TODO Auto-generated method stub
            qianKeBeans = arg0;
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


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (qianKeBeans.getState().equals("success")) {
                        if (qianKeBeans.getData() == null
                                || qianKeBeans.getData().size() == 0) {
                            none_ll.setVisibility(View.VISIBLE);
                            main_lv.setVisibility(View.GONE);
                        } else {
                            main_lv.setAdapter(new QiankeAdapter(
                                    CustomManagerActivity.this, qianKeBeans.getData(), "manager"));
                            none_ll.setVisibility(View.GONE);
                            main_lv.setVisibility(View.VISIBLE);
                        }
                    } else {
                        none_ll.setVisibility(View.VISIBLE);
                        main_lv.setVisibility(View.GONE);
                    }
                    break;
                case -1:
                    break;
                default:
                    break;
            }
        }
    };


    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.you_rl:
                type = "t";

                setPop();
                break;
            case R.id.city_rl:
                type = "c";

                setPop();
                break;
            case R.id.city_pop_rl:
                city_pop_rl.setVisibility(View.GONE);
                city_iv.setImageResource(R.mipmap.icon_sanjiao_down);
                you_iv.setImageResource(R.mipmap.icon_sanjiao_down);
                break;
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.add_iv:
                intent(AddCustomActivity.class);
                break;

        }
    }

    private void setPop() {
        if (city_pop_rl.isShown()) {
            city_pop_rl.setVisibility(View.GONE);
            city_iv.setImageResource(R.mipmap.icon_sanjiao_down);
            you_iv.setImageResource(R.mipmap.icon_sanjiao_down);
        } else {
            city_pop_rl.setVisibility(View.VISIBLE);

            ArrayAdapter<String> cxarrz_adapter;

            if (type.equals("c")) {
                cxarrz_adapter = new ArrayAdapter<String>(
                        CustomManagerActivity.this, R.layout.view_textview, customTypes);

                city_iv.setImageResource(R.mipmap.icon_sanjiao_up);
            } else {
                cxarrz_adapter = new ArrayAdapter<String>(
                        CustomManagerActivity.this, R.layout.view_textview, customClasss);

                you_iv.setImageResource(R.mipmap.icon_sanjiao_up);
            }

            city_lv.setAdapter(cxarrz_adapter);
        }
    }


    private View convertView;
    private PopupWindow popupwindowd;


    // 选择时间
    public void initPopupWindow() {
        convertView = getLayoutInflater().inflate(R.layout.pop_city, null,
                false);
        popupwindowd = new PopupWindow(convertView,
                LinearLayout.LayoutParams.FILL_PARENT, getResources()
                .getDimensionPixelSize(R.dimen.hundred), true);
        //        popupwindowd.setAnimationStyle(R.style.AnimationPhoto);

        ExpandableListView city_lv = (ExpandableListView) convertView.findViewById(R.id.city_lv);


        popupwindowd.setBackgroundDrawable(new BitmapDrawable());
        popupwindowd.setFocusable(true);
        popupwindowd.setTouchable(true);
        popupwindowd.setOutsideTouchable(true);
        backgroundAlpha(0.5f);
        popupwindowd.setOnDismissListener(new poponDismissListener());
        popupwindowd.update();
    }

}
