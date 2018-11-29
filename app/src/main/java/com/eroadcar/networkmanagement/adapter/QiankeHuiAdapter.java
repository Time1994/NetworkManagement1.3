package com.eroadcar.networkmanagement.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.QianKeBean;
import com.eroadcar.networkmanagement.bean.QianKeHuiBean;

import java.util.ArrayList;

public class QiankeHuiAdapter extends BaseAdapter {
    private ArrayList<QianKeHuiBean> mData;
    private Context mContext;


    public QiankeHuiAdapter(Context context, ArrayList<QianKeHuiBean> data) {
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
                    .inflate(R.layout.adapter_custom_hui, null);
        }

        final QianKeHuiBean bean = mData.get(position);

        TextView content = (TextView) convertView.findViewById(R.id.content_tv);
        TextView time = (TextView) convertView.findViewById(R.id.time_tv);

        content.setText(bean.getFrd_crevist_context());
        time.setText(bean.getFrd_cadd_date());


        return convertView;
    }
}
