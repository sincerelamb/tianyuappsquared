package com.tygas.tianyu.tianyu.ui.view.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.adapter.PushPhoneReAdapter;
import com.tygas.tianyu.tianyu.ui.model.PID;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SJTY_YX on 2016/8/9.
 */
public class PushPhoneReActivity extends BaseActivity implements View.OnClickListener {
    private ImageView PushPhoneRe_back;
    private TextView nodata;
//    private Button Upload;
    private PushPhoneReAdapter pushPhoneReAdapter;
    private List<PID> list_pid = new ArrayList<PID>();
    private ListView pushlist;
    private DbUtils dbUtils;

    private void infilterupdataUI(List<PID> list_userNum) {
        if (list_userNum != null && list_userNum.size() > 0) {

            if (pushPhoneReAdapter != null) {
                pushPhoneReAdapter.setListTotal(list_userNum);
                pushPhoneReAdapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushphonere);



        initView();

    }

    private void initView() {


        PushPhoneRe_back = (ImageView)findViewById(R.id.activity_pushphonere_back);
//        Upload = (Button)findViewById(R.id.activity_pushphonere_upload);
        pushlist = (ListView)findViewById(R.id.pushlist);
        nodata = (TextView)findViewById(R.id.nodata);
        try {
            list_pid = dbUtils.findAll(PID.class);
//        PID pid = new PID();
//        pid.setFormID("AndroidApp_search_customer");
//        list_pid.add(pid);
//        PID pid1 = new PID();
//        pid1.setFormID("AndroidApp_search_clue");
//        list_pid.add(pid1);
//        PID pid2 = new PID();
//        pid2.setFormID("AndroidApp_repair_customer");
//        list_pid.add(pid2);

            if (list_pid != null && list_pid.size() > 0) {
                Log.d("list_pid", list_pid.toString());
//                PID pid_problem = new PID();
//                pid_problem.setFormID("AndroidApp_repair_AddCus");
//                list_pid.add(pid_problem);
                pushPhoneReAdapter = new PushPhoneReAdapter(this, list_pid);
                pushlist.setAdapter(pushPhoneReAdapter);
            }

        } catch (DbException e) {
             e.printStackTrace();
        }
         catch (NullPointerException e){
             e.printStackTrace();
         }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_pushphonere_upload://全部上传按钮
/*没搞清楚上传逻辑*/


            break;
            case R.id.activity_pushphonere_back://返回按钮
//                Intent intent_back = new Intent (this,MainActivity.class);
//                startActivity(intent_back);
                finish();
            break;
        }
    }



}
