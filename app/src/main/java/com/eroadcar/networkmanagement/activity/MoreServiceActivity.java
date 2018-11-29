package com.eroadcar.networkmanagement.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.MoreAdapter;
import com.eroadcar.networkmanagement.bean.HomeBean;
import com.eroadcar.networkmanagement.utils.FileUtil;
import com.eroadcar.networkmanagement.utils.PrintUtil;
import com.eroadcar.networkmanagement.utils.RecognizeService;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by amos on 2018/7/28.
 */

public class MoreServiceActivity extends BaseActivity implements View.OnClickListener {
    private Button back_btn;
    private TextView title_tv;

    private GridView img_gv;

    private ArrayList<HomeBean> homeBeans;

    private boolean hasGotToken = false;

    private static final int REQUEST_CODE_LICENSE_PLATE = 122,
            REQUEST_CODE_DRIVING_LICENSE = 121, REQUEST_CODE_VEHICLE_LICENSE = 120;
    private static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    private static final int REQUEST_CODE_PICK_IMAGE_BACK = 202;
    private static final int REQUEST_CODE_CAMERA = 102;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_service);

        title_tv = (TextView) findViewById(R.id.title_tv);
        back_btn = (Button) findViewById(R.id.back_btn);

        img_gv = (GridView) findViewById(R.id.img_gv);

        title_tv.setText(R.string.tool);

        back_btn.setOnClickListener(this);

        setHomeBean();
        img_gv.setAdapter(new MoreAdapter(MoreServiceActivity.this, homeBeans));

        img_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setScan(i);
            }
        });

//        PrintUtil printUtil = new PrintUtil(MoreServiceActivity.this,
//                "/storage/emulated/0/Pictures/Screenshots/Screenshot_2018-09-25-17-57-36.png");

        initAccessToken();
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    break;
                case -1:
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;

        }
    }

    private void setHomeBean() {
        homeBeans = new ArrayList<>();

        HomeBean bean = new HomeBean();
        bean.setImageId(R.mipmap.icon_more_sfzz);
        bean.setContent("身份证(正)");
        bean.setNewsnum(1);
        bean.setType("sfzz");

        HomeBean bean1 = new HomeBean();
        bean1.setImageId(R.mipmap.icon_more_sfzf);
        bean1.setContent("身份证(反)");
        bean1.setNewsnum(0);
        bean1.setType("sfzf");

        HomeBean bean3 = new HomeBean();
        bean3.setImageId(R.mipmap.icon_more_chep);
        bean3.setContent("车牌");
        bean3.setNewsnum(0);
        bean3.setType("cp");

        HomeBean bean2 = new HomeBean();
        bean2.setImageId(R.mipmap.icon_more_jsz);
        bean2.setContent("驾驶证");
        bean2.setNewsnum(0);
        bean2.setType("jsz");

        HomeBean bean4 = new HomeBean();
        bean4.setImageId(R.mipmap.icon_more_xsz);
        bean4.setContent("行驶证");
        bean4.setNewsnum(0);
        bean4.setType("xsz");


        homeBeans.add(bean);
        homeBeans.add(bean1);
        homeBeans.add(bean3);
        homeBeans.add(bean2);
        homeBeans.add(bean4);
    }

    private void setScan(int i) {
        if (homeBeans.get(i).getType().equals("cp")) {
            if (!checkTokenStatus()) {
                return;
            }
            Intent intent = new Intent(MoreServiceActivity.this, CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                    FileUtil.getSaveFile(getApplication()).getAbsolutePath());
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                    CameraActivity.CONTENT_TYPE_GENERAL);
            startActivityForResult(intent, REQUEST_CODE_LICENSE_PLATE);
        } else if (homeBeans.get(i).getType().equals("jsz")) {
            if (!checkTokenStatus()) {
                return;
            }
            Intent intent = new Intent(MoreServiceActivity.this, CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                    FileUtil.getSaveFile(getApplication()).getAbsolutePath());
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                    CameraActivity.CONTENT_TYPE_GENERAL);
            startActivityForResult(intent, REQUEST_CODE_DRIVING_LICENSE);

        } else if (homeBeans.get(i).getType().equals("sfzz")) {
            Intent intent = new Intent(MoreServiceActivity.this, CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                    FileUtil.getSaveFile(getApplication()).getAbsolutePath());
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        } else if (homeBeans.get(i).getType().equals("sfzf")) {
            Intent intent = new Intent(MoreServiceActivity.this, CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                    FileUtil.getSaveFile(getApplication()).getAbsolutePath());
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);

        } else if (homeBeans.get(i).getType().equals("xsz")) {
            if (!checkTokenStatus()) {
                return;
            }
            Intent intent = new Intent(MoreServiceActivity.this, CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                    FileUtil.getSaveFile(getApplication()).getAbsolutePath());
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                    CameraActivity.CONTENT_TYPE_GENERAL);
            startActivityForResult(intent, REQUEST_CODE_VEHICLE_LICENSE);
        }
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
//                            ToastUtils.showShort(result);

                            try {
                                JSONObject object = new JSONObject(result);
                                JSONObject object1 = object.getJSONObject("words_result");
//                                JSONObject object2 = object1.getJSONObject("number");
                                final String number = object1.getString("number");

                                showDialogMessage(MoreServiceActivity.this, "识别结果", "车牌号：" + number, "复制文字", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        copy("车牌号：" + number);

                                        ToastUtils.showShort("已复制到剪切板");
                                        dialogMessage.dismiss();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();

                                ToastUtils.showShort("无法识别，请重新拍照");
                            }
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
//                            ToastUtils.showShort(result);

                            try {
                                JSONObject object = new JSONObject(result);
                                JSONObject object1 = object.getJSONObject("words_result");
                                JSONObject object2 = object1.getJSONObject("号牌号码");
                                JSONObject object3 = object1.getJSONObject("所有人");
                                JSONObject object4 = object1.getJSONObject("品牌型号");
                                JSONObject object5 = object1.getJSONObject("住址");
                                JSONObject object6 = object1.getJSONObject("使用性质");
                                JSONObject object7 = object1.getJSONObject("车辆类型");
                                JSONObject object8 = object1.getJSONObject("发动机号码");
                                JSONObject object9 = object1.getJSONObject("车辆识别代号");
                                JSONObject object10 = object1.getJSONObject("注册日期");
                                JSONObject object11 = object1.getJSONObject("发证日期");
                                final String number = "号牌号码：" + object2.getString("words") +
                                        "\n所有人：" + object3.getString("words") +
                                        "\n住址：" + object5.getString("words") +
                                        "\n品牌型号：" + object4.getString("words") +
                                        "\n使用性质：" + object6.getString("words") +
                                        "\n车辆类型：" + object7.getString("words") +
                                        "\n发动机号码：" + object8.getString("words") +
                                        "\n车辆识别代号：" + object9.getString("words") +
                                        "\n注册日期：" + object10.getString("words") +
                                        "\n发证日期：" + object11.getString("words");

                                showDialogMessage(MoreServiceActivity.this, "识别结果", number, "复制文字", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        copy(number);

                                        ToastUtils.showShort("已复制到剪切板");
                                        dialogMessage.dismiss();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                ToastUtils.showShort("无法识别，请重新拍照");
                            }
                        }
                    });
        }

        // 识别成功回调，驾驶证识别
        if (requestCode == REQUEST_CODE_DRIVING_LICENSE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recDrivingLicense(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            System.out.println("识别返回数据＝" + result);
//                            ToastUtils.showShort(result);

                            try {
                                JSONObject object = new JSONObject(result);
                                JSONObject object1 = object.getJSONObject("words_result");
                                JSONObject object2 = object1.getJSONObject("证号");
                                JSONObject object3 = object1.getJSONObject("姓名");
                                JSONObject object4 = object1.getJSONObject("住址");
                                JSONObject object5 = object1.getJSONObject("性别");
                                JSONObject object6 = object1.getJSONObject("准驾车型");
                                JSONObject object7 = object1.getJSONObject("初次领证日期");
                                JSONObject object8 = object1.getJSONObject("有效期限");
                                final String number = "证号：" + object2.getString("words") +
                                        "\n姓名：" + object3.getString("words") +
                                        "\n性别：" + object5.getString("words") +
                                        "\n住址：" + object4.getString("words") +
                                        "\n准驾车型：" + object6.getString("words") +
                                        "\n初次领证日期：" + object7.getString("words") +
                                        "\n有效期限：" + object8.getString("words");

                                showDialogMessage(MoreServiceActivity.this, "识别结果", number, "复制文字", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        copy(number);

                                        ToastUtils.showShort("已复制到剪切板");
                                        dialogMessage.dismiss();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                ToastUtils.showShort("无法识别，请重新拍照");
                            }
                        }
                    });
        }

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                    }
                }
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
                if (result != null) {
                    System.out.println("识别返回数据＝" + result.toString());
//                    ToastUtils.showShort(result.toString());
                    try {
                        String s = "";
                        if (result.getIdCardSide().equals("front")) {
                            s = "姓名：" + result.getName().toString() + "\n身份证号：" + result.getIdNumber().toString() + "\n性别："
                                    + result.getGender().toString() + "\n地址：" + result.getAddress().toString();
                        } else {
                            s = "签发机关：" + result.getIssueAuthority() + "\n有效期：" + result.getSignDate() + "-"
                                    + result.getExpiryDate();
                        }
                        final String copy = s;

                        showDialogMessage(MoreServiceActivity.this, "识别结果", s, "复制文字", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                copy(copy);

                                ToastUtils.showShort("已复制到剪切板");
                                dialogMessage.dismiss();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtils.showShort("无法识别，请重新拍照");
                    }
                }
            }

            @Override
            public void onError(OCRError error) {
                System.out.println("识别返回数据＝" + error.getMessage());
                ToastUtils.showShort(error.getMessage());
            }
        });
    }
}
