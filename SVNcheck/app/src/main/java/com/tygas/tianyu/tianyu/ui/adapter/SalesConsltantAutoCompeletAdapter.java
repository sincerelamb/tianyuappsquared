package com.tygas.tianyu.tianyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.model.SalesConsltant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SJTY_YX on 2016/3/9.
 * 销售顾问的 AutoCompeletTextView的适配器
 *
 */
public class SalesConsltantAutoCompeletAdapter extends BaseAdapter implements Filterable {


    private SalesFilter mFilter;
    private List<SalesConsltant> mList;
    private ArrayList<SalesConsltant> mUnfilteredData;

    public SalesConsltantAutoCompeletAdapter(List<SalesConsltant> mList) {
        this.mList = mList;
    }

    public void setmUnfilteredData(ArrayList<SalesConsltant> xmUnfilteredData) {
        mUnfilteredData = new ArrayList<>(xmUnfilteredData);
    }//end setmUnfilteredData

    @Override
    public int getCount() {
        return mList==null ? 0:mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item,null);
            SalesConsltantViewHolder holder = new SalesConsltantViewHolder();
            holder.nameTextView = (TextView) convertView.findViewById(R.id.test_item_tv_name);
            convertView.setTag(holder);
        }
        SalesConsltantViewHolder holder = (SalesConsltantViewHolder) convertView.getTag();
        holder.nameTextView.setText(mList.get(position).getName());
        return convertView;
    }

    private class SalesConsltantViewHolder{
        public TextView nameTextView;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new SalesFilter();
        }
        return mFilter;
    }

    private class SalesFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList<SalesConsltant>(mList);
            }

            if (prefix == null || prefix.length() == 0 || "".equals(prefix.toString().trim())) {
                ArrayList<SalesConsltant> list = mUnfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();

                ArrayList<SalesConsltant> unfilteredValues = mUnfilteredData;
                int count = unfilteredValues.size();

                ArrayList<SalesConsltant> newValues = new ArrayList<SalesConsltant>(count);

                for (int i = 0; i < count; i++) {
                    if(unfilteredValues.get(i).getName().contains(prefixString)){
                        newValues.add(unfilteredValues.get(i));
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mList = (List<SalesConsltant>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }//end SalesFilter

}
