package com.eroadcar.networkmanagement.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.HomeBean;

import java.util.ArrayList;

public class HomeAdapter extends BaseAdapter {
    private ArrayList<HomeBean> mData;
    private Context mContext;
    private int maxImgCount;


    public HomeAdapter(Context context, ArrayList<HomeBean> data) {
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
                    .inflate(R.layout.adapter_home, null);
        }

        HomeBean bean = mData.get(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_iv);
        TextView content = (TextView) convertView.findViewById(R.id.content_tv);
        TextView num = (TextView) convertView.findViewById(R.id.num_tv);

//        ((BaseActivity) mContext).setImage(Constant.HTTP + bean.getHm_img(), imageView, R.mipmap.icon_login);
        imageView.setImageResource(bean.getImageId());
        content.setText(bean.getContent());

        if (bean.getType().equals("sale")){
            content.setTextColor(mContext.getResources().getColor(R.color.redo));
        }else if(bean.getType().equals("wei")){
            content.setTextColor(mContext.getResources().getColor(R.color.blue));
        }else if(bean.getType().equals("ku")){
            content.setTextColor(mContext.getResources().getColor(R.color.purple));
        }else if(bean.getType().equals("employee")){
            content.setTextColor(mContext.getResources().getColor(R.color.yellow));
        }else if(bean.getType().equals("car")){
            content.setTextColor(mContext.getResources().getColor(R.color.orange));
        }else if(bean.getType().equals("tong")){
            content.setTextColor(mContext.getResources().getColor(R.color.green));
        }

        if (bean.getNewsnum() != 0) {
            num.setText(bean.getNewsnum() + "");
            num.setVisibility(View.VISIBLE);
        } else {
            num.setVisibility(View.GONE);
        }

        return convertView;
    }
}
