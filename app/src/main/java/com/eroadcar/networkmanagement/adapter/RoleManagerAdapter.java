package com.eroadcar.networkmanagement.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.activity.RolesActivity;
import com.eroadcar.networkmanagement.bean.RoleBean;
import com.eroadcar.networkmanagement.bean.UserBean;

import java.util.ArrayList;

public class RoleManagerAdapter extends BaseAdapter {
    private ArrayList<UserBean> mData;
    private Context mContext;


    public RoleManagerAdapter(Context context, ArrayList<UserBean> data) {
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
                    .inflate(R.layout.adapter_role_manager, null);
        }

        final UserBean bean = mData.get(position);


        TextView name = (TextView) convertView.findViewById(R.id.name_tv);
        TextView id = (TextView) convertView.findViewById(R.id.id_tv);
        TextView title = (TextView) convertView.findViewById(R.id.tit_tv);

        Button role_btn = (Button) convertView.findViewById(R.id.role_btn);


        String role = "";

        if (bean.getMg_role_ids() != null && !bean.getMg_role_ids().equals("")) {
            if (bean.getMg_role_ids().contains("1")) {
                role += "管理员，";

                role_btn.setEnabled(false);
            }
            if (bean.getMg_role_ids().contains("2")) {
                role += "销售经理，";
            }
            if (bean.getMg_role_ids().contains("3")) {
                role += "销售顾问，";
            }
            if (bean.getMg_role_ids().contains("4")) {
                role += "财务经理，";
            }
            role = role.substring(0, role.length() - 1);
        } else {
            role = "未分配";
        }

        name.setText(bean.getMg_name());
        id.setText(bean.getMg_code());
        title.setText(role);

        role_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RolesActivity.class);
                intent.putExtra("ID", bean.getMg_id());
                intent.putExtra("SHOPSID", bean.getMg_shopsid());
                intent.putExtra("IDS", bean.getMg_role_ids());
                mContext.startActivity(intent);
            }
        });


        return convertView;
    }
}
