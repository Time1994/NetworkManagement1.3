package com.eroadcar.networkmanagement.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.activity.ChooseEmployeeActivity;
import com.eroadcar.networkmanagement.bean.EmployeeBean;
import com.eroadcar.networkmanagement.bean.StoreBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChooseEmployeesAdapter extends BaseExpandableListAdapter {

    private ArrayList<StoreBean> list;
    private Context mContext;

    private Map<Integer, ArrayList<ImageView>> imageViews = new HashMap<>();

    public ChooseEmployeesAdapter(Context context,
                                  ArrayList<StoreBean> data) {
        // TODO Auto-generated constructor stub
        list = data;
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getWks().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getWks().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.adapter_employee_chooses, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        final StoreBean fatherInfo = (StoreBean) getGroup(groupPosition);
        groupViewHolder.tv_shop_name.setText(fatherInfo.getPmg_points_name());//给父组件的标题设置值
        if (isExpanded) {
            groupViewHolder.up_iv.setImageResource(R.mipmap.icon_sanjiao_up);
        } else {
            groupViewHolder.up_iv.setImageResource(R.mipmap.icon_sanjiao_down);
        }

        groupViewHolder.ck_shop_all.setChecked(fatherInfo.isSelectedOr());
        groupViewHolder.ck_shop_all.setFocusable(false);
        groupViewHolder.ck_shop_all.setOnClickListener(new GroupCheckBoxClickListener(groupPosition));

        if (groupViewHolder.ck_shop_all.isChecked()) {
            ((ChooseEmployeeActivity) mContext).employeeBeans.addAll(fatherInfo.getWks());
        } else {
            ((ChooseEmployeeActivity) mContext).employeeBeans.removeAll(fatherInfo.getWks());
        }

//        groupViewHolder.ck_shop_all.setTag("0");
//        if (groupViewHolder.ck_shop_all.getTag().equals("0")) {
//            groupViewHolder.ck_shop_all.setTag("1");
//            ((ChooseEmployeeActivity) mContext).employeeBeans.addAll(fatherInfo.getWks());
//        } else {
//            groupViewHolder.ck_shop_all.setTag("0");
//            ((ChooseEmployeeActivity) mContext).employeeBeans.removeAll(fatherInfo.getWks());
//        }

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.adapter_employee_item_chooses, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        //子列表项的复选框

        final EmployeeBean classInfo = list.get(groupPosition).getWks().get(childPosition);
        childViewHolder.tv_shop_product_name.setText(classInfo.getMg_name());
        String tit = "销售顾问";
        if (classInfo.getMg_role_ids() == null || classInfo.getMg_role_ids().equals("")) {
        } else {
            if (classInfo.getMg_role_ids().contains("1")) {
                tit = "店长";
            }
            if (classInfo.getMg_role_ids().contains("2")) {
                tit = "销售经理";
            }
            if (classInfo.getMg_role_ids().contains("3")) {
                tit = "销售顾问";
            }
            if (classInfo.getMg_role_ids().contains("4")) {
                tit = "财务经理";
            }
        }
        childViewHolder.state_tv.setText(tit);
        childViewHolder.ck_shop_product.setChecked(classInfo.isSelectedOr());
        childViewHolder.ck_shop_product.setFocusable(false);
        childViewHolder.ck_shop_product.setOnClickListener(new ChildCheckBoxClidkListener(groupPosition, childPosition));

        if (childViewHolder.ck_shop_product.isChecked()) {
//            boolean isadd = true;
//            for (int i = 0; i < ((ChooseEmployeeActivity) mContext).employeeBeans.size(); i++) {
//                if (classInfo.getMg_id().equals(((ChooseEmployeeActivity) mContext).employeeBeans.get(i).getMg_id())) {
//                    isadd = false;
//                    break;
//                }
//            }
//            if (isadd) {
            ((ChooseEmployeeActivity) mContext).employeeBeans.add(classInfo);
//            }
        } else {
            ((ChooseEmployeeActivity) mContext).employeeBeans.remove(classInfo);
        }

//        childViewHolder.ck_shop_product.setTag("0");
//        if (childViewHolder.ck_shop_product.getTag().equals("0")) {
//            childViewHolder.ck_shop_product.setTag("1");
//
//            ((ChooseEmployeeActivity) mContext).employeeBeans.add(classInfo);
//        } else {
//            childViewHolder.ck_shop_product.setTag("0");
//
//            ((ChooseEmployeeActivity) mContext).employeeBeans.remove(classInfo);
//        }

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (childViewHolder.ck_shop_product.isChecked()) {
//                    childViewHolder.ck_shop_product.setChecked(false);
//                } else {
//                    childViewHolder.ck_shop_product.setChecked(true);
//                }
//            }
//        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * 父节点的CheckBox的Click监听器
     */
    class GroupCheckBoxClickListener implements View.OnClickListener {

        private int mGroupPosition;

        public GroupCheckBoxClickListener(int groupPosition) {
            mGroupPosition = groupPosition;
        }

        @Override
        public void onClick(View v) {
            list.get(mGroupPosition).toggle();

            // 将 Children 的 isChecked 全部设置跟 Group一样
            int childrenCount = list.get(mGroupPosition).getChildCount();
            boolean groupIsChecked = list.get(mGroupPosition).isSelectedOr();
            for (int i = 0; i < childrenCount; i++)
                list.get(mGroupPosition).getChildItem(i).setSelectedOr(groupIsChecked);

            // 注意，一定要通知 ExpandableListView 数据已经改变，
            //ExpandableListView 会重新绘制
            notifyDataSetChanged();
        }
    }

    /**
     * 子列表的CheckBox的Click监听器
     */
    class ChildCheckBoxClidkListener implements View.OnClickListener {

        private int mGroupPosition;
        private int mchildPosition;

        public ChildCheckBoxClidkListener(int groupPosition, int childPositon) {
            mGroupPosition = groupPosition;
            mchildPosition = childPositon;
        }

        @Override
        public void onClick(View v) {
            list.get(mGroupPosition).getChildItem(mchildPosition).toggle();
            // 检查Child CheckBox 是否有全部勾选，以控制Group的CheckBox
            int childrenCount = list.get(mGroupPosition).getChildCount();
            boolean childrenAllIsChecked = true;
            for (int i = 0; i < childrenCount; i++) {
                if (!list.get(mGroupPosition).getChildItem(i).isSelectedOr())
                    childrenAllIsChecked = false;
            }
            list.get(mGroupPosition).setSelectedOr(childrenAllIsChecked);

            // 注意，一定要通知 ExpandableListView 数据已经改变，
            //ExpandableListView 会重新绘制
            notifyDataSetChanged();
        }
    }

    //父组件Holder
    static class GroupViewHolder {
        private CheckBox ck_shop_all;
        private TextView tv_shop_name;
        private ImageView up_iv;

        public GroupViewHolder(View view) {
            ck_shop_all = (CheckBox) view.findViewById(R.id.ck_shop_all);
            tv_shop_name = (TextView) view.findViewById(R.id.store_tv);
            up_iv = (ImageView) view.findViewById(R.id.up_iv);
        }
    }

    //子组件的Holder
    static class ChildViewHolder {
        private CheckBox ck_shop_product;
        private TextView tv_shop_product_name, state_tv;

        public ChildViewHolder(View v) {
            ck_shop_product = (CheckBox) v.findViewById(R.id.ck_shop_product);
            tv_shop_product_name = (TextView) v.findViewById(R.id.name_tv);
            state_tv = (TextView) v.findViewById(R.id.state_tv);
        }
    }

}

