package com.eroadcar.networkmanagement.activity;

import com.eroadcar.networkmanagement.MyApplication;
import com.eroadcar.networkmanagement.R;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class LoadingActivity extends BaseActivity {
	private Thread t;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		setContentView(R.layout.activity_loading);

		setSwipeBackEnable(false);

		application = (MyApplication) MyApplication.getContext();
		application.addActivity(this);


		t = new Thread() {
			public void run() {
				try {
					// Looper.prepare();
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				tmHandler.post(tmUpdateResults);
			}
		};
		t.start();

	}

	final Handler tmHandler = new Handler();
	final Runnable tmUpdateResults = new Runnable() {
		@Override
		public void run() {
			intent(LoginActivity.class);
			finish();
		}
	};

}
