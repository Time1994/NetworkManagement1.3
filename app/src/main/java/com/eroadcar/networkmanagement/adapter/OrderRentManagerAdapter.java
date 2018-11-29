package com.eroadcar.networkmanagement.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.OrderRentBean;
import com.eroadcar.networkmanagement.bean.OrderSalesBean;

import java.util.ArrayList;

public class OrderRentManagerAdapter extends BaseAdapter {
    private ArrayList<OrderRentBean> mData;
    private Context mContext;


    public OrderRentManagerAdapter(Context context, ArrayList<OrderRentBean> data) {
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
                    .inflate(R.layout.adapter_order_sale_manager, null);
        }

        final OrderRentBean bean = mData.get(position);

        TextView name = (TextView) convertView.findViewById(R.id.name_tv);
        TextView state = (TextView) convertView.findViewById(R.id.state_tv);
        TextView empl = (TextView) convertView.findViewById(R.id.emplo_tv);
        TextView time = (TextView) convertView.findViewById(R.id.time_tv);

        name.setText(position+1+"、"+"客户姓名：" + bean.getYfs_leasesq_ownner());
        state.setText(bean.getYfs_leasesq_state());
        empl.setText("提交人：" + bean.getYfs_leasesq_person());
        time.setText("提交时间：" + bean.getYfs_leasesq_time());


        return convertView;
    }
}
