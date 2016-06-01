package com.tygas.tianyu.tianyu.ui.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by SJTY_YX on 2016/3/2.
 * 播出成功
 *
 */
public class CallOutSuccessActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, DatePickerDialog.OnDateSetListener {

    private ImageView saveImageView;//保存按钮
    private ImageView playImageView;//播放录音的按钮
    private CheckBox inviteCheckBox;//再次邀约
    private EditText inviteDateEditText;//再次邀约日期

    private CheckBox upkeepCheckBox;//已保养
    private EditText upkeepTimeEditText;//保养日期
    private EditText upkeepMilTextView;//保养里程

    private CheckBox lostCustomerCheckBox;//流失客户
    private EditText lostReasonEditText;//流失原因

    private CheckBox inviteSuccessCheckBox;//邀约成功
    private TextView comeingtimeTextView;//邀约进厂时间
    private TextView lastComeingTimeTextView;//最迟进厂时间
    private EditText asEditText;//预约服务顾问

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private View upkeepView;


    private static final String LOG_TAG = "CallOutSuccessActivity";
    private static final int CANUSE_COLOR = Color.WHITE;
    private static final int DONTUSER_COLOR = Color.rgb(221,221,221);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calloutsuccess);
        initView();
    }//end oncreate

    private void initView() {
//        saveImageView = (ImageView) findViewById(R.id.activity_calloutsuccess_save);
//        saveImageView.setOnClickListener(this);
//        playImageView = (ImageView) findViewById(R.id.activity_calloutsuccess_iv_playIcon);
//        playImageView.setOnClickListener(this);
//
//        inviteCheckBox = (CheckBox) findViewById(R.id.activity_calloutsuccess_cb_invite);
//        inviteCheckBox.setOnCheckedChangeListener(this);
//        inviteDateEditText = (EditText) findViewById(R.id.activity_calloutsuccess_et_invitetime);
//
//        upkeepCheckBox = (CheckBox) findViewById(R.id.activity_calloutsuccess_cb_upkeep);
//        upkeepCheckBox.setOnCheckedChangeListener(this);
//        upkeepTimeEditText = (EditText) findViewById(R.id.activity_calloutsuccess_et_upkeeptime);
//        upkeepMilTextView = (EditText) findViewById(R.id.activity_calloutsucces_et_mil);
//
//        lostCustomerCheckBox = (CheckBox) findViewById(R.id.activity_calloutsucces_cb_lostcusotmer);
//        lostCustomerCheckBox.setOnCheckedChangeListener(this);
//        lostReasonEditText = (EditText) findViewById(R.id.activity_calloutsucces_et_lostreason);
//
//        inviteSuccessCheckBox = (CheckBox) findViewById(R.id.activity_calloutsuccess_cb_invitesuccess);
//        inviteSuccessCheckBox.setOnCheckedChangeListener(this);
//
//        comeingtimeTextView = (TextView) findViewById(R.id.activity_calloutsucces_tv_comeintime);
//        lastComeingTimeTextView = (TextView) findViewById(R.id.activity_calloutsuccess_tv_lastcometime);
//        comeingtimeTextView.setOnClickListener(this);
//        lastComeingTimeTextView.setOnClickListener(this);
//
//        //asEditText = (EditText) findViewById(R.id.activity_calloutsuccess_et_sa);
//
//        upkeepView = findViewById(R.id.activity_calloutsuccess_v_upkepptime);

        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = DatePickerDialog.newInstance(this,y,m,d);
        datePickerDialog.setAccentColor(Color.rgb(50, 126, 202));//327ECA

    }//end initview

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.activity_calloutsuccess_save://点击了保存
//                break;
//            case R.id.activity_calloutsuccess_iv_playIcon://点击了播放
//                break;
//            case R.id.activity_calloutsucces_tv_comeintime://邀约进厂时间
//                if(inviteSuccessCheckBox.isChecked())
//                    datePickerDialog.show(getFragmentManager(), "comeintime");
//                break;
//            case R.id.activity_calloutsuccess_tv_lastcometime://最迟进厂时间
//                if(inviteSuccessCheckBox.isChecked())
//                    datePickerDialog.show(getFragmentManager(), "lastcometime");
//                break;
        }
    }//end onClick

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
//            case R.id.activity_calloutsuccess_cb_invite://再次邀约
//                Log.i(LOG_TAG, "再次邀约 " + isChecked);
//                if(isChecked){
//                    datePickerDialog.show(getFragmentManager(),"invite");
//                    ((View)inviteDateEditText.getParent()).setBackgroundColor(CANUSE_COLOR);
//                }else{
//                    inviteDateEditText.setText("");
//                    ((View)inviteDateEditText.getParent()).setBackgroundColor(DONTUSER_COLOR);
//                }
//                break;
//            case R.id.activity_calloutsuccess_cb_upkeep://已保养
//                Log.i(LOG_TAG,"已保养 "+isChecked);
//                if(isChecked){
//                    datePickerDialog.show(getFragmentManager(), "upkeep");
//                    ((View)upkeepTimeEditText.getParent()).setBackgroundColor(CANUSE_COLOR);
//                    ((View)upkeepMilTextView.getParent()).setBackgroundColor(CANUSE_COLOR);
//                    upkeepMilTextView.setFocusable(true);
//                    upkeepMilTextView.setFocusableInTouchMode(true);
//                }else{
//                    upkeepTimeEditText.setText("");
//                    upkeepMilTextView.setText("");
//                    upkeepMilTextView.setFocusable(false);
//                    ((View)upkeepTimeEditText.getParent()).setBackgroundColor(DONTUSER_COLOR);
//                    ((View)upkeepMilTextView.getParent()).setBackgroundColor(DONTUSER_COLOR);
//                }
//                break;
//            case R.id.activity_calloutsucces_cb_lostcusotmer://流失客户
//                Log.i(LOG_TAG,"流失客户 "+isChecked);
//                if(isChecked){
//                    ((View)lostReasonEditText.getParent()).setBackgroundColor(CANUSE_COLOR);
//                    lostReasonEditText.setFocusable(true);
//                    lostReasonEditText.setFocusableInTouchMode(true);
//                }else{
//                    lostReasonEditText.setText("");
//                    lostReasonEditText.setFocusable(false);
//                    ((View)lostReasonEditText.getParent()).setBackgroundColor(DONTUSER_COLOR);
//                }
//                break;
//            case R.id.activity_calloutsuccess_cb_invitesuccess://成功邀约
//                Log.i(LOG_TAG, "成功邀约 " + isChecked);
//                if(isChecked){
//                    ((View)comeingtimeTextView.getParent()).setBackgroundColor(CANUSE_COLOR);
//                    ((View)lastComeingTimeTextView.getParent()).setBackgroundColor(CANUSE_COLOR);
//                    ((View)asEditText.getParent()).setBackgroundColor(CANUSE_COLOR);
//                    asEditText.setFocusable(true);
//                    asEditText.setFocusableInTouchMode(true);
//                }else{
//                    ((View)comeingtimeTextView.getParent()).setBackgroundColor(DONTUSER_COLOR);
//                    ((View)lastComeingTimeTextView.getParent()).setBackgroundColor(DONTUSER_COLOR);
//                    ((View)asEditText.getParent()).setBackgroundColor(DONTUSER_COLOR);
//                    comeingtimeTextView.setText("邀约进厂时间");
//                    lastComeingTimeTextView.setText("最迟进厂时间");
//                    asEditText.setText("");
//                    asEditText.setFocusable(false);
//                }
//                break;
        }
        clearCheckBox(buttonView,isChecked);
    }//end onCheckedChanged


    private void clearCheckBox(CompoundButton buttonView, boolean isChecked) {
        ArrayList<CheckBox> boxs = new ArrayList<>();
        boxs.add(inviteCheckBox);
        boxs.add(upkeepCheckBox);
        boxs.add(lostCustomerCheckBox);
        boxs.add(inviteSuccessCheckBox);
        for(int i=0;i<boxs.size();i++){
            boxs.get(i).setChecked(false);
        }
        for(int i=0;i<boxs.size();i++){
            if(boxs.get(i) == buttonView){
                if(isChecked){
                    boxs.get(i).setChecked(isChecked);
                }
            }
        }//end for
    }

    @Override
    public void onDateSet(DatePickerDialog view, final int year, final int monthOfYear, final int dayOfMonth){
        if(view.getTag().equals("invite")){
            inviteDateEditText.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
        }else if(view.getTag().equals("upkeep")){
            upkeepTimeEditText.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
        }else if(view.getTag().equals("comeintime")){//邀约进厂时间
            timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                    String result = year+"-"+(monthOfYear+1)+"-"+dayOfMonth+" "+hourOfDay+":"+minute;
                    comeingtimeTextView.setText(result);
                }
            },12,12,true);
            timePickerDialog.setAccentColor(Color.rgb(50, 126, 202));
            timePickerDialog.show(getFragmentManager(), "comeintime");
        }else if(view.getTag().equals("lastcometime")){//最迟进厂时间
            timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                    String result = year+"-"+(monthOfYear+1)+"-"+dayOfMonth+" "+hourOfDay+":"+minute;
                    lastComeingTimeTextView.setText(result);
                }
            },12,12,true);
            timePickerDialog.setAccentColor(Color.rgb(50, 126, 202));
            timePickerDialog.show(getFragmentManager(), "comeintime");
        }
    }//end onDateSet
}
