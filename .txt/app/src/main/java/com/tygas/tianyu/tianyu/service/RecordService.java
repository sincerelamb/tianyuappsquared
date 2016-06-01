package com.tygas.tianyu.tianyu.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.context.PhonStateLisen;

/**
 * Created by SJTY_YX on 2016/1/25.
 *
 * 录音服务 启动录音监听
 *
 */
public class RecordService extends Service {


    private PhonStateLisen phonStateLisen;
    private TelephonyManager manager;
    private static final String LOG_TAG = "RecordService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phonStateLisen = ((MyAppCollection)getApplicationContext()).getPhonStateLisen();
        manager.listen(phonStateLisen, PhoneStateListener.LISTEN_CALL_STATE);
        Log.i(LOG_TAG,"启动录音服务 phonStateLisen "+phonStateLisen);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if(manager != null && phonStateLisen != null){
            manager.listen(null,PhoneStateListener.LISTEN_CALL_STATE);
        }
        PhonStateLisen.clear();
        Log.i(LOG_TAG,"结束录音服务");
    }//end onDestory
}
