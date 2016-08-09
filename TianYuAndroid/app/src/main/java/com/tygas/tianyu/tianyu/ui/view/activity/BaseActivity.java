package com.tygas.tianyu.tianyu.ui.view.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by SJTY_YX on 2016/2/25.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }

}
