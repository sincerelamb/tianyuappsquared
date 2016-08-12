package com.tygas.tianyu.tianyu.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.dragsortadapter.DragSortAdapter;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.model.PID;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;

import java.util.Calendar;
import java.util.List;

/**
 * Created by SJTY_YX on 2016/8/12.
 */
public class PushPhoneReAdapter extends BaseAdapter {

    private Context context;
    private List<UserPtInfoModel> list;
    private List<PID> list_pid;
    private LayoutInflater inflate;



    public void setListTotal(List<UserPtInfoModel> list) {
        this.list = list;
    }


    public PushPhoneReAdapter(Context context, List<UserPtInfoModel> list, List<PID> list_pid) {
        this.context = context;
        this.list = list;
        this.list_pid = list_pid;
        inflate = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return list_pid.size();
    }

    @Override
    public PID getItem(int position) {
        return list_pid.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (null == convertView) {
            convertView = inflate.inflate(R.layout.activity_main_gv_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        if (list_pid.size() > 0) {
            final PID pid = list_pid.get(position);
            switch (pid.getFormID()) {
            }
        }

        return convertView;
    }

    class ViewHolder {
        Button Buttonall;
        TextView textView;

        public ViewHolder(View view) {
            Buttonall = (Button) view.findViewById(R.id.activity_pushphonere_button);
            textView = (TextView) view.findViewById(R.id.activity_pushphonere_item);
        }
    }


}
