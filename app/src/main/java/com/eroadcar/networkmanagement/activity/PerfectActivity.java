package com.eroadcar.networkmanagement.activity;

import android.Manifest;
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
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.CarBean;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.ImagePathBean;
import com.eroadcar.networkmanagement.bean.OrderSalesBean;
import com.eroadcar.networkmanagement.bean.QianKeBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.Logger;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.utils.Tool;
import com.eroadcar.networkmanagement.view.ClearEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class PerfectActivity extends BaseActivity implements OnClickListener {
    private Button back_btn, sure_btn;
    private TextView title_tv;

    private ImageView sfzz_iv, sfzf_iv, fap_iv, chanz_iv, xsz_iv;

    private ImageView camrea_iv;

    //    private ClearEditText custom_cet;
    private AutoCompleteTextView owner_cet;

    private LinearLayout img_ll, text_ll;

    private String sfzzPath = "", sfzfPath = "", fapPath = "", chanzPath = "", xszPath = "", takePhotoType = "";

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private static int REQUEST_PERMISSION_CODE = 1;

    private CommonBean commonBean;

    private OrderSalesBean orderSalesBean;


    private String[] licenses;
    private CommonBean<ArrayList<CarBean>> qianKeBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfact);

        back_btn = (Button) findViewById(R.id.back_btn);
        sure_btn = (Button) findViewById(R.id.sure_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);

        sfzz_iv = (ImageView) findViewById(R.id.sfzz_iv);
        sfzf_iv = (ImageView) findViewById(R.id.sfzf_iv);
        fap_iv = (ImageView) findViewById(R.id.fap_iv);
        chanz_iv = (ImageView) findViewById(R.id.chanz_iv);
        xsz_iv = (ImageView) findViewById(R.id.xsz_iv);

        camrea_iv = (ImageView) findViewById(R.id.camrea_iv);

//        custom_cet = (ClearEditText) findViewById(R.id.custom_cet);
        owner_cet = (AutoCompleteTextView) findViewById(R.id.owner_cet);

        img_ll = (LinearLayout) findViewById(R.id.img_ll);
        text_ll = (LinearLayout) findViewById(R.id.text_ll);

        back_btn.setOnClickListener(this);
        sure_btn.setOnClickListener(this);

        sfzz_iv.setOnClickListener(this);
        sfzf_iv.setOnClickListener(this);
        fap_iv.setOnClickListener(this);
        chanz_iv.setOnClickListener(this);
        xsz_iv.setOnClickListener(this);

        camrea_iv.setOnClickListener(this);

        title_tv.setText("客户信息");
        back_btn.setText("");

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

        orderSalesBean = (OrderSalesBean) getIntent().getSerializableExtra("BEAN");

        if (orderSalesBean.getYfs_sellsq_ywtype().equals("2") || orderSalesBean.getYfs_sellsq_ywtype().equals("1")) {
            img_ll.setVisibility(View.GONE);
            text_ll.setVisibility(View.GONE);
        }


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
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

                        licenses = new String[qianKeBeans.getData().size()];

                        for (int i = 0; i < qianKeBeans.getData().size(); i++) {
                            licenses[i] = qianKeBeans.getData().get(i)
                                    .getYck_car_vin();
                        }
                        ArrayAdapter<String> av = new ArrayAdapter<String>(
                                PerfectActivity.this, R.layout.view_textview,
                                licenses);
                        // ArrayAdapter adapter = new
                        // ArrayAdapter<String>(mMainActivity,
                        // android.R.layout.simple_dropdown_item_1line, licenses);

                        owner_cet.setAdapter(av);
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
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.camrea_iv:
                onScanBarcode();
                break;
            case R.id.sure_btn:
//                if (sfzzPath.equals("")) {
//                    ToastUtils.showShort("请拍摄或选择身份证正面照片");
//                    return;
//                }
//                if (sfzfPath.equals("")) {
//                    ToastUtils.showShort("请拍摄或选择身份证反面照片");
//                    return;
//                }
                if (fapPath.equals("")) {
                    ToastUtils.showShort("请拍摄或选择发票照片");
                    return;
                }
                if (chanzPath.equals("")) {
                    ToastUtils.showShort("请拍摄或选择产证照片");
                    return;
                }
                if (xszPath.equals("")) {
                    ToastUtils.showShort("请拍摄或选择行驶证照片");
                    return;
                }

//                if (custom_cet.getText().toString().equals("") || custom_cet.getText().toString().length() <= 4) {
//                    ToastUtils.showShort("请输入车架号");
//                    return;
//                }
                if (owner_cet.getText().toString().equals("") || owner_cet.getText().toString().length() <= 4) {
                    ToastUtils.showShort("请输入车架号");
                    return;
                }

                uploadSellPics(sfzzPath, sfzfPath, fapPath, xszPath, chanzPath);

                break;
            case R.id.sfzz_iv:
                setPopup("sfzz", v);
                break;
            case R.id.sfzf_iv:
                setPopup("sfzf", v);
                break;
            case R.id.fap_iv:
                setPopup("fap", v);
                break;
            case R.id.chanz_iv:
                setPopup("chanz", v);
                break;
            case R.id.xsz_iv:
                setPopup("xsz", v);
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

    private void setPopup(String s, View v) {
        takePhotoType = s;
        if (null != popupwindow && popupwindow.isShowing()) {
            popupwindow.dismiss();
        } else {
            initmPopupWindowView_photo();
            popupwindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
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

        GsonRequest<CommonBean<ArrayList<CarBean>>> requtst = new GsonRequest<CommonBean<ArrayList<CarBean>>>(
                Request.Method.POST, url, listener_getPotentialClientByName);
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

    private void fillSellData(String yfs_car_vin, String yfs_client_id_pic, String yfs_client_id_other_pic,
                              String yfs_car_fapiao_pic, String yfs_car_xingshi_apic, String yfs_car_chanzheng_apic) {
        loadingDialog.setMessage("正在提交信息...");
        loadingDialog.dialogShow();
        String url = Constant.FILLSELLDATA;
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


        map.put("yfs_id", orderSalesBean.getYfs_id());
        map.put("yfs_sellsq_code", orderSalesBean.getYfs_sellsq_code());

        map.put("yfs_car_vin", yfs_car_vin);
        map.put("yfs_client_id_pic", yfs_client_id_pic);
        map.put("yfs_client_id_other_pic", yfs_client_id_other_pic);
        map.put("yfs_car_fapiao_pic", yfs_car_fapiao_pic);
        map.put("yfs_car_xingshi_apic", yfs_car_xingshi_apic);
        map.put("yfs_car_chanzheng_apic", yfs_car_chanzheng_apic);


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

    private void uploadSellPics(String yfs_client_id_pic, String yfs_client_id_other_pic,
                                String yfs_car_fapiao_pic, String yfs_car_xingshi_apic, String yfs_car_chanzheng_apic) {
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

        params.addBodyParameter("yfs_client_id_pic", new File(yfs_client_id_pic));
        params.addBodyParameter("yfs_client_id_other_pic", new File(yfs_client_id_other_pic));
        params.addBodyParameter("yfs_car_fapiao_pic", new File(yfs_car_fapiao_pic));
        params.addBodyParameter("yfs_car_xingshi_apic", new File(yfs_car_xingshi_apic));
        params.addBodyParameter("yfs_car_chanzheng_apic", new File(yfs_car_chanzheng_apic));


        HttpUtils http = new HttpUtils();

        http.send(HttpRequest.HttpMethod.POST, Constant.UPLOADSELLPICS,
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

                        if (commonBean != null && commonBean.getState() != null && commonBean.getState().equals("success")) {
                            fillSellData(owner_cet.getText().toString(), commonBean.getData().getYfs_client_id_pic(),
                                    commonBean.getData().getYfs_client_id_other_pic(), commonBean.getData().getYfs_car_fapiao_pic(),
                                    commonBean.getData().getYfs_car_xingshi_apic(), commonBean.getData().getYfs_car_chanzheng_apic());
                        } else {
                            ToastUtils.showShort("上传图片失败，请稍后再试");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {// 拍照
            try {
                int degree = Tool.readPictureDegree(getImagePath());
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeFile(getImagePath(), options);
                bitmap = Tool.rotaingImageView(degree, bitmap);

                setImageView(bitmap);

                Tool.saveImage(Tool.ratio(bitmap, 1000, 600), getImagePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == 2) {
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
                        takePhotoType + ".jpg");
                String p = imageFile.getPath();

                setImagePath(p);

                setImageView(bitmap);

                Tool.saveImage(Tool.ratio(bitmap, 1000, 600), p);
            } catch (Exception e) {
                // TODO Auto-generatedcatch block
                e.printStackTrace();
            }
        } else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "扫码取消！", Toast.LENGTH_LONG).show();
                } else {
//                    Toast.makeText(this, "扫描成功，条码值: " + result.getContents(), Toast.LENGTH_LONG).show();
                    owner_cet.setText(result.getContents());
                }
            }
        }
        if (null != popupwindow && popupwindow.isShowing()) {
            popupwindow.dismiss();
        }
    }

    private void Photograph() {// 系统相机拍照
        try {
            File imageFile = new File(
                    Environment.getExternalStorageDirectory(), takePhotoType + ".jpg");// 通过路径创建保存文件
            String p = imageFile.getPath();
            setImagePath(p);
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
        startActivityForResult(intent, 2);
    }

    private void setImagePath(String p) {
        if (takePhotoType.equals("sfzz")) {
            sfzzPath = p;
        } else if (takePhotoType.equals("sfzf")) {
            sfzfPath = p;
        } else if (takePhotoType.equals("fap")) {
            fapPath = p;
        } else if (takePhotoType.equals("chanz")) {
            chanzPath = p;
        } else if (takePhotoType.equals("xsz")) {
            xszPath = p;
        }
    }

    private String getImagePath() {
        String p = "";
        if (takePhotoType.equals("sfzz")) {
            p = sfzzPath;
        } else if (takePhotoType.equals("sfzf")) {
            p = sfzfPath;
        } else if (takePhotoType.equals("fap")) {
            p = fapPath;
        } else if (takePhotoType.equals("chanz")) {
            p = chanzPath;
        } else if (takePhotoType.equals("xsz")) {
            p = xszPath;
        }

        return p;
    }

    private void setImageView(Bitmap bitmap) {
        if (takePhotoType.equals("sfzz")) {
            sfzz_iv.setImageBitmap(bitmap);
        } else if (takePhotoType.equals("sfzf")) {
            sfzf_iv.setImageBitmap(bitmap);
        } else if (takePhotoType.equals("fap")) {
            fap_iv.setImageBitmap(bitmap);
        } else if (takePhotoType.equals("chanz")) {
            chanz_iv.setImageBitmap(bitmap);
        } else if (takePhotoType.equals("xsz")) {
            xsz_iv.setImageBitmap(bitmap);
        }
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

    public void onScanBarcode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("扫描条形码");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }
}
