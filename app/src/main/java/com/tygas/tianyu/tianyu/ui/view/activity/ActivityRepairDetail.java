package com.tygas.tianyu.tianyu.ui.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.adapter.RepairDetailFragmentPAgeAdapter;
import com.tygas.tianyu.tianyu.ui.view.fragment.PartsDetailFragment;
import com.tygas.tianyu.tianyu.ui.view.fragment.RepairProjectFragment;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/3/2.
 * 维修明细
 */
public class ActivityRepairDetail extends AppCompatActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {

    private ImageView backImageView;//返回按钮
    private TabLayout tabLayout;
    private ViewPager contentViewPager;

    private PartsDetailFragment partsDetailFragment;//配件明细
    private RepairProjectFragment repairProjectFragment;//维修项目明细
    private ArrayList<Fragment> fragments;

    private RepairDetailFragmentPAgeAdapter adapter;

    private static final String LOG_TAG = "ActivityRepairDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_repairdetail);
        initView();
    }

    //初始化控件
    private void initView() {
        backImageView = (ImageView) findViewById(R.id.activity_repairdetail_iv_back);
        /*tabLayout = (TabLayout) findViewById(R.id.activity_repairdetail_tl_title);
        contentViewPager = (ViewPager) findViewById(R.id.activity_repairdetail_vp_content);*/

        fragments = new ArrayList<>();
        partsDetailFragment = new PartsDetailFragment();
        repairProjectFragment = new RepairProjectFragment();
        fragments.add(partsDetailFragment);
        fragments.add(repairProjectFragment);
        adapter = new RepairDetailFragmentPAgeAdapter(getSupportFragmentManager(),fragments);

        contentViewPager.setAdapter(adapter);
        tabLayout.setOnTabSelectedListener(this);
        tabLayout.setupWithViewPager(contentViewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);

        backImageView.setOnClickListener(this);
    }//end initView

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_repairdetail_iv_back://点击了返回按钮
                break;
        }
    }//end onclick

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        contentViewPager.setCurrentItem(position);
    }
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}
    @Override
    public void onTabReselected(TabLayout.Tab tab) {}
}
