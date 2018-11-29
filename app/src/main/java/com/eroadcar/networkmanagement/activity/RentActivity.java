package com.eroadcar.networkmanagement.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.RentCarAdapter;
import com.eroadcar.networkmanagement.bean.AddCarsBean;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.GeneralBean;
import com.eroadcar.networkmanagement.bean.ImagePathBean;
import com.eroadcar.networkmanagement.bean.QianKeBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.FileUtil;
import com.eroadcar.networkmanagement.utils.Logger;
import com.eroadcar.networkmanagement.utils.RecognizeService;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.utils.Tool;
import com.eroadcar.networkmanagement.view.ClearEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class RentActivity extends BaseActivity implements OnClickListener {
    private Button back_btn, sure_btn;
    private TextView title_tv;

    private Spinner spinner;
    private TextView shenfz_tv, shenfzf_tv, jiasz_tv;
    private ImageView shenfz_iv, shenfzf_iv, jiasz_iv;

    private ClearEditText custom_cet, mobile_cet, id_cet, faz_cet, dan_cet;

    private LinearLayout dan_ll, zheng_ll, tv_ll, image_ll;

    private TextView dan_tv, zheng_tv;

    private RelativeLayout select_rl;

    private ListView car_lv;

    private ArrayList<String> customs;

    private String custom = "政府", yfs_leasesq_idtype = "身份证";

    private String sfzzPath = "", sfzfPath = "", jiaszPath = "", yingyPath = "";

    private boolean hasGotToken = false;

    private static final int REQUEST_CODE_LICENSE_PLATE = 122,
            REQUEST_CODE_DRIVING_LICENSE = 121, REQUEST_CODE_VEHICLE_LICENSE = 120;
    private static final int REQUEST_CODE_BUSINESS_LICENSE = 123;
    private static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    private static final int REQUEST_CODE_PICK_IMAGE_BACK = 202;
    private static final int REQUEST_CODE_CAMERA = 102;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static int REQUEST_PERMISSION_CODE = 1;


    private CommonBean<ArrayList<QianKeBean>> qianKeBeans;
    private CommonBean commonBean;

    private ArrayList<AddCarsBean> addCarsBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        back_btn = (Button) findViewById(R.id.back_btn);
        sure_btn = (Button) findViewById(R.id.sure_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);

        spinner = (Spinner) findViewById(R.id.spinner);

        shenfz_tv = (TextView) findViewById(R.id.shenfz_tv);
        shenfzf_tv = (TextView) findViewById(R.id.shenfzf_tv);
        jiasz_tv = (TextView) findViewById(R.id.jiasz_tv);

        shenfz_iv = (ImageView) findViewById(R.id.shenfz_iv);
        shenfzf_iv = (ImageView) findViewById(R.id.shenfzf_iv);
        jiasz_iv = (ImageView) findViewById(R.id.jiasz_iv);

        custom_cet = (ClearEditText) findViewById(R.id.custom_cet);
        mobile_cet = (ClearEditText) findViewById(R.id.mobile_cet);
        id_cet = (ClearEditText) findViewById(R.id.id_cet);
        faz_cet = (ClearEditText) findViewById(R.id.faz_cet);
        dan_cet = (ClearEditText) findViewById(R.id.dan_cet);

        zheng_ll = (LinearLayout) findViewById(R.id.zheng_ll);
        dan_ll = (LinearLayout) findViewById(R.id.dan_ll);

        zheng_tv = (TextView) findViewById(R.id.zheng_tv);
        dan_tv = (TextView) findViewById(R.id.dan_tv);

        image_ll = (LinearLayout) findViewById(R.id.image_ll);
        tv_ll = (LinearLayout) findViewById(R.id.tv_ll);

        select_rl = (RelativeLayout) findViewById(R.id.select_rl);

        car_lv = (ListView) findViewById(R.id.car_lv);

        back_btn.setOnClickListener(this);
        sure_btn.setOnClickListener(this);

        shenfz_iv.setOnClickListener(this);
        shenfzf_iv.setOnClickListener(this);
        jiasz_iv.setOnClickListener(this);
        select_rl.setOnClickListener(this);

        title_tv.setText("新增租赁");
        back_btn.setText("");

        setSpinner();

        initAccessToken();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }
    }


    Handler mHandler = new Handler() {// 18672250286
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (commonBean.getState().equals("success")) {
                        onBackPressed();
                    }
                    ToastUtils.showShort(commonBean.getMsg());
                    break;
                case 10:
                    if (qianKeBeans.getState().equals("success")) {
//                        licenses = new String[qianKeBeans.getData().size()];
//                        for (int i = 0; i < qianKeBeans.getData().size(); i++) {
//                            licenses[i] = qianKeBeans.getData().get(i)
//                                    .getPtc_name()
//                                    + " "
//                                    + qianKeBeans.getData().get(i).getPtc_mobile();
//                        }
//                        ArrayAdapter<String> av = new ArrayAdapter<String>(
//                                SalesActivity.this, R.layout.view_textview,
//                                licenses);
//                        owner_cet.setAdapter(av);
//                        av.notifyDataSetChanged();

                        if (qianKeBeans.getData() != null && qianKeBeans.getData().size() != 0) {
                            mobile_cet.setText(qianKeBeans.getData().get(0).getPtc_mobile());
                        }
                    }
                    break;

                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initAccessToken();
        } else {
            Toast.makeText(getApplicationContext(), "需要android.permission.READ_PHONE_STATE", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;

            case R.id.select_rl:
                Intent intent = new Intent(RentActivity.this, ChooseCarRentActivity.class);
                startActivityForResult(intent, 1);
                break;

            case R.id.sure_btn:
                if (custom_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请输入客户姓名");
                    return;
                }

                if (mobile_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请输入客户手机号码");
                    return;
                }
                boolean judge = isMobile(mobile_cet.getText().toString());
                if (judge == false) {
                    ToastUtils.showShort("请输入正确的手机号码");
                    return;
                }

//                if (id_cet.getText().toString().equals("")) {
//                    ToastUtils.showShort("请输入客户证件号码");
//                    return;
//                }

                if (faz_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请输入客户住址");
                    return;
                }
                if (custom.equals("个人")) {
                    boolean idcheck = personIdValidation(id_cet.getText().toString());

                    if (idcheck == false) {
                        ToastUtils.showShort("请输入正确的身份证号码");
                        return;
                    }
                }
                if (addCarsBeans == null || addCarsBeans.size() == 0) {
                    ToastUtils.showShort("请选择租赁车辆");
                    return;
                }

                if (custom.equals("政府")) {
                    requreLeaseCars("", custom, dan_cet.getText()
                                    .toString(), mobile_cet.getText().toString(),
                            id_cet.getText().toString(), "", "",
                            "", faz_cet.getText().toString(), "申请中",
                            "",
                            "",
                            "",
                            "",
                            yfs_leasesq_idtype,
                            new Gson().toJson(addCarsBeans), custom_cet.getText().toString());
                } else {
                    uploadLeasesqPic();
                }


                break;
            case R.id.shenfz_iv:
                if (custom.equals("个人")) {
                    Intent intentz = new Intent(RentActivity.this, CameraActivity.class);
                    intentz.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intentz.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                    startActivityForResult(intentz, REQUEST_CODE_CAMERA);
                } else {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intenty = new Intent(RentActivity.this, CameraActivity.class);
                    intenty.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intenty.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intenty, REQUEST_CODE_BUSINESS_LICENSE);
                }
                break;
            case R.id.shenfzf_iv:
                Intent intentf = new Intent(RentActivity.this, CameraActivity.class);
                intentf.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intentf.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                startActivityForResult(intentf, REQUEST_CODE_CAMERA);
                break;
            case R.id.jiasz_iv:
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intentj = new Intent(RentActivity.this, CameraActivity.class);
                intentj.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intentj.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intentj, REQUEST_CODE_DRIVING_LICENSE);
                break;
            default:
                break;
        }
    }

    private void requreLeaseCars(String yfs_id, String yfs_leasesq_ywtype,
                                 String yfs_leasesq_ownner, String yfs_leasesq_cellphone,
                                 String yfs_leasesq_id, String yfs_leasesq_province,
                                 String yfs_leasesq_city, String yfs_leasesq_country,
                                 String yfs_leasesq_addr, String yfs_leasesq_state,
                                 String yfs_leasesq_remark, String yfs_leasesq_idpic,
                                 String yfs_leasesq_driverpica, String yfs_leasesq_driverpicb, String yfs_leasesq_idtype,
                                 String yfs_leasesq_cars, String yfs_leasesq_contact) {
        loadingDialog.setMessage("正在上传信息...");
        loadingDialog.dialogShow();
        String url = Constant.REQLEASECARS;
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

        map.put("yfs_id ", yfs_id);
        map.put("yfs_leasesq_ywtype", yfs_leasesq_ywtype);
        map.put("yfs_leasesq_id", yfs_leasesq_id);
        map.put("yfs_leasesq_ownner", yfs_leasesq_ownner);
        map.put("yfs_leasesq_cellphone", yfs_leasesq_cellphone);
        map.put("yfs_leasesq_province", yfs_leasesq_province);
        map.put("yfs_leasesq_city", yfs_leasesq_city);
        map.put("yfs_leasesq_country", yfs_leasesq_country);
        map.put("yfs_leasesq_addr", yfs_leasesq_addr);
        map.put("yfs_leasesq_state", yfs_leasesq_state);
        map.put("yfs_leasesq_remark", yfs_leasesq_remark);
        map.put("yfs_leasesq_idpic", yfs_leasesq_idpic);
        map.put("yfs_leasesq_driverpica", yfs_leasesq_driverpica);
        map.put("yfs_leasesq_driverpicb", yfs_leasesq_driverpicb);
        map.put("yfs_leasesq_cars", yfs_leasesq_cars);
        map.put("yfs_leasesq_idtype", yfs_leasesq_idtype);
        map.put("yfs_leasesq_fullname", yfs_leasesq_contact);

        GsonRequest<CommonBean<GeneralBean>> requtst = new GsonRequest<CommonBean<GeneralBean>>(
                Request.Method.POST, url, listener_requreLeaseCars);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
        // mRequestQueue.start();
    }

    private RequesListener<CommonBean<GeneralBean>> listener_requreLeaseCars = new RequesListener<CommonBean<GeneralBean>>() {
        @Override
        public void onResponse(CommonBean<GeneralBean> arg0) {
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


    private void getPotentialClientByName(String ptc_name) {
        // loadingDialog.setMessage("正在获取信息...");
        // loadingDialog.dialogShow();
        String url = Constant.GETPOTENTIALCLIENTBYNAME;
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

        map.put("ptc_name", ptc_name);

        GsonRequest<CommonBean<ArrayList<QianKeBean>>> requtst = new GsonRequest<CommonBean<ArrayList<QianKeBean>>>(
                Request.Method.POST, url, listener_getPotentialClientByName);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<QianKeBean>>> listener_getPotentialClientByName = new RequesListener<CommonBean<ArrayList<QianKeBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<QianKeBean>> arg0) {
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


    private void setSpinner() {
        customs = new ArrayList<String>();
        customs.add(0, "政府");
        customs.add(1, "单位");
        customs.add(2, "个人");
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(
                RentActivity.this, R.layout.view_textview, customs);
        spinner.setAdapter(arr_adapter);
        spinner.setSelection(0, true);

        yfs_leasesq_idtype = "";

        dan_ll.setVisibility(View.VISIBLE);
        zheng_ll.setVisibility(View.GONE);
        dan_tv.setText("机关全称");

        tv_ll.setVisibility(View.GONE);
        image_ll.setVisibility(View.GONE);

        custom_cet.setText("");
        id_cet.setText("");
        faz_cet.setText("");
        dan_cet.setText("");


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                custom = customs.get(position);

                if (custom.equals("个人")) {
                    yfs_leasesq_idtype = "身份证";

                    dan_ll.setVisibility(View.GONE);
                    zheng_ll.setVisibility(View.VISIBLE);
                    zheng_tv.setText("证件号码");

                    shenfz_tv.setText("请拍摄身份证正面提取信息");
                    image_ll.setVisibility(View.VISIBLE);
                    tv_ll.setVisibility(View.VISIBLE);


                    shenfzf_iv.setVisibility(View.VISIBLE);
                    jiasz_iv.setVisibility(View.VISIBLE);
                    shenfzf_tv.setVisibility(View.VISIBLE);
                    jiasz_tv.setVisibility(View.VISIBLE);

                    yingyPath = "";
                    shenfz_iv.setImageResource(R.mipmap.icon_camrea_sfzs);

                    custom_cet.setText("");
                    id_cet.setText("");
                    faz_cet.setText("");
                    dan_cet.setText("");
                } else if (custom.equals("单位")) {
                    yfs_leasesq_idtype = "营业执照";

                    dan_ll.setVisibility(View.VISIBLE);
                    dan_tv.setText("公司全称");
                    zheng_ll.setVisibility(View.VISIBLE);
                    zheng_tv.setText("税号");

                    shenfz_tv.setText("请拍摄营业执照提取信息");
                    tv_ll.setVisibility(View.VISIBLE);
                    image_ll.setVisibility(View.VISIBLE);

                    shenfzf_iv.setVisibility(View.INVISIBLE);
                    jiasz_iv.setVisibility(View.INVISIBLE);
                    shenfzf_tv.setVisibility(View.INVISIBLE);
                    jiasz_tv.setVisibility(View.INVISIBLE);

                    sfzzPath = "";
                    shenfz_iv.setImageResource(R.mipmap.icon_camrea_sfzs);
                    sfzfPath = "";
                    shenfzf_iv.setImageResource(R.mipmap.icon_camrea_sfzs);
                    jiaszPath = "";
                    jiasz_iv.setImageResource(R.mipmap.icon_camrea_sfzs);

                    custom_cet.setText("");
                    id_cet.setText("");
                    faz_cet.setText("");
                    dan_cet.setText("");
                } else {
                    yfs_leasesq_idtype = "";

                    dan_ll.setVisibility(View.VISIBLE);
                    zheng_ll.setVisibility(View.GONE);
                    dan_tv.setText("机关全称");

                    tv_ll.setVisibility(View.GONE);
                    image_ll.setVisibility(View.GONE);

                    custom_cet.setText("");
                    id_cet.setText("");
                    faz_cet.setText("");
                    dan_cet.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });


    }

    /**
     * 以license文件方式初始化
     */
    private void initAccessToken() {
        OCR.getInstance(this).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();

                ToastUtils.showShort("licence方式获取token失败");
//                alertText("licence方式获取token失败", error.getMessage());
            }
        }, getApplicationContext());
    }

    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            ToastUtils.showShort("token还未成功获取");
        }
        return hasGotToken;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 识别成功回调，车牌识别
        if (requestCode == REQUEST_CODE_LICENSE_PLATE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recLicensePlate(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            System.out.println("识别返回数据＝" + result);
                            ToastUtils.showShort(result);
                        }
                    });
        }

        // 识别成功回调，行驶证识别
        if (requestCode == REQUEST_CODE_VEHICLE_LICENSE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recVehicleLicense(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            System.out.println("识别返回数据＝" + result);
                            ToastUtils.showShort(result);
                        }
                    });
        }

        // 识别成功回调，驾驶证识别
        if (requestCode == REQUEST_CODE_DRIVING_LICENSE && resultCode == Activity.RESULT_OK) {
            File sd = Environment.getExternalStorageDirectory();
            String path = sd.getPath();//+ "/eroadcar/";
            path = path + "/jiasz.jpg";
//            jiaszPath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
            String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();

            Tool.saveImage(getLoacalBitmap(filePath), path);

            jiaszPath = path;

            RecognizeService.recDrivingLicense(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            if (result != null) {
                                System.out.println("识别返回数据＝" + result);
//                            ToastUtils.showShort(result);
                                setInterface(result);
                            } else {
                                ToastUtils.showShort(R.string.shibie);
                            }
                        }
                    });
        }
        // 识别成功回调，营业执照识别
        if (requestCode == REQUEST_CODE_BUSINESS_LICENSE && resultCode == Activity.RESULT_OK) {
            yingyPath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();

            RecognizeService.recBusinessLicense(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            System.out.println("识别返回数据＝" + result);
                            setInterfaceD(result);
                        }
                    });
        }


        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
                File sd = Environment.getExternalStorageDirectory();
                String path = sd.getPath() + "/";// + "/eroadcar/";
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        path = path + "sfzz.jpg";
                        Tool.saveImage(getLoacalBitmap(filePath), path);
                        sfzzPath = path;
//                        sfzzPath = filePath;

                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        path = path + "sfzf.jpg";
                        Tool.saveImage(getLoacalBitmap(filePath), path);
                        sfzfPath = path;
//                        sfzfPath = filePath;

                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                    }
                }
            }
        }

        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            addCarsBeans.add((AddCarsBean) data.getSerializableExtra("LIST"));

            car_lv.setAdapter(new RentCarAdapter(addCarsBeans, RentActivity.this));
            setListViewHeightBasedOnChildren(car_lv);
        }
    }

    private void recIDCard(final String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        OCR.getInstance(this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                try {
                    if (result != null) {
                        System.out.println("识别返回数据＝" + result.toString());
                        if (idCardSide.equals("front")) {
                            setInterface(result);
                        } else {
                            shenfzf_iv.setImageBitmap(getLoacalBitmap(sfzfPath));
                        }
                    } else {
                        ToastUtils.showShort(R.string.shibie);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(R.string.shibie);
                }
            }

            @Override
            public void onError(OCRError error) {
                System.out.println("识别返回数据＝" + error.getMessage());
                ToastUtils.showShort(error.getMessage());
            }
        });
    }


    private void setInterface(IDCardResult result) {
        custom_cet.setText(result.getName().toString());
        id_cet.setText(result.getIdNumber().toString());
        faz_cet.setText(result.getAddress().toString());

        shenfz_iv.setImageBitmap(getLoacalBitmap(sfzzPath));

//        getPotentialClientByName(result.getName().toString());
    }

    private void setInterface(String result) {
        try {
//            JSONObject object = new JSONObject(result);
//            JSONObject object1 = object.getJSONObject("words_result");
//            JSONObject object2 = object1.getJSONObject("证号");
//            String id = object2.getString("words");
//
//            JSONObject object3 = object1.getJSONObject("住址");
//            String address = object3.getString("words");
//
//            JSONObject object4 = object1.getJSONObject("姓名");
//            String name = object4.getString("words");
//
//            custom_cet.setText(name);
//            id_cet.setText(id);
//            faz_cet.setText(address);

            jiasz_iv.setImageBitmap(getLoacalBitmap(jiaszPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setInterfaceD(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject object1 = object.getJSONObject("words_result");
            JSONObject object2 = object1.getJSONObject("社会信用代码");
            String id = object2.getString("words");

            JSONObject object3 = object1.getJSONObject("单位名称");
            String address = object3.getString("words");

            JSONObject object4 = object1.getJSONObject("法人");
            String name = object4.getString("words");

            JSONObject object5 = object1.getJSONObject("地址");
            String addr = object5.getString("words");

            custom_cet.setText(name);
            id_cet.setText(id);
            faz_cet.setText(addr);
            dan_cet.setText(address);

            shenfz_iv.setImageBitmap(getLoacalBitmap(yingyPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void uploadLeasesqPic() {
        String subjet = "";
        if (custom.equals("单位")) {
            if (yingyPath.equals("")) {
                ToastUtils.showShort("请上传企业资质照片");
                return;
            }
            subjet = "1";
        } else {
            if (sfzzPath.equals("")) {
                ToastUtils.showShort("请上传身份证正面照片");
                return;
            }
            if (sfzfPath.equals("")) {
                ToastUtils.showShort("请上传身份证反面照片");
                return;
            }
            if (jiaszPath.equals("")) {
                ToastUtils.showShort("请上传驾驶证照片");
                return;
            }
            subjet = "0";
        }

        RequestParams params = new RequestParams(); // 默认编码UTF-8
        // 添加文本信息
        params.addQueryStringParameter("yfs_leasesq_ywtype", subjet);
        if (subjet.equals("0")) {
            params.addBodyParameter("yfs_leasesq_idpic", new File(
                    sfzzPath));
            params.addBodyParameter("yfs_leasesq_driverpica", new File(
                    jiaszPath));
            params.addBodyParameter("yfs_leasesq_driverpicb", new File(
                    sfzfPath));
        } else if (subjet.equals("1")) {
            params.addBodyParameter("yfs_leasesq_idpic", new File(
                    yingyPath));
        }

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000 * 1);// 设置当前网络吗请求的缓存超时时间
        http.configTimeout(1000 * 5);// 设置连接超时时间
        http.configSoTimeout(1000 * 5);// 设置连接超时时间
        http.send(HttpRequest.HttpMethod.POST, Constant.UPLOADLEASESQPIC,
                params, new RequestCallBack<String>() {
                    @Override
                    // 开始提交
                    public void onStart() {
                        ToastUtils.showShort("正在上传信息...");
                    }

                    @Override
                    // 正在提交
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        // if (isUploading) {
                        // ToastUtils.showShort("上传图片中... ");
                        // } else {
                        // ToastUtils.showShort("上传图片成功!");
                        // }
                    }

                    @Override
                    // 提交成功
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        Logger.getLogger().i("上传图片返回数据=" + result);
                        // if (subjet.equals("0")) {
                        // application.imagePathSFZ = "";
                        // application.imagePathJSZ = "";
                        // application.imagePathJSZF = "";
                        // shenfz_iv
                        // .setImageResource(R.drawable.identity_card_carema);
                        // jiasz_iv.setImageResource(R.drawable.driver_license);
                        // jiaszf_iv
                        // .setImageResource(R.drawable.driver_licensef);
                        // deletes_iv.setVisibility(View.GONE);
                        // deletej_iv.setVisibility(View.GONE);
                        // deletejf_iv.setVisibility(View.GONE);
                        // } else {
                        // application.imagePathQYZZ = "";
                        // qiyzz_iv.setImageResource(R.drawable.enterprise_qualification);
                        // deleteq_iv.setVisibility(View.GONE);
                        // }

                        CommonBean<ImagePathBean> commonBean = new Gson()
                                .fromJson(
                                        result,
                                        new TypeToken<CommonBean<ImagePathBean>>() {
                                        }.getType());
                        if (custom.equals("个人")) {
                            requreLeaseCars("", custom, custom_cet.getText()
                                            .toString(), mobile_cet.getText().toString(),
                                    id_cet.getText().toString(), "", "",
                                    "", faz_cet.getText().toString(), "申请中",
                                    "",
                                    commonBean.getData().getYfs_leasesq_idpic(),
                                    commonBean.getData()
                                            .getYfs_leasesq_driverpica(),
                                    commonBean.getData()
                                            .getYfs_leasesq_driverpicb(),
                                    yfs_leasesq_idtype,
                                    new Gson().toJson(addCarsBeans), "");
                        } else {
                            requreLeaseCars("", custom, dan_cet.getText()
                                            .toString(), mobile_cet.getText().toString(),
                                    id_cet.getText().toString(), "", "",
                                    "", faz_cet.getText().toString(), "申请中",
                                    "",
                                    commonBean.getData().getYfs_leasesq_idpic(),
                                    commonBean.getData()
                                            .getYfs_leasesq_driverpica(),
                                    commonBean.getData()
                                            .getYfs_leasesq_driverpicb(),
                                    yfs_leasesq_idtype,
                                    new Gson().toJson(addCarsBeans), custom_cet.getText().toString());
                        }
                    }

                    @Override
                    // 提交失败
                    public void onFailure(HttpException error, String msg) {
                        ToastUtils.showShort(error.getExceptionCode() + ":"
                                + msg);
                        Logger.getLogger().i(
                                error.getExceptionCode() + ":" + msg);
                    }
                });
    }
}
