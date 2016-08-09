package com.tygas.tianyu.tianyu.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.model.RepairProject;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/3/2.
 */
public class RepairProjectAdapter extends BaseAdapter {

    private ArrayList<RepairProject> data;

    public RepairProjectAdapter(ArrayList<RepairProject> data) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.repairproject_item,parent,false);
            RepairProjectViewHolder holder = new RepairProjectViewHolder(convertView);
            convertView.setTag(holder);
        }
        RepairProjectViewHolder holder = (RepairProjectViewHolder) convertView.getTag();
        holder.setData(data.get(position));
        return convertView;
    }


    class RepairProjectViewHolder{
        public TextView projectNameTextView;//项目名称
        public TextView amountTotalTextView;//价格
        public TextView serviceGroupNameTextView;//维修班组

        public RepairProjectViewHolder(View root) {
            projectNameTextView = (TextView) root.findViewById(R.id.repairproject_item_projectName);
            amountTotalTextView = (TextView) root.findViewById(R.id.repairproject_item_amountTotal);
            serviceGroupNameTextView = (TextView) root.findViewById(R.id.repairproject_item_serviceGroupName);
        }

        public void setData(RepairProject data){
            projectNameTextView.setText(data.getProjectName());
            amountTotalTextView.setText(data.getAmountTotal());
            serviceGroupNameTextView.setText(data.getServiceGroupName());
        }
    }//end RepairProjectViewHolder

}
