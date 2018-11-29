package com.eroadcar.networkmanagement.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.ChooseInsuranceAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.InsuranceBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;

public class ChooseInsuranceActivity extends BaseActivity implements
        OnClickListener {
    private Button other_btn, back_btn, sure_btn;
    private TextView title_tv;
    private ListView dot_lv;

    private CommonBean<ArrayList<InsuranceBean>> itemdateBean;
    private ChooseInsuranceAdapter adapter;

    public ArrayList<InsuranceBean> insuranceBean = new ArrayList<>();

    private String insurance = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_items);

        title_tv = (TextView) findViewById(R.id.title_tv);
        other_btn = (Button) findViewById(R.id.other_btn);
        back_btn = (Button) findViewById(R.id.back_btn);
        sure_btn = (Button) findViewById(R.id.sure_btn);

        dot_lv = (ListView) findViewById(R.id.dot_lv);

        back_btn.setOnClickListener(this);
        other_btn.setOnClickListener(this);
        sure_btn.setOnClickListener(this);

        title_tv.setText("投保险种");
        back_btn.setText("");
        sure_btn.setText("确认选择");

        // chooseItem = new ArrayList<InsuranceBean>();

        dot_lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
//                position--;

                ImageView chick_iv = (ImageView) view
                        .findViewById(R.id.check_iv);
                if (chick_iv.isShown()) {
                    chick_iv.setVisibility(View.GONE);
                    for (int i = 0; i < insuranceBean.size(); i++) {
                        if (insuranceBean
                                .get(i)
                                .getIt_id()
                                .equals(itemdateBean.getData().get(position)
                                        .getIt_id())) {
                            insuranceBean.remove(i);
                            adapter.checks[position] = false;
                            break;
                        }
                    }
                } else {
                    chick_iv.setVisibility(View.VISIBLE);
                    insuranceBean.add(itemdateBean.getData().get(
                            position));
                    adapter.checks[position] = true;
                }
            }
        });

//        View view = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
//                .inflate(R.layout.view_listview_header, null);
//        dot_lv.addHeaderView(view);

        getInsuranceType();

        insurance = getIntent().getStringExtra("INSURANCE");
    }

    private void getInsuranceType() {
        loadingDialog.setMessage("正在加载险种...");
        loadingDialog.dialogShow();
        String url = Constant.GETINSURANCETYPE;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appcode", IMEI);
        map.put("apptype", Constant.APPTYPE);
        map.put("mg_id", application.userBean.getMg_id());
        map.put("mg_name", application.userBean.getMg_name());
        map.put("mg_shopsid", application.userBean.getMg_shopsid());
        map.put("mg_groupid", application.userBean.getMg_groupid());
        map.put("mg_shopname", application.userBean.getMg_shopname());
        map.put("mg_shopcode", application.userBean.getMg_shopcode());

        GsonRequest<CommonBean<ArrayList<InsuranceBean>>> requtst = new GsonRequest<CommonBean<ArrayList<InsuranceBean>>>(
                Method.POST, url, listener_getItemData);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
        mRequestQueue.start();
    }

    private RequesListener<CommonBean<ArrayList<InsuranceBean>>> listener_getItemData = new RequesListener<CommonBean<ArrayList<InsuranceBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<InsuranceBean>> arg0) {
            // TODO Auto-generated method stub
            itemdateBean = arg0;
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
                    if (itemdateBean != null
                            && itemdateBean.getState().equals("success")) {
                        adapter = new ChooseInsuranceAdapter(
                                ChooseInsuranceActivity.this,
                                itemdateBean.getData());
                        dot_lv.setAdapter(adapter);

                        for (int i = 0; i < itemdateBean.getData().size(); i++) {
                            if (insurance != null
                                    && insurance.contains(itemdateBean.getData()
                                    .get(i).getIt_insurance_type())) {
                                insuranceBean.add(itemdateBean
                                        .getData().get(i));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
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
            case R.id.other_btn:
                break;
            case R.id.sure_btn:
                if (insuranceBean.size() == 0) {
                    ToastUtils.showShort("请选择保险险种！");
                    return;
                }
                // application.insuranceBean = chooseItem;

                String insurance = "";
                for (int i = 0; i < insuranceBean.size(); i++) {
                    insurance += insuranceBean.get(i)
                            .getIt_insurance_type() + "、";
                }
                Intent mIntent = new Intent();
                mIntent.putExtra("INSURANCE", insurance);
                setResult(RESULT_OK, mIntent);
                onBackPressed();
                break;
            default:
                break;
        }
    }
}
