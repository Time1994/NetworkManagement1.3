package com.eroadcar.networkmanagement.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Request.Method;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.CarColorAdapter;
import com.eroadcar.networkmanagement.adapter.CarTypeAdapter;
import com.eroadcar.networkmanagement.adapter.ShowCarAdapter;
import com.eroadcar.networkmanagement.bean.AllCarTypeBean;
import com.eroadcar.networkmanagement.bean.CarImageBean;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.view.MyGridView;

public class ShowCarActivity extends BaseActivity implements OnClickListener {
    private Button sure_btn, back_btn, other_btn;
    private TextView title_tv, kucun_tv, kucunno_tv;

    private MyGridView type_gv, color_gv, show_gv;

    private LinearLayout kucun_ll, none_ll, show_ll;

    private RadioGroup as_rg;

    private CarTypeAdapter adapter;
    private CarColorAdapter adapterc;

    private ArrayList<CarImageBean> carImageBeans;

    private String carType = "", carColor = "", type = "0";// 3：参数 ；2，细节； 1，内饰 ；
    // 0，外观

    private CommonBean<AllCarTypeBean> allCommonBean;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_car);

        title_tv = (TextView) findViewById(R.id.title_tv);
        sure_btn = (Button) findViewById(R.id.sure_btn);
        back_btn = (Button) findViewById(R.id.back_btn);
        other_btn = (Button) findViewById(R.id.other_btn);
        kucun_tv = (TextView) findViewById(R.id.kucun_tv);
        kucunno_tv = (TextView) findViewById(R.id.kucunno_tv);
        none_ll = (LinearLayout) findViewById(R.id.none_ll);
        show_ll = (LinearLayout) findViewById(R.id.show_ll);

        type_gv = (MyGridView) findViewById(R.id.typer_gv);
        color_gv = (MyGridView) findViewById(R.id.colorr_gv);
        show_gv = (MyGridView) findViewById(R.id.show_gv);

        kucun_ll = (LinearLayout) findViewById(R.id.kucun_ll);

        as_rg = (RadioGroup) findViewById(R.id.as_rg);

        back_btn.setOnClickListener(this);
        kucun_ll.setOnClickListener(this);
        other_btn.setOnClickListener(this);
        sure_btn.setOnClickListener(this);

        title_tv.setText(R.string.cars);
        back_btn.setText("");
        other_btn.setText("更换车型");

//		Intent intent = getIntent();
//		carColor = intent.getStringExtra("carColor");
//		carType = intent.getStringExtra("carType");
        carColor = "奇瑞白";
        carType = "艾瑞泽5e";

        type_gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                adapter.setSeclection(position);
                adapter.notifyDataSetChanged();

                index = position;

                carType = allCommonBean.getData().getCartype().get(position)
                        .getCt_car_type();

                adapterc = new CarColorAdapter(allCommonBean.getData()
                        .getCartype().get(position).getColor(),
                        ShowCarActivity.this);
                color_gv.setAdapter(adapterc);
                adapterc.setSeclection(0);

                carColor = allCommonBean.getData().getCartype().get(position)
                        .getColor().get(0).getCt_car_color();

                kucun_tv.setText(allCommonBean.getData().getCartype()
                        .get(position).getColor().get(0).getCount()
                        + "辆");
                if (allCommonBean.getData().getCartype().get(position)
                        .getColor().get(0).getCount().equals("0")) {
                    kucunno_tv.setVisibility(View.VISIBLE);
                } else {
                    kucunno_tv.setVisibility(View.GONE);
                }
            }
        });

        color_gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                adapterc.setSeclection(position);
                adapterc.notifyDataSetChanged();

                carColor = allCommonBean.getData().getCartype().get(index)
                        .getColor().get(position).getCt_car_color();

                kucun_tv.setText(allCommonBean.getData().getCartype()
                        .get(index).getColor().get(position).getCount()
                        + "辆");
                if (allCommonBean.getData().getCartype().get(index).getColor()
                        .get(position).getCount().equals("0")) {
                    kucunno_tv.setVisibility(View.VISIBLE);
                } else {
                    kucunno_tv.setVisibility(View.GONE);
                }
            }
        });

        as_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                if (arg1 == R.id.wai_rb) {
                    type = "0";
                } else if (arg1 == R.id.nei_rb) {
                    type = "1";
                } else if (arg1 == R.id.xi_rb) {
                    type = "2";
                } else if (arg1 == R.id.can_rb) {
                    type = "3";
                }

                mHandler.sendEmptyMessage(1);
            }
        });

        getCarImgByTc(carType, carColor);

        // getAllCarType();

        // setCurDate();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (carImageBeans != null && carImageBeans.size() != 0) {

                        ArrayList<CarImageBean> imageBeans = new ArrayList<>();
                        for (int i = 0; i < carImageBeans.size(); i++) {
                            if (carImageBeans.get(i).getCi_class().equals(type)) {
                                imageBeans.add(carImageBeans.get(i));
                            }
                        }

                        ShowCarAdapter adapter = new ShowCarAdapter(
                                ShowCarActivity.this, imageBeans);
                        show_gv.setAdapter(adapter);
                        show_gv.setVisibility(View.VISIBLE);
                        none_ll.setVisibility(View.GONE);
                    } else {
                        show_gv.setVisibility(View.GONE);
                        none_ll.setVisibility(View.VISIBLE);
                    }
                    break;

                case 5:
                    if (allCommonBean.getState().equals("success")) {
                        show_ll.setVisibility(View.VISIBLE);
                        show_gv.setVisibility(View.GONE);
                        none_ll.setVisibility(View.GONE);

                        adapter = new CarTypeAdapter(allCommonBean.getData()
                                .getCartype(), ShowCarActivity.this);
                        type_gv.setAdapter(adapter);
                        adapter.setSeclection(0);
                        carType = allCommonBean.getData().getCartype().get(0)
                                .getCt_car_type();

                        adapterc = new CarColorAdapter(allCommonBean.getData()
                                .getCartype().get(0).getColor(),
                                ShowCarActivity.this);
                        color_gv.setAdapter(adapterc);
                        adapterc.setSeclection(0);
                        carColor = allCommonBean.getData().getCartype().get(0)
                                .getColor().get(0).getCt_car_color();

                        kucun_tv.setText(allCommonBean.getData().getCartype()
                                .get(0).getColor().get(0).getCount()
                                + "辆");
                        if (allCommonBean.getData().getCartype().get(0).getColor()
                                .get(0).getCount().equals("0")) {
                            kucunno_tv.setVisibility(View.VISIBLE);
                        } else {
                            kucunno_tv.setVisibility(View.GONE);
                        }

                        // loading_ll.setVisibility(View.GONE);
                    }
                    break;
                case -1:

                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;

            case R.id.kucun_ll:
                // if (!kucun_tv.getText().toString().equals("0辆")) {
                // Intent intent = new Intent(ShowCarActivity.this,
                // CarActivity.class);
                // intent.putExtra("LIST", kucunBean.getData());
                // startActivity(intent);
                // }
                break;
            case R.id.sure_btn:
                show_ll.setVisibility(View.GONE);

                getCarImgByTc(carType, carColor);
                break;
            case R.id.other_btn:
                getAllCarType();
                break;
            default:
                break;
        }
    }

    private void getAllCarType() {
        loadingDialog.setMessage("正在加载数据...");
        loadingDialog.dialogShow();
        String url = Constant.GETALLCARTYPESELL;
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

        GsonRequest<CommonBean<AllCarTypeBean>> requtst = new GsonRequest<CommonBean<AllCarTypeBean>>(
                Method.POST, url, listener_getAllCarType);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<AllCarTypeBean>> listener_getAllCarType = new RequesListener<CommonBean<AllCarTypeBean>>() {
        @Override
        public void onResponse(CommonBean<AllCarTypeBean> arg0) {
            // TODO Auto-generated method stub
            allCommonBean = arg0;
            mHandler.sendEmptyMessage(5);
            loadingDialog.dismiss();
        }

        @Override
        public void onErrorResponse(VolleyError arg0) {
            // TODO Auto-generated method stub
            mHandler.sendEmptyMessage(-1);
            loadingDialog.dismiss();
        }

    };

    private void getCarImgByTc(String ci_car_type, String ci_car_color) {
        loadingDialog.setMessage("正在加载数据...");
        loadingDialog.dialogShow();
        String url = Constant.GETCARIMGBYTC;
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
        map.put("ci_car_type", ci_car_type);
        map.put("ci_car_color", ci_car_color);

        GsonRequest<ArrayList<CarImageBean>> requtst = new GsonRequest<ArrayList<CarImageBean>>(
                Method.POST, url, listener_getCarType);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
        mRequestQueue.start();
    }

    private RequesListener<ArrayList<CarImageBean>> listener_getCarType = new RequesListener<ArrayList<CarImageBean>>() {
        @Override
        public void onResponse(ArrayList<CarImageBean> arg0) {
            // TODO Auto-generated method stub
            carImageBeans = arg0;
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
