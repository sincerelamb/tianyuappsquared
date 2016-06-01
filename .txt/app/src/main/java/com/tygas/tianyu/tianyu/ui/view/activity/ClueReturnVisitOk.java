package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.cheapmp3.CheapSoundFile;
import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.data.Config;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.adapter.LevelAdapterFromOk;
import com.tygas.tianyu.tianyu.ui.model.Clue;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;
import com.tygas.tianyu.tianyu.ui.view.customview.FocuGirdView;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.MyDialogHelper;
import com.tygas.tianyu.tianyu.utils.PhoneValidate;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by SJTY_YX on 2016/1/21.
 */
public class ClueReturnVisitOk extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, MyDialogHelper.DialogCallBack, DatePickerDialog.OnDateSetListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private ImageView backImageView;//返回按钮
    private ImageView saveImageView;//保存按钮
    private TextView durationTextView;//通话时长
    private ImageView playIcon;//录音播放
    private EditText remark;//洽谈内容
    private RelativeLayout level;//客户等级
    private String levelStr;
    private LinearLayout defeatRERelativeLayout;
    private RelativeLayout defeatRERelativeLayout_;
    private int defeatPositoin;
    private LinearLayout linearLayout;//流转
    private GridView gridView;
    private List<UserPtInfoModel> data;//战败的类型
    private DefeatTypeAdapter adapter;

    private TextView fileDurationTextView;//文件的长度

    private CheckBox checkBox;//成功邀约的checkbox

    private RelativeLayout pplz;
    private RelativeLayout lzcx;
    private TextView pplzTextView;
    private TextView lzcxTextView;
    private ListView dialogListView;
    private ArrayList<String> dialogListViewData;
    private int pplzPosition;

    private RelativeLayout comeTimeLayout;
    private DatePickerDialog dpd;
    private TextView comeTimeText;

    private RelativeLayout visitLayout;//成功邀约的布局

    private User user;//基础数据
    private String failTypeStr;//用户选择的战败类型

    private ProgressDialog progressDialog;

    private String recordPath;//录音文件的路径
    private long duration;//通话时长
    private MediaPlayer mediaPlayer;
    private boolean isPlaying;

    private FocuGirdView levelFocuGirdView;
    private List<UserPtInfoModel> levelData;
    private LevelAdapterFromOk levelAdapter;

    //public ClueActivity clueActivity;
    public static Activity clueActivity;
    private Clue clue;
    private boolean isCeapRecord = false;
    private File outputFile;

    private static final int CHOISE_COLOR = Color.rgb(161, 225, 156);//0xa1e19c; //等级默认的颜色
    private static final int DEFAULT_COLOR = Color.rgb(244,254,192);//0xf4fec0; //等级选中的颜色
    private static final String LOG_TAG = "ClueReturnVisitOk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_returnvisitok);
        user = ((MyAppCollection)getApplicationContext()).getUser();
        clue = (Clue) getIntent().getSerializableExtra("data");
        if(user == null || clue == null){
            finish();
        }
        recordPath = getIntent().getStringExtra("path");
        duration = getIntent().getLongExtra("duration", 0);
        //mediaPlayer = MediaPlayer.create(this, Uri.fromFile(new File(recordPath)));
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(recordPath);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnPreparedListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initView();
        //加载MP3文件
        if(Config.isDebug)
        Log.i(LOG_TAG, "[文件路径]" + recordPath);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mediaPlayer == null){
            Toast.makeText(this,"录音文件损坏",Toast.LENGTH_SHORT).show();
        }else{
            if(isCeapRecord){//是否截取录音
                //截取录音文件
                int length = mediaPlayer.getDuration()/1000;
                // Log.i(LOG_TAG, "[录音文件的长度] " + length);
                if(Config.isDebug){
                    Log.i(LOG_TAG,"[录音的文件]  length "+length+"   duration "+duration);
                }
                if(length >= duration){
                    outputFile = cheapMap3((int) (length - duration), length);
                    mediaPlayer = MediaPlayer.create(this,Uri.fromFile(outputFile));
                    if(Config.isDebug){
                        Log.i(LOG_TAG,"[截取后的录音长度]"+mediaPlayer.getDuration());
                    }
                }
            }else{
                outputFile = new File(recordPath);
            }
        }
    }

    private void showDialog(String msg){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.setMessage(msg);
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    private void dismissDialog(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    private void initView() {
        backImageView = (ImageView) findViewById(R.id.activity_returnvisitok_iv_back);
        backImageView.setOnClickListener(this);
        durationTextView = (TextView) findViewById(R.id.activity_returnvisitok_tv_duration);
        durationTextView.setText(String.valueOf(duration));

        playIcon = (ImageView) findViewById(R.id.activity_returnvisitok_iv_playIcon);
        if(mediaPlayer == null){
            playIcon.setImageResource(R.mipmap.bofangjingyong);
        }else{
            playIcon.setOnClickListener(this);
        }

        remark = (EditText) findViewById(R.id.activity_returnvisitok_et_remark);
        //level = (RelativeLayout) findViewById(R.id.activity_returnvisitok_rl_level);

        levelFocuGirdView = (FocuGirdView) findViewById(R.id.activity_returnvisitok_fgv_level);
        levelData = user.getList_CustomerLevel();
        if(clue != null){
            levelStr = clue.getIntentLevel();
        }else{
            levelStr = "";
        }

        levelAdapter = new LevelAdapterFromOk(this,levelData,levelStr);
        levelFocuGirdView.setAdapter(levelAdapter);
        //levelStr = levelData.get(0).getName();

        saveImageView = (ImageView) findViewById(R.id.activity_returnvisitok_tv_save);
        saveImageView.setOnClickListener(this);

        defeatRERelativeLayout = (LinearLayout) findViewById(R.id.activtiy_returnvisitok_ll_defeat);

        gridView = (GridView) findViewById(R.id.activtiy_returnvisitok_gridview);
        data = user.getList_FailType();

        adapter = new DefeatTypeAdapter(data);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        defeatRERelativeLayout.setVisibility(View.GONE);
        linearLayout = (LinearLayout) findViewById(R.id.activity_returnvisitok_ll_liuzhuan);
        linearLayout.setVisibility(View.GONE);

        checkBox = (CheckBox) findViewById(R.id.activtiy_returnvisitok_cb_visit);

        pplz = (RelativeLayout) findViewById(R.id.activtiy_returnvisitok_rl_pplz);
        pplz.setOnClickListener(this);
        lzcx = (RelativeLayout) findViewById(R.id.activtiy_returnvisitok_rl_lzcx);
        lzcx.setOnClickListener(this);

        pplzTextView = (TextView) findViewById(R.id.activtiy_returnvisitok_tv_pplz);
        lzcxTextView = (TextView) findViewById(R.id.activtiy_returnvisitok_tv_lzcx);

        comeTimeLayout = (RelativeLayout) findViewById(R.id.activtiy_returnvisitok_rl_cometime);
        comeTimeLayout.setOnClickListener(this);
        comeTimeText = (TextView) findViewById(R.id.activtiy_returnvisitok_tv_cometime);
        visitLayout = (RelativeLayout) findViewById(R.id.activtiy_returnvisitok_rl_visit);

        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, +1);
        String nowStr  = now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH);
        comeTimeText.setText(nowStr);

        dpd = DatePickerDialog.newInstance(
                ClueReturnVisitOk.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(Color.rgb(50, 126, 202));//327ECA

        fileDurationTextView = (TextView) findViewById(R.id.activity_returnvisitok_tv_fileduration);

    }//end initView


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(this,"请提交数据",Toast.LENGTH_SHORT).show();
    }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_returnvisitok_iv_playIcon://点击录音播放
                if(mediaPlayer != null){
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        playIcon.setImageResource(R.mipmap.playstart);
                    }else{
                        mediaPlayer.start();
                        playIcon.setImageResource(R.mipmap.playpause);
                    }
                }
                break;
            case R.id.activity_returnvisitok_tv_save://点击保存按钮
                if("".equals(levelStr)){
                    Toast.makeText(this,"等级不能为空",Toast.LENGTH_SHORT).show();
                }else if(levelStr.equals("F") && (failTypeStr == null || "".equals(failTypeStr))){
                    Toast.makeText(this,"战败类型不能为空",Toast.LENGTH_SHORT).show();
                }else if(levelStr.equals("F") && (failTypeStr.equals("竞品战败") && pplzTextView.getText().equals(""))){
                    Toast.makeText(this,"流转品牌不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    uploadDataToNet();
                }
                break;
            case R.id.activity_returnvisitok_iv_back://点击了返回按钮
                showBackDialog();
                break;
            case R.id.activtiy_returnvisitok_rl_pplz:
                //初始化 listview和adapter
                new MyDialogHelper().showListDialog(this, "请选择", getAllBrand(), pplzTextView, this);
                break;
            case R.id.activtiy_returnvisitok_rl_lzcx:
                new MyDialogHelper().showListDialog(this,"请选择",getAllCarSeriesByBrandName(pplzTextView.getText().toString()),lzcxTextView);
                break;
            case R.id.activtiy_returnvisitok_rl_cometime:
                //弹出日期选择
                dpd.show(getFragmentManager(),"time");
                break;
            default: //客户等级
                if(((View)v.getParent().getParent()).getId() == R.id.activity_returnvisitok_fgv_level){
                    levelStr = (String) v.getTag();
                    levelAdapter.setPosition(v);
                    if("F".equals(levelStr)){//战败
                        defeatRERelativeLayout.setVisibility(View.VISIBLE);
                        //setBrand(0);
                        //setCarSeries(0);
                        pplzTextView.setText("");
                        lzcxTextView.setText("");
                        //不可选邀约时间
                        /*defeatRERelativeLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                //setDefeatColor(1);
                                //failTypeStr = data.get(1).getName();
                            }
                        });*/
                        failTypeStr = "";
                        setDefeatColor(-1);
                        Log.i(LOG_TAG,"[设置战败类型]");
                        comeTimeLayout.setVisibility(View.GONE);
                        visitLayout.setVisibility(View.GONE);
                    }else{
                        defeatRERelativeLayout.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.GONE);
                        comeTimeLayout.setVisibility(View.VISIBLE);
                        visitLayout.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }//end onClick

    //保存数据到服务器
    private void uploadDataToNet() {
        //首先上传基本数据
        if(Config.isDebug)
        Log.i(LOG_TAG,"[回访成功上传基本数据]");
        RequestParams requestParams = null;
        if("F".equals(levelStr)){//战败
            /**
             *
             String clueID, String empID, String talkProcess, String intentLevel,
             String isSuccess, String failBackType, String backDateTime, String failType,
             String otherBrandName, String otherSeriesName, String isSubscribe, String SubscribeDate,
             String Remark
             *
             *
             */
            if("竞品战败".equals(failTypeStr)){
                requestParams = XutilsRequest.getCvisit(clue.getClueId(), user.getEmpId(), remark.getText().toString(), levelStr,
                                                        "true","", "2015-06-06", failTypeStr,
                                                        pplzTextView.getText().toString(),lzcxTextView.getText().toString(),"false", "", "");
            }else{
                requestParams = XutilsRequest.getCvisit(clue.getClueId(), user.getEmpId(), remark.getText().toString(), levelStr,
                        "true", "", "2015-06-06", failTypeStr,
                        "", "", "false", "", "");
            }
        }else{
            if(checkBox.isChecked()){
                //没有战败成功邀约
                requestParams = XutilsRequest.getCvisit(clue.getClueId(), user.getEmpId(), remark.getText().toString(), levelStr,
                        "true", "", "2015-06-06", "",
                        "", "", "true", comeTimeText.getText().toString(), "");
            }else{
                //没有战败的请求没有成功邀约
                requestParams = XutilsRequest.getCvisit(clue.getClueId(), user.getEmpId(), remark.getText().toString(), levelStr,
                        "true", "", "2015-06-06", "",
                        "", "", "false", "", "");
            }
        }
        //发送请求
        showDialog("提交中..");
        HttpUtils utils = HttpUtilsHelper.getInstance();
        utils.send(HttpRequest.HttpMethod.POST, UrlData.CVISIT_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                dismissDialog();
                if(Config.isDebug)
                Log.i(LOG_TAG, "[提交后返回的数据]" + responseInfo.result);
                parseJson(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                dismissDialog();
                Toast.makeText(ClueReturnVisitOk.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }//end uploadDataToNet

    private void parseJson(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String status = jsonObject.getString("status");
            if ("Success".equals(status)) {
                String dataStr = jsonObject.getString("data");
                if (dataStr != null || dataStr.length() > 1) {
                    JSONObject data = new JSONObject(dataStr);
                    String s = data.getString("Status");
                    if ("Success".equals(s)) {
                        //上传成功  开始上传 录音
                        Toast.makeText(ClueReturnVisitOk.this, data.getString("Message"), Toast.LENGTH_SHORT).show();
                        String callFollowId = data.getString("CallFirstID");//上传录音的时候需要
                        //上传录音
                        uploadSoundToNet(callFollowId);
                        //finish();
                    } else {
                        Toast.makeText(ClueReturnVisitOk.this, data.getString("Message"), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ClueReturnVisitOk.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ClueReturnVisitOk.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if(Config.isDebug)
            Log.i(LOG_TAG, "[数据解析出错]" + e);
            Toast.makeText(ClueReturnVisitOk.this, "数据解析错误", Toast.LENGTH_SHORT).show();
        }
    }//end parseJson


    //上传录音文件
    private void uploadSoundToNet(final String callFollowId) {
        if(mediaPlayer == null){
            //mediaPlayer = MediaPlayer.create(this, Uri.fromFile(new File(recordPath)));
            Toast.makeText(this,"录音文件出错,请重启手机",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
       /* int length = mediaPlayer.getDuration()/1000;
        // Log.i(LOG_TAG, "[录音文件的长度] " + length);

        if(length > duration){
            final File outputFile = cheapMap3((int) (length-duration),length);*/
            //final File outputFile = new File(recordPath);

            if(outputFile != null){
                //上传文件
                showDialog("上传录音文件");
                RequestParams requestParams = new RequestParams();
                requestParams.addBodyParameter("recodeing",outputFile);

                String ts = String.valueOf(System.currentTimeMillis());
                //getSign(String type, String pc, String time, String data, String key)
                String sign = XutilsRequest.getSign("uploadclue", XutilsRequest.PC, ts,"",XutilsRequest.KEY);

                String queryStr = "&ts="+ts+"&sign="+sign+"&CallFirstID="+callFollowId+"&timeInt="+duration+"&pc="+XutilsRequest.PC;
                if(Config.isDebug)
                Log.i(LOG_TAG,"[上传文件的queryStr]"+queryStr);

                HttpUtils httpUtils = HttpUtilsHelper.getInstance();
                //&sign=8989732&ts=7778776677&CallFollowID=29226&timeInt=35
                Log.i(LOG_TAG,"[dizhi]"+UrlData.UPLOADCLUE_URL+queryStr);
                httpUtils.send(HttpRequest.HttpMethod.POST, UrlData.UPLOADCLUE_URL+queryStr, requestParams, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        dismissDialog();
                        if(Config.isDebug)
                        Log.i(LOG_TAG, "[上传录音返回的数据]" + responseInfo.result);

                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            String status = jsonObject.getString("status");
                            if("Success".equals(status)){
                                String dataStr = jsonObject.getString("data");
                                JSONObject jsonobj = new JSONObject(dataStr);
                                String sta = jsonobj.getString("Status");
                                if("Success".equals(sta)){
                                    Toast.makeText(ClueReturnVisitOk.this,"录音上传成功",Toast.LENGTH_SHORT).show();
                                    if(outputFile.exists()){
                                        outputFile.delete();//删除文件
                                    }
                                    File file = new File(recordPath);
                                    if(file.exists()){
                                        file.delete();
                                    }
                                    finish();
                                    //这里需要刷新数据

                                    HttpUtilsHelper.downLoadUpdataUI(ClueReturnVisitOk.this,false,0,false,false,false);
                                    if(levelStr.equals("F")) {
                                        Intent intent = new Intent();
                                        intent.setClass(ClueReturnVisitOk.this, ClueActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.putExtra("type", 1);
                                        /**
                                         * name = intent.getStringExtra("name");
                                         phone = intent.getStringExtra("phone");
                                         level = intent.getStringExtra("level");
                                         cusempname = intent.getStringExtra("cusempname");
                                         isExpiry = intent.getStringExtra("isExpiry");
                                         begintime = intent.getStringExtra("begintime");
                                         endtime = intent.getStringExtra("endtime");
                                         *
                                         */
                                        intent.putExtra("name", ClueActivity.clueName);
                                        intent.putExtra("phone", ClueActivity.cluePhone);
                                        intent.putExtra("level", ClueActivity.level);
                                        intent.putExtra("cusempname", ClueActivity.cusempname);
                                        intent.putExtra("isExpiry", ClueActivity.isExpiry);
                                        intent.putExtra("begintime", ClueActivity.begintime);
                                        intent.putExtra("endtime", ClueActivity.endtime);
                                        startActivity(intent);
                                    }else{
                                        if(clueActivity instanceof ClueActivity){
                                            ClueActivity temp = (ClueActivity) clueActivity;
                                            temp.pageIndex = 1;
                                            temp.initData();
                                            clueActivity = null;
                                        }
                                    }

                                    //clueActivity.pageIndex = 1;
                                    //clueActivity.initDataByFilter();
                                }else{
                                    Toast.makeText(ClueReturnVisitOk.this,"录音上传失败"+jsonobj.getString("Message"),Toast.LENGTH_SHORT).show();
                                    showDialogForUploadFail(callFollowId);
                                }
                            }else{
                                Toast.makeText(ClueReturnVisitOk.this,"录音上传失败"+jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                showDialogForUploadFail(callFollowId);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        if(Config.isDebug)
                            Log.i(LOG_TAG, "上传录音失败" + s);
                        dismissDialog();
                        Toast.makeText(ClueReturnVisitOk.this,"录音上传失败"+s,Toast.LENGTH_SHORT).show();
                        showDialogForUploadFail(callFollowId);
                    }
                });
            }else{
                Toast.makeText(this,"录音处理出错",Toast.LENGTH_SHORT).show();
            }
        /*}else{
            Toast.makeText(this,"录音出错",Toast.LENGTH_SHORT).show();
        }*/
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.release();
        }
    }

    private void showDialogForUploadFail(final String callFollowId){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                                        .setMessage("文件上传出错是否重新上传")
                                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                uploadSoundToNet(callFollowId);
                                            }
                                        })
                                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        })
                                        .setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private File cheapMap3(int start,int end){
        File outputFile = new File(Environment.getExternalStorageDirectory(),"tempmp3.mp3");
        try {
            CheapSoundFile cheapSoundFile = CheapSoundFile.create(recordPath,null);
            int startFrame = (int) (1.0 * start * cheapSoundFile.getSampleRate() / cheapSoundFile.getSamplesPerFrame() + 0.5);
            int endFrame = (int) (1.0 * end * cheapSoundFile.getSampleRate() / cheapSoundFile.getSamplesPerFrame() + 0.5);
            if(Config.isDebug)
            Log.i(LOG_TAG,"[截取录音文件] startFrame "+startFrame+"   endFrame "+endFrame+"   start "+start+"   end "+end);
            cheapSoundFile.WriteFile(outputFile,startFrame,endFrame-startFrame);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile;
    }


    private void setDefeatColor(int position){
        if(gridView != null){
            for(int i=0;i<gridView.getChildCount();i++){
                TextView temp = (TextView) gridView.getChildAt(i);
                if(i == position){
                    temp.setBackgroundColor(CHOISE_COLOR);
                }else{
                    temp.setBackgroundColor(DEFAULT_COLOR);
                }
            }
        }
    }

    private void setBrand(int position){
        ArrayList<String> allBrand = getAllBrand();
        if(allBrand.size() > position){
            pplzTextView.setText(allBrand.get(position));
        }
    }

    private void setCarSeries(int position){
        ArrayList<String> strings = getAllCarSeriesByBrandName(pplzTextView.getText().toString());
        if(strings.size() > position){
            lzcxTextView.setText(strings.get(position));
        }
    }

    //获取到所有的品牌
    private ArrayList<String> getAllBrand(){
        if(Config.isDebug)
        Log.i(LOG_TAG,"[getAllBrand]");
        if(user != null){
            List<UserPtInfoModel> list_brand = user.getList_Brand();
            ArrayList<String> temp = new ArrayList<>();
            if(list_brand != null)
            for(int i=0;i<list_brand.size();i++){
                temp.add(list_brand.get(i).getName());
            }
            return temp;
        }
        return new ArrayList<String>();
    }

    private ArrayList<String> getAllCarSeriesByBrandName(String name){
        if(user != null){
            List<UserPtInfoModel> list_brand = user.getList_Brand();
            UserPtInfoModel model = list_brand.get(getPositionByBrandName(name));
            if(Config.isDebug)
            Log.i(LOG_TAG,"通过名字得到的型号"+model);

            int bid = Integer.valueOf(model.getID());
            ArrayList<String> temp = new ArrayList<>();
            if(user.getList_OtherCarSeries()!=null)
            for(int i=0;i<user.getList_OtherCarSeries().size();i++){
                if(Integer.valueOf(user.getList_OtherCarSeries().get(i).getPID()) == bid){
                    temp.add(user.getList_OtherCarSeries().get(i).getName());
                }
            }
            return temp;
        }//end if
        return new ArrayList<String>();
    }

    private int getPositionByBrandName(String name){
        if(user != null){
            List<UserPtInfoModel> list_brand = user.getList_Brand();
            for(int i=0;i<list_brand.size();i++){
                if(list_brand.get(i).getName().equals(name)){
                    return i;
                }
            }
        }
        return 0;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Button temp = (Button) view;
        TextView temp = (TextView) view;
        failTypeStr = temp.getText().toString();
        if("竞品战败".equals(temp.getText())){
            linearLayout.setVisibility(View.VISIBLE);
        }else{
            linearLayout.setVisibility(View.GONE);
        }
        setDefeatColor(position);
    }

    @Override
    public void callBack(int position) {
        lzcxTextView.setText("");
        //setCarSeries(0);
    }


    private String getNowDate(){
        Calendar now = Calendar.getInstance();
        String nowStr  = now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH);
        return nowStr;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String other = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
        String nowstr = getNowDate();
        if(PhoneValidate.compareData(other, nowstr)){
            comeTimeText.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
        }else{
            Toast.makeText(this,"邀约到店时间必须大于今天",Toast.LENGTH_SHORT).show();
        }
        //comeTimeText.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.i(LOG_TAG,"[播放完成]");
        playIcon.setImageResource(R.mipmap.playstart);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if(mediaPlayer != null){
            fileDurationTextView.setText("文件长度:"+mediaPlayer.getDuration()/1000);
        }
    }

    class DefeatTypeAdapter extends BaseAdapter{

        private List<UserPtInfoModel> data;

        public DefeatTypeAdapter(List<UserPtInfoModel> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.returnvisitok_defeat_type_item,null);
            /*if(position == 1){
                textView.set
            }*/
            textView.setText(data.get(position).getName());
            return textView;
        }
    }

}
