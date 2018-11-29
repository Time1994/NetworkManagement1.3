package com.eroadcar.networkmanagement.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.activity.BaseActivity;
import com.eroadcar.networkmanagement.activity.RolesActivity;
import com.eroadcar.networkmanagement.bean.HomeBean;
import com.eroadcar.networkmanagement.bean.RoleBean;

import java.util.ArrayList;

public class RoleAdapter extends BaseAdapter {
    private ArrayList<RoleBean> mData;
    private Context mContext;


    public RoleAdapter(Context context, ArrayList<RoleBean> data) {
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
                    .inflate(R.layout.adapter_role, null);
        }

        final RoleBean bean = mData.get(position);

        CheckBox choose_cb = (CheckBox) convertView.findViewById(R.id.choose_cb);
        TextView role = (TextView) convertView.findViewById(R.id.role_tv);

        role.setText(bean.getRole_app_name());

        boolean isExit = false;

        if (((RolesActivity) mContext).ids.contains(bean.getRole_app_id())) {
            choose_cb.setChecked(true);
            for (int i = 0; i < ((RolesActivity) mContext).roleBeans.size(); i++) {
                if (((RolesActivity) mContext).roleBeans.get(i).getRole_app_id().equals(bean.getRole_app_id())) {
                    isExit = true;

                    break;
                }
            }
            if (!isExit) {
                ((RolesActivity) mContext).roleBeans.add(bean);
            }
        }


        choose_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ((RolesActivity) mContext).roleBeans.add(bean);
                } else {
                    for (int i = 0; i < ((RolesActivity) mContext).roleBeans.size(); i++) {
                        if (((RolesActivity) mContext).roleBeans.get(i).getRole_app_id().equals(bean.getRole_app_id())) {
                            ((RolesActivity) mContext).roleBeans.remove(i);
                            break;
                        }
                    }
                }
            }
        });


        return convertView;
    }
}
