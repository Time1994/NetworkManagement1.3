package com.eroadcar.networkmanagement.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.ChooseEmployeeAdapter;
import com.eroadcar.networkmanagement.adapter.ChooseEmployeesAdapter;
import com.eroadcar.networkmanagement.adapter.DialogItemAdapter;
import com.eroadcar.networkmanagement.adapter.MyExpandableListViewAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.EmployeeBean;
import com.eroadcar.networkmanagement.bean.OrderSalesBean;
import com.eroadcar.networkmanagement.bean.RoleBean;
import com.eroadcar.networkmanagement.bean.StoreBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

public class ChooseEmployeeActivity extends BaseActivity implements OnClickListener {
    private Button other_btn, back_btn;
    private TextView title_tv;
    private ExpandableListView product_elv;
    private LinearLayout none;

    private CommonBean<ArrayList<StoreBean>> commonBean;

    public ArrayList<EmployeeBean> employeeBeans = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        title_tv = (TextView) findViewById(R.id.title_tv);
        other_btn = (Button) findViewById(R.id.other_btn);
        back_btn = (Button) findViewById(R.id.back_btn);

        product_elv = (ExpandableListView) findViewById(R.id.product_elv);

        none = (LinearLayout) findViewById(R.id.none);

        back_btn.setOnClickListener(this);
        other_btn.setOnClickListener(this);

        title_tv.setText("选择接收人");
        back_btn.setText("");
        other_btn.setText("完成");
    }

    @Override
    protected void onResume() {
        super.onResume();

        getWorkersByShop("");
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.other_btn:
                if (employeeBeans.size() == 0) {
                    ToastUtils.showShort("请选择任务接收人");
                    return;
                }
                String ids = "", names = "";
                for (int i = 0; i < employeeBeans.size(); i++) {
                    if (!names.contains(employeeBeans.get(i).getMg_name())) {
                        ids += employeeBeans.get(i).getMg_id() + ",";
                        names += employeeBeans.get(i).getMg_name() + ",";
                    }
                }

                Intent intent = new Intent();
                intent.putExtra("wj_employee_ids", ids.substring(0, ids.length() - 1));
                intent.putExtra("wj_employee_names", names.substring(0, names.length() - 1));
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }

    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (commonBean.getState().equals("success")) {
                        if (commonBean.getData() != null && commonBean.getData().size() != 0) {
                            product_elv.setAdapter(new ChooseEmployeesAdapter(ChooseEmployeeActivity.this, commonBean.getData()));
                            product_elv.setGroupIndicator(null);

                            none.setVisibility(View.GONE);
                            product_elv.setVisibility(View.VISIBLE);
                        } else {
                            none.setVisibility(View.VISIBLE);
                            product_elv.setVisibility(View.GONE);
                        }
                    }
                    ToastUtils.showShort(commonBean.getMsg());
                    break;

                default:
                    break;
            }
        }
    };

    private void getWorkersByShop(String key) {
        loadingDialog.setMessage("正在获取数据...");
        loadingDialog.dialogShow();
        String url = Constant.GETSHOPWORKERSDETAIL;

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appcode", IMEI);
        map.put("apptype", Constant.APPTYPE);
        map.put("mg_id", application.userBean.getMg_id());
        map.put("mg_name", application.userBean.getMg_name());
        map.put("mg_shopsid", application.userBean.getMg_shopsid());
        map.put("mg_groupid", application.userBean.getMg_groupid());
        map.put("mg_shopname", application.userBean.getMg_shopname());
        map.put("mg_code", application.userBean.getMg_code());
        map.put("mg_groupname", application.userBean.getMg_groupname());
        map.put("mg_posid", application.userBean.getMg_posid());
        map.put("mg_posname", application.userBean.getMg_posname());
        map.put("mg_shopcode", application.userBean.getMg_shopcode());

        map.put("key", key);

        GsonRequest<CommonBean<ArrayList<StoreBean>>> requtst = new GsonRequest<CommonBean<ArrayList<StoreBean>>>(
                Request.Method.POST, url, listener_reqZxInfo);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<StoreBean>>> listener_reqZxInfo = new RequesListener<CommonBean<ArrayList<StoreBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<StoreBean>> arg0) {
            // TODO Auto-generated method stub
            commonBean = arg0;
            mHandler.sendEmptyMessage(1);
            loadingDialog.dismiss();
        }

        @Override
        public void onErrorResponse(VolleyError arg0) {
            // TODO Auto-generated method stub
            mHandler.sendEmptyMessage(-1);
            loadingDialog.dismiss();
        }

    };


    /**
     * 弹窗
     */
    @SuppressLint("NewApi")
    public void showDialogList(Context context, OrderSalesBean bean) {
        dialogMessage = new DialogPlus.Builder(context)
                .setContentHolder(new ViewHolder(R.layout.dialog_content_list))
                .setCancelable(true).setGravity(Gravity.CENTER)
                .setBackgroundColorResourceId(R.color.transparent).create();
        View content = dialogMessage.getHolderView();
        final ListView lv = (ListView) content
                .findViewById(R.id.content_lv);
        TextView title = (TextView) content.findViewById(R.id.text_dialog_title);
        title.setText("客户信息");
        ImageView close_iv = (ImageView) content.findViewById(R.id.close_iv);


        ArrayList<RoleBean> list = new ArrayList<>();
        RoleBean roleBean = new RoleBean();
        roleBean.setRole_app_id("客户姓名：");
        roleBean.setRole_app_name(bean.getYfs_sellsq_ownner());
        RoleBean roleBean1 = new RoleBean();
        roleBean1.setRole_app_id("手机号：");
        roleBean1.setRole_app_name(bean.getYfs_sellsq_cellphone());
        RoleBean roleBean2 = new RoleBean();
        roleBean2.setRole_app_id("客户类型：");
        if (bean.getYfs_sellsq_ywtype().equals("0")) {
            roleBean2.setRole_app_name("个人");
        } else {
            roleBean2.setRole_app_name("个人(非本地)");
        }
        RoleBean roleBean3 = new RoleBean();
        roleBean3.setRole_app_id("证件类型：");
        roleBean3.setRole_app_name(bean.getYfs_sellsq_idtype());
        RoleBean roleBean4 = new RoleBean();
        roleBean4.setRole_app_id("证件号码：");
        roleBean4.setRole_app_name(bean.getYfs_sellsq_id());
        RoleBean roleBean5 = new RoleBean();
        roleBean5.setRole_app_id("住址：");
        roleBean5.setRole_app_name(bean.getYfs_sellsq_addr());

        list.add(roleBean);
        list.add(roleBean1);
        list.add(roleBean2);
        list.add(roleBean3);
        list.add(roleBean4);
        list.add(roleBean5);

        lv.setAdapter(new DialogItemAdapter(ChooseEmployeeActivity.this, list));

        close_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMessage.dismiss();
            }
        });

        dialogMessage.show();
    }
}
