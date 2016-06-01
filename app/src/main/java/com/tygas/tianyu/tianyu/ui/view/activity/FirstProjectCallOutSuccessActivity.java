package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.data.Config;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.adapter.SalesConsltantAutoCompeletAdapter;
import com.tygas.tianyu.tianyu.ui.model.FirstProtect;
import com.tygas.tianyu.tianyu.ui.model.SalesConsltant;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.MyDialogHelper;
import com.tygas.tianyu.tianyu.utils.PhoneValidate;
import com.tygas.tianyu.tianyu.utils.ProgressDialogHelper;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by SJTY_YX on 2016/3/2.
 * 播出成功
 */
public class FirstProjectCallOutSuccessActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, DatePickerDialog.OnDateSetListener, MediaPlayer.OnCompletionListener {

    private ImageView saveImageView;//保存按钮
    private ImageView playImageView;//播放录音的按钮
    private CheckBox inviteCheckBox;//再次邀约
    private TextView tv_inviteDate;//再次邀约日期
    private RelativeLayout rl_inviteDateText;

    private CheckBox upkeepCheckBox;//已保养
    private RelativeLayout rl_upkeepTime;
    private TextView tv_upkeepTime;//保养日期
    private EditText upkeepMilTextView;//保养里程

    private CheckBox lostCustomerCheckBox;//流失客户
    private TextView lostReasonEditText;//流失原因
    private LinearLayout ll_lostReason;

    private CheckBox inviteSuccessCheckBox;//邀约成功
    private TextView tv_comeingtime;//邀约进厂时间
    private RelativeLayout rl_comeingtime;
    private TextView tv_lastComeingTime;//最迟进厂时间
    private RelativeLayout rl_lastComeingTime;

    private AutoCompleteTextView asAutoCompleteTextView;//预约服务顾问
    private SalesConsltantAutoCompeletAdapter salesConsltantAutoCompeletAdapter;//自动补全的销售顾问的适配器
    private ArrayList<SalesConsltant> salesConsltants;//自动补全的销售顾问
    private String saleTemp = "";//展示保存asAutoCompelteTextView里面的数据

    private LinearLayout inViteView;//再次邀约的 LinearLayout activity_calloutsuccess_new_ll_invitetime
    private LinearLayout upkeepView;//已保养日期的 LinearLayout  activity_calloutsuccess_new_ll_upkeeptime
    private LinearLayout loastReasomView;//流失原因的 LinearLayout activity_calloutsucces_new_ll_lostreason
    private LinearLayout comeinTimeView;// 邀约进厂时间  activity_calloutsucces_new_ll_comeintime

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private EditText talkProcessEditText;//洽谈记录

    private User user;
    private FirstProtect firstProtect;
    private String path;
    private long duration;

    private TextView durationTextView;//录音时长

    private boolean isCeapRecord = false;//是否截取录音

    private boolean isUploadSalesConsltant = false;//是否在更新销售顾问

    private MediaPlayer mediaPlayer;

    private static final String LOG_TAG = "FirstProjectCallOutSuccessActivity";
//    public static Activity firstProjectActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calloutsuccess);
        MyAppCollection myAppCollection = (MyAppCollection) getApplication();
        if (myAppCollection.getUser() == null) {
            finish();
        }
        user = myAppCollection.getUser();
        firstProtect = (FirstProtect) getIntent().getSerializableExtra("data");
        path = getIntent().getStringExtra("path");
        duration = getIntent().getLongExtra("duration", 0);
        initView();
        initData();
    }//end oncreate

    private void initData() {
        salesConsltants = new ArrayList<>();
        /*for(int i=0;i<3;i++){
            SalesConsltant salesConsltant = new SalesConsltant();
            salesConsltant.setName(""+i);
            salesConsltants.add(salesConsltant);
        }*/
        SalesConsltant salesConsltant = new SalesConsltant();
        salesConsltant.setId(user.getEmpId());
        salesConsltant.setName(user.getEmpName());
        salesConsltants.add(salesConsltant);

        salesConsltantAutoCompeletAdapter = new SalesConsltantAutoCompeletAdapter(salesConsltants);
        asAutoCompleteTextView.setAdapter(salesConsltantAutoCompeletAdapter);
        asAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean flag = false;
                for (int i = 0; i < salesConsltants.size(); i++) {
                    if (salesConsltants.get(i).getName().contains(s.toString())) {
                        flag = true;
                    }
                }
                if (!saleTemp.equals(s.toString().trim()) && !flag) {
                    saleTemp = asAutoCompleteTextView.getText().toString().trim();
                    isUploadSalesConsltant = true;
                    uploadSalesInfo(s);
                }
            }
        });
        durationTextView.setText("" + duration);
        //加载录音文件
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mediaPlayer == null) {
            playImageView.setImageResource(R.mipmap.bofangjingyong);
        } else {
            playImageView.setOnClickListener(this);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
//        Toast.makeText(this, "请提交数据", Toast.LENGTH_SHORT).show();
        showBackDialog();
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

    private void uploadSalesInfo(CharSequence s) {
        RequestParams requestParams = XutilsRequest.getSalesConsltantRequestParams(s.toString());
        HttpUtils httpUtils = HttpUtilsHelper.getInstance();
        httpUtils.send(HttpRequest.HttpMethod.POST, UrlData.SALESCONSLTANT_LIKENAME_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    parseSalesResult(responseInfo.result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                isUploadSalesConsltant = false;
            }//end onSuccess

            @Override
            public void onFailure(HttpException e, String s) {
                isUploadSalesConsltant = false;
            }//end onFailure
        });
    }


    //{"status":"Success","message":"","data":"{\"LikeNameList\":[{\"ID\":\"1\",\"Name\":\"admin\"}],\"totalrows\":0}"}
    private void parseSalesResult(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        String status = jsonObject.getString("status");
        if ("Success".equals(status)) {
            String dataStr = jsonObject.getString("data");
            JSONObject object = new JSONObject(dataStr);
            JSONArray arr = object.getJSONArray("LikeNameList");
            if (salesConsltants == null) {
                salesConsltants = new ArrayList<>();
            }
            //salesConsltants.clear();
            for (int i = 0; i < arr.length(); i++) {
                SalesConsltant parseSale = parseSale(arr.getJSONObject(i));
                if (!salesConsltants.contains(parseSale)) {//比较的是 equls
                    salesConsltants.add(parseSale);
                }
            }
            salesConsltantAutoCompeletAdapter.setmUnfilteredData(salesConsltants);
            asAutoCompleteTextView.setText(asAutoCompleteTextView.getText());
            asAutoCompleteTextView.setSelection(asAutoCompleteTextView.getText().length());
        }
    }//end parseSalesResult

    private SalesConsltant parseSale(JSONObject jsonObject) throws JSONException {
        SalesConsltant salesConsltant = new SalesConsltant();
        salesConsltant.setId(jsonObject.getString("ID"));
        salesConsltant.setName(jsonObject.getString("Name"));
        return salesConsltant;
    }//end saleaConsltant

    @Override
    protected void onResume() {
        super.onResume();
       /* if(mediaPlayer == null){
            Toast.makeText(this,"录音文件损坏",Toast.LENGTH_SHORT).show();
        }else{
            if(isCeapRecord){//是否截取录音
                //截取录音文件
                int length = mediaPlayer.getDuration()/1000;
                // Log.i(LOG_TAG, "[录音文件的长度] " + length);
                }
                if(length >= duration) {
                    //outputFile = cheapMap3((int) (length - duration), length);
                    //mediaPlayer = MediaPlayer.create(this, Uri.fromFile(outputFile));
                }
            }else{
                //outputFile = new File(recordPath);
            }
        }*/
    }

    private void initView() {
        saveImageView = (ImageView) findViewById(R.id.activity_calloutsuccess_fp_save);
        saveImageView.setOnClickListener(this);
        playImageView = (ImageView) findViewById(R.id.activity_calloutsuccess_fp_iv_playIcon);

        inviteCheckBox = (CheckBox) findViewById(R.id.activity_calloutsuccess_fp_cb_invite);
        inviteCheckBox.setOnCheckedChangeListener(this);
        tv_inviteDate = (TextView) findViewById(R.id.activity_calloutsuccess_fp_tv_invitetime);
        rl_inviteDateText = (RelativeLayout) findViewById(R.id.activity_calloutsuccess_fp_rl_invitetime);
        rl_inviteDateText.setOnClickListener(this);

        upkeepCheckBox = (CheckBox) findViewById(R.id.activity_calloutsuccess_fp_cb_upkeep);
        upkeepCheckBox.setOnCheckedChangeListener(this);
        tv_upkeepTime = (TextView) findViewById(R.id.activity_calloutsuccess_fp_tv_upkeeptime);
        rl_upkeepTime = (RelativeLayout) findViewById(R.id.activity_calloutsuccess_fp_rl_upkeeptime);
        upkeepMilTextView = (EditText) findViewById(R.id.activity_calloutsucces_fp_et_mil);
        rl_upkeepTime.setOnClickListener(this);


        lostCustomerCheckBox = (CheckBox) findViewById(R.id.activity_calloutsucces_fp_cb_lostcusotmer);
        lostCustomerCheckBox.setOnCheckedChangeListener(this);
        lostReasonEditText = (TextView) findViewById(R.id.activity_calloutsucces_fp_et_lostreason);
        ll_lostReason = (LinearLayout) findViewById(R.id.activity_calloutsucces_fp_ll_lostreason);
        ll_lostReason.setOnClickListener(this);

        inViteView = (LinearLayout) findViewById(R.id.activity_calloutsuccess_fp_ll_invitetime);//再次邀约的 LinearLayout activity_calloutsuccess_new_ll_invitetime
        upkeepView = (LinearLayout) findViewById(R.id.activity_calloutsuccess_fp_ll_upkeeptime);//已保养日期的 LinearLayout  activity_calloutsuccess_new_ll_upkeeptime
        loastReasomView = (LinearLayout) findViewById(R.id.activity_calloutsucces_fp_ll_lostreason);//流失原因的 LinearLayout activity_calloutsucces_new_ll_lostreason
        comeinTimeView = (LinearLayout) findViewById(R.id.activity_calloutsucces_fp_ll_comeintime);


        inviteSuccessCheckBox = (CheckBox) findViewById(R.id.activity_calloutsuccess_fp_cb_invitesuccess);
        inviteSuccessCheckBox.setOnCheckedChangeListener(this);

        tv_comeingtime = (TextView) findViewById(R.id.activity_calloutsucces_fp_tv_comeintime);
        rl_comeingtime = (RelativeLayout) findViewById(R.id.activity_calloutsucces_fp_rl_comeintime);
        tv_lastComeingTime = (TextView) findViewById(R.id.activity_calloutsuccess_fp_tv_lastcometime);
        rl_lastComeingTime = (RelativeLayout) findViewById(R.id.activity_calloutsuccess_fp_rl_lastcometime);
        rl_comeingtime.setOnClickListener(this);
        rl_lastComeingTime.setOnClickListener(this);


        asAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.activity_calloutsuccess_fp_actv_sa);
        asAutoCompleteTextView.setText(user.getEmpName());
        saleTemp = asAutoCompleteTextView.getText().toString();


        // upkeepView = findViewById(R.id.activity_calloutsuccess_v_upkepptime);

        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = DatePickerDialog.newInstance(this, y, m, d);
        datePickerDialog.setAccentColor(Color.rgb(50, 126, 202));//327ECA

        talkProcessEditText = (EditText) findViewById(R.id.activity_calloutsuccesset_fp_talkprocess);
        durationTextView = (TextView) findViewById(R.id.activity_calloutsuccess_fp_tv_duration);

    }//end initview

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_calloutsuccess_fp_save://点击了保存
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                saveToNet();
                break;
            case R.id.activity_calloutsuccess_fp_iv_playIcon://点击了播放
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        playImageView.setImageResource(R.mipmap.playstart);
                    } else {
                        mediaPlayer.start();
                        playImageView.setImageResource(R.mipmap.playpause);
                    }
                }
                break;

            case R.id.activity_calloutsuccess_fp_rl_invitetime:
                if (inviteCheckBox.isChecked()) {
                    if (!datePickerDialog.isAdded()) {
                        datePickerDialog.show(getFragmentManager(), "invite");
                    }
                }
                break;

            case R.id.activity_calloutsuccess_fp_rl_upkeeptime:
                if (upkeepCheckBox.isChecked()) {
                    if (!datePickerDialog.isAdded()) {
                        datePickerDialog.show(getFragmentManager(), "upkeep");
                    }
                }
                break;

            case R.id.activity_calloutsucces_fp_rl_comeintime://邀约进厂时间
                if (inviteSuccessCheckBox.isChecked()) {
                    if (!datePickerDialog.isAdded()) {
                        datePickerDialog.show(getFragmentManager(), "comeintime");
                    }
                }

                break;
            case R.id.activity_calloutsuccess_fp_rl_lastcometime://最迟进厂时间
                if (inviteSuccessCheckBox.isChecked()) {
                    if (!datePickerDialog.isAdded()) {
                        datePickerDialog.show(getFragmentManager(), "lastcometime");
                    }
                }

                break;


            case R.id.activity_calloutsucces_fp_ll_lostreason://流失原因
                if (lostCustomerCheckBox.isChecked()) {
                    new MyDialogHelper().showListDialog(this, "请选择", getLostReasonString(), lostReasonEditText);
                }
                break;
        }
    }//end onClick


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    private List<String> getLostReasonString() {
        List<UserPtInfoModel> result = user.getList_LostResult();
        List<String> names = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            names.add(result.get(i).getName());
        }
        return names;
    }


    //上传基本数据
    private void saveToNet() {
        RequestParams requestParams = getRequestParams();
        if (requestParams == null) {
        } else {
            ProgressDialogHelper.showProgressDialog(this, "数据提交中..");
            //提交到网络
            HttpUtils httpUtils = HttpUtilsHelper.getInstance();
            httpUtils.send(HttpRequest.HttpMethod.POST, UrlData.FIRST_ISSHOURESULT_URL, requestParams, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    ProgressDialogHelper.dismissProgressDialog();
                    try {
                        parseResult(responseInfo.result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    ProgressDialogHelper.dismissProgressDialog();
                    Toast.makeText(FirstProjectCallOutSuccessActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }//end saveToNet


    //{"status":"Success","message":"","data":"{\"Status\":\"Success\",\"Message\":\"回访保存成功!\",\"InviteCallID\":\"8092\"}"}
    private void parseResult(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        String status = jsonObject.getString("status");
        if ("Success".equals(status)) {
            //Log.i(LOG_TAG,"[status_1]"+status);
            String dataStr = jsonObject.getString("data");
            JSONObject object = new JSONObject(dataStr);
            status = object.getString("Status");
            if ("Success".equals(status)) {
                Toast.makeText(this, "" + object.getString("Message"), Toast.LENGTH_SHORT).show();
                //数据上传成功 上传录音文件
                String InviteCallID = object.getString("InviteCallID");
                uploadRecordFile(InviteCallID);//上传录音文件
            } else {
                Toast.makeText(this, "" + object.getString("Message"), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
        }
    }//end

    private void uploadRecordFile(final String id) {
        final File recordFile = new File(path);
        if (recordFile.exists()) {
            ProgressDialogHelper.showProgressDialog(this, "上传录音文件");
            RequestParams requestParams = new RequestParams();
            requestParams.addBodyParameter("recodeing", recordFile);
            String ts = String.valueOf(System.currentTimeMillis());
            String sign = XutilsRequest.getSign("UploadShouInv", XutilsRequest.PC, ts, XutilsRequest.KEY);
            String queryStr = "&ts=" + ts + "&sign=" + sign + "&CallFirstID=" + id + "&timeInt=" + duration + "&pc=" + XutilsRequest.PC;
            HttpUtils httpUtils = HttpUtilsHelper.getInstance();
            httpUtils.configCurrentHttpCacheExpiry(1000 * 10);//设置当前网络请求的缓存超时时间
            httpUtils.configTimeout(1000 * 60);//设置连接超时时间
            httpUtils.configSoTimeout(1000 * 60);//设置连接超时时间
            //Log.i(LOG_TAG,"[上传录音时候的url]"+UrlData.UPLOADSHOUINV_URL+queryStr);
            httpUtils.send(HttpRequest.HttpMethod.POST, UrlData.UPLOADSHOUINV_URL + queryStr, requestParams, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    ProgressDialogHelper.dismissProgressDialog();
                    try {
                        parseUploadResult(responseInfo.result, recordFile, id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }//end onSuccess

                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(FirstProjectCallOutSuccessActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    ProgressDialogHelper.dismissProgressDialog();
                }//end onFailure
            });
        } else {
            Toast.makeText(this, "录音文件不存在", Toast.LENGTH_SHORT).show();
            finish();
        }
    }//end uploadRecordFile

    /**
     * 解析文件上传后的数据
     * {"status":"Success","message":"","data":"{\"Status\":\"Success\",\"Message\":\"\",\"UploadMsg\":\"文件上传成功1:/OS2:/UpLoadFile/201603/09/3:20160309133654黄刚.mp34:/OS/UpLoadFile/201603/09/20160309133654黄刚.mp35:System.Web.HttpRequest\",\"UploadStatus\":\"Success\"}"}
     *
     * @param result
     */
    private void parseUploadResult(String result, File f, String id) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        String status = jsonObject.getString("status");
        if ("Success".equals(status)) {
            String dataStr = jsonObject.getString("data");
            jsonObject = new JSONObject(dataStr);
            status = jsonObject.getString("Status");
            if ("Success".equals(status)) {
                Toast.makeText(this, "录音上传成功", Toast.LENGTH_SHORT).show();
                if (f.exists()) {
                    f.delete();
                }
                finish();
                HttpUtilsHelper.downLoadUpdataUI(FirstProjectCallOutSuccessActivity.this, false, 0, false, false, false);
//                if (firstProjectActivity instanceof FirstProtectActivity) {
//                    FirstProtectActivity temp = (FirstProtectActivity) firstProjectActivity;
//                    temp.refreshData();
//                    firstProjectActivity = null;
//                }
                Intent i = new Intent("ty.refreshlist.fp");
                sendBroadcast(i);

            } else {
                Toast.makeText(this, "录音上传失败" + jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
                showDialogForUploadFail(id);
            }
        } else {
            Toast.makeText(this, "录音上传失败" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            showDialogForUploadFail(id);
        }
    }//emd

    private void showDialogForUploadFail(final String callFollowId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("文件上传出错是否重新上传")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        uploadRecordFile(callFollowId);
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


    private boolean validateNotEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return false;
        } else {
            return true;
        }
    }//end validateEmpty

    private boolean validateDate(String date) {
        String nowstr = getNextTime(getNowDate());
        return PhoneValidate.compareData(date, nowstr);
    }

    public String getNextTime(String strTime) {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(strTime);
            cal.setTime(date);
            cal.add(cal.DATE, 0);
            return sdf.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getNowDate() {
        Calendar now = Calendar.getInstance();
        String nowStr = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        return nowStr;
    }

    /**
     * 得到请求的参数
     *
     * @return
     */
    private RequestParams getRequestParams() {

        String perdictFitDate = firstProtect.getPredictFitDate();
        String frameNum = firstProtect.getFrameNum();
        if (!validateNotEmpty(talkProcessEditText.getText().toString())) {
            Toast.makeText(this, "请填写洽谈记录", Toast.LENGTH_SHORT).show();
            return null;
        }

        if (inviteCheckBox.isChecked()) {
            if (!validateNotEmpty(tv_inviteDate.getText().toString())) {
                Toast.makeText(this, "请选择邀约日期", Toast.LENGTH_SHORT).show();
                return null;
            }
            //再次邀约
            if (validateDate(tv_inviteDate.getText().toString())) {
                return XutilsRequest.getFirstProjectCallSuccess(frameNum, "true", talkProcessEditText.getText().toString(),
                        "1", tv_inviteDate.getText().toString(),
                        "0", "", "",
                        "0", "",
                        "0", "", "", "", "1",
                        user.getEmpId(), "首保", perdictFitDate, null);
            } else {
                Toast.makeText(this, "再次邀约不能小于今天", Toast.LENGTH_SHORT).show();
                return null;
            }

        } else if (upkeepCheckBox.isChecked()) {
            if (!validateNotEmpty(upkeepMilTextView.getText().toString())) {
                Toast.makeText(this, "请填写保养里程", Toast.LENGTH_SHORT).show();
                return null;
            }
            if (!validateNotEmpty(tv_upkeepTime.getText().toString())) {
                Toast.makeText(this, "请选择保养日期", Toast.LENGTH_SHORT).show();
                return null;
            }
            if (PhoneValidate.compareData(tv_upkeepTime.getText().toString(), getNowDate())) {
                Toast.makeText(this, "保养日期不能大于今天", Toast.LENGTH_SHORT).show();
                return null;
            }
            //已保养
            return XutilsRequest.getFirstProjectCallSuccess(frameNum, "true", talkProcessEditText.getText().toString(),
                    "0", "",
                    "1", tv_upkeepTime.getText().toString(), upkeepMilTextView.getText().toString(),
                    "0", "",
                    "0", "", "", "", "1",
                    user.getEmpId(), "首保", perdictFitDate, null);
        } else if (lostCustomerCheckBox.isChecked()) {
            if (!validateNotEmpty(lostReasonEditText.getText().toString())) {
                Toast.makeText(this, "请填写流失原因", Toast.LENGTH_SHORT).show();
                return null;
            }
            //客户流失
            return XutilsRequest.getFirstProjectCallSuccess(frameNum, "true", talkProcessEditText.getText().toString(),
                    "0", "",
                    "0", "", "",
                    "1", getLostReasonId(lostReasonEditText.getText().toString()),
                    "0", "", "", "", "1",
                    user.getEmpId(), "首保", perdictFitDate, null);
        } else if (inviteSuccessCheckBox.isChecked()) {
            //邀约成功
            if (getAsID(asAutoCompleteTextView.getText().toString()).equals("false")) {
                Toast.makeText(this, "预约服务顾问不存在", Toast.LENGTH_SHORT).show();
                return null;
            }
            if (!validateNotEmpty(tv_comeingtime.getText().toString())) {
                Toast.makeText(this, "请选择邀约进厂时间", Toast.LENGTH_SHORT).show();
                return null;
            }
            if (!validateDate(tv_comeingtime.getText().toString())) {
                Toast.makeText(this, "邀约进厂时间不能小于今天", Toast.LENGTH_SHORT).show();
                return null;
            }
            if (!validateNotEmpty(tv_lastComeingTime.getText().toString())) {
                Toast.makeText(this, "请选择最迟进厂时间", Toast.LENGTH_SHORT).show();
                return null;
            }
            if (PhoneValidate.compareDataTime(tv_comeingtime.getText().toString(), tv_lastComeingTime.getText().toString())) {
                Toast.makeText(this, "最迟邀约时间不能小于邀约进厂时间", Toast.LENGTH_SHORT).show();
                return null;
            }

            return XutilsRequest.getFirstProjectCallSuccess(frameNum, "true", talkProcessEditText.getText().toString(),
                    "0", "",
                    "0", "", "",
                    "0", "",
                    "1", tv_comeingtime.getText().toString(), tv_lastComeingTime.getText().toString(),
                    getAsID(asAutoCompleteTextView.getText().toString()), "1",
                    user.getEmpId(), "首保", perdictFitDate, null);
        } else {
            Toast.makeText(this, "请选择邀约情况", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private String getLostReasonId(String s) {
        List<UserPtInfoModel> result = user.getList_LostResult();
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getName().equals(s)) {
                return result.get(i).getID();
            }
        }
        return "";
    }

    /**
     * 通过name获取到销售顾问的id
     *
     * @param name
     * @return
     */
    private String getAsID(String name) {
        for (int i = 0; i < salesConsltants.size(); i++) {
            if (name.equals(salesConsltants.get(i).getName())) {
                return salesConsltants.get(i).getId();
            }
        }
        return "false";
    }//end getAsID

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.activity_calloutsuccess_fp_cb_invite://再次邀约

                if (isChecked) {
                    inViteView.setVisibility(View.VISIBLE);
                    upkeepView.setVisibility(View.GONE);
                    loastReasomView.setVisibility(View.GONE);
                    comeinTimeView.setVisibility(View.GONE);
                    tv_inviteDate.setText(getNextDate(1));
                    //datePickerDialog.show(getFragmentManager(), "invite");
                } else {
                    inViteView.setVisibility(View.GONE);
                    tv_inviteDate.setText("");
                }
                break;
            case R.id.activity_calloutsuccess_fp_cb_upkeep://已保养
                if (isChecked) {
                    upkeepView.setVisibility(View.VISIBLE);
                    inViteView.setVisibility(View.GONE);
                    loastReasomView.setVisibility(View.GONE);
                    comeinTimeView.setVisibility(View.GONE);
                    //   datePickerDialog.show(getFragmentManager(), "upkeep");

                } else {
                    upkeepView.setVisibility(View.GONE);
                    tv_upkeepTime.setText("");
                    upkeepMilTextView.setText("");


                }
                break;
            case R.id.activity_calloutsucces_fp_cb_lostcusotmer://流失客户
                if (isChecked) {
                    loastReasomView.setVisibility(View.VISIBLE);
                    inViteView.setVisibility(View.GONE);
                    upkeepView.setVisibility(View.GONE);
                    comeinTimeView.setVisibility(View.GONE);

                } else {
                    loastReasomView.setVisibility(View.GONE);
                    lostReasonEditText.setText("");
                }
                break;
            case R.id.activity_calloutsuccess_fp_cb_invitesuccess://成功邀约
                if (isChecked) {
                    comeinTimeView.setVisibility(View.VISIBLE);
                    inViteView.setVisibility(View.GONE);
                    upkeepView.setVisibility(View.GONE);
                    loastReasomView.setVisibility(View.GONE);
                    asAutoCompleteTextView.setText(user.getEmpName());

                } else {
                    comeinTimeView.setVisibility(View.GONE
                    );
                    asAutoCompleteTextView.setText("");

                }
                break;
        }
        clearCheckBox(buttonView, isChecked);
    }//end onCheckedChanged

    public String getNextDate(int index) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, index);
        String nowStr = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        return nowStr;
    }

    private void clearCheckBox(CompoundButton buttonView, boolean isChecked) {
        ArrayList<CheckBox> boxs = new ArrayList<>();
        boxs.add(inviteCheckBox);
        boxs.add(upkeepCheckBox);
        boxs.add(lostCustomerCheckBox);
        boxs.add(inviteSuccessCheckBox);
        for (int i = 0; i < boxs.size(); i++) {
            boxs.get(i).setChecked(false);
        }
        for (int i = 0; i < boxs.size(); i++) {
            if (boxs.get(i) == buttonView) {
                if (isChecked) {
                    boxs.get(i).setChecked(isChecked);
                }
            }
        }//end for
    }

    private String yaoyuejinchangdate;
    private String zuicijingchangdate;

    @Override
    public void onDateSet(DatePickerDialog view, final int year, final int monthOfYear, final int dayOfMonth) {
        String other = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        String nowstr = getNowDate();
        if (view.getTag().equals("invite")) {
            if (PhoneValidate.compareData(other, nowstr)) {
                tv_inviteDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            } else {
                Toast.makeText(this, "再次邀约时间必须大于等于今天", Toast.LENGTH_SHORT).show();
            }

        } else if (view.getTag().equals("upkeep")) {
            tv_upkeepTime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        } else if (view.getTag().equals("comeintime")) {//邀约进厂时间
            if (PhoneValidate.compareData(other, nowstr)) {
                timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        String result = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " " + hourOfDay + ":" + minute;
                        if (zuicijingchangdate == null) {
                            tv_comeingtime.setText(result);
                            yaoyuejinchangdate = result;
                        } else {
                            if (PhoneValidate.compareDataTime(zuicijingchangdate, result)) {
                                tv_comeingtime.setText(result);
                                yaoyuejinchangdate = result;
                            } else {
                                Toast.makeText(FirstProjectCallOutSuccessActivity.this, "最迟进厂时间不能小于邀约进厂时间", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, 12, 12, true);
                timePickerDialog.setAccentColor(Color.rgb(50, 126, 202));
                if (!timePickerDialog.isAdded()) {
                    timePickerDialog.show(getFragmentManager(), "comeintime");
                }

            } else {
                Toast.makeText(this, "邀约进厂时间必须大于等于今天", Toast.LENGTH_SHORT).show();
            }

        } else if (view.getTag().equals("lastcometime")) {//最迟进厂时间
            if (PhoneValidate.compareData(other, nowstr)) {
                timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        String result = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " " + hourOfDay + ":" + minute;
                        if (yaoyuejinchangdate == null) {
                            tv_lastComeingTime.setText(result);
                            zuicijingchangdate = result;
                        } else {
                            if (PhoneValidate.compareDataTime(result, yaoyuejinchangdate)) {
                                tv_lastComeingTime.setText(result);
                                zuicijingchangdate = result;
                            } else {
                                Toast.makeText(FirstProjectCallOutSuccessActivity.this, "最迟进厂时间不能小于邀约进厂时间", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }, 12, 12, true);
                timePickerDialog.setAccentColor(Color.rgb(50, 126, 202));
                if (!timePickerDialog.isAdded()) {
                    timePickerDialog.show(getFragmentManager(), "comeintime");
                }
            } else {
                Toast.makeText(this, "最迟进厂时间必须大于等于今天", Toast.LENGTH_SHORT).show();
            }
        }
    }//end onDateSet

    @Override
    public void onCompletion(MediaPlayer mp) {
        playImageView.setImageResource(R.mipmap.playstart);
    }
}
