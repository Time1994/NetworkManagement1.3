package com.eroadcar.networkmanagement.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

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
import com.eroadcar.networkmanagement.bean.AddCarsBean;
import com.eroadcar.networkmanagement.bean.AllCarTypeBean;
import com.eroadcar.networkmanagement.bean.CarBean;
import com.eroadcar.networkmanagement.bean.CarColorBean;
import com.eroadcar.networkmanagement.bean.CarTypeBean;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.QianKeBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.view.ClearEditText;
import com.eroadcar.networkmanagement.view.MyGridView;
import com.eroadcar.networkmanagement.view.widget.NumericWheelAdapter;
import com.eroadcar.networkmanagement.view.widget.OnWheelChangedListener;
import com.eroadcar.networkmanagement.view.widget.OnWheelScrollListener;
import com.eroadcar.networkmanagement.view.widget.WheelView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ChooseCarRentActivity extends BaseActivity implements OnClickListener {
    private Button sure_btn, back_btn, show_btn;
    private TextView title_tv, kucun_tv, kucunno_tv;

    private MyGridView type_gv, color_gv;
    private TextView select_getcar_tv, dates_tv, datee_tv, datebs_tv, datebe_tv,datedj_tv;

    private RadioGroup price_rg, payment_rg;

    private ClearEditText address_cet, amount_cet, rnum_cet, pai_cet, zlprice_cet;//, vin_cet;

    private LinearLayout kucun_ll;

    private ImageView yuyin_iv, camrea_iv;

    private AutoCompleteTextView owner_cet;

    private CarTypeAdapter adapter;
    private CarColorAdapter adapterc;

    private CommonBean<ArrayList<CarTypeBean>> carTypeBean;
    private CommonBean<ArrayList<CarColorBean>> carColorBean;
    private CommonBean<ArrayList<CarBean>> kucunBean;
    private CommonBean commonBean;

//    private ArrayList<AddCarsBean> addCarsBeans;

    private String payMent = "POS", price = "0", carType = "",
            yck_car_subchexing = "", carColor = "", carYwType = "",
            carPrice = "", yfs_car_zlstart = "", yfs_car_zlend = "", yfs_id,
            yfs_leasesq_code, timeType;

    private CommonBean<AllCarTypeBean> allCommonBean;

    private int index = 0;

    private String[] licenses;
    private CommonBean<ArrayList<CarBean>> qianKeBeans;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA};
    private static int REQUEST_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_car_rent);

        title_tv = (TextView) findViewById(R.id.title_tv);
        sure_btn = (Button) findViewById(R.id.sure_btn);
        back_btn = (Button) findViewById(R.id.back_btn);
        show_btn = (Button) findViewById(R.id.show_btn);
        kucun_tv = (TextView) findViewById(R.id.kucun_tv);
        kucunno_tv = (TextView) findViewById(R.id.kucunno_tv);

        type_gv = (MyGridView) findViewById(R.id.typer_gv);
        color_gv = (MyGridView) findViewById(R.id.colorr_gv);

        select_getcar_tv = (TextView) findViewById(R.id.selectr_tv);
        dates_tv = (TextView) findViewById(R.id.dates_tv);
        datee_tv = (TextView) findViewById(R.id.datee_tv);
        datebs_tv = (TextView) findViewById(R.id.datebs_tv);
        datebe_tv = (TextView) findViewById(R.id.datebe_tv);
        datedj_tv = (TextView) findViewById(R.id.datedj_tv);

        price_rg = (RadioGroup) findViewById(R.id.price_rg);
        payment_rg = (RadioGroup) findViewById(R.id.paymentr_rg);

        address_cet = (ClearEditText) findViewById(R.id.address_cet);
        amount_cet = (ClearEditText) findViewById(R.id.amount_cet);
        rnum_cet = (ClearEditText) findViewById(R.id.rnum_cet);
        zlprice_cet = (ClearEditText) findViewById(R.id.zlprice_cet);

        pai_cet = (ClearEditText) findViewById(R.id.pai_cet);
//        vin_cet = (ClearEditText) findViewById(R.id.vin_cet);

        kucun_ll = (LinearLayout) findViewById(R.id.kucun_ll);

        yuyin_iv = (ImageView) findViewById(R.id.yuyin_iv);
        camrea_iv = (ImageView) findViewById(R.id.camrea_iv);

        owner_cet = (AutoCompleteTextView) findViewById(R.id.owner_cet);

        back_btn.setOnClickListener(this);
        kucun_ll.setOnClickListener(this);
        show_btn.setOnClickListener(this);
        yuyin_iv.setOnClickListener(this);
        camrea_iv.setOnClickListener(this);

        title_tv.setText("租赁车型");
        back_btn.setText("");

//        Intent intent = getIntent();
//        yfs_id = intent.getStringExtra("yfs_id");
//        yfs_leasesq_code = intent.getStringExtra("yfs_leasesq_code");

        type_gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                adapter.setSeclection(position);
                adapter.notifyDataSetChanged();
                // carPrice = carTypeBean.getData().get(position)
                // .getCt_car_price();
                // carType =
                // carTypeBean.getData().get(position).getCt_car_type();
                // carYwType = carTypeBean.getData().get(position)
                // .getCt_car_ywtype();
                // yck_car_subchexing = carTypeBean.getData().get(position)
                // .getCt_car_xinghao();
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
                        ChooseCarRentActivity.this);
                color_gv.setAdapter(adapterc);
                adapterc.setSeclection(0);

                carColor = allCommonBean.getData().getCartype().get(position)
                        .getColor().get(0).getCt_car_color();

                carPrice = allCommonBean.getData().getCartype().get(position)
                        .getColor().get(0).getCt_car_price();
                // car_price_tv.setText(allCommonBean.getData().getCartype()
                // .get(position).getColor().get(0).getCt_car_price()
                // + "万元");

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
                // car_price_tv.setText(allCommonBean.getData().getCartype()
                // .get(index).getColor().get(position).getCt_car_price()
                // + "万元");

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
                } else {
                    price = "1";// 全款
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

        owner_cet.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

                getPotentialClientByName(owner_cet.getText().toString());

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

        select_getcar_tv.setOnClickListener(this);
        sure_btn.setOnClickListener(this);
        datee_tv.setOnClickListener(this);
        dates_tv.setOnClickListener(this);
        datebe_tv.setOnClickListener(this);
        datebs_tv.setOnClickListener(this);
        datedj_tv.setOnClickListener(this);

        // getCarType();

        getAllCarType();

        setCurDate();

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
                                    ChooseCarRentActivity.this);
                            type_gv.setAdapter(adapter);
                            adapter.setSeclection(0);
                            carPrice = carTypeBean.getData().get(0)
                                    .getCt_car_price();
                            carType = carTypeBean.getData().get(0).getCt_car_type();
                            carYwType = carTypeBean.getData().get(0)
                                    .getCt_car_ywtype();
                            yck_car_subchexing = carTypeBean.getData().get(0)
                                    .getCt_car_xinghao();

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
                                    ChooseCarRentActivity.this);
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
                                .getCartype(), ChooseCarRentActivity.this);
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
                                ChooseCarRentActivity.this);
                        color_gv.setAdapter(adapterc);
                        adapterc.setSeclection(0);
                        carColor = allCommonBean.getData().getCartype().get(0)
                                .getColor().get(0).getCt_car_color();

                        carPrice = allCommonBean.getData().getCartype().get(0)
                                .getColor().get(0).getCt_car_price();
                        // car_price_tv.setText(allCommonBean.getData().getCartype()
                        // .get(0).getColor().get(0).getCt_car_price()
                        // + "万元");

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
                case 10:
                    if (qianKeBeans.getState().equals("success")) {

                        licenses = new String[qianKeBeans.getData().size()];

                        for (int i = 0; i < qianKeBeans.getData().size(); i++) {
                            licenses[i] = qianKeBeans.getData().get(i)
                                    .getYck_car_vin();
                        }
                        ArrayAdapter<String> av = new ArrayAdapter<String>(
                                ChooseCarRentActivity.this, R.layout.view_textview,
                                licenses);
                        // ArrayAdapter adapter = new
                        // ArrayAdapter<String>(mMainActivity,
                        // android.R.layout.simple_dropdown_item_1line, licenses);

                        owner_cet.setAdapter(av);
                        av.notifyDataSetChanged();
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
            case R.id.camrea_iv:
                onScanBarcode();
                break;
            case R.id.kucun_ll:
//                if (kucunBean != null && kucunBean.getData() != null
//                        && kucunBean.getData().size() != 0) {
//                    Intent intent = new Intent(ChooseCarRentActivity.this,
//                            CarActivity.class);
//                    intent.putExtra("LIST", kucunBean.getData());
//                    startActivity(intent);
//                }
                break;
            case R.id.sure_btn:
                // if (yfs_id == null || yfs_id.equals("")) {
                // ToastUtils.showShort("请先提交个人信息");
                // return;
                // }
                yfs_car_zlstart = dates_tv.getText().toString();
                yfs_car_zlend = datee_tv.getText().toString();

//                if (rnum_cet.getText().toString().equals("")) {
//                    ToastUtils.showShort("请输入租赁车辆");
//                    return;
//                }
//
//                if (Integer.valueOf(rnum_cet.getText().toString()) > Integer
//                        .valueOf(kucun_tv.getText().toString().replace("辆", ""))) {
//                    ToastUtils.showShort("车辆库存不足，请重新输入");
//                    return;
//                }\
                if (zlprice_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请填写租赁价格");
                    return;
                }
                if (pai_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请填写车牌号");
                    return;
                }
//                if (vin_cet.getText().toString().equals("")) {
//                    ToastUtils.showShort("请填写车架号");
//                    return;
//                }
                if (owner_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请填写车架号");
                    return;
                }

                if (yfs_car_zlstart.contains("选择")) {
                    ToastUtils.showShort("请选择起始时间");
                    return;
                }
                if (yfs_car_zlend.contains("选择")) {
                    ToastUtils.showShort("请选择结束时间");
                    return;
                }
//                if (amount_cet.getText().toString().equals("")) {
//                    ToastUtils.showShort("请填写金额");
//                    return;
//                }
                if (address_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请填写充电桩安装地址");
                    return;
                }

//                if (yfs_id == null || yfs_id.equals("")) {
                AddCarsBean addCarsBean = new AddCarsBean();
                addCarsBean.setYfs_car_carcolor(carColor);
                addCarsBean.setYfs_car_carinsurance("");
                addCarsBean.setYfs_car_carprice(zlprice_cet.getText().toString());//carPrice
                addCarsBean.setYfs_car_cartype(carType);
                addCarsBean.setYfs_car_chargpostaddr(address_cet.getText()
                        .toString());
                addCarsBean.setYfs_car_paytype(payMent);
                addCarsBean.setYfs_car_pricetype(price);
                addCarsBean.setYfs_car_remark("");
                addCarsBean.setYfs_car_zlend(yfs_car_zlend);
                addCarsBean.setYfs_car_zlstart(yfs_car_zlstart);
                addCarsBean.setYfs_id("");
                addCarsBean.setYfs_leasesq_code("");
                addCarsBean.setYfs_car_num("1");
                addCarsBean.setYfs_car_license(pai_cet.getText().toString());
//                addCarsBean.setYfs_car_vin(vin_cet.getText().toString());
                addCarsBean.setYfs_car_vin(owner_cet.getText().toString());
                addCarsBean.setYfs_car_preprice("");
//                addCarsBean.setYfs_car_preprice(Double.parseDouble(amount_cet
//                        .getText().toString()) / 10000 + "");
//                addCarsBeans.add(addCarsBean);

                ToastUtils.showShort("车辆已添加");

                Intent mIntent = new Intent();
                mIntent.putExtra("LIST", addCarsBean);
                setResult(RESULT_OK, mIntent);
                onBackPressed();
//                } else {
//                    addLeaseCars(yfs_id, yfs_leasesq_code, carType, carColor, "",
//                            "", price, carPrice, payMent, application.dotBean
//                                    .get(0).getPmg_points_id(), application.dotBean
//                                    .get(0).getPmg_points_name(), address_cet
//                                    .getText().toString(), yfs_car_zlstart,
//                            yfs_car_zlend, amount_cet.getText().toString(),
//                            rnum_cet.getText().toString());
//                }
                break;
            case R.id.datee_tv:
                setDate("E", datee_tv);
                break;
            case R.id.dates_tv:
                setDate("S", dates_tv);
                break;
            case R.id.datebe_tv:
                setDate("E", datebe_tv);
                break;
            case R.id.datebs_tv:
                setDate("S", datebs_tv);
                break;
            case R.id.datedj_tv:
                setDate("S", datedj_tv);
                break;
            case R.id.show_btn:
                Intent intents = new Intent(ChooseCarRentActivity.this,
                        ShowCarActivity.class);
                intents.putExtra("carType", carType);
                intents.putExtra("carColor", carColor);
                startActivity(intents);
                break;
            default:
                break;
        }
    }

    private void setDate(String t, TextView time_tv) {
        timeType = t;
        if (null != popupwindowd && popupwindowd.isShowing()) {
            popupwindowd.dismiss();
        } else {
            initPopupWindow(time_tv);
            popupwindowd.showAtLocation(time_tv, Gravity.BOTTOM, 0, 0);
        }
    }

    private void getPotentialClientByName(String ptc_name) {
        // loadingDialog.setMessage("正在获取信息...");
        // loadingDialog.dialogShow();
        String url = Constant.GETVINBYKEY;
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

        map.put("key", ptc_name);
        map.put("yck_car_chexing", carType);
        map.put("yck_car_color", carColor);

        GsonRequest<CommonBean<ArrayList<CarBean>>> requtst = new GsonRequest<CommonBean<ArrayList<CarBean>>>(
                Method.POST, url, listener_getPotentialClientByName);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<CarBean>>> listener_getPotentialClientByName = new RequesListener<CommonBean<ArrayList<CarBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<CarBean>> arg0) {
            // TODO Auto-generated method stub
            qianKeBeans = arg0;
            mHandler.sendEmptyMessage(10);
            // loadingDialog.dismiss();
        }

        @Override
        public void onErrorResponse(VolleyError arg0) {
            // TODO Auto-generated method stub
            mHandler.sendEmptyMessage(-1);
            // loadingDialog.dismiss();
        }

    };

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
        map.put("mg_groupname", application.userBean.getMg_groupname());
        map.put("mg_posid", application.userBean.getMg_posid());
        map.put("mg_posname", application.userBean.getMg_posname());

        GsonRequest<CommonBean<ArrayList<CarTypeBean>>> requtst = new GsonRequest<CommonBean<ArrayList<CarTypeBean>>>(
                Method.POST, url, listener_getCarType);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
        mRequestQueue.start();
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
        map.put("mg_groupname", application.userBean.getMg_groupname());
        map.put("mg_posid", application.userBean.getMg_posid());
        map.put("mg_posname", application.userBean.getMg_posname());

        GsonRequest<CommonBean<ArrayList<CarColorBean>>> requtst = new GsonRequest<CommonBean<ArrayList<CarColorBean>>>(
                Method.POST, url, listener_getCarColor);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
        mRequestQueue.start();
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

    private void addLeaseCars(String yfs_id, String yfs_leasesq_code,
                              String yfs_car_cartype, String yfs_car_carcolor,
                              String yfs_car_carinsurance, String yfs_car_remark,
                              String yfs_car_pricetype, String yfs_car_carprice,
                              String yfs_car_paytype, String yfs_car_tichepointid,
                              String yfs_car_tichepoint, String yfs_car_chargpostaddr,
                              String yfs_car_zlstart, String yfs_car_zlend,
                              String yfs_car_preprice, String yfs_car_num) {
        String url = Constant.ADDLEASECARS;
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

        map.put("yfs_id", yfs_id);
        map.put("yfs_leasesq_code", yfs_leasesq_code);
        map.put("yfs_car_cartype", yfs_car_cartype);
        map.put("yfs_car_carcolor", yfs_car_carcolor);
        map.put("yfs_car_carinsurance", yfs_car_carinsurance);
        map.put("yfs_car_remark", yfs_car_remark);
        map.put("yfs_car_pricetype", yfs_car_pricetype);
        map.put("yfs_car_carprice", yfs_car_carprice);
        map.put("yfs_car_paytype", yfs_car_paytype);
        map.put("yfs_car_tichepointid", yfs_car_tichepointid);
        map.put("yfs_car_tichepoint", yfs_car_tichepoint);
        map.put("yfs_car_chargpostaddr", yfs_car_chargpostaddr);
        map.put("yfs_car_zlstart", yfs_car_zlstart);
        map.put("yfs_car_zlend", yfs_car_zlend);
        map.put("yfs_car_preprice", Double.parseDouble(yfs_car_preprice)
                / 10000 + "");
        map.put("yfs_car_num", yfs_car_num);

        GsonRequest<CommonBean> requtst = new GsonRequest<CommonBean>(
                Method.POST, url, listener_addLeaseCars);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
        // mRequestQueue.start();
    }

    private RequesListener<CommonBean> listener_addLeaseCars = new RequesListener<CommonBean>() {
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

    private View convertView;
    private PopupWindow popupwindowd;
    // 用来保存年月日：
    private int mYear, mMonth, mDay;// , mHours, mMinutes, mWay;
    private WheelView year_wv, month_wv, date_wv;
    private java.text.DecimalFormat fo;
    private boolean timeChanged = false, timeScrolled = false;
    private StringBuilder time;

    // private String[] strings = { "2017", "2018", "2019", "2020", "02:30",
    // "03:00", "03:30", "04:00", "04:30", "05:00", "05:30", "06:00",
    // "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30",
    // "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00",
    // "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
    // "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00",
    // "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30",
    // "00:00" };

    private void setCurDate() {
        fo = new java.text.DecimalFormat("00");
        // 获得当前的日期：
        final Calendar currentDate = Calendar.getInstance();
        mYear = currentDate.get(Calendar.YEAR);
        mMonth = currentDate.get(Calendar.MONTH) + 1;
        mDay = currentDate.get(Calendar.DAY_OF_MONTH);

        time = new StringBuilder().append(mYear).append("-")
                .append(fo.format(mMonth)).append("-")
                // 得到的月份+1，因为从0开始
                .append(fo.format(mDay));
    }

    // 选择时间
    public void initPopupWindow(final TextView time_tv) {
        convertView = getLayoutInflater().inflate(R.layout.dialog_date_year,
                null, false);
        popupwindowd = new PopupWindow(convertView,
                LinearLayout.LayoutParams.FILL_PARENT, getResources()
                .getDimensionPixelSize(R.dimen.hundredt), true);
        popupwindowd.setAnimationStyle(R.style.AnimationPhoto);

        Button submit = (Button) convertView.findViewById(R.id.sure_btn);
        TextView title = (TextView) convertView.findViewById(R.id.title_tv);
        TextView close_tv = (TextView) convertView.findViewById(R.id.close_tv);
        ImageView cancel_iv = (ImageView) convertView
                .findViewById(R.id.cancel_iv);

        year_wv = (WheelView) convertView.findViewById(R.id.mins);
        month_wv = (WheelView) convertView.findViewById(R.id.data);
        date_wv = (WheelView) convertView.findViewById(R.id.hour);

        if (timeType.equals("S")) {
            title.setText("选择起始时间");
        } else {
            title.setText("选择结束时间");
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        // year_wv.setAdapter(new StringWheelAdapter(strings));
        // year_wv.setLabel("");
        // year_wv.setCyclic(true);

        year_wv.setAdapter(new NumericWheelAdapter(2017, 2117, "%02d", "年"));
        year_wv.setLabel("");
        year_wv.setCyclic(true);

        month_wv.setAdapter(new NumericWheelAdapter(1, 12, "%02d", "月"));
        month_wv.setLabel("");
        month_wv.setCyclic(true);

        date_wv.setAdapter(new NumericWheelAdapter(1, 31, "%02d", "日"));
        date_wv.setLabel("");
        date_wv.setCyclic(true);

        year_wv.setCurrentItem(mYear - 2017);
        month_wv.setCurrentItem(mMonth - 1);
        date_wv.setCurrentItem(mDay - 1);

        OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!timeScrolled) {
                    timeChanged = true;

                    timeChanged = false;
                }
            }
        };

        year_wv.addChangingListener(wheelListener);
        month_wv.addChangingListener(wheelListener);
        date_wv.addChangingListener(wheelListener);

        OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
                timeScrolled = true;
            }

            public void onScrollingFinished(WheelView wheel) {
                timeScrolled = false;
                timeChanged = true;
                try {
                    timeChanged = false;
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        };

        year_wv.addScrollingListener(scrollListener);
        month_wv.addScrollingListener(scrollListener);
        date_wv.addScrollingListener(scrollListener);

        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String tString = (year_wv.getCurrentItem() + 2017) + "-"
                        + fo.format(month_wv.getCurrentItem() + 1) + "-"
                        + fo.format(date_wv.getCurrentItem() + 1);//
                // + strings[year_wv.getCurrentItem()];
                time_tv.setText(tString);
                // time = new StringBuilder().append(mYear)
                // .append("-")
                // .append(fo.format(month_wv.getCurrentItem() + 1))
                // .append("-")
                // // 得到的月份+1，因为从0开始
                // .append(fo.format(date_wv.getCurrentItem() + 1))
                // .append(" ").append(strings[year_wv.getCurrentItem()])
                // .append(":00");
                // time = new StringBuilder().append(month_wv.getCurrentItem())
                // .append("-")
                // .append(fo.format(month_wv.getCurrentItem() + 1))
                // .append("-")
                // // 得到的月份+1，因为从0开始
                // .append(fo.format(date_wv.getCurrentItem() + 1));

                popupwindowd.dismiss();
            }
        });

        close_tv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupwindowd.dismiss();
            }
        });

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupwindowd.setBackgroundDrawable(new BitmapDrawable());
        // 使其聚集
        popupwindowd.setFocusable(true);
        popupwindowd.setTouchable(true); // 设置PopupWindow可触摸
        // 设置允许在外点击消失
        popupwindowd.setOutsideTouchable(true);

        backgroundAlpha(0.5f);
        popupwindowd.setOnDismissListener(new poponDismissListener());

        // 刷新状态
        popupwindowd.update();
    }

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

    public void onScanBarcode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("扫描条形码");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }

    public void onScanQrcode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("扫描二维码");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
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
        } else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "扫码取消！", Toast.LENGTH_LONG).show();
                } else {
//                    Toast.makeText(this, "扫描成功，条码值: " + result.getContents(), Toast.LENGTH_LONG).show();
//                    vin_cet.setText(result.getContents());
                    owner_cet.setText(result.getContents());
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (myRecognizer != null) {
            myRecognizer.release();
        }
    }
}
