package com.eroadcar.networkmanagement.activity;

import java.util.HashMap;

import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.RegistBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.Logger;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.view.ClearEditText;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditPwdActivity extends BaseActivity implements OnClickListener {
    private Button back_btn, getcode_btn, sure_btn;

    private TextView title_tv, mobile_tv;
    private ClearEditText npwd_cet, rpwd_cet, code_cet, mobile_cet;

    private LinearLayout mobile_ll;

    private String mobile, CODE;
    private RegistBean yzbean;
    private CommonBean cbean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepwd);
        back_btn = (Button) findViewById(R.id.back_btn);
        getcode_btn = (Button) findViewById(R.id.getcode_btn);
        sure_btn = (Button) findViewById(R.id.sure_btn);
        mobile_tv = (TextView) findViewById(R.id.mobile_tv);
        title_tv = (TextView) findViewById(R.id.title_tv);
        npwd_cet = (ClearEditText) findViewById(R.id.npwd_cet);
        rpwd_cet = (ClearEditText) findViewById(R.id.rpwd_cet);
        code_cet = (ClearEditText) findViewById(R.id.code_cet);
        mobile_cet = (ClearEditText) findViewById(R.id.mobile_cet);

        mobile_ll = (LinearLayout) findViewById(R.id.mobile_ll);

        back_btn.setOnClickListener(this);
        getcode_btn.setOnClickListener(this);
        sure_btn.setOnClickListener(this);

        title_tv.setText(R.string.updatepwd);
        back_btn.setText("");

        try {
            mobile = application.userBean.getMg_cellphone();
            if (mobile != null && !mobile.equals("")) {
                mobile_tv.setText("手机号  " + mobile.substring(0, 3) + "****"
                        + mobile.substring(7, 11));

                mobile_tv.setVisibility(View.VISIBLE);
                mobile_ll.setVisibility(View.GONE);
            } else {
                mobile_tv.setVisibility(View.GONE);
                mobile_ll.setVisibility(View.VISIBLE);
                npwd_cet.setOnFocusChangeListener(new OnFocusChangeListener() {

                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        // TODO Auto-generated method stub
                        if (hasFocus) {
                            if (mobile_cet.getText().toString().length() < 6) {
                                ToastUtils.showShort("请输入手机号码");
                                mobile_cet.clearFocus();
                                mobile_cet.requestFocus();
                            }
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        rpwd_cet.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    if (npwd_cet.getText().toString().length() < 6) {
                        ToastUtils.showShort("请输入至少6位密码");
                        rpwd_cet.clearFocus();
                        npwd_cet.requestFocus();
                    }
                }
            }
        });
        code_cet.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    if (rpwd_cet.getText().toString().length() < 6) {
                        ToastUtils.showShort("请输入至少6位密码");
                        code_cet.clearFocus();
                        rpwd_cet.requestFocus();
                    }
                }
            }
        });

        code_cet.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (s.length() == 4) {
                    sure_btn.setEnabled(true);
                } else {
                    sure_btn.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void getVerificateCode(String mg_cellphone) {
        // TODO Auto-generated method stub
        loadingDialog.setMessage("正在获取验证码...");
        loadingDialog.dialogShow();
        String url = Constant.GETVERIFICATECODE;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appcode", IMEI);
        map.put("apptype", Constant.APPTYPE);
        map.put("mg_cellphone", mg_cellphone);
        GsonRequest<RegistBean> requtst = new GsonRequest<RegistBean>(
                Method.POST, url, listener_getVerificateCode);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
        Logger.getLogger().i("url==" + url);

    }

    private RequesListener<RegistBean> listener_getVerificateCode = new RequesListener<RegistBean>() {
        @Override
        public void onResponse(RegistBean arg0) {
            // TODO Auto-generated method stub
            yzbean = arg0;
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

    private void modifyPassword(String mg_cellphone, String repassword,
                                String newpassword, String verifycode) {
        // TODO Auto-generated method stub
        String url = Constant.MODIFYPASSWORD;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("mg_cellphone", mg_cellphone);
        map.put("repassword", repassword);
        map.put("newpassword", newpassword);
        map.put("verifycode", verifycode);
        GsonRequest<CommonBean> requtst = new GsonRequest<CommonBean>(
                Method.POST, url, listener_modifyPassword);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
        Logger.getLogger().i("url==" + url);

    }

    private RequesListener<CommonBean> listener_modifyPassword = new RequesListener<CommonBean>() {
        @Override
        public void onResponse(CommonBean arg0) {
            // TODO Auto-generated method stub
            cbean = arg0;
            mHandler.sendEmptyMessage(2);
        }

        @Override
        public void onErrorResponse(VolleyError arg0) {
            // TODO Auto-generated method stub
            mHandler.sendEmptyMessage(-1);
        }

    };

    Handler mHandler = new Handler() {// 18672250286
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (yzbean.getState().equals("success")) {
                        ToastUtils.showShort("验证码已发送,请注意查收短信!");
                        CODE = yzbean.getData();
                        MyCountDownTimer mc = new MyCountDownTimer(60000, 1000);
                        mc.start();
                    } else {
                        ToastUtils.showShort(yzbean.getMsg());
                    }
                    break;
                case 2:
                    if (cbean == null) {
                        ToastUtils.showShort("该用户没有注册或密码有误！");
                    } else {
                        SharedPreferences mySharedPreferences = getSharedPreferences(
                                "eroadcar", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mySharedPreferences
                                .edit();
                        editor.putString("phone", mobile);
                        // editor.putString("habit", "sleep");
                        editor.putString("pwd", "");
                        editor.commit();
                        Intent intent = new Intent(EditPwdActivity.this,
                                LoginActivity.class);
                        intent.putExtra("PHONE", mobile);
                        startActivity(intent);
                        ToastUtils.showShort(cbean.getMsg());
                    }
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.getcode_btn:
                if (mobile == null || mobile.equals("")) {
                    mobile = mobile_cet.getText().toString();
                }

                if (mobile.equals("") || mobile.length() != 11) {
                    ToastUtils.showShort("请输入正确的手机号码");
                    return;
                }
                getVerificateCode(mobile);
                break;
            case R.id.sure_btn:
                if (!code_cet.getText().toString().equals(CODE)) {
                    ToastUtils.showShort("短信验证码输入错误,请查看后重新输入");
                    return;
                }
                if (npwd_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请输入密码");
                    return;
                }
                if (rpwd_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请再次确认密码");
                    return;
                }
                if (!rpwd_cet.getText().toString().equals(npwd_cet.getText().toString())) {
                    ToastUtils.showShort("密码输入不一致，请确认重新输入");
                    return;
                }
                if (mobile != null && !mobile.equals("")) {
                    modifyPassword(mobile, rpwd_cet.getText().toString(), npwd_cet
                            .getText().toString(), CODE);
                } else {
                    modifyPassword(mobile_cet.getText().toString(), rpwd_cet.getText().toString(), npwd_cet
                            .getText().toString(), CODE);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 继承 CountDownTimer 防范
     * <p>
     * 重写 父类的方法 onTick() 、 onFinish()
     */

    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         *                          <p>
         *                          例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         *                          <p>
         *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            getcode_btn.setEnabled(true);
            getcode_btn.setText("获取验证码");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getcode_btn.setText(millisUntilFinished / 1000 + "秒");
            getcode_btn.setEnabled(false);
        }
    }
}
