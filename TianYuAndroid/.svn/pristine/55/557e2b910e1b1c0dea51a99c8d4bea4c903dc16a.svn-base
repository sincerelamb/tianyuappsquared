package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.data.SharedPreferencesDate;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

public class UrlSetActivity extends Activity {

    private ImageView iv_back;
    private TextView tv_oldurl;
    private TextView tv_save;
    private EditText ed_ip;
    private EditText ed_duankou;
    private EditText ed_interface;
    private EditText ed_shop_number;

    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferences_f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_url_set);
        sharedPreferences = getSharedPreferences(SharedPreferencesDate.SHAREDPREFERENCES_NAME_SET, MODE_PRIVATE);
        sharedPreferences_f = getSharedPreferences(SharedPreferencesDate.SHAREDPREFERENCES_NAME_FIRST_PREF, MODE_PRIVATE);
        initView();
    }


    private void initView() {
        iv_back = (ImageView) findViewById(R.id.activity_urlset_iv_back);
        tv_oldurl = (TextView) findViewById(R.id.activity_urlset_old_url);
//        if (!sharedPreferences_f.getBoolean(SharedPreferencesDate.ISSETURL, false)) {
//            tv_oldurl.setText("未设置");
//        } else {
        tv_oldurl.setText(UrlData.APP_IP);
//        }

        tv_save = (TextView) findViewById(R.id.activity_urlset_tv_save);
        ed_ip = (EditText) findViewById(R.id.activity_urlset_ed_ip);
        ed_duankou = (EditText) findViewById(R.id.activity_urlset_ed_duankou);
        ed_interface = (EditText) findViewById(R.id.activity_urlset_ed_interface);
        ed_shop_number = (EditText) findViewById(R.id.activity_urlset_ed_shopnumber);
        ed_shop_number.setText(XutilsRequest.PC);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_urlset_iv_back:
                finish();
                break;
            case R.id.activity_urlset_tv_save:
                String ip = ed_ip.getText().toString();
                String duankou = ed_duankou.getText().toString();
                String textinterface = ed_interface.getText().toString();
                String shopnumber = ed_shop_number.getText().toString();
                SharedPreferences.Editor edit = sharedPreferences.edit();
                if (!TextUtils.isEmpty(ip) && !TextUtils.isEmpty(textinterface)) {
                    if (!TextUtils.isEmpty(shopnumber)) {
                        edit.putString(SharedPreferencesDate.SHOP_CODE, shopnumber);
                        XutilsRequest.PC = shopnumber;
                        if (!TextUtils.isEmpty(duankou)) {
                            edit.putString(SharedPreferencesDate.URL_SET, ip + ":" + duankou + "/" + textinterface);
                            UrlData.APP_IP = ip + ":" + duankou + "/" + textinterface;
                        } else {
                            edit.putString(SharedPreferencesDate.URL_SET, ip + "/" + textinterface);
                            UrlData.APP_IP = ip + "/" + textinterface;
                        }

                        UrlData.reloadURL();
                        Toast.makeText(UrlSetActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        sharedPreferences_f.edit().putBoolean(SharedPreferencesDate.ISSETURL, true).commit();
                        edit.commit();
                        finish();
                    } else {
                        Toast.makeText(UrlSetActivity.this, "店址编号未填写", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UrlSetActivity.this, "网络端口填写不完整,请重新填写", Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }
}
