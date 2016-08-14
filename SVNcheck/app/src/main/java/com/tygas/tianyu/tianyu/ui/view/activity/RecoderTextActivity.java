package com.tygas.tianyu.tianyu.ui.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.PhonStateLisen;
import com.tygas.tianyu.tianyu.utils.PhoneValidate;
import com.tygas.tianyu.tianyu.utils.RecordFile;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;

public class RecoderTextActivity extends Activity {

    private ImageView iv_back;
    private TextView tv_jixin;
    private TextView tv_xitong;
    private ImageView iv_call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_recoder_text);
        initView();
    }


    private void initView() {
        iv_back = (ImageView) findViewById(R.id.back);
        tv_jixin = (TextView) findViewById(R.id.jixin);
        tv_jixin.setText(android.os.Build.MODEL);
        tv_xitong = (TextView) findViewById(R.id.xitong);
        tv_xitong.setText(android.os.Build.VERSION.RELEASE);
        iv_call = (ImageView) findViewById(R.id.call);


    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                if (PhonStateLisen.isCall) {
                    Toast.makeText(this, "通话过程中不可返回", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }

                break;
            case R.id.call:
                TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                if (manager.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK) {
                    Toast.makeText(this, "不能同时播出多个号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {

                    Uri uri = Uri.parse("tel:" + "10010");
                    Intent call = new Intent(Intent.ACTION_CALL, uri); //直接播出电话
                    RecordFile recordFile = new RecordFile(this, "中国移动" + "10010");
                    PhonStateLisen.setParamsForText(this,
                            recordFile.getFilePath(), recordFile.getRecordFile());
                    startActivity(call);
                } else {
                    Toast.makeText(this, "该应用没有允许打电话权限或读取通话记录，请在设应用程序权限管理中将打开！", Toast.LENGTH_SHORT).show();
                }


                break;

        }
    }


}
