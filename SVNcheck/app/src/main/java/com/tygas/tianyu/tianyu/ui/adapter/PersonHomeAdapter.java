package com.tygas.tianyu.tianyu.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.model.UserKPI;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/3/15.
 *
 * 个人工作主页的adapter
 *
 */
public class PersonHomeAdapter extends BaseAdapter {

    private ArrayList<UserKPI>  data;

    public PersonHomeAdapter(ArrayList<UserKPI> data) {
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
        PersonHomeViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.personhome_item,null,false);
            holder = new PersonHomeViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (PersonHomeViewHolder) convertView.getTag();
        holder.setData(data.get(position));
        return convertView;
    }


    class PersonHomeViewHolder{

        public TextView modelNameTextView;//模块的姓名
        public TextView tatolTextView;//总条数
        public ProgressBar progressBar;//进度条

        public PersonHomeViewHolder(View root) {
            modelNameTextView = (TextView) root.findViewById(R.id.personhome_item_name);
            tatolTextView = (TextView) root.findViewById(R.id.personhome_item_tatol);
//            progressBar = (ProgressBar) root.findViewById(R.id.personhome_item_progressbar);
        }

        public void setData(UserKPI data){
            modelNameTextView.setText(data.getTitle());
            tatolTextView.setText(data.getNum());
            //progressBar.setVisibility(View.GONE);
        }

    }

}
