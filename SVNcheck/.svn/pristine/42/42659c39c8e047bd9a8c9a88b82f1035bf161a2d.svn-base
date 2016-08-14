package com.tygas.tianyu.tianyu.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/2/1.
 */
public class CluePagerAdapter extends FragmentPagerAdapter {


    ArrayList<Fragment> data;

    public CluePagerAdapter(FragmentManager fm,ArrayList<Fragment> data) {
        super(fm);
        this.data = data;
    }


    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "基本信息" : "联系历史" ;
    }
}
