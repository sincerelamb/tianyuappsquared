package com.tygas.tianyu.tianyu.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.adapter.CpDialogAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/1/22.
 */
public class MyDialogHelper {

    //Dialog窗体显示
    public void showListDialog(Context context, String message, final List<String> list, final TextView textView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.ptcustomer_dialog_layout, null);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        ListView lv = (ListView) view.findViewById(R.id.cpcustom_dialog_lv);
        lv.setAdapter(new CpDialogAdapter(context, list));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(list.get(position));
                alertDialog.dismiss();
            }
        });
        TextView title = (TextView) view.findViewById(R.id.cpcustom_dialog_title_tv);
        title.setText(message);


    }


    //Dialog窗体显示
    public void showListDialog(Context context, String message, final List<String> list, final TextView textView, final DialogCallBack callBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.ptcustomer_dialog_layout, null);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        ListView lv = (ListView) view.findViewById(R.id.cpcustom_dialog_lv);
        lv.setAdapter(new CpDialogAdapter(context, list));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(list.get(position));
                alertDialog.dismiss();
                callBack.callBack(position);
            }
        });
        TextView title = (TextView) view.findViewById(R.id.cpcustom_dialog_title_tv);
        title.setText(message);
    }


    public interface DialogCallBack {
        public void callBack(int position);
    }

}
