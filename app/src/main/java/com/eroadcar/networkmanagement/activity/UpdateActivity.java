package com.eroadcar.networkmanagement.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.VersionBean;
import com.eroadcar.networkmanagement.service.DownloadService;
import com.eroadcar.networkmanagement.service.DownloadService.*;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.Logger;
import com.eroadcar.networkmanagement.utils.ToastUtils;

public class UpdateActivity extends BaseActivity implements OnClickListener {
    private Button other_btn, back_btn;
    private TextView title_tv, note_tv, new_tv;
    private Button logout_btn;

    private final int CHECKVERSION = 1;
    private ServiceConnection conn = null;
    private DownloadService.DownloadBinder binder;
    private boolean isBinded;

    private double currentVersionCode, versionNo;
    private CommonBean<ArrayList<VersionBean>> bean;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static int REQUEST_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_tv = (TextView) findViewById(R.id.title_tv);
        other_btn = (Button) findViewById(R.id.other_btn);
        back_btn = (Button) findViewById(R.id.back_btn);
        note_tv = (TextView) findViewById(R.id.note_tv);
        new_tv = (TextView) findViewById(R.id.new_tv);

        logout_btn = (Button) findViewById(R.id.logout_btn);

        logout_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        other_btn.setOnClickListener(this);

        title_tv.setText("版本更新");
        back_btn.setText("");

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), 0);
            String appVersion = info.versionName; // 版本名
            currentVersionCode = info.versionCode; // 版本号
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        conn = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                // TODO Auto-generated method stub
                isBinded = false;
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                // TODO Auto-generated method stub
                binder = (DownloadBinder) service;
                Logger.getLogger().i("服务启动。。。");
                // 开始下载
                isBinded = true;
                binder.addCallback(callback);
                binder.start();
            }
        };

        getApkInfo();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }
    }

    private void getApkInfo() {
        loadingDialog.setMessage("正在获取版本信息...");
        loadingDialog.dialogShow();
        String url = Constant.GETAPKINFO;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appcode", IMEI);
        map.put("apptype", Constant.APPTYPE);
        map.put("mg_id", application.userBean.getMg_id());
        map.put("mg_name", application.userBean.getMg_name());
        map.put("mg_shopsid", application.userBean.getMg_shopsid());
        map.put("mg_groupid", application.userBean.getMg_groupid());
        map.put("mg_shopname", application.userBean.getMg_shopname());
        GsonRequest<CommonBean<ArrayList<VersionBean>>> requtst = new GsonRequest<CommonBean<ArrayList<VersionBean>>>(
                Method.POST, url, listener_acc);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
        mRequestQueue.start();
    }

    private RequesListener<CommonBean<ArrayList<VersionBean>>> listener_acc = new RequesListener<CommonBean<ArrayList<VersionBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<VersionBean>> arg0) {
            // TODO Auto-generated method stub
            bean = arg0;
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

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CHECKVERSION:
                    if (bean.getState().equals("success")) {
                        try {
                            versionNo = Double.valueOf(bean.getData().get(0)
                                    .getVersionCode());
                        } catch (Exception e) {
                            // TODO: handle exception
                            versionNo = 1.0;
                        }
                        if (bean.getData().get(0).getRemark() != null
                                && !bean.getData().get(0).getRemark().equals("")) {
                            note_tv.setText(bean.getData().get(0).getRemark());
                        }
                        new_tv.setText("新版本 V "
                                + bean.getData().get(0).getVersionName());

                        if (versionNo <= currentVersionCode) {
                            ToastUtils.showShort("当前版本已是最新！");
                            logout_btn.setVisibility(View.GONE);
                            new_tv.setText("当前版本 V " + currentVersionCode);
                        } else {
                            // ToastUtils.showShort("检测到有新版本，建议立即更新！");
                        }
                    } else {
                        ToastUtils.showShort(bean.getMsg());
                    }
                    break;
                case -1:

                    break;
                default:
                    // tv_progress.setText("当前进度 ： " + msg.what + "%");
                    break;

            }
        }

        ;
    };

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        if (application.isDownload) {
            Intent it = new Intent(UpdateActivity.this, DownloadService.class);
            startService(it);
            bindService(it, conn, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBinded) {
            unbindService(conn);
        }
        if (binder != null && binder.isCanceled()) {
            Intent it = new Intent(this, DownloadService.class);
            stopService(it);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;

            case R.id.logout_btn:
                ToastUtils.showLong("已在后台下载新版本！");
                application.isDownload = true;
                application.pathUrl = bean.getData().get(0).getApkLink();
                if (application.isDownload) {
                    Intent it = new Intent(UpdateActivity.this,
                            DownloadService.class);
                    startService(it);
                    bindService(it, conn, Context.BIND_AUTO_CREATE);
                }
                break;
            default:
                break;
        }
    }

    private ICallbackResult callback = new ICallbackResult() {

        @Override
        public void OnBackResult(Object result) {
            // TODO Auto-generated method stub
            if ("finish".equals(result)) {
                finish();
                return;
            }
            int i = (Integer) result;
            // mProgressBar.setProgress(i);
            // mHandler.sendEmptyMessage(i);
        }
    };

    public interface ICallbackResult {
        public void OnBackResult(Object result);
    }

}
