package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.adapter.PersonHomeSetAdapter;
import com.tygas.tianyu.tianyu.ui.model.PersonHomeSetModel;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.ui.model.UserKPI;
import com.tygas.tianyu.tianyu.ui.model.UserKPIS;
import com.tygas.tianyu.tianyu.utils.DbUtilsHelper;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SJTY_YX on 2016/3/15.
 * <p/>
 * 我的显示页面
 */
public class PersonHomeShowSetAvtivity extends Activity implements View.OnClickListener {

    private ImageView backImageView;//返回按钮
    private ListView listView;
    private ImageView nodate;

    private PersonHomeSetAdapter adapter;
    private DbUtils dbUtils;

    private List<UserKPIS> allkpis = new ArrayList<>();

    private static final String LOG_TAG = "PersonHomeShowSetAvtivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_personhomeshowset);
        dbUtils = DbUtilsHelper.newInstance(this);
        initView();
        initUserKpi();
        initData();
    }

    private void initUserKpi() {
        try {
            List<UserKPIS> all = dbUtils.findAll(UserKPIS.class);
            if (all != null && all.size() > 0) {
                allkpis.clear();
                allkpis.addAll(all);
            } else {
                nodate.setVisibility(View.VISIBLE);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
//        SharedPreferences ps = PreferenceManager.getDefaultSharedPreferences(this);
//        String psString = ps.getString(PersonHomePageActivity.SAVE_TAG, "");
//        if(!"".equals(psString)){
//            String[] kpis = psString.split(",");
//            for(int i=0;i<kpis.length;i++) {
//                UserKPI userKPI = new UserKPI();
//                userKPI.setTitle(kpis[i].substring(0, kpis[i].length() - 1));
//                if (kpis[i].endsWith("F")) {//不显示
//                    userKPI.setIsShow(false);
//                } else {//显示
//                    userKPI.setIsShow(true);
//                }
//                allkpis.add(userKPI);
//            }
//        }

    }


    private void initData() {
        adapter = new PersonHomeSetAdapter(allkpis, this);
        listView.setAdapter(adapter);
    }

    private void initView() {
        backImageView = (ImageView) findViewById(R.id.activity_personhomeshowset_iv_back);
        backImageView.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.activity_personhomeshowset_lv_content);
        nodate = (ImageView) findViewById(R.id.activity_personhomeshowset_iv_nodate);
    }//end initView

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_personhomeshowset_iv_back://点击了返回按钮
                finish();
                break;
        }
    }//end onclick
}
