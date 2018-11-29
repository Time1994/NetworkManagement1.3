package com.eroadcar.networkmanagement.activity;

import java.util.ArrayList;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.CarAdapter;
import com.eroadcar.networkmanagement.bean.CarBean;
public class CarActivity extends BaseActivity implements OnClickListener {
	private Button other_btn, back_btn;
	private TextView title_tv;
	private ListView dot_lv;

	private ArrayList<CarBean> carBeans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car);

		title_tv = (TextView) findViewById(R.id.title_tv);
		other_btn = (Button) findViewById(R.id.other_btn);
		back_btn = (Button) findViewById(R.id.back_btn);

		dot_lv = (ListView) findViewById(R.id.dot_lv);

		back_btn.setOnClickListener(this);
		other_btn.setOnClickListener(this);

		title_tv.setText("库存车辆");
		back_btn.setText("");

		carBeans = (ArrayList<CarBean>) getIntent()
				.getSerializableExtra("LIST");
		dot_lv.setAdapter(new CarAdapter(carBeans, CarActivity.this));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back_btn:
			onBackPressed();
			break;
		case R.id.other_btn:

			break;
		default:
			break;
		}
	}

}
