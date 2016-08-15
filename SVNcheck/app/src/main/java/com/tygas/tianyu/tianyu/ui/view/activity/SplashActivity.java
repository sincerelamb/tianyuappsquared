package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.data.SharedPreferencesDate;
import com.tygas.tianyu.tianyu.ui.model.PID;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;
import com.tygas.tianyu.tianyu.utils.DbUtilsHelper;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;

import java.io.File;

/**
 * @{# SplashActivity.java Create on 2013-5-2 下午9:10:01
 * <p/>
 * class desc: 启动画面 (1)判断是否是首次加载应用--采取读取SharedPreferences的方法
 * (2)是，则进入GuideActivity；否，则进入MainActivity (3)3s后执行(2)操作
 * <p/>
 * <p>
 * Copyright: Copyright(c) 2013
 * </p>
 * @Version 1.0
 * @Author <a href="mailto:gaolei_xj@163.com">Leo</a>
 */
public class SplashActivity extends Activity {
    boolean isFirstIn = false;

    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    // 延迟3秒
    private static final long SPLASH_DELAY_MILLIS = 3000;


    /**
     * Handler:跳转到不同界面
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SystemBarUtils.setSystemBarColor(this, "#00000000");
        init();
        HttpUtilsHelper.getInstance();
        HttpUtilsHelper.getInstanceUpData();
        DbUtils dbUtils = DbUtilsHelper.newInstance(this);

        try {
            User user = dbUtils.findFirst(User.class);
//            Log.d("user_second", user.toString());
            if (user != null) {
                user.setList_PID(dbUtils.findAll(PID.class));
                user.setList_CarSeries(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "CarSeries")));
                user.setList_Brand(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "OtherBrandList")));
                user.setList_IntentBrand(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "CarBrand")));
                user.setList_Channel(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "ChannelList")));
                user.setList_CheckColorList(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "CheckColorList")));
                user.setList_FailType(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "FailType")));
                user.setList_Focus(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "FocusCarmodelList")));
                user.setList_SourceChannel(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "SourceChannel")));
                user.setList_Useage(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "UseageList")));
                user.setList_CustomerLevel(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "CustomerLevel")));
                user.setList_CarModels(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "CarModels")));
                user.setList_OtherCarSeries(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "OtherSeries")));
                user.setList_UserNum(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "UserNum")));
                user.setList_LevelNotDefeat(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "LevelNotDefeat")));
                user.setList_LevelNotClinch(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "LevelNotClinch")));
                user.setList_LevelAll(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "LevelAll")));
                user.setList_SubscribeType(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "SubscribeType")));
                user.setList_LostResult(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "LostResult")));

                user.setList_WishBuyDate(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "WishBuyDate")));
                user.setList_PayType(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "PayType")));
                user.setList_TimeFrame(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "TimeFrame")));
                user.setList_AllEmpID(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "AllEmpID")));
                Log.d("user_second", user.getList_LevelAll().toString());
                ((MyAppCollection) getApplicationContext()).setUser(user);
            }


        } catch (DbException e) {

        }

    }

    private void init() {
        // 读取SharedPreferences中需要的数据
        // 使用SharedPreferences来记录程序的使用次数
        SharedPreferences preferences = getSharedPreferences(
                SharedPreferencesDate.SHAREDPREFERENCES_NAME_FIRST_PREF, MODE_PRIVATE);

        // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        isFirstIn = preferences.getBoolean(SharedPreferencesDate.ISFIRST, true);
        getPackageInfo();
        // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
        if (!isFirstIn) {
            // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
            mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
        }

    }

    private void goHome() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }

    private void goGuide() {
        Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }

    private int getPackageInfo() {
        //获取packagemanager的实例
        int currentVersionCode = 0;
        PackageManager manager = getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            String appVersionName = info.versionName; // 版本名
            currentVersionCode = info.versionCode; // 版本号
            System.out.println(currentVersionCode + " /" + appVersionName);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch blockd
            e.printStackTrace();
        }
        return currentVersionCode;

    }

    public static final String URL = "http://music.baidu.com/cms/BaiduMusic-danqu.apk";

    // 进行下载
    public void down() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        progressDialog.setMessage("准备下载...");
        progressDialog.show();

        String str = "/BBB.apk";
        String fileName = null;
        File file = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            fileName = Environment.getExternalStorageDirectory() + str;
            file = new File(fileName);//获取跟目录
        }

        final File finalFile = file;
        HttpUtilsHelper.httpUtils_updata.download(URL, fileName,
                true, true, new RequestCallBack<File>() {
                    @Override
                    public void onSuccess(ResponseInfo<File> arg0) {
                        // TODO Auto-generated method stub
                        progressDialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(finalFile), "application/vnd.android.package-archive");
                        startActivity(intent);
                        finish();
                    }


                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        progressDialog.setMessage("正在下载...");
                        int i = (int) (((double) current / (double) total) * 100d);
                        if (total > current) {
                            progressDialog.setProgress(i);
                        } else {
                            progressDialog.setMessage("下载完成");
                        }
                        super.onLoading(total, current, isUploading);
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        progressDialog.setMessage("准备下载...");
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        Log.d("onFailure", arg1 + arg0.getMessage());
                        // TODO Auto-generated method stub
                        progressDialog.dismiss();
                        progressDialog.setMessage("下载失败");
                        finish();
                    }
                });

    }

    private void getUrlPackageCode(final long time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    int urlcode = 0;
                    long l = System.currentTimeMillis() - time;
                    if (l < 3000) {

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                down();
                            }
                        });
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
