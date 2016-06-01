package com.tygas.tianyu.tianyu.ui.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.PhonStateLisen;
import com.tygas.tianyu.tianyu.ui.model.PtCustomer;
import com.tygas.tianyu.tianyu.ui.view.activity.PtCustomersDetailActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.ReturnVisitFail;
import com.tygas.tianyu.tianyu.utils.PhoneValidate;
import com.tygas.tianyu.tianyu.utils.RecordFile;


import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/18.
 */
public class PtCustomUltiRecycleAdapter extends UltimateViewAdapter<PtCustomUltiRecycleAdapter.PtCustomerViewHodler> {

    private ArrayList<PtCustomer> data;
    private Activity activity;

    private static final String LOG_TAG = "PtCustomUltiRecycleAdapter";

    public PtCustomUltiRecycleAdapter(ArrayList<PtCustomer> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public PtCustomerViewHodler getViewHolder(View view) {
        return new PtCustomerViewHodler(view);
    }

    @Override
    public PtCustomerViewHodler onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ptcustomers_layout_item, parent, false);
        PtCustomerViewHodler vh = new PtCustomerViewHodler(v);
        return vh;
    }

    @Override
    public int getAdapterItemCount() {
        return data.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(PtCustomerViewHodler holder, int position) {
        //Log.i(LOG_TAG,"[Positoin] "+position);
        if (position >= data.size()) {
            return;
        }
        final PtCustomer customer = data.get(position);

        holder.setValueFromPtCustomer(customer);
        holder.leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击进入详细信息页面
                Intent intent = new Intent();
                intent.setClass(v.getContext(), PtCustomersDetailActivity.class);
                intent.putExtra("data", customer);
                v.getContext().startActivity(intent);
            }
        });

        holder.rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TelephonyManager manager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
                if (manager.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK) {
                    Toast.makeText(activity, "不能同时播出多个号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!PhoneValidate.validateNumber(customer.getCustomerPhone())) {//电话又字符串则返回true
                    Toast.makeText(activity, "电话号码有误,请修改后再回访", Toast.LENGTH_SHORT).show();
                    /*Intent intent = new Intent();
                    intent.putExtra("data", customer);
                    intent.putExtra("path", "  ");
                    intent.setClass(activity, ReturnVisitFail.class);
                    ReturnVisitFail.ptCustomerActivity = activity;
                    //intent.setClass(this,ReturnVisitOk.class);
                    activity.startActivity(intent);*/
                } else {

                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {

                        //点击拨号
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + customer.getCustomerPhone());
                        intent.setData(data);
                        RecordFile recordFile = new RecordFile(activity, customer.getCustomerName() + customer.getCustomerPhone());
                        PhonStateLisen.setParamsForPtCustomer(activity, customer,
                                recordFile.getFilePath(),recordFile.getRecordFile());
                        activity.startActivity(intent);
                        return;
                    } else {
                        Toast.makeText(activity, "未开启拨打电话或者获取通话记录权限，请在应用程序设置中开启权限！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }//end

    private static final String SCHEME = "package";
    /**
     * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.1及之前版本)
     */
    private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
    /**
     * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.2)
     */
    private static final String APP_PKG_NAME_22 = "pkg";
    /**
     * InstalledAppDetails所在包名
     */
    private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
    /**
     * InstalledAppDetails类名
     */
    private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class PtCustomerViewHodler extends UltimateRecyclerviewViewHolder {

        public TextView name;
        public TextView phone;
        public TextView empname;
        public TextView level; //等级
        public TextView lastTime;//最近跟踪时间
        public TextView nextTime; //下一次跟踪时间
        public TextView loveCar; //意向的车系
        public TextView recode;//洽谈记录
        public View leftView;
        public View rightView;

        public PtCustomerViewHodler(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_ptcustomers_tv_name);
            phone = (TextView) itemView.findViewById(R.id.item_ptcustomers_tv_phone);
            level = (TextView) itemView.findViewById(R.id.item_ptcustomers_tv_level);
            lastTime = (TextView) itemView.findViewById(R.id.item_ptcustomers_tv_recent_time);
            nextTime = (TextView) itemView.findViewById(R.id.item_ptcustomers_tv_callbacktime_title);
            loveCar = (TextView) itemView.findViewById(R.id.item_ptcustomers_tv_love_car_name);
            recode = (TextView) itemView.findViewById(R.id.item_ptcustomers_tv_record_content);
            leftView = itemView.findViewById(R.id.item_ptcustomers_rl_left);
            rightView = itemView.findViewById(R.id.item_ptcustomers_iv_right);
            empname = (TextView) itemView.findViewById(R.id.item_ptcustomers_tv_xiaoshouguwen_context);
        }

        public void setValueFromPtCustomer(PtCustomer customer) {
            name.setText(customer.getCustomerName());
            phone.setText(customer.getCustomerPhone());
            level.setText(customer.getIntentLevel());
            lastTime.setText(customer.getLastContratTime());
            nextTime.setText(customer.getNextCallTime());
            loveCar.setText(customer.getCarseriesName());
            recode.setText(customer.getLastTalkProcess());
            empname.setText(customer.getEmpName());
        }

    }//end class PtCustomerViewHodler
}
