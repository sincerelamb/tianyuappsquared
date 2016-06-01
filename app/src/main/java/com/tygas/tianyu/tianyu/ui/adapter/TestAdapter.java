package com.tygas.tianyu.tianyu.ui.adapter;

/**
 * Created by SJTY_YX on 2016/3/9.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;


public class TestAdapter extends BaseAdapter implements Filterable {

    private ArrayFilter mFilter;
    private List<String> mList;
    private Context context;
    private ArrayList<String> mUnfilteredData;

    public TestAdapter(List<String> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    public void setmUnfilteredData(ArrayList<String> mUnfilteredData) {
        this.mUnfilteredData = new ArrayList<>(mUnfilteredData);
    }

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
        View view;
        ViewHolder holder;
        if(convertView==null){
            view = View.inflate(context, R.layout.test_item, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) view.findViewById(R.id.test_item_tv_name);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }


        holder.tv_name.setText(mList.get(position));

        return view;
    }

    static class ViewHolder{
        public TextView tv_name;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private class ArrayFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            Log.i("test","performFiltering");
            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList<String>(mList);
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<String> list = mUnfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();

                ArrayList<String> unfilteredValues = mUnfilteredData;
                int count = unfilteredValues.size();

                ArrayList<String> newValues = new ArrayList<String>(count);

                for (int i = 0; i < count; i++) {
                    if(unfilteredValues.get(i).startsWith(prefixString)){
                        newValues.add(unfilteredValues.get(i));
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            //noinspection unchecked
            mList = (List<String>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }//end ArrayFilter
}
