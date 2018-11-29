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
import com.eroadcar.networkmanagement.activity.RolesActivity;
import com.eroadcar.networkmanagement.activity.ZhengxActivity;
import com.eroadcar.networkmanagement.bean.OrderSalesBean;
import com.eroadcar.networkmanagement.bean.UserBean;

import java.util.ArrayList;

public class ZhengxAdapter extends BaseAdapter {
    private ArrayList<OrderSalesBean> mData;
    private Context mContext;
    private String type;


    public ZhengxAdapter(Context context, ArrayList<OrderSalesBean> data, String t) {
        // TODO Auto-generated constructor stub
        mData = data;
        mContext = context;
        type = t;
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
                    .inflate(R.layout.adapter_zhengx, null);
        }

        final OrderSalesBean bean = mData.get(position);


        TextView name = (TextView) convertView.findViewById(R.id.name_tv);
        TextView state = (TextView) convertView.findViewById(R.id.state_tv);

        Button sure_btn = (Button) convertView.findViewById(R.id.sure_btn);


        name.setText(bean.getYfs_sellsq_ownner());
        state.setText(bean.getYfs_sellsq_state());

        if (type.equals("choose")) {
            state.setTextColor(mContext.getResources().getColor(R.color.blue));
            sure_btn.setText("选择车辆");
            if (bean.getYfs_sellsq_state().contains("通过")) {
                sure_btn.setVisibility(View.VISIBLE);
            } else if (bean.getYfs_sellsq_state().contains("驳回")) {
                sure_btn.setVisibility(View.VISIBLE);
                sure_btn.setText("修改资料");
                state.setTextColor(mContext.getResources().getColor(R.color.red));
            } else {
                sure_btn.setVisibility(View.GONE);
            }
        } else if (type.equals("wan")) {
            state.setTextColor(mContext.getResources().getColor(R.color.blue));
            sure_btn.setVisibility(View.VISIBLE);
            sure_btn.setText("完善信息");
        }

        sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("choose")) {
                    if (bean.getYfs_sellsq_state().contains("通过")) {
                        Intent intent = new Intent(mContext, OrderSaleDetailsActivity.class);
                        intent.putExtra("yfs_sellsq_code", bean.getYfs_sellsq_code());
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, ZhengxActivity.class);
                        intent.putExtra("BEAN", bean);
                        mContext.startActivity(intent);
                    }
                } else if (type.equals("wan")) {
                    Intent intent = new Intent(mContext, PerfectActivity.class);
                    intent.putExtra("yfs_sellsq_code", bean.getYfs_sellsq_code());
                    intent.putExtra("BEAN", bean);
                    mContext.startActivity(intent);
                }
            }
        });


        return convertView;
    }
}
