package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.adapter.PartsDetailAdapter;
import com.tygas.tianyu.tianyu.ui.adapter.RepairDetailFragmentPAgeAdapter;
import com.tygas.tianyu.tianyu.ui.adapter.RepairProjectAdapter;
import com.tygas.tianyu.tianyu.ui.model.MaintenanceHistory;
import com.tygas.tianyu.tianyu.ui.model.PartsDetail;
import com.tygas.tianyu.tianyu.ui.model.RepairProject;
import com.tygas.tianyu.tianyu.ui.view.fragment.PartsDetailFragment;
import com.tygas.tianyu.tianyu.ui.view.fragment.RepairProjectFragment;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SJTY_YX on 2016/3/2.
 * 维修明细
 */
public class FirstProjectRepairDetailActivity extends Activity implements View.OnClickListener{

    private ImageView backImageView;//返回按钮
    private TextView projectdetailTextView;//工时费合计    activity_repairdetail_tv_projectdetail_tatol
    private TextView partDetailTextView;//合计金额  activity_repairdetail_tv_partdetail_tatol

    private TextView itemsProjectEmptyTextView;//activity_repairdetail_tv_itemsproject_empty
    private TextView partdetailEmptyTextView;//activity_repairdetail_tv_partdetail_empty

    private ListView itemsProjectListView;//activity_repairdetail_lv_itemsproject
    private ArrayList<RepairProject> itemsProjects;
    private RepairProjectAdapter itemProjectAdapter;

    private ListView partdetailListView;//activity_repairdetail_lv_partdetail
    private ArrayList<PartsDetail> partsDetails;
    private PartsDetailAdapter partsDetailAdapter;


    private MaintenanceHistory data;

    //private TabLayout tabLayout;
    //private ViewPager contentViewPager;

    /*private PartsDetailFragment partsDetailFragment;//配件明细
    private RepairProjectFragment repairProjectFragment;//维修项目明细
    private ArrayList<Fragment> fragments;*/

    //private RepairDetailFragmentPAgeAdapter adapter;

    private static final int HANDLER_UPDATAUI_ITEMS = 0x0001;//items 加载完成
    private static final int HANDLER_UPDATAUI_PARTS = 0x0003;// part加载完成
    private static final int HANDLER_LOADDATABEFORE_ITEMS = 0x0002;//加载数据之前
    private static final int HANDLER_LOADDATABEFORE_PARTS = 0x0004;//

    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANDLER_UPDATAUI_ITEMS:
                    uploadUIItems();
                    break;
                case HANDLER_UPDATAUI_PARTS:
                    uploadUIParts();
                    break;
                case HANDLER_LOADDATABEFORE_ITEMS:
                    loadDataBeforeItems();
                    break;
                case HANDLER_LOADDATABEFORE_PARTS:
                    loadDataBeforeParts();
                    break;
            }
        }
    };//end Handler

    private void loadDataBeforeParts() {
        partdetailEmptyTextView.setVisibility(View.VISIBLE);
        partdetailEmptyTextView.setText("数据加载中..");
        partdetailListView.setVisibility(View.GONE);
        partDetailTextView.setText("");
    }

    private void loadDataBeforeItems() {
            itemsProjectEmptyTextView.setVisibility(View.VISIBLE);
            itemsProjectEmptyTextView.setText("数据加载中..");
            itemsProjectListView.setVisibility(View.GONE);
        projectdetailTextView.setText("");
    }

    private void uploadUIItems(){
        if(itemsProjects == null || itemsProjects.size() == 0){
            itemsProjectEmptyTextView.setVisibility(View.VISIBLE);
            itemsProjectEmptyTextView.setText("没有数据");
            itemsProjectListView.setVisibility(View.GONE);
        }else{
            itemsProjectEmptyTextView.setVisibility(View.GONE);
            itemsProjectListView.setVisibility(View.VISIBLE);
        }
        if(itemsProjects != null){
            double tatol = 0;
            for(int i=0;i<itemsProjects.size();i++){
                tatol += Double.valueOf(itemsProjects.get(i).getAmountTotal().equals("") ? "0" : itemsProjects.get(i).getAmountTotal());
            }
            DecimalFormat df   =new   DecimalFormat("0.00");
            String format = df.format(tatol);
            projectdetailTextView.setText(format);
        }
        itemProjectAdapter.notifyDataSetChanged();
    }

    private void uploadUIParts() {
        if(partsDetails == null || partsDetails.size() == 0){
            partdetailEmptyTextView.setVisibility(View.VISIBLE);
            partdetailEmptyTextView.setText("没有数据");
            partdetailListView.setVisibility(View.GONE);
        }else{
            partdetailEmptyTextView.setVisibility(View.GONE);
            partdetailListView.setVisibility(View.VISIBLE);
        }
        partsDetailAdapter.notifyDataSetChanged();
        //设置总金额
        if(partsDetails != null){
            double tatol = 0;
            for(int i=0;i<partsDetails.size();i++){
                tatol += Double.valueOf(partsDetails.get(i).getTrueSalePriceTotal().equals("") ? "0" : partsDetails.get(i).getTrueSalePriceTotal());
            }
            DecimalFormat df   =new   DecimalFormat("0.00");
            String format = df.format(tatol);
            partDetailTextView.setText(format);
        }
    }//end uploadUI

    private static final String LOG_TAG = "FirstProjectRepairDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_repairdetail);
        data = (MaintenanceHistory) getIntent().getSerializableExtra("data");
        if(data == null){
            finish();
        }
        initView();
        loadData();
    }

    private void loadData() {
        //加载 项目明细
        uiHandler.sendEmptyMessage(HANDLER_LOADDATABEFORE_ITEMS);
        loadDataItems();
        //加载配件明细
        uiHandler.sendEmptyMessage(HANDLER_LOADDATABEFORE_PARTS);
        loadDataParts();
    }


    private void loadDataItems() {
        RequestParams requestParams = XutilsRequest.getAftItemsDetail(data.getReciveComeID());
        HttpUtils httpUtils = HttpUtilsHelper.getInstance();
        httpUtils.send(HttpRequest.HttpMethod.POST, UrlData.AFTITEMSDETAIL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    parseDataItems(responseInfo.result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                uiHandler.sendEmptyMessage(HANDLER_UPDATAUI_ITEMS);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(FirstProjectRepairDetailActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                uiHandler.sendEmptyMessage(HANDLER_LOADDATABEFORE_ITEMS);
            }
        });
    }//end loadData

    private void parseDataItems(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        String status = jsonObject.getString("status");
        if("Success".equals(status)){
            String dataStr = jsonObject.getString("data");
            if(dataStr.length()>10){
                JSONObject jon = new JSONObject(dataStr);
                parseRepairProject(jon);
            }
        }else{
            if(jsonObject.getString("message") != null && !"null".equals(jsonObject.getString("message"))){
                Toast.makeText(this,""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        }
    }//end parseData

    private void parseRepairProject(JSONObject jon) throws JSONException {
        JSONArray arr = jon.getJSONArray("WxItemsDetailList");
        for(int i=0;i<arr.length();i++){
            JSONObject jsonObject = arr.getJSONObject(i);
            RepairProject repairProject = new RepairProject();
            repairProject.setProjectName(jsonObject.getString("ProjectName"));
            repairProject.setAmountTotal(jsonObject.getString("AmountTotal"));
            repairProject.setServiceGroupName(jsonObject.getString("ServiceGroupName"));
            itemsProjects.add(repairProject);
        }
    }

    private void loadDataParts() {
        RequestParams requestParams = XutilsRequest.getAftItemsDetail(data.getReciveComeID());
        HttpUtils httpUtils = HttpUtilsHelper.getInstance();
        httpUtils.send(HttpRequest.HttpMethod.POST, UrlData.AFTPARTDETAIL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    parseDataParts(responseInfo.result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                uiHandler.sendEmptyMessage(HANDLER_UPDATAUI_PARTS);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(FirstProjectRepairDetailActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                uiHandler.sendEmptyMessage(HANDLER_UPDATAUI_PARTS);
            }
        });
    }//end loadData

    private void parseDataParts(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        String status = jsonObject.getString("status");
        if("Success".equals(status)){
            String dataStr = jsonObject.getString("data");
            if(dataStr.length()>10){
                JSONObject jon = new JSONObject(dataStr);
                parsePartsDetail(jon);
            }
        }else{
            if(jsonObject.getString("message") != null && !"null".equals(jsonObject.getString("message"))){
                Toast.makeText(this,""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        }
    }//end parseData

    private void parsePartsDetail(JSONObject jon) throws JSONException {
        JSONArray arr = jon.getJSONArray("WxPartDetailList");
        for(int i=0;i<arr.length();i++){
            JSONObject jsonObject = arr.getJSONObject(i);
            PartsDetail partsDetail = new PartsDetail();
            partsDetail.setPartName(jsonObject.getString("PartName"));
            partsDetail.setPartCode(jsonObject.getString("PartCode"));
            partsDetail.setTrueSalePriceTotal(jsonObject.getString("TrueSalePriceTotal"));
            partsDetails.add(partsDetail);
        }
    }


    //初始化控件
    private void initView() {
        backImageView = (ImageView) findViewById(R.id.activity_repairdetail_iv_back);
        /*tabLayout = (TabLayout) findViewById(R.id.activity_repairdetail_tl_title);
        contentViewPager = (ViewPager) findViewById(R.id.activity_repairdetail_vp_content);*/
        backImageView.setOnClickListener(this);
        projectdetailTextView = (TextView) findViewById(R.id.activity_repairdetail_tv_projectdetail_tatol);
        partDetailTextView  = (TextView) findViewById(R.id.activity_repairdetail_tv_partdetail_tatol);
        itemsProjectEmptyTextView = (TextView) findViewById(R.id.activity_repairdetail_tv_itemsproject_empty);
        partdetailEmptyTextView = (TextView) findViewById(R.id.activity_repairdetail_tv_partdetail_empty);
        itemsProjectListView = (ListView) findViewById(R.id.activity_repairdetail_lv_itemsproject);
        partdetailListView = (ListView) findViewById(R.id.activity_repairdetail_lv_partdetail);

        itemsProjects = new ArrayList<>();
        itemProjectAdapter = new RepairProjectAdapter(itemsProjects);
        itemsProjectListView.setAdapter(itemProjectAdapter);
        itemsProjectListView.setEnabled(false);
        partsDetails = new ArrayList<>();
        partsDetailAdapter = new PartsDetailAdapter(partsDetails);
        partdetailListView.setAdapter(partsDetailAdapter);
        partdetailListView.setEnabled(false);
        /*fragments = new ArrayList<>();
        partsDetailFragment = new PartsDetailFragment();
        repairProjectFragment = new RepairProjectFragment();
        fragments.add(repairProjectFragment);
        fragments.add(partsDetailFragment);
        adapter = new RepairDetailFragmentPAgeAdapter(getSupportFragmentManager(),fragments);
        contentViewPager.setAdapter(adapter);
        tabLayout.setOnTabSelectedListener(this);
        tabLayout.setupWithViewPager(contentViewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);*/
    }//end initView

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_repairdetail_iv_back://点击了返回按钮
                finish();
                break;
        }
    }//end onclick

    /*@Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        contentViewPager.setCurrentItem(position);
    }
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}
    @Override
    public void onTabReselected(TabLayout.Tab tab) {}*/
}
