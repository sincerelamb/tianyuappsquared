package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.adapter.FirstProtectAdapter;
import com.tygas.tianyu.tianyu.ui.adapter.TimingProtectAdapter;
import com.tygas.tianyu.tianyu.ui.model.FirstProtect;
import com.tygas.tianyu.tianyu.ui.model.TimingProtect;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.JsonParser;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimingProtectActivity extends Activity implements View.OnClickListener, UltimateRecyclerView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private ImageView backImageView;//返回按钮
    private ImageView nodata;
    private TextView tv_find_tatol;
    private UltimateRecyclerView ultimateRecyclerView;

    private TimingProtectAdapter adapter;
    private List<TimingProtect> tpList = new ArrayList<TimingProtect>();

    private final int PAGESIZE = 10;
    private int pageindex = 1;


    private final int DOWN = 1;
    private final int UP = 2;


    public static String starttime;
    public static String endtime;
    public static String CarOwnerName;
    public static String CarOwnerPhone;
    public static String DueState = "";
    public static String ServiceEmpName;
    public static String CarInfo;
    public View viewCustoner;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case 1:
                    showCustomerPro();
                    break;
                case 2:
                    showCustomerNoData();
                    break;
                case 3:
                    showCustomerNoInternet();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_timing_protect);
        initData();
        initView();
        initBroad();
    }


    private void initData() {
        if (getIntent().getStringExtra("isoverdue") != null) {
            DueState = getIntent().getStringExtra("isoverdue");
        }
        if (getIntent().getStringExtra("fromtime") != null) {
            starttime = getIntent().getStringExtra("fromtime");
        }
        if (getIntent().getStringExtra("totime") != null) {
            endtime = getIntent().getStringExtra("totime");
        }
        if (getIntent().getStringExtra("CarOwnerName") != null) {
            CarOwnerName = getIntent().getStringExtra("CarOwnerName");
        }
        if (getIntent().getStringExtra("CarOwnerPhone") != null) {
            CarOwnerPhone = getIntent().getStringExtra("CarOwnerPhone");
        }
        if (getIntent().getStringExtra("ServiceEmpName") != null) {
            ServiceEmpName = getIntent().getStringExtra("ServiceEmpName");
        }
        if (getIntent().getStringExtra("CarInfo") != null) {
            CarInfo = getIntent().getStringExtra("CarInfo");
        }


        Calendar now = Calendar.getInstance();
        String nowStr = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        if (starttime == null) {
            starttime = nowStr;
        }
        if (endtime == null) {
            endtime = nowStr;
        }
    }


    private void initView() {
        tv_find_tatol = (TextView) findViewById(R.id.activity_tpcustomer_tv_find_total);
        nodata = (ImageView) findViewById(R.id.activity_tpcustom_iv_nodata);
        backImageView = (ImageView) findViewById(R.id.activtiy_tpcustomers_back);
        backImageView.setOnClickListener(this);

        ultimateRecyclerView = (UltimateRecyclerView) findViewById(R.id.activity_tpcustom_ulry_cplist);
        ultimateRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ultimateRecyclerView.enableLoadmore();
        ultimateRecyclerView.setHasFixedSize(false);
        ultimateRecyclerView.setOnLoadMoreListener(this);
        ultimateRecyclerView.setDefaultOnRefreshListener(this);

        adapter = new TimingProtectAdapter(this, tpList);
        viewCustoner = View.inflate(this, R.layout.custom_bottom_progressbar, null);
        adapter.setCustomLoadMoreView(viewCustoner);
        ultimateRecyclerView.setAdapter(adapter);

        refreshData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activtiy_tpcustomers_back://点击了返回按钮
                finish();
                break;
            case R.id.activtiy_tpcustomers_select:
                Intent intent = new Intent(this, TpScreenActivity.class);
                intent.putExtra("name", CarOwnerName);
                intent.putExtra("phone", CarOwnerPhone);
                intent.putExtra("fromtime", starttime);
                intent.putExtra("totime", endtime);
                intent.putExtra("isoverdue", DueState);
                intent.putExtra("guwen", ServiceEmpName);
                intent.putExtra("CarInfo", CarInfo);
                startActivityForResult(intent, 0);
                break;
        }
    }


    private void downLoadData(final int type, int index) {
        MyAppCollection applicationContext = (MyAppCollection) getApplicationContext();
        User user = applicationContext.getUser();

        Log.d("IP", UrlData.TIMING_PROTECT_URL);
        HttpUtilsHelper.getInstance().send(HttpRequest.HttpMethod.POST, UrlData.TIMING_PROTECT_URL, XutilsRequest.getTpCustomerList(user.getEmpId(),
                CarOwnerName, CarOwnerPhone, starttime, endtime, index, PAGESIZE, DueState, ServiceEmpName, CarInfo),
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                        Log.d("tpresult", objectResponseInfo.result);
                        Bundle bundle = JsonParser.tpCustomersParser(objectResponseInfo.result);
                        List<TimingProtect> tpCustomerList = (List<TimingProtect>) bundle.get("list");
                        if (bundle.getString("totalrows") != null) {
                            tv_find_tatol.setText(bundle.getString("totalrows"));
                        }

                        Message message = handler.obtainMessage();
                        switch (type) {
                            case 1:
                                ultimateRecyclerView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ultimateRecyclerView.setRefreshing(false);
                                    }
                                });

                                tpList.clear();
                                tpList.addAll(tpCustomerList);
                                if (tpCustomerList.size() < 10) {
                                    message.arg1 = 2;
                                } else {
                                    message.arg1 = 1;
                                }
                                showCustomerPro();

                                break;
                            case 2:
                                if (tpCustomerList.size() == 0) {
                                    pageindex--;
                                    // Toast.makeText(CpCustomActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                                    //   showCustomerNoData();
                                    message.arg1 = 2;
                                } else {
                                    // showCustomerPro();
                                    message.arg1 = 1;
                                }

                                tpList.addAll(tpCustomerList);
                                break;
                        }
                        handler.sendMessage(message);
                        adapter.notifyDataSetChanged();
                        if (tpList.size() > 0) {
                            nodata.setVisibility(View.GONE);
                        } else {
                            nodata.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                        Toast.makeText(TimingProtectActivity.this, "网络请求出错，请检查网络", Toast.LENGTH_SHORT).show();
                        switch (type) {
                            case 1:
                                ultimateRecyclerView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ultimateRecyclerView.setRefreshing(false);
                                    }
                                });
                                break;
                            case 2:
                                pageindex--;
                                Message message = handler.obtainMessage();
                                message.arg1 = 3;
                                handler.sendMessage(message);
                                //   showCustomerNoInternet();
                                break;
                        }
                        if (tpList.size() > 0) {
                            nodata.setVisibility(View.GONE);
                        } else {
                            nodata.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }


    private void showCustomerPro() {
        TextView tv = (TextView) viewCustoner.findViewById(R.id.bottom_text);
        viewCustoner.findViewById(R.id.bottom_progress_bar).setVisibility(View.VISIBLE);
        tv.setVisibility(View.VISIBLE);
        tv.setText("加载更多");
    }

    private void showCustomerNoInternet() {
        TextView tv = (TextView) viewCustoner.findViewById(R.id.bottom_text);
        viewCustoner.findViewById(R.id.bottom_progress_bar).setVisibility(View.INVISIBLE);
        tv.setVisibility(View.VISIBLE);
        tv.setText("网络链接出错");
    }

    private void showCustomerNoData() {
        TextView tv = (TextView) viewCustoner.findViewById(R.id.bottom_text);
        viewCustoner.findViewById(R.id.bottom_progress_bar).setVisibility(View.INVISIBLE);
        tv.setVisibility(View.VISIBLE);
        tv.setText("没有更多数据");
    }


    @Override
    public void loadMore(int itemsCount, int maxLastVisiblePosition) {
        pageindex++;
        downLoadData(UP, pageindex);
    }

    @Override
    public void onRefresh() {
        pageindex = 1;
        downLoadData(DOWN, pageindex);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            CarOwnerName = data.getStringExtra("name");
            CarOwnerPhone = data.getStringExtra("phone");
            starttime = data.getStringExtra("fromtime");
            endtime = data.getStringExtra("totime");
            DueState = data.getStringExtra("isoverdue");
            ServiceEmpName = data.getStringExtra("guwen");
            CarInfo = data.getStringExtra("CarInfo");
            refreshData();
        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            refreshData();
        }

    }

    public void refreshData() {
        ultimateRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                ultimateRecyclerView.setRefreshing(true);
            }
        });
        onRefresh();
    }


    private RefreshDataBroad refreshDataBroad;

    private void initBroad() {
        //生成广播处理
        refreshDataBroad = new RefreshDataBroad();
        //实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter("ty.refreshlist.tp");
        //注册广播
        this.registerReceiver(refreshDataBroad, intentFilter);
    }

    class RefreshDataBroad extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            refreshData();
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(refreshDataBroad);
        super.onDestroy();
    }
}
