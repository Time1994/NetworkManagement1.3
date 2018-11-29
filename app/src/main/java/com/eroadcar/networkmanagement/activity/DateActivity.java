package com.eroadcar.networkmanagement.activity;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
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
import com.eroadcar.networkmanagement.adapter.StatisticsAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.StatisticsBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.utils.Tool;
import com.eroadcar.networkmanagement.view.widget.NumericWheelAdapter;
import com.eroadcar.networkmanagement.view.widget.OnWheelChangedListener;
import com.eroadcar.networkmanagement.view.widget.OnWheelScrollListener;
import com.eroadcar.networkmanagement.view.widget.WheelView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class DateActivity extends BaseActivity implements View.OnClickListener {
    private Button back_btn, other_btn;
    private TextView title_tv;

    private TextView cur_tv, start_tv, end_tv;

    private RadioGroup time_rg;

    private WheelView year_wv, month_wv, date_wv, yearm_wv, monthm_wv;

    private LinearLayout day_ll, month_ll;

    private Calendar calendar;
    private java.text.DecimalFormat fo;


    private int year, monthi, dayi;

    private String month = "", startDate = "", endDate = "", type = "start";

    private boolean timeChanged = false, timeScrolled = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        back_btn = (Button) findViewById(R.id.back_btn);
        other_btn = (Button) findViewById(R.id.other_btn);

        title_tv = (TextView) findViewById(R.id.title_tv);

        time_rg = (RadioGroup) findViewById(R.id.time_rg);

        cur_tv = (TextView) findViewById(R.id.cur_tv);
        start_tv = (TextView) findViewById(R.id.start_tv);
        end_tv = (TextView) findViewById(R.id.end_tv);

        day_ll = (LinearLayout) findViewById(R.id.day_ll);
        month_ll = (LinearLayout) findViewById(R.id.month_ll);

        year_wv = (WheelView) findViewById(R.id.mins);
        month_wv = (WheelView) findViewById(R.id.data);
        date_wv = (WheelView) findViewById(R.id.hour);

        yearm_wv = (WheelView) findViewById(R.id.year);
        monthm_wv = (WheelView) findViewById(R.id.month);

        back_btn.setOnClickListener(this);
        start_tv.setOnClickListener(this);
        end_tv.setOnClickListener(this);
        other_btn.setOnClickListener(this);

        title_tv.setText("选择时间");
        back_btn.setText("");
        other_btn.setText("完成");

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthi = calendar.get(Calendar.MONTH) + 1;
        dayi = calendar.get(Calendar.DAY_OF_MONTH);

        fo = new java.text.DecimalFormat("00");

        month = new StringBuilder().append(year).append("-")
                .append(fo.format(monthi)) + "";
        startDate = new StringBuilder().append(year).append("-")
                .append(fo.format(monthi)).append("-")
                .append(fo.format(dayi)) + "";
        endDate = new StringBuilder().append(year).append("-")
                .append(fo.format(monthi)).append("-")
                .append(fo.format(dayi)) + "";

        start_tv.setText(startDate);
        end_tv.setText(endDate);
        cur_tv.setText(month);


        year_wv.setAdapter(new NumericWheelAdapter(2000, 2019, "%02d", "年"));
        year_wv.setLabel("");
        year_wv.setCyclic(true);

        month_wv.setAdapter(new NumericWheelAdapter(1, 12, "%02d", "月"));
        month_wv.setLabel("");
        month_wv.setCyclic(true);

        date_wv.setAdapter(new NumericWheelAdapter(1, 31, "%02d", "日"));
        date_wv.setLabel("");
        date_wv.setCyclic(true);

        yearm_wv.setAdapter(new NumericWheelAdapter(2000, 2019, "%02d", "年"));
        yearm_wv.setLabel("");
        yearm_wv.setCyclic(true);

        monthm_wv.setAdapter(new NumericWheelAdapter(1, 12, "%02d", "月"));
        monthm_wv.setLabel("");
        monthm_wv.setCyclic(true);

        year_wv.setCurrentItem(year - 2000);
        month_wv.setCurrentItem(monthi - 1);
        date_wv.setCurrentItem(dayi - 1);

        yearm_wv.setCurrentItem(year - 2000);
        monthm_wv.setCurrentItem(monthi - 1);


//        OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
//            public void onChanged(WheelView wheel, int oldValue, int newValue) {
//                if (!timeScrolled) {
//                    timeChanged = true;
//
//                    timeChanged = false;
//                }
//            }
//        };
//
//        year_wv.addChangingListener(wheelListener);
//        month_wv.addChangingListener(wheelListener);
//        date_wv.addChangingListener(wheelListener);
//
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

                if (type.equals("start")) {
                    startDate = (year_wv.getCurrentItem() + 2000) + "-"
                            + fo.format(month_wv.getCurrentItem() + 1) + "-"
                            + fo.format(date_wv.getCurrentItem() + 1);
                    start_tv.setText(startDate);
                } else {
                    endDate = (year_wv.getCurrentItem() + 2000) + "-"
                            + fo.format(month_wv.getCurrentItem() + 1) + "-"
                            + fo.format(date_wv.getCurrentItem() + 1);
                    end_tv.setText(endDate);
                }
            }
        };

        OnWheelScrollListener scrollListenerm = new OnWheelScrollListener() {
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

                month = (yearm_wv.getCurrentItem() + 2000) + "-"
                        + fo.format(monthm_wv.getCurrentItem() + 1);
                cur_tv.setText(month);

            }
        };

        year_wv.addScrollingListener(scrollListener);
        month_wv.addScrollingListener(scrollListener);
        date_wv.addScrollingListener(scrollListener);

        yearm_wv.addScrollingListener(scrollListenerm);
        monthm_wv.addScrollingListener(scrollListenerm);


        time_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                if (arg1 == R.id.day_rb) {
                    day_ll.setVisibility(View.VISIBLE);
                    month_ll.setVisibility(View.GONE);
                } else if (arg1 == R.id.month_rb) {
                    day_ll.setVisibility(View.GONE);
                    month_ll.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.start_tv:
                type = "start";

                start_tv.setTextColor(getResources().getColor(R.color.green));
                end_tv.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.end_tv:
                type = "end";

                start_tv.setTextColor(getResources().getColor(R.color.black));
                end_tv.setTextColor(getResources().getColor(R.color.green));
                break;
            case R.id.other_btn:

                Intent intent = new Intent();

                if (((RadioButton) findViewById(R.id.day_rb)).isChecked()) {
                    if (Tool.isDateOneBigger(startDate, endDate)) {
                        ToastUtils.showShort("结束日期需大于开始日期");
                        return;
                    }

                    intent.putExtra("start", startDate);
                    intent.putExtra("end", endDate);
                    intent.putExtra("month", "");
                } else {
                    intent.putExtra("start", "");
                    intent.putExtra("end", "");
                    intent.putExtra("month", month);
                }


                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }

}
