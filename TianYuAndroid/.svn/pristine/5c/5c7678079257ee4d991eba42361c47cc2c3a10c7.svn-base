package com.tygas.tianyu.tianyu.ui.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.PhonStateLisen;
import com.tygas.tianyu.tianyu.ui.adapter.CluePagerAdapter;
import com.tygas.tianyu.tianyu.ui.model.Clue;
import com.tygas.tianyu.tianyu.ui.model.PtCustomer;
import com.tygas.tianyu.tianyu.ui.view.fragment.ClueInfoFragment;
import com.tygas.tianyu.tianyu.ui.view.fragment.ClueProgressFragment;
import com.tygas.tianyu.tianyu.utils.PhoneValidate;
import com.tygas.tianyu.tianyu.utils.RecordFile;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/1/28.
 */
public class ClueDetailActivity extends BaseActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {


    private PtCustomer customer;

    private ImageView backImageView;//返回按钮
    private ViewPager viewPager;
    private TabLayout tabLayout;//联动菜单
    private Button btn_call;

    private ClueInfoFragment clueInfoFragment;//线索详细的fragment
    private ClueProgressFragment clueProgressFragment;//线索跟进过程的fragemnt

    private CluePagerAdapter adapter;
    private ArrayList<Fragment> data;


    private static final String LOG_TAG = "ClueDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_cluedetail);
        customer = (PtCustomer) getIntent().getSerializableExtra("data");
        initView();
    }//end oncreate


    private void initView() {
        backImageView = (ImageView) findViewById(R.id.activity_cluedetail_iv_back);
        backImageView.setOnClickListener(this);
        viewPager = (ViewPager) findViewById(R.id.activity_cluedetail_vp_content);
        tabLayout = (TabLayout) findViewById(R.id.activity_cluedetail_tl_tt);

        clueInfoFragment = new ClueInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",customer);
        clueInfoFragment.setArguments(bundle);
        clueProgressFragment = new ClueProgressFragment();
        clueProgressFragment.setArguments(bundle);
        data = new ArrayList<>();
        data.add(clueInfoFragment);
        data.add(clueProgressFragment);
        adapter = new CluePagerAdapter(getSupportFragmentManager(),data);
        viewPager.setAdapter(adapter);

        tabLayout.setOnTabSelectedListener(this);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);

        btn_call = (Button) findViewById(R.id.activity_cluedetail_bt_callout);
        btn_call.setOnClickListener(this);
    }

//    public void callPhone(){
//
//
//        if(!PhoneValidate.validateNumber(customer.getCustomerPhone())){
//            Toast.makeText(this,"电话号码错误",Toast.LENGTH_SHORT).show();
//            /*Intent intent = new Intent();
//            intent.putExtra("data", clue);
//            intent.putExtra("path", "  ");
//            intent.setClass(this, ClueReturnVisitFail.class);
//
//            ClueReturnVisitFail.clueActivity = this;
//            startActivity(intent);*/
//
//        }else {
//
//            Intent intent = new Intent(Intent.ACTION_CALL);
//            Uri data = Uri.parse("tel:" + clue.getCluePhone());
//            intent.setData(data);
//            RecordFile recordFile = new RecordFile(this, "clue" + clue.getClueName() + clue.getCluePhone());
//            PhonStateLisen.setParamsForClue(this, clue,
//                    recordFile.getFilePath(), recordFile.getRecordFile());
//            startActivity(intent);
//        }
//    }

    @Override
    public void onBackPressed() {
        if(PhonStateLisen.isCall){
            Toast.makeText(this,"通话过程中不能返回",Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_cluedetail_iv_back://点击返回
                if(PhonStateLisen.isCall){
                    Toast.makeText(this,"通话过程中不能返回",Toast.LENGTH_SHORT).show();
                    return;
                }
                finish();
                break;

            case R.id.activity_cluedetail_bt_callout:
                dialog1();
                break;

            case R.id.callout_ll_phone:
                call(customer.getCustomerPhone(), 1);
                alertDialog.dismiss();
                break;

            case R.id.callout_ll_phone_beiyong:
                call(customer.getSparePhone(), 2);
                alertDialog.dismiss();
                break;
        }
    }//end onClick

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}


    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    private void dialog1() {
        builder = new AlertDialog.Builder(this);  //先得到构造器
//        builder.setTitle("提示"); //设置标题
        builder.setMessage("选择拨出电话:"); //设置内容
//        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        View inflate = View.inflate(this, R.layout.callout_layout, null);
        LinearLayout ll_phone = (LinearLayout) inflate.findViewById(R.id.callout_ll_phone);
        ll_phone.setOnClickListener(this);
        LinearLayout ll_phone_beiyong = (LinearLayout) inflate.findViewById(R.id.callout_ll_phone_beiyong);
        ll_phone_beiyong.setOnClickListener(this);
        TextView tv_phone = (TextView) inflate.findViewById(R.id.callout_tv_phone);
        if (TextUtils.isEmpty(customer.getCustomerPhone())) {
            tv_phone.setText("暂无数据");
        } else {
            tv_phone.setText(customer.getCustomerPhone());
        }

        TextView tv_phone_beiyong = (TextView) inflate.findViewById(R.id.callout_tv_phone_beiyong);
        if (TextUtils.isEmpty(customer.getSparePhone())) {
            tv_phone_beiyong.setText("暂无数据");
        } else {
            tv_phone_beiyong.setText(customer.getSparePhone());
        }

        builder.setView(inflate);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        //参数都设置完成了，创建并显示出来
        alertDialog = builder.create();
        alertDialog.show();
    }


    private void call(String phone, int type) {
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (manager.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK) {
            Toast.makeText(this, "不能同时播出多个号码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!PhoneValidate.validateNumber(phone)) {//电话又字符串则返回true
            Toast.makeText(this, "电话号码有误,请修改后再回访", Toast.LENGTH_SHORT).show();
                   /* Intent intent = new Intent();
                    intent.putExtra("data", customer);
                    intent.putExtra("path", "  ");
                    intent.setClass(activity, ReturnVisitFail.class);
                    ReturnVisitFail.ptCustomerActivity = activity;
                    //intent.setClass(this,ReturnVisitOk.class);
                    activity.startActivity(intent);*/
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                Uri uri = Uri.parse("tel:" + phone);
                Intent call = new Intent(Intent.ACTION_CALL, uri); //直接播出电话
                RecordFile recordFile = new RecordFile(this, customer.getCustomerName() + phone);
                PhonStateLisen.setParamsForPtCustomer(this, customer,
                        recordFile.getFilePath(), recordFile.getRecordFile(), type,2);
                startActivity(call);
            } else {
                Toast.makeText(this, "该应用没有允许打电话权限或读取通话记录，请在设应用程序权限管理中将打开！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
