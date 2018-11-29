package com.eroadcar.networkmanagement.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.SaleManagerAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.HomeBean;
import com.eroadcar.networkmanagement.bean.MsgsBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.KickBackAnimator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by amos on 2018/7/28.
 */

public class SaleManagerActivity extends BaseActivity implements View.OnClickListener {
    private Button back_btn;
    private TextView title_tv;

    private GridView img_gv;

    private ArrayList<HomeBean> homeBeans;

    private MsgsBean msgsBean;

    private int xuanche = 0, wanshan = 0, orders = 0, xiaoshou = 0, zuling = 0, guwen = 0, guanli = 0, shouyin = 0;


    private CommonBean<MsgsBean> msgsBeanCommonBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_manager);

        title_tv = (TextView) findViewById(R.id.title_tv);
        back_btn = (Button) findViewById(R.id.back_btn);

        img_gv = (GridView) findViewById(R.id.img_gv);

        title_tv.setText(R.string.sale_manager);

        back_btn.setOnClickListener(this);

//        try {
//            msgsBean = (MsgsBean) getIntent().getSerializableExtra("BEAN");
//
//
//            setInterface();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        img_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (homeBeans.get(i).getType().equals("chexing")) {
                    intent(ShowCarActivity.class);
                } else if (homeBeans.get(i).getType().equals("kehu")) {
//                    setPopWindow(view);
                    initmPopupWindowViewGuwen();
                    popupwindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                } else if (homeBeans.get(i).getType().equals("gengxin")) {
//                    setPopWindowManager(view);
                    initmPopupWindowView();
                    popupwindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                } else if (homeBeans.get(i).getType().equals("shouyin")) {
                    try {
                        Intent intent = new Intent(SaleManagerActivity.this, OrderSalesCaiwuActivity.class);
                        intent.putExtra("BEAN", msgsBean);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        intent(OrderSalesCaiwuActivity.class);
                    }
                }
            }
        });
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try {
                        if (msgsBeanCommonBean.getState().equals("success")) {
                            msgsBean = msgsBeanCommonBean.getData();

                            setInterface();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case -1:
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        getPushCenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;

        }
    }

    private void setHomeBean() {
        String role = application.userBean.getMg_role_ids();

        homeBeans = new ArrayList<>();

        HomeBean bean = new HomeBean();
        bean.setImageId(R.mipmap.icon_sale_custom);
        bean.setContent("顾问专区");
        bean.setNewsnum(guwen);
        bean.setType("kehu");

        HomeBean bean1 = new HomeBean();
        bean1.setImageId(R.mipmap.icon_sale_jilu);
        bean1.setContent("管理专区");
        bean1.setNewsnum(guanli);
        bean1.setType("gengxin");

        HomeBean bean2 = new HomeBean();
        bean2.setImageId(R.mipmap.icon_sale_shouy);
        bean2.setContent("财务专区");
        bean2.setNewsnum(shouyin);
        bean2.setType("shouyin");

        HomeBean bean3 = new HomeBean();
        bean3.setImageId(R.mipmap.icon_sale_chex);
        bean3.setContent(getString(R.string.cars));
        bean3.setNewsnum(0);
        bean3.setType("chexing");


        if (role.contains("4")) {
            homeBeans.clear();

            homeBeans.add(bean2);
            homeBeans.add(bean);
            homeBeans.add(bean3);
        }

        if (role.contains("3")) {
            homeBeans.clear();

            homeBeans.add(bean);
            homeBeans.add(bean3);
        }

        if (role.contains("2")) {
            homeBeans.clear();

            homeBeans.add(bean1);
            homeBeans.add(bean);
            homeBeans.add(bean3);
        }

        if (role.contains("1")) {
            homeBeans.clear();

            homeBeans.add(bean);
            homeBeans.add(bean1);
            homeBeans.add(bean2);
            homeBeans.add(bean3);
        }
    }


    private void setInterface() {
        try {
            shouyin = Integer.valueOf(msgsBean.getSell().getDsdj()) + Integer.valueOf(msgsBean.getSell().getDsqk()) + Integer.valueOf(msgsBean.getSell().getYhhcdz())
                    + Integer.valueOf(msgsBean.getLease().getYjc());

            xuanche = Integer.valueOf(msgsBean.getSell().getZxtg()) + Integer.valueOf(msgsBean.getSell().getZxbh());

            if (msgsBean.getSell().getYsqk() == null || msgsBean.getSell().getYsqk().equals("null")) {
                wanshan = 0;
            } else {
                wanshan = Integer.valueOf(msgsBean.getSell().getYsqk());//getYhhcdz()//getYbqzl()
            }


            xiaoshou = Integer.valueOf(msgsBean.getSell().getZxz()) + Integer.valueOf(msgsBean.getSell().getDsdj()) +
                    Integer.valueOf(msgsBean.getSell().getYsqk());
            zuling = Integer.valueOf(msgsBean.getLease().getSqz()) + Integer.valueOf(msgsBean.getLease().getYjc());

//            orders = Integer.valueOf(msgsBean.getLease().getYwc()) + Integer.valueOf(msgsBean.getSell().getYwc());

            guwen = xuanche + wanshan;

            guanli = Integer.valueOf(msgsBean.getSell().getTotal()) + Integer.valueOf(msgsBean.getLease().getTotal());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            setHomeBean();
            img_gv.setAdapter(new SaleManagerAdapter(SaleManagerActivity.this, homeBeans));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPushCenter() {
        loadingDialog.setMessage("正在获取数据...");
        loadingDialog.dialogShow();
        String url = Constant.GETPUSHCENTER;
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

        map.put("mg_role_ids", application.userBean.getMg_role_ids());


        GsonRequest<CommonBean<MsgsBean>> requtst = new GsonRequest<CommonBean<MsgsBean>>(
                Request.Method.POST, url, listener_getPushCenter);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<MsgsBean>> listener_getPushCenter = new RequesListener<CommonBean<MsgsBean>>() {
        @Override
        public void onResponse(CommonBean<MsgsBean> arg0) {
            // TODO Auto-generated method stub
            msgsBeanCommonBean = arg0;
            mHandler.sendEmptyMessage(1);
            loadingDialog.dismiss();
        }

        @Override
        public void onErrorResponse(VolleyError arg0) {
            // TODO Auto-generated method stub
            mHandler.sendEmptyMessage(-2);
            loadingDialog.dismiss();
        }

    };

    private void setPopWindow(View view) {
        //准备PopupWindow的布局View
        final View popupView = LayoutInflater.from(this).inflate(R.layout.pop_sales, null);
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

        ((TextView) popupView.findViewById(R.id.zheng_tv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent(ZhengxActivity.class);
            }
        });
        ((TextView) popupView.findViewById(R.id.rent_tv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent(RentActivity.class);
            }
        });
        ((TextView) popupView.findViewById(R.id.customs_tv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent(CustomActivity.class);
            }
        });
        ((TextView) popupView.findViewById(R.id.order_zheng_tv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                intent(ZhengxListActivity.class);
                Intent intent = new Intent(SaleManagerActivity.this, ZhengxListActivity.class);
                intent.putExtra("FROM", "choose");
                startActivity(intent);
            }
        });
        ((TextView) popupView.findViewById(R.id.order_wan_tv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                intent(ZhengxListActivity.class);
                Intent intent = new Intent(SaleManagerActivity.this, ZhengxListActivity.class);
                intent.putExtra("FROM", "wan");
                startActivity(intent);
            }
        });
        ((TextView) popupView.findViewById(R.id.orders_tv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent(OrderSalesActivity.class);
            }
        });

        //PopupWindow在targetView下方弹出
        popupWindow.showAsDropDown(view);
    }

    private void setPopWindowManager(View view) {
        //准备PopupWindow的布局View
        final View popupView = LayoutInflater.from(this).inflate(R.layout.pop_manager, null);
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

        ((TextView) popupView.findViewById(R.id.mcustoms_tv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent(CustomManagerActivity.class);
            }
        });
        ((TextView) popupView.findViewById(R.id.morders_tv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent(OrderSalesManagerActivity.class);
            }
        });

        //PopupWindow在targetView下方弹出
        popupWindow.showAsDropDown(view);
    }


    private View customView;
    private PopupWindow popupwindow;
    private RelativeLayout contentView;
    private LinearLayout xinz_ll, zhengx_ll, wans_ll, kehu_ll, ll_close;
    private RelativeLayout zulin_rl;


    public void initmPopupWindowViewGuwen() {
        // 获取自定义布局文件pop.xml的视图
        customView = getLayoutInflater().inflate(R.layout.pop_choice,
                null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindow = new PopupWindow(customView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        // 获取控件
        contentView = (RelativeLayout) customView.findViewById(R.id.contentView);
        xinz_ll = (LinearLayout) customView.findViewById(R.id.xinz_ll);
        zhengx_ll = (LinearLayout) customView.findViewById(R.id.zhengx_ll);
        wans_ll = (LinearLayout) customView.findViewById(R.id.wans_ll);
        RelativeLayout kehu_rl = (RelativeLayout) customView.findViewById(R.id.kehu_rl);
        RelativeLayout zulin_rl = (RelativeLayout) customView.findViewById(R.id.zulin_ll);
        RelativeLayout dingd_ll = (RelativeLayout) customView.findViewById(R.id.dingd_ll);
        ll_close = (LinearLayout) customView.findViewById(R.id.ll_close);

        TextView msg_shenh_tv = (TextView) customView.findViewById(R.id.msg_shenh_tv);
        TextView msg_wans_tv = (TextView) customView.findViewById(R.id.msg_wans_tv);


        if (xuanche != 0) {
            msg_shenh_tv.setText(xuanche + "");
            msg_shenh_tv.setVisibility(View.VISIBLE);
        }
        if (wanshan != 0) {
            msg_wans_tv.setText(wanshan + "");
            msg_wans_tv.setVisibility(View.VISIBLE);
        }


        ll_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeAnimation(contentView);
            }
        });
        wans_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent(CustomActivity.class);

//                closeAnimation(contentView);
            }
        });
        xinz_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent(ZhengxActivity.class);

//                closeAnimation(contentView);
            }
        });
        zhengx_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent(RentActivity.class);

//                closeAnimation(contentView);
            }
        });

        kehu_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                intent(ZhengxListActivity.class);
                Intent intent = new Intent(SaleManagerActivity.this, ZhengxListActivity.class);
                intent.putExtra("FROM", "choose");
                startActivity(intent);

//                closeAnimation(contentView);
            }
        });
        zulin_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                intent(ZhengxListActivity.class);
                Intent intent = new Intent(SaleManagerActivity.this, ZhengxListActivity.class);
                intent.putExtra("FROM", "wan");
                startActivity(intent);

//                closeAnimation(contentView);
            }
        });
        dingd_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent(OrderSalesActivity.class);

//                closeAnimation(contentView);
            }
        });

        showAnimation(contentView);
        backgroundAlpha(1f);
        popupwindow.setOnDismissListener(new poponDismissListener());

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupwindow.setBackgroundDrawable(new BitmapDrawable());
        // 使其聚集
        popupwindow.setFocusable(true);
        popupwindow.setTouchable(true); // 设置PopupWindow可触摸
        // 设置允许在外点击消失
        popupwindow.setOutsideTouchable(true);
        // 刷新状态
        popupwindow.update();
    }

    public void initmPopupWindowView() {
        // 获取自定义布局文件pop.xml的视图
        customView = getLayoutInflater().inflate(R.layout.pop_manag,
                null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindow = new PopupWindow(customView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        // 获取控件
        contentView = (RelativeLayout) customView.findViewById(R.id.contentView);

        kehu_ll = (LinearLayout) customView.findViewById(R.id.kehu_ll);
        zulin_rl = (RelativeLayout) customView.findViewById(R.id.zulin_rl);
        ll_close = (LinearLayout) customView.findViewById(R.id.ll_close);

        TextView msg_zulin_tv = (TextView) customView.findViewById(R.id.msg_zulin_tv);

        if (guanli != 0) {
            msg_zulin_tv.setText(guanli + "");
            msg_zulin_tv.setVisibility(View.VISIBLE);
        }

        ll_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeAnimation(contentView);
            }
        });


        kehu_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent(CustomManagerActivity.class);

//                closeAnimation(contentView);
            }
        });
        zulin_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(SaleManagerActivity.this, OrderSalesManagerActivity.class);
                    intent.putExtra("BEAN", msgsBean);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();

                    intent(OrderSalesManagerActivity.class);
                }

//                closeAnimation(contentView);
            }
        });


        showAnimation(contentView);
        backgroundAlpha(1f);
        popupwindow.setOnDismissListener(new poponDismissListener());

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupwindow.setBackgroundDrawable(new BitmapDrawable());
        // 使其聚集
        popupwindow.setFocusable(true);
        popupwindow.setTouchable(true); // 设置PopupWindow可触摸
        // 设置允许在外点击消失
        popupwindow.setOutsideTouchable(true);
        // 刷新状态
        popupwindow.update();
    }

    /**
     * 显示进入动画效果
     *
     * @param layout
     */
    private void showAnimation(ViewGroup layout) {
        //遍历根试图下的一级子试图
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
//            //忽略关闭组件
            if (child.getId() == R.id.ll_close) {
                continue;
            }
            //设置所有一级子试图的点击事件
//            child.setOnClickListener(this);
            child.setVisibility(View.INVISIBLE);
            //延迟显示每个子试图(主要动画就体现在这里)
            Observable.timer(i * 50, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            child.setVisibility(View.VISIBLE);
                            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                            fadeAnim.setDuration(300);
                            KickBackAnimator kickAnimator = new KickBackAnimator();
                            kickAnimator.setDuration(150);
                            fadeAnim.setEvaluator(kickAnimator);
                            fadeAnim.start();
                        }
                    });
        }

    }

    /**
     * 关闭动画效果
     *
     * @param layout
     */
    private void closeAnimation(ViewGroup layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
//            if (child.getId() == R.id.ll_close) {
//                continue;
//            }
            Observable.timer((layout.getChildCount() - i - 1) * 30, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            child.setVisibility(View.VISIBLE);
                            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 0, 600);
                            fadeAnim.setDuration(200);
                            KickBackAnimator kickAnimator = new KickBackAnimator();
                            kickAnimator.setDuration(100);
                            fadeAnim.setEvaluator(kickAnimator);
                            fadeAnim.start();
                            fadeAnim.addListener(new Animator.AnimatorListener() {

                                @Override
                                public void onAnimationStart(Animator animation) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    child.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                }
                            });
                        }
                    });


            if (child.getId() == R.id.ll_close) {
                Observable.timer((layout.getChildCount() - i) * 30 + 80, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                popupwindow.dismiss();
                            }
                        });
            }
        }

    }
}
