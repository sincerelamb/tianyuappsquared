package com.tygas.tianyu.tianyu.ui.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.data.Config;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by SJTY_YX on 2016/1/28.
 */
public class ClueFilterActivity extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {


    private ImageView backImageView;//返回按钮
    private ImageView confirmImageView;//确定按钮

    private EditText nameEditText;//姓名
    private EditText phoneEditText;//电话
    private EditText empNameEditText;//员工姓名
    private LinearLayout isExpireRadioGroup;//是否逾期
    private CheckBox yTimeCheckBox;//逾期
    private CheckBox nTimeCheckBox;//未逾期
    private RelativeLayout callTimeRelativeLayout;//应访问时间
    private RelativeLayout nextTimeRelativeLayout;//结束访问时间

    private View clearLastTimeButton;
    private View clearNextTimeButton;

    private DatePickerDialog datePickerDialog;
    private String begintime;
    private String endtime;

    private static final String LOG_TAG = "ClueFilterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_cluefilter);
        begintime = getIntent().getStringExtra("begintime");
        endtime = getIntent().getStringExtra("endtime");
        initView();
    }

    private void initView() {

        clearLastTimeButton = findViewById(R.id.activity_cluefilter_btn_clearlasttime);
        clearNextTimeButton = findViewById(R.id.activity_cluefilter_btn_clearlastnext);
        clearLastTimeButton.setOnClickListener(this);
        clearNextTimeButton.setOnClickListener(this);

        backImageView = (ImageView) findViewById(R.id.activity_cluefilter_back);
        backImageView.setOnClickListener(this);
        confirmImageView = (ImageView) findViewById(R.id.activity_cluefilter_confirm);
        confirmImageView.setOnClickListener(this);
        nameEditText = (EditText) findViewById(R.id.activity_cluefilter_et_name);
        phoneEditText = (EditText) findViewById(R.id.activity_cluefilter_et_phone);
        empNameEditText = (EditText) findViewById(R.id.activity_cluefilter_et_empName);

        yTimeCheckBox = (CheckBox) findViewById(R.id.activity_cluefilter_rb_yes);
        nTimeCheckBox = (CheckBox) findViewById(R.id.activity_cluefilter_rb_no);
        yTimeCheckBox.setOnClickListener(this);
        nTimeCheckBox.setOnClickListener(this);

        callTimeRelativeLayout = (RelativeLayout) findViewById(R.id.activity_cluefilter_rl_calltime);
        callTimeRelativeLayout.setOnClickListener(this);
        nextTimeRelativeLayout = (RelativeLayout) findViewById(R.id.activity_cluefilter_rl_nexttime);
        nextTimeRelativeLayout.setOnClickListener(this);

        /**
         intent.putExtra("clueName",clueName);
         intent.putExtra("cluePhone",cluePhone);
         intent.putExtra("cusempname",cusempname);
         intent.putExtra("isExpiry",isExpiry);
         *
         */
        nameEditText.setText(getIntent().getStringExtra("clueName"));
        phoneEditText.setText(getIntent().getStringExtra("cluePhone"));
        empNameEditText.setText(getIntent().getStringExtra("cusempname"));

        Log.i(LOG_TAG,"[获取到的isExpiry]"+getIntent().getStringExtra("isExpiry"));

        if(getIntent().getStringExtra("isExpiry").equals("false") || getIntent().getStringExtra("isExpiry").equals("true")){
            Log.i(LOG_TAG,"[设置逾期的值]");
            //setIsExpire(Boolean.valueOf(getIntent().getStringExtra("isExpiry")));
            if(Boolean.valueOf(getIntent().getStringExtra("isExpiry"))){
                yTimeCheckBox.setChecked(true);
                nTimeCheckBox.setChecked(false);
            }else{
                nTimeCheckBox.setChecked(true);
                yTimeCheckBox.setChecked(false);
            }
        }

        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        if(begintime == null){
            setTime(callTimeRelativeLayout,y+"-"+(m+1)+"-"+d);
        }else{
            setTime(callTimeRelativeLayout,begintime);
        }
        if(endtime == null){
            setTime(nextTimeRelativeLayout,y+"-"+(m+1)+"-"+d);
        }else{
            setTime(nextTimeRelativeLayout,endtime);
        }
        datePickerDialog = DatePickerDialog.newInstance(this,y,m,d);
        if(Config.isDebug)
        Log.i(LOG_TAG,"[设置弹出框的 y m d]"+y+"  "+m+"  "+d);
        datePickerDialog.setAccentColor(Color.rgb(50, 126, 202));//327ECA
    }

    //设置是否过期
    private void setIsExpire(boolean flag){
        if(flag){//逾期
            if(yTimeCheckBox.isChecked()){
                yTimeCheckBox.setChecked(true);
            }else{
                yTimeCheckBox.setChecked(false);
            }
            nTimeCheckBox.setChecked(false);
        }else{
            if(nTimeCheckBox.isChecked()){
                nTimeCheckBox.setChecked(true);
            }else{
                nTimeCheckBox.setChecked(false);
            }
            yTimeCheckBox.setChecked(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_cluefilter_back://点击了返回按钮
                finish();
                break;
            case R.id.activity_cluefilter_confirm://点击了确定按钮
                Intent intent = new Intent();
                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String empName = empNameEditText.getText().toString();
                String isExpire = getIsExpire();
                String starttime = getTime(callTimeRelativeLayout);
                String endtime = getTime(nextTimeRelativeLayout);
                if(Config.isDebug)
                Log.i(LOG_TAG,"[回传的数据] name "+name+"  phone "+phone+"  isexpire "+isExpire+"  starttime "+starttime+"  endtime "+endtime);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("empName",empName);
                intent.putExtra("isExpire",isExpire);
                intent.putExtra("starttime",starttime);
                intent.putExtra("endtime",endtime);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.activity_cluefilter_rl_calltime://点击了开始时间
                datePickerDialog.show(getFragmentManager(), "begintime");
                break;
            case R.id.activity_cluefilter_rl_nexttime://点击了结束时间
                datePickerDialog.show(getFragmentManager(), "endtime");
                break;
            case R.id.activity_cluefilter_rb_yes:
                setIsExpire(true);
                break;
            case R.id.activity_cluefilter_rb_no:
                setIsExpire(false);
                break;
            case R.id.activity_cluefilter_btn_clearlasttime://清除开始时间
                setTime(callTimeRelativeLayout,"");
                break;
            case R.id.activity_cluefilter_btn_clearlastnext://清除 结束时间
                setTime(nextTimeRelativeLayout,"");
                break;
        }
    }//end onclick

    private String getTime(RelativeLayout relativeLayout){
        TextView textView = (TextView) relativeLayout.getChildAt(1);
        return textView.getText().toString();
    }

    private void setTime(RelativeLayout relativeLayout,String time){
        TextView textView = (TextView) relativeLayout.getChildAt(1);
        textView.setText(time);
    }

    private String getIsExpire(){
        if(yTimeCheckBox.isChecked()){
            return "true";
        }else if(nTimeCheckBox.isChecked()){
            return "false";
        }else{
            return "";
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String tag = view.getTag();
        String data = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
        if("begintime".equals(tag)){
            setTime(callTimeRelativeLayout,data);
        }else{
            setTime(nextTimeRelativeLayout,data);
        }
    }//end onDateSet
}
