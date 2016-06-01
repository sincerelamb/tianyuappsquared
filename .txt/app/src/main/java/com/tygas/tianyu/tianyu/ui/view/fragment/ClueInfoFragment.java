package com.tygas.tianyu.tianyu.ui.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.model.Clue;
import com.tygas.tianyu.tianyu.ui.view.activity.ClueDetailActivity;

/**
 * Created by SJTY_YX on 2016/1/28.
 */
public class ClueInfoFragment extends Fragment implements View.OnClickListener {


    private View root;

    private TextView nameTextView;//线索姓名
    private TextView phoneTextView;//线索电话
    private TextView empTextView;//电销员
    private TextView liveProvinceTextView;//常住省
    private TextView liveCityTextView;//常住市
    private TextView comeFromTextView;//来店渠道
    private TextView infoFromTextView;//信息渠道
    private TextView loveCarTextView;//意向车系
    private TextView loveCarModelTextView;//意向车型
    private TextView person;//转介绍人

    private Button callOutButton;//播出按钮

    private Clue clue;//线索

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_clueinfo, container, false);
        nameTextView = (TextView) root.findViewById(R.id.fragment_clueinfo_tv_name);
        phoneTextView = (TextView) root.findViewById(R.id.fragment_clueinfo_tv_phone);
        empTextView = (TextView) root.findViewById(R.id.fragment_clueinfo_tv_emp);
        liveProvinceTextView = (TextView) root.findViewById(R.id.fragment_clueinfo_tv_liveProvince);
        liveCityTextView = (TextView) root.findViewById(R.id.fragment_clueinfo_tv_liveCity);
        comeFromTextView = (TextView) root.findViewById(R.id.fragment_clueinfo_tv_comefrom);
        infoFromTextView = (TextView) root.findViewById(R.id.fragment_clueinfo_tv_infofrom);
        loveCarTextView = (TextView) root.findViewById(R.id.fragment_clueinfo_tv_lovecar);
        loveCarModelTextView = (TextView) root.findViewById(R.id.fragment_clueinfo_tv_lovecarmodel);

        person = (TextView) root.findViewById(R.id.fragment_clueinfo_tv_person);
        
        callOutButton = (Button) root.findViewById(R.id.fragment_clueinfo_btn_callout);
        callOutButton.setOnClickListener(this);
        Bundle bundle = getArguments();
        clue = (Clue) bundle.getSerializable("data");
        initData();
        return root;
    }//end onCreateView

    private void initData() {
        if(clue != null){
            nameTextView.setText(clue.getClueName());
            phoneTextView.setText(clue.getCluePhone());
            empTextView.setText(clue.getEmpName());
            liveProvinceTextView.setText(clue.getLiveProvince());
            liveCityTextView.setText(clue.getLiveCity());
            comeFromTextView.setText(clue.getChannelName());
            infoFromTextView.setText(clue.getSourceChannelName());
            loveCarTextView.setText(clue.getCarseriesName());
            loveCarModelTextView.setText(clue.getCarModelName());
            person.setText(clue.getIntroducer());
        }
    }//end initdata

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_clueinfo_btn_callout://点击了跟踪播出
                TelephonyManager manager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                if(manager.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK){
                    Toast.makeText(getActivity(),"不能同时播出多个号码",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                    ClueDetailActivity clueDetailActivity = (ClueDetailActivity) getActivity();
                    clueDetailActivity.callPhone();
                }else{
                    Toast.makeText(getActivity(), "该应用没有允许打电话权限或读取通话记录，请在设应用程序权限管理中将打开！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }//end onClick
}
