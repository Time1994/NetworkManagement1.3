package com.eroadcar.networkmanagement.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.activity.OrderSaleDetailsActivity;
import com.eroadcar.networkmanagement.activity.PerfectActivity;
import com.eroadcar.networkmanagement.bean.OrderSalesBean;

import java.util.ArrayList;

public class OrderSaleAdapter extends BaseAdapter {
    private ArrayList<OrderSalesBean> mData;
    private Context mContext;


    public OrderSaleAdapter(Context context, ArrayList<OrderSalesBean> data) {
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
                    .inflate(R.layout.adapter_order, null);
        }

        final OrderSalesBean bean = mData.get(position);


        TextView name = (TextView) convertView.findViewById(R.id.name_tv);
        TextView state = (TextView) convertView.findViewById(R.id.state_tv);
        TextView time = (TextView) convertView.findViewById(R.id.time_tv);


        name.setText(position+1+"、"+bean.getYfs_sellsq_ownner());
        state.setText(bean.getYfs_sellsq_state());
        time.setText(bean.getYfs_sellsq_time());


        return convertView;
    }
}
