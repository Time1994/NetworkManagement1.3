package com.eroadcar.networkmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.AddCarsBean;
import com.eroadcar.networkmanagement.bean.CarBean;

import java.util.ArrayList;

public class RentCarAdapter extends BaseAdapter {
    private ArrayList<AddCarsBean> vector;
    private Context context;

    public RentCarAdapter(ArrayList<AddCarsBean> vector, Context context) {
        super();
        this.vector = vector;
        this.context = context;
    }

    @Override
    public int getCount() {
        return vector.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return vector.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.adapter_rent_car, null);

        }
        TextView car_tv = (TextView) convertView.findViewById(R.id.car_tv);
        TextView color_tv = (TextView) convertView.findViewById(R.id.color_tv);
        TextView vin_tv = (TextView) convertView.findViewById(R.id.vin_tv);
        TextView pai_tv = (TextView) convertView.findViewById(R.id.pai_tv);
        TextView price_tv = (TextView) convertView.findViewById(R.id.price_tv);
        TextView cdzaddress_tv = (TextView) convertView.findViewById(R.id.cdzaddress_tv);
        TextView time_tv = (TextView) convertView.findViewById(R.id.time_tv);

        TextView state_tv = (TextView) convertView.findViewById(R.id.state_tv);
        TextView jtime_tv = (TextView) convertView.findViewById(R.id.jtime_tv);


        AddCarsBean bean = vector.get(position);

        car_tv.setText("车型：" + bean.getYfs_car_cartype() + "  " + bean.getYfs_car_num() + "辆");
        color_tv.setText("颜色：" + bean.getYfs_car_carcolor());
        price_tv.setText("价格：" + bean.getYfs_car_carprice() + "元");
        cdzaddress_tv.setText("充电桩地址：" + bean.getYfs_car_chargpostaddr());
        time_tv.setText("租赁日期：" + bean.getYfs_car_zlstart() + " -- " + bean.getYfs_car_zlend());
        jtime_tv.setText(bean.getYfs_car_jctime());

        if (bean.getYfs_car_vin() != null && !bean.getYfs_car_vin().equals("")) {
            vin_tv.setText("车架号：" + bean.getYfs_car_vin());
            pai_tv.setText("车牌号：" + bean.getYfs_car_license());
        } else {
            vin_tv.setText("车架号：" + bean.getYfs_car_carvin());
            pai_tv.setText("车牌号：" + bean.getYfs_car_carlicense());
        }

        if (bean.getYfs_car_state() != null && bean.getYfs_car_state().equals("02")) {
            state_tv.setText("已交车");
        } else {
            state_tv.setText("未交车");
        }

        return convertView;
    }
}
