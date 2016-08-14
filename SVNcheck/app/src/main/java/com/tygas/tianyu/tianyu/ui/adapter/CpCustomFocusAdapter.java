package com.tygas.tianyu.tianyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/22.
 */
public class CpCustomFocusAdapter extends BaseAdapter {

    private Context context;
    private List<UserPtInfoModel> list;
    private LayoutInflater inflate;

    public List<Boolean> getList_checkinfo() {
        return list_checkinfo;
    }

    public void setList_checkinfo(List<Boolean> list_checkinfo) {
        this.list_checkinfo = list_checkinfo;
    }


    private List<Boolean> list_checkinfo = new ArrayList<Boolean>();

    public CpCustomFocusAdapter(Context context, List<UserPtInfoModel> list) {
        this.context = context;
        this.list = list;
        inflate = LayoutInflater.from(context);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                list_checkinfo.add(false);
            }
        }

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public UserPtInfoModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        FocuViewHolder holder;
        if (null == convertView) {
            convertView = inflate.inflate(R.layout.ptcustomer_focus_item_layout, null);
            holder = new FocuViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (FocuViewHolder) convertView.getTag();
        holder.textView.setText(list.get(position).getName());
        holder.checkBox.setChecked(list_checkinfo.get(position));
        final FocuViewHolder finalHolder = holder;
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list_checkinfo.get(position)) {
                    list_checkinfo.set(position, false);
                    finalHolder.checkBox.setChecked(false);
                } else {
                    list_checkinfo.set(position, true);
                    finalHolder.checkBox.setChecked(true);
                }
            }
        });
//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                list_checkinfo.set(position, isChecked);
//            }
//        });
        return convertView;
    }

    class FocuViewHolder {
        TextView textView;
        CheckBox checkBox;
        LinearLayout ll;

        public FocuViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.customersinfo_fouces_item_tv);
            checkBox = (CheckBox) view.findViewById(R.id.customersinfo_fouces_item_cb);
            ll = (LinearLayout) view.findViewById(R.id.customersinfo_fouces_itemll);
        }
    }

    public void setFocuItem(int[] item) {

    }

}
