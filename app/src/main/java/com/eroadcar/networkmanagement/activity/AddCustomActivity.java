package com.eroadcar.networkmanagement.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
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
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DigitalDialogInput;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.CommonBean;
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
import java.util.Map;

public class AddCustomActivity extends BaseActivity implements OnClickListener {
    private Button back_btn, sure_btn;
    private TextView title_tv;

    private Spinner spinner, spinnerz, spinnercx;

    private ClearEditText custom_cet, mobile_cet, rem_cet;

    private RadioGroup subjects_rg;

    private ImageView yuyin_iv;


    private ArrayList<String> customClasss, customTypes, chexings;

    private String customClass = "A", customType = "意向", chexing = "奇瑞小蚂蚁", sex = "男";

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
    private static int REQUEST_PERMISSION_CODE = 1;

    private CommonBean commonBean;

    private QianKeBean qianKeBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom);

        back_btn = (Button) findViewById(R.id.back_btn);
        sure_btn = (Button) findViewById(R.id.sure_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerz = (Spinner) findViewById(R.id.spinnerz);
        spinnercx = (Spinner) findViewById(R.id.spinnercx);

        custom_cet = (ClearEditText) findViewById(R.id.custom_cet);
        mobile_cet = (ClearEditText) findViewById(R.id.mobile_cet);
        rem_cet = (ClearEditText) findViewById(R.id.rem_cet);

        subjects_rg = (RadioGroup) findViewById(R.id.subjects_rg);

        yuyin_iv = (ImageView) findViewById(R.id.yuyin_iv);

        back_btn.setOnClickListener(this);
        sure_btn.setOnClickListener(this);
        yuyin_iv.setOnClickListener(this);

        title_tv.setText("新增客户");
        back_btn.setText("");

        setSpinner();

        subjects_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.man_rb) {
                    sex = "男";
                } else {
                    sex = "女";
                }
            }
        });

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }

        try {
            qianKeBean = (QianKeBean) getIntent().getSerializableExtra("BEAN");

            if (qianKeBean != null && qianKeBean.getPtc_id() != null) {
                title_tv.setText("修改客户资料");
            }
            setInterface();
        } catch (Exception e) {
            e.printStackTrace();
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


    Handler mHandler = new Handler() {// 18672250286
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (commonBean.getState().equals("success")) {
                        onBackPressed();
                    }
                    ToastUtils.showShort(commonBean.getMsg());
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
                if (qianKeBean != null && qianKeBean.getPtc_id() != null) {
                    newCustomer(custom_cet.getText().toString(), mobile_cet.getText().toString(),
                            sex, customClass, customType, chexing, rem_cet.getText().toString(), qianKeBean.getPtc_id());
                } else {
                    newCustomer(custom_cet.getText().toString(), mobile_cet.getText().toString(),
                            sex, customClass, customType, chexing, rem_cet.getText().toString(), "");
                }

                break;

            default:
                break;
        }
    }

    private void newCustomer(String ptc_name, String ptc_mobile, String ptc_sex,
                             String ptc_level, String ptc_client_type, String ptc_yx_cartype,
                             String ptc_remark, String ptc_id) {
        loadingDialog.setMessage("正在提交信息...");
        loadingDialog.dialogShow();
        String url = Constant.NEWCUSTOMERO;
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

        map.put("ptc_shopid", application.userBean.getMg_shopsid());
        map.put("ptc_shopcode", application.userBean.getMg_shopcode());
        map.put("ptc_shopname", application.userBean.getMg_shopname());

        map.put("ptc_name", ptc_name);
        map.put("ptc_mobile", ptc_mobile);
        map.put("ptc_sex", ptc_sex);
        map.put("ptc_level", ptc_level);
        map.put("ptc_client_type", ptc_client_type);
        map.put("ptc_yx_cartype", ptc_yx_cartype);
        map.put("ptc_remark", ptc_remark);
        map.put("ptc_id", ptc_id);


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
                    }

                    @Override
                    // 提交失败
                    public void onFailure(HttpException error, String msg) {
                        ToastUtils.showShort(error.getExceptionCode() + ":"
                                + msg);
                    }
                });
    }


    private void setSpinner() {

        customTypes = new ArrayList<String>();
        customTypes.add(0, "意向");
        customTypes.add(1, "认购");
        customTypes.add(2, "签约");
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(
                AddCustomActivity.this, R.layout.view_textview, customTypes);
        spinner.setAdapter(arr_adapter);
        spinner.setSelection(0, true);

        customClasss = new ArrayList<String>();
        customClasss.add(0, "O");
        customClasss.add(1, "F");
        customClasss.add(2, "A");
        customClasss.add(3, "B");
        customClasss.add(4, "C");
        ArrayAdapter<String> arrz_adapter = new ArrayAdapter<String>(
                AddCustomActivity.this, R.layout.view_textview, customClasss);
        spinnerz.setAdapter(arrz_adapter);
        spinnerz.setSelection(0, true);

        chexings = new ArrayList<String>();
        chexings.add(0, "奇瑞小蚂蚁");
        chexings.add(1, "奇瑞eQ");
        chexings.add(2, "奇瑞瑞虎3Xe");
        chexings.add(3, "奇瑞艾瑞泽5e");
        chexings.add(4, "荣威e950");
        chexings.add(5, "荣威ei6");
        chexings.add(6, "荣威erx5");
        ArrayAdapter<String> cxarrz_adapter = new ArrayAdapter<String>(
                AddCustomActivity.this, R.layout.view_textview, chexings);
        spinnercx.setAdapter(cxarrz_adapter);
        spinnercx.setSelection(0, true);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                customType = customTypes.get(position);
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
                customClass = customClasss.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        spinnercx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                chexing = chexings.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void setInterface() {
        try {
            custom_cet.setText(qianKeBean.getPtc_name());
            mobile_cet.setText(qianKeBean.getPtc_mobile());
            rem_cet.setText(qianKeBean.getPtc_remark());

            if (qianKeBean.getPtc_sex() != null && qianKeBean.getPtc_sex().equals("女")) {
                ((RadioButton) findViewById(R.id.women_rb)).setChecked(true);
            } else {
                ((RadioButton) findViewById(R.id.man_rb)).setChecked(true);
            }

            for (int i = 0; i < customClasss.size(); i++) {
                if (customClasss.get(i).equals(qianKeBean.getPtc_level())) {
                    spinnerz.setSelection(i, true);
                    break;
                }
            }

            for (int i = 0; i < customTypes.size(); i++) {
                if (customTypes.get(i).equals(qianKeBean.getPtc_client_type())) {
                    spinner.setSelection(i, true);
                    break;
                }
            }

            for (int i = 0; i < chexings.size(); i++) {
                if (chexings.get(i).equals(qianKeBean.getPtc_yx_cartype())) {
                    spinnercx.setSelection(i, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

                rem_cet.setText(results.get(0) + "");
            } else {
                message += "没有结果";
            }

            MyLogger.info(message);
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
