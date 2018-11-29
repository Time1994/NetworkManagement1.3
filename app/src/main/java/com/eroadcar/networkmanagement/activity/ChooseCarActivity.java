package com.eroadcar.networkmanagement.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.android.volley.Request.Method;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.baidu.aip.asrwakeup3.core.recog.MyRecognizer;
import com.baidu.aip.asrwakeup3.core.recog.listener.ChainRecogListener;
import com.baidu.aip.asrwakeup3.core.recog.listener.IRecogListener;
import com.baidu.aip.asrwakeup3.core.recog.listener.MessageStatusRecogListener;
import com.baidu.aip.asrwakeup3.core.util.MyLogger;
import com.baidu.aip.asrwakeup3.uiasr.params.CommonRecogParams;
import com.baidu.aip.asrwakeup3.uiasr.params.OnlineRecogParams;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DigitalDialogInput;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.CarColorAdapter;
import com.eroadcar.networkmanagement.adapter.CarTypeAdapter;
import com.eroadcar.networkmanagement.bean.AllCarTypeBean;
import com.eroadcar.networkmanagement.bean.CarBean;
import com.eroadcar.networkmanagement.bean.CarColorBean;
import com.eroadcar.networkmanagement.bean.CarTypeBean;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.view.ClearEditText;
import com.eroadcar.networkmanagement.view.MyGridView;

public class ChooseCarActivity extends BaseActivity implements OnClickListener {
    private Button sure_btn, back_btn, show_btn;
    private TextView title_tv, kucun_tv, kucunno_tv;

    private MyGridView type_gv, color_gv;
    private TextView select_getcar_tv, select_insurance_tv, car_price_tv;

    private RadioGroup price_rg, payment_rg;

    private LinearLayout loading_ll, kucun_ll;

    private ClearEditText address_cet, amount_cet, insurance_cet, remark_cet, remarkfei_cet;

    private ImageView yuyin_iv;

    private CarTypeAdapter adapter;
    private CarColorAdapter adapterc;

    private CommonBean<ArrayList<CarTypeBean>> carTypeBean;
    private CommonBean<ArrayList<CarColorBean>> carColorBean;
    private CommonBean<ArrayList<CarBean>> kucunBean;
    private CommonBean commonBean;
    private CommonBean<AllCarTypeBean> allCommonBean;

    private String payMent = "POS", price = "0", carType = "",
            yck_car_subchexing = "", carYwType = "", carColor = "",
            carPrice = "", yfs_id, yfs_sellsq_code, insurance = "";

    private int index = 0;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA};
    private static int REQUEST_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_car);

        title_tv = (TextView) findViewById(R.id.title_tv);
        sure_btn = (Button) findViewById(R.id.sure_btn);
        back_btn = (Button) findViewById(R.id.back_btn);
        show_btn = (Button) findViewById(R.id.show_btn);

        type_gv = (MyGridView) findViewById(R.id.type_gv);
        color_gv = (MyGridView) findViewById(R.id.color_gv);

        kucun_tv = (TextView) findViewById(R.id.kucun_tv);
        kucunno_tv = (TextView) findViewById(R.id.kucunno_tv);

        select_getcar_tv = (TextView) findViewById(R.id.select_getcar_tv);
        select_insurance_tv = (TextView) findViewById(R.id.select_insurance_tv);
        car_price_tv = (TextView) findViewById(R.id.car_price_tv);

        price_rg = (RadioGroup) findViewById(R.id.price_rg);
        payment_rg = (RadioGroup) findViewById(R.id.payment_rg);

        loading_ll = (LinearLayout) findViewById(R.id.loading_ll);
        kucun_ll = (LinearLayout) findViewById(R.id.kucun_ll);

        address_cet = (ClearEditText) findViewById(R.id.address_cet);
        amount_cet = (ClearEditText) findViewById(R.id.amount_cet);

        insurance_cet = (ClearEditText) findViewById(R.id.insurance_cet);
        remark_cet = (ClearEditText) findViewById(R.id.remark_cet);
        remarkfei_cet = (ClearEditText) findViewById(R.id.remarfei_cet);

        yuyin_iv = (ImageView) findViewById(R.id.yuyin_iv);

        back_btn.setOnClickListener(this);
        sure_btn.setOnClickListener(this);
        show_btn.setOnClickListener(this);
        yuyin_iv.setOnClickListener(this);

        title_tv.setText("销售车型");
        back_btn.setText("");

        Intent intent = getIntent();
        yfs_id = intent.getStringExtra("yfs_id");
        yfs_sellsq_code = intent.getStringExtra("yfs_sellsq_code");

        type_gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                adapter.setSeclection(position);
                adapter.notifyDataSetChanged();
                // carPrice = carTypeBean.getData().get(position)
                // .getCt_car_price();
                // car_price_tv.setText(carTypeBean.getData().get(position)
                // .getCt_car_price()
                // + "万元");
                // carType =
                // carTypeBean.getData().get(position).getCt_car_type();
                // yck_car_subchexing = carTypeBean.getData().get(position)
                // .getCt_car_xinghao();
                // carYwType = carTypeBean.getData().get(position)
                // .getCt_car_ywtype();
                //
                // getCarColor(carType, yck_car_subchexing);
                // getCarkucunNumByCarType(carType, carYwType);

                index = position;

                carType = allCommonBean.getData().getCartype().get(position)
                        .getCt_car_type();
                yck_car_subchexing = allCommonBean.getData().getCartype()
                        .get(position).getCt_car_type();
                carYwType = allCommonBean.getData().getCartype().get(position)
                        .getCt_car_ywtype();

                adapterc = new CarColorAdapter(allCommonBean.getData()
                        .getCartype().get(position).getColor(),
                        ChooseCarActivity.this);
                color_gv.setAdapter(adapterc);
                adapterc.setSeclection(0);

                carColor = allCommonBean.getData().getCartype().get(position)
                        .getColor().get(0).getCt_car_color();

                carPrice = allCommonBean.getData().getCartype().get(position)
                        .getColor().get(0).getCt_car_price();
                car_price_tv.setText(allCommonBean.getData().getCartype()
                        .get(position).getColor().get(0).getCt_car_price()
                        );
                amount_cet.setText((allCommonBean.getData().getCartype()
                        .get(position).getColor().get(0).getCt_car_price()));

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
                // carColor = carColorBean.getData().get(position)
                // .getCt_car_color();
                carColor = allCommonBean.getData().getCartype().get(index)
                        .getColor().get(position).getCt_car_color();

                carPrice = allCommonBean.getData().getCartype().get(index)
                        .getColor().get(position).getCt_car_price();
                car_price_tv.setText(allCommonBean.getData().getCartype()
                        .get(index).getColor().get(position).getCt_car_price()
                        );
                amount_cet.setText((allCommonBean.getData().getCartype()
                        .get(index).getColor().get(position).getCt_car_price()) );

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

        price_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == R.id.dingjin_rb) {
                    price = "0";// 定金
                    amount_cet.setVisibility(View.VISIBLE);
                    car_price_tv.setVisibility(View.GONE);
                } else {
                    price = "1";// 全款
                    amount_cet.setVisibility(View.GONE);
                    car_price_tv.setVisibility(View.VISIBLE);
                }
            }
        });
        payment_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == R.id.pos_rb) {
                    payMent = "POS";
                } else if (checkedId == R.id.ebank_rb) {
                    payMent = "网银";
                } else {
                    payMent = "现金";
                }
            }
        });

        select_getcar_tv.setOnClickListener(this);
        select_insurance_tv.setOnClickListener(this);
        kucun_ll.setOnClickListener(this);

        // getCarType();

        getAllCarType();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }

        try {
            // DEMO集成步骤 1.1 新建一个回调类，识别引擎会回调这个类告知重要状态和识别结果
            IRecogListener listener = new MessageStatusRecogListener(null);
            // DEMO集成步骤 1.2 初始化：new一个IRecogListener示例 & new 一个 MyRecognizer 示例
            myRecognizer = new MyRecognizer(this, listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (carTypeBean.getState().equals("success")) {
                        if (carTypeBean.getData() != null
                                && carTypeBean.getData().size() != 0) {
                            adapter = new CarTypeAdapter(carTypeBean.getData(),
                                    ChooseCarActivity.this);
                            type_gv.setAdapter(adapter);
                            adapter.setSeclection(0);
                            carType = carTypeBean.getData().get(0).getCt_car_type();
                            yck_car_subchexing = carTypeBean.getData().get(0)
                                    .getCt_car_xinghao();
                            carYwType = carTypeBean.getData().get(0)
                                    .getCt_car_ywtype();
                            carPrice = carTypeBean.getData().get(0)
                                    .getCt_car_price();
                            car_price_tv.setText(carTypeBean.getData().get(0)
                                    .getCt_car_price()
                                    );
                            amount_cet.setText((allCommonBean.getData().getCartype()
                                    .get(0).getColor().get(0).getCt_car_price()) + "");

                            loading_ll.setVisibility(View.GONE);

                            getCarColor(carType, yck_car_subchexing);

                            getCarkucunNumByCarType(carType, carYwType);
                        }
                    }
                    break;
                case 2:
                    if (carColorBean.getState().equals("success")) {
                        if (carColorBean.getData() != null
                                && carColorBean.getData().size() != 0) {
                            adapterc = new CarColorAdapter(carColorBean.getData(),
                                    ChooseCarActivity.this);
                            color_gv.setAdapter(adapterc);
                            adapterc.setSeclection(0);
                            carColor = carColorBean.getData().get(0)
                                    .getCt_car_color();
                        }
                    }

                    break;
                case 3:
                    if (commonBean.getState().equals("success")) {
                        onBackPressed();
                    }
                    ToastUtils.showShort(commonBean.getMsg());
                    break;
                case 4:
                    if (kucunBean.getState().equals("success")) {
                        kucun_tv.setText(kucunBean.getData().size() + "辆");
                        if (kucunBean.getData().size() == 0) {
                            kucunno_tv.setVisibility(View.VISIBLE);
                        } else {
                            kucunno_tv.setVisibility(View.GONE);
                        }
                    }
                    break;
                case 5:
                    if (allCommonBean.getState().equals("success")) {
                        adapter = new CarTypeAdapter(allCommonBean.getData()
                                .getCartype(), ChooseCarActivity.this);
                        type_gv.setAdapter(adapter);
                        adapter.setSeclection(0);
                        carType = allCommonBean.getData().getCartype().get(0)
                                .getCt_car_type();
                        yck_car_subchexing = allCommonBean.getData().getCartype()
                                .get(0).getCt_car_type();
                        carYwType = allCommonBean.getData().getCartype().get(0)
                                .getCt_car_ywtype();

                        adapterc = new CarColorAdapter(allCommonBean.getData()
                                .getCartype().get(0).getColor(),
                                ChooseCarActivity.this);
                        color_gv.setAdapter(adapterc);
                        adapterc.setSeclection(0);
                        carColor = allCommonBean.getData().getCartype().get(0)
                                .getColor().get(0).getCt_car_color();

                        carPrice = allCommonBean.getData().getCartype().get(0)
                                .getColor().get(0).getCt_car_price();
                        car_price_tv.setText(allCommonBean.getData().getCartype()
                                .get(0).getColor().get(0).getCt_car_price()
                                );
                        amount_cet.setText((allCommonBean.getData().getCartype()
                                .get(0).getColor().get(0).getCt_car_price()));

                        kucun_tv.setText(allCommonBean.getData().getCartype()
                                .get(0).getColor().get(0).getCount()
                                + "辆");
                        if (allCommonBean.getData().getCartype().get(0).getColor()
                                .get(0).getCount().equals("0")) {
                            kucunno_tv.setVisibility(View.VISIBLE);
                        } else {
                            kucunno_tv.setVisibility(View.GONE);
                        }

                        loading_ll.setVisibility(View.GONE);
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
            case R.id.yuyin_iv:
                rec();
                break;
            case R.id.show_btn:
                // starActivity(ShowActivity.class);
                Intent intents = new Intent(ChooseCarActivity.this,
                        ShowCarActivity.class);
                intents.putExtra("carType", carType);
                intents.putExtra("carColor", carColor);
                startActivity(intents);
                break;
            case R.id.kucun_ll:
                if (kucunBean != null && kucunBean.getData() != null
                        && kucunBean.getData().size() != 0) {
                    Intent intent = new Intent(ChooseCarActivity.this,
                            CarActivity.class);
                    intent.putExtra("LIST", kucunBean.getData());
                    startActivity(intent);//
                }
                break;

            case R.id.sure_btn:
                if (amount_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请输入车辆价格");
                    return;
                }
                if (address_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请填写充电桩安装地址");
                    return;
                }
                if (select_insurance_tv.getText().toString().equals("选择投保险种")) {
                    ToastUtils.showShort("请选择投保险种");
                    return;
                }
                if (insurance_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请输入保险费用");
                    return;
                }

                carPrice = amount_cet.getText().toString();

                selectBuyCar(
                        yfs_id,
                        yfs_sellsq_code,
                        carType,
                        carColor,
                        select_insurance_tv.getText().toString()
                                .replace("选择了保险", ""), carPrice,
                        address_cet.getText().toString(), insurance_cet.getText().toString(),
                        remark_cet.getText().toString(), remarkfei_cet.getText().toString()
                );

                break;
            case R.id.select_insurance_tv:
                Intent intent = new Intent(ChooseCarActivity.this, ChooseInsuranceActivity.class);
                intent.putExtra("INSURANCE", insurance);
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            try {
                insurance = data.getStringExtra("INSURANCE");
                select_insurance_tv.setText("选择了保险" + insurance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == 2) {
            String message = "对话框的识别结果：";
            if (resultCode == RESULT_OK) {
                ArrayList results = data.getStringArrayListExtra("results");
                if (results != null && results.size() > 0) {
                    message += results.get(0);
                }
                System.out.println("结果是：" + results);

                address_cet.setText(results.get(0) + "");
            } else {
                message += "没有结果";
            }

            MyLogger.info(message);
        }
    }

    private void getAllCarType() {
        String url = Constant.GETALLCARTYPE;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appcode", IMEI);
        map.put("apptype", Constant.APPTYPE);
        map.put("mg_id", application.userBean.getMg_id());
        map.put("mg_name", application.userBean.getMg_name());
        map.put("mg_shopsid", application.userBean.getMg_shopsid());
        map.put("mg_groupid", application.userBean.getMg_groupid());
        map.put("mg_shopname", application.userBean.getMg_shopname());
        map.put("mg_code", application.userBean.getMg_code());
        map.put("mg_groupname", application.userBean
                .getMg_groupname());
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
        }

        @Override
        public void onErrorResponse(VolleyError arg0) {
            // TODO Auto-generated method stub
            mHandler.sendEmptyMessage(-1);
        }

    };

    private void getCarType() {
        String url = Constant.GETCARTYPE;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appcode", IMEI);
        map.put("apptype", Constant.APPTYPE);
        map.put("mg_id", application.userBean.getMg_id());
        map.put("mg_name", application.userBean.getMg_name());
        map.put("mg_shopsid", application.userBean.getMg_shopsid());
        map.put("mg_groupid", application.userBean.getMg_groupid());
        map.put("mg_shopname", application.userBean.getMg_shopname());
        map.put("mg_code", application.userBean.getMg_code());
        map.put("mg_groupname", application.userBean
                .getMg_groupname());
        map.put("mg_posid", application.userBean.getMg_posid());
        map.put("mg_posname", application.userBean.getMg_posname());

        GsonRequest<CommonBean<ArrayList<CarTypeBean>>> requtst = new GsonRequest<CommonBean<ArrayList<CarTypeBean>>>(
                Method.POST, url, listener_getCarType);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<CarTypeBean>>> listener_getCarType = new RequesListener<CommonBean<ArrayList<CarTypeBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<CarTypeBean>> arg0) {
            // TODO Auto-generated method stub
            carTypeBean = arg0;
            mHandler.sendEmptyMessage(1);
        }

        @Override
        public void onErrorResponse(VolleyError arg0) {
            // TODO Auto-generated method stub
            mHandler.sendEmptyMessage(-1);
        }

    };

    private void getCarColor(String ct_car_type, String yck_car_subchexing) {
        String url = Constant.GETCARCOLOR;
        HashMap<String, String> map = new HashMap<String, String>();
        // map.put("ct_car_type", ct_car_type);
        map.put("yck_car_chexing", ct_car_type);
        map.put("yck_car_subchexing", yck_car_subchexing);
        map.put("appcode", IMEI);
        map.put("apptype", Constant.APPTYPE);
        map.put("mg_id", application.userBean.getMg_id());
        map.put("mg_name", application.userBean.getMg_name());
        map.put("mg_shopsid", application.userBean.getMg_shopsid());
        map.put("mg_groupid", application.userBean.getMg_groupid());
        map.put("mg_shopname", application.userBean.getMg_shopname());
        map.put("mg_code", application.userBean.getMg_code());
        map.put("mg_groupname", application.userBean
                .getMg_groupname());
        map.put("mg_posid", application.userBean.getMg_posid());
        map.put("mg_posname", application.userBean.getMg_posname());

        GsonRequest<CommonBean<ArrayList<CarColorBean>>> requtst = new GsonRequest<CommonBean<ArrayList<CarColorBean>>>(
                Method.POST, url, listener_getCarColor);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<CarColorBean>>> listener_getCarColor = new RequesListener<CommonBean<ArrayList<CarColorBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<CarColorBean>> arg0) {
            // TODO Auto-generated method stub
            carColorBean = arg0;
            mHandler.sendEmptyMessage(2);
        }

        @Override
        public void onErrorResponse(VolleyError arg0) {
            // TODO Auto-generated method stub
            mHandler.sendEmptyMessage(-1);
        }
    };

    private void selectBuyCar(String yfs_id, String yfs_sellsq_code,
                              String yfs_car_cartype, String yfs_car_carcolor,
                              String yfs_car_carinsurance,
                              String yfs_car_carprice, String yfs_car_chargpostaddr,
                              String yfs_insurance_price, String yfs_additional_items,
                              String yfs_additional_price) {
        String url = Constant.SELECTBUYCAR;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appcode", IMEI);
        map.put("apptype", Constant.APPTYPE);
        map.put("mg_id", application.userBean.getMg_id());
        map.put("mg_name", application.userBean.getMg_name());
        map.put("mg_shopsid", application.userBean.getMg_shopsid());
        map.put("mg_groupid", application.userBean.getMg_groupid());
        map.put("mg_shopname", application.userBean.getMg_shopname());
        map.put("mg_code", application.userBean.getMg_code());
        map.put("mg_groupname", application.userBean
                .getMg_groupname());
        map.put("mg_posid", application.userBean.getMg_posid());
        map.put("mg_posname", application.userBean.getMg_posname());

        map.put("yfs_id", yfs_id);
        map.put("yfs_sellsq_code", yfs_sellsq_code);
        map.put("yfs_car_cartype", yfs_car_cartype);
        map.put("yfs_car_carcolor", yfs_car_carcolor);
        map.put("yfs_car_carinsurance", yfs_car_carinsurance);
        map.put("yfs_car_carprice", yfs_car_carprice);
        map.put("yfs_car_chargpostaddr", yfs_car_chargpostaddr);
        map.put("yfs_insurance_price", yfs_insurance_price);
        map.put("yfs_additional_items", yfs_additional_items);
        map.put("yfs_additional_price", yfs_additional_price);

        GsonRequest<CommonBean> requtst = new GsonRequest<CommonBean>(
                Method.POST, url, listener_addBuyCars);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean> listener_addBuyCars = new RequesListener<CommonBean>() {
        @Override
        public void onResponse(CommonBean arg0) {
            // TODO Auto-generated method stub
            commonBean = arg0;
            mHandler.sendEmptyMessage(3);
        }

        @Override
        public void onErrorResponse(VolleyError arg0) {
            // TODO Auto-generated method stub
            mHandler.sendEmptyMessage(-1);
        }
    };

    private void getCarkucunNumByCarType(String ct_car_type,
                                         String ct_car_ywtype) {
        String url = Constant.GETCARKUCUNLISTBYCARTYPE;// .GETCARKUCUNNUMBYCARTYPE;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("ct_car_type", ct_car_type);
        map.put("ct_car_ywtype", ct_car_ywtype);
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

        GsonRequest<CommonBean<ArrayList<CarBean>>> requtst = new GsonRequest<CommonBean<ArrayList<CarBean>>>(
                Method.POST, url, listener_getCarkucunNumByCarType);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<CarBean>>> listener_getCarkucunNumByCarType = new RequesListener<CommonBean<ArrayList<CarBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<CarBean>> arg0) {
            // TODO Auto-generated method stub
            kucunBean = arg0;
            mHandler.sendEmptyMessage(4);
        }

        @Override
        public void onErrorResponse(VolleyError arg0) {
            // TODO Auto-generated method stub
            mHandler.sendEmptyMessage(-1);
        }
    };


    /*
     * Api的参数类，仅仅用于生成调用START的json字符串，本身与SDK的调用无关
     */
    private CommonRecogParams apiParams;
    /**
     * 识别控制器，使用MyRecognizer控制识别的流程
     */
    protected MyRecognizer myRecognizer;

    private ChainRecogListener chainRecogListener;

    private DigitalDialogInput input;

    private void rec() {
        try {
            /**
             * 有2个listner，一个是用户自己的业务逻辑，如MessageStatusRecogListener。另一个是UI对话框的。
             * 使用这个ChainRecogListener把两个listener和并在一起
             */
            chainRecogListener = new ChainRecogListener();
            // DigitalDialogInput 输入 ，MessageStatusRecogListener可替换为用户自己业务逻辑的listener
            chainRecogListener.addListener(new MessageStatusRecogListener(null));
            myRecognizer.setEventListener(chainRecogListener); // 替换掉原来的listener

            final Map<String, Object> params = fetchParams();
            System.out.println("params" + params);
            // BaiduASRDigitalDialog的输入参数
            input = new DigitalDialogInput(myRecognizer, chainRecogListener, params);
            BaiduASRDigitalDialog.setInput(input); // 传递input信息，在BaiduASRDialog中读取,
            Intent intent = new Intent(this, BaiduASRDigitalDialog.class);

            // 修改对话框样式
            // intent.putExtra(BaiduASRDigitalDialog.PARAM_DIALOG_THEME, BaiduASRDigitalDialog.THEME_ORANGE_DEEPBG);
            startActivityForResult(intent, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> fetchParams() {
        apiParams = new OnlineRecogParams();
        apiParams.initSamplePath(this);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        //  上面的获取是为了生成下面的Map， 自己集成时可以忽略
        Map<String, Object> params = apiParams.fetch(sp);
        //  集成时不需要上面的代码，只需要params参数。
        return params;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (myRecognizer != null) {
            myRecognizer.release();
        }
    }

}
