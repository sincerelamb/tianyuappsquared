package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.data.Config;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.model.Clue;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.PhoneValidate;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;

/**
 *
 * 回访失败
 * Created by SJTY_YX on 2016/1/21.
 *
 *
 */
public class ClueReturnVisitFail extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private TextView duration;
    private ImageView saveImageView; //保存按钮
    private ImageView backImageView;//返回按钮

    private ImageView playIcon;
    private EditText remark;
    private LinearLayout time;
    private TextView nextTime;
    private int nowPositon;//当前的 选择位置
    private DatePickerDialog dpd;
    private ProgressDialog progressDialog;
    private String callBackString;// "稍后回访"  明日回访  约定回访


    private Clue clue;//当前回访的潜客
    private User user;//当前的登录用户

    private String recordPath;//录音文件的路径

//    public static Activity clueActivity;

    private static final String LOG_TAG = "ClueReturnVisitFail";


    private static final int CHOISE_COLOR = Color.rgb(161,225,156);//0xa1e19c; //等级默认的颜色
    private static final int DEFAULT_COLOR = Color.rgb(244,254,192);//0xf4fec0; //等级选中的颜色

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_returnvisitfail);
        clue = (Clue) getIntent().getSerializableExtra("data");
        MyAppCollection applicationContext = (MyAppCollection) getApplicationContext();
        user = applicationContext.getUser();
        if(clue == null || user == null){
            finish();
        }
       /*user = new User();
        user.setEmpId("1");*/
        recordPath = getIntent().getStringExtra("path");
        initView();
    }

    private void initView() {
        backImageView = (ImageView) findViewById(R.id.activity_returnvisitfail_iv_back);
        backImageView.setOnClickListener(this);

        saveImageView = (ImageView) findViewById(R.id.activity_returnvisitfail_tv_save);
        saveImageView.setOnClickListener(this);

        duration = (TextView) findViewById(R.id.activity_returnvisitfail_tv_duration);
        playIcon = (ImageView) findViewById(R.id.activity_returnvisitfail_iv_playIcon);
        playIcon.setOnClickListener(this);
        remark = (EditText) findViewById(R.id.activity_returnvisitfail_et_remark);
        time = (LinearLayout) findViewById(R.id.activity_returnvisitfail_ll_time);
        for(int i=0;i<time.getChildCount();i++){
            time.getChildAt(i).setOnClickListener(this);
        }
        nextTime = (TextView) findViewById(R.id.activity_returnvisitfail_tv_nextTime);
        setColor();
        nextTime.setText(getNowTime());

        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                ClueReturnVisitFail.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(Color.rgb(50, 126, 202));//327ECA

        callBackString = "稍后回访";//默认的是稍后回访

    }

    private void showDialog(String mes){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.setMessage(mes);
        progressDialog.show();
    }

    private void dismissDialog(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
//        Toast.makeText(this,"请提交数据",Toast.LENGTH_SHORT).show();
        showBackDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_returnvisitfail_tv_later://稍后回访
                nowPositon = 0;
                setColor();
                nextTime.setText(getNowTime());
                callBackString = "稍后回访";
                break;
            case R.id.activity_returnvisitfail_tv_tom://明日
                nowPositon = 1;
                setColor();
                nextTime.setText(getNextTime());
                callBackString = "明日回访";
                break;
            case R.id.activity_returnvisitfail_tv_self://约定
                if(!dpd.isAdded()){
                    nowPositon = 2;
                    setColor();
                    dpd.show(getFragmentManager(), "nexttime");
                    callBackString = "约定回访";
                }

                break;
            case R.id.activity_returnvisitfail_tv_save:
                uploadDataToNet();
                break;
            case R.id.activity_returnvisitfail_iv_playIcon://播放

                break;
            case R.id.activity_returnvisitfail_iv_back://返回按钮
                showBackDialog();
                break;
        }
    }//end onClick

    private void showBackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("还没有提交数据是否返回")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void uploadDataToNet() {
        showDialog("提交中..");
        if(recordPath != null ){
            File file = new File(recordPath);
            if(file.exists()){
                file.delete();//删除录音文件
                if(Config.isDebug)
                Log.i(LOG_TAG,"[删除录音文件]"+file.getAbsolutePath());
            }
        }
        String bz  = remark.getText().toString();
        String callbackStr = callBackString;
        String time = nextTime.getText().toString();
        if(Config.isDebug)
        Log.i(LOG_TAG,"bz "+bz+"  ca"+callBackString+"   time"+time);

        //获得回访的请求参数

        /**
         String clueID, String empID, String talkProcess, String intentLevel,
         String isSuccess, String failBackType, String backDateTime, String failType,
         String otherBrandName, String otherSeriesName, String isSubscribe, String SubscribeDate,
         String Remark
         *
         */
        RequestParams requestParams = XutilsRequest.getCvisit(clue.getClueId(), user.getEmpId(), bz, clue.getIntentLevel(),
                                                                "false", callBackString, time, "",
                                                                "", "", "false", "2015-06-06", "");

        HttpUtils utils = HttpUtilsHelper.getInstance();
        utils.send(HttpRequest.HttpMethod.POST, UrlData.CVISIT_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if(Config.isDebug)
                Log.i(LOG_TAG, "[提交后返回的数据]" + responseInfo.result);
                parseResult(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                dismissDialog();
                Toast.makeText(ClueReturnVisitFail.this,"数据提交失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //解析网络请求的返回结果
    private void parseResult(String result) {
        //{"status":"Success","message":"","data":"{\"Status\":\"Success\",\"Message\":\"回访保存成功!\",\"CallFollowID\":16642}"}
        //{"status":"Success","message":"","data":"{\"Status\":\"Fail\",\"Message\":\"请选择线索等级！\",\"CallFirstID\":0}"}
        dismissDialog();
        try {
            JSONObject jsonObject = new JSONObject(result);
            String status = jsonObject.getString("status");
            if("Success".equals(status)){
                //提交成功
                String dataStr = jsonObject.getString("data");
                if(dataStr !=null && dataStr.length()>0){
                    JSONObject data = new JSONObject(dataStr);
                    String st = data.getString("Status");
                    if("Success".equals(st)){
                        Toast.makeText(this,"数据保存成功",Toast.LENGTH_SHORT).show();
                        finish();
                        HttpUtilsHelper.downLoadUpdataUI(ClueReturnVisitFail.this, false, 0, false, false, false);
//                        if(clueActivity instanceof ClueActivity){
//                            ClueActivity temp = (ClueActivity) clueActivity;
//                            temp.pageIndex = 1;
//                            temp.initData();
//                            clueActivity = null;
//                        }
                        //clueActivity.pageIndex = 1;
                        //clueActivity.initDataByFilter();
                        Intent i = new Intent("ty.refreshlist.clue");
                        sendBroadcast(i);
                    }else{
                        Toast.makeText(this,data.getString("Message"),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,"提交失败",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }else{
                Toast.makeText(this,"提交失败"+jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"数据解析错误",Toast.LENGTH_SHORT).show();
        }
    }

    private void setColor(){
        for(int i=0;i<time.getChildCount();i++){
            if(i == nowPositon){
                time.getChildAt(i).setBackgroundColor(CHOISE_COLOR);
            }else{
                time.getChildAt(i).setBackgroundColor(DEFAULT_COLOR);
            }
        }
    }

    private String getNowTime(){
        Calendar cal=Calendar.getInstance();
        int y =cal.get(Calendar.YEAR);
        int m =cal.get(Calendar.MONTH)+1;
        int d =cal.get(Calendar.DATE);
        return y+"-"+m+"-"+d;
    }

    private String getNextTime(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, +1);
        int y =cal.get(Calendar.YEAR);
        int m =cal.get(Calendar.MONTH)+1;
        int d =cal.get(Calendar.DATE);
        return y+"-"+m+"-"+d;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String other = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
        String nowstr = getNowTime();
        if(PhoneValidate.compareData(other, nowstr)){
            nextTime.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
        }else{
            Toast.makeText(this,"下次回访日期不能小于今天",Toast.LENGTH_SHORT).show();
        }
        //nextTime.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
    }
}
