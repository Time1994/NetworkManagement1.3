package com.eroadcar.networkmanagement.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.TaskAdapter;
import com.eroadcar.networkmanagement.adapter.TaskEmployeeAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.TaskBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskDetailsActivity extends BaseActivity implements OnClickListener {
    private Button other_btn, back_btn;
    private TextView title_tv;
    private ListView dot_lv;

    private TextView tit_tv, content_tv, name_tv, time_tv, jieshou_tv;

    private ScrollView task_sv;

    private TaskBean commonBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        title_tv = (TextView) findViewById(R.id.title_tv);
        other_btn = (Button) findViewById(R.id.other_btn);
        back_btn = (Button) findViewById(R.id.back_btn);

        dot_lv = (ListView) findViewById(R.id.dot_lv);

        tit_tv = (TextView) findViewById(R.id.tit_tv);
        content_tv = (TextView) findViewById(R.id.content_tv);
        name_tv = (TextView) findViewById(R.id.name_tv);
        time_tv = (TextView) findViewById(R.id.time_tv);
        jieshou_tv = (TextView) findViewById(R.id.jieshou_tv);

        task_sv = (ScrollView) findViewById(R.id.task_sv);

        back_btn.setOnClickListener(this);
        other_btn.setOnClickListener(this);

        title_tv.setText("任务详情");
        back_btn.setText("");
        other_btn.setText("");

        commonBean = (TaskBean) getIntent().getSerializableExtra("BEAN");


        dot_lv.setAdapter(new TaskEmployeeAdapter(commonBean.getWorkers(), TaskDetailsActivity.this));
        setListViewHeightBasedOnChildren(dot_lv);

        tit_tv.setText("任务主题：" + commonBean.getWj_job_name());
        content_tv.setText(commonBean.getWj_job_context());
        name_tv.setText("发布人：" + commonBean.getWj_employer());
        time_tv.setText(commonBean.getWj_create_time());
        jieshou_tv.setText(commonBean.getWj_employee_names());


        handler.post(new Runnable() {
            @Override
            public void run() {
                task_sv.fullScroll(ScrollView.FOCUS_UP);
            }
        });

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

    Handler handler = new Handler();

}
