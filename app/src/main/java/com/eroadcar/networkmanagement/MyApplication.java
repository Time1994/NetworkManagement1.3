package com.eroadcar.networkmanagement;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.eroadcar.networkmanagement.bean.UserBean;
import com.tencent.bugly.crashreport.CrashReport;

import cn.jpush.android.api.JPushInterface;

/**
 * amos.
 */
public class MyApplication extends Application {
    private List<Activity> activityList = new LinkedList<Activity>();
    private static Context sContext;

    public UserBean userBean;
    public static boolean isDownload;
    public static String pathUrl;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();
        // 获取屏幕高宽
        // windowManager = (WindowManager) this
        // .getSystemService(Context.WINDOW_SERVICE);
        // Display display = windowManager.getDefaultDisplay();
        // DisplayMetrics dm = new DisplayMetrics();
        // display.getMetrics(dm);
        // width = display.getWidth();
        // hight = display.getHeight();
        // mDefaultImageDrawable = getResources().getDrawable(
        // R.drawable.ic_launcher);
        // mDataRootPath = this.getCacheDir().getPath();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        CrashReport.initCrashReport(getApplicationContext(), "f2ac77d2a5", true);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();


//		SDKInitializer.initialize(getApplicationContext());

        // CrashHandler catchExcep = new CrashHandler(this);
        // Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }

    /**
     * 获取储存Image的目录
     *
     * @return
     */
    // public static String getStorageDirectory() {
    // return Environment.getExternalStorageState().equals(
    // Environment.MEDIA_MOUNTED) ? mSdRootPath + FOLDER_NAME
    // : mDataRootPath + FOLDER_NAME;
    // }
    //
    // public static String getPath() {
    // String path = getStorageDirectory();
    // File folderFile = new File(path);
    // if (!folderFile.exists()) {
    // folderFile.mkdir();
    // }
    // return getStorageDirectory();
    //
    // }

    // 可以在整个项目中获取上下文
    public static Context getContext() {
        return sContext;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // 遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            try {
                activity.finish();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        // System.exit(0);
    }

    @Override
    public void onLowMemory() {
        System.gc();
        super.onLowMemory();
    }

}
