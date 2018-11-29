package com.eroadcar.networkmanagement.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.activity.BaseActivity;
import com.eroadcar.networkmanagement.activity.OrderSaleDetailsActivity;
import com.eroadcar.networkmanagement.bean.HomeBean;
import com.eroadcar.networkmanagement.bean.OrderSalesBean;
import com.eroadcar.networkmanagement.utils.Constant;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private ArrayList<HomeBean> mData;
    private Context mContext;


    public ImageAdapter(Context context, ArrayList<HomeBean> data) {
        // TODO Auto-generated constructor stub
        mData = data;
        mContext = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return mData.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        if (convertView == null || convertView.getTag() == null) {
            convertView = ((LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.adapter_image, null);
        }

        final HomeBean bean = mData.get(position);


        TextView remark = (TextView) convertView.findViewById(R.id.remark_tv);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);

        remark.setText(bean.getContent());
        ((BaseActivity) mContext).setImageh(bean.getType(), image, R.mipmap.icon_camrea_sfzs);


        return convertView;
    }
}
