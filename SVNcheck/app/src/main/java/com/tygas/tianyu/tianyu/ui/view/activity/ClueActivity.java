package com.tygas.tianyu.tianyu.ui.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
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
import com.tygas.tianyu.tianyu.ui.adapter.ClueUltiRecycleAdapter;
import com.tygas.tianyu.tianyu.ui.model.Clue;
import com.tygas.tianyu.tianyu.ui.model.PtCustomer;
import com.tygas.tianyu.tianyu.ui.model.PtCustomerFilter;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by SJTY_YX on 2016/1/19.
 * 我的线索列表
 */
public class ClueActivity extends BaseActivity implements View.OnClickListener, UltimateRecyclerView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    private ImageView backImageView;//返回按钮
    private ImageView filterImageView;//筛选按钮

    private UltimateRecyclerView ultimateRecyclerView;
    private TextView emptyView;//没有数据的时候的视图
    private LinearLayoutManager linearLayoutManager;
    private ViewGroup loadMoreView;//加载更多的时候的布局
    private ClueUltiRecycleAdapter adapter;
    private ArrayList<PtCustomer> data;
    private TextView tatolItemTextView;//显示总的条数

    private User user;//员工信息
    public static String clueName = "";//线索姓名
    public static String cluePhone = "";//线索电话
    public static String cusempname = "";//员工电话
    public static String isExpiry = "";//时候逾期

    public static String level = "";//战败的等级
    public static String begintime;
    public static String endtime;

    public int pageIndex = 1;//
    private String pageSize = "10";//页数大小

    private boolean havemoreData;//是否有多的数据

    private PhonStateLisen phonStateLisen;
    private TelephonyManager manager;

    private static final String LOG_TAG = "ClueActivity";

    private static final int FILTER_REQUEST_CODE = 0x0001;//筛选的请求码

    private static final int HANDLER_LISTVIEW_REFRESH_TRUE = 0x0005;// ultimateRecyclerView.setRefreshing(true);
    private static final int HANDLER_LOAD_FINIS = 0x0009;//数据加载完成
    private static final int HANDLER_NET_ERROR = 0x000A;//网络请求错误
    private static final int HANDLER_SHOW_LOADMORE = 0x000B;

    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANDLER_LISTVIEW_REFRESH_TRUE:
                    ultimateRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            ultimateRecyclerView.setRefreshing(true);
                        }
                    });
                    adapter.notifyDataSetChanged();
                    tatolItemTextView.setText(""+totalrows);
                    break;
                case HANDLER_LOAD_FINIS://数据加载完成
                    uploadUILoadFinis();
                    break;
                case HANDLER_NET_ERROR://网络请求错误
                    netError();
                    break;
                case HANDLER_SHOW_LOADMORE:
                    showLoadMore();
                    break;
            }
        }//end handleMessage
    };

    private void netError() {
        loadMoreView.getChildAt(0).setVisibility(View.INVISIBLE);
        TextView textView = (TextView) loadMoreView.getChildAt(1);
        textView.setText("网络请求错误");
    }

    private void uploadUILoadFinis() {
        ultimateRecyclerView.setRefreshing(false);
        showEmptyView();
        showLoadMore();
        tatolItemTextView.setText("" + totalrows);
        adapter.notifyDataSetChanged();
        //ultimateRecyclerView.invalidate();
    }

    private Integer totalrows = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_clue);
        MyAppCollection myAppCollection = (MyAppCollection) getApplicationContext();
        if(myAppCollection.getUser() != null){
            user = myAppCollection.getUser();
        }else{
            finish();
        }
        if(begintime == null){
            begintime = getNowTime();
        }
        if(endtime == null){
            endtime = getNowTime();
        }

        if(savedInstanceState != null){
            clueName = savedInstanceState.getString("clueName");
            cluePhone = savedInstanceState.getString("cluePhone");
            cusempname = savedInstanceState.getString("cusempname");
            isExpiry = savedInstanceState.getString("isExpiry");
            begintime = savedInstanceState.getString("begintime");
            endtime = savedInstanceState.getString("endtime");
            level = savedInstanceState.getString("level");
        }else{
            Intent intent = getIntent();
            int type = intent.getIntExtra("type",0);
            if(type == 1){
                clueName = intent.getStringExtra("name");
                cluePhone = intent.getStringExtra("phone");
                level = intent.getStringExtra("level");
                cusempname = intent.getStringExtra("cusempname");
                isExpiry = intent.getStringExtra("isExpiry");
                begintime = intent.getStringExtra("begintime");
                endtime = intent.getStringExtra("endtime");
            }else if(type == 10000){
                //begintime = null;
                //isExpiry = "";
                //endtime = null;
            }else if(type == 100){
                isExpiry = "未逾期";
                begintime = null;
                endtime = null;
                clueName = "";
                cluePhone = "";
                level = "";
                cusempname = "";
            }else if(type == 1000){
                isExpiry = "逾期";
                begintime = "";
                endtime = "";
                clueName = "";
                cluePhone = "";
                level = "";
                cusempname = "";
            }
        }
        if(begintime == null){
            begintime = getNowTime();
        }
        if(endtime == null){
            endtime = getNowTime();
        }
        phonStateLisen = myAppCollection.getPhonStateLisen();
        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        manager.listen(phonStateLisen, PhoneStateListener.LISTEN_CALL_STATE);
        initView();
        initData();
        initBroad();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("clueName",clueName);
        outState.putString("cluePhone",cluePhone);
        outState.putString("cusempname",cusempname);
        outState.putString("isExpiry",isExpiry);
        outState.putString("begintime",begintime);
        outState.putString("endtime", endtime);
        outState.putString("level", level);
    }

    @Override
    public void onBackPressed() {
        if(phonStateLisen.isCall){
            Toast.makeText(this,"通话过程中不能返回",Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(refreshDataBroad);
        super.onDestroy();
        if(manager != null){
            if(Config.isDebug)
                Log.i(LOG_TAG,"[调用destory设置监听为null]");
            manager.listen(null,PhoneStateListener.LISTEN_CALL_STATE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case FILTER_REQUEST_CODE:
                    clueName = data.getStringExtra("name");
                    cluePhone = data.getStringExtra("phone");
                    cusempname = data.getStringExtra("cusempname");
                    isExpiry = data.getStringExtra("isExpiry");
                    begintime = data.getStringExtra("fromtime");
                    endtime = data.getStringExtra("totime");
                    level = data.getStringExtra("level");
                    initData();
                    break;
            }
        }//end resultcode
    }//end onActivityResult

    public void initData() {
        pageIndex = 1;
        totalrows = 0;
        havemoreData = false;
        data.clear();
        uiHandler.sendEmptyMessage(HANDLER_LISTVIEW_REFRESH_TRUE);
        Log.i(LOG_TAG,"[initData]请求数据");
        loadData();
    }


    private void loadData() {
        RequestParams requestParams = XutilsRequest.getClueList(user.getEmpId(),clueName,cluePhone,
                                                                isExpiry,begintime,endtime,String.valueOf(pageIndex),
                                                                pageSize,cusempname,level);
        if(Config.isDebug)
        Log.i(LOG_TAG,"[加载数据的参数] cusempname "+cusempname+" clueName "+clueName+"   cluePhone "+cluePhone+"  isExpiry "+isExpiry+"  begintime "+begintime+"  endtime "+endtime+"  pageIndex "+pageIndex);

        HttpUtils httpUtils = HttpUtilsHelper.getInstance();
        httpUtils.send(HttpRequest.HttpMethod.POST, /*"http://118.123.249.59:1999/OpenService/App.aspx?type=cluelist"*/
                                UrlData.CLUELIST_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.i(LOG_TAG,"[返回的数据]"+responseInfo.result);
                if (responseInfo.result != null && responseInfo.result.length() > 4) {
                    //解析灵魂厌倦
                    try {
                        parseResult(responseInfo.result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                uiHandler.sendEmptyMessage(HANDLER_LOAD_FINIS);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(ClueActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                if(data == null || data.size() == 0){
                    totalrows = 0;
                }
                uiHandler.sendEmptyMessage(HANDLER_LOAD_FINIS);
                uiHandler.sendEmptyMessage(HANDLER_NET_ERROR);
            }
        });
    }

    //解析请求的数据 {"status":"Success","message":"查询DataSet为空.","data":"{\"ClueList\":null}"}
    private void parseResult(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        String status = jsonObject.getString("status");
        if("Success".equals(status)){
            String dataStr = jsonObject.getString("data");
            if(dataStr != null && dataStr.length() > 4){
                JSONObject dataobj = new JSONObject(dataStr);
                JSONArray arr = dataobj.optJSONArray("ClueList");
                if(arr == null || arr.length() == 0){
                    havemoreData = false;
                    totalrows = 0;
                }else{
                    if(data == null){
                        data = new ArrayList<>();
                    }
                    for(int i=0;i<arr.length();i++){
                        data.add(parseClue(arr.getJSONObject(i)));
                    }
                    totalrows = Integer.valueOf(dataobj.getString("totalrows")); //这里和文档不一样
                    if(totalrows > data.size()){
                        havemoreData = true;
                        pageIndex++;
                    }else{
                        havemoreData = false;
                    }
                }

            }else{
                Toast.makeText(this,"数据返回异常"+jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"数据返回失败",Toast.LENGTH_SHORT).show();
        }
    }

    private PtCustomer parseClue(JSONObject jsonObject) throws JSONException {
        PtCustomer ptCustomer = new PtCustomer();
        if(!jsonObject.isNull("clueId")){
            ptCustomer.setCustomerId(jsonObject.getString("clueId"));
        }else {
            ptCustomer.setCustomerId("");
        }

        if(!jsonObject.isNull("clueName")){
            ptCustomer.setCustomerName(jsonObject.getString("clueName"));
        }else {
            ptCustomer.setCustomerName("");
        }

        if(!jsonObject.isNull("cluePhone")){
            ptCustomer.setCustomerPhone(jsonObject.getString("cluePhone"));
        }else {
            ptCustomer.setCustomerPhone("");
        }

        if(!jsonObject.isNull("empName")){
            ptCustomer.setEmpName(jsonObject.getString("empName"));
        }else {
            ptCustomer.setEmpName("");
        }

        if(!jsonObject.isNull("intentLevel")){
            ptCustomer.setIntentLevel(jsonObject.getString("intentLevel"));
        }else {
            ptCustomer.setIntentLevel("");
        }

        if(!jsonObject.isNull("lastContratTime")){
            ptCustomer.setLastContratTime(jsonObject.getString("lastContratTime"));
        }else {
            ptCustomer.setLastContratTime("");
        }

        if(!jsonObject.isNull("carseriesName")){
            ptCustomer.setCarseriesName(jsonObject.getString("carseriesName"));
        }else {
            ptCustomer.setCarseriesName("");
        }

        if(!jsonObject.isNull("carModelName")){
            ptCustomer.setCarModelName(jsonObject.getString("carModelName"));
        }else {
            ptCustomer.setCarModelName("");
        }

        if(!jsonObject.isNull("sourceChannelName")){
            ptCustomer.setSourceChannelName(jsonObject.getString("sourceChannelName"));
        }else {
            ptCustomer.setSourceChannelName("");
        }

        if(!jsonObject.isNull("liveProvince")){
            ptCustomer.setLiveProvince(jsonObject.getString("liveProvince"));
        }else {
            ptCustomer.setLiveProvince("");
        }

        if(!jsonObject.isNull("liveCity")){
            ptCustomer.setLiveCity(jsonObject.getString("liveCity"));
        }else {
            ptCustomer.setLiveCity("");
        }

        if(!jsonObject.isNull("liveArea")){
            ptCustomer.setLiveArea(jsonObject.getString("liveArea"));
        }else {
            ptCustomer.setLiveArea("");
        }

        if(!jsonObject.isNull("channelName")){
            ptCustomer.setChannelName(jsonObject.getString("channelName"));
        }else {
            ptCustomer.setChannelName("");
        }

        if(!jsonObject.isNull("nextCallTime")){
            ptCustomer.setNextCallTime(jsonObject.getString("nextCallTime"));
        }else {
            ptCustomer.setNextCallTime("");
        }

        if(!jsonObject.isNull("TalkProcess")){
            ptCustomer.setLastTalkProcess(jsonObject.getString("TalkProcess"));
        }else {
            ptCustomer.setLastTalkProcess("");
        }

        if(!jsonObject.isNull("SparePhone")){
            ptCustomer.setSparePhone(jsonObject.getString("SparePhone"));
        }else {
            ptCustomer.setSparePhone("");
        }

        if(!jsonObject.isNull("CarColorName")){
            ptCustomer.setColorName(jsonObject.getString("CarColorName"));
        }else {
            ptCustomer.setColorName("");
        }

        if(!jsonObject.isNull("CarBrandName")){
            ptCustomer.setCarBrandName(jsonObject.getString("CarBrandName"));
        }else {
            ptCustomer.setCarBrandName("");
        }

        if(!jsonObject.isNull("BuyCarUsage")){
            ptCustomer.setUseageName(jsonObject.getString("BuyCarUsage"));
        }else {
            ptCustomer.setUseageName("");
        }

        if(!jsonObject.isNull("MortgeCondition")){
            ptCustomer.setPayType(jsonObject.getString("MortgeCondition"));
        }else {
            ptCustomer.setPayType("");
        }

        if(!jsonObject.isNull("BuyChange")){
            ptCustomer.setIsChange(jsonObject.getString("BuyChange"));
        }else {
            ptCustomer.setIsChange("");
        }

        if(!jsonObject.isNull("QuoteSituation")){
            ptCustomer.setQuoteSituation(jsonObject.getString("QuoteSituation"));
        }else {
            ptCustomer.setQuoteSituation("");
        }

        if(!jsonObject.isNull("FocusCarFeatureName")){
            ptCustomer.setFocusCarModel(jsonObject.getString("FocusCarFeatureName"));
        }else {
            ptCustomer.setFocusCarModel("");
        }


        return ptCustomer;
    }

    private void showLoadMore(){
        if(havemoreData){
            loadMoreView.getChildAt(0).setVisibility(View.VISIBLE);
            TextView textView = (TextView) loadMoreView.getChildAt(1);
            textView.setText("加载..");
        }else{
            loadMoreView.getChildAt(0).setVisibility(View.INVISIBLE);
            TextView textView = (TextView) loadMoreView.getChildAt(1);
            textView.setText("没有更多数据");
        }
    }

    /**
     * 没有数据显示
     *
     */
    private void showEmptyView(){
        if(data == null || data.size() == 0){
            emptyView.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.GONE);
        }
    }


    private String getNowTime(){
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH)+1;
        int d = calendar.get(Calendar.DATE);
        return y+"-"+m+"-"+d;
    }

    private void initView() {
        tatolItemTextView = (TextView) findViewById(R.id.activity_clue_tv_tatol_item);
        //uiHandler.sendEmptyMessage(HANDLER_CAHNGE_TATOLITEM);
        tatolItemTextView.setText(""+totalrows);
        backImageView = (ImageView) findViewById(R.id.activity_clue_iv_back);
        backImageView.setOnClickListener(this);
        filterImageView = (ImageView) findViewById(R.id.activity_clue_iv_filter);
        filterImageView.setOnClickListener(this);

        ultimateRecyclerView = (UltimateRecyclerView) findViewById(R.id.activity_clue_urv_list);
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.enableLoadmore();
        ultimateRecyclerView.setHasFixedSize(false);
        ultimateRecyclerView.setOnLoadMoreListener(this);
        ultimateRecyclerView.setDefaultOnRefreshListener(this);

        emptyView = (TextView) findViewById(R.id.activity_clue_tv_empty);
        emptyView.setVisibility(View.GONE);
        loadMoreView = (ViewGroup) View.inflate(this, R.layout.custom_bottom_progressbar, null);

        data = new ArrayList<>();
        adapter = new ClueUltiRecycleAdapter(this,data);
        adapter.setCustomLoadMoreView(loadMoreView);
        ultimateRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_clue_iv_back://点击返回按钮
                if(phonStateLisen.isCall){
                    Toast.makeText(this,"通话过程中不能返回",Toast.LENGTH_SHORT).show();
                    return;
                }
                finish();
                break;
            case R.id.activity_clue_iv_filter://点击筛选按钮
                if(Config.isDebug)
                Log.i(LOG_TAG, "[点击了筛选按钮]  begintime " + begintime + "  endtime " + endtime);
                Intent intent = new Intent();
                intent.setClass(this, ClueFilterActivity.class);
                intent.putExtra("begintime", begintime);
                intent.putExtra("endtime",endtime);
                /**
                 private String clueName = "";//线索姓名
                 private String cluePhone = "";//线索电话
                 private String cusempname = "";//员工电话
                 private String isExpiry = "false";//时候逾期
                 */
                intent.putExtra("level",level);
                intent.putExtra("name",clueName);
                intent.putExtra("phone",cluePhone);
                intent.putExtra("cusempname",cusempname);
                intent.putExtra("isExpiry",isExpiry);
            //    intent.putExtra("type",PtCustomersFilterActivity.TYPE_CLUE);
                startActivityForResult(intent, FILTER_REQUEST_CODE);
                break;
        }
    }

    @Override
    public void loadMore(int itemsCount, int maxLastVisiblePosition) {
        if(havemoreData){
            uiHandler.sendEmptyMessage(HANDLER_SHOW_LOADMORE);
            loadData();
        }
    }

    @Override
    public void onRefresh() {
        initData();
    }

    private RefreshDataBroad refreshDataBroad;

    private void initBroad() {
        //生成广播处理
        refreshDataBroad = new RefreshDataBroad();
        //实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter("ty.refreshlist.clue");
        //注册广播
        this.registerReceiver(refreshDataBroad, intentFilter);
    }

    class RefreshDataBroad extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            initData();
            ultimateRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    ultimateRecyclerView.setRefreshing(true);
                }
            });
        }
    }

}
