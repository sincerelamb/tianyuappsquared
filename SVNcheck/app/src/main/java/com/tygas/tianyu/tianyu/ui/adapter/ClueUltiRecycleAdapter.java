package com.tygas.tianyu.tianyu.ui.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.PhonStateLisen;
import com.tygas.tianyu.tianyu.ui.model.Clue;
import com.tygas.tianyu.tianyu.ui.model.PtCustomer;
import com.tygas.tianyu.tianyu.ui.view.activity.ClueActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.ClueDetailActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.ClueReturnVisitFail;
import com.tygas.tianyu.tianyu.utils.PhoneValidate;
import com.tygas.tianyu.tianyu.utils.RecordFile;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/18.
 */
public class ClueUltiRecycleAdapter extends UltimateViewAdapter<ClueUltiRecycleAdapter.ClueViewHodler> {

    private ArrayList<PtCustomer> data;
    private ClueActivity clueActivity;

    private static final String LOG_TAG = "ClueUltiRecycleAdapter";

    public ClueUltiRecycleAdapter(ClueActivity clueActivity, ArrayList<PtCustomer> data) {
        this.data = data;
        this.clueActivity = clueActivity;
    }

    @Override
    public ClueViewHodler getViewHolder(View view) {
        return new ClueViewHodler(view);
    }

    @Override
    public ClueViewHodler onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clue_item, parent, false);
        ClueViewHodler vh = new ClueViewHodler(v);
        return vh;
    }

    @Override
    public int getAdapterItemCount() {
        return data.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(ClueViewHodler holder, int position) {
        if (position >= data.size()) {
            return;
        }
        final PtCustomer clue = data.get(position);
        holder.setValueFromClue(clue);
        //设置点击事件
        holder.leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击进入详细信息
                Intent intent = new Intent();
                intent.putExtra("data", clue);
                intent.setClass(clueActivity, ClueDetailActivity.class);
                clueActivity.startActivity(intent);
            }
        });
        holder.rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击进行拨打电话
                TelephonyManager manager = (TelephonyManager) clueActivity.getSystemService(Context.TELEPHONY_SERVICE);
                if (manager.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK) {
                    Toast.makeText(clueActivity, "不能同时播出多个号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!PhoneValidate.validateNumber(clue.getCustomerPhone())) {
                    Toast.makeText(clueActivity, "电话号码错误,请修改后再回访", Toast.LENGTH_SHORT).show();
                    /*Intent intent = new Intent();
                    intent.putExtra("data", clue);
                    intent.putExtra("path", "  ");
                    intent.setClass(clueActivity, ClueReturnVisitFail.class);

                    ClueReturnVisitFail.clueActivity = clueActivity;
                    clueActivity.startActivity(intent);*/

                } else {
                    if (ActivityCompat.checkSelfPermission(clueActivity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(clueActivity, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + clue.getCustomerPhone());
                        intent.setData(data);
                        RecordFile recordFile = new RecordFile(clueActivity, "clue" + clue.getCustomerName() + clue.getCustomerPhone());
                        PhonStateLisen.setParamsForPtCustomer(clueActivity, clue,
                                recordFile.getFilePath(), recordFile.getRecordFile(),1,2);
                        clueActivity.startActivity(intent);//xiugai
                    } else {
                        Toast.makeText(clueActivity, "该应用没有允许打电话权限或读取通话记录，请在设应用程序权限管理中将打开！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }//end

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class ClueViewHodler extends UltimateRecyclerviewViewHolder {

        public TextView nameTextView;
        public TextView phoneTextView;
        public TextView levelTextView;
        public TextView lastTextView;
        public TextView callbackTextView;
        public TextView loveCar;
        public TextView talk;
        public TextView empname;

        public View leftView;
        public View rightView;

        public ClueViewHodler(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.clue_item_tv_name);
            phoneTextView = (TextView) itemView.findViewById(R.id.clue_item_tv_phone);
            levelTextView = (TextView) itemView.findViewById(R.id.clue_item_tv_level);
            lastTextView = (TextView) itemView.findViewById(R.id.clue_item_tv_lasttime);
            callbackTextView = (TextView) itemView.findViewById(R.id.clue_item_tv_nexttime);
            loveCar = (TextView) itemView.findViewById(R.id.clue_item_tv_lovecar);
            talk = (TextView) itemView.findViewById(R.id.clue_item_tv_talk);
            empname = (TextView) itemView.findViewById(R.id.clue_item_tv_xiaoshouguwen_context);
            leftView = itemView.findViewById(R.id.clue_item_ll_left);
            rightView = itemView.findViewById(R.id.clue_item_iv_right);
        }

        public void setValueFromClue(PtCustomer c) {
            nameTextView.setText(c.getCustomerName());
            phoneTextView.setText(c.getCustomerPhone());
            levelTextView.setText(c.getIntentLevel());
            lastTextView.setText(c.getLastContratTime());
            callbackTextView.setText(c.getNextCallTime());
            loveCar.setText(c.getCarseriesName());
            talk.setText(c.getLastTalkProcess());
            empname.setText(c.getEmpName());
        }

    }//end class PtCustomerViewHodler
}
