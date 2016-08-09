package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;

public class LeavelInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_leavel_info);

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_leavlinfo_iv_back:
                finish();
                break;
        }
    }

}
