package com.eroadcar.networkmanagement.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
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
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.ImagePathBean;
import com.eroadcar.networkmanagement.bean.OrderSalesBean;
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

public class ZhengxActivity extends BaseActivity implements OnClickListener {
    private Button back_btn, sure_btn;
    private TextView title_tv;

    private Spinner spinner, spinnerz;
    private TextView note_tv, notej_tv, zhengxin_tv, notey_tv;
    private ImageView sfz_iv, jzz_iv, yyzz_iv;

    private ClearEditText mobile_cet, id_cet, faz_cet, dan_cet, remark_cet, fzjg_cet;//custom_cet,

    private AutoCompleteTextView custom_cet;

    private LinearLayout dan_ll, fzjg_ll;

    private TextView che_tv;

    private ArrayList<String> customs, ids;

    private String custom = "个人", idi = "身份证", yfs_id = "", IDS = "";

    private String imagePath = "", jzzPath = "", yingyPath = "";

    private boolean hasGotToken = false;

    private static final int REQUEST_CODE_LICENSE_PLATE = 122,
            REQUEST_CODE_DRIVING_LICENSE = 121, REQUEST_CODE_VEHICLE_LICENSE = 120;
    private static final int REQUEST_CODE_BUSINESS_LICENSE = 123;
    private static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    private static final int REQUEST_CODE_PICK_IMAGE_BACK = 202;
    private static final int REQUEST_CODE_CAMERA = 102;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private static int REQUEST_PERMISSION_CODE = 1;


    private CommonBean<ArrayList<QianKeBean>> qianKeBeans;
    private CommonBean commonBean;

    private OrderSalesBean orderSalesBean;

    private QianKeBean qianKeBean;

    private String[] licenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhengxin);

        back_btn = (Button) findViewById(R.id.back_btn);
        sure_btn = (Button) findViewById(R.id.sure_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerz = (Spinner) findViewById(R.id.spinnerz);

        note_tv = (TextView) findViewById(R.id.note_tv);
        notej_tv = (TextView) findViewById(R.id.note2_tv);
        notey_tv = (TextView) findViewById(R.id.note3_tv);

        zhengxin_tv = (TextView) findViewById(R.id.zhengxin_tv);

        sfz_iv = (ImageView) findViewById(R.id.sfz_iv);
        jzz_iv = (ImageView) findViewById(R.id.jzz_iv);
        yyzz_iv = (ImageView) findViewById(R.id.yyzz_iv);

        custom_cet = (AutoCompleteTextView) findViewById(R.id.custom_cet);
//        custom_cet = (ClearEditText) findViewById(R.id.custom_cet);
        mobile_cet = (ClearEditText) findViewById(R.id.mobile_cet);
        id_cet = (ClearEditText) findViewById(R.id.id_cet);
        faz_cet = (ClearEditText) findViewById(R.id.faz_cet);
        dan_cet = (ClearEditText) findViewById(R.id.dan_cet);
        remark_cet = (ClearEditText) findViewById(R.id.remark_cet);

        fzjg_cet = (ClearEditText) findViewById(R.id.fzjg_cet);

        dan_ll = (LinearLayout) findViewById(R.id.dan_ll);
        fzjg_ll = (LinearLayout) findViewById(R.id.fzjg_ll);

        che_tv = (TextView) findViewById(R.id.che_tv);

        back_btn.setOnClickListener(this);
        sure_btn.setOnClickListener(this);

        sfz_iv.setOnClickListener(this);
        jzz_iv.setOnClickListener(this);
        yyzz_iv.setOnClickListener(this);

        che_tv.setOnClickListener(this);

        title_tv.setText("新增销售");
        back_btn.setText("");

        setSpinner();

        initAccessToken();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }

        try {
            orderSalesBean = (OrderSalesBean) getIntent().getSerializableExtra("BEAN");

            if (orderSalesBean != null && orderSalesBean.getYfs_sellsq_id() != null) {
                title_tv.setText("修改销售资料");
            }

            zhengxin_tv.setText("征信驳回：" + orderSalesBean.getYfs_sellsq_remark());
            zhengxin_tv.setVisibility(View.VISIBLE);

            custom_cet.setText(orderSalesBean.getYfs_sellsq_ownner());
            mobile_cet.setText(orderSalesBean.getYfs_sellsq_cellphone());
            id_cet.setText(orderSalesBean.getYfs_sellsq_id());
            faz_cet.setText(orderSalesBean.getYfs_sellsq_addr());
            remark_cet.setText(orderSalesBean.getYfs_sellsq_desc());

            yfs_id = orderSalesBean.getYfs_id();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            qianKeBean = (QianKeBean) getIntent().getSerializableExtra("QBEAN");

            custom_cet.setText(qianKeBean.getPtc_name());
            mobile_cet.setText(qianKeBean.getPtc_mobile());
        } catch (Exception e) {
            e.printStackTrace();
        }

        custom_cet.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

                getPotentialClientByName(custom_cet.getText().toString());

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

        custom_cet.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
//                owner_cet
//                        .setText(qianKeBeans.getData().get(arg2).getPtc_name());
                mobile_cet.setText(qianKeBeans.getData().get(arg2)
                        .getPtc_mobile());
                id_cet.setText(qianKeBeans.getData().get(arg2).getPtc_cardid());
                faz_cet.setText(qianKeBeans.getData().get(arg2)
                        .getPtc_addr());
            }
        });

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

//                        if (qianKeBeans.getData() != null && qianKeBeans.getData().size() != 0) {
//                            mobile_cet.setText(qianKeBeans.getData().get(0).getPtc_mobile());
//                        }

                        licenses = new String[qianKeBeans.getData().size()];

                        for (int i = 0; i < qianKeBeans.getData().size(); i++) {
                            licenses[i] = qianKeBeans.getData().get(i)
                                    .getPtc_name();
                        }
                        ArrayAdapter<String> av = new ArrayAdapter<String>(
                                ZhengxActivity.this, R.layout.view_textview,
                                licenses);
                        // ArrayAdapter adapter = new
                        // ArrayAdapter<String>(mMainActivity,
                        // android.R.layout.simple_dropdown_item_1line, licenses);

                        custom_cet.setAdapter(av);
                        av.notifyDataSetChanged();
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
            case R.id.che_tv:
                Intent intentc = new Intent(ZhengxActivity.this, CheActivity.class);
                intentc.putExtra("IDS", IDS);
                startActivityForResult(intentc, 2);
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

                if (id_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请输入客户证件号码");
                    return;
                }

                if (faz_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请输入客户住址");
                    return;
                }

                if (IDS.equals("")) {
                    ToastUtils.showShort("请选择预购车辆品牌");
                    return;
                }

                String ywtype = "";
                if (custom.equals("个人")) {
                    ywtype = "0";

                    if (imagePath.equals("")) {
                        ToastUtils.showShort("请上传身份证照片");
                        return;
                    }
                    boolean idcheck = personIdValidation(id_cet.getText().toString());

                    if (idcheck == false) {
                        ToastUtils.showShort("请输入正确的身份证号码");
                        return;
                    }
                } else if (custom.equals("单位")) {
                    ywtype = "1";
                    if (dan_cet.getText().toString().equals("")) {
                        ToastUtils.showShort("请输入单位名称");
                        return;
                    }

                    if (yingyPath.equals("")) {
                        ToastUtils.showShort("请上传营业执照照片");
                        return;
                    }
                    imagePath = yingyPath;
                } else {
                    ywtype = "3";

                    if (imagePath.equals("")) {
                        ToastUtils.showShort("请上传身份证照片");
                        return;
                    }
                    if (jzzPath.equals("")) {
                        ToastUtils.showShort("请上传居住证照片");
                        return;
                    }
                }

                reqZxInfo(ywtype, imagePath, jzzPath);

                break;
            case R.id.sfz_iv:
                if (idi.equals("驾驶证")) {
                    if (!checkTokenStatus()) {
                        return;
                    }
                    Intent intent = new Intent(ZhengxActivity.this, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, REQUEST_CODE_DRIVING_LICENSE);

                } else if (idi.equals("身份证")) {
                    Intent intent = new Intent(ZhengxActivity.this, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                }
                break;
            case R.id.jzz_iv:
//                Photograph();
                if (null != popupwindow && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                } else {
                    initmPopupWindowView_photo();
                    popupwindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                }
                break;
            case R.id.yyzz_iv:
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intenty = new Intent(ZhengxActivity.this, CameraActivity.class);
                intenty.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intenty.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intenty, REQUEST_CODE_BUSINESS_LICENSE);
                break;
            case R.id.btn_take_photo:// 拍照
                Photograph();
                break;
            case R.id.btn_pick_photo:// 从相册中选中
                choosePic();
                break;
            case R.id.btn_cancel:// 取消
                popupwindow.dismiss();
                break;
            default:
                break;
        }
    }

    private void reqZxInfo(String yfs_sellsq_ywtype, String yfs_sellsq_idtype, String yfs_sellsq_id,
                           String yfs_sellsq_person, String yfs_sellsq_cellphone, String yfs_sellsq_idpic,
                           String yfs_sellsq_jzpic, String yfs_sellsq_addr, String yfs_id,
                           String yfs_sellsq_contact, String yfs_precar_type, String yfs_sellsq_remark,
                           String yfs_sellsq_fzaddr) {
        loadingDialog.setMessage("正在提交信息...");
        loadingDialog.dialogShow();
        String url = Constant.REQZXINFO;
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

        //        params.addQueryStringParameter("yfs_id", yfs_id);
        map.put("yfs_sellsq_ywtype", yfs_sellsq_ywtype);
        map.put("yfs_sellsq_idtype", yfs_sellsq_idtype);
        map.put("yfs_sellsq_id", yfs_sellsq_id);
        map.put("yfs_sellsq_person", yfs_sellsq_person);
        map.put("yfs_sellsq_ownner", yfs_sellsq_person);
        map.put("yfs_sellsq_cellphone", yfs_sellsq_cellphone);
        map.put("yfs_sellsq_idpic", yfs_sellsq_idpic);
        map.put("yfs_sellsq_jzpic", yfs_sellsq_jzpic);
        map.put("yfs_sellsq_addr", yfs_sellsq_addr);
        map.put("yfs_id", yfs_id);
        map.put("yfs_sellsq_contact", yfs_sellsq_contact);
        map.put("yfs_precar_type", yfs_precar_type);
        map.put("yfs_sellsq_desc", yfs_sellsq_remark);
        map.put("yfs_sellsq_fzaddr", yfs_sellsq_fzaddr);


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

    private void reqZxInfo(final String yfs_sellsq_ywtype, String yfs_sellsq_idpic,
                           String yfs_sellsq_jzpic) {
        RequestParams params = new RequestParams(); // 默认编码UTF-8
        // 添加文本信息

        params.addQueryStringParameter("appcode", IMEI);
        params.addQueryStringParameter("apptype", Constant.APPTYPE);
        params.addQueryStringParameter("mg_id", application.userBean.getMg_id());
        params.addQueryStringParameter("mg_name", application.userBean.getMg_name());
        params.addQueryStringParameter("mg_shopsid", application.userBean.getMg_shopsid());
        params.addQueryStringParameter("mg_groupid", application.userBean.getMg_groupid());
        params.addQueryStringParameter("mg_shopname", application.userBean.getMg_shopname());
        params.addQueryStringParameter("mg_code", application.userBean.getMg_code());
        params.addQueryStringParameter("mg_groupname", application.userBean.getMg_groupname());
        params.addQueryStringParameter("mg_posid", application.userBean.getMg_posid());
        params.addQueryStringParameter("mg_posname", application.userBean.getMg_posname());
        params.addQueryStringParameter("mg_shopcode", application.userBean.getMg_shopcode());

//        params.addQueryStringParameter("yfs_id", yfs_id);
        params.addQueryStringParameter("yfs_sellsq_ywtype", yfs_sellsq_ywtype);
//        params.addQueryStringParameter("yfs_sellsq_idtype", yfs_sellsq_idtype);
//        params.addQueryStringParameter("yfs_sellsq_id", yfs_sellsq_id);
//        params.addQueryStringParameter("yfs_sellsq_person", yfs_sellsq_person);
//        params.addQueryStringParameter("yfs_sellsq_cellphone", yfs_sellsq_cellphone);

        if (yfs_sellsq_ywtype.equals("3")) {
            params.addBodyParameter("yfs_sellsq_jzpic", new File(yfs_sellsq_jzpic));
        }
        params.addBodyParameter("yfs_sellsq_idpic", new File(yfs_sellsq_idpic));


        HttpUtils http = new HttpUtils();

        http.send(HttpRequest.HttpMethod.POST, Constant.UPLOADSELLSQPIC,
                params, new RequestCallBack<String>() {
                    @Override
                    // 开始提交
                    public void onStart() {
                        ToastUtils.showShort("上传图片中...");
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
                        // ToastUtils.showShort(result);
                        if (result == null || result.equals("")) {
                            ToastUtils.showShort("图片上传失败，请稍后重试！");
                            return;
                        }

                        CommonBean<ImagePathBean> commonBean = new Gson()
                                .fromJson(
                                        result,
                                        new TypeToken<CommonBean<ImagePathBean>>() {
                                        }.getType());
                        Logger.getLogger().i("上传图片返回数据=" + result);

                        if (custom.equals("单位")) {
                            reqZxInfo(yfs_sellsq_ywtype, idi, id_cet.getText().toString(), dan_cet.getText().toString(),
                                    mobile_cet.getText().toString(),
                                    commonBean.getData().getYfs_sellsq_idpic(), commonBean.getData().getYfs_sellsq_jzpic(),
                                    faz_cet.getText().toString(), yfs_id, custom_cet.getText().toString(), IDS,
                                    remark_cet.getText().toString(), fzjg_cet.getText().toString());
                        } else {
                            reqZxInfo(yfs_sellsq_ywtype, idi, id_cet.getText().toString(), custom_cet.getText().toString(),
                                    mobile_cet.getText().toString(),
                                    commonBean.getData().getYfs_sellsq_idpic(), commonBean.getData().getYfs_sellsq_jzpic(),
                                    faz_cet.getText().toString(), yfs_id, "", IDS, remark_cet.getText().toString()
                                    , fzjg_cet.getText().toString());
                        }
                    }

                    @Override
                    // 提交失败
                    public void onFailure(HttpException error, String msg) {
                        ToastUtils.showShort(error.getExceptionCode() + ":"
                                + msg);
                    }
                });
    }

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
//        customs.add(0, "选择客户类型");
        customs.add(0, "个人");
        customs.add(1, "个人(非本地)");
        customs.add(2, "单位");
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(
                ZhengxActivity.this, R.layout.view_textview, customs);
        // arr_adapter
        // .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arr_adapter);
        spinner.setSelection(0, true);

        ids = new ArrayList<String>();
//        ids.add(0, "选择证件类型");
        ids.add(0, "身份证");
        ids.add(1, "驾驶证");
//        ids.add(2, "营业执照");
        ArrayAdapter<String> arrz_adapter = new ArrayAdapter<String>(
                ZhengxActivity.this, R.layout.view_textview, ids);
        // arr_adapter
        // .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerz.setAdapter(arrz_adapter);
        spinnerz.setSelection(0, true);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                custom = customs.get(position);

                if (custom.equals("个人")) {
                    notej_tv.setVisibility(View.GONE);
                    jzz_iv.setVisibility(View.GONE);

                    note_tv.setVisibility(View.VISIBLE);
                    sfz_iv.setVisibility(View.VISIBLE);

                    notey_tv.setVisibility(View.GONE);
                    yyzz_iv.setVisibility(View.GONE);

                    dan_ll.setVisibility(View.GONE);


                    ids.clear();
                    ids.add(0, "身份证");
                    ids.add(1, "驾驶证");
                    ArrayAdapter<String> arrz_adapter = new ArrayAdapter<String>(
                            ZhengxActivity.this, R.layout.view_textview, ids);
                    spinnerz.setAdapter(arrz_adapter);
                    spinnerz.setSelection(0, true);
                    spinnerz.setSelected(true);
                    spinnerz.setEnabled(true);
                } else if (custom.equals("单位")) {
                    notej_tv.setVisibility(View.GONE);
                    jzz_iv.setVisibility(View.GONE);

                    note_tv.setVisibility(View.GONE);
                    sfz_iv.setVisibility(View.GONE);

                    notey_tv.setVisibility(View.VISIBLE);
                    yyzz_iv.setVisibility(View.VISIBLE);

                    dan_ll.setVisibility(View.VISIBLE);

//                    spinnerz.setSelection(2, true);
                    ids.clear();
                    ids.add(0, "营业执照");
                    ArrayAdapter<String> arrz_adapter = new ArrayAdapter<String>(
                            ZhengxActivity.this, R.layout.view_textview, ids);
                    spinnerz.setAdapter(arrz_adapter);
                    spinnerz.setSelection(0, true);
                    spinnerz.setSelected(false);
                    spinnerz.setEnabled(false);
                } else {
                    notej_tv.setVisibility(View.VISIBLE);
                    jzz_iv.setVisibility(View.VISIBLE);

                    note_tv.setVisibility(View.VISIBLE);
                    sfz_iv.setVisibility(View.VISIBLE);

                    notey_tv.setVisibility(View.GONE);
                    yyzz_iv.setVisibility(View.GONE);

                    dan_ll.setVisibility(View.GONE);

                    ids.clear();
                    ids.add(0, "身份证");
                    ids.add(1, "驾驶证");
                    ArrayAdapter<String> arrz_adapter = new ArrayAdapter<String>(
                            ZhengxActivity.this, R.layout.view_textview, ids);
                    spinnerz.setAdapter(arrz_adapter);
                    spinnerz.setSelection(0, true);
                    spinnerz.setSelected(true);
                    spinnerz.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        spinnerz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                idi = ids.get(position);

                if (idi.equals("身份证")) {
                    note_tv.setText("请拍摄身份证正面提取信息");
                    fzjg_ll.setVisibility(View.GONE);
                } else {
                    note_tv.setText("请拍摄驾驶证提取信息");
                    View view1 = view;
                    fzjg_ll.setVisibility(View.VISIBLE);
                }

                imagePath = "";
                jzzPath = "";
                yingyPath = "";
                sfz_iv.setImageResource(R.mipmap.icon_camrea_sfz);
                jzz_iv.setImageResource(R.mipmap.icon_camrea_sfz);
                yyzz_iv.setImageResource(R.mipmap.icon_camrea_sfz);
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
            imagePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();

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

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
                imagePath = filePath;
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                    }
                }
            }
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

        if (requestCode == 1 && resultCode == RESULT_OK) {// 拍照
            try {
                int degree = Tool.readPictureDegree(jzzPath);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeFile(jzzPath, options);
                bitmap = Tool.rotaingImageView(degree, bitmap);

                jzz_iv.setImageBitmap(bitmap);

//                Tool.saveImage(Tool.ratio(bitmap, 1000, 600), jzzPath);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (null != popupwindow && popupwindow.isShowing()) {
                popupwindow.dismiss();
            }
        }

        if (requestCode == 2) {
            try {
                IDS = data.getStringExtra("IDS");
                IDS = IDS.substring(0, IDS.length() - 1);
                che_tv.setText(IDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 3 && resultCode == RESULT_OK) {
            try {
                Uri selectedImage = geturi(data);// data.getData(); //
                // 获取系统返回的照片的Uri
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);// 从系统表中查询指定Uri对应的照片
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex); // 获取照片路径
                cursor.close();
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                File imageFile = new File(
                        Environment.getExternalStorageDirectory(),
                        "jzz.jpg");
                jzzPath = imageFile.getPath();

                jzz_iv.setImageBitmap(bitmap);

//                Tool.saveImage(Tool.ratio(bitmap, 1000, 600), jzzPath);
            } catch (Exception e) {
                // TODO Auto-generatedcatch block
                e.printStackTrace();
            }

            if (null != popupwindow && popupwindow.isShowing()) {
                popupwindow.dismiss();
            }
        }
    }

    private void recIDCard(String idCardSide, String filePath) {
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
//                    ToastUtils.showShort(result.toString());
                        setInterface(result);
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

        sfz_iv.setImageBitmap(getLoacalBitmap(imagePath));

        getPotentialClientByName(result.getName().toString());
    }

    private void setInterface(String result) {
        try {
            JSONObject object = new JSONObject(result);
            JSONObject object1 = object.getJSONObject("words_result");
            JSONObject object2 = object1.getJSONObject("证号");
            String id = object2.getString("words");

            JSONObject object3 = object1.getJSONObject("住址");
            String address = object3.getString("words");

            JSONObject object4 = object1.getJSONObject("姓名");
            String name = object4.getString("words");

            custom_cet.setText(name);
            id_cet.setText(id);
            faz_cet.setText(address);

            sfz_iv.setImageBitmap(getLoacalBitmap(imagePath));
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

            yyzz_iv.setImageBitmap(getLoacalBitmap(yingyPath));
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

    private void Photograph() {// 系统相机拍照
        try {
            File imageFile = new File(
                    Environment.getExternalStorageDirectory(), "juzhuzheng.jpg");// 通过路径创建保存文件
            jzzPath = imageFile.getPath();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivityForResult(intent, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void choosePic() {// 相册选择图片
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 3);
    }

    /**
     * 解决小米手机上获取图片路径为null的情况
     *
     * @param intent
     * @return
     */
    public Uri geturi(android.content.Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    private View customView;
    private PopupWindow popupwindow;
    private Button btn_take_photo, btn_pick_photo, btn_cancel;

    // 照相弹出层
    public void initmPopupWindowView_photo() {
        // 获取自定义布局文件pop.xml的视图
        customView = getLayoutInflater().inflate(R.layout.pop_photo_choice,
                null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindow = new PopupWindow(customView,
                LinearLayout.LayoutParams.FILL_PARENT, getResources()
                .getDimensionPixelSize(R.dimen.hundredt), true);
        // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
        popupwindow.setAnimationStyle(R.style.AnimationPhoto);
        // 获取控件
        btn_take_photo = (Button) customView.findViewById(R.id.btn_take_photo);// 拍照
        btn_pick_photo = (Button) customView.findViewById(R.id.btn_pick_photo);// 选中相册
        btn_cancel = (Button) customView.findViewById(R.id.btn_cancel);// 取消
        btn_cancel.setOnClickListener(this);
        btn_pick_photo.setOnClickListener(this);
        btn_take_photo.setOnClickListener(this);

        backgroundAlpha(0.5f);
        popupwindow.setOnDismissListener(new poponDismissListener());

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupwindow.setBackgroundDrawable(new BitmapDrawable());
        // 使其聚集
        popupwindow.setFocusable(true);
        popupwindow.setTouchable(true); // 设置PopupWindow可触摸
        // 设置允许在外点击消失
        popupwindow.setOutsideTouchable(true);
        // 刷新状态
        popupwindow.update();
    }
}
