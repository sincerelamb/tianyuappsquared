package com.tygas.tianyu.tianyu.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.model.Clue;
import com.tygas.tianyu.tianyu.ui.model.ClueProgress;
import com.tygas.tianyu.tianyu.ui.model.User;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/2/1.
 */
public class ClueProgressAdapter extends BaseAdapter {

    private ArrayList<ClueProgress> datas;
    private Clue clue;
    private String userName;

    public ClueProgressAdapter(ArrayList<ClueProgress> datas, Clue clue,String userName) {
        this.datas = datas;
        this.clue = clue;
        this.userName = userName;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.clue_process_item, null);
            ClueProgressViewHolder holder = new ClueProgressViewHolder(convertView);
            convertView.setTag(holder);
        }
        ClueProgressViewHolder h = (ClueProgressViewHolder) convertView.getTag();
        h.setValue(datas.get(position));
        return convertView;
    }

    class ClueProgressViewHolder{

        public TextView nameTextView;
        public TextView levelTextView;
        public TextView timeTextView;
        public TextView nextTimeTextView;
        public TextView recordTextView;
        public TextView empNameTextView;

        public ClueProgressViewHolder(View view) {
            nameTextView = (TextView) view.findViewById(R.id.clue_process_item_tv_name);
            levelTextView = (TextView) view.findViewById(R.id.clue_process_item_tv_level);
            timeTextView = (TextView) view.findViewById(R.id.clue_process_item_tv_time);
            nextTimeTextView = (TextView) view.findViewById(R.id.clue_process_item_tv_nexttime);
            recordTextView = (TextView) view.findViewById(R.id.clue_process_item_tv_recode);
            empNameTextView = (TextView) view.findViewById(R.id.clue_process_item_tv_job);
        }

        public void setValue(ClueProgress clueProgress){
            nameTextView.setText(clue.getClueName());
            levelTextView.setText(clueProgress.getIntentLevel());
            timeTextView.setText(clueProgress.getCallTime());
            nextTimeTextView.setText(clueProgress.getCusComeDate());
            recordTextView.setText(clueProgress.getTalkProcess());
            empNameTextView.setText("电销员:"+clueProgress.getEmpName());
        }

    }

}
