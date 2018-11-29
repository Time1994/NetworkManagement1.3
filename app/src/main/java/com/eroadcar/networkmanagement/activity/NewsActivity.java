package com.eroadcar.networkmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.CarAdapter;
import com.eroadcar.networkmanagement.adapter.NewsAdapter;
import com.eroadcar.networkmanagement.bean.CarBean;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.NewsBean;
import com.eroadcar.networkmanagement.bean.StatisticsBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class NewsActivity extends BaseActivity implements OnClickListener {
    private Button other_btn, back_btn;
    private TextView title_tv;
    private ListView dot_lv;

    private LinearLayout none;

    private CommonBean<ArrayList<NewsBean>> commobBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        title_tv = (TextView) findViewById(R.id.title_tv);
        other_btn = (Button) findViewById(R.id.other_btn);
        back_btn = (Button) findViewById(R.id.back_btn);

        dot_lv = (ListView) findViewById(R.id.dot_lv);

        none = (LinearLayout) findViewById(R.id.none);

        back_btn.setOnClickListener(this);
        other_btn.setOnClickListener(this);

        title_tv.setText("消息中心");
        back_btn.setText("");

        dot_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if (commobBean.getData().get(i).getIc_info_type().equals("任务")) {
                        Intent intent = new Intent(NewsActivity.this, TaskActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(NewsActivity.this, OrderSalesActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        getPushInfoForSelf();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.other_btn:

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
                        if (commobBean.getData() != null && commobBean.getData().size() != 0) {
                            dot_lv.setAdapter(new NewsAdapter(NewsActivity.this, commobBean.getData()));

                            dot_lv.setVisibility(View.VISIBLE);
                            none.setVisibility(View.GONE);
                        } else {
                            dot_lv.setVisibility(View.GONE);
                            none.setVisibility(View.VISIBLE);
                        }
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

    private void getPushInfoForSelf() {
        loadingDialog.setMessage("正在获取数据...");
        loadingDialog.dialogShow();
        String url = Constant.GETPUSHINFOFORSELF;
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


        GsonRequest<CommonBean<ArrayList<NewsBean>>> requtst = new GsonRequest<CommonBean<ArrayList<NewsBean>>>(
                Request.Method.POST, url, listener_getInshopCars);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<NewsBean>>> listener_getInshopCars = new RequesListener<CommonBean<ArrayList<NewsBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<NewsBean>> arg0) {
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
