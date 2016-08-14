package com.tygas.tianyu.tianyu.ui.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.adapter.FpHistoryAdapter;
import com.tygas.tianyu.tianyu.ui.model.MaintenanceHistory;
import com.tygas.tianyu.tianyu.ui.model.TimingProtect;
import com.tygas.tianyu.tianyu.ui.view.activity.FirstProjectRepairDetailActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.TimingProjectRepairDetailActivity;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.JsonParser;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TP_History_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TP_History_Fragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private TimingProtect mParam1;
    private ListView lv;
    private TextView tv;
    private String FrameNum;


    private ArrayList<MaintenanceHistory> maintenanceHistories;
    private FpHistoryAdapter fpHistoryAdapter;


    public TP_History_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment TP_History_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TP_History_Fragment newInstance(TimingProtect param1) {
        TP_History_Fragment fragment = new TP_History_Fragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (TimingProtect) (getArguments().getSerializable(ARG_PARAM1));
            FrameNum = mParam1.getFrameNum();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_tp__history_, container, false);
        lv = (ListView) inflate.findViewById(R.id.fragment_tphistory_lv);
        tv = (TextView) inflate.findViewById(R.id.fragment_tphistory_tv);
        tv.setText("正在加载...");
        maintenanceHistories = new ArrayList<>();
        fpHistoryAdapter = new FpHistoryAdapter(getActivity(), maintenanceHistories);
        lv.setAdapter(fpHistoryAdapter);

        lv.setOnItemClickListener(this);
        return inflate;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        downloadMaintenanceHistoryData();
    }

    private void downloadMaintenanceHistoryData() {
        HttpUtils httpUtils = HttpUtilsHelper.getInstance();
        httpUtils.send(HttpRequest.HttpMethod.POST, UrlData.FIRST_PROTECT_HISTORY_URL, XutilsRequest.getFpCustomerHistory(FrameNum), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.d("MaintenanceHistory", responseInfo.result);
                maintenanceHistories.addAll(JsonParser.fpCustomersHistoryMachineParser(responseInfo.result));
                if (maintenanceHistories.size() == 0) {
                    tv.setText("没有数据");
                } else {
                    tv.setText("");
                    tv.setVisibility(View.GONE);
                }
                fpHistoryAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(HttpException e, String s) {
                tv.setText("加载失败");
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), TimingProjectRepairDetailActivity.class);
        intent.putExtra("data", maintenanceHistories.get(position));
        startActivity(intent);
    }
}
