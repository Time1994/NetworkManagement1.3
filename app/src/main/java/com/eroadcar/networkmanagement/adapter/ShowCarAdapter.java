package com.eroadcar.networkmanagement.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.activity.BaseActivity;
import com.eroadcar.networkmanagement.activity.ShowCarActivity;
import com.eroadcar.networkmanagement.bean.CarImageBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ImageCacheManager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ShowCarAdapter extends BaseAdapter {
    private ArrayList<CarImageBean> vector;
    private Context context;
    private LayoutInflater infater = null;
    public Map<Integer, ImageView> map = new HashMap<Integer, ImageView>();

    public ShowCarAdapter(Context context, ArrayList<CarImageBean> vector) {
        super();
        this.vector = vector;
        this.context = context;
        infater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return vector.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return vector.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        ShowHolder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = infater.inflate(R.layout.adapter_showcar, null);
            holder = new ShowHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ShowHolder) convertView.getTag();
        }
        final CarImageBean showBean = vector.get(position);

        map.put(position, holder.car_iv);
//
//		Drawable mDefaultImageDrawable = context.getResources().getDrawable(
//				R.drawable.eq);
//		try {
//			ImageCacheManager.loadImage(
//					Constant.HTTP + showBean.getCi_car_img(), ImageCacheManager
//							.getImageListener(holder.car_iv,
//									mDefaultImageDrawable,
//									mDefaultImageDrawable));
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}

        ((BaseActivity) context).setImageh(Constant.HTTP + showBean.getCi_car_img(), holder.car_iv, R.drawable.eq);

        holder.car_iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                ((ShowCarActivity) context).showDialogImage(context,
                        Constant.HTTP + showBean.getCi_car_img());
            }
        });

        return convertView;
    }

    class ShowHolder {
        private ImageView car_iv;

        public ShowHolder(View view) {
            this.car_iv = (ImageView) view.findViewById(R.id.car_iv);
        }
    }

}
