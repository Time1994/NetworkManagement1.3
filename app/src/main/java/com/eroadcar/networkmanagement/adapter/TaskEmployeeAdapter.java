package com.eroadcar.networkmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.TaskBean;

import java.util.ArrayList;

public class TaskEmployeeAdapter extends BaseAdapter {
    private ArrayList<TaskBean.Worker> vector;
    private Context context;

    public TaskEmployeeAdapter(ArrayList<TaskBean.Worker> vector, Context context) {
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
                    R.layout.adapter_task_employee, null);

        }

        TaskBean.Worker bean = vector.get(position);

        TextView name = (TextView) convertView.findViewById(R.id.name_tv);
        TextView state = (TextView) convertView.findViewById(R.id.state_tv);
        TextView tit = (TextView) convertView.findViewById(R.id.reason_tv);
        ImageView state_iv = (ImageView) convertView.findViewById(R.id.state_iv);

        name.setText("接收人：" + bean.getJw_employee_name());
        state.setText(bean.getJw_job_state());
        if (bean.getJw_job_remark() == null || bean.getJw_job_remark().equals("")) {
            tit.setText("未处理原因：暂无");
        } else {
            tit.setText("未处理原因：" + bean.getJw_job_remark());
        }

        if (bean.getJw_job_state().contains("未")) {
            tit.setVisibility(View.GONE);
            state.setTextColor(context.getResources().getColor(R.color.blue));
            state_iv.setImageResource(R.mipmap.icon_task_wei);
            tit.setVisibility(View.VISIBLE);
        } else {
            tit.setVisibility(View.VISIBLE);
            state.setTextColor(context.getResources().getColor(R.color.yellow));
            state_iv.setImageResource(R.mipmap.icon_task_yi);
            tit.setVisibility(View.GONE);
        }

        return convertView;
    }
}
