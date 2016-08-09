package com.tygas.tianyu.tianyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;

import java.util.List;

/**
 * Created by Administrator on 2016/1/22.
 */
public class CpDialogAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private LayoutInflater inflate;

    public CpDialogAdapter(Context context, List<String> list) {
        this.list = list;
        this.context = context;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DialogViewHolder holder;
        if (null == convertView) {
            convertView = inflate.inflate(R.layout.ptcustom_dialog_item_layout, null);
            holder = new DialogViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (DialogViewHolder) convertView.getTag();
        holder.textView.setText(list.get(position));
        return convertView;
    }

    class DialogViewHolder {
        TextView textView;

        public DialogViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.cp_dialog_item_tv);
        }
    }
}
