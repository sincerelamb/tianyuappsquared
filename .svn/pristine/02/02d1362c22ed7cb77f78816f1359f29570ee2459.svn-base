package com.tygas.tianyu.tianyu.ui.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.PhonStateLisen;
import com.tygas.tianyu.tianyu.ui.adapter.CluePagerAdapter;
import com.tygas.tianyu.tianyu.ui.model.Clue;
import com.tygas.tianyu.tianyu.ui.view.fragment.ClueInfoFragment;
import com.tygas.tianyu.tianyu.ui.view.fragment.ClueProgressFragment;
import com.tygas.tianyu.tianyu.utils.PhoneValidate;
import com.tygas.tianyu.tianyu.utils.RecordFile;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/1/28.
 */
public class ClueDetailActivity extends BaseActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {


    private Clue clue;

    private ImageView backImageView;//返回按钮
    private ViewPager viewPager;
    private TabLayout tabLayout;//联动菜单

    private ClueInfoFragment clueInfoFragment;//线索详细的fragment
    private ClueProgressFragment clueProgressFragment;//线索跟进过程的fragemnt

    private CluePagerAdapter adapter;
    private ArrayList<Fragment> data;


    private static final String LOG_TAG = "ClueDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_cluedetail);
        clue = (Clue) getIntent().getSerializableExtra("data");
        initView();
    }//end oncreate


    private void initView() {
        backImageView = (ImageView) findViewById(R.id.activity_cluedetail_iv_back);
        backImageView.setOnClickListener(this);
        viewPager = (ViewPager) findViewById(R.id.activity_cluedetail_vp_content);
        tabLayout = (TabLayout) findViewById(R.id.activity_cluedetail_tl_tt);

        clueInfoFragment = new ClueInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",clue);
        clueInfoFragment.setArguments(bundle);
        clueProgressFragment = new ClueProgressFragment();
        clueProgressFragment.setArguments(bundle);
        data = new ArrayList<>();
        data.add(clueInfoFragment);
        data.add(clueProgressFragment);
        adapter = new CluePagerAdapter(getSupportFragmentManager(),data);
        viewPager.setAdapter(adapter);

        tabLayout.setOnTabSelectedListener(this);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);

    }

    public void callPhone(){


        if(!PhoneValidate.validateNumber(clue.getCluePhone())){
            Toast.makeText(this,"电话号码错误",Toast.LENGTH_SHORT).show();
            /*Intent intent = new Intent();
            intent.putExtra("data", clue);
            intent.putExtra("path", "  ");
            intent.setClass(this, ClueReturnVisitFail.class);

            ClueReturnVisitFail.clueActivity = this;
            startActivity(intent);*/

        }else {

            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + clue.getCluePhone());
            intent.setData(data);
            RecordFile recordFile = new RecordFile(this, "clue" + clue.getClueName() + clue.getCluePhone());
            PhonStateLisen.setParamsForClue(this, clue,
                    recordFile.getFilePath(), recordFile.getRecordFile());
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if(PhonStateLisen.isCall){
            Toast.makeText(this,"通话过程中不能返回",Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_cluedetail_iv_back://点击返回
                if(PhonStateLisen.isCall){
                    Toast.makeText(this,"通话过程中不能返回",Toast.LENGTH_SHORT).show();
                    return;
                }
                finish();
                break;
        }
    }//end onClick

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}
}
