package com.tygas.tianyu.tianyu.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.model.CustomerProcess;
import com.tygas.tianyu.tianyu.ui.model.PtCustomer;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/1/20.
 */
public class CustomersProcessAdapter extends BaseAdapter {

    private ArrayList<CustomerProcess> datas;
    private PtCustomer customer;

    public CustomersProcessAdapter(PtCustomer customer, ArrayList<CustomerProcess> datas) {
        this.customer = customer;
        this.datas = datas;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ptcustomers_process_item, null);
            CustomersProgressViewHolder customersProgressViewHolder = new CustomersProgressViewHolder(convertView);
            convertView.setTag(customersProgressViewHolder);
        }
        CustomersProgressViewHolder customersProgressViewHolder = (CustomersProgressViewHolder) convertView.getTag();
        customersProgressViewHolder.setValue(datas.get(position));
        Log.d("datas", datas.get(position).getEmpName() + "dsdasda");
        return convertView;
    }


    class CustomersProgressViewHolder {
        private TextView name;
        private TextView level;
        private TextView callTime;
        private TextView nextTime;
        private TextView recode;
        private TextView empName;

        public CustomersProgressViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.ptcustomers_process_item_name);
            level = (TextView) view.findViewById(R.id.ptcustomers_process_item_level);
            callTime = (TextView) view.findViewById(R.id.ptcustomers_process_item_time);
            nextTime = (TextView) view.findViewById(R.id.ptcustomers_process_item__next_time);
            recode = (TextView) view.findViewById(R.id.ptcustomers_process_item_recode);
            empName = (TextView) view.findViewById(R.id.ptcustomers_process_item_job);
        }

        public void setValue(CustomerProcess process) {
            name.setText(customer.getCustomerName());
            level.setText(process.getIntentLevel());
            callTime.setText(process.getCallTime());
            nextTime.setText(process.getNextCallTime());
            recode.setText(process.getTalkProcess());
            empName.setText("接触人:" + process.getEmpName());
            Log.d("接触", "ss" + process.getEmpName());
        }
    }

}
