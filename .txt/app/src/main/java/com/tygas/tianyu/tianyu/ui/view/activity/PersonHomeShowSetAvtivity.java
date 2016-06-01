package com.tygas.tianyu.tianyu.ui.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.adapter.PersonHomeSetAdapter;
import com.tygas.tianyu.tianyu.ui.model.PersonHomeSetModel;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/3/15.
 *
 * 我的显示页面
 *
 */
public class PersonHomeShowSetAvtivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backImageView;//返回按钮
    private ListView listView;

    private ArrayList<PersonHomeSetModel> data;
    private PersonHomeSetAdapter adapter;

    private static final String LOG_TAG = "PersonHomeShowSetAvtivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personhomeshowset);
        initView();
        initData();
    }

    private void initData() {
        data = new ArrayList<>();
        for(int i=0;i<10;i++){
            PersonHomeSetModel model = new PersonHomeSetModel();
            model.setIsShow("true");
            model.setName("测试");
            data.add(model);
        }
        adapter = new PersonHomeSetAdapter(data);
        listView.setAdapter(adapter);
    }

    private void initView() {
        backImageView = (ImageView) findViewById(R.id.activity_personhomeshowset_iv_back);
        backImageView.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.activity_personhomeshowset_lv_content);
    }//end initView

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_personhomeshowset_iv_back://点击了返回按钮
                finish();
                break;
        }
    }//end onclick
}
