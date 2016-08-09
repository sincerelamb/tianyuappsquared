package com.tygas.tianyu.tianyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.model.MaintenanceHistory;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/3/2.
 */
public class FpHistoryAdapter extends BaseAdapter {

    private List<MaintenanceHistory> list;
    private Context context;
    private LayoutInflater inflate;

    public FpHistoryAdapter(Context context, List<MaintenanceHistory> list) {
        this.context = context;
        this.list = list;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MaintenanceHistory getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        FpHisViewHolder holder;
        if (null == convertView) {
            convertView = inflate.inflate(R.layout.fphistory_layout_item, null);
            holder = new FpHisViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (FpHisViewHolder) convertView.getTag();
        MaintenanceHistory maintenanceHistory = list.get(position);
        holder.tv_maintenance_date.setText(maintenanceHistory.getReciveComeDate());
        holder.tv_receptionpersonnel.setText(maintenanceHistory.getServiceEmpName());
        holder.tv_worktype.setText(maintenanceHistory.getReciveTypeName());

        return convertView;

    }

    class FpHisViewHolder {
        private TextView tv_maintenance_date;
        private TextView tv_receptionpersonnel;
        private TextView tv_worktype;

        FpHisViewHolder(View view) {
            tv_maintenance_date = (TextView) view.findViewById(R.id.item_fphistory_date_context);
            tv_receptionpersonnel = (TextView) view.findViewById(R.id.item_fphistory_tv_receptionpersonnel);
            tv_worktype = (TextView) view.findViewById(R.id.item_fphistory_tv_worktype);
        }
    }

}
