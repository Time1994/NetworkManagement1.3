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
import com.eroadcar.networkmanagement.bean.StatisticsBean;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class StatisticsAdapter extends BaseAdapter {
    private ArrayList<StatisticsBean> mData;
    private Context mContext;


    public StatisticsAdapter(Context context, ArrayList<StatisticsBean> data) {
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
                    .inflate(R.layout.adapter_statistics, null);
        }

        final StatisticsBean bean = mData.get(position);


        TextView store_tv = (TextView) convertView.findViewById(R.id.store_tv);
        TextView amount_tv = (TextView) convertView.findViewById(R.id.amount_tv);
        TextView num_tv = (TextView) convertView.findViewById(R.id.num_tv);

        Double p = Double.valueOf(bean.getWx_order_price()) + (Double.valueOf(bean.getXs_order_price()) * 1) +
                Double.valueOf(bean.getZl_order_price());

        DecimalFormat format = new DecimalFormat("#,##0.00");//不以科学计数法显示，并把结果用逗号隔开保留两位小数
        BigDecimal bigDecimal = new BigDecimal(p);//不以科学计数法显示，正常显示保留两位小数
//        tv.setText("" + format.format(stt) + "\n" + bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP) + "");

        store_tv.setText(bean.getXs_shop_name());
        num_tv.setText(bean.getQk_person_count());
        amount_tv.setText(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP) + "");


        return convertView;
    }
}
