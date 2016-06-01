package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.adapter.PersonHomeAdapter;
import com.tygas.tianyu.tianyu.ui.adapter.PersonHomeUltiRecycleAdapter;
import com.tygas.tianyu.tianyu.ui.model.UserKPI;
import com.tygas.tianyu.tianyu.ui.model.UserKPIS;
import com.tygas.tianyu.tianyu.ui.view.customview.FocuGirdView;
import com.tygas.tianyu.tianyu.utils.DbUtilsHelper;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SJTY_YX on 2016/3/15.
 * <p/>
 * 个人工作主页
 */
public class PersonHomePageActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ImageView backImageView;//返回按钮
    private ImageView addImageView;//底部的添加按钮
    // private GridView contentFocuGirdView;//
    private UltimateRecyclerView contentFocuGirdView;
    private GridLayoutManager layoutManager;
    private ImageView iv_nodate;

    private ArrayList<UserKPIS> showKpis = new ArrayList<>();//需要显示的kpi
    private List<UserKPIS> allKpis = new ArrayList<>();//全部的kpi

    private DbUtils dbUtils;
    private boolean isHaveSave;
    private PersonHomeUltiRecycleAdapter adapter;

    private static final int UP_COLOR = Color.rgb(32, 123, 3);//增长的字体颜色 绿色
    private static final int DOWN_COLOR = Color.rgb(246, 5, 22);//下降的字体颜色 红色

    private static final String LOG_TAG = "PersonHomePageActivity";
    public static final String SAVE_TAG = "userkpishow";//应该显示那些userkpi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_personhomepage);
        dbUtils = DbUtilsHelper.newInstance(this);
        initView();
        initUserKPI();
        contentFocuGirdView.post(new Runnable() {
            @Override
            public void run() {
                contentFocuGirdView.setRefreshing(true);
            }
        });
        onRefresh();
    }//end onCreatea

    private void initUserKPI() {
//        SharedPreferences ps = PreferenceManager.getDefaultSharedPreferences(this);
//        String kpi = ps.getString(SAVE_TAG, "");
//        Log.i(LOG_TAG, "[读取到的数据]" + kpi);
//        showKpis.clear();
//        if (!"".equals(kpi)) {
//            allKpis.clear();
//            String[] kpis = kpi.split(",");
//            for (int i = 0; i < kpis.length; i++) {
//                UserKPI userKPI = new UserKPI();
//                userKPI.setTitle(kpis[i].substring(0, kpis[i].length() - 1));
//                if (kpis[i].endsWith("F")) {//不显示
//                    userKPI.setIsShow(false);
//                } else { //显示
//                    userKPI.setIsShow(true);
//                    showKpis.add(userKPI);
//                }
//                allKpis.add(userKPI);
//            }
//            adapter.notifyDataSetChanged();
//        }
        try {
            List<UserKPIS> all = dbUtils.findAll(UserKPIS.class);

            if (all != null && all.size() > 0) {
                allKpis.clear();
                allKpis.addAll(all);
                Log.d("allKpis", all.toString());
                Log.d("allKpis", allKpis.toString());
                //获取到adapter
                List<UserKPIS> temp = new ArrayList<>();
                for (int i = 0; i < allKpis.size(); i++) {
                    if (allKpis.get(i).isShow()) {
                        temp.add(allKpis.get(i));
                    }
                }
                showKpis.clear();
                showKpis.addAll(temp);
                Log.d("temp", temp.toString());
                adapter.notifyDataSetChanged();
                iv_nodate.setVisibility(View.GONE);
                isHaveSave = true;
            } else {
                iv_nodate.setVisibility(View.VISIBLE);
                isHaveSave = false;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //initUserKPI();
        //initData();
    }


    private void initData() {
        // showProgress();
        String empid = ((MyAppCollection) getApplication()).getUser().getEmpId();
        RequestParams requestParams = XutilsRequest.getUserKpiRequestParams("", empid);
        HttpUtils instance = HttpUtilsHelper.getInstance();
        instance.send(HttpRequest.HttpMethod.POST, UrlData.USERKPI_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                contentFocuGirdView.post(new Runnable() {
                    @Override
                    public void run() {
                        contentFocuGirdView.setRefreshing(false);
                    }
                });
                try {
                    parseKpiResult(responseInfo.result);
                    //  hidenProgress();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                contentFocuGirdView.post(new Runnable() {
                    @Override
                    public void run() {
                        contentFocuGirdView.setRefreshing(false);
                    }
                });
                //  hidenProgress();
                if (isHaveSave) {
                    iv_nodate.setVisibility(View.GONE);
                } else {
                    iv_nodate.setVisibility(View.VISIBLE);
                }

                Log.i(LOG_TAG, "[网络请求错误]" + s);
            }
        });

    }//end initData

    //{"status":"Success","message":"","data":"{\"UserKPIList\":[{\"title\":\"当前库存数\",\"Num\":0},{\"title\":\"库存深度\",\"Num\":0},{\"title\":\"在途数\",\"Num\":0},{\"title\":\"在跟线索数\",\"Num\":7},{\"title\":\"在跟H级线索数\",\"Num\":1},{\"title\":\"在跟A级线索数\",\"Num\":1},{\"title\":\"在跟B级线索数\",\"Num\":2},{\"title\":\"在跟C级线索数\",\"Num\":2},{\"title\":\"在跟FR级线索数\",\"Num\":1},{\"title\":\"在跟H级潜客数 \",\"Num\":56},{\"title\":\"在跟A级潜客数\",\"Num\":5},{\"title\":\"在跟B级潜客数\",\"Num\":48},{\"title\":\"在跟C级潜客数\",\"Num\":32},{\"title\":\"在跟FR级潜客数\",\"Num\":8},{\"title\":\"当前未成交订单\",\"Num\":0},{\"title\":\"当前被驳回按揭\",\"Num\":0},{\"title\":\"结算未交车\",\"Num\":0}],\"totalrows\":0}"}
    private void parseKpiResult(String result) throws JSONException {
        Log.d("result", result);
        JSONObject jsonObject = new JSONObject(result);
        String status = jsonObject.getString("status");
        if ("Success".equals(status)) {
            String dataStr = jsonObject.getString("data");
            jsonObject = new JSONObject(dataStr);
            JSONArray arr = jsonObject.getJSONArray("UserKPIList");
            ArrayList<UserKPIS> temp = new ArrayList<UserKPIS>();
            Log.i(LOG_TAG, "[all kpis before]" + allKpis);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                UserKPIS userKPI = new UserKPIS();
                userKPI.setTitle(obj.getString("title"));
                userKPI.setIsShow(true);
                userKPI.setNum(obj.getString("Num"));
                if (allKpis != null && allKpis.size() > 0) {
                    for (int j = 0; j < allKpis.size(); j++) {
                        if (userKPI.getTitle().equals(allKpis.get(j).getTitle())) {
                            userKPI.setIsShow(allKpis.get(j).isShow());
                        }
                    }//end for
                }
                temp.add(userKPI);

            }//end for
            Log.d("temp", temp.toString());

            if (temp.size() > 0) {
                allKpis.clear();
                allKpis.addAll(temp);
                Log.d("allKpis", allKpis.toString());
                //写入shareprefences
                //   saveToFile(allKpis);
                try {
                    dbUtils.deleteAll(UserKPIS.class);
                    dbUtils.saveAll(allKpis);
                    List<UserKPIS> all = dbUtils.findAll(UserKPIS.class);
                    Log.d("allKpis", all.toString());
                } catch (DbException e) {
                    e.printStackTrace();
                }

                //获取到adapter
                List<UserKPIS> temps = new ArrayList<>();
                for (int i = 0; i < allKpis.size(); i++) {
                    if (allKpis.get(i).isShow()) {
                        temps.add(allKpis.get(i));
                    }
                }
                showKpis.clear();
                showKpis.addAll(temps);
                adapter.notifyDataSetChanged();
                iv_nodate.setVisibility(View.GONE);
            } else {
                if (isHaveSave) {
                    iv_nodate.setVisibility(View.GONE);
                } else {
                    iv_nodate.setVisibility(View.VISIBLE);
                }
            }


            //contentFocuGirdView.invalidate();
        } else {
            if (isHaveSave) {
                iv_nodate.setVisibility(View.GONE);
            } else {
                iv_nodate.setVisibility(View.VISIBLE);
            }

        }
    }//end parseKpiResult

    private void saveToFile(ArrayList<UserKPI> allKpis) {
        String result = "";
        for (int i = 0; i < allKpis.size(); i++) {
            result += allKpis.get(i).toString() + ",";
        }
        result = result.endsWith(",") ? result.substring(0, result.length()) : result;
        SharedPreferences ps = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = ps.edit();
        editor.putString(SAVE_TAG, result);
        editor.commit();
    }//end saveToFile

    private void initView() {
        contentFocuGirdView = (UltimateRecyclerView) findViewById(R.id.activity_personhomepage_focugirdview_content);
        layoutManager = new GridLayoutManager(this, 3);
        contentFocuGirdView.setLayoutManager(layoutManager);
        contentFocuGirdView.disableLoadmore();
        contentFocuGirdView.setHasFixedSize(false);
        contentFocuGirdView.setDefaultOnRefreshListener(this);
        adapter = new PersonHomeUltiRecycleAdapter(showKpis, this);
        contentFocuGirdView.setAdapter(adapter);

        addImageView = (ImageView) findViewById(R.id.activity_personhomepage_iv_add);
        addImageView.setOnClickListener(this);
        backImageView = (ImageView) findViewById(R.id.activity_personhomepage_iv_back);
        backImageView.setOnClickListener(this);
        iv_nodate = (ImageView) findViewById(R.id.activity_personhomepage_iv_nodate);
    }//end initView

//    private void showProgress(){
//        handelProgress(View.VISIBLE);
//    }
//
//    private void hidenProgress(){
//        handelProgress(View.GONE);
//    }
//
//    private void handelProgress(int what){
//        if(contentFocuGirdView != null){
//            //contentFocuGirdView
//            for(int i=0;i<contentFocuGirdView.getChildCount();i++){
//                ViewGroup view = (ViewGroup) contentFocuGirdView.getChildAt(i);
//                ViewGroup temp = (ViewGroup) view.getChildAt(1);
//                temp.getChildAt(0).setVisibility(what);
//            }
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_personhomepage_iv_back:
                finish();
                break;
            case R.id.activity_personhomepage_iv_add:
                Intent intent = new Intent();
                intent.setClass(this, PersonHomeShowSetAvtivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onRefresh() {
        //   initUserKPI();
        initData();
    }

    @Override
    protected void onResume() {
        initUserKPI();
        super.onResume();
    }
}
