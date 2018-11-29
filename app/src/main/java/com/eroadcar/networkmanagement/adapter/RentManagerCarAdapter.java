package com.eroadcar.networkmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.activity.OrderRentManagerDetailsActivity;
import com.eroadcar.networkmanagement.activity.RolesActivity;
import com.eroadcar.networkmanagement.bean.AddCarsBean;

import java.util.ArrayList;

public class RentManagerCarAdapter extends BaseAdapter {
    private ArrayList<AddCarsBean> vector;
    private Context mContext;
    private boolean isSe = false;

    public RentManagerCarAdapter(ArrayList<AddCarsBean> vector, Context context, boolean quan) {
        super();
        this.vector = vector;
        this.mContext = context;
        this.isSe = quan;
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
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.adapter_rent_manager_car, null);

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

        CheckBox choose_cb = (CheckBox) convertView.findViewById(R.id.choose_cb);

        final LinearLayout con_ll = (LinearLayout) convertView.findViewById(R.id.con_ll);
        RelativeLayout tit_rl = (RelativeLayout) convertView.findViewById(R.id.tit_rl);

        final ImageView sanjiao_iv = (ImageView) convertView.findViewById(R.id.sanjiao_iv);


        final AddCarsBean bean = vector.get(position);

        car_tv.setText("车型：" + bean.getYfs_car_cartype() + "  " + bean.getYfs_car_num() + "辆");
        color_tv.setText("颜色：" + bean.getYfs_car_carcolor());
        price_tv.setText("价格：" + bean.getYfs_car_carprice());
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

            choose_cb.setVisibility(View.INVISIBLE);
        } else {
            state_tv.setText("未交车");
        }

        if (isSe) {
            choose_cb.setChecked(true);
        } else {
            choose_cb.setChecked(false);
        }

//        boolean isExit = false;
//
//        if (((OrderRentManagerDetailsActivity) mContext).cars.contains(bean.getYfs_car_id())) {
//            choose_cb.setChecked(true);
//            for (int i = 0; i < ((OrderRentManagerDetailsActivity) mContext).addCarsBeans.size(); i++) {
//                if (((OrderRentManagerDetailsActivity) mContext).addCarsBeans.get(i).getYfs_car_id().equals(bean.getYfs_car_id())) {
//                    isExit = true;
//
//                    break;
//                }
//            }
//            if (!isExit) {
//                ((OrderRentManagerDetailsActivity) mContext).addCarsBeans.add(bean);
//            }
//        }


        choose_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ((OrderRentManagerDetailsActivity) mContext).addCarsBeans.add(bean);
                } else {
                    for (int i = 0; i < ((OrderRentManagerDetailsActivity) mContext).addCarsBeans.size(); i++) {
                        if (((OrderRentManagerDetailsActivity) mContext).addCarsBeans.get(i).getYfs_car_id().equals(bean.getYfs_car_id())) {
                            ((OrderRentManagerDetailsActivity) mContext).addCarsBeans.remove(i);
                            break;
                        }
                    }
                }
            }
        });

        tit_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (con_ll.isShown()) {
                    con_ll.setVisibility(View.GONE);
                    sanjiao_iv.setImageResource(R.mipmap.icon_sanjiao_down);
                } else {
                    con_ll.setVisibility(View.VISIBLE);
                    sanjiao_iv.setImageResource(R.mipmap.icon_sanjiao_up);
                }
            }
        });

        return convertView;
    }
}
