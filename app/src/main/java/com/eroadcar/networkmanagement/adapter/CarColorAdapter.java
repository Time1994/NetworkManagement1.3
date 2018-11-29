package com.eroadcar.networkmanagement.adapter;


import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.R.color;
import com.eroadcar.networkmanagement.bean.CarColorBean;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CarColorAdapter extends BaseAdapter {
	private ArrayList<CarColorBean> vector;
	private Context context;
	private int clickTemp = -1;

	public CarColorAdapter(ArrayList<CarColorBean> vector, Context context) {
		super();
		this.vector = vector;
		this.context = context;
	}

	public void setSeclection(int position) {
		clickTemp = position;
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
					R.layout.adapter_carcolor, null);
			// R.layout.adapter_cartype, null);

		}
		TextView content_tv = (TextView) convertView
				.findViewById(R.id.content_tv);
		TextView color_tv = (TextView) convertView.findViewById(R.id.color_tv);
		if (clickTemp == position) {
			convertView.setBackgroundResource(R.drawable.btn_green);
			content_tv.setTextColor(context.getResources().getColor(
					color.blue));
		} else {
			convertView.setBackgroundResource(R.drawable.btn_gray);
			content_tv.setTextColor(context.getResources().getColor(
					color.black));
		}

		content_tv.setText(vector.get(position).getCt_car_color());

		String color = vector.get(position).getCt_color_rgb();
		if (color == null || color.equals("")) {
			color_tv.setVisibility(View.GONE);
		} else {
			color = "#" + color;
			color_tv.setBackgroundColor(Color.parseColor(color));
			color_tv.setVisibility(View.VISIBLE);
		}
		

		return convertView;
	}
}
