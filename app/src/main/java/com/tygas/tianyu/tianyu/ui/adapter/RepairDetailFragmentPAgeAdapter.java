package com.tygas.tianyu.tianyu.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/3/2.
 */
public class RepairDetailFragmentPAgeAdapter extends FragmentPagerAdapter {


    ArrayList<Fragment> fragments;

    public RepairDetailFragmentPAgeAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0?"维修项目明细":"配件明细";
    }

}
