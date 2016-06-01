package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class TpScreenActivity extends Activity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private ImageView backImageView;//返回按钮

    private ImageView confirmTextView;//确定按钮
    private EditText nameEditText; //姓名
    private EditText phoneEditText;//电话
    private EditText guwenText;
    private RadioGroup rg_Isoverdue;
    private RadioButton rb_all;
    private RadioButton rb_yes;
    private RadioButton rb_no;


    private RelativeLayout nextTimeRelativeLayout;
    private RelativeLayout lastTimeRelativeLayout;
    private RelativeLayout rl_isoverdue;
    private TextView nextTimeTextView;
    private TextView lastTimeTextView;
    private TextView tv_zhizuiqian;
    private TextView tv_zhizuihou;
    private DatePickerDialog dpd;
    private String string_Isoverdue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_tp_screen);
        initView();
       // initData();
    }

    private void initView() {

        backImageView = (ImageView) findViewById(R.id.activtiy_tpcustomersfilter_back);
        backImageView.setOnClickListener(this);

        confirmTextView = (ImageView) findViewById(R.id.activity_tpcustomersfilter_tv_confirm);
        confirmTextView.setOnClickListener(this);

        nameEditText = (EditText) findViewById(R.id.activity_tpcustomersfilter_et_name);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("name"))) {
            nameEditText.setText(getIntent().getStringExtra("name"));
        }
        phoneEditText = (EditText) findViewById(R.id.activity_tpcustomersfilter_et_phone);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("phone"))) {
            phoneEditText.setText(getIntent().getStringExtra("phone"));
        }

        guwenText = (EditText) findViewById(R.id.activity_tpcustomersfilter_et_fuwuguwen);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("guwen"))) {
            guwenText.setText(getIntent().getStringExtra("guwen"));
        }

        rl_isoverdue= (RelativeLayout) findViewById(R.id.activity_tpcustomersfilter_rl_isoverdue);
        rg_Isoverdue = (RadioGroup) findViewById(R.id.activity_tpcustomersfilter_rg);
        rg_Isoverdue.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.activity_tpcustomersfilter_rb_all:
                        string_Isoverdue = "";
                        break;
                    case R.id.activity_tpcustomersfilter_rb_yes:
                        string_Isoverdue = "逾期";
                        break;
                    case R.id.activity_tpcustomersfilter_rb_no:
                        string_Isoverdue = "未逾期";
                        break;

                }
            }
        });

        rb_all = (RadioButton) findViewById(R.id.activity_tpcustomersfilter_rb_all);
        rb_yes = (RadioButton) findViewById(R.id.activity_tpcustomersfilter_rb_yes);
        rb_no = (RadioButton) findViewById(R.id.activity_tpcustomersfilter_rb_no);
        if (null != getIntent().getStringExtra("isoverdue")) {
            switch (getIntent().getStringExtra("isoverdue")) {
                case "逾期":
                    rb_yes.setChecked(true);
                    break;
                case "未逾期":
                    rb_no.setChecked(true);
                    break;
                case "":
                    rb_all.setChecked(true);
                    break;

            }
        }


        nextTimeRelativeLayout = (RelativeLayout) findViewById(R.id.activity_tpcustomersfilter_rl_nexttime);
        lastTimeRelativeLayout = (RelativeLayout) findViewById(R.id.activity_tpcustomersfilter_rl_lasttime);

        tv_zhizuiqian = (TextView) findViewById(R.id.activity_tpcustomersfilter_tv_lasttime_zhikong);
        tv_zhizuihou = (TextView) findViewById(R.id.activity_tpcustomersfilter_tv_nexttime_zhikong);
        lastTimeTextView = (TextView) findViewById(R.id.activity_tpcustomersfilter_tv_lasttime);
        nextTimeTextView = (TextView) findViewById(R.id.activity_tpcustomersfilter_tv_nexttime);

        Calendar now = Calendar.getInstance();
        String nowStr = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        nextTimeTextView.setText(getIntent().getStringExtra("totime"));
        lastTimeTextView.setText(getIntent().getStringExtra("fromtime"));


        lastTimeTextView.setOnClickListener(this);
        nextTimeTextView.setOnClickListener(this);
        tv_zhizuiqian.setOnClickListener(this);
        tv_zhizuihou.setOnClickListener(this);

        dpd = DatePickerDialog.newInstance(
                TpScreenActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(Color.rgb(50, 126, 202));//327ECA


    }//end initView

    private void initData(){
        nameEditText.setText("");
        phoneEditText.setText("");
        guwenText.setText("");
        Calendar now = Calendar.getInstance();
        String nowStr = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        lastTimeTextView.setText(nowStr);
        nextTimeTextView.setText(nowStr);
        rb_all.setChecked(true);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        switch (id) {
            case R.id.activity_tpcustomersfilter_tv_lasttime:
                dpd.show(getFragmentManager(), "lasttime");
                break;
            case R.id.activity_tpcustomersfilter_tv_nexttime:
                dpd.show(getFragmentManager(), "nexttime");
                break;

            case R.id.activity_tpcustomersfilter_tv_confirm:
                //点击了确定按钮
                Intent intent = new Intent();


                if (!TextUtils.isEmpty(nameEditText.getText().toString())) {

                    intent.putExtra("name", nameEditText.getText().toString());
                }
                if (!TextUtils.isEmpty(phoneEditText.getText().toString())) {

                    intent.putExtra("phone", phoneEditText.getText().toString());
                }

                intent.putExtra("fromtime", lastTimeTextView.getText().toString());

                intent.putExtra("totime", nextTimeTextView.getText().toString());


                intent.putExtra("guwen", guwenText.getText().toString());

                intent.putExtra("isoverdue", string_Isoverdue);

//                try {
//                    dbUtils.deleteAll(SearchCpCustomer.class);
//                    dbUtils.save(cpCustomer);
//                } catch (DbException e) {
//                    e.printStackTrace();
//                }

                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.activity_tpcustomersfilter_tv_lasttime_zhikong:
                lastTimeTextView.setText("");
                break;

            case R.id.activity_tpcustomersfilter_tv_nexttime_zhikong:
                nextTimeTextView.setText("");
                break;

            case R.id.activtiy_tpcustomersfilter_back:
                finish();
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String time = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        String tag = view.getTag();
        if ("lasttime".equals(tag)) {
            lastTimeTextView.setText(time);
        } else if ("nexttime".equals(tag)) {
            nextTimeTextView.setText(time);
        }
    }
}
