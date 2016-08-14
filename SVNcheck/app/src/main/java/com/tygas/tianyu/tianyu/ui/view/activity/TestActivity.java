package com.tygas.tianyu.tianyu.ui.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.adapter.LevelAdapter;
import com.tygas.tianyu.tianyu.ui.view.customview.FocuGirdView;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/1/20.
 */
public class TestActivity extends AppCompatActivity{

    private static final String LOG_TAG = "TestActivity";

    private FocuGirdView focuGirdView;
    private ArrayList<String> data;
    private LevelAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        focuGirdView = (FocuGirdView) findViewById(R.id.gridview);
        data = new ArrayList<String>();
        //adapter = new LevelAdapter(data);
        for(int i=0;i<10;i++){
            data.add("Item_"+i);
        }
        focuGirdView.setAdapter(adapter);

    }//end onCreate

}
