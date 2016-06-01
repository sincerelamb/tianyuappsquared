package com.tygas.tianyu.tianyu.ui.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.PhonStateLisen;
import com.tygas.tianyu.tianyu.ui.adapter.PtCustomersFragmentPageAdapter;
import com.tygas.tianyu.tianyu.ui.model.PtCustomer;
import com.tygas.tianyu.tianyu.ui.view.fragment.CustomersInfoFragment;
import com.tygas.tianyu.tianyu.ui.view.fragment.CustomersProcessFragment;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/1/20.
 * 线索的详细信息页面
 */
public class PtCustomersDetailActivity extends BaseActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {

    private PtCustomer customer;

    private ImageView returnImageView;//返回按钮
    private ViewPager viewPager;
    private TabLayout tabLayout;//联动的菜单

    private PtCustomersFragmentPageAdapter adapter;
    private CustomersInfoFragment customersInfoFragment;
    private CustomersProcessFragment processFragment;
    ArrayList<Fragment> fragments;

    private static final String LOG_TAG = "PtCustomersDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_ptcustomersdetail);
        if (getIntent().getSerializableExtra("data") == null) {
            finish();
        } else {
            customer = (PtCustomer) getIntent().getSerializableExtra("data");
        }
        initView();

    }

    @Override
    public void onBackPressed() {
        if(PhonStateLisen.isCall){
            Toast.makeText(this,"通话过程中不能返回",Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }

    private void initView() {
        returnImageView = (ImageView) findViewById(R.id.activity_ptcustomersdetail_tv_return);
        returnImageView.setOnClickListener(this);
        tabLayout = (TabLayout) findViewById(R.id.activity_ptcustomersdetail_tl);
        viewPager = (ViewPager) findViewById(R.id.activity_ptcustomersdetail_vp);

        customersInfoFragment = new CustomersInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", customer);
        customersInfoFragment.setArguments(bundle);

        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("data", customer);
        processFragment = new CustomersProcessFragment();
        processFragment.setArguments(bundle1);
        fragments = new ArrayList<Fragment>();

        fragments.add(customersInfoFragment);
        fragments.add(processFragment);
        adapter = new PtCustomersFragmentPageAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        //viewPager.addOnPageChangeListener(this);
        tabLayout.setOnTabSelectedListener(this);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);

    }//end initView



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_ptcustomersdetail_tv_return://点击了返回按钮
                if(PhonStateLisen.isCall){
                    Toast.makeText(this,"通话过程中不能返回",Toast.LENGTH_SHORT).show();
                    return;
                }
                finish();
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
