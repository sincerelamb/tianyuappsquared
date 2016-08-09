package com.tygas.tianyu.tianyu.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.data.SharedPreferencesDate;
import com.tygas.tianyu.tianyu.ui.view.activity.LoginActivity;

import java.util.List;


public class GuideViewPagerAdapter extends PagerAdapter {


    private List<View> views;
    private Activity activity;


    public GuideViewPagerAdapter(List<View> views, Activity activity) {
        this.views = views;
        this.activity = activity;
    }


    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(views.get(arg1));
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(views.get(arg1), 0);
        if (arg1 == views.size() - 1) {
            ImageView tv_start = (ImageView) arg0
                    .findViewById(R.id.tv_start);
            tv_start.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    setGuided();
                    goHome();

                }

            });
        }
        return views.get(arg1);
    }

    private void goHome() {
        // 跳转
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * method desc：设置已经引导过了，下次启动不用再次引导
     */
    private void setGuided() {
        SharedPreferences preferences = activity.getSharedPreferences(
                SharedPreferencesDate.SHAREDPREFERENCES_NAME_FIRST_PREF, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean(SharedPreferencesDate.ISFIRST, false);
        editor.commit();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

}
