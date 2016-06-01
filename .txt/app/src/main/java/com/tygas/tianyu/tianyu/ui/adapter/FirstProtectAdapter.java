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
import com.tygas.tianyu.tianyu.ui.view.activity.FPinfoActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.ReturnVisitFail;
import com.tygas.tianyu.tianyu.utils.PhoneValidate;
import com.tygas.tianyu.tianyu.utils.RecordFile;

import java.util.List;

/**
 * Created by Administrator on 2016/3/2.
 */
public class FirstProtectAdapter extends UltimateViewAdapter<FirstProtectAdapter.FpViewHodler> {

    private List<FirstProtect> list;
    private Context context;

    public FirstProtectAdapter(Context context, List<FirstProtect> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public FpViewHodler getViewHolder(View view) {
        return new FpViewHodler(view);
    }

    @Override
    public FpViewHodler onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fpcustomers_layout_item, parent, false);
        FpViewHodler vh = new FpViewHodler(v);
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
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(FpViewHodler holder, int position) {
        if (position >= list.size()) {
            return;
        }
        final FirstProtect firstProtect = list.get(position);
        holder.tv_name.setText(firstProtect.getCarOwnerName());
        holder.tv_phoen.setText(firstProtect.getCarOwnerPhone());
        holder.tv_invitationdate.setText(firstProtect.getShouldInviteCallDate());
        holder.tv_takecardate.setText(firstProtect.getCarSalesTime());
        holder.tv_talkcontent.setText(firstProtect.getTalkProcess());

        holder.rl_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fp_info_intent = new Intent(context, FPinfoActivity.class);
                fp_info_intent.putExtra("fp_info_data", firstProtect);
                context.startActivity(fp_info_intent);
            }
        });

        holder.iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (manager.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK) {
                    Toast.makeText(context, "不能同时播出多个号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!PhoneValidate.validateNumber(firstProtect.getCarOwnerPhone())) {//电话又字符串则返回true
                    Toast.makeText(context, "电话号码有误", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent();
//                    intent.putExtra("data", customer);
//                    intent.putExtra("path", "  ");
//                    intent.setClass(context, ReturnVisitFail.class);
//                    ReturnVisitFail.ptCustomerActivity = context;
//                    //intent.setClass(this,ReturnVisitOk.class);
//                    context.startActivity(intent);
                } else {

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                        //点击拨号
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + firstProtect.getCarOwnerPhone());
                        intent.setData(data);
                        PhonStateLisen.setParamsForFpCustomer((Activity) context, firstProtect,
                                new RecordFile(context, firstProtect.getCarOwnerName() + firstProtect.getCarOwnerPhone()).getFilePath());
                        context.startActivity(intent);
                        return;
                    } else {
                        Toast.makeText(context, "未开启拨打电话或者获取通话记录权限，请在应用程序设置中开启权限！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


    class FpViewHodler extends UltimateRecyclerviewViewHolder {
        private RelativeLayout rl_info;
        private TextView tv_name;
        private TextView tv_phoen;
        private TextView tv_takecardate;
        private TextView tv_invitationdate;
        private TextView tv_talkcontent;
        private ImageView iv_call;


        public FpViewHodler(View itemView) {
            super(itemView);
            rl_info = (RelativeLayout) itemView.findViewById(R.id.item_fpcustomers_info);
            tv_name = (TextView) itemView.findViewById(R.id.item_fpcustomers_tv_name);
            tv_phoen = (TextView) itemView.findViewById(R.id.item_fpcustomers_tv_phone);
            tv_takecardate = (TextView) itemView.findViewById(R.id.item_fpcustomers_tv_date_takecar_content);
            tv_invitationdate = (TextView) itemView.findViewById(R.id.item_fpcustomers_tv_data_invitation_content);
            tv_talkcontent = (TextView) itemView.findViewById(R.id.item_fpcustomers_tv_talkcontent);
            iv_call = (ImageView) itemView.findViewById(R.id.item_fpcustomers_iv_invitation_call);
        }
    }
}
