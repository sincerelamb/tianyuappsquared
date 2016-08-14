package com.tygas.tianyu.tianyu.ui.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.tygas.tianyu.tianyu.ui.view.activity.PushPhoneReActivity;

import java.util.Calendar;
import java.util.List;

/**
 * Created by SJTY_YX on 2016/8/12.
 */
public class PushPhoneReAdapter extends BaseAdapter {

    private Context context;
    private List<PID> list;
    private LayoutInflater inflate;



    public void setListTotal(List<PID> list) {
        this.list = list;
    }


    public PushPhoneReAdapter(Context context, List<PID> list) {
        this.context = context;
        this.list = list;
        inflate = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public PID getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (null == convertView) {
            convertView = inflate.inflate(R.layout.pushphonereitem, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        if (list.size() > 0) {

            final PID pid = list.get(position);
            for(int i = 0;i < list.size();i++){

                holder.textView.setText(i);
//                holder.Button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent_newout = new Intent(context, PushPhoneReActivity.class);
//                        context.startActivity(intent_newout);
//                    }
//                });

            }
        }

        return convertView;
    }

    class ViewHolder {
        Button Button;
        TextView textView;

        public ViewHolder(View view) {
//            Button = (Button) view.findViewById(R.id.activity_pushphonere_button);
            textView = (TextView) view.findViewById(R.id.activity_pushphonere_item);
        }
    }


}
