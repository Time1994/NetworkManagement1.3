package com.eroadcar.networkmanagement.utils;

import com.eroadcar.networkmanagement.R;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

/**
 * Created by admin on 14-3-17. 加载中对话框显示
 */
public class LoadingDialog {
	private Dialog loadingDialog;
	private TextView textView;

	public LoadingDialog(Context context) {
		loadingDialog = new Dialog(context, R.style.MyDialog_loading);
		loadingDialog.setContentView(R.layout.loding_dialog);
		textView = (TextView) loadingDialog.findViewById(R.id.loading_message);
	}

	/**
	 * 设置消息显示
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		textView.setText(message);
	}

	/**
	 * 关闭对话框
	 */
	public void dismiss() {
		loadingDialog.dismiss();
	}

	/**
	 * 显示对话框
	 */
	public void dialogShow() {
		try {
			loadingDialog.show();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
