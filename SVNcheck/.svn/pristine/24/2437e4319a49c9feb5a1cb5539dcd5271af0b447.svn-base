package com.tygas.tianyu.tianyu.ui.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.model.PersonHomeSetModel;
import com.tygas.tianyu.tianyu.ui.model.UserKPI;
import com.tygas.tianyu.tianyu.ui.model.UserKPIS;
import com.tygas.tianyu.tianyu.ui.view.activity.PersonHomePageActivity;
import com.tygas.tianyu.tianyu.utils.DbUtilsHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SJTY_YX on 2016/3/15.
 */
public class PersonHomeSetAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {

    private List<UserKPIS> data;
    private Context context;
    private static final String LOG_TAG = "PersonHomeSetAdapter";
    private DbUtils dbUtils;

    public PersonHomeSetAdapter(List<UserKPIS> data, Context context) {
        this.data = data;
        this.context = context;
        dbUtils = DbUtilsHelper.newInstance(context);

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public UserKPIS getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PersonHomeSetViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.personhomeset_item, parent, false);
            holder = new PersonHomeSetViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (PersonHomeSetViewHolder) convertView.getTag();
        holder.setData(data.get(position));
      //  holder.stateCheckBox.setChecked(data.get(position).isShow());
        holder.stateCheckBox.setOnCheckedChangeListener(this);
        holder.stateCheckBox.setTag(data.get(position));
        return convertView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        UserKPIS model = (UserKPIS) buttonView.getTag();
        model.setIsShow(isChecked);

        try {
            dbUtils.update(model, WhereBuilder.b("title", "=", model.getTitle()));
           // dbUtils.upda
        } catch (DbException e) {
            e.printStackTrace();
        }
        //  saveToFile(data);
    }

    private void saveToFile(ArrayList<UserKPI> allKpis) {
        String result = "";
        for (int i = 0; i < allKpis.size(); i++) {
            result += allKpis.get(i).toString() + ",";
        }
        result = result.endsWith(",") ? result.substring(0, result.length()) : result;
        SharedPreferences ps = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = ps.edit();
        editor.putString(PersonHomePageActivity.SAVE_TAG, result);
        editor.commit();
    }//end saveToFile

    class PersonHomeSetViewHolder {
        public TextView nameTextView;
        public CheckBox stateCheckBox;

        public PersonHomeSetViewHolder(View root) {
            nameTextView = (TextView) root.findViewById(R.id.personhomeset_item_name);
            stateCheckBox = (CheckBox) root.findViewById(R.id.personhomeset_item_state);
        }

        public void setData(UserKPIS data) {
            nameTextView.setText(data.getTitle());
            stateCheckBox.setOnCheckedChangeListener(null);
            stateCheckBox.setChecked(data.isShow() ? true : false);
        }
    }
}
