package com.eroadcar.networkmanagement.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.HomeAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.HomeBean;
import com.eroadcar.networkmanagement.bean.MsgsBean;
import com.eroadcar.networkmanagement.bean.StatisticsBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private GridView home_gv;
    private TextView note_tv, time_tv, store_tv;
    private RadioGroup time_rg;
    private ImageView setting_iv;
    private RelativeLayout msg_rl, custom_rl, sale_rl, rent_rl;

    private ArrayList<HomeBean> homeBeans;
    private CommonBean<StatisticsBean> commobBean;
    private CommonBean<MsgsBean> msgsBeanCommonBean;

    private String type = "day", date = "";

    private int year, monthi, dayi, weeki;
    private String month, day, week;

    private Calendar calendar;

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setSwipeBackEnable(false);

        home_gv = (GridView) findViewById(R.id.home_gv);

        note_tv = (TextView) findViewById(R.id.note_tv);
        time_tv = (TextView) findViewById(R.id.time_tv);
        store_tv = (TextView) findViewById(R.id.store_tv);

        time_rg = (RadioGroup) findViewById(R.id.time_rg);

        setting_iv = (ImageView) findViewById(R.id.setting_iv);

        msg_rl = (RelativeLayout) findViewById(R.id.msg_rl);

        custom_rl = (RelativeLayout) findViewById(R.id.custom_rl);
        sale_rl = (RelativeLayout) findViewById(R.id.sale_rl);
        rent_rl = (RelativeLayout) findViewById(R.id.rent_rl);

        setting_iv.setOnClickListener(this);
        msg_rl.setOnClickListener(this);
        custom_rl.setOnClickListener(this);
        sale_rl.setOnClickListener(this);
        rent_rl.setOnClickListener(this);

//        setHomeBean();
//        home_gv.setAdapter(new HomeAdapter(HomeActivity.this, homeBeans));

        try {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            String s = "";
            if (hour >= 8 && hour < 10) {
                s = " 您好，每一天都是成功的开始，加油！";
            } else if (hour >= 10 && hour < 12) {
                s = " 您好，笃行致新，争创辉煌！";
            } else if (hour >= 12 && hour < 14) {
                s = " 您好，午休时间，身体是革命的本钱！";
            } else if (hour >= 14 && hour < 17) {
                s = " 您好，永不言退，我们是最好的团队！";
            } else if (hour >= 17 && hour < 18) {
                s = " 您好，感谢您一天辛勤的工作！";
            } else {
                s = " 您好，工作之余，好好享受生活吧！";
            }

            note_tv.setText(application.userBean.getMg_name() + s);
//        note_tv.setText(application.userBean.getMg_name() + "(" + application.userBean.getMg_groupname() + ") 您好，一天的工作开始了，加油！");
            store_tv.setText(application.userBean.getMg_shopname());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTime();
        time_tv.setText(date);

        time_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                if (arg1 == R.id.day_rb) {
                    type = "day";

                    date = year + "-" + month + "-" + day;

                } else if (arg1 == R.id.month_rb) {
                    type = "month";

                    date = month + "月";
                }


                time_tv.setText(date);

                reqStatisticsForManager(type, date);
            }
        });

        home_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (homeBeans.get(i).getType().equals("sale")) {
                    try {
                        Intent intent = new Intent(HomeActivity.this, SaleManagerActivity.class);
                        intent.putExtra("BEAN", msgsBeanCommonBean.getData());
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();

                        intent(SaleManagerActivity.class);
                    }
                } else if (homeBeans.get(i).getType().equals("tong")) {
                    if (application.userBean.getMg_code().equals("9004001") ||
                            application.userBean.getMg_code().equals("9001001")) {
                        intent(TongActivity.class);
                    } else {
                        Intent intent = new Intent(HomeActivity.this, StatisticsActivity.class);
                        intent.putExtra("CODE", application.userBean.getMg_shopcode());
                        startActivity(intent);
                    }
                } else if (homeBeans.get(i).getType().equals("employee")) {
//                    if (application.userBean.getMg_role_ids().contains("1") ||
//                            application.userBean.getMg_role_ids().contains("2") ||
//                            application.userBean.getMg_role_ids().contains("4")) {
//                        intent(EmployeeActivity.class);
//                    } else {
//                        intent(EmployeeDetailsActivity.class);
//                    }
                    intent(EmployeeManagerActivity.class);
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        reqStatisticsForManager(type, date);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.setting_iv:
                intent(SettingActivity.class);
                break;

            case R.id.msg_rl:
                intent(NewsActivity.class);
                break;

            case R.id.sale_rl:
                if (application.userBean.getMg_role_ids().contains("3")) {
                    intent(OrderSalesActivity.class);
                } else {
                    intent(OrderSalesManagerActivity.class);
                }
                break;

            case R.id.rent_rl:
                if (application.userBean.getMg_role_ids().contains("3")) {
//                    intent(OrderSalesActivity.class);
                    Intent intent = new Intent(HomeActivity.this, OrderSalesActivity.class);
                    intent.putExtra("FROM", "RENT");
                    startActivity(intent);
                } else {
//                    intent(OrderSalesManagerActivity.class);
                    Intent intent = new Intent(HomeActivity.this, OrderSalesManagerActivity.class);
                    intent.putExtra("FROM", "RENT");
                    startActivity(intent);
                }
                break;

            case R.id.custom_rl:
                if (application.userBean.getMg_role_ids().contains("3")) {
                    intent(CustomActivity.class);
                } else {
                    intent(CustomManagerActivity.class);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }

    /**
     * 退出程序
     */
    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showShort(R.string.exit_app);
            exitTime = System.currentTimeMillis();
        } else {
            application.exit();
        }
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (commobBean.getState().equals("success")) {
                        ((TextView) findViewById(R.id.sale_tv)).setText(commobBean.getData().getXs_order_count());
                        ((TextView) findViewById(R.id.salem_tv)).setText(commobBean.getData().getXs_order_price());//10000
                        ((TextView) findViewById(R.id.rent_tv)).setText(commobBean.getData().getZl_order_count());
                        ((TextView) findViewById(R.id.rentm_tv)).setText(commobBean.getData().getZl_order_price());
                        ((TextView) findViewById(R.id.wei_tv)).setText(commobBean.getData().getWx_order_count());
                        ((TextView) findViewById(R.id.weim_tv)).setText(commobBean.getData().getWx_order_price());
                        ((TextView) findViewById(R.id.custom_tv)).setText(commobBean.getData().getQk_person_count());

                        if (application.userBean.getMg_role_ids().contains("3")) {
//                            ((TextView) findViewById(R.id.wei_tv)).setText(commobBean.getData().getWx_order_count());
//                            ((TextView) findViewById(R.id.weim_tv)).setText((Double.valueOf(commobBean.getData().getXs_order_price())
//                                    + Double.valueOf(commobBean.getData().getZl_order_price())) + "");
                            reqStatisticsForSeller(type, date);
                        }
                    }

                    getPushCenter();

                    break;
                case 2:
                    if (commobBean.getState().equals("success")) {
                        ((TextView) findViewById(R.id.wei_tv)).setText(commobBean.getData().getPm_sort_rank());
                        ((TextView) findViewById(R.id.weim_tv)).setText(commobBean.getData().getPm_order_price());
                        ((TextView) findViewById(R.id.custom_tv)).setText(commobBean.getData().getQk_person_count());
                    }

                    break;
                case 3:
//                    if (msgsBeanCommonBean.getState().equals("success")) {
                    setHomeBean();
                    home_gv.setAdapter(new HomeAdapter(HomeActivity.this, homeBeans));
//                    }

                    break;
                case -2:
                    setHomeBean();
                    home_gv.setAdapter(new HomeAdapter(HomeActivity.this, homeBeans));
                    break;
                default:
                    break;
            }
        }
    };

    private void reqStatisticsForManager(String type, String data) {
        loadingDialog.setMessage("正在获取数据...");
        loadingDialog.dialogShow();
        String url = Constant.REQSTATISTICSFORMANAGER;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appcode", IMEI);
        map.put("apptype", Constant.APPTYPE);
        if (application == null || application.userBean == null || application.userBean.getMg_id() == null) {
            return;
        }
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

        map.put("type", type);
        map.put("date", data.replace("-", "").replace("月", ""));


        GsonRequest<CommonBean<StatisticsBean>> requtst = new GsonRequest<CommonBean<StatisticsBean>>(
                Request.Method.POST, url, listener_getInshopCars);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<StatisticsBean>> listener_getInshopCars = new RequesListener<CommonBean<StatisticsBean>>() {
        @Override
        public void onResponse(CommonBean<StatisticsBean> arg0) {
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

    private void reqStatisticsForSeller(String type, String data) {
        loadingDialog.setMessage("正在获取数据...");
        loadingDialog.dialogShow();
        String url = Constant.REQSTATISTICSFORSELLER;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appcode", IMEI);
        map.put("apptype", Constant.APPTYPE);
        map.put("mg_id", application.userBean
                .getMg_id());
        map.put("mg_name", application.userBean
                .getMg_name());
        map.put("mg_shopsid", application.userBean
                .getMg_shopsid());
        map.put("mg_groupid", application.userBean
                .getMg_groupid());
        map.put("mg_shopname", application.userBean
                .getMg_shopname());
        map.put("mg_shopcode", application.userBean
                .getMg_shopcode());
        map.put("mg_code", application.userBean
                .getMg_code());
        map.put("mg_groupname", application.userBean
                .getMg_groupname());
        map.put("mg_posid", application.userBean
                .getMg_posid());
        map.put("mg_posname", application.userBean
                .getMg_posname());

        map.put("type", type);
        map.put("date", data.replace("-", "").replace("月", ""));

        GsonRequest<CommonBean<StatisticsBean>> requtst = new GsonRequest<CommonBean<StatisticsBean>>(
                Request.Method.POST, url, listener_reqStatisticsForSeller);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<StatisticsBean>> listener_reqStatisticsForSeller = new RequesListener<CommonBean<StatisticsBean>>() {
        @Override
        public void onResponse(CommonBean<StatisticsBean> arg0) {
            // TODO Auto-generated method stub
            commobBean = arg0;
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

    private void getPushCenter() {
        loadingDialog.setMessage("正在获取数据...");
        loadingDialog.dialogShow();
        String url = Constant.GETPUSHCENTER;
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

        map.put("mg_role_ids", application.userBean.getMg_role_ids());


        GsonRequest<CommonBean<MsgsBean>> requtst = new GsonRequest<CommonBean<MsgsBean>>(
                Request.Method.POST, url, listener_getPushCenter);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<MsgsBean>> listener_getPushCenter = new RequesListener<CommonBean<MsgsBean>>() {
        @Override
        public void onResponse(CommonBean<MsgsBean> arg0) {
            // TODO Auto-generated method stub
            msgsBeanCommonBean = arg0;
            mHandler.sendEmptyMessage(3);
            loadingDialog.dismiss();
        }

        @Override
        public void onErrorResponse(VolleyError arg0) {
            // TODO Auto-generated method stub
            mHandler.sendEmptyMessage(-2);
            loadingDialog.dismiss();
        }

    };


    private void setTime() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthi = calendar.get(Calendar.MONTH) + 1;
        dayi = calendar.get(Calendar.DAY_OF_MONTH);
        weeki = calendar.get(Calendar.WEEK_OF_YEAR);

        month = dataLong(monthi);
        day = dataLong(dayi);
        week = dataLong(weeki);

        date = year + "-" + month + "-" + day;
    }


    private void setHomeBean() {
        String role = application.userBean.getMg_role_ids();

        homeBeans = new ArrayList<>();

        HomeBean bean = new HomeBean();
        bean.setImageId(R.mipmap.icon_home_saleb);
        bean.setContent("销售管理");
        try {
            if ((Integer.valueOf(msgsBeanCommonBean.getData().getSell().getTotal()) +
                    Integer.valueOf(msgsBeanCommonBean.getData().getLease().getTotal())) != 0) {
                bean.setNewsnum((Integer.valueOf(msgsBeanCommonBean.getData().getSell().getTotal()) +
                        Integer.valueOf(msgsBeanCommonBean.getData().getLease().getTotal())));
            } else {
                bean.setNewsnum(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            bean.setNewsnum(0);
        }
        bean.setType("sale");

        HomeBean bean1 = new HomeBean();
        bean1.setImageId(R.mipmap.icon_home_weib);
        bean1.setContent("维修管理");
        bean1.setNewsnum(0);
        bean1.setType("wei");

        HomeBean bean2 = new HomeBean();
        bean2.setImageId(R.mipmap.icon_home_ku);
        bean2.setContent("仓库管理");
        bean2.setNewsnum(0);
        bean2.setType("ku");

        HomeBean bean3 = new HomeBean();
        bean3.setImageId(R.mipmap.icon_home_employee);
        bean3.setContent("员工管理");
        bean3.setNewsnum(0);
        bean3.setType("employee");

        HomeBean bean4 = new HomeBean();
        bean4.setImageId(R.mipmap.icon_home_car);
        bean4.setContent("车辆管理");
        bean4.setNewsnum(0);
        bean4.setType("car");

        HomeBean bean5 = new HomeBean();
        bean5.setImageId(R.mipmap.icon_home_tong);
        bean5.setContent("数据统计");
        bean5.setNewsnum(0);
        bean5.setType("tong");


        if (role.contains("4")) {
            ((RelativeLayout) findViewById(R.id.wei_rl)).setVisibility(View.GONE);
            ((RelativeLayout) findViewById(R.id.custom_rl)).setVisibility(View.GONE);

            homeBeans.add(bean);
            homeBeans.add(bean3);
            homeBeans.add(bean5);
        }

        if (role.contains("3")) {
            ((ImageView) findViewById(R.id.wei_iv)).setImageResource(R.mipmap.icon_home_pai);
            ((TextView) findViewById(R.id.wei)).setText("当前排名");
            ((TextView) findViewById(R.id.dan)).setText("名");

            homeBeans.clear();

            homeBeans.add(bean);

//            bean3.setContent("任务管理");
            homeBeans.add(bean3);

            homeBeans.add(bean5);
        }

        if (role.contains("2")) {
            ((RelativeLayout) findViewById(R.id.wei_rl)).setVisibility(View.GONE);
            ((RelativeLayout) findViewById(R.id.custom_rl)).setVisibility(View.VISIBLE);

            homeBeans.clear();

            homeBeans.add(bean);
            homeBeans.add(bean3);
            homeBeans.add(bean5);
        }

        if (role.contains("1")) {
            ((RelativeLayout) findViewById(R.id.wei_rl)).setVisibility(View.VISIBLE);
            ((RelativeLayout) findViewById(R.id.custom_rl)).setVisibility(View.VISIBLE);

            homeBeans.clear();

            homeBeans.add(bean);
            homeBeans.add(bean1);
            homeBeans.add(bean2);
            homeBeans.add(bean3);
            homeBeans.add(bean4);
            homeBeans.add(bean5);
        }
    }
}
