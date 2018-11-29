package com.eroadcar.networkmanagement.activity;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.eroadcar.networkmanagement.MyApplication;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.LoadingDialog;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.utils.Tool;
import com.eroadcar.networkmanagement.view.BaseDragZoomImageView;
import com.eroadcar.networkmanagement.view.PinchImageView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.eroadcar.networkmanagement.R;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


/**
 * @author amos
 */
public class BaseActivity extends SwipeBackActivity {//FragmentActivity
    public static RequestQueue mRequestQueue;
    public MyApplication application;
    public LoadingDialog loadingDialog;

    public String IMEI = "";

    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        application = (MyApplication) MyApplication.getContext();
        application.addActivity(this);

        mRequestQueue = Volley.newRequestQueue(MyApplication.getContext());

        loadingDialog = new LoadingDialog(this);


//		TelephonyManager telephonyManager = (TelephonyManager) this
//				.getSystemService(Context.TELEPHONY_SERVICE);
//		IMEI = telephonyManager.getDeviceId();
//		if (IMEI == null || IMEI.equals("")) {
//			IMEI = "0123456789";
//		}
    }


    @Override
    public void onBackPressed() {
        // overridePendingTransition(R.anim.right_out, R.anim.right_out);
        super.finish();
        super.onBackPressed();
    }


    public void intent(Class<?> context) {
        Intent intent = new Intent(this, context);
        startActivity(intent);
        // overridePendingTransition(R.anim.left_in, R.anim.left_in);
    }


    public void intentCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 页面跳转 带字段
     *
     * @param c
     * @param name
     * @param value
     */
    public <T> void starActivity(Class<T> c, String name, String value) {
        Intent intent = new Intent();
        intent.setClass(BaseActivity.this, c);
        intent.putExtra(name, value);
        startActivity(intent);
        // overridePendingTransition(R.anim.left_in, R.anim.left_in);
    }

    /**
     * 页面跳转ONTOP
     *
     * @param c
     */
    public <T> void starActivityOntop(Class<T> c) {
        Intent intent = new Intent();
        intent.setClass(BaseActivity.this, c);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // overridePendingTransition(R.anim.right_out, R.anim.right_out);
    }


    /**
     * 定义listView的高度
     *
     * @param listView 需要定义高度的listview
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        ((ViewGroup.MarginLayoutParams) params).setMargins(0, 0, 0, 0);
        listView.setLayoutParams(params);
    }

    public boolean JudgeMobile(String tel) {
        if (tel != null && Constant.TEL_PATTERN.matcher(tel).matches()) {
            return true;
        } else {
            return false;
        }
    }


    public String gbEncoding(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            buffer.append("/u" + hexB);
        }
        return buffer.substring(0);
    }

    public DialogPlus dialogMessage;

    /**
     * 弹窗
     */
    @SuppressLint("NewApi")
    public void showDialogMessage(Context context, String title, String text,
                                  String sure, OnClickListener listener) {
        dialogMessage = new DialogPlus.Builder(context)
                .setContentHolder(new ViewHolder(R.layout.dialog_content))
                .setCancelable(true).setGravity(Gravity.CENTER)
                .setBackgroundColorResourceId(R.color.transparent).create();
        View content = dialogMessage.getHolderView();
        Button confirm = (Button) content.findViewById(R.id.btn_dialog_confirm);
        Button cancel = (Button) content.findViewById(R.id.btn_dialog_cancel);
        TextView text_content = (TextView) content
                .findViewById(R.id.text_content);
        TextView text_title = (TextView) content
                .findViewById(R.id.text_dialog_title);

        text_title.setText(title);
        text_content.setText(text);
        confirm.setText(sure);
        confirm.setOnClickListener(listener);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogMessage.dismiss();
            }
        });

        dialogMessage.show();
    }

    /**
     * 弹窗
     */
    @SuppressLint("NewApi")
    public void showDialogMessage(Context context, String text,
                                  OnClickListener listener) {
        dialogMessage = new DialogPlus.Builder(context)
                .setContentHolder(new ViewHolder(R.layout.dialog_content))
                .setCancelable(true).setGravity(Gravity.CENTER)
                .setBackgroundColorResourceId(R.color.transparent).create();
        View content = dialogMessage.getHolderView();
        Button confirm = (Button) content.findViewById(R.id.btn_dialog_confirm);
        Button cancel = (Button) content.findViewById(R.id.btn_dialog_cancel);
        TextView text_content = (TextView) content
                .findViewById(R.id.text_content);

        text_content.setText(text);
        confirm.setOnClickListener(listener);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogMessage.dismiss();
            }
        });

        dialogMessage.show();
    }


    /**
     * 弹窗
     */
    @SuppressLint("NewApi")
    public void showDialogImage(Context context, String imagePath) {
        dialogMessage = new DialogPlus.Builder(context)
                .setContentHolder(new ViewHolder(R.layout.dialog_image))
                .setCancelable(true).setGravity(Gravity.CENTER)
                .setBackgroundColorResourceId(R.color.transparent).create();
        View content = dialogMessage.getHolderView();
//        final ImageView imageView = (ImageView) content
//                .findViewById(R.id.image);
//        final BaseDragZoomImageView imageView = (BaseDragZoomImageView) content
//                .findViewById(R.id.image);
        final PinchImageView imageView = (PinchImageView) content
                .findViewById(R.id.image);
        final ProgressBar progressbar = (ProgressBar) content.findViewById(R.id.progressbar);
        if (imagePath.contains("http:")) {
//            ImageRequest imageRequest = new ImageRequest(imagePath,
//                    new Response.Listener<Bitmap>() {
//                        @Override
//                        public void onResponse(Bitmap response) {
//                            // imageView.setImageBitmap(response);
//                            imageView.setImageBitmap(response);
//                        }
//                    }, 0, 0, Config.RGB_565, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    // imageView.setImageResource(R.drawable.default_image);
//                }
//            });
//            Volley.newRequestQueue(context).add(imageRequest);

            Glide.with(this)
                    .load(imagePath)
                    .placeholder(R.mipmap.icon_none_logo)
                    .into(imageView);

//            Glide.with(this)
//                    .load(imagePath)
//                    .asBitmap()//强制Glide返回一个Bitmap对象
//                    .listener(new RequestListener<String, Bitmap>() {
//                        @Override
//                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
//                            progressbar.setVisibility(View.GONE);
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Bitmap bitmap, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                            DisplayMetrics dm = getResources().getDisplayMetrics();
//
//                            int x = (dm.widthPixels - bitmap.getWidth()) / 2;
//                            x = x < 0 ? 0 : x;
//                            int y = (dm.heightPixels - bitmap.getHeight()) / 2;
//                            y = y < 0 ? 0 : y;
//
//                            Matrix matrix = new Matrix();
//                            matrix.postTranslate(x, y);
//                            imageView.setImageMatrix(matrix);
//
//                            progressbar.setVisibility(View.GONE);
//
//                            return false;
//                        }
//                    }).placeholder(R.mipmap.icon_none_logo).into(imageView);
        } else {
            int degree = Tool.readPictureDegree(imagePath);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            bitmap = Tool.rotaingImageView(degree, bitmap);
            imageView.setImageBitmap(bitmap);
        }


        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialogMessage.dismiss();
            }
        });
        dialogMessage.show();
    }

    // 检测网络
    public boolean checkNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return true;
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            backgroundAlpha(1f);
        }

    }

    public void setImageh(String url, final ImageView image, final int defaultid) {
        System.out.println("url=" + url);
        try {
//            ImageRequest imageRequest = new ImageRequest(url,
//                    new Response.Listener<Bitmap>() {
//                        @Override
//                        public void onResponse(Bitmap response) {
//                            // imageView.setImageBitmap(response);
//                            ByteArrayOutputStream out = new ByteArrayOutputStream();
//                            response.compress(Bitmap.CompressFormat.JPEG, 5, out);
//                            image.setImageBitmap(response);
//                        }
//                    }, 0, 0, Config.RGB_565, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    image.setImageResource(defaultid);
//                }
//            });
//            mRequestQueue.add(imageRequest);

            Glide.with(this)
                    .load(url)
                    .placeholder(defaultid)
                    .into(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Volley.newRequestQueue(BaseActivity.this).add(imageRequest);
        // Volley.newRequestQueue(BaseActivity.this).start();
    }

    public void Navi(String title, String addr, String lat, String lng) {
        try {
            Intent intent = Intent.getIntent("intent://map/marker?location="
                    + lat + "," + lng + "&title=" + title + "&content=" + addr
                    + "&src=" + "上海翼禄新能源汽车服务有限公司" + "|" + "翼禄门店版"
                    + "#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            startActivity(intent); //启动调用
        } catch (Exception e) {
            // TODO: handle exception
            try {
                Intent intent = Intent
                        .getIntent("androidamap://viewMap?sourceApplication="
                                + "我的位置" + "&poiname=" + title + "&lat=" + lat
                                + "&lon=" + lng + "&dev=0");
                startActivity(intent);
            } catch (Exception e2) {
                // TODO: handle exception
                ToastUtils.showShort("您没有安装地图软件");
            }
        }
    }

    public String dataLong(int c) {
        if (c >= 10) {
            return String.valueOf(c);
        } else {
            return "0" + String.valueOf(c);
        }
    }


    public void copy(String code) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(code);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText(
                    code, code);
            clipboard.setPrimaryClip(clip);
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、152、157(TD)、158、159、178(新)、182、184、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、170、173、177、180、181、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1]\\d{10}";//"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }
    /**
     * 验证身份证号是否符合规则
     * @param text 身份证号
     * @return
     */
    public boolean personIdValidation(String text) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }
}

