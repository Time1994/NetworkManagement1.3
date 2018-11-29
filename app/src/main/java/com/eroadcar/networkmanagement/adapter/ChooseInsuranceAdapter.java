package com.eroadcar.networkmanagement.adapter;

import java.util.ArrayList;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.activity.BaseActivity;
import com.eroadcar.networkmanagement.activity.ChooseInsuranceActivity;
import com.eroadcar.networkmanagement.bean.InsuranceBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChooseInsuranceAdapter extends BaseAdapter {
    private ArrayList<InsuranceBean> mData;
    private LayoutInflater infater = null;
    private Context mContext;
    public boolean[] checks; // 用于保存checkBox的选择状态

    public ChooseInsuranceAdapter(Context context, ArrayList<InsuranceBean> data) {
        // TODO Auto-generated constructor stub
        mData = data;
        infater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        checks = new boolean[getCount()];
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

    // 设置选中项
    public void setSelect(int index) {
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        // PaymentHolder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = infater.inflate(R.layout.adapter_choose_insurance,
                    null);
            // holder = new PaymentHolder(convertView);
            // convertView.setTag(holder);
        } else {
            // holder = (PaymentHolder) convertView.getTag();
        }
        InsuranceBean bean = mData.get(position);

        TextView name_tv = (TextView) convertView.findViewById(R.id.name_tv);

        ImageView chick_iv = (ImageView) convertView
                .findViewById(R.id.check_iv);

        for (int i = 0; i < ((ChooseInsuranceActivity) mContext).insuranceBean
                .size(); i++) {
            if (((ChooseInsuranceActivity) mContext).insuranceBean.get(i)
                    .getIt_id().equals(bean.getIt_id())) {
                checks[position] = true;
                break;
            }
        }

        if (checks[position]) {
            chick_iv.setVisibility(View.VISIBLE);
        }

        name_tv.setText((position + 1) + "." + bean.getIt_insurance_type());

        return convertView;
    }

}
