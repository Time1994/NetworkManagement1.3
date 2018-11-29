package com.eroadcar.networkmanagement.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.ImageAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.HomeBean;
import com.eroadcar.networkmanagement.bean.OrderDetailsBean;
import com.eroadcar.networkmanagement.bean.OrderSalesBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.view.ClearEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderSaleEmployDetailsActivity extends BaseActivity implements OnClickListener {
    private Button other_btn, back_btn, sure_btn;
    private TextView title_tv;

    private TextView orderid_tv, state_tv, name_tv, mobile_tv, idtype_tv, id_tv, address_tv;
    private TextView car_tv, color_tv, price_tv, cdzaddress_tv, baoxian_tv;

    private TextView iprice_tv, fprice_tv, fu_tv, info_tv;

    private GridView image_gv;

    private LinearLayout car_ll, pay_ll;

    private TextView payment_tv, pay_tv;
    private TextView vin_tv, pai_tv;

    private CommonBean commonBean;
    private CommonBean<OrderDetailsBean> detailsBean;

    private OrderSalesBean bean;

    private String yfs_sellsq_code = "", zhengxState = "征信通过";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_sale_details);

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

        info_tv = (TextView) findViewById(R.id.info_tv);

        car_tv = (TextView) findViewById(R.id.car_tv);
        color_tv = (TextView) findViewById(R.id.color_tv);
        price_tv = (TextView) findViewById(R.id.price_tv);
        cdzaddress_tv = (TextView) findViewById(R.id.cdzaddress_tv);
        baoxian_tv = (TextView) findViewById(R.id.baoxian_tv);

        image_gv = (GridView) findViewById(R.id.image_gv);

        car_ll = (LinearLayout) findViewById(R.id.car_ll);
        pay_ll = (LinearLayout) findViewById(R.id.pay_ll);

        payment_tv = (TextView) findViewById(R.id.payment_tv);
        pay_tv = (TextView) findViewById(R.id.pay_tv);

        vin_tv = (TextView) findViewById(R.id.vin_tv);
        pai_tv = (TextView) findViewById(R.id.pai_tv);

        iprice_tv = (TextView) findViewById(R.id.iprice_tv);
        fprice_tv = (TextView) findViewById(R.id.fprice_tv);
        fu_tv = (TextView) findViewById(R.id.fu_tv);

        back_btn.setOnClickListener(this);
        other_btn.setOnClickListener(this);
        sure_btn.setOnClickListener(this);

        title_tv.setText("订单详情");
        back_btn.setText("");

        bean = (OrderSalesBean) getIntent().getSerializableExtra("BEAN");
//        yfs_sellsq_code = getIntent().getStringExtra("yfs_sellsq_code");
        yfs_sellsq_code = bean.getYfs_sellsq_code();

        getSellCustomer(yfs_sellsq_code);

        image_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDialogImage(OrderSaleEmployDetailsActivity.this, list.get(i).getType());
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

    private void zxSellVerify(String yfs_id, String yfs_sellsq_mgid, String yfs_sellsq_cellphone,
                              String yfs_sellsq_state, String yfs_sellsq_remark, String yfs_car_carvin,
                              String yfs_car_carlicense, String yfs_sellsq_paytype, String yfs_sellsq_dingjingprice) {
        loadingDialog.setMessage("正在提交数据...");
        loadingDialog.dialogShow();
        String url = Constant.ZXSELLVERIFY;

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
        map.put("yfs_sellsq_mgid", yfs_sellsq_mgid);
        map.put("yfs_sellsq_cellphone", yfs_sellsq_cellphone);
        map.put("yfs_sellsq_state", yfs_sellsq_state);
        map.put("yfs_sellsq_remark", yfs_sellsq_remark);
        map.put("yfs_car_carvin", yfs_car_carvin);
        map.put("yfs_car_carlicense", yfs_car_carlicense);
        map.put("yfs_sellsq_paytype", yfs_sellsq_paytype);
        map.put("yfs_sellsq_dingjingprice", yfs_sellsq_dingjingprice);

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

    private void getSellCustomer(String yfs_sellsq_code) {
        loadingDialog.setMessage("正在获取数据...");
        loadingDialog.dialogShow();
        String url = Constant.GETSELLCUSTOMER;

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

        map.put("yfs_sellsq_code", yfs_sellsq_code);

        GsonRequest<CommonBean<OrderDetailsBean>> requtst = new GsonRequest<CommonBean<OrderDetailsBean>>(
                Request.Method.POST, url, listener_getSellCustomer);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<OrderDetailsBean>> listener_getSellCustomer = new RequesListener<CommonBean<OrderDetailsBean>>() {
        @Override
        public void onResponse(CommonBean<OrderDetailsBean> arg0) {
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
        orderid_tv.setText("订单号：" + bean.getYfs_sellsq_code());
        state_tv.setText(bean.getYfs_sellsq_state());//"订单状态：" +
        name_tv.setText("姓名：" + bean.getYfs_sellsq_ownner());
        mobile_tv.setText("手机号：" + bean.getYfs_sellsq_cellphone());
        idtype_tv.setText("证件类型：" + bean.getYfs_sellsq_idtype());
        id_tv.setText("身份证号：" + bean.getYfs_sellsq_id());
        address_tv.setText("地址：" + bean.getYfs_sellsq_addr());

        if (bean.getYfs_sellsq_ywtype().equals("1") ||
                bean.getYfs_sellsq_ywtype().equals("单位")) {
            info_tv.setText("单位信息");

            name_tv.setText("单位名称：" + bean.getYfs_sellsq_ownner());
            mobile_tv.setText("联系方式：" + bean.getYfs_sellsq_cellphone() + "（" + bean.getYfs_sellsq_contact() + "）");
            id_tv.setText("统一信用代码：" + bean.getYfs_sellsq_id());
        }

        list = new ArrayList<>();
        HomeBean homeBean = new HomeBean();
//        if (bean.getYfs_sellsq_ywtype().equals("1")) {
//            homeBean.setContent("");
//        } else {
//            homeBean.setContent("");
//        }
        homeBean.setContent(bean.getYfs_sellsq_idtype());
        homeBean.setType(Constant.HTTP + bean.getYfs_sellsq_idpic());
        list.add(homeBean);

        if (bean.getYfs_sellsq_ywtype().equals("3")) {
            HomeBean homeBean1 = new HomeBean();
            homeBean1.setContent("居住证");
            homeBean1.setType(Constant.HTTP + bean.getYfs_sellsq_jzpic());
            list.add(homeBean1);
        }

        image_gv.setAdapter(new ImageAdapter(OrderSaleEmployDetailsActivity.this, list));

        if (detailsBean.getData().getCars() != null && detailsBean.getData().getCars().size() != 0) {
            car_ll.setVisibility(View.VISIBLE);

            car_tv.setText("车型：" + detailsBean.getData().getCars().get(0).getYfs_car_cartype());
            color_tv.setText("颜色：" + detailsBean.getData().getCars().get(0).getYfs_car_carcolor());
            price_tv.setText("价格：" + detailsBean.getData().getCars().get(0).getYfs_car_carprice() + "元");
            cdzaddress_tv.setText("充电桩地址：" + detailsBean.getData().getCars().get(0).getYfs_car_chargpostaddr());
            baoxian_tv.setText("保险：" + detailsBean.getData().getCars().get(0).getYfs_car_carinsurance());

            iprice_tv.setText("保险费用：" + detailsBean.getData().getCars().get(0).getYfs_insurance_price() + "元");
            fprice_tv.setText("服务费用：" + detailsBean.getData().getCars().get(0).getYfs_additional_price() + "元");
            fu_tv.setText("其他服务：" + detailsBean.getData().getCars().get(0).getYfs_additional_items());

            if (detailsBean.getData().getCars().get(0).getYfs_car_carvin() != null &&
                    !detailsBean.getData().getCars().get(0).getYfs_car_carvin().equals("")) {
                vin_tv.setText("车架号：" + detailsBean.getData().getCars().get(0).getYfs_car_carvin());
            }
            if (detailsBean.getData().getCars().get(0).getYfs_car_carlicense() != null &&
                    !detailsBean.getData().getCars().get(0).getYfs_car_carlicense().equals("")) {
                pai_tv.setText("车牌号：" + detailsBean.getData().getCars().get(0).getYfs_car_carlicense());
            }
        }

        if (bean.getYfs_sellsq_paytype() != null && !bean.getYfs_sellsq_paytype().equals("")) {
            pay_ll.setVisibility(View.VISIBLE);

            payment_tv.setText("支付方式：" + bean.getYfs_sellsq_paytype());
            if (bean.getYfs_sellsq_pricetype() != null && bean.getYfs_sellsq_pricetype().equals("定金")) {
                pay_tv.setText("支付金额：" + bean.getYfs_sellsq_dingjingprice() + "(" + bean.getYfs_sellsq_pricetype() + ")");
            } else {
                pay_tv.setText(bean.getYfs_sellsq_pricetype());
            }
        }
    }

}
