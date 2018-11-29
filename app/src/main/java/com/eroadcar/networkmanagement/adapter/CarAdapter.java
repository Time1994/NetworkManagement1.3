package com.eroadcar.networkmanagement.adapter;

import java.util.ArrayList;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.CarBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CarAdapter extends BaseAdapter {
	private ArrayList<CarBean> vector;
	private Context context;

	public CarAdapter(ArrayList<CarBean> vector, Context context) {
		super();
		this.vector = vector;
		this.context = context;
	}

	@Override
	public int getCount() {
		return vector.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return vector.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_car, null);

		}
		TextView class_tv = (TextView) convertView.findViewById(R.id.class_tv);
		TextView color_tv = (TextView) convertView.findViewById(R.id.color_tv);
		TextView vin_tv = (TextView) convertView.findViewById(R.id.vin_tv);

		class_tv.setText(vector.get(position).getYck_car_chexing() + " "
				+ vector.get(position).getYck_car_subchexing());
		color_tv.setText(vector.get(position).getYck_car_color());
		vin_tv.setText(vector.get(position).getYck_car_vin());

		return convertView;
	}
}
