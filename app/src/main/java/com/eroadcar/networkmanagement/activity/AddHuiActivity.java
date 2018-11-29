package com.eroadcar.networkmanagement.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.QianKeBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.utils.Tool;
import com.eroadcar.networkmanagement.view.ClearEditText;
import com.eroadcar.networkmanagement.view.widget.NumericWheelAdapter;
import com.eroadcar.networkmanagement.view.widget.OnWheelChangedListener;
import com.eroadcar.networkmanagement.view.widget.OnWheelScrollListener;
import com.eroadcar.networkmanagement.view.widget.StringWheelAdapter;
import com.eroadcar.networkmanagement.view.widget.WheelView;

public class AddHuiActivity extends BaseActivity implements OnClickListener {
    private Button other_btn, back_btn, sure_btn;
    private TextView title_tv;

    private TextView time_tv;
    private ClearEditText vin_cet, type_cet, address_cet, huod_cet;

    private RadioGroup as_rg;

    private CheckBox checkbox3, checkbox2, checkbox1;

    private String zhud = "", yishic = "", yijiao = "";

    private String sex = "是";

    private CommonBean commonBean;

    private QianKeBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addhui);

        title_tv = (TextView) findViewById(R.id.title_tv);
        other_btn = (Button) findViewById(R.id.other_btn);
        back_btn = (Button) findViewById(R.id.back_btn);
        sure_btn = (Button) findViewById(R.id.sure_btn);

        vin_cet = (ClearEditText) findViewById(R.id.vin_cet);
        type_cet = (ClearEditText) findViewById(R.id.type_cet);
        huod_cet = (ClearEditText) findViewById(R.id.huod_cet);
        address_cet = (ClearEditText) findViewById(R.id.address_cet);

        as_rg = (RadioGroup) findViewById(R.id.as_rg);

        time_tv = (TextView) findViewById(R.id.time_tv);

        checkbox3 = (CheckBox) findViewById(R.id.checkbox3);
        checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
        checkbox1 = (CheckBox) findViewById(R.id.checkbox1);

        back_btn.setOnClickListener(this);
        other_btn.setOnClickListener(this);
        sure_btn.setOnClickListener(this);

        time_tv.setOnClickListener(this);

        title_tv.setText("新增回访");
        back_btn.setText("");

        bean = (QianKeBean) getIntent().getSerializableExtra("BEAN");

        vin_cet.setText(bean.getPtc_adviser());

        as_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == R.id.man_rb) {
                    sex = "是";
                } else {
                    sex = "否";
                }
            }
        });

        checkbox1
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton arg0,
                                                 boolean arg1) {
                        // TODO Auto-generated method stub
                        if (arg1) {
                            zhud = "02";
                        } else {
                            zhud = "";
                        }
                    }

                });
        checkbox2
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton arg0,
                                                 boolean arg1) {
                        // TODO Auto-generated method stub
                        if (arg1) {
                            yishic = "03";
                        } else {
                            yishic = "";
                        }
                    }

                });

        checkbox3
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton arg0,
                                                 boolean arg1) {
                        // TODO Auto-generated method stub
                        if (arg1) {
                            yijiao = "04";
                        } else {
                            yijiao = "";
                        }
                    }

                });

        setCurDate(null);
    }

    private void newPotentVisitContext(String frd_ptc_id, String frd_cname,
                                       String frd_csex, String frd_cmobile, String frd_clevel,
                                       String frd_cstate, String frd_cerp, String frd_cadviser,
                                       String frd_cisernet, String frd_cadd_date, String frd_crevist_date,
                                       String frd_lon, String frd_lat, String frd_crevist_context,
                                       String frd_follow_state, String frd_importance,
                                       String frd_visit_addr, String frd_visit_action) {
        loadingDialog.setMessage("正在新增回访信息...");
        loadingDialog.dialogShow();
        String url = Constant.NEWPOTENTVISITCONTEXT;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appcode", IMEI);
        map.put("apptype", Constant.APPTYPE);
        map.put("mg_id", application.userBean.getMg_id());
        map.put("mg_name", application.userBean.getMg_name());
        map.put("mg_shopsid", application.userBean.getMg_shopsid());
        map.put("mg_groupid", application.userBean.getMg_groupid());
        map.put("mg_shopname", application.userBean.getMg_shopname());
        map.put("mg_shopcode", application.userBean.getMg_shopcode());
        map.put("mg_code", application.userBean.getMg_code());

        map.put("frd_ptc_id", frd_ptc_id);
        map.put("frd_cname", frd_cname);
        map.put("frd_csex", frd_csex);
        map.put("frd_cmobile", frd_cmobile);
        map.put("frd_clevel", frd_clevel);
        map.put("frd_cstate", frd_cstate);
        map.put("frd_cerp", frd_cerp);
        map.put("frd_cadviser", frd_cadviser);
        map.put("frd_cisernet", frd_cisernet);
        map.put("frd_cadd_date", frd_cadd_date);
        map.put("frd_crevist_date", frd_crevist_date);
        map.put("frd_lon", frd_lon);
        map.put("frd_lat", frd_lat);
        map.put("frd_crevist_context", frd_crevist_context);
        map.put("frd_follow_state", frd_follow_state);
        map.put("frd_importance", frd_importance);
        map.put("frd_visit_addr", frd_visit_addr);
        map.put("frd_visit_action", frd_visit_action);

        GsonRequest<CommonBean> requtst = new GsonRequest<CommonBean>(
                Method.POST, url, listener_getWorkersDb);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean> listener_getWorkersDb = new RequesListener<CommonBean>() {
        @Override
        public void onResponse(CommonBean arg0) {
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

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (commonBean != null
                            && commonBean.getState().equals("success")) {
                        onBackPressed();

                    }
                    ToastUtils.showShort(commonBean.getMsg());
                    break;
                case -1:
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
            case R.id.sure_btn:
//			if (vin_cet.getText().toString().equals("")) {
//				ToastUtils.showShort("请填写回访人");
//				return;
//			}
                if (type_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请填写回访内容");
                    return;
                }
                String s = "";
                if (!zhud.equals("")) {
                    s += zhud + ",";
                }
                if (!yishic.equals("")) {
                    s += yishic + ",";
                }
                if (!yijiao.equals("")) {
                    s += yijiao + ",";
                }

                newPotentVisitContext(bean.getPtc_id(), bean.getPtc_name(),
                        bean.getPtc_sex(), bean.getPtc_mobile(),
                        bean.getPtc_level(), bean.getPtc_state(),
                        bean.getPtc_erp(), bean.getPtc_adviser(), sex, "",
                        time.toString(), "121.231", "32.124", type_cet.getText()
                                .toString(), "跟进中", s.substring(0, s.length() - 1),
                        address_cet.getText().toString(), huod_cet.getText()
                                .toString());
                break;
            case R.id.time_tv:
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(time_tv.getWindowToken(), 0);

                if (null != popupwindowd && popupwindowd.isShowing()) {
                    popupwindowd.dismiss();
                } else {
                    initPopupWindow(time_tv);
                    popupwindowd.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                }
                break;
            default:
                break;
        }
    }

    private View convertView;
    private PopupWindow popupwindowd;
    // 用来保存年月日：
    private int mYear, mMonth, mDay, mHours, mMinutes, mWay;
    private WheelView year_wv, month_wv, date_wv;
    private java.text.DecimalFormat fo;
    private boolean timeChanged = false, timeScrolled = false;
    private StringBuilder time;
    private String[] stringy = {"2016", "2017", "2018", "2019", "2020",
            "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028",
            "2029", "2030"};

    private void setCurDate(TextView time_tv) {
        fo = new java.text.DecimalFormat("00");
        // 获得当前的日期：
        final Calendar currentDate = Calendar.getInstance();
        mYear = currentDate.get(Calendar.YEAR);
        mMonth = currentDate.get(Calendar.MONTH) + 1;
        mDay = currentDate.get(Calendar.DAY_OF_MONTH);
        mHours = currentDate.get(Calendar.HOUR_OF_DAY);
        mMinutes = currentDate.get(Calendar.MINUTE);
        mWay = currentDate.get(Calendar.DAY_OF_WEEK);

        if (mMinutes >= 30) {
            mHours++;
            mMinutes = 0;
        } else {
            mMinutes = 30;
        }

        // time = new StringBuilder().append(mYear).append("-")
        // .append(fo.format(mMonth)).append("-")
        // // 得到的月份+1，因为从0开始
        // .append(fo.format(mDay)).append(" ").append(fo.format(mHours))
        // .append(":").append(fo.format(mMinutes)).append(":00");
        time = new StringBuilder().append(mYear).append("-")
                .append(fo.format(mMonth)).append("-")
                // 得到的月份+1，因为从0开始
                .append(fo.format(mDay)).append(" ").append(fo.format(mHours))
                .append(":").append(fo.format(mMinutes));
        StringBuilder timeText = new StringBuilder()
                .append(fo.format(mMonth))
                .append("月")
                // 得到的月份+1，因为从0开始
                .append(fo.format(mDay)).append("日 ").append(fo.format(mHours))
                .append(":").append(fo.format(mMinutes)).append(" ")
                .append(Tool.getWeek(mWay + ""));

        if (time_tv != null) {
            time_tv.setText(timeText);
        }
    }

    // 选择时间
    public void initPopupWindow(final TextView time_tv) {
        convertView = getLayoutInflater().inflate(R.layout.dialog_date_day,
                null, false);
        popupwindowd = new PopupWindow(convertView,
                LinearLayout.LayoutParams.FILL_PARENT, getResources()
                .getDimensionPixelSize(R.dimen.hundredte), true);
        popupwindowd.setAnimationStyle(R.style.AnimationPhoto);

        Button submit = (Button) convertView.findViewById(R.id.sure_btn);
        TextView title = (TextView) convertView.findViewById(R.id.title_tv);
        ImageView cancel_iv = (ImageView) convertView
                .findViewById(R.id.cancel_iv);

        year_wv = (WheelView) convertView.findViewById(R.id.mins);
        month_wv = (WheelView) convertView.findViewById(R.id.data);
        date_wv = (WheelView) convertView.findViewById(R.id.hour);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        title.setText("回访日期");

        year_wv.setAdapter(new StringWheelAdapter(stringy));
        year_wv.setLabel("");
        year_wv.setCyclic(true);

        month_wv.setAdapter(new NumericWheelAdapter(1, 12, "%02d", "月"));
        month_wv.setLabel("");
        month_wv.setCyclic(true);

        date_wv.setAdapter(new NumericWheelAdapter(1, 31, "%02d", "日"));
        date_wv.setLabel("");
        date_wv.setCyclic(true);

        year_wv.setCurrentItem(2);
        month_wv.setCurrentItem(mMonth - 1);
        date_wv.setCurrentItem(mDay - 1);

        OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!timeScrolled) {
                    timeChanged = true;

                    timeChanged = false;
                }
            }
        };

        year_wv.addChangingListener(wheelListener);
        month_wv.addChangingListener(wheelListener);
        date_wv.addChangingListener(wheelListener);

        OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
                timeScrolled = true;
            }

            public void onScrollingFinished(WheelView wheel) {
                timeScrolled = false;
                timeChanged = true;
                try {
                    timeChanged = false;
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        };

        year_wv.addScrollingListener(scrollListener);
        month_wv.addScrollingListener(scrollListener);
        date_wv.addScrollingListener(scrollListener);

        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String tString = stringy[year_wv.getCurrentItem()] + "年"
                        + fo.format(month_wv.getCurrentItem() + 1) + "月"
                        + fo.format(date_wv.getCurrentItem() + 1) + "日  ";
                time_tv.setText(tString);
                // time = new StringBuilder().append(mYear)
                // .append("-")
                // .append(fo.format(month_wv.getCurrentItem() + 1))
                // .append("-")
                // // 得到的月份+1，因为从0开始
                // .append(fo.format(date_wv.getCurrentItem() + 1))
                // .append(" ").append(strings[year_wv.getCurrentItem()])
                // .append(":00");
                time = new StringBuilder()
                        .append(stringy[year_wv.getCurrentItem()]).append("-")
                        .append(fo.format(month_wv.getCurrentItem() + 1))
                        .append("-")
                        // 得到的月份+1，因为从0开始
                        .append(fo.format(date_wv.getCurrentItem() + 1));
                popupwindowd.dismiss();
            }
        });

        cancel_iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupwindowd.dismiss();
            }
        });

        backgroundAlpha(0.5f);
        popupwindowd.setOnDismissListener(new poponDismissListener());
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupwindowd.setBackgroundDrawable(new BitmapDrawable());
        // 使其聚集
        popupwindowd.setFocusable(true);
        popupwindowd.setTouchable(true); // 设置PopupWindow可触摸
        // 设置允许在外点击消失
        popupwindowd.setOutsideTouchable(true);
        // 刷新状态
        popupwindowd.update();
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            backgroundAlpha(1f);
        }

    }
}
