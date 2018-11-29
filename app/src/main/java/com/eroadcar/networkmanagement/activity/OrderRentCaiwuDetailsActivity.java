package com.eroadcar.networkmanagement.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.ImageAdapter;
import com.eroadcar.networkmanagement.adapter.RentCarAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.HomeBean;
import com.eroadcar.networkmanagement.bean.OrderRentBean;
import com.eroadcar.networkmanagement.bean.OrderRentDetailsBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.view.ClearEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderRentCaiwuDetailsActivity extends BaseActivity implements OnClickListener {
    private Button other_btn, back_btn, sure_btn;
    private TextView title_tv;

    private TextView orderid_tv, state_tv, name_tv, mobile_tv, idtype_tv, id_tv, address_tv;

    private LinearLayout car_ll, pay_ll, paye_ll;

    private GridView image_gv;

    private TextView payment_tv, pay_tv, kaip_tv;
    private TextView dan_tv, personal_tv;

    private ListView car_lv;

    private Spinner spinner;

    private ClearEditText amount_cet, kpamount_cet;

    private CommonBean commonBean;
    private CommonBean<OrderRentDetailsBean> detailsBean;

    private OrderRentBean bean;

    private ArrayList<String> payMents;

    private String yfs_leasesq_code = "", payament = "现金";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_rent_caiwu_details);

        title_tv = (TextView) findViewById(R.id.title_tv);
        other_btn = (Button) findViewById(R.id.other_btn);
        back_btn = (Button) findViewById(R.id.back_btn);

        sure_btn = (Button) findViewById(R.id.sure_btn);

        orderid_tv = (TextView) findViewById(R.id.orderid_tv);
        state_tv = (TextView) findViewById(R.id.state_tv);
        name_tv = (TextView) findViewById(R.id.name_tv);
        mobile_tv = (TextView) findViewById(R.id.mobile_tv);
        idtype_tv = (TextView) findViewById(R.id.idtype_tv);
        id_tv = (TextView) findViewById(R.id.id_tv);
        address_tv = (TextView) findViewById(R.id.address_tv);

        pay_ll = (LinearLayout) findViewById(R.id.pay_ll);
        paye_ll = (LinearLayout) findViewById(R.id.paye_ll);


        image_gv = (GridView) findViewById(R.id.image_gv);

        car_ll = (LinearLayout) findViewById(R.id.car_ll);

        payment_tv = (TextView) findViewById(R.id.payment_tv);
        pay_tv = (TextView) findViewById(R.id.pay_tv);

        dan_tv = (TextView) findViewById(R.id.dan_tv);
        personal_tv = (TextView) findViewById(R.id.personal_tv);

        car_lv = (ListView) findViewById(R.id.car_lv);

        spinner = (Spinner) findViewById(R.id.spinner);

        amount_cet = (ClearEditText) findViewById(R.id.amount_cet);
        kpamount_cet = (ClearEditText) findViewById(R.id.kpamount_cet);

        kaip_tv = (TextView) findViewById(R.id.kaip_tv);

        back_btn.setOnClickListener(this);
        other_btn.setOnClickListener(this);
        sure_btn.setOnClickListener(this);

        title_tv.setText("订单详情");
        back_btn.setText("");

        setSpinner();

        bean = (OrderRentBean) getIntent().getSerializableExtra("BEAN");
        yfs_leasesq_code = bean.getYfs_leasesq_code();

        getLeaseCustomer(yfs_leasesq_code);

        image_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDialogImage(OrderRentCaiwuDetailsActivity.this, list.get(i).getType());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.sure_btn:

                if (amount_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请输入收款金额");
                    return;
                }
                if (kpamount_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请输入开票金额");
                    return;
                }

                showDialogMessage(OrderRentCaiwuDetailsActivity.this, "收取租赁费", "确认信息无误吗？", "确认", new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        leaseVerify(bean.getYfs_id(), bean.getYfs_leasesq_cellphone()
                                , "已完成", "",
                                amount_cet.getText().toString(), payament, kpamount_cet.getText().toString());
                    }
                });


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
                        onBackPressed();
                    }
                    ToastUtils.showShort(commonBean.getMsg());
                    break;
                case 2:
                    if (detailsBean.getState().equals("success")) {

                        bean = detailsBean.getData().getCustormer();

                        setInterface();
                    }
                    ToastUtils.showShort(detailsBean.getMsg());
                    break;

                default:
                    break;
            }
        }
    };

    private void leaseVerify(String yfs_id, String yfs_leasesq_cellphone,
                             String yfs_leasesq_state, String yfs_leasesq_remark, String yfs_leasesq_dingjingprice,
                             String yfs_leasesq_paytype, String yfs_leasesq_kp_price) {
        loadingDialog.setMessage("正在提交数据...");
        loadingDialog.dialogShow();
        String url = Constant.LEASEVERIFY;

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

        map.put("yfs_id", yfs_id);
        map.put("yfs_leasesq_cellphone", yfs_leasesq_cellphone);
        map.put("yfs_leasesq_state", yfs_leasesq_state);
        map.put("yfs_leasesq_remark", yfs_leasesq_remark);
        map.put("yfs_leasesq_dingjingprice", "");
        map.put("yfs_leasesq_paytype", yfs_leasesq_paytype);
        map.put("yfs_leasesq_total_carprice", yfs_leasesq_dingjingprice);
        map.put("yfs_leasesq_kp_price", yfs_leasesq_kp_price);

        GsonRequest<CommonBean> requtst = new GsonRequest<CommonBean>(
                Request.Method.POST, url, listener_reqZxInfo);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean> listener_reqZxInfo = new RequesListener<CommonBean>() {
        @Override
        public void onResponse(CommonBean arg0) {
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

    private void getLeaseCustomer(String yfs_leasesq_code) {
        loadingDialog.setMessage("正在获取数据...");
        loadingDialog.dialogShow();
        String url = Constant.GETLEASECUSTOMER;

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

        map.put("yfs_leasesq_code", yfs_leasesq_code);

        GsonRequest<CommonBean<OrderRentDetailsBean>> requtst = new GsonRequest<CommonBean<OrderRentDetailsBean>>(
                Request.Method.POST, url, listener_getSellCustomer);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<OrderRentDetailsBean>> listener_getSellCustomer = new RequesListener<CommonBean<OrderRentDetailsBean>>() {
        @Override
        public void onResponse(CommonBean<OrderRentDetailsBean> arg0) {
            // TODO Auto-generated method stub
            detailsBean = arg0;
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

    private ArrayList<HomeBean> list;

    private void setInterface() {
        orderid_tv.setText("订单号：" + bean.getYfs_leasesq_code());
        state_tv.setText(bean.getYfs_leasesq_state());//"订单状态：" +
        name_tv.setText("姓名：" + bean.getYfs_leasesq_ownner());
        mobile_tv.setText("手机号：" + bean.getYfs_leasesq_cellphone());
        idtype_tv.setText("证件类型：" + bean.getYfs_leasesq_idtype());
        id_tv.setText("身份证号：" + bean.getYfs_leasesq_id());
        address_tv.setText("地址：" + bean.getYfs_leasesq_addr());

        list = new ArrayList<>();

        if (bean.getYfs_leasesq_ywtype().equals("个人")) {
            personal_tv.setText("个人信息");

            HomeBean homeBean = new HomeBean();
            homeBean.setContent("身份证正面");
            homeBean.setType(Constant.HTTP + bean.getYfs_leasesq_idpic());
            list.add(homeBean);

            HomeBean homeBean1 = new HomeBean();
            homeBean1.setContent("身份证反面");
            homeBean1.setType(Constant.HTTP + bean.getYfs_leasesq_driverpicb());
            list.add(homeBean1);

            HomeBean homeBean2 = new HomeBean();
            homeBean2.setContent("驾驶证");
            homeBean2.setType(Constant.HTTP + bean.getYfs_leasesq_driverpica());
            list.add(homeBean2);

        } else if (bean.getYfs_leasesq_ywtype().equals("单位")) {
            personal_tv.setText("企业信息");
            name_tv.setText("联系人：" + bean.getYfs_leasesq_fullname());
            id_tv.setText("税号：" + bean.getYfs_leasesq_id());
            dan_tv.setVisibility(View.VISIBLE);
            dan_tv.setText("企业全称：" + bean.getYfs_leasesq_ownner());

            list.clear();

            HomeBean homeBean1 = new HomeBean();
            homeBean1.setContent("营业执照");
            homeBean1.setType(Constant.HTTP + bean.getYfs_leasesq_idpic());
            list.add(homeBean1);
        } else {
            personal_tv.setText("机关信息");
            name_tv.setText("联系人：" + bean.getYfs_leasesq_fullname());
            id_tv.setVisibility(View.GONE);
            dan_tv.setVisibility(View.VISIBLE);
            dan_tv.setText("机关全称：" + bean.getYfs_leasesq_ownner());
        }

        image_gv.setAdapter(new ImageAdapter(OrderRentCaiwuDetailsActivity.this, list));

        if (detailsBean.getData().getCars() != null && detailsBean.getData().getCars().size() != 0) {
            car_ll.setVisibility(View.VISIBLE);

            car_lv.setAdapter(new RentCarAdapter(detailsBean.getData().getCars(), OrderRentCaiwuDetailsActivity.this));
            setListViewHeightBasedOnChildren(car_lv);
        }

        if (bean.getYfs_leasesq_paytype() != null && !bean.getYfs_leasesq_paytype().equals("")) {
            pay_ll.setVisibility(View.VISIBLE);

            payment_tv.setText("支付方式：" + bean.getYfs_leasesq_paytype());
            if (bean.getYfs_leasesq_total_carprice() != null && !bean.getYfs_leasesq_total_carprice().equals("")) {
                pay_tv.setText("支付金额：" + bean.getYfs_leasesq_total_carprice());
                kaip_tv.setText("开票金额：" + bean.getYfs_leasesq_kp_price());
            }
        }
//        else {
        if (!bean.getYfs_leasesq_state().equals("申请中") && !bean.getYfs_leasesq_state().equals("已完成")) {
            paye_ll.setVisibility(View.VISIBLE);

            sure_btn.setVisibility(View.VISIBLE);
            sure_btn.setText("收取租赁费");
//            }
        }
    }

    private void setSpinner() {
        payMents = new ArrayList<String>();
        payMents.add(0, "现金");
        payMents.add(1, "网银");
        payMents.add(2, "支付宝");
        payMents.add(3, "微信");
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(
                OrderRentCaiwuDetailsActivity.this, R.layout.view_textview, payMents);
        // arr_adapter
        // .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arr_adapter);
        spinner.setSelection(0, true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                payament = payMents.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

}
