package com.tygas.tianyu.tianyu.ui.view.activity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tygas.tianyu.tianyu.R;

/**
 * Created by SJTY_YX on 2016/8/9.
 */
public class PushPhoneReActivity extends BaseActivity implements View.OnClickListener {
    private ImageView PushPhoneRe_back;
    private Button Upload;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ptcustomers);

        initView();

    }

    private void initView() {


        PushPhoneRe_back = (ImageView)findViewById(R.id.activity_PushPhoneRe_back);
        Upload = (Button)findViewById(R.id.activity_PushPhoneRe_back_upload);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_PushPhoneRe_back_upload://全部上传按钮



            break;
            case R.id.activity_PushPhoneRe_back://返回按钮
            finish();
            break;
        }
    }



}
