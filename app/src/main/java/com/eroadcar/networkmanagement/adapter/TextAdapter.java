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
import com.eroadcar.networkmanagement.activity.BaseActivity;
import com.eroadcar.networkmanagement.activity.RolesActivity;
import com.eroadcar.networkmanagement.bean.RoleBean;

import java.util.ArrayList;

public class TextAdapter extends BaseAdapter {
    private ArrayList<String> mData;
    private Context mContext;


    public TextAdapter(Context context, ArrayList<String> data) {
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
                    .inflate(R.layout.adapter_text, null);
        }

        final String bean = mData.get(position);

        TextView content = (TextView) convertView.findViewById(R.id.content_tv);
        TextView copy = (TextView) convertView.findViewById(R.id.copy_tv);

        content.setText(bean);


        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity) mContext).copy(bean);
            }
        });


        return convertView;
    }
}
