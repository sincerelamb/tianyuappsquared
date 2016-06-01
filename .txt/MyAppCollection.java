package com.tygas.tianyu.tianyu.context;

import android.app.Application;

import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;

/**
 * Created by Administrator on 2016/1/21.
 */
public class MyAppCollection extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtilsHelper.getInstance();
    }
}
