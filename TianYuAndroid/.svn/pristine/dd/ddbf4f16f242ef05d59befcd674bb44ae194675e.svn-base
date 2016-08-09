package com.tygas.tianyu.tianyu.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.model.PartsDetail;
import com.tygas.tianyu.tianyu.ui.model.RepairProject;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/3/2.
 */
public class PartsDetailAdapter extends BaseAdapter {

    private ArrayList<PartsDetail> data;

    public PartsDetailAdapter(ArrayList<PartsDetail> data) {
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
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.partsdetail_item,parent,false);
            PartsDetailViewHolder holder = new PartsDetailViewHolder(convertView);
            convertView.setTag(holder);
        }
        PartsDetailViewHolder holder = (PartsDetailViewHolder) convertView.getTag();
        holder.setData(data.get(position));
        return convertView;
    }

    class PartsDetailViewHolder{
        public TextView partNameTextView;//配件名称
        public TextView partCodeTextView;//配件编码
        public TextView trueSalePriceTotalTextView;//配件的价格

        public PartsDetailViewHolder(View root) {
            partNameTextView = (TextView) root.findViewById(R.id.partsdetail_item_partName);
            partCodeTextView = (TextView) root.findViewById(R.id.partsdetail_item_partCode);
            trueSalePriceTotalTextView = (TextView) root.findViewById(R.id.partsdetail_item_trueSalePriceTotal);
        }

        public void setData(PartsDetail data){
            partNameTextView.setText(data.getPartName());
            partCodeTextView.setText(data.getPartCode());
            trueSalePriceTotalTextView.setText(data.getTrueSalePriceTotal());
        }
    }//end PartdDeatilViewHolder

}

