package com.tygas.tianyu.tianyu.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.model.PtCustomer;
import com.tygas.tianyu.tianyu.ui.model.UserKPI;
import com.tygas.tianyu.tianyu.ui.model.UserKPIS;
import com.tygas.tianyu.tianyu.ui.view.activity.PersonHomePageActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/30.
 */
public class PersonHomeUltiRecycleAdapter extends UltimateViewAdapter<PersonHomeUltiRecycleAdapter.PersonHomeViewHolder> {
    private ArrayList<UserKPIS> data;
    private Context activity;

    public PersonHomeUltiRecycleAdapter(ArrayList<UserKPIS> data, Context activity){
        this.data = data;
        this.activity = activity;
    }

    @Override
    public PersonHomeViewHolder getViewHolder(View view) {
        return new PersonHomeViewHolder(view);
    }

    @Override
    public PersonHomeViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.personhome_item, parent, false);
        PersonHomeViewHolder vh = new PersonHomeViewHolder(v);
        return vh;
    }

    @Override
    public int getAdapterItemCount() {
        return data.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(PersonHomeViewHolder holder, int position) {
        if (position >= data.size()) {
            return;
        }
        holder.setData(data.get(position));
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class PersonHomeViewHolder extends UltimateRecyclerviewViewHolder {

        public TextView modelNameTextView;//模块的姓名
        public TextView tatolTextView;//总条数

        public PersonHomeViewHolder(View root) {
            super(root);
            modelNameTextView = (TextView) root.findViewById(R.id.personhome_item_name);
            tatolTextView = (TextView) root.findViewById(R.id.personhome_item_tatol);
        }
        public void setData(UserKPIS data) {
            modelNameTextView.setText(data.getTitle());
            tatolTextView.setText(data.getNum());
            //progressBar.setVisibility(View.GONE);
        }

    }
}
