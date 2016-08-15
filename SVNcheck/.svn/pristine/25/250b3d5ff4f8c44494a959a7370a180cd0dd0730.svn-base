package com.tygas.tianyu.tianyu.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SJTY_YX on 2016/2/3.
 */
public class LevelAdapter extends BaseAdapter implements View.OnClickListener {

    private List<UserPtInfoModel> data;
    private static final String LOG_TAG = "LevelAdapter";

    private int checkPosition = -1;

    private ViewGroup parent;

    public LevelAdapter(List<UserPtInfoModel> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(parent != null){
            this.parent = parent;
        }
        LinearLayout viewGroup = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.level_item,null);
        CheckBox checkBox = (CheckBox) viewGroup.getChildAt(0);
        checkBox.setText(data.get(position).getName());
        checkBox.setOnClickListener(this);
        checkBox.setTag(position);
        if(position == checkPosition){
            checkBox.setChecked(true);
        }
        return viewGroup;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        ViewGroup viewGroup = (ViewGroup) v.getParent().getParent();
        for(int i=0;i<viewGroup.getChildCount();i++){
            ViewGroup vg = (ViewGroup) viewGroup.getChildAt(i);
            CheckBox checkBox = (CheckBox) vg.getChildAt(0);
            if(position == i){
                if(checkBox.isChecked()){
                    checkBox.setChecked(true);
                    checkPosition = i;
                }else{
                    checkBox.setChecked(false);
                    checkPosition = -1;
                }
            }else{
                checkBox.setChecked(false);
            }
        }
        Log.i(LOG_TAG, "checkLevel " + getCheckLevel());
    }

    public String getCheckLevel(){
        return checkPosition >= data.size() || checkPosition < 0 ? "" : data.get(checkPosition).getName();
    }

    public void setLevel(String level){
        for(int i=0;i<data.size();i++){
            if(data.get(i).getName().equals(level)){
                checkPosition = i;
            }
        }
    }

}
