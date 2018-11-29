package com.eroadcar.networkmanagement.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.activity.RolesActivity;
import com.eroadcar.networkmanagement.bean.RoleBean;

import java.util.ArrayList;

public class DialogItemAdapter extends BaseAdapter {
    private ArrayList<RoleBean> mData;
    private Context mContext;


    public DialogItemAdapter(Context context, ArrayList<RoleBean> data) {
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
                    .inflate(R.layout.adapter_dialog_item, null);
        }

        final RoleBean bean = mData.get(position);


        TextView role = (TextView) convertView.findViewById(R.id.role_tv);
        TextView title = (TextView) convertView.findViewById(R.id.t_tv);

        role.setText(bean.getRole_app_name());
        title.setText(bean.getRole_app_id());


        return convertView;
    }
}
