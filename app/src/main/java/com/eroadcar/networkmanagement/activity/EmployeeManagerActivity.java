package com.eroadcar.networkmanagement.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.SaleManagerAdapter;
import com.eroadcar.networkmanagement.bean.HomeBean;
import java.util.ArrayList;

/**
 * Created by amos on 2018/7/28.
 */

public class EmployeeManagerActivity extends BaseActivity implements View.OnClickListener {
    private Button back_btn;
    private TextView title_tv;

    private GridView img_gv;

    private ArrayList<HomeBean> homeBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_manager);

        title_tv = (TextView) findViewById(R.id.title_tv);
        back_btn = (Button) findViewById(R.id.back_btn);

        img_gv = (GridView) findViewById(R.id.img_gv);

        title_tv.setText("员工管理");

        back_btn.setOnClickListener(this);

        setHomeBean();

        img_gv.setAdapter(new SaleManagerAdapter(EmployeeManagerActivity.this, homeBeans));

        img_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (homeBeans.get(i).getType().equals("fabu")) {
                    intent(TaskPublicActivity.class);
                } else if (homeBeans.get(i).getType().equals("liebiao")) {
                    intent(TaskActivity.class);
                } else if (homeBeans.get(i).getType().equals("renyuan")) {
                    if (application.userBean.getMg_role_ids().contains("1") ||
                            application.userBean.getMg_role_ids().contains("2") ||
                            application.userBean.getMg_role_ids().contains("4")) {
                        intent(EmployeeActivity.class);
                    } else {
                        intent(EmployeeDetailsActivity.class);
                    }
                }
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;

        }
    }

    private void setHomeBean() {
        String role = application.userBean.getMg_role_ids();

        homeBeans = new ArrayList<>();

        HomeBean bean = new HomeBean();
        bean.setImageId(R.mipmap.icon_renwu_fabu);
        bean.setContent("任务发布");
        bean.setNewsnum(0);
        bean.setType("fabu");

        HomeBean bean1 = new HomeBean();
        bean1.setImageId(R.mipmap.icon_renwu_liebiao);
        bean1.setContent("任务列表");
        bean1.setNewsnum(0);
        bean1.setType("liebiao");

        HomeBean bean2 = new HomeBean();
        bean2.setImageId(R.mipmap.icon_renyuan);
        bean2.setContent("人员详情");
        bean2.setNewsnum(0);
        bean2.setType("renyuan");


        if (role.contains("4")) {
            homeBeans.clear();

            homeBeans.add(bean);
            homeBeans.add(bean1);
            homeBeans.add(bean2);
        }

        if (role.contains("3")) {
            homeBeans.clear();

            homeBeans.add(bean1);
            homeBeans.add(bean2);
        }

        if (role.contains("2")) {
            homeBeans.clear();

            homeBeans.add(bean);
            homeBeans.add(bean1);
            homeBeans.add(bean2);
        }

        if (role.contains("1")) {
            homeBeans.clear();

            homeBeans.add(bean);
            homeBeans.add(bean1);
            homeBeans.add(bean2);
        }
    }

}
