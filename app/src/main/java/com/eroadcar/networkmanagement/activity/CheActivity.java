package com.eroadcar.networkmanagement.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.CheAdapter;
import com.eroadcar.networkmanagement.adapter.RoleAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.RoleBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;


public class CheActivity extends BaseActivity implements View.OnClickListener {

    private Button back_btn, save_btn;
    private TextView title_tv;

    private GridView home_gv;

    public ArrayList<String> roleBeans = new ArrayList<>();
    public ArrayList<String> cheBeans = new ArrayList<>();

    public String ids = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roles);

        back_btn = (Button) findViewById(R.id.back_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);

        save_btn = (Button) findViewById(R.id.save_btn);

        home_gv = (GridView) findViewById(R.id.home_gv);

        title_tv.setText("品牌选择");

        back_btn.setOnClickListener(this);
        save_btn.setOnClickListener(this);

        ids = getIntent().getStringExtra("IDS");

        cheBeans.add("奇瑞");
        cheBeans.add("荣威");
        cheBeans.add("长安");
        cheBeans.add("吉利");
        cheBeans.add("比亚迪");
        cheBeans.add("上汽");
        cheBeans.add("广汽");
        cheBeans.add("北汽");
        cheBeans.add("腾势");
        cheBeans.add("别克");
        home_gv.setAdapter(new CheAdapter(CheActivity.this, cheBeans));
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.save_btn:
                if (roleBeans.size() == 0) {
                    ToastUtils.showShort("请选择车辆品牌");
                    return;
                }
                ids = "";
                for (int i = 0; i < roleBeans.size(); i++) {
                    ids += roleBeans.get(i) + ",";
                }

                Intent mIntent = new Intent();
                mIntent.putExtra("IDS", ids);
                setResult(RESULT_OK, mIntent);
                onBackPressed();
                break;
            default:
                break;
        }
    }
}
