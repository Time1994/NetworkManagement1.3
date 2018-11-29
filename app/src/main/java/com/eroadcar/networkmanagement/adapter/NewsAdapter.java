package com.eroadcar.networkmanagement.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.NewsBean;
import com.eroadcar.networkmanagement.bean.StatisticsBean;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {
    private ArrayList<NewsBean> mData;
    private Context mContext;


    public NewsAdapter(Context context, ArrayList<NewsBean> data) {
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
                    .inflate(R.layout.adapter_news, null);
        }

        final NewsBean bean = mData.get(position);


        TextView type_tv = (TextView) convertView.findViewById(R.id.type_tv);
        TextView tit_tv = (TextView) convertView.findViewById(R.id.tit_tv);
        TextView time_tv = (TextView) convertView.findViewById(R.id.time_tv);
        TextView content_tv = (TextView) convertView.findViewById(R.id.content_tv);


        type_tv.setText(bean.getIc_info_type());
        time_tv.setText(bean.getIc_app_pushdate());
        tit_tv.setText(bean.getIc_info_state());
        content_tv.setText(bean.getIc_app_pushtext());


        return convertView;
    }
}
