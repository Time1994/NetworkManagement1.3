package com.eroadcar.networkmanagement.service;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.eroadcar.networkmanagement.activity.OrderSalesActivity;
import com.eroadcar.networkmanagement.activity.OrderSalesCaiwuActivity;
import com.eroadcar.networkmanagement.activity.OrderSalesManagerActivity;
import com.eroadcar.networkmanagement.activity.SaleManagerActivity;
import com.eroadcar.networkmanagement.activity.ZhengxListActivity;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
//        Logger.d(TAG, "onReceive - " + intent.getAction() + ", extras: " + AndroidUtil.printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//            Logger.d(TAG, "JPush 用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//            Logger.d(TAG, "接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//            Logger.d(TAG, "接受到推送下来的通知");

            receivingNotification(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//            Logger.d(TAG, "用户点击打开了通知");

            openNotification(context, bundle);

        } else {
//            Logger.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//        Logger.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
//        Logger.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//        Logger.d(TAG, "extras : " + extras);
    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String key = "";
        try {
            JSONObject extrasJson = new JSONObject(extras);
            key = extrasJson.optString("remark");
        } catch (Exception e) {
//            Logger.w(TAG, "Unexpected: extras is not a valid json", e);
            e.printStackTrace();
            return;
        }
        try {
            if (key.equals("征信中")) {
                Intent mIntent = new Intent(context, OrderSalesManagerActivity.class);
                mIntent.putExtras(bundle);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mIntent);
            } else if (key.equals("征信通过")) {
                Intent intent = new Intent(context, ZhengxListActivity.class);
                intent.putExtra("FROM", "choose");
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (key.equals("待收定金") || key.equals("待收全款")) {
                Intent intent = new Intent(context, OrderSalesCaiwuActivity.class);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (key.equals("已收定金") || key.equals("已审核充电桩") || key.equals("已补全资料")) {
                Intent intent = new Intent(context, OrderSalesManagerActivity.class);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (key.equals("已收全款")) {
                Intent intent = new Intent(context, ZhengxListActivity.class);
                intent.putExtra("FROM", "wan");
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (key.equals("已完成")) {
                Intent intent = new Intent(context, OrderSalesActivity.class);
                intent.putExtra("FROM", "wan");
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (key.equals("申请中")) {
                Intent intent = new Intent(context, OrderSalesManagerActivity.class);
                intent.putExtra("FROM", "rent");
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (key.equals("已交车")) {
                Intent intent = new Intent(context, OrderSalesCaiwuActivity.class);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
