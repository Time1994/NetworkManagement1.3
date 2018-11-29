package com.eroadcar.networkmanagement.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.DialogItemAdapter;
import com.eroadcar.networkmanagement.adapter.ZhengxAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.OrderSalesBean;
import com.eroadcar.networkmanagement.bean.RoleBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

public class ZhengxListActivity extends BaseActivity implements OnClickListener {
    private Button other_btn, back_btn;
    private TextView title_tv;
    private ListView dot_lv;
    private LinearLayout none;

    private CommonBean<ArrayList<OrderSalesBean>> commonBean;

    private ArrayList<OrderSalesBean> orderSalesBeans;

    private String yfs_sellsq_state = "", type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        title_tv = (TextView) findViewById(R.id.title_tv);
        other_btn = (Button) findViewById(R.id.other_btn);
        back_btn = (Button) findViewById(R.id.back_btn);

        dot_lv = (ListView) findViewById(R.id.dot_lv);

        none = (LinearLayout) findViewById(R.id.none);

        back_btn.setOnClickListener(this);
        other_btn.setOnClickListener(this);

        title_tv.setText("待处理订单");
        back_btn.setText("");
        other_btn.setText("");

//        Drawable drawable = getResources().getDrawable(R.mipmap.icon_order_state);
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        other_btn.setCompoundDrawables(null, null, drawable, null);

        type = getIntent().getStringExtra("FROM");

        dot_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDialogList(ZhengxListActivity.this, commonBean.getData().get(i));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSellOrder(yfs_sellsq_state);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.other_btn:
//                setPopWindow(yfs_sellsq_state);
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
                            orderSalesBeans = new ArrayList<>();

                            for (int i = 0; i < commonBean.getData().size(); i++) {
                                String t = commonBean.getData().get(i).getYfs_sellsq_state();
                                if (type.equals("choose")) {
                                    if (t.equals("征信通过") || t.equals("征信中") || t.equals("征信驳回")) {
                                        orderSalesBeans.add(commonBean.getData().get(i));
                                    }
                                } else {
                                    if (t.equals("已收全款")) {//if (t.equals("已审核充电桩")) {
                                        orderSalesBeans.add(commonBean.getData().get(i));
                                    }
                                }
                            }
                            if (orderSalesBeans.size() != 0) {
                                dot_lv.setAdapter(new ZhengxAdapter(ZhengxListActivity.this, orderSalesBeans, type));
                                none.setVisibility(View.GONE);
                                dot_lv.setVisibility(View.VISIBLE);
                            } else {
                                none.setVisibility(View.VISIBLE);
                                dot_lv.setVisibility(View.GONE);
                            }
                        } else {
                            none.setVisibility(View.VISIBLE);
                            dot_lv.setVisibility(View.GONE);
                        }
                    }
                    ToastUtils.showShort(commonBean.getMsg());
                    break;

                default:
                    break;
            }
        }
    };

    private void getSellOrder(String yfs_sellsq_state) {
        loadingDialog.setMessage("正在获取数据...");
        loadingDialog.dialogShow();
        String url = "";

//        if (application.userBean.getMg_role_ids().contains("1") || application.userBean.getMg_role_ids().contains("2")) {
//            url = Constant.GETSELLORDERFORMANAGER;
//        } else {
        url = Constant.GETSELLORDERFORSELLER;
//        }

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

        map.put("yfs_sellsq_state", yfs_sellsq_state);

        GsonRequest<CommonBean<ArrayList<OrderSalesBean>>> requtst = new GsonRequest<CommonBean<ArrayList<OrderSalesBean>>>(
                Request.Method.POST, url, listener_reqZxInfo);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<OrderSalesBean>>> listener_reqZxInfo = new RequesListener<CommonBean<ArrayList<OrderSalesBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<OrderSalesBean>> arg0) {
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

    private void setPopWindow(String type) {
        //准备PopupWindow的布局View
        final View popupView = LayoutInflater.from(this).inflate(R.layout.popup, null);
        //初始化一个PopupWindow，width和height都是WRAP_CONTENT
        final PopupWindow popupWindow = new PopupWindow(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow的视图内容
        popupWindow.setContentView(popupView);
        //点击空白区域PopupWindow消失，这里必须先设置setBackgroundDrawable，否则点击无反应
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
        //设置PopupWindow动画
        popupWindow.setAnimationStyle(R.style.AnimHorizontal);
        //设置是否允许PopupWindow的范围超过屏幕范围
        popupWindow.setClippingEnabled(true);
        //设置PopupWindow消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        if (type.equals("")) {
            ((RadioButton) popupView.findViewById(R.id.all_rb)).setChecked(true);
        } else if (type.equals("征信中")) {
            ((RadioButton) popupView.findViewById(R.id.ing_rb)).setChecked(true);
        } else if (type.equals("征信通过")) {
            ((RadioButton) popupView.findViewById(R.id.tong_rb)).setChecked(true);
        } else if (type.equals("征信驳回")) {
            ((RadioButton) popupView.findViewById(R.id.bohui_rb)).setChecked(true);
        }

        RadioGroup state_rg = (RadioGroup) popupView.findViewById(R.id.state_rg);

        state_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.all_rb) {
                    yfs_sellsq_state = "";
                } else if (i == R.id.ing_rb) {
                    yfs_sellsq_state = "征信中";
                } else if (i == R.id.tong_rb) {
                    yfs_sellsq_state = "征信通过";
                } else if (i == R.id.bohui_rb) {
                    yfs_sellsq_state = "征信驳回";
                }
                popupWindow.dismiss();
                getSellOrder(yfs_sellsq_state);
            }
        });

        //PopupWindow在targetView下方弹出
        popupWindow.showAsDropDown(other_btn);
    }

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

        lv.setAdapter(new DialogItemAdapter(ZhengxListActivity.this, list));

        close_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMessage.dismiss();
            }
        });

        dialogMessage.show();
    }
}
