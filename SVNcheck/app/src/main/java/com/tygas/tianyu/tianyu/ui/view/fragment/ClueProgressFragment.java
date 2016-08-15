package com.tygas.tianyu.tianyu.ui.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.adapter.ClueProgressAdapter;
import com.tygas.tianyu.tianyu.ui.model.Clue;
import com.tygas.tianyu.tianyu.ui.model.ClueProgress;
import com.tygas.tianyu.tianyu.ui.model.PtCustomer;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.ui.view.activity.ClueDetailActivity;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/2/1.
 */
public class ClueProgressFragment extends Fragment {


    private View root;
    private User user;

    private TextView emptyTextView;//没有数据的时候显示的视图
    private ListView listView;//显示的列表

    private ArrayList<ClueProgress> data;
    private ClueProgressAdapter adapter;//适配器
    private PtCustomer clue;
    private View footerView;

    private static final String LOG_TAG = "ClueProgressFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_clueprogress,null);
        getUserFromActivity();
        emptyTextView = (TextView) root.findViewById(R.id.fragment_clueprogress_tv_empty);
        listView = (ListView) root.findViewById(R.id.fragment_clueprogress_lv_content);
        footerView = root.findViewById(R.id.frgment_customersinfo_bt_callout);
        Bundle bundle = getArguments();
        clue = (PtCustomer) bundle.getSerializable("data");
        return root;
    }


    @Override
    public void onStart() {
        super.onStart();
        if(data != null){
            data.clear();
        }
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
        emptyTextView.setText("数据加载..");
        emptyTextView.setVisibility(View.VISIBLE);
        initData();
    }

    private void getUserFromActivity(){
        MyAppCollection myAppCollection = (MyAppCollection) getActivity().getApplication();
        if(myAppCollection.getUser() == null){
            user = new User();
        }else{
            user = myAppCollection.getUser();
        }
    }

    private void initData() {
        RequestParams requestParams = XutilsRequest.getClue(clue.getCustomerId());
        HttpUtils utils = HttpUtilsHelper.getInstance();
        utils.send(HttpRequest.HttpMethod.POST, UrlData.CLUE_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.i(LOG_TAG,"[返回的数据]"+responseInfo.result);
                try {
                    parseJson(responseInfo.result);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ClueProgressFragment.this.getActivity(),"数据解析出错",Toast.LENGTH_SHORT).show();
                    emptyTextView.setText("数据解析出错");
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getActivity(),"网络请求失败",Toast.LENGTH_SHORT).show();
                emptyTextView.setText("网络请求失败");
            }
        });
    }//end initData

    private void parseJson(String result) throws JSONException {

        if(result != null && result.length() > 0){
            JSONObject jsonObject = new JSONObject(result);
            String status = jsonObject.getString("status");
            if("Success".equals(status)){
                //数据返回成功
                data = new ArrayList<>();
                adapter = new ClueProgressAdapter(data,clue,user.getEmpName());
                String dataStr = jsonObject.getString("data");
                if(dataStr != null && dataStr.length() > 10){
                    JSONObject object = new JSONObject(dataStr);
                    JSONArray jsonArray = object.getJSONArray("ClueDetailed");
                    for(int i=0;i<jsonArray.length();i++){
                        data.add(parseClueProgress(jsonArray.getJSONObject(i)));
                    }
                    listView.setAdapter(adapter);
                    emptyTextView.setVisibility(View.GONE);
                }else{
                    Toast.makeText(getActivity(),"没有数据",Toast.LENGTH_SHORT).show();
                    emptyTextView.setText("没有数据");
                }
            }else{
                Toast.makeText(getActivity(),"数据错误",Toast.LENGTH_SHORT).show();
                emptyTextView.setText("数据错误");
            }
        }else{
            //没有返回数据
            Toast.makeText(getActivity(),"数据返回错误",Toast.LENGTH_SHORT).show();
            emptyTextView.setText("数据返回错误");
        }

    }

    private ClueProgress parseClueProgress(JSONObject jsonObject) throws JSONException {
        ClueProgress clueProgress = new ClueProgress();
        clueProgress.setEmpName(jsonObject.getString("empName"));
        clueProgress.setIntentLevel(jsonObject.getString("intentLevel"));
        clueProgress.setCallTime(jsonObject.getString("callTime"));
        clueProgress.setCusComeDate(jsonObject.getString("cusComeDate"));
        clueProgress.setTalkProcess(jsonObject.getString("talkProcess"));
        return clueProgress;
    }


}
