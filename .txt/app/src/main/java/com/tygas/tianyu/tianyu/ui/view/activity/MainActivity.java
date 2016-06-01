package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.data.SharedPreferencesDate;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.service.RecordService;
import com.tygas.tianyu.tianyu.service.UpVersonService;
import com.tygas.tianyu.tianyu.ui.adapter.MainGvAdapter;
import com.tygas.tianyu.tianyu.ui.model.PID;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;
import com.tygas.tianyu.tianyu.ui.view.customview.FocuGirdView;
import com.tygas.tianyu.tianyu.utils.DbUtilsHelper;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.JsonParser;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends Activity {

    //主页面条目列表
    private RelativeLayout rl_ptcustomers;
    private RelativeLayout rl_clue;
    private RelativeLayout rl_cpcustomers;
    private RelativeLayout rl_newoutcustomers;
    private RelativeLayout rl_fpcustomers;
    private RelativeLayout rl_tpcustomers;

    //条目列表数目
    private TextView rv_ptcustomers_today_tatol;
    private TextView rv_ptcustomers_his_tatol;
    private TextView rv_clue_today_tatol;
    private TextView rv_clue_his_tatol;
    private TextView rv_cpcustomers_today_tatol;
    private TextView rv_cpcustomers_his_tatol;
    private TextView tv_fpcustomers_today_talol;
    private TextView tv_fpcustomers_his_talol;
    private TextView tv_tpcustomers_today_talol;
    private TextView tv_tpcustomers_his_talol;


    private FocuGirdView girdView;

    //退出登录
    private TextView tv_zhanghao;

    //用户权限常量
    private static final String PT_LIST = "AndroidApp_search_customer";
    private static final String CLUE_LIST = "AndroidApp_search_clue";
    private static final String CP_LIST = "AndroidApp_repair_customer";
    private static final String NO_LIST = "AndroidApp_repair_AddCus";
    private static final String FP_LIST = "AndroidApp_aft_Edit";
    private static final String TP_LIST = "AndroidApp_sale_Edit";

    //数据库   （缓存列目条数数据）
    private DbUtils dbUtils;
    //用户配置数据
    private User user;
    //定义广播  用于更新列表条目数
    private RecevieMessageUpDataUIBroad recevieMessageUpDataUIBroad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_main);
        //初始化数据库
        dbUtils = DbUtilsHelper.newInstance(this);
        //获取用户配置信息
        user = ((MyAppCollection) getApplicationContext()).getUser();
        //注册广播
        initBroad();
        //初始化控件
        initView();
        //初始化数据
        initdata();
        //获取版本号
        getUrlPackageCode();

        //开启录音服务
        Intent intent = new Intent();
        intent.setClass(this, RecordService.class);
        startService(intent);
    }

    //每次回到次页面时候刷新列表项的条数
    @Override
    protected void onStart() {
        super.onStart();
        HttpUtilsHelper.downLoadUpdataUI(MainActivity.this, false, 0, false, false, false);
    }

    private void initBroad() {
        //生成广播处理
        recevieMessageUpDataUIBroad = new RecevieMessageUpDataUIBroad();
        //实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter("ty.updataUI");
        //注册广播
        this.registerReceiver(recevieMessageUpDataUIBroad, intentFilter);
    }

    private void initdata() {
        List<UserPtInfoModel> list_userNum = user.getList_UserNum();
        infilterupdataUI(list_userNum);
    }

    //根据缓存列表条目数更新UI
    private void infilterupdataUI(List<UserPtInfoModel> list_userNum) {
        if (list_userNum != null && list_userNum.size() > 0) {
            for (UserPtInfoModel userPtInfoModel : list_userNum) {

                String id = userPtInfoModel.getID();
                if (Integer.parseInt(id) > 999) {
                    id = "999+";
                }
                String pid = userPtInfoModel.getPID();
                if (Integer.parseInt(pid) > 999) {
                    pid = "999+";
                }

                switch (userPtInfoModel.getName()) {
                    case "潜客列表":

                        rv_ptcustomers_today_tatol.setText(id);
//                        if ("0".equals(id)) {
//                            rv_ptcustomers_today_tatol.setVisibility(View.GONE);
//                        } else {
//                            rv_ptcustomers_today_tatol.setVisibility(View.VISIBLE);
//                        }

                        rv_ptcustomers_his_tatol.setText(pid);
//                        if ("0".equals(pid)) {
//                            rv_ptcustomers_his_tatol.setVisibility(View.GONE);
//                        } else {
//                            rv_ptcustomers_his_tatol.setVisibility(View.VISIBLE);
//                        }


                        break;
                    case "线索列表":
//                        rv_clue_today_tatol.setText(userPtInfoModel.getID() + "");
//                        rv_clue_his_tatol.setText(userPtInfoModel.getPID() + "");

                        rv_clue_today_tatol.setText(id);
//                        if ("0".equals(id)) {
//                            rv_clue_today_tatol.setVisibility(View.GONE);
//                        } else {
//                            rv_clue_today_tatol.setVisibility(View.VISIBLE);
//                        }

                        rv_clue_his_tatol.setText(pid);
//                        if ("0".equals(pid)) {
//                            rv_clue_his_tatol.setVisibility(View.GONE);
//                        } else {
//                            rv_clue_his_tatol.setVisibility(View.VISIBLE);
//                        }

                        break;
                    case "补全列表":
//                        rv_cpcustomers_today_tatol.setText(userPtInfoModel.getID() + "");
//                        rv_cpcustomers_his_tatol.setText(userPtInfoModel.getPID() + "");

                        rv_cpcustomers_today_tatol.setText(id);
//                        if ("0".equals(id)) {
//                            rv_cpcustomers_today_tatol.setVisibility(View.GONE);
//                        } else {
//                            rv_cpcustomers_today_tatol.setVisibility(View.VISIBLE);
//                        }

                        rv_cpcustomers_his_tatol.setText(pid);
//                        if ("0".equals(pid)) {
//                            rv_cpcustomers_his_tatol.setVisibility(View.GONE);
//                        } else {
//                            rv_cpcustomers_his_tatol.setVisibility(View.VISIBLE);
//                        }
                        break;

                    case "首保列表":
                        tv_fpcustomers_today_talol.setText(id);
                        tv_fpcustomers_his_talol.setText(pid);
                        break;
                    case "定保列表":
                        tv_tpcustomers_today_talol.setText(id);
                        tv_tpcustomers_his_talol.setText(pid);
                        break;


                }
            }
        }
    }

    private void initView() {
        rl_ptcustomers = (RelativeLayout) findViewById(R.id.activity_main_rl_ptcustomers);
        rl_clue = (RelativeLayout) findViewById(R.id.activity_main_rl_clue);
        rl_cpcustomers = (RelativeLayout) findViewById(R.id.activity_main_rl_cpcustomers);
        rl_newoutcustomers = (RelativeLayout) findViewById(R.id.activity_main_rl_newoutcustomers);
        rv_ptcustomers_today_tatol = (TextView) findViewById(R.id.activity_main_tv_ptcustomers_total_today);
        rv_ptcustomers_his_tatol = (TextView) findViewById(R.id.activity_main_tv_ptcustomers_total_history);
        rv_clue_today_tatol = (TextView) findViewById(R.id.activity_main_tv_clue_total_today);
        rv_clue_his_tatol = (TextView) findViewById(R.id.activity_main_tv_clue_total_history);
        rv_cpcustomers_today_tatol = (TextView) findViewById(R.id.activity_main_tv_cpcustomers_total_today);
        rv_cpcustomers_his_tatol = (TextView) findViewById(R.id.activity_main_tv_cpcustomers_total_history);

        rl_fpcustomers = (RelativeLayout) findViewById(R.id.btn_fp);
        rl_tpcustomers = (RelativeLayout) findViewById(R.id.btn_tp);

        tv_fpcustomers_today_talol = (TextView) findViewById(R.id.activity_main_tv_fpcustomers_total_today);
        tv_fpcustomers_his_talol = (TextView) findViewById(R.id.activity_main_tv_fpcustomers_total_history);
        tv_tpcustomers_today_talol = (TextView) findViewById(R.id.activity_main_tv_tpcustomers_total_today);
        tv_tpcustomers_his_talol = (TextView) findViewById(R.id.activity_main_tv_tpcustomers_total_history);



//        girdView = (FocuGirdView) findViewById(R.id.activity_main_gv);
//
//        List<Integer> integers = new ArrayList<>();
//        integers.add(1);
//        integers.add(1);
//        integers.add(1);
//        integers.add(1);
//        integers.add(1);
//        integers.add(1);
//        integers.add(1);
//        girdView.setAdapter(new MainGvAdapter(this, integers));

        tv_zhanghao = (TextView) findViewById(R.id.activity_main_tv_zhanghao);
        tv_zhanghao.setText(((MyAppCollection) getApplicationContext()).getUser().getEmpName());
        try {
            List<PID> list_pid = dbUtils.findAll(PID.class);
            Log.d("adadada", list_pid.toString());
            for (PID pid : list_pid) {
                switch (pid.getFormID()) {
                    case PT_LIST:
                        rl_ptcustomers.setVisibility(View.VISIBLE);
                        break;
                    case CLUE_LIST:
                        rl_clue.setVisibility(View.VISIBLE);
                        break;
                    case CP_LIST:
                        rl_cpcustomers.setVisibility(View.VISIBLE);
                        break;
                    case NO_LIST:
                        rl_newoutcustomers.setVisibility(View.VISIBLE);
                        break;
                    case FP_LIST:
                        rl_fpcustomers.setVisibility(View.VISIBLE);

                        break;
                    case TP_LIST:
                        rl_tpcustomers.setVisibility(View.VISIBLE);
                        break;

                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    public void listOnclick(View view) {
        Calendar now = Calendar.getInstance();
        String nowStr = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        String yesterdayStr = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + (now.get(Calendar.DAY_OF_MONTH)-1);
        switch (view.getId()) {
            case R.id.activity_main_rl_ptcustomers:
                Intent intent_pt = new Intent(MainActivity.this, PtCustomersActivity.class);
                intent_pt.putExtra("type", 10000);
                startActivity(intent_pt);
                break;
            case R.id.activity_main_ll_ptcustomers_total_today:
                Intent intent_pt_today = new Intent(MainActivity.this, PtCustomersActivity.class);
                intent_pt_today.putExtra("type", 100);
                startActivity(intent_pt_today);
                break;
            case R.id.activity_main_ll_ptcustomers_total_history:
                Intent intent_pt_his = new Intent(MainActivity.this, PtCustomersActivity.class);
                intent_pt_his.putExtra("type", 1000);
                startActivity(intent_pt_his);
                break;

            case R.id.activity_main_rl_clue:
                Intent intent_clue = new Intent(MainActivity.this, ClueActivity.class);
                intent_clue.putExtra("type", 10000);
                startActivity(intent_clue);
                break;

            case R.id.activity_main_ll_clue_total_today:
                Intent intent_clue_today = new Intent(MainActivity.this, ClueActivity.class);
                intent_clue_today.putExtra("type", 100);
                startActivity(intent_clue_today);
                break;

            case R.id.activity_main_ll_clue_total_history:
                Intent intent_clue_his = new Intent(MainActivity.this, ClueActivity.class);
                intent_clue_his.putExtra("type", 1000);
                startActivity(intent_clue_his);
                break;


            case R.id.activity_main_rl_cpcustomers:
                Intent intent_cp = new Intent(MainActivity.this, CpCustomActivity.class);
                startActivity(intent_cp);
                break;
            case R.id.activity_main_ll_cpcustomers_total_today:
                Intent intent_cp_today = new Intent(MainActivity.this, CpCustomActivity.class);
                intent_cp_today.putExtra("fromtime", nowStr);
                intent_cp_today.putExtra("totime", nowStr);
                intent_cp_today.putExtra("issupplestate", "0");
                startActivity(intent_cp_today);
                break;

            case R.id.activity_main_ll_cpcustomers_total_history:
                Intent intent_cp_his = new Intent(MainActivity.this, CpCustomActivity.class);
                intent_cp_his.putExtra("fromtime", "");
                intent_cp_his.putExtra("totime", yesterdayStr);
                intent_cp_his.putExtra("issupplestate", "0");
                startActivity(intent_cp_his);
                break;


            case R.id.activity_main_rl_newoutcustomers:
                Intent intent_newout = new Intent(MainActivity.this, AddNewOutreachActivity.class);
                startActivity(intent_newout);
                break;


            case R.id.btn_fp:
                Intent intent_fp = new Intent(MainActivity.this, FirstProtectActivity.class);
                startActivity(intent_fp);
                break;
            case R.id.activity_main_ll_fpcustomers_total_today:
                Intent intent_fp_today = new Intent(MainActivity.this, FirstProtectActivity.class);
                intent_fp_today.putExtra("fromtime", nowStr);
                intent_fp_today.putExtra("totime", nowStr);
                intent_fp_today.putExtra("isoverdue", "");
                startActivity(intent_fp_today);
                break;
            case R.id.activity_main_ll_fpcustomers_total_history:
                Intent intent_fp_his = new Intent(MainActivity.this, FirstProtectActivity.class);
                intent_fp_his.putExtra("fromtime", "");
                intent_fp_his.putExtra("totime", "");
                intent_fp_his.putExtra("isoverdue", "逾期");
                startActivity(intent_fp_his);
                break;


            case R.id.btn_tp:
                Intent intent_tf = new Intent(MainActivity.this, TimingProtectActivity.class);
                startActivity(intent_tf);
                break;
            case R.id.activity_main_ll_tpcustomers_total_today:
                Intent intent_tf_today = new Intent(MainActivity.this, TimingProtectActivity.class);
                intent_tf_today.putExtra("fromtime", nowStr);
                intent_tf_today.putExtra("totime", nowStr);
                intent_tf_today.putExtra("isoverdue", "");
                startActivity(intent_tf_today);
                break;
            case R.id.activity_main_ll_tpcustomers_total_history:
                Intent intent_tf_his = new Intent(MainActivity.this, TimingProtectActivity.class);
                intent_tf_his.putExtra("fromtime", "");
                intent_tf_his.putExtra("totime", "");
                intent_tf_his.putExtra("isoverdue", "逾期");
                startActivity(intent_tf_his);
                break;


            case R.id.activity_main_tv_exit:
                Intent intent_login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent_login);
                getSharedPreferences(SharedPreferencesDate.SHAREDPREFERENCES_NAME_USERINFO, MODE_PRIVATE).edit().putBoolean(SharedPreferencesDate.ISLOGIN, false).commit();
                getSharedPreferences(SharedPreferencesDate.SHAREDPREFERENCES_NAME_USERINFO, MODE_PRIVATE).edit().putString(SharedPreferencesDate.USER_REAL_NAME, "").commit();

                try {
                    dbUtils.deleteAll(User.class);
                    dbUtils.deleteAll(UserPtInfoModel.class);
                    dbUtils.deleteAll(PID.class);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                finish();
                break;
            case R.id.activity_cpinfo_user_icon:
                // TODO Auto-generated method stub
                // 获取自定义布局文件activity_popupwindow_left.xml的视图

//                View popupWindow_view = getLayoutInflater().inflate(R.layout.user_info_popwindow, null, false);
//                TextView id = (TextView) popupWindow_view.findViewById(R.id.pop_id);
//                id.setText(user.getEmpId());
//                TextView name = (TextView) popupWindow_view.findViewById(R.id.pop_name);
//                name.setText(user.getEmpName());
//
//                // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
//                popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//                // popupWindow_view.setBackground();
//                // 设置动画效果
//                //  popupWindow[0].setAnimationStyle(R.style.AnimationFade);
//                if (popupWindow.isShowing()) {
//                    popupWindow.dismiss();
//                } else {
//                    popupWindow.showAsDropDown(view);
//                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(recevieMessageUpDataUIBroad);
        if (file != null && file.exists()) {
            Log.d("delete", "delete");
            file.delete();
            Intent media = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(file);
            media.setData(contentUri);
            MainActivity.this.sendBroadcast(media);
        }
        Intent intent = new Intent();
        intent.setClass(this, RecordService.class);
        this.stopService(intent);
        CpCustomActivity.isSuppleState = "";
        CpCustomActivity.starttime = null;
        CpCustomActivity.endtime = null;
        CpCustomActivity.customername = "";
        CpCustomActivity.customerphone = "";
        CpCustomActivity.guwen = "";

        PtCustomersActivity.name = "";
        PtCustomersActivity.phone = "";
        PtCustomersActivity.level = "";
        PtCustomersActivity.cusempname = "";
        PtCustomersActivity.isExpiry = "";//是否逾期
        PtCustomersActivity.begintime = null;
        PtCustomersActivity.endtime = null;

        ClueActivity.clueName = "";//线索姓名
        ClueActivity.cluePhone = "";//线索电话
        ClueActivity.cusempname = "";//员工电话
        ClueActivity.isExpiry = "";//时候逾期

        ClueActivity.level = "";//战败的等级
        ClueActivity.begintime = null;
        ClueActivity.endtime = null;


        FirstProtectActivity.DueState = "";
        FirstProtectActivity.ServiceEmpName = "";
        FirstProtectActivity.CarOwnerPhone = "";
        FirstProtectActivity.CarOwnerName = "";
        FirstProtectActivity.starttime = null;
        FirstProtectActivity.endtime = null;

        TimingProtectActivity.DueState = "";
        TimingProtectActivity.ServiceEmpName = "";
        TimingProtectActivity.CarOwnerPhone = "";
        TimingProtectActivity.CarOwnerName = "";
        TimingProtectActivity.starttime = null;
        TimingProtectActivity.endtime = null;


        super.onDestroy();

    }


    private void getUrlPackageCode() {
        HttpUtilsHelper.getInstance().send(HttpRequest.HttpMethod.POST, UrlData.VERSON_URL, XutilsRequest.getVersonCode(), new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                Log.i("veron", responseInfo.result.toString());
                final Bundle bundle = JsonParser.versionCodeParser(responseInfo.result.toString());
                Log.d("bundle", bundle.toString());
                if (bundle.getBoolean("isSucces")) {
                    try {
                        //  final int versionCode = MainActivity.this.getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
                        final String versionCodestr = MainActivity.this.getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                        double versionCode = Double.parseDouble(versionCodestr);

                        Log.d("versionCode", versionCode + "");
                        if (versionCode <
                                Double.parseDouble(bundle.getString("APPVersion"))
                                ) {
                            //发现新版本，提示用户更新
                            final String appDownLoadPath = bundle.getString("APPDownLoadPath");
                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                            alert.setCancelable(false);
                            alert.setTitle("软件升级")
                                    .setMessage("发现新版本,建议立即更新使用.")
                                    .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 创建通知
                                            createNotification();
                                            // 开始下载
                                            down(appDownLoadPath, appDownLoadPath.substring(appDownLoadPath.lastIndexOf("/") + 1));
                                        }
                                    });
//                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    });
                            if (getSharedPreferences(SharedPreferencesDate.SHAREDPREFERENCES_NAME_USERINFO, MODE_PRIVATE).getBoolean(SharedPreferencesDate.ISLOGIN, false)) {
                                alert.create().show();
                            }


                        } else {
                            //清理工作，略去
                            //cheanUpdateFile(),文章后面我会附上代码
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i("veron", s);
            }
        });
    }

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();

        }
    }


    class RecevieMessageUpDataUIBroad extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            List<UserPtInfoModel> upUIdata = (List<UserPtInfoModel>) intent.getSerializableExtra("upUIdata");
            infilterupdataUI(upUIdata);
        }
    }

    private int notification_id = 0;
    RemoteViews contentView;
    private NotificationManager notificationManager;
    private Notification notification;

    public void createNotification() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification();
        notification.icon = R.mipmap.app_icon;
        // 这个参数是通知提示闪出来的值.
        notification.tickerText = "开始下载";

        // pendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);

        // 这里面的参数是通知栏view显示的内容
        //  notification.setLatestEventInfo(this, app_name, "下载：0%", pendingIntent);

        // notificationManager.notify(notification_id, notification);

        /***
         * 在这里我们用自定的view来显示Notification
         */
        contentView = new RemoteViews(getPackageName(), R.layout.notification_item);
        contentView.setTextViewText(R.id.notificationTitle, "正在下载");
        contentView.setTextViewText(R.id.notificationPercent, "0%");
        contentView.setProgressBar(R.id.notificationProgress, 100, 0, false);

        notification.contentView = contentView;
        notificationManager.notify(notification_id, notification);
    }

    private File file;

    // 进行下载
    public void down(String url, String app_name) {


        String str = "/ " + app_name;
        String fileName = null;

        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            fileName = Environment.getExternalStorageDirectory() + str;
            file = new File(fileName);//获取跟目录
            if (file.exists()) {
                file.delete();
            }
            HttpUtilsHelper.getInstanceUpData().download(url, fileName,
                    true, true, new RequestCallBack<File>() {
                        @Override
                        public void onSuccess(ResponseInfo<File> arg0) {
                            // TODO Auto-generated method stub
                            notification.contentView.setTextViewText(R.id.notificationTitle, "下载完成");
                            notificationManager.notify(notification_id, notification);
                            upApp();
                        }


                        @Override
                        public void onLoading(long total, long current,
                                              boolean isUploading) {
                            double x_double = current * 1.0;
                            double tempresult = x_double / total;
                            DecimalFormat df1 = new DecimalFormat("0.00"); // ##.00%
                            // 百分比格式，后面不足2位的用0补齐
                            String result = df1.format(tempresult);
                            contentView.setTextViewText(R.id.notificationPercent, (int) (Float.parseFloat(result) * 100) + "%");
                            contentView.setProgressBar(R.id.notificationProgress, 100, (int) (Float.parseFloat(result) * 100), false);
                            notificationManager.notify(notification_id, notification);
                            super.onLoading(total, current, isUploading);
                        }

                        @Override
                        public void onStart() {
                            super.onStart();
                            notification.contentView.setTextViewText(R.id.notificationTitle, "准备下载");
                            notification.contentView.setProgressBar(R.id.notificationProgress, 100, 0, false);
                            notificationManager.notify(notification_id, notification);
                        }

                        @Override
                        public void onFailure(HttpException arg0, String arg1) {
                            Log.d("onFailure", arg1 + arg0.getMessage());
                            // TODO Auto-generated method stub
                            notification.contentView.setTextViewText(R.id.notificationTitle, "下载失败");
                            notificationManager.notify(notification_id, notification);
                        }
                    });


        }
    }


    public static String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();
        // 取得扩展名
        String end = fName
                .substring(fName.lastIndexOf(".") + 1, fName.length());
        if (end.equals("apk")) {
            type = "application/vnd.android.package-archive";
        } else {
            // /*如果无法直接打开，就跳出软件列表给用户选择 */
            type = "*/*";
        }
        return type;
    }

    public void upApp() {
        Uri uri = Uri.fromFile(file);
        String type = getMIMEType(file);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, type);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // file.delete();
        }
    }

}
