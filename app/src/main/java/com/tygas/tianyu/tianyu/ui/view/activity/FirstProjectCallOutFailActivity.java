package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.model.FirstProtect;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.PhoneValidate;
import com.tygas.tianyu.tianyu.utils.ProgressDialogHelper;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by SJTY_YX on 2016/3/3.
 */
public class FirstProjectCallOutFailActivity extends Activity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {


    private ImageView saveImageView; //保存按钮

    private EditText remarkEditText;//备注
    private LinearLayout time;
    private TextView nextTimeTextView;
    private int nowPositon;//当前的 选择位置
    private DatePickerDialog dpd;
    private String callBackString;// "稍后回访"  明日回访  约定回访

    private User user;
    private FirstProtect firstProtect;

    private static final String LOG_TAG = "FirstProjectCallOutFailActivity";

    private static final int CHOISE_COLOR = Color.rgb(161, 225, 156);//0xa1e19c; //等级默认的颜色
    private static final int DEFAULT_COLOR = Color.rgb(244, 254, 192);//0xf4fec0; //等级选中的颜色
//    public static Activity firstProjectActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calloutfail);
        MyAppCollection myAppCollection = (MyAppCollection) getApplication();
        if (myAppCollection.getUser() == null) {
            finish();
        }
        user = myAppCollection.getUser();
        //intent.putExtra("data", nowfirstProtect);
        //intent.putExtra("path", recordPath);
        firstProtect = (FirstProtect) getIntent().getSerializableExtra("data");
        initView();
    }

    private void initView() {
        saveImageView = (ImageView) findViewById(R.id.activity_calloutfail_iv_save);
        saveImageView.setOnClickListener(this);
        remarkEditText = (EditText) findViewById(R.id.activity_calloutfail_et_remark);
        nextTimeTextView = (TextView) findViewById(R.id.activity_calloutfail_tv_nextTime);
        time = (LinearLayout) findViewById(R.id.activity_calloutfail_ll_time);
        for (int i = 0; i < time.getChildCount(); i++) {
            time.getChildAt(i).setOnClickListener(this);
        }
        setColor();
        nextTimeTextView.setText(getNowTime());

        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(Color.rgb(50, 126, 202));//327ECA

        callBackString = "稍后邀约";//默认的是稍后回访
    }//end initView

    private void setColor() {
        for (int i = 0; i < time.getChildCount(); i++) {
            if (i == nowPositon) {
                time.getChildAt(i).setBackgroundColor(CHOISE_COLOR);
            } else {
                time.getChildAt(i).setBackgroundColor(DEFAULT_COLOR);
            }
        }
    }

    private String getNowTime() {
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DATE);
        return y + "-" + m + "-" + d;
    }

    private String getNextTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, +1);
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DATE);
        return y + "-" + m + "-" + d;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String other = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        String nowstr = getNowTime();
        if (PhoneValidate.compareData(other, nowstr)) {
            nextTimeTextView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        } else {
            Toast.makeText(this, "下次邀约日期不能小于今天", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_calloutfail_iv_save://保存按钮
                //Log.i(LOG_TAG,"[点击了保存]"+callBackString+"  "+nextTimeTextView.getText().toString()+"   "+remarkEditText.getText().toString());
                if (remarkEditText.getText().toString() == null || "".equals(remarkEditText.getText().toString())) {
                    Toast.makeText(this, "请填写备注", Toast.LENGTH_SHORT).show();
                } else {
                    saveToNet();
                }
                break;
            case R.id.activity_calloutfail_tv_later://稍后回访
                nowPositon = 0;
                setColor();
                nextTimeTextView.setText(getNowTime());
                callBackString = "稍后邀约";
                break;
            case R.id.activity_calloutfail_tv_tom://明日
                nowPositon = 1;
                setColor();
                nextTimeTextView.setText(getNextTime());
                callBackString = "明日邀约";
                break;
            case R.id.activity_calloutfail_tv_self://约定
                nowPositon = 2;
                setColor();
                if (!dpd.isAdded()) {
                    dpd.show(getFragmentManager(), "nexttime");
                }

                callBackString = "约定邀约";
                break;
        }
    }

    /**
     * 保存数据到服务器
     */
    private void saveToNet() {
        String talkProcess = remarkEditText.getText().toString();
        String callbackTime = nextTimeTextView.getText().toString();
        String frameNum = firstProtect.getFrameNum();
        String perdictFitDate = firstProtect.getPredictFitDate();
        RequestParams requestParams =
                XutilsRequest.getFirstProjectCallSuccess(
                        frameNum, "false", talkProcess,
                        "1", callbackTime,
                        "0", "", "",
                        "0", "",
                        "0", "", "", "", "",
                        user.getEmpId(), "首保", perdictFitDate, null);
        ProgressDialogHelper.showProgressDialog(this, "数据提交中...");
        HttpUtils httpUtils = HttpUtilsHelper.getInstance();
        httpUtils.send(HttpRequest.HttpMethod.POST, UrlData.FIRST_ISSHOURESULT_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ProgressDialogHelper.dismissProgressDialog();
                try {
                    parseResult(responseInfo.result);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(FirstProjectCallOutFailActivity.this, "数据解析出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(FirstProjectCallOutFailActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                ProgressDialogHelper.dismissProgressDialog();
            }
        });

    }//end saveToNet


    //{"status":"Success","message":"","data":"{\"Status\":\"Success\",\"Message\":\"回访保存成功!\",\"InviteCallID\":\"8558\"}"}
    private void parseResult(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        String status = jsonObject.getString("status");
        if ("Success".equals(status)) {
            String dataStr = jsonObject.getString("data");
            JSONObject object = new JSONObject(dataStr);
            status = object.getString("Status");
            if ("Success".equals(status)) {
                Toast.makeText(this, "回访保存成功", Toast.LENGTH_SHORT).show();
                finish();
                HttpUtilsHelper.downLoadUpdataUI(FirstProjectCallOutFailActivity.this, false, 0, false, false, false);
//                if (firstProjectActivity instanceof FirstProtectActivity) {
//                    FirstProtectActivity temp = (FirstProtectActivity) firstProjectActivity;
//                    temp.refreshData();
//                    firstProjectActivity = null;
//                }
                Intent i = new Intent("ty.refreshlist.fp");
                sendBroadcast(i);

            } else {
                Toast.makeText(this, "" + object.getString("Message"), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, " " + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
        }
    }//end parseResult

    @Override
    public void onBackPressed() {
        //super .onBackPressed();
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

}
