package com.tygas.tianyu.tianyu.ui.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import com.tygas.tianyu.tianyu.context.PhonStateLisen;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.adapter.CustomersProcessAdapter;
import com.tygas.tianyu.tianyu.ui.model.CustomerProcess;
import com.tygas.tianyu.tianyu.ui.model.PtCustomer;
import com.tygas.tianyu.tianyu.ui.view.activity.PtCustomersDetailActivity;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.PhoneValidate;
import com.tygas.tianyu.tianyu.utils.RecordFile;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/1/20.
 */
public class CustomersProcessFragment extends Fragment {


    private View root;
    private ListView listView;
    private TextView loading;
    private ArrayList<CustomerProcess> data;
    private CustomersProcessAdapter adapter;
    private PtCustomer customer;

    private PtCustomersDetailActivity activity;

    private static final String LOG_TAG = "CustomersProcessFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.activity = (PtCustomersDetailActivity) getActivity();
        root = inflater.inflate(R.layout.fragment_ptcustomers_process, null);

        loading = (TextView) root.findViewById(R.id.fragment_ptcustomers_process_tv);
        listView = (ListView) root.findViewById(R.id.fragment_ptcustomers_process_lv);

        customer = (PtCustomer) getArguments().get("data");
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (data != null) {
            data.clear();
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        loading.setText("数据加载..");
        loading.setVisibility(View.VISIBLE);
        initData();
    }

    //刷新数据
    public void initData() {
        HttpUtils utils = HttpUtilsHelper.getInstance();
        RequestParams requestParams = XutilsRequest.getPc(customer.getCustomerId());
        utils.send(HttpRequest.HttpMethod.POST, UrlData.PC_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String result = responseInfo.result;
                Log.d("sdasdasd", result);
                loading.setVisibility(View.GONE);
                parseJson(result);

            }

            @Override
            public void onFailure(HttpException e, String s) {
                loading.setText("加载失败");
            }
        });
    }


    private void parseJson(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String status = jsonObject.getString("status");
            if ("Success".equals(status)) {
                //返回成功
                String dataStr = jsonObject.getString("data");
                data = new ArrayList<>();
                JSONObject obj = new JSONObject(dataStr);
                JSONArray arr = obj.getJSONArray("CFList");
                if (arr.length() == 0) {
                    loading.setVisibility(View.VISIBLE);
                    loading.setText("没有数据");
                } else {
                    for (int i = 0; i < arr.length(); i++) {
                        data.add(parseCP(arr.getJSONObject(i)));
                    }
                    Log.d("datsasaa", data.toString());
                    adapter = new CustomersProcessAdapter(customer, data);

                    listView.setAdapter(adapter);
                }

            } else {
                //数据加载失败
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private CustomerProcess parseCP(JSONObject jsonObject) throws JSONException {
        CustomerProcess customerProcess = new CustomerProcess();
        customerProcess.setEmpName(jsonObject.getString("EmpName"));
        customerProcess.setIntentLevel(jsonObject.getString("IntentLevel"));
        customerProcess.setCallTime(jsonObject.getString("CallTime"));
        customerProcess.setNextCallTime(jsonObject.getString("NextCallTime"));
        customerProcess.setTalkProcess(jsonObject.getString("TalkProcess"));
        customerProcess.setQuoteSituation(jsonObject.getString("QuoteSituation"));
        Log.d("接触s","sasas"+ customerProcess.getEmpName());
        return customerProcess;
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.frgment_customersinfo_bt_callout://点击播出按钮
//                TelephonyManager manager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
//                if(manager.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK){
//                    Toast.makeText(getActivity(),"不能同时播出多个号码",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if(!PhoneValidate.validateNumber(customer.getCustomerPhone())){//电话又字符串则返回true
//                    Toast.makeText(activity,"电话号码有误,请修改后再回访",Toast.LENGTH_SHORT).show();
//                   /* Intent intent = new Intent();
//                    intent.putExtra("data", customer);
//                    intent.putExtra("path", "  ");
//                    intent.setClass(activity, ReturnVisitFail.class);
//                    ReturnVisitFail.ptCustomerActivity = activity;
//                    //intent.setClass(this,ReturnVisitOk.class);
//                    activity.startActivity(intent);*/
//                }else {
//
//                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
//                            ActivityCompat.checkSelfPermission(activity,Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
//
//                        Uri uri = Uri.parse("tel:" + customer.getCustomerPhone());
//                        Intent call = new Intent(Intent.ACTION_CALL, uri); //直接播出电话
//                        RecordFile recordFile = new RecordFile(activity, customer.getCustomerName() + customer.getCustomerPhone());
//                        PhonStateLisen.setParamsForPtCustomer(activity, customer,
//                                recordFile.getFilePath(), recordFile.getRecordFile());
//                        getActivity().startActivity(call);
//                    }else{
//                        Toast.makeText(activity, "该应用没有允许打电话权限或读取通话记录，请在设应用程序权限管理中将打开！", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//        }
//    }//end onClick
}
