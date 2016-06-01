package com.tygas.tianyu.tianyu.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/1/20.
 */
public class PtCustomersFragmentPageAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments;

    public PtCustomersFragmentPageAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
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
        return position == 0?"客户信息":"跟进过程";
    }
}
