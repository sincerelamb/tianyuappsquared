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
import com.tygas.tianyu.tianyu.ui.adapter.PtCustomersFragmentPageAdapter;
import com.tygas.tianyu.tianyu.ui.model.PtCustomer;
import com.tygas.tianyu.tianyu.ui.view.fragment.CustomersInfoFragment;
import com.tygas.tianyu.tianyu.ui.view.fragment.CustomersProcessFragment;
import com.tygas.tianyu.tianyu.utils.PhoneValidate;
import com.tygas.tianyu.tianyu.utils.RecordFile;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/1/20.
 * 线索的详细信息页面
 */
public class PtCustomersDetailActivity extends BaseActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {

    private PtCustomer customer;

    private ImageView returnImageView;//返回按钮
    private ViewPager viewPager;
    private TabLayout tabLayout;//联动的菜单

    private PtCustomersFragmentPageAdapter adapter;
    private CustomersInfoFragment customersInfoFragment;
    private CustomersProcessFragment processFragment;
    private Button btn_call;
    ArrayList<Fragment> fragments;

    private static final String LOG_TAG = "PtCustomersDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_ptcustomersdetail);
        if (getIntent().getSerializableExtra("data") == null) {
            finish();
        } else {
            customer = (PtCustomer) getIntent().getSerializableExtra("data");
        }
        initView();

    }

    @Override
    public void onBackPressed() {
        if (PhonStateLisen.isCall) {
            Toast.makeText(this, "通话过程中不能返回", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }

    private void initView() {
        returnImageView = (ImageView) findViewById(R.id.activity_ptcustomersdetail_tv_return);
        returnImageView.setOnClickListener(this);
        tabLayout = (TabLayout) findViewById(R.id.activity_ptcustomersdetail_tl);
        viewPager = (ViewPager) findViewById(R.id.activity_ptcustomersdetail_vp);

        customersInfoFragment = new CustomersInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", customer);
        customersInfoFragment.setArguments(bundle);

        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("data", customer);
        processFragment = new CustomersProcessFragment();
        processFragment.setArguments(bundle1);
        fragments = new ArrayList<Fragment>();

        fragments.add(customersInfoFragment);
        fragments.add(processFragment);
        adapter = new PtCustomersFragmentPageAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        //viewPager.addOnPageChangeListener(this);
        tabLayout.setOnTabSelectedListener(this);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);

        btn_call = (Button) findViewById(R.id.activity_ptcustomersdetail_bt_callout);
        btn_call.setOnClickListener(this);

    }//end initView


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_ptcustomersdetail_tv_return://点击了返回按钮
                if (PhonStateLisen.isCall) {
                    Toast.makeText(this, "通话过程中不能返回", Toast.LENGTH_SHORT).show();
                    return;
                }
                finish();
                break;
            case R.id.activity_ptcustomersdetail_bt_callout:
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
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

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
                        recordFile.getFilePath(), recordFile.getRecordFile(), type,1);
                startActivity(call);
            } else {
                Toast.makeText(this, "该应用没有允许打电话权限或读取通话记录，请在设应用程序权限管理中将打开！", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
