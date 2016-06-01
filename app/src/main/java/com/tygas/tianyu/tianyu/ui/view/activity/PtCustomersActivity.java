package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.context.PhonStateLisen;
import com.tygas.tianyu.tianyu.data.Config;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.adapter.PtCustomUltiRecycleAdapter;
import com.tygas.tianyu.tianyu.ui.model.PtCustomer;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class PtCustomersActivity extends BaseActivity implements View.OnClickListener,
                                                    UltimateRecyclerView.OnLoadMoreListener,
                                                    SwipeRefreshLayout.OnRefreshListener {

    private ImageView backImageView;//返回按钮

    private UltimateRecyclerView listRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private PtCustomUltiRecycleAdapter adapter;
    private ArrayList<PtCustomer> data;
    private TextView tatolItemTextView;//显示共有多少条数据

    private ImageView filterImageView;//筛选按钮

    private User user;//登录的用户

    public static String name = "";
    public static String phone = "";
    public static String level = "";
    public static String cusempname = "";
    public static String isExpiry = "";//是否逾期
    public static String begintime;
    public static String endtime;


    private String empid;


    public int pageindex;
    private int pagesize;
    private boolean haveMore;//时候有更多的数据
    private ViewGroup loadMoreView;//加载更多的时候显示的 视图
    private TextView emptyTextView;//没有数据的时候显示的视图

    private int totalrows = 0; //总的数据条数

    private static final String LOG_TAG = "PtCustomersActivity";
    private static final int FILTER_REQUEST_CODE = 0x0001;//筛选按钮的请求码

    private static final int HANDLER_REFRESH_PULL = 0x0002;//下拉刷新时的UI
    private static final int HANDLER_LOADMORE_NHAVEMORE = 0x0003;//有更多的数据
    private static final int HANDLER_VISIT_LOADMOREVIEW = 0x0005;//显示loadmoreView
    private static final int HANDLER_LOADING = 0x0007;//正在加载
    private static final int HANDLER_LOAD_FINIS = 0x000D;//数据加载完成
    private static final int HANDLER_NET_ERROR = 0x000F;//网络请求错误

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANDLER_REFRESH_PULL:
                    listRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            listRecyclerView.setRefreshing(true);
                            showLoadMore(false, false, "");
                        }
                    });
                    adapter.notifyDataSetChanged();
                    tatolItemTextView.setText(""+totalrows);
                    break;
                case HANDLER_LOADMORE_NHAVEMORE:
                    showLoadMore(true, false, "没有更多数据");
                    break;
                case HANDLER_VISIT_LOADMOREVIEW:
                    loadMoreView.setVisibility(View.VISIBLE);
                    break;
                case HANDLER_LOADING:
                    showLoadMore(true, true, "加载..");
                    break;
                case HANDLER_LOAD_FINIS://加载完成
                    updateUILoadFinis();
                    break;
                case HANDLER_NET_ERROR://网络加载错误
                    showLoadMore(true, false, "网络请求错误");
                    break;
            }
        }
    };

    //数据加载完成的时候更新UI
    private void updateUILoadFinis() {
        tatolItemTextView.setText(""+totalrows);
        listRecyclerView.setRefreshing(false);
        if(data == null || data.size() == 0){
            emptyTextView.setVisibility(View.VISIBLE);
        }else{
            emptyTextView.setVisibility(View.GONE);
        }
        if(haveMore){
            showLoadMore(true, true, "加载..");
        }else{
            showLoadMore(true, false, "没有更多数据");
        }
        adapter.notifyDataSetChanged();
    }//end updateUILoadFinis

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_ptcustomers);

        //初始化user
        MyAppCollection applicationContext = (MyAppCollection) getApplicationContext();
        if(applicationContext.getUser() == null){
            finish();
        }else{
            user = applicationContext.getUser();
        }

        if(savedInstanceState != null){
            name = savedInstanceState.getString("name");
            phone = savedInstanceState.getString("phone");
            level = savedInstanceState.getString("level");
            cusempname = savedInstanceState.getString("cusempname");
            isExpiry = savedInstanceState.getString("isExpiry");
            begintime = savedInstanceState.getString("begintime");
            endtime = savedInstanceState.getString("endtime");
        }else{
            Intent intent = getIntent();
            int type = intent.getIntExtra("type",0);
            if(type == 1){
                name = intent.getStringExtra("name");
                phone = intent.getStringExtra("phone");
                level = intent.getStringExtra("level");
                cusempname = intent.getStringExtra("cusempname");
                isExpiry = intent.getStringExtra("isExpiry");
                begintime = intent.getStringExtra("begintime");
                endtime = intent.getStringExtra("endtime");
            }else if(type == 100){
                isExpiry = "false";
                begintime = null;
                endtime = null;
                name = "";
                phone = "";
                level = "";
                cusempname = "";
            }else if(type == 1000){
                isExpiry = "true";
                begintime = "";
                endtime = "";
                name = "";
                phone = "";
                level = "";
                cusempname = "";
            }else if(type == 10000){
                //isExpiry = "";
                //begintime = null;
                //endtime = null;
            }
        }
        if(begintime == null){
            begintime = getNowDate();//获取当前时间
        }
        if(endtime == null){
            endtime = getNowDate();
        }
        initView();
        initData();
        initBroad();
    }//end onCreate

    public void initData() {
        pageindex = 1;
        pagesize = 10;
        totalrows = 0;
        empid = user.getEmpId();//获取当前的empoid
        data.clear();
        adapter.notifyDataSetChanged();
        haveMore = false;
        handler.sendEmptyMessage(HANDLER_REFRESH_PULL);
        loadData();
    }//end initData

    //网络请求
    public void loadData(){
        HttpUtils httpUtils = HttpUtilsHelper.getInstance();
        RequestParams requestParams = XutilsRequest.getPvcList(user.getEmpId(), name, phone, level,
                begintime, endtime,
                String.valueOf(pageindex), String.valueOf(pagesize),cusempname,isExpiry);
        httpUtils.send(HttpRequest.HttpMethod.POST, UrlData.PCVLIST_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (result != null && result.length() > 0) {
                    parsePtCustomers(result);
                }
                handler.sendEmptyMessage(HANDLER_LOAD_FINIS);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                handler.sendEmptyMessage(HANDLER_LOAD_FINIS);
                handler.sendEmptyMessage(HANDLER_NET_ERROR);
                Toast.makeText(PtCustomersActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
            }
        });//http 请求
    }

    private void parsePtCustomers(String result) {
        if(data == null){
            data = new ArrayList<>();
        }
        try {
            JSONObject jsonObject = new JSONObject(result);
            String status = jsonObject.getString("status");
            if("Success".equals(status)){
                //成功返回
                Log.i(LOG_TAG,"[result]"+result);
                String dataStr = jsonObject.getString("data");
                if(dataStr != null && dataStr.length() > 0){
                    JSONObject obj = new JSONObject(dataStr);
                    totalrows = obj.getInt("totalrows");//获取总数据条数
                    if(totalrows > 0 ){
                        JSONArray arr =obj.getJSONArray("CusList");
                        for(int i=0;i<arr.length();i++){
                            JSONObject jobj = arr.getJSONObject(i);
                            data.add(parsePtCustomer(jobj));
                        }
                        if(totalrows > data.size()){
                            haveMore = true;
                            pageindex++;
                        }else{
                            haveMore = false;
                        }
                    }
                }
            }else{
                Toast.makeText(this,"解析出错"+jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                if(data == null || data.size() == 0){
                    totalrows = 0;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activtiy_clue_tv_filter://点击筛选按钮
                Intent intent = new Intent();
                intent.setClass(this, PtCustomersFilterActivity.class);
                intent.putExtra("begintime", begintime);
                intent.putExtra("endtime", endtime);
                intent.putExtra("level", level);
                intent.putExtra("name", name);
                intent.putExtra("phone",phone);
                intent.putExtra("cusempname",cusempname);
                intent.putExtra("isExpiry",isExpiry);
                intent.putExtra("type",PtCustomersFilterActivity.TYPE_PTCUSTOMER);
                startActivityForResult(intent, FILTER_REQUEST_CODE);
                break;
            case R.id.activtiy_ptcustomers_back://点击了返回按钮
                if(PhonStateLisen.isCall){
                    Toast.makeText(this,"通话过程中不能返回",Toast.LENGTH_SHORT).show();
                    return;
                }
                finish();
                break;
        }//end switch
    }//end onclick

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case FILTER_REQUEST_CODE:
                    if(data.getStringExtra("name") == null){
                        name = "";
                    }else{
                        name = data.getStringExtra("name");
                    }
                    if(data.getStringExtra("phone") == null){
                        phone = "";
                    }else{
                        phone = data.getStringExtra("phone");
                    }
                    if(data.getStringExtra("cusempname") == null){
                        cusempname = "";
                    }else{
                        cusempname = data.getStringExtra("cusempname");
                    }
                    isExpiry = data.getStringExtra("isExpiry");
                    level = data.getStringExtra("level");
                    begintime = data.getStringExtra("fromtime");
                    endtime = data.getStringExtra("totime");
                    initData();
                    break;
                case 100:
                    if(Config.isDebug)
                    Log.i(LOG_TAG,"[打电话后返回]");
                    break;
            }
        }
    }

    @Override
    public void loadMore(int itemsCount, int maxLastVisiblePosition) {
        Log.i(LOG_TAG,"[loadMore]");
        if(adapter !=null){
            if(haveMore){
                handler.sendEmptyMessage(HANDLER_LOADING);
                loadData();
            }
        }
    }//end loadMore

    private void showLoadMore(boolean show,boolean showPro,String message){
        if(loadMoreView != null){
            if(show){
                loadMoreView.setVisibility(View.VISIBLE);
                if(showPro){
                    loadMoreView.getChildAt(0).setVisibility(View.VISIBLE);
                }else{
                    loadMoreView.getChildAt(0).setVisibility(View.INVISIBLE);
                }
            }else{
                loadMoreView.setVisibility(View.GONE);
            }
            TextView textView = (TextView) loadMoreView.getChildAt(1);
            textView.setText(message);
        }
    }

    //下拉刷新的时候
    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onBackPressed() {
        if(PhonStateLisen.isCall){
            Toast.makeText(this,"通话过程中不能返回",Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }

    private PtCustomer parsePtCustomer(JSONObject jobj) throws JSONException {
        PtCustomer customer = new PtCustomer();
        customer.setCustomerId(jobj.getInt("CustomerId") + "");
        customer.setCustomerInfoID(jobj.getInt("CustomerInfoID") + "");
        customer.setCustomerName(jobj.getString("CustomerName"));
        customer.setCustomerPhone(jobj.getString("CustomerPhone"));
        customer.setEmpName(jobj.getString("EmpName"));
        customer.setIntentLevel(jobj.getString("IntentLevel"));
        customer.setLastContratTime(jobj.getString("LastContratTime"));
        customer.setLastTalkProcess(jobj.getString("LastTalkProcess"));
        customer.setCarseriesName(jobj.getString("CarseriesName"));
        customer.setCarModelName(jobj.getString("CarModelName"));
        customer.setColorName(jobj.getString("ColorName"));
        customer.setSourceChannelName(jobj.getString("SourceChannelName"));
        customer.setTurnToIntroduce(jobj.getString("TurnToIntroduce"));
        customer.setOldCusCarNO(jobj.getString("OldCusCarNO"));
        customer.setOtherBrandName(jobj.getString("OtherBrandName"));
        customer.setOtherSeriesName(jobj.getString("OtherSeriesName"));
        customer.setFailType(jobj.getString("FailType"));
        customer.setPayType(jobj.getString("PayType"));
        customer.setLiveProvince(jobj.getString("LiveProvince"));
        customer.setLiveCity(jobj.getString("LiveCity"));
        customer.setLiveArea(jobj.getString("LiveArea"));
        customer.setChannelName(jobj.getString("ChannelName"));
        customer.setContactType(jobj.getString("ContactType"));
        customer.setUseageName(jobj.getString("UseageName"));
        customer.setFocusCarModel(jobj.getString("FocusCarModel"));
        customer.setNextCallTime(jobj.getString("NextCallTime"));
        customer.setIntroducer(jobj.getString("Introducer"));
        customer.setIsChange(jobj.getString("IsChange"));
        customer.setQuoteSituation(jobj.getString("QuoteSituation"));
        return customer;
    }

    private String getNowDate(){
        Calendar cal=Calendar.getInstance();
        int y =cal.get(Calendar.YEAR);
        int m =cal.get(Calendar.MONTH)+1;
        int d =cal.get(Calendar.DATE);
        return y+"-"+m+"-"+d;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name",name);
        outState.putString("phone",phone);
        outState.putString("level",level);
        outState.putString("cusempname",cusempname);
        outState.putString("isExpiry", isExpiry);
        outState.putString("endtime",endtime);
        outState.putString("begintime",begintime);
    }


    private void initView() {
        tatolItemTextView = (TextView) findViewById(R.id.activity_ptcustomers_tv_tatol_item);
        tatolItemTextView.setText(""+totalrows);
        //handler.sendEmptyMessage(HANDLER_CHANGE_TATOLITEM);

        backImageView = (ImageView) findViewById(R.id.activtiy_ptcustomers_back);
        backImageView.setOnClickListener(this);

        emptyTextView = (TextView) findViewById(R.id.activity_ptcustomers_tv_empty);
        emptyTextView.setVisibility(View.GONE);

        listRecyclerView = (UltimateRecyclerView) findViewById(R.id.activity_ptcustomers_rv_list);
        linearLayoutManager = new LinearLayoutManager(this);
        listRecyclerView.setLayoutManager(linearLayoutManager);
        listRecyclerView.enableLoadmore();
        listRecyclerView.setHasFixedSize(false);
        listRecyclerView.setOnLoadMoreListener(this);
        listRecyclerView.setDefaultOnRefreshListener(this);

        filterImageView = (ImageView) findViewById(R.id.activtiy_clue_tv_filter);
        filterImageView.setOnClickListener(this);
        loadMoreView = (ViewGroup) View.inflate(this, R.layout.custom_bottom_progressbar, null);

        //初始化适配器 和 data的数据集合
        data = new ArrayList<>();
        adapter = new PtCustomUltiRecycleAdapter(data,this);
        adapter.setCustomLoadMoreView(loadMoreView);
        listRecyclerView.setAdapter(adapter);

    }//end initView


    private RefreshDataBroad refreshDataBroad;

    private void initBroad() {
        //生成广播处理
        refreshDataBroad = new RefreshDataBroad();
        //实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter("ty.refreshlist.pt");
        //注册广播
        this.registerReceiver(refreshDataBroad, intentFilter);
    }

    class RefreshDataBroad extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            initData();
            listRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    listRecyclerView.setRefreshing(true);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(refreshDataBroad);
        super.onDestroy();
    }

}


