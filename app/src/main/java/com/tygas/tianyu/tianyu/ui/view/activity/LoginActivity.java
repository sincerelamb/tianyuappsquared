package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.data.SharedPreferencesDate;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.model.PID;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;
import com.tygas.tianyu.tianyu.utils.DbUtilsHelper;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.JsonParser;
import com.tygas.tianyu.tianyu.utils.ProgressDialogHelper;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

public class LoginActivity extends Activity implements CompoundButton.OnCheckedChangeListener, TextWatcher, View.OnClickListener {

    private EditText userName, password;
    private TextView btn_login, vercoede;
    private String userNameValue, passwordValue;
    private SharedPreferences sp;
    private SharedPreferences sp_f;
    private HttpUtils httputils;
    private CheckBox checkBox_rmb_pas;
    private CheckBox checkBox_auto_login;
    private boolean isSavePsw;
    private DbUtils dbUtils;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获得实例对象
        sp = this.getSharedPreferences(SharedPreferencesDate.SHAREDPREFERENCES_NAME_USERINFO, Context.MODE_PRIVATE);
        isSavePsw = sp.getBoolean(SharedPreferencesDate.ISRMBPSW, false);
        sp_f = this.getSharedPreferences(SharedPreferencesDate.SHAREDPREFERENCES_NAME_FIRST_PREF, MODE_PRIVATE);
        if (!sp_f.getBoolean(SharedPreferencesDate.ISSETURL, false)) {
            Intent intent = new Intent(LoginActivity.this, UrlSetActivity.class);
            LoginActivity.this.startActivity(intent);
        }

        //判断自动登陆
        if (sp.getBoolean(SharedPreferencesDate.AUTO_LOGIN, false) && sp.getBoolean(SharedPreferencesDate.ISLOGIN, false)) {
            Intent intent_main = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(intent_main);
            finish();
        }
        SystemBarUtils.setSystemBarColor(this, "#166EA4");
        setContentView(R.layout.activity_login);
        dbUtils = DbUtilsHelper.newInstance(this);
        httputils = HttpUtilsHelper.getInstance();
        //初始化控件
        initView();
    }


    private void initView() {
        vercoede = (TextView) findViewById(R.id.activity_login_versioncode);
        try {
            vercoede.setText("VersionName  " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        userName = (EditText) findViewById(R.id.et_zh);
        userName.addTextChangedListener(this);
        checkBox_rmb_pas = (CheckBox) findViewById(R.id.activity_login_cb_remenber_psw);
        checkBox_rmb_pas.setChecked(isSavePsw);
        checkBox_rmb_pas.setOnCheckedChangeListener(this);
        checkBox_auto_login = (CheckBox) findViewById(R.id.activity_login_cb_automatic_login);
        checkBox_auto_login.setChecked(sp.getBoolean(SharedPreferencesDate.AUTO_LOGIN, false));
        checkBox_auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sp.edit().putBoolean(SharedPreferencesDate.AUTO_LOGIN, isChecked).commit();
            }
        });

        password = (EditText) findViewById(R.id.et_mima);
        btn_login = (TextView) findViewById(R.id.btn_login);
        if (isSavePsw) {
            userName.setText(sp.getString(SharedPreferencesDate.USER_NAME, ""));
            password.setText(sp.getString(SharedPreferencesDate.PASSWORD, ""));
        }
//        userName.setText("admin");
//        userName.setFocusable(false);
//        userName.setFocusableInTouchMode(false);
//
//        password.setText("niceeasy");
//        password.setFocusable(false);
//        password.setFocusableInTouchMode(false);

        // 登录监听事件
        btn_login.setOnClickListener(this);

    }

    //设置检验账号密码方法
    private boolean verificationAccountPassword(String userNameValue, String passwordValue) {
        if (TextUtils.isEmpty(userNameValue)) {
            Toast.makeText(LoginActivity.this, "账号不能为空", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(passwordValue)) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void setUrl(View view) {
        Intent intent = new Intent(LoginActivity.this, UrlSetActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isSavePsw = isChecked;
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(SharedPreferencesDate.ISRMBPSW, isChecked);
        edit.commit();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        password.setText("");
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        userNameValue = userName.getText().toString();
        passwordValue = password.getText().toString();
        if (verificationAccountPassword(userNameValue, passwordValue)) {
            //使软键盘消失
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            ProgressDialogHelper.showProgressDialog(LoginActivity.this, "正在登陆...");
            Log.i("LOGIN_URL", UrlData.LOGIN_URL);
            Log.i("APP IP", UrlData.APP_IP);
            httputils.send(HttpRequest.HttpMethod.POST, UrlData.LOGIN_URL, XutilsRequest.getLoginParams(userNameValue, passwordValue), new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> objectResponseInfo) {

                    Log.i("Loginresult", objectResponseInfo.result);
                    Bundle bundle = JsonParser.loginParser(objectResponseInfo.result);
                    User user = (User) (bundle.getSerializable("user"));
                    String projectCode = bundle.getString("ProjectCode");

                    Log.d("user", user.toString());
                    ((MyAppCollection) getApplicationContext()).setUser(user);
                    if (user != null &&
                            user.getIsLoginSuccess()
                            && projectCode != null
                            ) {

                        if (projectCode.equals(XutilsRequest.PC)) {
                            try {
                                dbUtils.deleteAll(User.class);
                                dbUtils.deleteAll(PID.class);
                                dbUtils.deleteAll(UserPtInfoModel.class);
                                dbUtils.save(user);
                                dbUtils.saveAll(user.getList_Brand());
                                dbUtils.saveAll(user.getList_CarSeries());
                                dbUtils.saveAll(user.getList_Channel());
                                dbUtils.saveAll(user.getList_CheckColorList());
                                dbUtils.saveAll(user.getList_FailType());
                                dbUtils.saveAll(user.getList_Focus());
                                dbUtils.saveAll(user.getList_SourceChannel());
                                dbUtils.saveAll(user.getList_Useage());
                                dbUtils.saveAll(user.getList_PID());
                                dbUtils.saveAll(user.getList_CustomerLevel());
                                dbUtils.saveAll(user.getList_CarModels());
                                dbUtils.saveAll(user.getList_OtherCarSeries());
                                dbUtils.saveAll(user.getList_UserNum());
                                dbUtils.saveAll(user.getList_LevelNotDefeat());
                                dbUtils.saveAll(user.getList_LevelNotClinch());
                                dbUtils.saveAll(user.getList_LevelAll());
                                dbUtils.saveAll(user.getList_SubscribeType());
                                dbUtils.saveAll(user.getList_LostResult());
                                Log.d("_LostResult", user.getList_LostResult().toString());

                                ((MyAppCollection) getApplicationContext()).setUser(user);
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                            SharedPreferences.Editor editor = sp.edit();
                            //存入用户设置相关数据
                            editor.putString(SharedPreferencesDate.USER_NAME, userNameValue);
                            editor.putString(SharedPreferencesDate.PASSWORD, passwordValue);
                            editor.putString(SharedPreferencesDate.USER_REAL_NAME, user.getEmpName());
                            editor.putBoolean(SharedPreferencesDate.ISLOGIN, true);
                            editor.commit();
                            //跳转至主界面
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "店址编码错误", Toast.LENGTH_LONG).show();
                        }

                        } else {
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
                        }
                        ProgressDialogHelper.dismissProgressDialog();
                    }

                    @Override
                    public void onFailure (HttpException e, String s){
                        Toast.makeText(LoginActivity.this, "网络请求出错，请检查" +
                                "网络", Toast.LENGTH_SHORT).show();
                        Log.i("Loginresult", "ss" + e);
                        ProgressDialogHelper.dismissProgressDialog();
                    }
                }

                );
            }
        }

        @Override
        protected void onDestroy () {
            super.onDestroy();
        }
    }