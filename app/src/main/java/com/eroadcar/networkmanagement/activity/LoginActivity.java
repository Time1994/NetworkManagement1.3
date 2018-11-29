package com.eroadcar.networkmanagement.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.eroadcar.networkmanagement.MainActivity;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.UserBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.IpV4Converter;
import com.eroadcar.networkmanagement.utils.Logger;
import com.eroadcar.networkmanagement.utils.MD5;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.utils.Tool;
import com.eroadcar.networkmanagement.view.ClearEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import java.text.SimpleDateFormat;

import cn.jpush.android.api.JPushInterface;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button login_btn;
    private ClearEditText pwd_cet, store_cet, employee_cet;
    private CheckBox choose_cb;
    private TextView forget_tv, tel_tv;

    private SharedPreferences mySharedPreferences;

    private CommonBean<UserBean> bean;
    private boolean isRember = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mySharedPreferences = getSharedPreferences("eroadcar", Context.MODE_PRIVATE);

        login_btn = (Button) findViewById(R.id.login_btn);

        pwd_cet = (ClearEditText) findViewById(R.id.pwd_cet);
        store_cet = (ClearEditText) findViewById(R.id.store_cet);
        employee_cet = (ClearEditText) findViewById(R.id.employee_cet);

        choose_cb = (CheckBox) findViewById(R.id.choose_cb);

        forget_tv = (TextView) findViewById(R.id.forget_tv);
        tel_tv = (TextView) findViewById(R.id.tel_tv);

        login_btn.setOnClickListener(this);
        forget_tv.setOnClickListener(this);
        tel_tv.setOnClickListener(this);

        if (!mySharedPreferences.getString("store", "").equals("")) {
            store_cet.setText(mySharedPreferences.getString("store", ""));
        }
        if (!mySharedPreferences.getString("user", "").equals("")) {
            employee_cet.setText(mySharedPreferences.getString("user", ""));
        }
        if (!mySharedPreferences.getString("pwd", "").equals("")) {
            pwd_cet.setText(mySharedPreferences.getString("pwd", ""));
            login_btn.setEnabled(true);

            loginXutils(store_cet.getText().toString(), employee_cet
                    .getText().toString(), pwd_cet.getText().toString());
        }

        employee_cet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    if (store_cet.getText().toString().equals("")) {
                        ToastUtils.showShort("请输入门店编号");
                        store_cet.requestFocus();
                        store_cet.setText("");
                    }
                }
            }
        });

        pwd_cet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    if (employee_cet.getText().toString().equals("")) {
                        ToastUtils.showShort("请输入员工编号");
                        employee_cet.requestFocus();
                        employee_cet.setText("");
                    }
                }
            }
        });

        pwd_cet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() > 0) {
                    login_btn.setEnabled(true);
                } else {
                    login_btn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        choose_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isRember = true;
                } else {
                    isRember = false;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        application.exit();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.login_btn:
                if (checkNetworkAvailable()) {
                    loginXutils(store_cet.getText().toString(), employee_cet
                            .getText().toString(), pwd_cet.getText().toString());
                } else {
                    showDialogMessage(LoginActivity.this, "网络故障，请检查网络！",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    dialogMessage.dismiss();
                                    Intent intent = new Intent(
                                            Settings.ACTION_SETTINGS);
                                    startActivity(intent);
                                }
                            });
                }
//                convert(employee_cet.getText().toString());

//                ToastUtils.showShort(new IpV4Converter().convert(employee_cet.getText().toString()) + "");
                break;

            case R.id.forget_tv:

                intent(EditPwdActivity.class);

                break;

            case R.id.tel_tv:
                intentCall("4009200665");
                break;
            default:
                break;
        }
    }


    public void loginXutils(String shopcode, String usercode, String pwd) {
        loadingDialog.setMessage("正在登录...");
        loadingDialog.dialogShow();
        RequestParams params = new RequestParams(); // 默认编码UTF-8
        // 添加文本信息
        // params.addQueryStringParameter("appcode", IMEI);
        params.addBodyParameter("appcode", IMEI);
        params.addBodyParameter("apptype", "android");
        params.addBodyParameter("shopcode", shopcode);
        params.addBodyParameter("usercode", usercode);
        params.addBodyParameter("password",
                Tool.encrypt(pwd, "1234567812345678"));
        params.addBodyParameter(
                "checkstr",
                MD5.Md5((new SimpleDateFormat("yyyyMMdd"))
                        .format(new java.util.Date()) + "eroadcar"));
        params.addBodyParameter("mg_registerid",
                JPushInterface.getRegistrationID(LoginActivity.this));

        HttpUtils http = new HttpUtils();
        http.send(HttpMethod.POST, Constant.LOGIN, params,
                new RequestCallBack<String>() {
                    @Override
                    // 开始提交
                    public void onStart() {
                        loadingDialog.setMessage("正在登录...");
                    }

                    @Override
                    // 正在提交
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        // if (isUploading) {
                        // loadingDialog.setMessage("上传图片中... ");
                        // } else {
                        // loadingDialog.setMessage("上传图片成功!");
                        // }
                    }

                    @Override
                    // 提交成功
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        System.out.println("返回数据＝" + result);

                        loadingDialog.dismiss();
                        Logger.getLogger().i(result);
                        bean = new Gson().fromJson(result,
                                new TypeToken<CommonBean<UserBean>>() {
                                }.getType());
                        mHandler.sendEmptyMessage(1);
                    }

                    @Override
                    // 提交失败
                    public void onFailure(HttpException error, String msg) {
                        // loadingDialog.setMessage(error.getExceptionCode() +
                        // ":"
                        // + msg);
                        loadingDialog.dismiss();
                        ToastUtils.showShort(error.getExceptionCode() + ":"
                                + msg);
                    }
                });
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (bean.getState().equals("success")) {

                        if (bean.getData().getMg_role_ids() == null || bean.getData().getMg_role_ids().equals("")) {
                            showDialogMessage(LoginActivity.this, "温馨提示", "   ", "确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogMessage.dismiss();
                                }
                            });
                        } else {
                            SharedPreferences.Editor editor = mySharedPreferences
                                    .edit();
                            editor.putString("store", store_cet.getText().toString());
                            editor.putString("user", employee_cet.getText().toString());
                            if (isRember) {
                                editor.putString("pwd", pwd_cet.getText().toString());
                            } else {
                                editor.putString("pwd", "");
                            }
                            editor.commit();

                            application.userBean = bean.getData();

                            intent(HomeActivity.class);
                            finish();
                        }

//                        if (bean.getData().getMg_shopsid().equals("0")) {
//                            application.user_status = "总部";
//                            intent(AccessCarNActivity.class);
//                        } else {
//                            Intent intent = null;
//                            if (bean.getData().getPmg_points_ywtype().equals("C")) {
//                                application.user_status = "维修";
//                                intent = new Intent(LoginActivity.this,
//                                        SalesActivity.class);
//                            } else {
//                                application.user_status = bean.getData()
//                                        .getMg_groupname();
//                                if (application.user_status.equals("行政")
//                                        || application.user_status.equals("管理员")) {
//                                    intent = new Intent(LoginActivity.this,
//                                            MainActivity.class);
//                                } else if (application.user_status.equals("维修")) {
//                                    // intent = new Intent(LoginActivity.this,
//                                    // WorkshopActivity.class);
//                                    intent = new Intent(LoginActivity.this,
//                                            RepairActivity.class);
//                                } else if (application.user_status.equals("租赁销售")) {
//                                    intent = new Intent(LoginActivity.this,
//                                            SalesActivity.class);
//                                }
//                            }
//                            try {
//                                startActivity(intent);
//                                overridePendingTransition(R.anim.anim_enter,
//                                        R.anim.anim_exit);
//                                finish();
//                            } catch (Exception e) {
//                                // TODO: handle exception
//                                ToastUtils.showShort("账号身份有误，请联系管理员！");
//                            }
//                        }
                    } else {
                        ToastUtils.showShort(bean.getMsg());
                    }
                    break;
                case -1:
                    break;
                default:
                    break;
            }
        }
    };

    public long convert(String string) {
        String[] strings = string.split("\\.");
        if (strings.length != 4) {
            ToastUtils.showShort("存在非法输入!");//封装toast提示信息
            return 0;
        }
        long a = 0l;
        for (int i = 0; i < strings.length; i++) {
            String s = strings[i].trim();
            if (s.equals("")) {
                ToastUtils.showShort("存在非法输入!");
                return 0;
            }
            if (s.indexOf(" ") != -1) {
                ToastUtils.showShort("存在非法字符!");
                return 0;
            }
            if (Integer.valueOf(s) > 255 || Integer.valueOf(s) < 0) {
                ToastUtils.showShort("存在非法输入!");
                return 0;
            }
            a += Long.parseLong(s) * Math.pow(256, (3 - i));
        }
        ToastUtils.showShort(a + "");
        return a;
    }


}
