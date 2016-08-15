package com.tygas.tianyu.tianyu.ui.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.tygas.tianyu.tianyu.ui.adapter.RepairProjectAdapter;
import com.tygas.tianyu.tianyu.ui.model.RepairProject;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/3/2.
 * 维修项目明细
 */
public class RepairProjectFragment extends Fragment implements View.OnClickListener {

    private TextView emptyTextView;//数据为空的时候显示的样式
    private ListView listView;//列表的布局
    private TextView tatolTextView;//总费用
    private Button calloutButton;//邀约播出按钮
    private View root;//根布局

    private ArrayList<RepairProject> data;//维修项目明细
    private RepairProjectAdapter adapter;

    private static final String LOG_TAG = "RepairProjectFragment";
    private static final int HANDLER_UPLOADUI  = 0x0001;//更新ui

    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANDLER_UPLOADUI://更新ui
                    uploadUI();
                    break;
            }
        }
    };

    private void uploadUI() {
        if(data != null && data.size() > 0){
            emptyTextView.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            //计算总费用
            double tatol = 0;
            for(int i=0;i<data.size();i++){
                RepairProject project = data.get(i);
                tatol += Double.valueOf(project.getAmountTotal() != null ? project.getAmountTotal() : "0");
            }
            tatolTextView.setText(tatol+"");
        }else{
            emptyTextView.setVisibility(View.VISIBLE);
            tatolTextView.setText("0");
        }
    }//end uploadUI

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_repairproject,null);
        initView();
        initData();
        return root;
    }

    private void initData() {
        data = new ArrayList<>();
        adapter = new RepairProjectAdapter(data);
        for(int i=0;i<10;i++){
            data.add(new RepairProject());
        }
        listView.setAdapter(adapter);
        uiHandler.sendEmptyMessage(HANDLER_UPLOADUI);
        loadData();
    }//end initData

    private void loadData() {
        RequestParams requestParams = XutilsRequest.getAftItemsDetail("1");
        HttpUtils httpUtils = HttpUtilsHelper.getInstance();
        Log.i(LOG_TAG, "[请求的url]" + UrlData.AFTITEMSDETAIL);
        httpUtils.send(HttpRequest.HttpMethod.POST, UrlData.AFTITEMSDETAIL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.i(LOG_TAG, "[请求返回的数据]" + responseInfo.result);
                try {
                    parseData(responseInfo.result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                uiHandler.sendEmptyMessage(HANDLER_UPLOADUI);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i(LOG_TAG, "[网络请求错误]" + s);
                Toast.makeText(getActivity(), "网络请求错误", Toast.LENGTH_SHORT).show();
                uiHandler.sendEmptyMessage(HANDLER_UPLOADUI);
            }
        });
    }//end loadData

    private void parseData(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        String status = jsonObject.getString("status");
        if("Success".equals(status)){
            String dataStr = jsonObject.getString("data");
            if(dataStr.length()>10){
                JSONObject jon = new JSONObject(dataStr);
                parseRepairProject(jon);
            }
        }else{
            Toast.makeText(getActivity(),""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();;
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
            data.add(repairProject);
        }
    }

    private void initView() {
        emptyTextView = (TextView) root.findViewById(R.id.fragment_repairproject_tv_empty);
        listView = (ListView) root.findViewById(R.id.fragment_repairproject_lv_content);
        tatolTextView = (TextView) root.findViewById(R.id.fragment_repairproject_tv_tatol);
        calloutButton = (Button) root.findViewById(R.id.fragment_repairproject_btn_callout);
        calloutButton.setOnClickListener(this);

    }//end initView


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_repairproject_btn_callout:
                break;
        }
    }//end onclick
}
