package com.tygas.tianyu.tianyu.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.model.PersonHomeSetModel;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/3/15.
 */
public class PersonHomeSetAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {

    private ArrayList<PersonHomeSetModel> data;
    private static final String LOG_TAG = "PersonHomeSetAdapter";

    public PersonHomeSetAdapter(ArrayList<PersonHomeSetModel> data) {
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
        PersonHomeSetViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.personhomeset_item,parent,false);
            holder = new PersonHomeSetViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (PersonHomeSetViewHolder) convertView.getTag();
        holder.setData(data.get(position));
        holder.stateCheckBox.setOnCheckedChangeListener(this);
        holder.stateCheckBox.setTag(data.get(position));
        return convertView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        PersonHomeSetModel model = (PersonHomeSetModel) buttonView.getTag();
        model.setIsShow(isChecked?"true":"false");
    }

    class PersonHomeSetViewHolder{
        public TextView nameTextView;
        public CheckBox stateCheckBox;

        public PersonHomeSetViewHolder(View root) {
            nameTextView = (TextView) root.findViewById(R.id.personhomeset_item_name);
            stateCheckBox = (CheckBox) root.findViewById(R.id.personhomeset_item_state);
        }

        public void setData(PersonHomeSetModel data){
            nameTextView.setText(data.getName());
            stateCheckBox.setChecked(data.getIsShow().equals("true")?true:false);
        }
    }
}
