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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.TaskAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.TaskBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskActivity extends BaseActivity implements OnClickListener {
    private Button other_btn, back_btn;
    private TextView title_tv;
    private ListView dot_lv;
    private LinearLayout none;

    private RadioGroup title_rg;

    private CommonBean<ArrayList<TaskBean>> commonBean;

    private String type = "j";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        title_tv = (TextView) findViewById(R.id.title_tv);
        other_btn = (Button) findViewById(R.id.other_btn);
        back_btn = (Button) findViewById(R.id.back_btn);

        dot_lv = (ListView) findViewById(R.id.dot_lv);

        none = (LinearLayout) findViewById(R.id.none);

        title_rg = (RadioGroup) findViewById(R.id.title_rg);

        back_btn.setOnClickListener(this);
        other_btn.setOnClickListener(this);

        title_tv.setText("任务列表");
        back_btn.setText("");
//        other_btn.setText("发布任务");
        other_btn.setText("");

        if (application.userBean.getMg_role_ids().contains("3")) {
            other_btn.setText("");
            title_rg.setVisibility(View.GONE);

            type = "j";
        } else {
            type = "f";
        }

        title_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.jieshou_rb) {
                    type = "j";
                } else {
                    type = "f";
                }

                getJobListDetail();
            }
        });

        dot_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = null;
                if (type.equals("j")) {
                    intent = new Intent(TaskActivity.this, TaskDetailsEmployeeActivity.class);
                } else {
                    intent = new Intent(TaskActivity.this, TaskDetailsActivity.class);
                }
                intent.putExtra("BEAN", commonBean.getData().get(i));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getJobListDetail();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.other_btn:
                intent(TaskPublicActivity.class);
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
                        if (commonBean.getData() != null && commonBean.getData().size() != 0) {
                            dot_lv.setAdapter(new TaskAdapter(commonBean.getData(), TaskActivity.this, type));

                            none.setVisibility(View.GONE);
                            dot_lv.setVisibility(View.VISIBLE);
                        } else {
                            none.setVisibility(View.VISIBLE);
                            dot_lv.setVisibility(View.GONE);
                        }
                    }
                    ToastUtils.showShort(commonBean.getMsg());
                    break;

                default:
                    break;
            }
        }
    };

    private void getJobListDetail() {
        loadingDialog.setMessage("正在获取数据...");
        loadingDialog.dialogShow();
        String url = "";
        if (type.equals("j")) {
            url = Constant.GETSLAVEJOBDETAIL;
        } else {
            url = Constant.GETMASTERJOBDETAIL;
        }

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

        GsonRequest<CommonBean<ArrayList<TaskBean>>> requtst = new GsonRequest<CommonBean<ArrayList<TaskBean>>>(
                Request.Method.POST, url, listener_reqZxInfo);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<TaskBean>>> listener_reqZxInfo = new RequesListener<CommonBean<ArrayList<TaskBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<TaskBean>> arg0) {
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
}
