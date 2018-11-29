package com.eroadcar.networkmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.activity.BaseActivity;
import com.eroadcar.networkmanagement.activity.EmployeeDetailsActivity;
import com.eroadcar.networkmanagement.activity.HomeActivity;
import com.eroadcar.networkmanagement.activity.TaskActivity;
import com.eroadcar.networkmanagement.bean.EmployeeBean;
import com.eroadcar.networkmanagement.bean.StoreBean;

import java.util.ArrayList;

public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

    private ArrayList<StoreBean> list;

    private Context mContext;

    public MyExpandableListViewAdapter(Context context,
                                       ArrayList<StoreBean> data) {
        // TODO Auto-generated constructor stub
        list = data;
        mContext = context;
    }


    @Override
    public Object getChild(int parentPos, int childPos) {
        return list.get(parentPos).getWks().get(childPos);
        // return dataset.get(parentList[parentPos]).get(childPos);
    }


    @Override
    public int getGroupCount() {
        return list.size();
    }


    @Override
    public int getChildrenCount(int parentPos) {
        return list.get(parentPos).getWks().size();
        // return dataset.get(parentList[parentPos]).size();
    }


    @Override
    public Object getGroup(int parentPos) {
        return list.get(parentPos);
        // return dataset.get(parentList[parentPos]);
    }


    @Override
    public long getGroupId(int parentPos) {
        return parentPos;
    }


    @Override
    public long getChildId(int parentPos, int childPos) {
        return childPos;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(int parentPos, boolean b, View convertView,
                             ViewGroup viewGroup) {
        if (convertView == null || convertView.getTag() == null) {
            convertView = ((LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.adapter_employee, null);
            // holder = new AccountHolder(convertView);
            // convertView.setTag(holder);
        }
        // else {
        // holder = (AccountHolder) convertView.getTag();
        // }

        TextView store_tv = (TextView) convertView.findViewById(R.id.store_tv);
        ImageView up_iv = (ImageView) convertView.findViewById(R.id.up_iv);

        if (b) {
            up_iv.setImageResource(R.mipmap.icon_sanjiao_up);
        } else {
            up_iv.setImageResource(R.mipmap.icon_sanjiao_down);
        }


        final StoreBean bean = list.get(parentPos);


        store_tv.setText(bean.getPmg_points_name() + " (" + bean.getWks().size() + ")");


        return convertView;
    }


    @Override
    public View getChildView(final int parentPos, int childPos, boolean b,
                             View convertView, ViewGroup viewGroup) {
        EmplyoeeViewHolder viewHolder = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = ((LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.adapter_employee_item, null);
            viewHolder = new EmplyoeeViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (EmplyoeeViewHolder) convertView.getTag();
        }

        final EmployeeBean trackData = list.get(parentPos).getWks()
                .get(childPos);

        String tit = "销售顾问";
        if (trackData.getMg_role_ids() == null || trackData.getMg_role_ids().equals("")) {
        } else {
            if (trackData.getMg_role_ids().contains("1")) {
                tit = "店长";
            }
            if (trackData.getMg_role_ids().contains("2")) {
                tit = "销售经理";
            }
            if (trackData.getMg_role_ids().contains("3")) {
                tit = "销售顾问";
            }
            if (trackData.getMg_role_ids().contains("4")) {
                tit = "财务经理";
            }
        }
        String state = "";
        if (trackData.getMg_isonline() != null && trackData.getMg_isonline().equals("1")) {
            state = "已登录";
            viewHolder.getStateView().setTextColor(mContext.getResources().getColor(R.color.blue));
        } else {
            if (trackData.getMg_lastlg_date() != null && !trackData.getMg_lastlg_date().equals("")) {
                state = "最近登录：" + trackData.getMg_lastlg_date();
                viewHolder.getStateView().setTextColor(mContext.getResources().getColor(R.color.blue));
            } else {
                state = "未登录";
                viewHolder.getStateView().setTextColor(mContext.getResources().getColor(R.color.red));
            }
        }

        viewHolder.getNameView().setText(trackData.getMg_name());
        viewHolder.getTitView().setText(tit);
        viewHolder.getStateView().setText(state);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EmployeeDetailsActivity.class);
                intent.putExtra("BEAN", trackData);
                ((BaseActivity) mContext).startActivity(intent);
            }
        });


        return convertView;
    }


    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}

class EmplyoeeViewHolder {
    View view;
    TextView name, state, tit;

    EmplyoeeViewHolder(View view) {
        this.view = view;
    }

    TextView getNameView() {
        if (name == null) {
            name = (TextView) view.findViewById(R.id.name_tv);
        }
        return name;
    }

    TextView getStateView() {
        if (state == null) {
            state = (TextView) view.findViewById(R.id.state_tv);
        }
        return state;
    }

    TextView getTitView() {
        if (tit == null) {
            tit = (TextView) view.findViewById(R.id.tit_tv);
        }
        return tit;
    }

}