package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.adapter.CpCustomUltiRecycleAdapter;
import com.tygas.tianyu.tianyu.ui.model.CpCustomer;
import com.tygas.tianyu.tianyu.ui.model.SearchCpCustomer;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.utils.DbUtilsHelper;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.JsonParser;
import com.tygas.tianyu.tianyu.utils.MyDateUtils;
import com.tygas.tianyu.tianyu.utils.ProgressDialogHelper;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EventListener;
import java.util.List;

public class CpCustomActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, UltimateRecyclerView.OnLoadMoreListener, View.OnClickListener {


    private ImageView backImageView;//返回按钮
    private ImageView nodata;
    private TextView tv_find_tatol;
    private UltimateRecyclerView ultimateRecyclerView;
    private CpCustomUltiRecycleAdapter adapter;
    private final int PAGESIZE = 10;
    private int pageindex = 1;
    private List<CpCustomer> cpList = new ArrayList<CpCustomer>();
    private final int DOWN = 1;
    private final int UP = 2;

    public static String starttime;
    public static String endtime;
    public static String customername="";
    public static String customerphone="";
    public static String isSuppleState = "";
    public static String guwen="";
    private View viewCustoner;

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
        setContentView(R.layout.activity_cp_custom);
        initDate();
        initView();
//        registerReceiver();
    }

    private void initView() {
        tv_find_tatol = (TextView) findViewById(R.id.activity_cpcustomer_tv_find_total);
        nodata = (ImageView) findViewById(R.id.activity_cpcustom_iv_nodata);
        backImageView = (ImageView) findViewById(R.id.activtiy_cpcustomers_back);
        backImageView.setOnClickListener(this);

        ultimateRecyclerView = (UltimateRecyclerView) findViewById(R.id.activity_cpcustom_ulry_cplist);
        ultimateRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ultimateRecyclerView.enableLoadmore();
        ultimateRecyclerView.setHasFixedSize(false);
        ultimateRecyclerView.setOnLoadMoreListener(this);
        ultimateRecyclerView.setDefaultOnRefreshListener(this);


        adapter = new CpCustomUltiRecycleAdapter(this, cpList);
        viewCustoner = View.inflate(this, R.layout.custom_bottom_progressbar, null);
        adapter.setCustomLoadMoreView(viewCustoner);
        ultimateRecyclerView.setAdapter(adapter);

        ultimateRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                ultimateRecyclerView.setRefreshing(true);
            }
        });
        onRefresh();

    }

    private void downLoadData(final int type, int index) {
        MyAppCollection applicationContext = (MyAppCollection) getApplicationContext();
        User user = applicationContext.getUser();

        Log.d("IP", UrlData.PCINFOLIST_URL);
        HttpUtilsHelper.getInstance().send(HttpRequest.HttpMethod.POST, UrlData.PCINFOLIST_URL, XutilsRequest.getCpCustomerList(user.getEmpId(),
                        customername, customerphone, starttime, endtime, index, PAGESIZE, isSuppleState, guwen),
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                        Log.d("cpresult", objectResponseInfo.result);
                        Bundle bundle = JsonParser.cpCustomersParser(objectResponseInfo.result);
                        List<CpCustomer> cpCustomerList = (List<CpCustomer>) bundle.get("list");
                        if (bundle.getString("totalrows") != null) {
                            tv_find_tatol.setText(bundle.getString("totalrows"));
                            Log.d("cpresultListSize",bundle.getString("totalrows"));
                        }

//                        Log.d("cpresultList", cpCustomerList.toString());

                        Message message = handler.obtainMessage();
                        switch (type) {
                            case 1:
                                ultimateRecyclerView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ultimateRecyclerView.setRefreshing(false);
                                    }
                                });

                                cpList.clear();
                                cpList.addAll(cpCustomerList);
                                if (cpCustomerList.size() < 10) {
                                    message.arg1 = 2;
                                } else {
                                    message.arg1 = 1;
                                }
                                //       showCustomerPro();


                                break;
                            case 2:
                                if (cpCustomerList.size() == 0) {
                                    pageindex--;
                                    // Toast.makeText(CpCustomActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                                    //   showCustomerNoData();
                                    message.arg1 = 2;
                                } else {
                                    // showCustomerPro();
                                    message.arg1 = 1;
                                }

                                cpList.addAll(cpCustomerList);
                                break;
                        }
                        handler.sendMessage(message);
                        adapter.notifyDataSetChanged();
                        if (cpList.size() > 0) {
                            nodata.setVisibility(View.GONE);
                        } else {
                            nodata.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                        Toast.makeText(CpCustomActivity.this, "网络请求出错，请检查网络", Toast.LENGTH_SHORT).show();
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
                        if (cpList.size() > 0) {
                            nodata.setVisibility(View.GONE);
                        } else {
                            nodata.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void initDate() {
//        DbUtils dbUtils = DbUtilsHelper.newInstance(this);
//        try {
//            SearchCpCustomer first = dbUtils.findFirst(SearchCpCustomer.class);
//            if (first != null) {
//                if (!TextUtils.isEmpty(first.getCustomerName())) {
//                    customername = first.getCustomerName();
//                }
//
//                if (!TextUtils.isEmpty(first.getCustomerPhone())) {
//                    customerphone = first.getCustomerPhone();
//                }
//
//                if (!TextUtils.isEmpty(first.getEmpNmae())) {
//                    guwen = first.getEmpNmae();
//                }
//
//                if (!TextUtils.isEmpty(first.getFromeTime())) {
//                    starttime = first.getFromeTime();
//                }
//
//                if (!TextUtils.isEmpty(first.getToTime())) {
//                    endtime = first.getToTime();
//                }
//
//                if (first.getIsSuppleState() != null) {
//                    isSuppleState = first.getIsSuppleState();
//                }
//            } else {
//                Calendar now = Calendar.getInstance();
//                String nowStr = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
//                starttime = nowStr;
//                endtime = nowStr;
//            }
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
        if (getIntent().getStringExtra("issupplestate") != null) {
            isSuppleState = getIntent().getStringExtra("issupplestate");
        }

        if (getIntent().getStringExtra("fromtime") != null) {
            starttime = getIntent().getStringExtra("fromtime");
        }
        if (getIntent().getStringExtra("totime") != null) {
            endtime = getIntent().getStringExtra("totime");
        }

        if (getIntent().getStringExtra("customername") != null) {
            customername = getIntent().getStringExtra("customername");
        }
        if (getIntent().getStringExtra("customerphone") != null) {
            customerphone = getIntent().getStringExtra("customerphone");
        }
        if (getIntent().getStringExtra("guwen") != null) {
            guwen = getIntent().getStringExtra("guwen");
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

    @Override
    public void onRefresh() {
        pageindex = 1;
        downLoadData(DOWN, pageindex);
    }

    @Override
    public void loadMore(int itemsCount, int maxLastVisiblePosition) {
        Log.d("xiala", "xiala");
        pageindex++;
        downLoadData(UP, pageindex);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activtiy_cpcustomers_back://点击了返回按钮
                finish();
                break;
            case R.id.activtiy_cpcustomers_select:
                Intent intent = new Intent(this, CpCustomerScreenActivity.class);
                intent.putExtra("name", customername);
                intent.putExtra("phone", customerphone);
                intent.putExtra("fromtime", starttime);
                intent.putExtra("totime", endtime);
                intent.putExtra("issupplestate", isSuppleState);
                intent.putExtra("guwen", guwen);
                startActivityForResult(intent, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            customername = data.getStringExtra("name");
            customerphone = data.getStringExtra("phone");
            starttime = data.getStringExtra("fromtime");
            endtime = data.getStringExtra("totime");
            isSuppleState = data.getStringExtra("issupplestate");
            guwen = data.getStringExtra("guwen");
            ultimateRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    ultimateRecyclerView.setRefreshing(true);
                }
            });
            onRefresh();
        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ultimateRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    ultimateRecyclerView.setRefreshing(true);
                }
            });
            onRefresh();
        }

    }


//    public class ConnectionChangeReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//            NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
//
//                ultimateRecyclerView.disableLoadmore();
//                //改变背景或者 处理网络的全局变量
//            } else {
//                //改变背景或者 处理网络的全局变量
//                //   ultimateRecyclerView.enableLoadmore();
//                if (!ultimateRecyclerView.isLoadMoreEnabled()) {
//                    ultimateRecyclerView.reenableLoadmore();
//                    adapter.setCustomLoadMoreView(View.inflate(CpCustomActivity.this, R.layout.custom_bottom_progressbar, null));
//                    ultimateRecyclerView.enableLoadmore();
//                }
//
//                // adapter.notifyDataSetChanged();
//
//            }
//        }
//    }
//
//    private ConnectionChangeReceiver myReceiver;
//
//    private void registerReceiver() {
//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        myReceiver = new ConnectionChangeReceiver();
//        this.registerReceiver(myReceiver, filter);
//    }
//
//    private void unregisterReceiver() {
//        this.unregisterReceiver(myReceiver);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver();
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


}
