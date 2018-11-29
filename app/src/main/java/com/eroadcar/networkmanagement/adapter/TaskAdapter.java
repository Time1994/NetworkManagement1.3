package com.eroadcar.networkmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.TaskBean;

import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {
    private ArrayList<TaskBean> vector;
    private Context context;
    private String type;

    public TaskAdapter(ArrayList<TaskBean> vector, Context context, String t) {
        super();
        this.vector = vector;
        this.context = context;
        this.type = t;
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
                    R.layout.adapter_task, null);

        }

        TaskBean bean = vector.get(position);

        TextView name = (TextView) convertView.findViewById(R.id.name_tv);
        TextView state = (TextView) convertView.findViewById(R.id.state_tv);
        TextView tit = (TextView) convertView.findViewById(R.id.tit_tv);


        if (type.equals("j")) {
            tit.setText("发布人：" + bean.getJw_employer_name());
            name.setText("任务主题：" + bean.getJw_job_name());
            state.setText(bean.getJw_job_state());
            if (bean.getJw_job_state().equals("已处理")) {
                state.setTextColor(context.getResources().getColor(R.color.blue));
            } else {
                state.setTextColor(context.getResources().getColor(R.color.red));
            }
        } else {
            tit.setText("接收人：" + bean.getWj_employee_names());
            name.setText("任务主题：" + bean.getWj_job_name());
            state.setText(bean.getWj_create_time());
        }

        return convertView;
    }
}
