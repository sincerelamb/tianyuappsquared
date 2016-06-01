package com.tygas.tianyu.tianyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;

import java.util.List;

/**
 * Created by Administrator on 2016/3/10.
 */
public class MainGvAdapter extends BaseAdapter {

    private Context context;
    private List<Integer> list;
    private LayoutInflater inflate;

    public MainGvAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Integer getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (null == convertView) {
            convertView = inflate.inflate(R.layout.activity_main_gv_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();


        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.item_main_iv);
            textView = (TextView) view.findViewById(R.id.item_main_tv_total);
        }
    }


}
