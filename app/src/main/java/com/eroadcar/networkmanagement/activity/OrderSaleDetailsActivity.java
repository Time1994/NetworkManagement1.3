package com.eroadcar.networkmanagement.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.OrderDetailsBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;

import java.util.HashMap;

public class OrderSaleDetailsActivity extends BaseActivity implements OnClickListener {
    private Button other_btn, back_btn;
    private TextView title_tv;

    private TextView orderid_tv, state_tv, name_tv, mobile_tv, idtype_tv, id_tv, address_tv;
    private TextView car_tv, color_tv, price_tv, cdzaddress_tv, baoxian_tv;
    private ImageView sfz_iv, jzz_iv;

    private RelativeLayout select_rl;

    private CommonBean<OrderDetailsBean> commonBean;

    private String yfs_sellsq_code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        title_tv = (TextView) findViewById(R.id.title_tv);
        other_btn = (Button) findViewById(R.id.other_btn);
        back_btn = (Button) findViewById(R.id.back_btn);

        orderid_tv = (TextView) findViewById(R.id.orderid_tv);
        state_tv = (TextView) findViewById(R.id.state_tv);
        name_tv = (TextView) findViewById(R.id.name_tv);
        mobile_tv = (TextView) findViewById(R.id.mobile_tv);
        idtype_tv = (TextView) findViewById(R.id.idtype_tv);
        id_tv = (TextView) findViewById(R.id.id_tv);
        address_tv = (TextView) findViewById(R.id.address_tv);

        car_tv = (TextView) findViewById(R.id.car_tv);
        color_tv = (TextView) findViewById(R.id.color_tv);
        price_tv = (TextView) findViewById(R.id.price_tv);
        cdzaddress_tv = (TextView) findViewById(R.id.cdzaddress_tv);
        baoxian_tv = (TextView) findViewById(R.id.baoxian_tv);

        sfz_iv = (ImageView) findViewById(R.id.sfz_iv);
        jzz_iv = (ImageView) findViewById(R.id.jzz_iv);

        select_rl = (RelativeLayout) findViewById(R.id.select_rl);

        back_btn.setOnClickListener(this);
        other_btn.setOnClickListener(this);
        select_rl.setOnClickListener(this);

        title_tv.setText("订单详情");
        back_btn.setText("");

        yfs_sellsq_code = getIntent().getStringExtra("yfs_sellsq_code");
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSellCustomer(yfs_sellsq_code);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.select_rl:
                Intent intent = new Intent(OrderSaleDetailsActivity.this, ChooseCarActivity.class);
                intent.putExtra("yfs_id", commonBean.getData().getCustormer().getYfs_id());
                intent.putExtra("yfs_sellsq_code", yfs_sellsq_code);
                startActivity(intent);
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
                        orderid_tv.setText("订单号：" + commonBean.getData().getCustormer().getYfs_sellsq_code());
                        state_tv.setText(commonBean.getData().getCustormer().getYfs_sellsq_state());
                        name_tv.setText("姓名：" + commonBean.getData().getCustormer().getYfs_sellsq_ownner());
                        mobile_tv.setText("手机号：" + commonBean.getData().getCustormer().getYfs_sellsq_cellphone());
                        idtype_tv.setText("证件类型：" + commonBean.getData().getCustormer().getYfs_sellsq_idtype());
                        id_tv.setText("身份证号：" + commonBean.getData().getCustormer().getYfs_sellsq_id());
                        address_tv.setText("住址：" + commonBean.getData().getCustormer().getYfs_sellsq_addr());

                        setImageh(Constant.HTTP + commonBean.getData().getCustormer().getYfs_sellsq_idpic(), sfz_iv, R.mipmap.icon_camrea_sfz);
                        if (commonBean.getData().getCustormer().getYfs_sellsq_ywtype().equals("3")) {
                            jzz_iv.setVisibility(View.VISIBLE);
                            setImageh(Constant.HTTP + commonBean.getData().getCustormer().getYfs_sellsq_jzpic(), jzz_iv, R.mipmap.icon_camrea_sfz);
                        }

                        if (commonBean.getData().getCars() != null && commonBean.getData().getCars().size() != 0) {
                            car_tv.setText("车型：" + commonBean.getData().getCars().get(0).getYfs_car_cartype());
                            color_tv.setText("颜色：" + commonBean.getData().getCars().get(0).getYfs_car_carcolor());
                            price_tv.setText("价格：" + commonBean.getData().getCars().get(0).getYfs_car_carprice() + "元");
                            cdzaddress_tv.setText("充电桩地址：" + commonBean.getData().getCars().get(0).getYfs_car_chargpostaddr());
                            baoxian_tv.setText("保险：" + commonBean.getData().getCars().get(0).getYfs_car_carinsurance());
                        }
                    }
                    ToastUtils.showShort(commonBean.getMsg());
                    break;

                default:
                    break;
            }
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
                Request.Method.POST, url, listener_reqZxInfo);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<OrderDetailsBean>> listener_reqZxInfo = new RequesListener<CommonBean<OrderDetailsBean>>() {
        @Override
        public void onResponse(CommonBean<OrderDetailsBean> arg0) {
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
