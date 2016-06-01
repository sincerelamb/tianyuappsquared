package com.tygas.tianyu.tianyu.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.tygas.tianyu.tianyu.R;

/**
 * Created by Administrator on 2016/1/18.
 */
public class MyUltiRecycleAdapter extends UltimateViewAdapter<MyUltiRecycleAdapter.MyViewHodler> {


    @Override
    public MyViewHodler getViewHolder(View view) {
        return new MyViewHodler(view);
    }



    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.myultirecycle_item, parent, false);
        MyViewHodler vh = new MyViewHodler(v);
        return vh;
    }

    @Override
    public int getAdapterItemCount() {
        return 50;
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }




    @Override
    public void onBindViewHolder(MyViewHodler holder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {

        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class MyViewHodler extends UltimateRecyclerviewViewHolder {
        private TextView textView;
        public MyViewHodler(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.text_rl);
        }

    }

}
