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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.PhonStateLisen;
import com.tygas.tianyu.tianyu.ui.model.FirstProtect;
import com.tygas.tianyu.tianyu.ui.model.TimingProtect;
import com.tygas.tianyu.tianyu.ui.view.activity.FPinfoActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.TpInfoActivity;
import com.tygas.tianyu.tianyu.utils.PhoneValidate;
import com.tygas.tianyu.tianyu.utils.RecordFile;

import java.util.List;

/**
 * Created by Administrator on 2016/3/3.
 */
public class TimingProtectAdapter extends UltimateViewAdapter<TimingProtectAdapter.TpViewHodler> {

    private List<TimingProtect> list;
    private Context context;

    public TimingProtectAdapter(Context context, List<TimingProtect> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public TpViewHodler getViewHolder(View view) {
        return new TpViewHodler(view);
    }

    @Override
    public TpViewHodler onCreateViewHolder(ViewGroup parent) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tpcustomers_layout_item, parent, false);
        TpViewHodler vh = new TpViewHodler(v);
        return vh;
    }

    @Override
    public int getAdapterItemCount() {
        return list.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(TpViewHodler holder, int position) {
        if (position >= list.size()) {
            return;
        }
        final TimingProtect timingProtect = list.get(position);
        holder.rl_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tp_info_intent = new Intent(context, TpInfoActivity.class);
                tp_info_intent.putExtra("tp_info_data", timingProtect);
                context.startActivity(tp_info_intent);
            }
        });
        holder.tv_name.setText(timingProtect.getCustomerName());
        holder.tv_phone.setText(timingProtect.getCustomerPhone());
        holder.tv_LatelyMaintainTime.setText(timingProtect.getLatelyMaintainTime());
        holder.tv_ShouldInviteCallDate.setText(timingProtect.getShouldInviteCallDate());
        holder.tv_LatelyTalkProcess.setText(timingProtect.getLatelyTalkProcess());


        holder.iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (manager.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK) {
                    Toast.makeText(context, "不能同时播出多个号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!PhoneValidate.validateNumber(timingProtect.getCustomerPhone())) {//电话又字符串则返回true
                    Toast.makeText(context, "电话号码有误", Toast.LENGTH_SHORT).show();

                } else {

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                        //点击拨号
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + timingProtect.getCustomerPhone());
                        intent.setData(data);
                        PhonStateLisen.setParamsForTpCustomer((Activity) context, timingProtect,
                                new RecordFile(context, timingProtect.getCustomerName() + timingProtect.getCustomerPhone()).getFilePath());
                        context.startActivity(intent);
                        return;
                    } else {
                        Toast.makeText(context, "未开启拨打电话或者获取通话记录权限，请在应用程序设置中开启权限！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class TpViewHodler extends UltimateRecyclerviewViewHolder {
        private RelativeLayout rl_info;
        private TextView tv_name;
        private TextView tv_phone;
        private TextView tv_LatelyMaintainTime;
        private TextView tv_ShouldInviteCallDate;
        private TextView tv_LatelyTalkProcess;
        private ImageView iv_call;

        public TpViewHodler(View itemView) {
            super(itemView);
            rl_info = (RelativeLayout) itemView.findViewById(R.id.item_tpcustomers_info);
            tv_name = (TextView) itemView.findViewById(R.id.item_tpcustomers_tv_name);
            tv_phone = (TextView) itemView.findViewById(R.id.item_tpcustomers_tv_phone);
            tv_LatelyMaintainTime = (TextView) itemView.findViewById(R.id.item_tpcustomers_tv_recentlydate_protect_content);
            tv_ShouldInviteCallDate = (TextView) itemView.findViewById(R.id.item_tpcustomers_tv_data_invitation_content);
            tv_LatelyTalkProcess = (TextView) itemView.findViewById(R.id.item_tpcustomers_tv_talkcontent);
            iv_call = (ImageView) itemView.findViewById(R.id.item_tpcustomers_iv_invitation_call);

        }
    }
}
