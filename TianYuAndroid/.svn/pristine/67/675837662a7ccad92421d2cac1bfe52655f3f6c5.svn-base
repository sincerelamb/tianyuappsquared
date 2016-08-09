package com.tygas.tianyu.tianyu.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.model.CpCustomer;
import com.tygas.tianyu.tianyu.ui.view.activity.CpCustomActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.CpCustomInfoActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/1/18.
 */
public class CpCustomUltiRecycleAdapter extends UltimateViewAdapter<CpCustomUltiRecycleAdapter.MyViewHodler> {

    private Context context;
    private List<CpCustomer> list;

    public CpCustomUltiRecycleAdapter(Context context, List<CpCustomer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHodler getViewHolder(View view) {
        return new MyViewHodler(view);
    }


    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cpcustomers_layout_item, parent, false);
        MyViewHodler vh = new MyViewHodler(v);
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
    public void onBindViewHolder(MyViewHodler holder, int position) {
        if (position >= list.size()) {
            return;
        }
        final CpCustomer cpCustomer = list.get(position);
        Log.d("positon", position + "(((" + list.get(position).getIsSuppleState());
        if ("是".equals(list.get(position).getIsSuppleState())) {
            holder.iv_inf_yibuquan.setVisibility(View.VISIBLE);
            holder.iv_info_weibuquan.setVisibility(View.GONE);
        } else if ("否".equals(list.get(position).getIsSuppleState())) {
            holder.iv_inf_yibuquan.setVisibility(View.GONE);
            holder.iv_info_weibuquan.setVisibility(View.VISIBLE);
        }
        holder.iv_info_weibuquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CpCustomInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CpData", cpCustomer);
                intent.putExtra("CpData", bundle);
                ((CpCustomActivity) context).startActivityForResult(intent, 1);
            }
        });
        //
//        holder.iv_inf_yibuquan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, CpCustomInfoActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("CpData", cpCustomer);
//                intent.putExtra("CpData", bundle);
//                ((CpCustomActivity) context).startActivityForResult(intent, 1);
//            }
//        });
        //

        holder.name.setText(cpCustomer.getCustomerName());
        holder.phone.setText(cpCustomer.getCustomerPhone());
        String comeTime = cpCustomer.getComeTime();

        if(!TextUtils.isEmpty(comeTime)&&!"null".equals(comeTime)){
            String substring_data = comeTime.substring(0, comeTime.indexOf(" "));
            String substring_time = comeTime.substring(comeTime.indexOf(" ") + 1);

            holder.comedate.setText(substring_data);
//            holder.cometime.setText(substring_time.substring(0,substring_time.lastIndexOf(":")));
            holder.cometime.setText(substring_time);
        }

        String leaveTime = cpCustomer.getLeaveTime();
        if(!TextUtils.isEmpty(leaveTime)&&!"null".equals(leaveTime)){
            String substring_leavetime = leaveTime.substring(leaveTime.lastIndexOf(" ") + 1);
//            holder.leavltime.setText(substring_leavetime.substring(0,substring_leavetime.lastIndexOf(":")));
            holder.leavltime.setText(substring_leavetime);
        }
        holder.consultant.setText(cpCustomer.getEmpName());

    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class MyViewHodler extends UltimateRecyclerviewViewHolder {

        private ImageView iv_inf_yibuquan;
        private ImageView iv_info_weibuquan;
        private TextView name;
        private TextView phone;
        private TextView comedate;
        private TextView cometime;
        private TextView leavltime;
        private TextView consultant;

        public MyViewHodler(View itemView) {
            super(itemView);
            iv_inf_yibuquan = (ImageView) itemView.findViewById(R.id.item_cpcustomers_iv_info_pic_yibuquan);
            iv_info_weibuquan = (ImageView) itemView.findViewById(R.id.item_cpcustomers_iv_info_pic_weibuquan);
            name = (TextView) itemView.findViewById(R.id.item_cpcustomers_tv_name);
            phone = (TextView) itemView.findViewById(R.id.item_cpcustomers_tv_phone);
            comedate = (TextView) itemView.findViewById(R.id.item_cpcustomers_tv_record_content);
            cometime = (TextView) itemView.findViewById(R.id.item_cpcustomers_tv_time_content);
            leavltime = (TextView) itemView.findViewById(R.id.item_cpcustomers_tv_time_shop_content);
            consultant = (TextView) itemView.findViewById(R.id.item_cpcustomers_tv_sales_consultant_name);
        }

    }
}
