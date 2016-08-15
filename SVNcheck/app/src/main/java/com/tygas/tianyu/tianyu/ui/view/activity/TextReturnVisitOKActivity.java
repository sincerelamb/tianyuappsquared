package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.PhonStateLisen;
import com.tygas.tianyu.tianyu.data.SharedPreferencesDate;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.JsonParser;
import com.tygas.tianyu.tianyu.utils.ProgressDialogHelper;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class TextReturnVisitOKActivity extends Activity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private TextView tv_shichang;
    private TextView tv_up;
    private ImageView play;

    private String path;
    private long duration;
    private MediaPlayer mediaPlayer;
    private String yesorno;


    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_return_visit_ok);
        initDate();
        initView();

    }



    private void initDate() {
        sharedPreferences = getSharedPreferences(SharedPreferencesDate.SHAREDPREFERENCES_NAME_FIRST_PREF, Context.MODE_PRIVATE);
        path = getIntent().getStringExtra("path");
        duration = getIntent().getLongExtra("duration", 0);
        //加载录音文件
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnPreparedListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void initView() {
        tv_up = (TextView) findViewById(R.id.ok_up);
        play = (ImageView) findViewById(R.id.ok_palay);

        tv_shichang = (TextView) findViewById(R.id.shichang);
        tv_shichang.setText(duration+"s");

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_ok_back:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                finish();
                break;
            case R.id.ok_palay:
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        play.setImageResource(R.mipmap.playstart);

                    } else {
                        mediaPlayer.start();
                        play.setImageResource(R.mipmap.playpause);
                    }
                }
                break;
            case R.id.ok_up:

                boolean aBoolean = sharedPreferences.getBoolean(SharedPreferencesDate.ISRECODERPROBLEM, false);
                  if (!aBoolean) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("当前手机是否录音存在问题？")
                        .setMessage(
                                "当前机型为：" + android.os.Build.MODEL + "  系统：" + android.os.Build.VERSION.RELEASE)
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                yesorno = "否";
                                upDatePhoneModel(android.os.Build.MODEL, android.os.Build.VERSION.RELEASE, Build.VERSION.SDK);
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                yesorno = "是";
                                upDatePhoneModel(android.os.Build.MODEL, android.os.Build.VERSION.RELEASE, Build.VERSION.SDK);
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                  } else {
                      Toast.makeText(this, "已反馈信息", Toast.LENGTH_SHORT).show();
                  }
                break;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        play.setImageResource(R.mipmap.playstart);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }


    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }


    public void upDatePhoneModel(String phoneModel, String phoneSys, String sdkNum) {
        ProgressDialogHelper.showProgressDialog(this, "正在反馈...");
        Log.d("PhinemodelIP", UrlData.PHONE_MODEL);
        HttpUtilsHelper.getInstance().send(HttpRequest.HttpMethod.POST, UrlData.PHONE_MODEL, XutilsRequest.getPhoneModel(phoneModel, phoneSys, sdkNum, yesorno), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.d("PhoneModel", responseInfo.result);
                ProgressDialogHelper.dismissProgressDialog();
                boolean b = JsonParser.savePhoneModelProblem(responseInfo.result);
                if (b) {
                    sharedPreferences.edit().putBoolean(SharedPreferencesDate.ISRECODERPROBLEM, true).commit();
                    Toast.makeText(TextReturnVisitOKActivity.this, "反馈成功", Toast.LENGTH_SHORT).show();
//                    src_list.remove(src_list.size() - 1);
//                    src_list.add(context.getResources().getDrawable(R.mipmap.yifankui));
//                    notifyDataSetChanged();
                } else {
                    Toast.makeText(TextReturnVisitOKActivity.this, "反馈失败", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(HttpException e, String s) {
                ProgressDialogHelper.dismissProgressDialog();
                Toast.makeText(TextReturnVisitOKActivity.this, "反馈失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (PhonStateLisen.isCall) {
                Toast.makeText(this, "通话过程中不可返回", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}
