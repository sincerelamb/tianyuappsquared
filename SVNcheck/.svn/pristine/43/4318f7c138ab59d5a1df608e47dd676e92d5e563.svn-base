package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.adapter.SalesConsltantAutoCompeletAdapter;
import com.tygas.tianyu.tianyu.ui.model.CpCustomer;
import com.tygas.tianyu.tianyu.ui.model.SalesConsltant;
import com.tygas.tianyu.tianyu.ui.model.SearchCpCustomer;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.utils.DbUtilsHelper;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.MyDialogHelper;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class CpCustomerScreenActivity extends Activity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private ImageView backImageView;//返回按钮

    private ImageView confirmTextView;//确定按钮
    private EditText nameEditText; //姓名
    private EditText phoneEditText;//电话
    private AutoCompleteTextView guwenText;
    private SalesConsltantAutoCompeletAdapter salesConsltantAutoCompeletAdapter;//自动补全的销售顾问的适配器
    private ArrayList<SalesConsltant> salesConsltants;//自动补全的销售顾问
    private String saleTemp = "";//展示保存asAutoCompelteTextView里面的数据
    private boolean isUploadSalesConsltant = false;//是否在更新销售顾问

    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_guwen;

    private User user;

    private RadioGroup rg_IsSuppleState;
    private RadioButton rb_all;
    private RadioButton rb_yes;
    private RadioButton rb_no;


    private RelativeLayout nextTimeRelativeLayout;
    private RelativeLayout lastTimeRelativeLayout;
    private RelativeLayout rl_isSuppleState;
    private TextView nextTimeTextView;
    private TextView lastTimeTextView;
    private TextView tv_zhizuiqian;
    private TextView tv_zhizuihou;

    private DatePickerDialog dpd;
    private String string_IsSuppleState;
    private DbUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_cp_customer_screen);
        user = ((MyAppCollection) getApplicationContext()).getUser();
        initView();
        //  initData();

    }

    private void initData() {
//        dbUtils = DbUtilsHelper.newInstance(this);
        try {
            SearchCpCustomer first = dbUtils.findFirst(SearchCpCustomer.class);
            if (first != null) {
                if (!TextUtils.isEmpty(first.getCustomerName())) {
                    nameEditText.setText(first.getCustomerName());
                }

                if (!TextUtils.isEmpty(first.getCustomerPhone())) {
                    phoneEditText.setText(first.getCustomerPhone());
                }

                if (!TextUtils.isEmpty(first.getEmpNmae())) {
                    guwenText.setText(first.getEmpNmae());
                }

                if (!TextUtils.isEmpty(first.getFromeTime())) {
                    lastTimeTextView.setText(first.getFromeTime());
                }

                if (!TextUtils.isEmpty(first.getToTime())) {
                    nextTimeTextView.setText(first.getToTime());
                }

                if (first.getIsSuppleState() != null) {
                    switch (first.getIsSuppleState()) {
                        case "":
                            rb_all.setChecked(true);
                            break;
                        case "1":
                            rb_yes.setChecked(true);
                            break;
                        case "0":
                            rb_no.setChecked(true);
                            break;
                    }
                }
            } else {
                nameEditText.setText("");
                phoneEditText.setText("");
                guwenText.setText("");
                Calendar now = Calendar.getInstance();
                String nowStr = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
                lastTimeTextView.setText(nowStr);
                nextTimeTextView.setText(nowStr);
                rb_all.setChecked(true);
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void initView() {

        backImageView = (ImageView) findViewById(R.id.activtiy_cpcustomersfilter_back);
        backImageView.setOnClickListener(this);

        confirmTextView = (ImageView) findViewById(R.id.activity_cpcustomersfilter_tv_confirm);
        confirmTextView.setOnClickListener(this);

        tv_name = (TextView) findViewById(R.id.activity_cpcustomersfilter_tv_name);
        nameEditText = (EditText) findViewById(R.id.activity_cpcustomersfilter_et_name);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("name"))) {
            nameEditText.setText(getIntent().getStringExtra("name"));
        }
        tv_phone = (TextView) findViewById(R.id.activity_cpcustomersfilter_tv_phone);
        phoneEditText = (EditText) findViewById(R.id.activity_cpcustomersfilter_et_phone);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("phone"))) {
            phoneEditText.setText(getIntent().getStringExtra("phone"));
        }


        salesConsltants = new ArrayList<>();
        SalesConsltant salesConsltant = new SalesConsltant();
        salesConsltant.setId(user.getEmpId());
        salesConsltant.setName(user.getEmpName());
        salesConsltants.add(salesConsltant);
        guwenText = (AutoCompleteTextView) findViewById(R.id.activity_cpcustomersfilter_et_xiaoshouguwen);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("guwen"))) {
            guwenText.setText(getIntent().getStringExtra("guwen"));
        }
        salesConsltantAutoCompeletAdapter = new SalesConsltantAutoCompeletAdapter(salesConsltants);
        guwenText.setAdapter(salesConsltantAutoCompeletAdapter);
        guwenText.addTextChangedListener(new TextWatcher() {
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
                    saleTemp = guwenText.getText().toString().trim();
                    isUploadSalesConsltant = true;
                    uploadSalesInfo(s);
                }
            }
        });


        rl_isSuppleState = (RelativeLayout) findViewById(R.id.activity_cpcustomersfilter_rl_issupplestate);
        rg_IsSuppleState = (RadioGroup) findViewById(R.id.activity_cpcustomersfilter_rg);
        rg_IsSuppleState.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.activity_cpcustomersfilter_rb_all:
                        string_IsSuppleState = "";
                        break;
                    case R.id.activity_cpcustomersfilter_rb_yes:
                        string_IsSuppleState = "是";
                        break;
                    case R.id.activity_cpcustomersfilter_rb_no:
                        string_IsSuppleState = "否";
                        break;
                }
            }
        });
        rb_all = (RadioButton) findViewById(R.id.activity_cpcustomersfilter_rb_all);
        rb_yes = (RadioButton) findViewById(R.id.activity_cpcustomersfilter_rb_yes);
        rb_no = (RadioButton) findViewById(R.id.activity_cpcustomersfilter_rb_no);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("issupplestate"))) {
            if ("否".equals(getIntent().getStringExtra("issupplestate"))) {
                rb_no.setChecked(true);
            } else if ("是".equals(getIntent().getStringExtra("issupplestate"))) {
                rb_yes.setChecked(true);
            }
        } else {
            rb_all.setChecked(true);
        }


        nextTimeRelativeLayout = (RelativeLayout) findViewById(R.id.activity_cpcustomersfilter_rl_nexttime);
        lastTimeRelativeLayout = (RelativeLayout) findViewById(R.id.activity_cpcustomersfilter_rl_lasttime);

        tv_zhizuiqian = (TextView) findViewById(R.id.activity_cpcustomersfilter_tv_lasttime_zhikong);
        tv_zhizuihou = (TextView) findViewById(R.id.activity_cpcustomersfilter_tv_nexttime_zhikong);
        lastTimeTextView = (TextView) findViewById(R.id.activity_cpcustomersfilter_tv_lasttime);
        nextTimeTextView = (TextView) findViewById(R.id.activity_cpcustomersfilter_tv_nexttime);

        Calendar now = Calendar.getInstance();
        String nowStr = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);

        nextTimeTextView.setText(getIntent().getStringExtra("totime"));
        lastTimeTextView.setText(getIntent().getStringExtra("fromtime"));

        lastTimeTextView.setOnClickListener(this);
        nextTimeTextView.setOnClickListener(this);
        tv_zhizuiqian.setOnClickListener(this);
        tv_zhizuihou.setOnClickListener(this);
        dpd = DatePickerDialog.newInstance(
                CpCustomerScreenActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(Color.rgb(50, 126, 202));//327ECA


    }//end initView


    //等级的点击监听
    @Override
    public void onClick(View v) {
        int id = v.getId();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        switch (id) {
            case R.id.activity_cpcustomersfilter_tv_lasttime:
                if (!dpd.isAdded()) {
                    dpd.show(getFragmentManager(), "lasttime");
                }

                break;
            case R.id.activity_cpcustomersfilter_tv_nexttime:
                if (!dpd.isAdded()) {
                    dpd.show(getFragmentManager(), "nexttime");
                }
                break;

            case R.id.activity_cpcustomersfilter_tv_confirm:
                //点击了确定按钮
                Intent intent = new Intent();
                //    SearchCpCustomer cpCustomer = new SearchCpCustomer();

//                if (!TextUtils.isEmpty(nameEditText.getText().toString())) {
                //   cpCustomer.setCustomerName(nameEditText.getText().toString());
                intent.putExtra("name", nameEditText.getText().toString());
//                }
//                if (!TextUtils.isEmpty(phoneEditText.getText().toString())) {
                //    cpCustomer.setCustomerPhone(phoneEditText.getText().toString());
                intent.putExtra("phone", phoneEditText.getText().toString());
//                }

                intent.putExtra("fromtime", lastTimeTextView.getText().toString());
                //     cpCustomer.setFromeTime(lastTimeTextView.getText().toString());
                intent.putExtra("totime", nextTimeTextView.getText().toString());
                //   cpCustomer.setToTime(nextTimeTextView.getText().toString());

                intent.putExtra("guwen", guwenText.getText().toString());
                //  cpCustomer.setEmpNmae(guwenText.getText().toString());
                intent.putExtra("issupplestate", string_IsSuppleState);
                //   cpCustomer.setIsSuppleState(string_IsSuppleState);
//                try {
//                    dbUtils.deleteAll(SearchCpCustomer.class);
//                    dbUtils.save(cpCustomer);
//                } catch (DbException e) {
//                    e.printStackTrace();
//                }

                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.activity_cpcustomersfilter_tv_lasttime_zhikong:
                lastTimeTextView.setText("");
                break;

            case R.id.activity_cpcustomersfilter_tv_nexttime_zhikong:
                nextTimeTextView.setText("");
                break;

            case R.id.activtiy_cpcustomersfilter_back:
                finish();
                break;
        }
    }//end onClick


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String time = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        String tag = view.getTag();
        if ("lasttime".equals(tag)) {
            lastTimeTextView.setText(time);
        } else if ("nexttime".equals(tag)) {
            nextTimeTextView.setText(time);
        }
    }//end onDateSet


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
            if (!object.isNull("LikeNameList")) {
                JSONArray arr = object.getJSONArray("LikeNameList");
                if (salesConsltants == null) {
                    salesConsltants = new ArrayList<>();
                }
                salesConsltants.clear();
                for (int i = 0; i < arr.length(); i++) {
                    salesConsltants.add(parseSale(arr.getJSONObject(i)));
                }
                salesConsltantAutoCompeletAdapter.setmUnfilteredData(salesConsltants);
            }

            guwenText.setText(guwenText.getText());
            guwenText.setSelection(guwenText.getText().length());

        }
    }//end parseSalesResult

    private SalesConsltant parseSale(JSONObject jsonObject) throws JSONException {
        SalesConsltant salesConsltant = new SalesConsltant();
        salesConsltant.setId(jsonObject.getString("ID"));
        salesConsltant.setName(jsonObject.getString("Name"));
        return salesConsltant;
    }//end saleaConsltant

}
