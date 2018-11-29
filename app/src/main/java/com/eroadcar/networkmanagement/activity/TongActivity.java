package com.eroadcar.networkmanagement.activity;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.HomeAdapter;
import com.eroadcar.networkmanagement.adapter.StatisticsAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.HomeBean;
import com.eroadcar.networkmanagement.bean.StatisticsBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.view.widget.NumericWheelAdapter;
import com.eroadcar.networkmanagement.view.widget.OnWheelChangedListener;
import com.eroadcar.networkmanagement.view.widget.OnWheelScrollListener;
import com.eroadcar.networkmanagement.view.widget.WheelView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class TongActivity extends BaseActivity implements View.OnClickListener {
    private Button back_btn;
    private TextView title_tv;
    private ImageView calendar_iv;

    private TextView time_tv;
    private RadioGroup time_rg;

    private ListView dot_lv;

    private CommonBean<ArrayList<StatisticsBean>> commobBean;
    private String type = "day", date = "";

    private int year, monthi, dayi, weeki;
    private String month, day, week, timeType = "day";

    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tong);

        back_btn = (Button) findViewById(R.id.back_btn);

        title_tv = (TextView) findViewById(R.id.title_tv);

        time_tv = (TextView) findViewById(R.id.time_tv);

        time_rg = (RadioGroup) findViewById(R.id.time_rg);

        dot_lv = (ListView) findViewById(R.id.dot_lv);
        calendar_iv = (ImageView) findViewById(R.id.calendar_iv);

        back_btn.setOnClickListener(this);
        calendar_iv.setOnClickListener(this);
        title_tv.setText("数据统计");
        back_btn.setText("");


        setTime();
        time_tv.setText(date);
        setCurDate();
        setCurDates();
        time_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                if (arg1 == R.id.day_rb) {
                    type = "day";

                    date = year + "-" + month + "-" + day;
                    timeType = "day";
                    time_tv.setText(date);
                } else if (arg1 == R.id.month_rb) {
                    type = "month";
                    date = year + "-" + month + "-" + day;
                    time_tv.setText(year + "-" + month );
                    timeType = "month";

                }


                reqStatisticsForManager(type, date);
            }
        });

        dot_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TongActivity.this, StatisticsActivity.class);
                intent.putExtra("CODE", commobBean.getData().get(i).getXs_shop_code());
                startActivity(intent);
            }
        });


        reqStatisticsForManager(type, date);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.calendar_iv:
                if (timeType.equals("day")) {
                    setDate("day", time_tv);
                } else {
                    setDates("month", time_tv);
                }

                break;
            default:
                break;
        }
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (commobBean.getState().equals("success")) {
                        dot_lv.setAdapter(new StatisticsAdapter(TongActivity.this, commobBean.getData()));
                    }
                    ToastUtils.showShort(commobBean.getMsg());
                    break;

                case -1:
                    break;
                default:
                    break;
            }
        }
    };

    private void reqStatisticsForManager(String type, String data) {
        loadingDialog.setMessage("正在获取数据...");
        loadingDialog.dialogShow();
        String url = Constant.REQSTATISTICSBYMWD;
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
        map.put("mg_groupname", application.userBean.getMg_groupname());
        map.put("mg_posid", application.userBean.getMg_posid());
        map.put("mg_posname", application.userBean.getMg_posname());

        map.put("shoptype", "0");


        map.put("type", type);
        map.put("date", data);


        GsonRequest<CommonBean<ArrayList<StatisticsBean>>> requtst = new GsonRequest<CommonBean<ArrayList<StatisticsBean>>>(
                Request.Method.POST, url, listener_getInshopCars);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<StatisticsBean>>> listener_getInshopCars = new RequesListener<CommonBean<ArrayList<StatisticsBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<StatisticsBean>> arg0) {
            // TODO Auto-generated method stub
            commobBean = arg0;
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


    private void setTime() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthi = calendar.get(Calendar.MONTH) + 1;
        dayi = calendar.get(Calendar.DAY_OF_MONTH);
        weeki = calendar.get(Calendar.WEEK_OF_YEAR);

        month = dataLong(monthi);
        day = dataLong(dayi);
        week = dataLong(weeki);

        date = year + "-" + month + "-" + day;
    }

    private void setDate(String t, TextView time_tv) {
        timeType = t;
        if (null != popupwindowd && popupwindowd.isShowing()) {
            popupwindowd.dismiss();
        } else {
            initPopupWindow(time_tv);
            popupwindowd.showAtLocation(time_tv, Gravity.BOTTOM, 0, 0);
        }
    }

    private void setDates(String t, TextView time_tv) {
        timeType = t;
        if (null != popupwindowds && popupwindowds.isShowing()) {
            popupwindowds.dismiss();
        } else {
            initPopupWindows(time_tv);
            popupwindowds.showAtLocation(time_tv, Gravity.BOTTOM, 0, 0);
        }
    }

    private View convertView;
    private PopupWindow popupwindowd;
    // 用来保存年月日：
    private int mYear, mMonth, mDay;// , mHours, mMinutes, mWay;
    private WheelView year_wv, month_wv, date_wv;
    private java.text.DecimalFormat fo;
    private boolean timeChanged = false, timeScrolled = false;
    private StringBuilder time;

    // private String[] strings = { "2017", "2018", "2019", "2020", "02:30",
    // "03:00", "03:30", "04:00", "04:30", "05:00", "05:30", "06:00",
    // "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30",
    // "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00",
    // "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
    // "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00",
    // "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30",
    // "00:00" };

    private void setCurDate() {
        fo = new java.text.DecimalFormat("00");
        // 获得当前的日期：
        final Calendar currentDate = Calendar.getInstance();
        mYear = currentDate.get(Calendar.YEAR);
        mMonth = currentDate.get(Calendar.MONTH) + 1;
        mDay = currentDate.get(Calendar.DAY_OF_MONTH);

        time = new StringBuilder().append(mYear).append("-")
                .append(fo.format(mMonth)).append("-")
                // 得到的月份+1，因为从0开始
                .append(fo.format(mDay));
    }


    // 选择时间
    public void initPopupWindow(final TextView time_tv) {
        convertView = getLayoutInflater().inflate(R.layout.dialog_date_year,
                null, false);
        popupwindowd = new PopupWindow(convertView,
                LinearLayout.LayoutParams.FILL_PARENT, getResources()
                .getDimensionPixelSize(R.dimen.hundredt), true);
        popupwindowd.setAnimationStyle(R.style.AnimationPhoto);

        Button submit = (Button) convertView.findViewById(R.id.sure_btn);
        TextView title = (TextView) convertView.findViewById(R.id.title_tv);
        TextView close_tv = (TextView) convertView.findViewById(R.id.close_tv);
        ImageView cancel_iv = (ImageView) convertView
                .findViewById(R.id.cancel_iv);

        year_wv = (WheelView) convertView.findViewById(R.id.mins);
        month_wv = (WheelView) convertView.findViewById(R.id.data);
        date_wv = (WheelView) convertView.findViewById(R.id.hour);

//        if (timeType.equals("S")) {
//            title.setText("选择起始时间");
//        } else {
//            title.setText("选择结束时间");
//        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        // year_wv.setAdapter(new StringWheelAdapter(strings));
        // year_wv.setLabel("");
        // year_wv.setCyclic(true);

        year_wv.setAdapter(new NumericWheelAdapter(2017, 2117, "%02d", "年"));
        year_wv.setLabel("");
        year_wv.setCyclic(true);

        month_wv.setAdapter(new NumericWheelAdapter(1, 12, "%02d", "月"));
        month_wv.setLabel("");
        month_wv.setCyclic(true);

        date_wv.setAdapter(new NumericWheelAdapter(1, 31, "%02d", "日"));
        date_wv.setLabel("");
        date_wv.setCyclic(true);

        year_wv.setCurrentItem(mYear - 2017);
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

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String tString = (year_wv.getCurrentItem() + 2017) + "-"
                        + fo.format(month_wv.getCurrentItem() + 1) + "-"
                        + fo.format(date_wv.getCurrentItem() + 1);//
                // + strings[year_wv.getCurrentItem()];
                time_tv.setText(tString);
                reqStatisticsForManager(timeType, tString);
                // time = new StringBuilder().append(mYear)
                // .append("-")
                // .append(fo.format(month_wv.getCurrentItem() + 1))
                // .append("-")
                // // 得到的月份+1，因为从0开始
                // .append(fo.format(date_wv.getCurrentItem() + 1))
                // .append(" ").append(strings[year_wv.getCurrentItem()])
                // .append(":00");
                // time = new StringBuilder().append(month_wv.getCurrentItem())
                // .append("-")
                // .append(fo.format(month_wv.getCurrentItem() + 1))
                // .append("-")
                // // 得到的月份+1，因为从0开始
                // .append(fo.format(date_wv.getCurrentItem() + 1));

                popupwindowd.dismiss();
            }
        });

        close_tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupwindowd.dismiss();
            }
        });

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupwindowd.setBackgroundDrawable(new BitmapDrawable());
        // 使其聚集
        popupwindowd.setFocusable(true);
        popupwindowd.setTouchable(true); // 设置PopupWindow可触摸
        // 设置允许在外点击消失
        popupwindowd.setOutsideTouchable(true);

        backgroundAlpha(0.5f);
        popupwindowd.setOnDismissListener(new poponDismissListener());

        // 刷新状态
        popupwindowd.update();
    }


    private View convertViews;
    private PopupWindow popupwindowds;
    // 用来保存年月日：
    private int mYears, mMonths;//, mDay;// , mHours, mMinutes, mWay;
    private WheelView year_wvs, month_wvs, date_wvs;
    private java.text.DecimalFormat fos;
    private boolean timeChangeds = false, timeScrolleds = false;
    private StringBuilder times;

    // private String[] strings = { "2017", "2018", "2019", "2020", "02:30",
    // "03:00", "03:30", "04:00", "04:30", "05:00", "05:30", "06:00",
    // "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30",
    // "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00",
    // "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
    // "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00",
    // "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30",
    // "00:00" };
    private void setCurDates() {
        fos = new java.text.DecimalFormat("00");
        // 获得当前的日期：
        final Calendar currentDate = Calendar.getInstance();
        mYears = currentDate.get(Calendar.YEAR);
        mMonths = currentDate.get(Calendar.MONTH) + 1;
//        mDays = currentDate.get(Calendar.DAY_OF_MONTH);

        time = new StringBuilder().append(mYear).append("-")
                .append(fo.format(mMonths));
//                .append("-")
//                // 得到的月份+1，因为从0开始
//                .append(fo.format(mDay));
    }

    // 选择时间
    public void initPopupWindows(final TextView time_tv) {
        convertViews = getLayoutInflater().inflate(R.layout.dialog_date_month,
                null, false);
        popupwindowds = new PopupWindow(convertViews,
                LinearLayout.LayoutParams.FILL_PARENT, getResources()
                .getDimensionPixelSize(R.dimen.hundredt), true);
        popupwindowds.setAnimationStyle(R.style.AnimationPhoto);

        Button submit = (Button) convertViews.findViewById(R.id.sure_btn);
        TextView title = (TextView) convertViews.findViewById(R.id.title_tv);
        TextView close_tv = (TextView) convertViews.findViewById(R.id.close_tv);
        ImageView cancel_iv = (ImageView) convertViews
                .findViewById(R.id.cancel_iv);

        year_wvs = (WheelView) convertViews.findViewById(R.id.mins);
        month_wvs = (WheelView) convertViews.findViewById(R.id.data);
//        date_wv = (WheelView) convertView.findViewById(R.id.hour);

//        if (timeType.equals("S")) {
//            title.setText("选择起始时间");
//        } else {
//            title.setText("选择结束时间");
//        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        // year_wv.setAdapter(new StringWheelAdapter(strings));
        // year_wv.setLabel("");
        // year_wv.setCyclic(true);

        year_wvs.setAdapter(new NumericWheelAdapter(2017, 2117, "%02d", "年"));
        year_wvs.setLabel("");
        year_wvs.setCyclic(true);

        month_wvs.setAdapter(new NumericWheelAdapter(1, 12, "%02d", "月"));
        month_wvs.setLabel("");
        month_wvs.setCyclic(true);

//        date_wv.setAdapter(new NumericWheelAdapter(1, 31, "%02d", "日"));
//        date_wv.setLabel("");
//        date_wv.setCyclic(true);

        year_wvs.setCurrentItem(mYears - 2017);
        month_wvs.setCurrentItem(mMonths - 1);
//        date_wv.setCurrentItem(mDay - 1);

        OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!timeScrolled) {
                    timeChanged = true;

                    timeChanged = false;
                }
            }
        };

        year_wvs.addChangingListener(wheelListener);
        month_wvs.addChangingListener(wheelListener);
//        date_wv.addChangingListener(wheelListener);

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

        year_wvs.addScrollingListener(scrollListener);
        month_wvs.addScrollingListener(scrollListener);
//        date_wv.addScrollingListener(scrollListener);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String tString = (year_wvs.getCurrentItem() + 2017) + "-"
                        + fos.format(month_wvs.getCurrentItem() + 1);
//                        + "-"
//                        + fo.format(date_wv.getCurrentItem() + 1);//
                // + strings[year_wv.getCurrentItem()];
                time_tv.setText(tString);
//                String[] strs = tString.split("-");

                reqStatisticsForManager(timeType, tString + "-01");
                // time = new StringBuilder().append(mYear)
                // .append("-")
                // .append(fo.format(month_wv.getCurrentItem() + 1))
                // .append("-")
                // // 得到的月份+1，因为从0开始
                // .append(fo.format(date_wv.getCurrentItem() + 1))
                // .append(" ").append(strings[year_wv.getCurrentItem()])
                // .append(":00");
                // time = new StringBuilder().append(month_wv.getCurrentItem())
                // .append("-")
                // .append(fo.format(month_wv.getCurrentItem() + 1))
                // .append("-")
                // // 得到的月份+1，因为从0开始
                // .append(fo.format(date_wv.getCurrentItem() + 1));

                popupwindowds.dismiss();
            }
        });

        close_tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupwindowds.dismiss();
            }
        });

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupwindowds.setBackgroundDrawable(new BitmapDrawable());
        // 使其聚集
        popupwindowds.setFocusable(true);
        popupwindowds.setTouchable(true); // 设置PopupWindow可触摸
        // 设置允许在外点击消失
        popupwindowds.setOutsideTouchable(true);

        backgroundAlpha(0.5f);
        popupwindowds.setOnDismissListener(new poponDismissListener());

        // 刷新状态
        popupwindowds.update();
    }


}
