package com.eroadcar.networkmanagement.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.DialogItemAdapter;
import com.eroadcar.networkmanagement.adapter.OrderRentManagerAdapter;
import com.eroadcar.networkmanagement.adapter.OrderSaleManagerAdapter;
import com.eroadcar.networkmanagement.adapter.ZhengxAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.MsgsBean;
import com.eroadcar.networkmanagement.bean.OrderRentBean;
import com.eroadcar.networkmanagement.bean.OrderSalesBean;
import com.eroadcar.networkmanagement.bean.RoleBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ExcelUtil;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class OrderSalesManagerActivity extends BaseActivity implements OnClickListener {
    private Button other_btn, back_btn;
    private TextView title_tv;
    private ListView dot_lv;
    private LinearLayout none;

    private TextView num_sale_tv, num_rent_tv, riqi_tv;

    private RadioGroup title_rg;//, order_rg, rent_rg;

    private ListView city_lv;
    private RelativeLayout city_rl, city_pop_rl;
    private ImageView city_iv;
    private TextView city_tv;

    private ImageView calendar_iv;

    private CommonBean<ArrayList<OrderSalesBean>> commonBean;
    private CommonBean<ArrayList<OrderRentBean>> commonRentBean;

    private ArrayList<OrderSalesBean> orderSalesBeans = new ArrayList<>(),
            saleBeanIng = new ArrayList<>(), saleBeanGuo = new ArrayList<>(), saleBeanBo = new ArrayList<>(), saleBeanYiDing = new ArrayList<>(), saleBeanYiBu = new ArrayList<>(), saleBeanDaiDing = new ArrayList<>(), saleBeanYiShen = new ArrayList<>(), saleBeanDaiQuan = new ArrayList<>(), saleBeanYiQuan = new ArrayList<>(), saleBeanDone = new ArrayList<>();
    private ArrayList<OrderRentBean> orderRentBeans = new ArrayList<>(),
            rentBeanIng = new ArrayList<>(), rentBeanBu = new ArrayList<>(), rentBeanYi = new ArrayList<>(), rentBeanDonw = new ArrayList<>();

    private String yfs_sellsq_state = "", type = "all", typer = "";

    private MsgsBean msgsBean;

    private int sale = 0, rent = 0, clickIndex = 1;

    private ArrayList<String> saleState, rentState;

    private Calendar calendar;
    private java.text.DecimalFormat fo;

    private int year, monthi, dayi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        title_tv = (TextView) findViewById(R.id.title_tv);
        other_btn = (Button) findViewById(R.id.other_btn);
        back_btn = (Button) findViewById(R.id.back_btn);

        dot_lv = (ListView) findViewById(R.id.dot_lv);
        none = (LinearLayout) findViewById(R.id.none);

        title_rg = (RadioGroup) findViewById(R.id.title_rg);

        num_sale_tv = (TextView) findViewById(R.id.num_sale_tv);
        num_rent_tv = (TextView) findViewById(R.id.num_rent_tv);

//        order_rg = (RadioGroup) findViewById(R.id.order_rg);
//        rent_rg = (RadioGroup) findViewById(R.id.rent_rg);

        city_lv = (ListView) findViewById(R.id.city_lv);
        city_pop_rl = (RelativeLayout) findViewById(R.id.city_pop_rl);
        city_rl = (RelativeLayout) findViewById(R.id.city_rl);
        city_iv = (ImageView) findViewById(R.id.city_iv);
        city_tv = (TextView) findViewById(R.id.city_tv);

        calendar_iv = (ImageView) findViewById(R.id.calendar_iv);

        riqi_tv = (TextView) findViewById(R.id.riqi_tv);

        calendar_iv.setOnClickListener(this);

        back_btn.setOnClickListener(this);
        other_btn.setOnClickListener(this);

        city_rl.setOnClickListener(this);
        city_pop_rl.setOnClickListener(this);

//        order_rg.setVisibility(View.VISIBLE);
        city_rl.setVisibility(View.VISIBLE);

        title_tv.setText("订单记录");
        back_btn.setText("");
        other_btn.setText("导出文件");

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthi = calendar.get(Calendar.MONTH) + 1;
        dayi = calendar.get(Calendar.DAY_OF_MONTH);

        fo = new java.text.DecimalFormat("00");
        month = new StringBuilder().append(year).append("-")
                .append(fo.format(monthi)) + "";
        riqi_tv.setText(month);

//        Drawable drawable = getResources().getDrawable(R.mipmap.icon_order_state);
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        other_btn.setCompoundDrawables(null, null, drawable, null);
//
        dot_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickIndex = i;

                if (((RadioButton) findViewById(R.id.sale_rb)).isChecked()) {
                    Intent intent = new Intent(OrderSalesManagerActivity.this, OrderSaleManagerDetailsActivity.class);
                    intent.putExtra("BEAN", orderSalesBeans.get(i));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(OrderSalesManagerActivity.this, OrderRentManagerDetailsActivity.class);
                    intent.putExtra("BEAN", commonRentBean.getData().get(i));
                    startActivity(intent);
                }
            }
        });

        city_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (((RadioButton) findViewById(R.id.sale_rb)).isChecked()) {
                    if (i == 0) {
                        orderSalesBeans = commonBean.getData();
                    } else if (i == 1) {
                        orderSalesBeans = saleBeanIng;
                    } else if (i == 2) {
                        orderSalesBeans = saleBeanYiDing;
                    } else if (i == 3) {
                        orderSalesBeans = saleBeanYiBu;
                    } else if (i == 4) {
                        orderSalesBeans = saleBeanBo;
                    } else if (i == 5) {
                        orderSalesBeans = saleBeanGuo;
                    } else if (i == 6) {
                        orderSalesBeans = saleBeanDaiDing;
                    } else if (i == 7) {
                        orderSalesBeans = saleBeanYiShen;
                    } else if (i == 8) {
                        orderSalesBeans = saleBeanDaiQuan;
                    } else if (i == 9) {
                        orderSalesBeans = saleBeanYiQuan;
                    } else if (i == 10) {
                        orderSalesBeans = saleBeanDone;
                    }

                    city_tv.setText(saleState.get(i));
                } else {
                    if (i == 0) {
                        orderRentBeans = commonRentBean.getData();
                    } else if (i == 1) {
                        orderRentBeans = rentBeanIng;
                    } else if (i == 2) {
                        orderRentBeans = rentBeanBu;
                    } else if (i == 3) {
                        orderRentBeans = rentBeanYi;
                    } else if (i == 4) {
                        orderRentBeans = rentBeanDonw;
                    }

                    city_tv.setText(rentState.get(i));
                }


                setListView();

                city_pop_rl.setVisibility(View.GONE);
                city_iv.setImageResource(R.mipmap.icon_sanjiao_down);
            }
        });

        title_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                clickIndex = 1;
                if (i == R.id.sale_rb) {
//                    order_rg.setVisibility(View.VISIBLE);
//                    rent_rg.setVisibility(View.GONE);
                    other_btn.setText("导出文件");
                    getSellOrder(yfs_sellsq_state);
                } else {
//                    order_rg.setVisibility(View.GONE);
//                    rent_rg.setVisibility(View.VISIBLE);
                    other_btn.setText("");
                    getLeaseOrderForManager(typer);
                }

                city_pop_rl.setVisibility(View.GONE);
                city_iv.setImageResource(R.mipmap.icon_sanjiao_down);
            }
        });

//        order_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                clickIndex = 1;
//                if (i == R.id.all_rb) {
//                    type = "all";
//                } else if (i == R.id.ing_rb) {
//                    type = "ing";
//                } else if (i == R.id.guo_rb) {
//                    type = "guo";
//                } else if (i == R.id.bo_rb) {
//                    type = "bo";
//                }
//
//                getSellOrder(yfs_sellsq_state);
//            }
//        });
//
//        rent_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                clickIndex = 1;
//                if (i == R.id.allr_rb) {
//                    typer = "";
//                } else if (i == R.id.ingr_rb) {
//                    typer = "申请中";
//                } else if (i == R.id.yi_rb) {
//                    typer = "已交车";
//                }
//
//                getLeaseOrderForManager(typer);
//            }
//        });

        try {
            msgsBean = (MsgsBean) getIntent().getSerializableExtra("BEAN");

            sale = Integer.valueOf(msgsBean.getSell().getTotal());
            rent = Integer.valueOf(msgsBean.getLease().getTotal());

            if (sale != 0) {
//                num_sale_tv.setText(sale + "");
                num_sale_tv.setVisibility(View.VISIBLE);
            }

            if (rent != 0) {
//                num_rent_tv.setText(rent + "");
                num_rent_tv.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String from = getIntent().getStringExtra("FROM");

            if (from.equals("RENT")) {
                ((RadioButton) findViewById(R.id.rent_rb)).setChecked(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (((RadioButton) findViewById(R.id.sale_rb)).isChecked()) {
            getSellOrder(yfs_sellsq_state);
        } else {
            getLeaseOrderForManager(typer);
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
//                setPopWindow(yfs_sellsq_state);

                showDialogMessage(OrderSalesManagerActivity.this, "确认导出文件吗？", new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            ExcelUtil.writeExcel(OrderSalesManagerActivity.this, orderSalesBeans, "征信" + new Date().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        dialogMessage.dismiss();
                    }
                });

                break;
            case R.id.city_rl:

                setPop();
                break;

            case R.id.calendar_iv:
                Intent intent = new Intent(OrderSalesManagerActivity.this, DateActivity.class);
                startActivityForResult(intent, 1);
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
                        if (commonBean.getData() != null) {
                            orderSalesBeans.clear();
                            saleBeanIng.clear();
                            saleBeanYiDing.clear();
                            saleBeanYiBu.clear();
                            saleBeanBo.clear();
                            saleBeanGuo.clear();
                            saleBeanDaiDing.clear();
                            saleBeanYiShen.clear();
                            saleBeanDaiQuan.clear();
                            saleBeanYiQuan.clear();
                            saleBeanDone.clear();

                            orderSalesBeans = commonBean.getData();
//                            if (type.equals("all")) {
//                                orderSalesBeans = commonBean.getData();
//                            } else if (type.equals("ing")) {
//                                orderSalesBeans.clear();
//                                for (int i = 0; i < commonBean.getData().size(); i++) {
//                                    if (commonBean.getData().get(i).getYfs_sellsq_state().equals("征信中")) {
//                                        orderSalesBeans.add(commonBean.getData().get(i));
//                                    }
//                                }
//                            } else if (type.equals("guo")) {
//                                orderSalesBeans.clear();
//                                for (int i = 0; i < commonBean.getData().size(); i++) {
//                                    if (commonBean.getData().get(i).getYfs_sellsq_state().equals("已收定金")) {
//                                        orderSalesBeans.add(commonBean.getData().get(i));
//                                    }
//                                }
//                            } else if (type.equals("bo")) {
//                                orderSalesBeans.clear();
//                                for (int i = 0; i < commonBean.getData().size(); i++) {
//                                    if (commonBean.getData().get(i).getYfs_sellsq_state().equals("已补全资料")) {
//                                        orderSalesBeans.add(commonBean.getData().get(i));
//                                    }
//                                }
//                            }

                            for (int i = 0; i < orderSalesBeans.size(); i++) {
                                if (orderSalesBeans.get(i).getYfs_sellsq_state().equals("征信中")) {
                                    saleBeanIng.add(orderSalesBeans.get(i));
                                } else if (orderSalesBeans.get(i).getYfs_sellsq_state().equals("已收定金")) {
                                    saleBeanYiDing.add(orderSalesBeans.get(i));
                                } else if (orderSalesBeans.get(i).getYfs_sellsq_state().equals("已补全资料")) {
                                    saleBeanYiBu.add(orderSalesBeans.get(i));
                                } else if (orderSalesBeans.get(i).getYfs_sellsq_state().equals("征信驳回")) {
                                    saleBeanBo.add(orderSalesBeans.get(i));
                                } else if (orderSalesBeans.get(i).getYfs_sellsq_state().equals("征信通过")) {
                                    saleBeanGuo.add(orderSalesBeans.get(i));
                                } else if (orderSalesBeans.get(i).getYfs_sellsq_state().equals("待收定金")) {
                                    saleBeanDaiDing.add(orderSalesBeans.get(i));
                                } else if (orderSalesBeans.get(i).getYfs_sellsq_state().equals("已审核充电桩")) {
                                    saleBeanYiShen.add(orderSalesBeans.get(i));
                                } else if (orderSalesBeans.get(i).getYfs_sellsq_state().equals("待收全款")) {
                                    saleBeanDaiQuan.add(orderSalesBeans.get(i));
                                } else if (orderSalesBeans.get(i).getYfs_sellsq_state().equals("已收全款")) {
                                    saleBeanYiQuan.add(orderSalesBeans.get(i));
                                } else if (orderSalesBeans.get(i).getYfs_sellsq_state().equals("已完成")) {
                                    saleBeanDone.add(orderSalesBeans.get(i));
                                }
                            }

                            saleState = new ArrayList<>();
                            saleState.add(0, "全部" + "（ " + orderSalesBeans.size() + " ）");
                            saleState.add(1, "征信中" + "（ " + saleBeanIng.size() + " ）");
                            saleState.add(2, "已收定金" + "（ " + saleBeanYiDing.size() + " ）");
                            saleState.add(3, "已补全资料" + "（ " + saleBeanYiBu.size() + " ）");
                            saleState.add(4, "征信驳回" + "（ " + saleBeanBo.size() + " ）");
                            saleState.add(5, "征信通过" + "（ " + saleBeanGuo.size() + " ）");
                            saleState.add(6, "待收定金" + "（ " + saleBeanDaiDing.size() + " ）");
                            saleState.add(7, "已审核充电桩" + "（ " + saleBeanYiShen.size() + " ）");
                            saleState.add(8, "待收全款" + "（ " + saleBeanDaiQuan.size() + " ）");
                            saleState.add(9, "已收全款" + "（ " + saleBeanYiQuan.size() + " ）");
                            saleState.add(10, "已完成" + "（ " + saleBeanDone.size() + " ）");

                            city_tv.setText("全部" + "（ " + orderSalesBeans.size() + " ）");

                            setListView();
                        } else {
                            none.setVisibility(View.VISIBLE);
                            dot_lv.setVisibility(View.GONE);
                        }
                    }
                    ToastUtils.showShort(commonBean.getMsg());
                    break;
                case 2:
                    if (commonRentBean.getState().equals("success")) {
                        if (commonRentBean.getData() != null) {

                            orderRentBeans.clear();
                            rentBeanIng.clear();
                            rentBeanYi.clear();
                            rentBeanDonw.clear();
                            rentBeanBu.clear();

                            orderRentBeans = commonRentBean.getData();

                            for (int i = 0; i < orderRentBeans.size(); i++) {
                                if (orderRentBeans.get(i).getYfs_leasesq_state().equals("申请中")) {
                                    rentBeanIng.add(orderRentBeans.get(i));
                                } else if (orderRentBeans.get(i).getYfs_leasesq_state().equals("部分交车")) {
                                    rentBeanBu.add(orderRentBeans.get(i));
                                } else if (orderRentBeans.get(i).getYfs_leasesq_state().equals("已交车")) {
                                    rentBeanYi.add(orderRentBeans.get(i));
                                } else if (orderRentBeans.get(i).getYfs_leasesq_state().equals("已完成")) {
                                    rentBeanDonw.add(orderRentBeans.get(i));
                                }
                            }

                            rentState = new ArrayList<>();
                            rentState.add(0, "全部（ " + orderRentBeans.size() + " ）");
                            rentState.add(1, "申请中（ " + rentBeanIng.size() + " ）");
                            rentState.add(2, "部分交车（ " + rentBeanBu.size() + " ）");
                            rentState.add(3, "已交车（ " + rentBeanYi.size() + " ）");
                            rentState.add(4, "已完成（ " + rentBeanDonw.size() + " ）");

                            city_tv.setText("全部（ " + orderRentBeans.size() + " ）");

                            setListView();
                        } else {
                            none.setVisibility(View.VISIBLE);
                            dot_lv.setVisibility(View.GONE);
                        }
                    }
                    ToastUtils.showShort(commonRentBean.getMsg());
                    break;
                default:
                    break;
            }
        }
    };

    private void getSellOrder(String yfs_sellsq_state) {
        loadingDialog.setMessage("正在获取数据...");
        loadingDialog.dialogShow();
        String url = Constant.GETSELLORDERFORMANAGER;

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

        map.put("yfs_sellsq_state", yfs_sellsq_state);

        String t = "";
        if (!month.equals("")) {
            t = "month";
        } else {
            t = "day";
        }

        map.put("type", t);//day，month
        map.put("month", month);
        map.put("sdate", startDate);
        map.put("edate", endDate);

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

    private void getLeaseOrderForManager(String yfs_leasesq_state) {
        loadingDialog.setMessage("正在获取数据...");
        loadingDialog.dialogShow();
        String url = Constant.GETLEASEORDERFORMANAGER;

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

        map.put("yfs_leasesq_state", yfs_leasesq_state);

        String t = "";
        if (!month.equals("")) {
            t = "month";
        } else {
            t = "day";
        }

        map.put("type", t);//day，month
        map.put("month", month);
        map.put("sdate", startDate);
        map.put("edate", endDate);

        GsonRequest<CommonBean<ArrayList<OrderRentBean>>> requtst = new GsonRequest<CommonBean<ArrayList<OrderRentBean>>>(
                Request.Method.POST, url, listener_getLeaseOrderForUser);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<OrderRentBean>>> listener_getLeaseOrderForUser = new RequesListener<CommonBean<ArrayList<OrderRentBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<OrderRentBean>> arg0) {
            // TODO Auto-generated method stub
            commonRentBean = arg0;
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

    private void setListView() {
        if (((RadioButton) findViewById(R.id.sale_rb)).isChecked()) {
            if (orderSalesBeans.size() == 0) {
                none.setVisibility(View.VISIBLE);
                dot_lv.setVisibility(View.GONE);
            } else {
                dot_lv.setAdapter(new OrderSaleManagerAdapter(OrderSalesManagerActivity.this,
                        orderSalesBeans));
                none.setVisibility(View.GONE);
                dot_lv.setVisibility(View.VISIBLE);

                dot_lv.setSelection(clickIndex - 1);
            }
        } else {
            if (orderRentBeans.size() == 0) {
                none.setVisibility(View.VISIBLE);
                dot_lv.setVisibility(View.GONE);
            } else {
                dot_lv.setAdapter(new OrderRentManagerAdapter(OrderSalesManagerActivity.this, orderRentBeans));
                none.setVisibility(View.GONE);
                dot_lv.setVisibility(View.VISIBLE);

                dot_lv.setSelection(clickIndex - 1);
            }
        }
    }

    private void setPop() {
        if (city_pop_rl.isShown()) {
            city_pop_rl.setVisibility(View.GONE);
            city_iv.setImageResource(R.mipmap.icon_sanjiao_down);
        } else {
            city_pop_rl.setVisibility(View.VISIBLE);
            city_iv.setImageResource(R.mipmap.icon_sanjiao_up);
            ArrayAdapter<String> cxarrz_adapter;

            if (((RadioButton) findViewById(R.id.sale_rb)).isChecked()) {
                cxarrz_adapter = new ArrayAdapter<String>(
                        OrderSalesManagerActivity.this, R.layout.view_textview, saleState);
            } else {
                cxarrz_adapter = new ArrayAdapter<String>(
                        OrderSalesManagerActivity.this, R.layout.view_textview, rentState);
            }

            city_lv.setAdapter(cxarrz_adapter);
        }
    }

    private String startDate = "", endDate = "", month = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1) {
            startDate = data.getExtras().getString("start");
            endDate = data.getExtras().getString("end");
            month = data.getExtras().getString("month");
        }

        if (month.equals("")) {
            riqi_tv.setText(startDate + " 至 " + endDate);
        } else {
            riqi_tv.setText(month);
        }

        if (((RadioButton) findViewById(R.id.sale_rb)).isChecked()) {
            getSellOrder(yfs_sellsq_state);
        } else {
            getLeaseOrderForManager(typer);
        }
    }

    private void setPopWindow(String type) {
        //准备PopupWindow的布局View
        final View popupView = LayoutInflater.from(this).inflate(R.layout.popup, null);
        //初始化一个PopupWindow，width和height都是WRAP_CONTENT
        final PopupWindow popupWindow = new PopupWindow(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow的视图内容
        popupWindow.setContentView(popupView);
        //点击空白区域PopupWindow消失，这里必须先设置setBackgroundDrawable，否则点击无反应
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
        //设置PopupWindow动画
        popupWindow.setAnimationStyle(R.style.AnimHorizontal);
        //设置是否允许PopupWindow的范围超过屏幕范围
        popupWindow.setClippingEnabled(true);
        //设置PopupWindow消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        if (type.equals("")) {
            ((RadioButton) popupView.findViewById(R.id.all_rb)).setChecked(true);
        } else if (type.equals("征信中")) {
            ((RadioButton) popupView.findViewById(R.id.ing_rb)).setChecked(true);
        } else if (type.equals("征信通过")) {
            ((RadioButton) popupView.findViewById(R.id.tong_rb)).setChecked(true);
        } else if (type.equals("征信驳回")) {
            ((RadioButton) popupView.findViewById(R.id.bohui_rb)).setChecked(true);
        }

        RadioGroup state_rg = (RadioGroup) popupView.findViewById(R.id.state_rg);

        state_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.all_rb) {
                    yfs_sellsq_state = "";
                } else if (i == R.id.ing_rb) {
                    yfs_sellsq_state = "征信中";
                } else if (i == R.id.tong_rb) {
                    yfs_sellsq_state = "征信通过";
                } else if (i == R.id.bohui_rb) {
                    yfs_sellsq_state = "征信驳回";
                }
                popupWindow.dismiss();
                getSellOrder(yfs_sellsq_state);
            }
        });

        //PopupWindow在targetView下方弹出
        popupWindow.showAsDropDown(other_btn);
    }

    /**
     * 弹窗
     */
    @SuppressLint("NewApi")
    public void showDialogList(Context context, OrderSalesBean bean) {
        dialogMessage = new DialogPlus.Builder(context)
                .setContentHolder(new ViewHolder(R.layout.dialog_content_list))
                .setCancelable(true).setGravity(Gravity.CENTER)
                .setBackgroundColorResourceId(R.color.transparent).create();
        View content = dialogMessage.getHolderView();
        final ListView lv = (ListView) content
                .findViewById(R.id.content_lv);
        TextView title = (TextView) content.findViewById(R.id.text_dialog_title);
        title.setText("客户信息");
        ImageView close_iv = (ImageView) content.findViewById(R.id.close_iv);


        ArrayList<RoleBean> list = new ArrayList<>();
        RoleBean roleBean = new RoleBean();
        roleBean.setRole_app_id("客户姓名：");
        roleBean.setRole_app_name(bean.getYfs_sellsq_ownner());
        RoleBean roleBean1 = new RoleBean();
        roleBean1.setRole_app_id("手机号：");
        roleBean1.setRole_app_name(bean.getYfs_sellsq_cellphone());
        RoleBean roleBean2 = new RoleBean();
        roleBean2.setRole_app_id("客户类型：");
        if (bean.getYfs_sellsq_ywtype().equals("0")) {
            roleBean2.setRole_app_name("个人");
        } else {
            roleBean2.setRole_app_name("个人(非本地)");
        }
        RoleBean roleBean3 = new RoleBean();
        roleBean3.setRole_app_id("证件类型：");
        roleBean3.setRole_app_name(bean.getYfs_sellsq_idtype());
        RoleBean roleBean4 = new RoleBean();
        roleBean4.setRole_app_id("证件号码：");
        roleBean4.setRole_app_name(bean.getYfs_sellsq_id());
        RoleBean roleBean5 = new RoleBean();
        roleBean5.setRole_app_id("住址：");
        roleBean5.setRole_app_name(bean.getYfs_sellsq_addr());

        list.add(roleBean);
        list.add(roleBean1);
        list.add(roleBean2);
        list.add(roleBean3);
        list.add(roleBean4);
        list.add(roleBean5);

        lv.setAdapter(new DialogItemAdapter(OrderSalesManagerActivity.this, list));

        close_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMessage.dismiss();
            }
        });

        dialogMessage.show();
    }
}
