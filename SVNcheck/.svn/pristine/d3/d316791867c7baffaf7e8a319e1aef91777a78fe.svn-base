package com.tygas.tianyu.tianyu.ui.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.PhonStateLisen;
import com.tygas.tianyu.tianyu.ui.adapter.PtCustomersFragmentPageAdapter;
import com.tygas.tianyu.tianyu.ui.adapter.TPCustomerFragmentAdapter;
import com.tygas.tianyu.tianyu.ui.model.FirstProtect;
import com.tygas.tianyu.tianyu.ui.model.TimingProtect;
import com.tygas.tianyu.tianyu.ui.view.fragment.FP_CustomerInfo_Fragment;
import com.tygas.tianyu.tianyu.ui.view.fragment.FP_History_Fragment;
import com.tygas.tianyu.tianyu.ui.view.fragment.TP_CustomerInfo_Fragment;
import com.tygas.tianyu.tianyu.ui.view.fragment.TP_History_Fragment;
import com.tygas.tianyu.tianyu.utils.PhoneValidate;
import com.tygas.tianyu.tianyu.utils.RecordFile;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;

import java.util.ArrayList;

public class TpInfoActivity extends FragmentActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {
    private ImageView returnImageView;//返回按钮
    private ViewPager viewPager;
    private TabLayout tabLayout;//联动的菜单

    private TPCustomerFragmentAdapter adapter;
    private TP_CustomerInfo_Fragment customersInfoFragment;
    private TP_History_Fragment historyFragment;
    ArrayList<Fragment> fragments;

    private TimingProtect tp_info_data;
    private Activity timeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_tp_info);
        initData();
        initView();
    }


    private void initData() {
        if (getIntent().getSerializableExtra("tp_info_data") != null) {
            tp_info_data = (TimingProtect) (getIntent().getSerializableExtra("tp_info_data"));
        }
    }

    private void initView() {
        returnImageView = (ImageView) findViewById(R.id.activity_tpcustominfo_iv_back);
        returnImageView.setOnClickListener(this);
        tabLayout = (TabLayout) findViewById(R.id.activity_tpcustominfo_tl);
        viewPager = (ViewPager) findViewById(R.id.activity_tpcustominfo_vp);

        customersInfoFragment = TP_CustomerInfo_Fragment.newInstance(tp_info_data);
        historyFragment = TP_History_Fragment.newInstance(tp_info_data);

        fragments = new ArrayList<Fragment>();
        fragments.add(customersInfoFragment);
        fragments.add(historyFragment);

        adapter = new TPCustomerFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        //viewPager.addOnPageChangeListener(this);

        tabLayout.setOnTabSelectedListener(this);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);

    }//end initView


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_tpcustominfo_iv_back:
                if (PhonStateLisen.isCall) {
                    Toast.makeText(this, "通话过程中不能返回", Toast.LENGTH_SHORT).show();
                    return;
                }
                finish();
                break;
            case R.id.activity_tpcustomers_bt_callout:
                TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                if (manager.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK) {
                    Toast.makeText(this, "不能同时播出多个号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!PhoneValidate.validateNumber(tp_info_data.getCustomerPhone())) {//电话又字符串则返回true
                    Toast.makeText(this, "电话号码有误", Toast.LENGTH_SHORT).show();

                } else {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {

                        Uri uri = Uri.parse("tel:" + tp_info_data.getCustomerPhone());
                        Intent call = new Intent(Intent.ACTION_CALL, uri); //直接播出电话
                        RecordFile recordFile = new RecordFile(this, tp_info_data.getCustomerName() + tp_info_data.getCustomerPhone());
                        PhonStateLisen.setParamsForTpCustomer(this, tp_info_data,
                                recordFile.getFilePath(), recordFile.getRecordFile());
                        startActivity(call);
                    } else {
                        Toast.makeText(this, "该应用没有允许打电话权限或读取通话记录，请在设应用程序权限管理中将打开！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
