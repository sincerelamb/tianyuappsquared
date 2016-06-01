package com.tygas.tianyu.tianyu.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.data.SharedPreferencesDate;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.model.PID;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;
import com.tygas.tianyu.tianyu.ui.view.activity.AddNewOutreachActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.ClueActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.CpCustomActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.FirstProtectActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.MainActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.PersonHomePageActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.PtCustomersActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.RecoderTextActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.TimingProtectActivity;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.JsonParser;
import com.tygas.tianyu.tianyu.utils.ProgressDialogHelper;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/3/10.
 */
public class MainGvAdapter extends BaseAdapter {

    private Context context;
    private List<UserPtInfoModel> list;
    private List<PID> list_pid;
    private LayoutInflater inflate;
    private String nowStr;
    private String yesterdayStr;


    //用户权限常量
    public static final String PT_LIST = "AndroidApp_search_customer";
    public static final String CLUE_LIST = "AndroidApp_search_clue";
    public static final String CP_LIST = "AndroidApp_repair_customer";
    public static final String NO_LIST = "AndroidApp_repair_AddCus";
    public static final String FP_LIST = "AndroidApp_aft_Edit";
    public static final String TP_LIST = "AndroidApp_sale_Edit";
    public static final String KPI_LIST = "AndroidApp_userkpi_Edit";

    public void setListTotal(List<UserPtInfoModel> list) {
        this.list = list;
    }


    public MainGvAdapter(Context context, List<UserPtInfoModel> list, List<PID> list_pid) {
        this.context = context;
        this.list = list;
        this.list_pid = list_pid;
        inflate = LayoutInflater.from(context);
        Calendar now = Calendar.getInstance();
        nowStr = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        yesterdayStr = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + (now.get(Calendar.DAY_OF_MONTH) - 1);

    }

    @Override
    public int getCount() {
        return list_pid.size();
    }

    @Override
    public PID getItem(int position) {
        return list_pid.get(position);
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
        if (list_pid.size() > 0) {
            final PID pid = list_pid.get(position);
            switch (pid.getFormID()) {
                case PT_LIST:
                    holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.gv_pt_customer_selector));
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent_pt_today = new Intent(context, PtCustomersActivity.class);
                            intent_pt_today.putExtra("type", 100);
                            context.startActivity(intent_pt_today);
                        }
                    });
                    if (list != null && list.size() > 0) {
                        for (UserPtInfoModel userPtInfoModel : list) {
                            if ("潜客列表".equals(userPtInfoModel.getName())) {
                                if (Integer.parseInt(userPtInfoModel.getID()) > 99) {
                                    holder.textView.setText("99+");
                                } else {
                                    holder.textView.setText(userPtInfoModel.getID());
                                }
                            }
                        }
                    }

                    break;
                case CLUE_LIST:
                    holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.gv_clue_customer_selector));
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent_clue_today = new Intent(context, ClueActivity.class);
                            intent_clue_today.putExtra("type", 100);
                            context.startActivity(intent_clue_today);
                        }
                    });
                    for (UserPtInfoModel userPtInfoModel : list) {
                        if ("线索列表".equals(userPtInfoModel.getName())) {
                            if (Integer.parseInt(userPtInfoModel.getID()) > 99) {
                                holder.textView.setText("99+");
                            } else {
                                holder.textView.setText(userPtInfoModel.getID());
                            }
                        }
                    }
                    break;
                case CP_LIST:
                    holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.gv_cp_customer_selector));
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent_cp_today = new Intent(context, CpCustomActivity.class);
                            intent_cp_today.putExtra("fromtime", nowStr);
                            intent_cp_today.putExtra("totime", nowStr);
                            intent_cp_today.putExtra("issupplestate", "0");
                            intent_cp_today.putExtra("customername", "");
                            intent_cp_today.putExtra("customerphone", "");
                            intent_cp_today.putExtra("guwen", "");
                            context.startActivity(intent_cp_today);
                        }
                    });
                    for (UserPtInfoModel userPtInfoModel : list) {
                        if ("补全列表".equals(userPtInfoModel.getName())) {
                            if (Integer.parseInt(userPtInfoModel.getID()) > 99) {
                                holder.textView.setText("99+");
                            } else {
                                holder.textView.setText(userPtInfoModel.getID());
                            }
                        }
                    }
                    break;
                case FP_LIST:
                    for (UserPtInfoModel userPtInfoModel : list) {
                        if ("首保列表".equals(userPtInfoModel.getName())) {
                            if (Integer.parseInt(userPtInfoModel.getID()) > 99) {
                                holder.textView.setText("99+");
                            } else {
                                holder.textView.setText(userPtInfoModel.getID());
                            }
                        }
                    }
                    holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.gv_fp_customer_selector));
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent_fp_today = new Intent(context, FirstProtectActivity.class);
                            intent_fp_today.putExtra("fromtime", nowStr);
                            intent_fp_today.putExtra("totime", nowStr);
                            intent_fp_today.putExtra("isoverdue", "");
                            intent_fp_today.putExtra("CarOwnerName", "");
                            intent_fp_today.putExtra("CarOwnerPhone", "");
                            intent_fp_today.putExtra("ServiceEmpName", "");
                            intent_fp_today.putExtra("CarInfo", "");
                            context.startActivity(intent_fp_today);
                        }
                    });

                    break;
                case TP_LIST:
                    for (UserPtInfoModel userPtInfoModel : list) {
                        if ("定保列表".equals(userPtInfoModel.getName())) {
                            if (Integer.parseInt(userPtInfoModel.getID()) > 99) {
                                holder.textView.setText("99+");
                            } else {
                                holder.textView.setText(userPtInfoModel.getID());
                            }
                        }
                    }
                    holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.gv_tp_customer_selector));
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent_tf_today = new Intent(context, TimingProtectActivity.class);
                            intent_tf_today.putExtra("fromtime", nowStr);
                            intent_tf_today.putExtra("totime", nowStr);
                            intent_tf_today.putExtra("isoverdue", "");
                            intent_tf_today.putExtra("CarOwnerName", "");
                            intent_tf_today.putExtra("CarOwnerPhone", "");
                            intent_tf_today.putExtra("ServiceEmpName", "");
                            intent_tf_today.putExtra("CarInfo", "");
                            context.startActivity(intent_tf_today);
                        }
                    });

                    break;
                case NO_LIST:
                    holder.textView.setVisibility(View.GONE);
                    holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.gv_newadd_customer_selector));
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent_newout = new Intent(context, AddNewOutreachActivity.class);
                            context.startActivity(intent_newout);
                        }
                    });
                    break;


                case KPI_LIST:
                    holder.textView.setVisibility(View.GONE);
                    holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.gv_owen_work_selector));
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent_person_work = new Intent(context, PersonHomePageActivity.class);
                            context.startActivity(intent_person_work);
                        }
                    });

                    break;
                case "problem":

                    holder.textView.setVisibility(View.GONE);
                    if (!context.getSharedPreferences(SharedPreferencesDate.SHAREDPREFERENCES_NAME_FIRST_PREF, Context.MODE_PRIVATE).getBoolean(SharedPreferencesDate.ISRECODERPROBLEM, false)) {
                        holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.gv_recoder_problem));
                    } else {
                        holder.imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.yifankui));
                    }
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent_text = new Intent(context, RecoderTextActivity.class);
                            context.startActivity(intent_text);
                        }
                    });
                    break;
            }


        }


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