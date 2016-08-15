package com.tygas.tianyu.tianyu.ui.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.adapter.LevelAdapter;
import com.tygas.tianyu.tianyu.ui.adapter.SalesConsltantAutoCompeletAdapter;
import com.tygas.tianyu.tianyu.ui.model.SalesConsltant;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;
import com.tygas.tianyu.tianyu.ui.view.customview.FocuGirdView;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by SJTY_YX on 2016/1/19.
 * 潜客筛选列表
 */
public class PtCustomersFilterActivity extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private ImageView backImageView;//返回按钮
    private TextView titleTextView;//标题的TextView
    private ImageView confirmTextView;//确定按钮
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_empName;

    private EditText nameEditText; //姓名
    private EditText phoneEditText;//电话

    private AutoCompleteTextView empNameEditText;//员工姓名
    private SalesConsltantAutoCompeletAdapter salesConsltantAutoCompeletAdapter;//自动补全的销售顾问的适配器
    private ArrayList<SalesConsltant> salesConsltants;//自动补全的销售顾问
    private String saleTemp = "";//展示保存asAutoCompelteTextView里面的数据
    private boolean isUploadSalesConsltant = false;//是否在更新销售顾问


    private LinearLayout isExpireRadioGroup;//是否逾期的夫布局
    private CheckBox yTimeCheckBox;//逾期
    private CheckBox nTimeCheckBox;//未逾期
    private TextView levelTextView;//客户等级
    private TextView beginTimeTextView;//应回访时间
    private View clearLastButton;//清空开始时间
    private View clearNextButton;//清空结束时间

    private User user;

    //private RelativeLayout levelRelativeLayout;//等级的布局
    private FocuGirdView levleFocuGirdView;
    private List<UserPtInfoModel> levlData;
    private LevelAdapter levelAdapter;

    private RelativeLayout nextTimeRelativeLayout;
    private RelativeLayout lastTimeRelativeLayout;
    private TextView nextTimeTextView;
    private TextView lastTimeTextView;
    private DatePickerDialog dpd;

    private String begintime;
    private String endtime;

    private static final int CHOISE_COLOR = Color.rgb(161, 225, 156);//0xa1e19c; //等级默认的颜色
    private static final int DEFAULT_COLOR = Color.rgb(244, 254, 192);//0xf4fec0; //等级选中的颜色
    private static final String LOG_TAG = "PtCustomersFilterActivity";

    public static final int TYPE_PTCUSTOMER = 0x0001;//筛选 潜客
    public static final int TYPE_CLUE = 0x0002;//筛选 线索

    private int type = TYPE_PTCUSTOMER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_ptcustomersfilter);
        MyAppCollection myAppCollection = (MyAppCollection) getApplicationContext();
        if (myAppCollection.getUser() != null) {
            user = myAppCollection.getUser();
        } else {
            finish();
        }
        if (getIntent().getIntExtra("type", 0) == 0) {
            finish();
        }
        type = getIntent().getIntExtra("type", 0);
        begintime = getIntent().getStringExtra("begintime");
        endtime = getIntent().getStringExtra("endtime");

        /**
         intent.putExtra("level", level);
         intent.putExtra("name", name);
         intent.putExtra("phone",phone);
         intent.putExtra("cusempname",cusempname);
         */

        initView();
        initPage();
    }

    private void initPage() {
        switch (type) {
            case TYPE_PTCUSTOMER:
                titleTextView.setText("潜客回访筛选");
                tv_empName.setText("客户姓名");
                tv_phone.setText("客户电话");
                tv_empName.setText("销售顾问");
                levelTextView.setText("客户等级");
                beginTimeTextView.setText("应回访时间从");
                break;
            case TYPE_CLUE:
                titleTextView.setText("线索跟踪查询");
                tv_empName.setText("线索姓名");
                tv_phone.setText("线索电话");
                tv_empName.setText("电销员");
                levelTextView.setText("线索等级");
                beginTimeTextView.setText("应回访时间从");
                break;
        }
    }//end initPage

    @Override
    protected void onResume() {
        super.onResume();

    }

    //设置是否过期
    private void setIsExpire(boolean flag) {
        if (flag) {//逾期
            if (yTimeCheckBox.isChecked()) {
                yTimeCheckBox.setChecked(true);
            } else {
                yTimeCheckBox.setChecked(false);
            }
            nTimeCheckBox.setChecked(false);
        } else {
            if (nTimeCheckBox.isChecked()) {
                nTimeCheckBox.setChecked(true);
            } else {
                nTimeCheckBox.setChecked(false);
            }
            yTimeCheckBox.setChecked(false);
        }
    }

    private void initView() {

        tv_name = (TextView) findViewById(R.id.activity_ptcustomersfilter_tv_name);
        tv_phone = (TextView) findViewById(R.id.activity_ptcustomersfilter_tv_phone);
        tv_empName = (TextView) findViewById(R.id.activity_ptcustomersfilter_tv_empName);


        titleTextView = (TextView) findViewById(R.id.activity_ptcustomersfilter_tv_title);
        isExpireRadioGroup = (LinearLayout) findViewById(R.id.activity_ptcustomersfilter_rg_isexpire);
        yTimeCheckBox = (CheckBox) findViewById(R.id.activity_ptcustomersfilter_rb_yes);
        nTimeCheckBox = (CheckBox) findViewById(R.id.activity_ptcustomersfilter_rb_no);
        yTimeCheckBox.setOnClickListener(this);
        nTimeCheckBox.setOnClickListener(this);
        levelTextView = (TextView) findViewById(R.id.activity_ptcustomersfilter_tv_level);
        beginTimeTextView = (TextView) findViewById(R.id.activity_ptcustomersfilter_tv_begintime);

        if ("false".equals(getIntent().getStringExtra("isExpiry")) || "true".equals(getIntent().getStringExtra("isExpiry"))) {
            if (Boolean.valueOf(getIntent().getStringExtra("isExpiry"))) {
                yTimeCheckBox.setChecked(true);
                nTimeCheckBox.setChecked(false);
            } else {
                nTimeCheckBox.setChecked(true);
                yTimeCheckBox.setChecked(false);
            }
        }

        clearLastButton = findViewById(R.id.activity_ptcustomers_btn_clearlasttime);
        clearNextButton = findViewById(R.id.activity_ptcustomers_btn_clearnexttime);

        clearLastButton.setOnClickListener(this);
        clearNextButton.setOnClickListener(this);

        backImageView = (ImageView) findViewById(R.id.activtiy_ptcustomersfilter_back);
        backImageView.setOnClickListener(this);
        levleFocuGirdView = (FocuGirdView) findViewById(R.id.activity_ptcustomersfilter_fgv_level);
        levlData = user.getList_LevelAll();
        Log.d("levlData", levlData.toString());
//        for(int i=0;i<levlData.size();i++){
//            if("D".equals(levlData.get(i).getName()) || "F".equals(levlData.get(i).getName())){
//                levlData.remove(i);
//                i--;
//            }
//        }
        levelAdapter = new LevelAdapter(levlData);
        levleFocuGirdView.setAdapter(levelAdapter);
        levelAdapter.setLevel(getIntent().getStringExtra("level"));
        confirmTextView = (ImageView) findViewById(R.id.activity_ptcustomersfilter_tv_confirm);
        confirmTextView.setOnClickListener(this);
        nameEditText = (EditText) findViewById(R.id.activity_ptcustomersfilter_et_name);
        phoneEditText = (EditText) findViewById(R.id.activity_ptcustomersfilter_et_phone);


        salesConsltants = new ArrayList<>();
        SalesConsltant salesConsltant = new SalesConsltant();
        salesConsltant.setId(user.getEmpId());
        salesConsltant.setName(user.getEmpName());
        salesConsltants.add(salesConsltant);
        empNameEditText = (AutoCompleteTextView) findViewById(R.id.activity_ptcustomersfilter_et_empName);
        empNameEditText.setText(getIntent().getStringExtra("cusempname"));
        salesConsltantAutoCompeletAdapter = new SalesConsltantAutoCompeletAdapter(salesConsltants);
        empNameEditText.setAdapter(salesConsltantAutoCompeletAdapter);
        empNameEditText.addTextChangedListener(new TextWatcher() {
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
                    saleTemp = empNameEditText.getText().toString().trim();
                    isUploadSalesConsltant = true;
                    uploadSalesInfo(s);
                }
            }
        });


        nameEditText.setText(getIntent().getStringExtra("name"));
        phoneEditText.setText(getIntent().getStringExtra("phone"));
        //levelRelativeLayout = (RelativeLayout) findViewById(R.id.activity_ptcustomersfilter_rl_level);
    /*setLevelColor();
    for(int i=0;i<levelRelativeLayout.getChildCount();i++){
        levelRelativeLayout.getChildAt(i).setOnClickListener(this);
    }*/

        nextTimeRelativeLayout = (RelativeLayout) findViewById(R.id.activity_ptcustomersfilter_rl_nexttime);
        lastTimeRelativeLayout = (RelativeLayout) findViewById(R.id.activity_ptcustomersfilter_rl_lasttime);
        nextTimeTextView = (TextView) nextTimeRelativeLayout.getChildAt(1);
        lastTimeTextView = (TextView) lastTimeRelativeLayout.getChildAt(1);
        Calendar now = Calendar.getInstance();
        String nowStr = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        if (begintime == null) {
            lastTimeTextView.setText(nowStr);
        } else {
            lastTimeTextView.setText(begintime);
        }
        if (endtime == null) {
            nextTimeTextView.setText(nowStr);
        } else {
            nextTimeTextView.setText(endtime);
        }
        lastTimeRelativeLayout.setOnClickListener(this);
        nextTimeRelativeLayout.setOnClickListener(this);
        dpd = DatePickerDialog.newInstance(
                PtCustomersFilterActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(Color.rgb(50, 126, 202));//327ECA
    }//end initView


    private String getNowTime() {
        Calendar now = Calendar.getInstance();
        String nowStr = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        return nowStr;
    }

    //等级的点击监听
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.activity_ptcustomersfilter_rl_lasttime) {
            //显示日期
            if (!dpd.isAdded()) {
                dpd.show(getFragmentManager(), "lasttime");
            }

        } else if (id == R.id.activity_ptcustomersfilter_rl_nexttime) {
            if (!dpd.isAdded()) {
                dpd.show(getFragmentManager(), "nexttime");
            }
        } else if (id == R.id.activity_ptcustomersfilter_tv_confirm) {
            //点击了确定按钮
            Intent intent = new Intent();
            if (!TextUtils.isEmpty(nameEditText.getText().toString())) {
                intent.putExtra("name", nameEditText.getText().toString());
            } else {
                intent.putExtra("name", "");
            }
            if (!TextUtils.isEmpty(phoneEditText.getText().toString())) {
                intent.putExtra("phone", phoneEditText.getText().toString());
            } else {
                intent.putExtra("phone", "");
            }
            if (!TextUtils.isEmpty(empNameEditText.getText().toString())) {
                intent.putExtra("cusempname", empNameEditText.getText().toString());
            } else {
                intent.putExtra("cusempname", "");
            }
            intent.putExtra("isExpiry", getIsExpire());
            intent.putExtra("level", levelAdapter.getCheckLevel());
            intent.putExtra("fromtime", lastTimeTextView.getText().toString());
            intent.putExtra("totime", nextTimeTextView.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        } else if (id == R.id.activtiy_ptcustomersfilter_back) {//点击了返回按钮
            finish();
        } else if (id == R.id.activity_ptcustomers_btn_clearlasttime) {//清楚开始时间
            lastTimeTextView.setText("");
        } else if (id == R.id.activity_ptcustomers_btn_clearnexttime) {//清楚结束时间
            nextTimeTextView.setText("");
        } else if (id == R.id.activity_ptcustomersfilter_rb_yes) {
            setIsExpire(true);
        } else if (id == R.id.activity_ptcustomersfilter_rb_no) {
            setIsExpire(false);
        }
    }//end onClick


    private String getIsExpire() {
        if (yTimeCheckBox.isChecked()) {
            return "true";
        } else if (nTimeCheckBox.isChecked()) {
            return "false";
        } else {
            return "";
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
            empNameEditText.setText(empNameEditText.getText());
            empNameEditText.setSelection(empNameEditText.getText().length());

        }
    }//end parseSalesResult

    private SalesConsltant parseSale(JSONObject jsonObject) throws JSONException {
        SalesConsltant salesConsltant = new SalesConsltant();
        salesConsltant.setId(jsonObject.getString("ID"));
        salesConsltant.setName(jsonObject.getString("Name"));
        return salesConsltant;
    }//end saleaConsltant

}
