package com.tygas.tianyu.tianyu.ui.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.adapter.PersonHomeAdapter;
import com.tygas.tianyu.tianyu.ui.model.PersonHomeModel;
import com.tygas.tianyu.tianyu.ui.view.customview.FocuGirdView;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/3/15.
 *
 * 个人工作主页
 *
 */
public class PersonHomePageActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageView backImageView;//返回按钮
    private ImageView addImageView;//底部的添加按钮
    private FocuGirdView contentFocuGirdView;//
    private ArrayList<PersonHomeModel> models;
    private PersonHomeAdapter adapter;

    private static final int UP_COLOR = Color.rgb(32,123,3);//增长的字体颜色 绿色
    private static final int DOWN_COLOR = Color.rgb(246,5,22);//下降的字体颜色 红色
    private String[] names = {"当前库存数","库存深度","在途数","在跟线索数","在跟H级线索数","在跟A级线索数","在跟B级线索数",
                                "在跟C级线索数","在跟FR级线索数","在跟潜客数","在跟H级潜客数","在跟A级潜客数","在跟B级潜客数",
                                "在跟C级潜客数","在跟FR级潜客数","当前未成交订单","当前被驳回按揭","当前结算未交车","当前未结算工单"};

    private static final String LOG_TAG = "PersonHomePageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personhomepage);
        initView();
        initData();
    }//end onCreatea

    private void initData() {
        models = new ArrayList<>();
        for(int i=0;i < names.length;i++){
            PersonHomeModel personHomeModel = new PersonHomeModel();
            personHomeModel.setModelName(names[i]);
            personHomeModel.setTongbiNumber("0%");
            personHomeModel.setHuanbiNumber("0%");
            models.add(personHomeModel);
        }

        adapter = new PersonHomeAdapter(models);
        contentFocuGirdView.setAdapter(adapter);

        String empid = "1";
        //String type = "当前库存数";
        //for(int i=0;i<models.size();i++){
            RequestParams requestParams = XutilsRequest.getUserKpiRequestParams("", empid);
            HttpUtils instance = HttpUtilsHelper.getInstance();
            //final int finalI = i;
            instance.send(HttpRequest.HttpMethod.POST, "http://118.123.249.59:1999/OS/OpenService/App.aspx?type=userkpi"/*UrlData.USERKPI_URL*/, requestParams, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    Log.i(LOG_TAG,"[服务器返回的数据]"+responseInfo.result);
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Log.i(LOG_TAG,"[网络请求错误]"+s);
                }
            });
        //}

    }//end initData

    private void initView() {
        addImageView = (ImageView) findViewById(R.id.activity_personhomepage_tv_add);
        contentFocuGirdView = (FocuGirdView) findViewById(R.id.activity_personhomepage_focugirdview_content);
        addImageView.setOnClickListener(this);
        contentFocuGirdView.setOnItemClickListener(this);
        backImageView = (ImageView) findViewById(R.id.activity_personhomepage_iv_back);
        backImageView.setOnClickListener(this);
    }//end initView

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_personhomepage_iv_back:
                finish();
                break;
            case R.id.activity_personhomepage_tv_add:
                Intent intent = new Intent();
                intent.setClass(this,PersonHomeShowSetAvtivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(LOG_TAG,"[点击了item]"+position);
    }
}
