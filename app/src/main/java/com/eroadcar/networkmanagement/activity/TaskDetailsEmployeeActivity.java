package com.eroadcar.networkmanagement.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.DialogItemAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.OrderSalesBean;
import com.eroadcar.networkmanagement.bean.RoleBean;
import com.eroadcar.networkmanagement.bean.TaskBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.view.ClearEditText;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskDetailsEmployeeActivity extends BaseActivity implements OnClickListener {
    private Button other_btn, back_btn;
    private TextView title_tv;

    private TextView tit_tv, content_tv, name_tv, time_tv, jieshou_tv;

    private Button sure_btn, cancel_btn;

    private TaskBean commonBean;

    private CommonBean bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details_employee);

        title_tv = (TextView) findViewById(R.id.title_tv);
        other_btn = (Button) findViewById(R.id.other_btn);
        back_btn = (Button) findViewById(R.id.back_btn);

        tit_tv = (TextView) findViewById(R.id.tit_tv);
        content_tv = (TextView) findViewById(R.id.content_tv);
        name_tv = (TextView) findViewById(R.id.name_tv);
        time_tv = (TextView) findViewById(R.id.time_tv);
        jieshou_tv = (TextView) findViewById(R.id.jieshou_tv);

        sure_btn = (Button) findViewById(R.id.sure_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);

        back_btn.setOnClickListener(this);
        other_btn.setOnClickListener(this);
        sure_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);

        title_tv.setText("任务详情");
        back_btn.setText("");
        other_btn.setText("");

        commonBean = (TaskBean) getIntent().getSerializableExtra("BEAN");
        tit_tv.setText("任务主题：" + commonBean.getJw_job_name());
        content_tv.setText(commonBean.getJw_job_context());
        name_tv.setText("发布人：" + commonBean.getJw_employer_name());
        time_tv.setText("发布时间：" + commonBean.getJw_create_time());
        jieshou_tv.setText(commonBean.getJw_employee_name());

        if (commonBean.getJw_job_state().equals("已处理")){
            cancel_btn.setVisibility(View.GONE);
            sure_btn.setEnabled(false);
            sure_btn.setText("已处理");
        }
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.sure_btn:
                showDialogMessage(TaskDetailsEmployeeActivity.this, "确认已处理任务吗？", new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dealWorkerJob(commonBean.getJw_job_id(), "已处理", "已处理");
                    }
                });
                break;
            case R.id.cancel_btn:
                showDialogWei(TaskDetailsEmployeeActivity.this);
                break;
            default:
                break;
        }
    }

    Handler mHandler = new Handler() {// 18672250286
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (bean.getState().equals("success")) {
                        onBackPressed();
                    }

                    ToastUtils.showShort(bean.getMsg());
                    break;

                default:
                    break;
            }
        }

        ;
    };

    private void dealWorkerJob(String jw_job_id, String jw_job_remark, String jw_job_state) {
        loadingDialog.setMessage("正在提交信息...");
        loadingDialog.dialogShow();
        String url = Constant.DEALWORKERJOB;
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

        map.put("ptc_shopid", application.userBean.getMg_shopsid());
        map.put("ptc_shopcode", application.userBean.getMg_shopcode());
        map.put("ptc_shopname", application.userBean.getMg_shopname());

        map.put("jw_job_id", jw_job_id);
        map.put("jw_job_remark", jw_job_remark);
        map.put("jw_job_state", jw_job_state);


        GsonRequest<CommonBean> requtst = new GsonRequest<CommonBean>(
                Request.Method.POST, url, listener_reqZxInfo);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean> listener_reqZxInfo = new RequesListener<CommonBean>() {
        @Override
        public void onResponse(CommonBean arg0) {
            // TODO Auto-generated method stub
            bean = arg0;
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
    public void showDialogWei(Context context) {
        dialogMessage = new DialogPlus.Builder(context)
                .setContentHolder(new ViewHolder(R.layout.dialog_wei))
                .setCancelable(true).setGravity(Gravity.CENTER)
                .setBackgroundColorResourceId(R.color.transparent).create();
        View content = dialogMessage.getHolderView();
        final ClearEditText content_cet = (ClearEditText) content
                .findViewById(R.id.content_cet);
        Button btn_dialog_confirm = (Button) content.findViewById(R.id.btn_dialog_confirm);
        ImageView close_iv = (ImageView) content.findViewById(R.id.close_iv);

        close_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMessage.dismiss();
            }
        });

        btn_dialog_confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (content_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请输入未处理原因");
                    return;
                }

                dealWorkerJob(commonBean.getJw_job_id(), content_cet.getText().toString(), "未处理");
            }
        });


        dialogMessage.show();
    }

}
