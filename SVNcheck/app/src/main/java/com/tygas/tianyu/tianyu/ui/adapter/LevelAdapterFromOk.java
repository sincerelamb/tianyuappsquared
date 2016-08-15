package com.tygas.tianyu.tianyu.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;

import java.util.List;

/**
 * Created by SJTY_YX on 2016/2/3.
 */
public class LevelAdapterFromOk extends BaseAdapter {

    private List<UserPtInfoModel> data;
    private String levString;
    private static final String LOG_TAG = "LevelAdapter";

    private int checkPosition = 0;
    private View.OnClickListener onClickListener;

    public LevelAdapterFromOk(View.OnClickListener onClickListener, List<UserPtInfoModel> data, String levelStr) {
        this.onClickListener = onClickListener;
        this.data = data;
        this.levString = levelStr;
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
        LinearLayout viewGroup = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.level_item, null);
        CheckBox checkBox = (CheckBox) viewGroup.getChildAt(0);
        checkBox.setText(data.get(position).getName());
        checkBox.setOnClickListener(onClickListener);
        checkBox.setTag(data.get(position));

        if (levString.equals(data.get(position).getName())) {
            checkBox.setChecked(true);
        }

        return viewGroup;
    }

    public void setPosition(View v) {
        String str = ((UserPtInfoModel) v.getTag()).getName();
        ViewGroup viewGroup = (ViewGroup) v.getParent().getParent();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            ViewGroup vg = (ViewGroup) viewGroup.getChildAt(i);
            CheckBox checkBox = (CheckBox) vg.getChildAt(0);
            if (str.equals(((UserPtInfoModel) checkBox.getTag()).getName())) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }
        }
    }

    public void setLeavl(String levString) {

        this.levString = levString;
        notifyDataSetChanged();
    }

    /*@Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        ViewGroup viewGroup = (ViewGroup) v.getParent().getParent();
        for(int i=0;i<viewGroup.getChildCount();i++){
            ViewGroup vg = (ViewGroup) viewGroup.getChildAt(i);
            CheckBox checkBox = (CheckBox) vg.getChildAt(0);
            if(position == i){
                checkBox.setChecked(true);
                checkPosition = i;
            }else{
                checkBox.setChecked(false);
            }
        }
        //Log.i(LOG_TAG,"checkLevel "+getCheckLevel());
    }

    public String getCheckLevel(){
        return checkPosition >= data.size() ?data.get(0).getName() : data.get(checkPosition).getName();
    }*/

}
