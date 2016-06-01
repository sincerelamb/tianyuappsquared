package com.tygas.tianyu.tianyu.context;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.czt.mp3recorder.MP3Recorder;
import com.tygas.tianyu.tianyu.data.Config;
import com.tygas.tianyu.tianyu.ui.model.Clue;
import com.tygas.tianyu.tianyu.ui.model.FirstProtect;
import com.tygas.tianyu.tianyu.ui.model.PtCustomer;
import com.tygas.tianyu.tianyu.ui.model.TimingProtect;
import com.tygas.tianyu.tianyu.ui.view.activity.ClueReturnVisitFail;
import com.tygas.tianyu.tianyu.ui.view.activity.ClueReturnVisitOk;
import com.tygas.tianyu.tianyu.ui.view.activity.FirstProjectCallOutFailActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.FirstProjectCallOutSuccessActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.ReturnVisitFail;
import com.tygas.tianyu.tianyu.ui.view.activity.ReturnVisitOk;
import com.tygas.tianyu.tianyu.ui.view.activity.TextReturnVisitFailActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.TextReturnVisitOKActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.TimingProjectCallOutFailActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.TimingProjectCallOutSuccessActivity;
import com.tygas.tianyu.tianyu.utils.ProgressDialogHelper;

import java.io.File;


/**
 * Created by SJTY_YX on 2016/1/25.
 */
public class PhonStateLisen extends PhoneStateListener {

    private static final String LOG_TAG = "PhonStateLisen";
    public static boolean isCall = false;

    public static MP3Recorder mediaRecorder;// = new MP3Recorder(new File(Environment.getExternalStorageDirectory(),"test.mp3"));

    private File file;
    private static Activity activity;
    private static String phoneNumber;
    private static PtCustomer nowCostomer;
    private static FirstProtect nowfirstProtect;
    private static TimingProtect nowtimeProtect;

    private static File recordFile;

    private static Clue nowClue;
    private static String recordPath;
    private static int flag;//潜客 和线索标志
    private boolean haveComeing = false;//是否在通话的过程中有电话打进来

    private static final int HANDLE_CALL_AFTER = 0x0003;
    private static final int HANDLE_DIALOG = 0x0004;

    //用来做延时的操作
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLE_DIALOG:
                    ProgressDialogHelper.showProgressDialog(activity, "正在处理请稍后");
                    break;
                case HANDLE_CALL_AFTER:
                    ProgressDialogHelper.dismissProgressDialog();
                    dealCallAfter();
                    break;
            }
        }
    };//end handler

    public static final int PTCUSTOMER_FLAG = 0x0001;//处理潜客录音
    public static final int CLUE_FLAG = 0x0002;//处理线索录音
    public static final int FP_FLAG = 0x0005;//处理首保录音
    public static final int TP_FLAG = 0x0006;//处理定保录音

    public static final int TEXT_FLAG = 0x0007;


    public PhonStateLisen() {
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE://电话挂断
                stopRecord(incomingNumber);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK://电话链接
                Log.i(LOG_TAG, "[电话链接] phoneNumber" + phoneNumber + "   incomingNumber" + incomingNumber + " mediaRecorder" + mediaRecorder);
                if (phoneNumber != null) {
                    isCall = true;
                }
                if (isCall && mediaRecorder == null) {
                    //启动录音服务
                    Toast.makeText(activity, "开始录音", Toast.LENGTH_SHORT).show();
                    //RecordFile recordFile = new RecordFile(activity);
                    file = new File(recordPath);
                    mediaRecorder = new MP3Recorder(file);
                    try {
                        mediaRecorder.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i(LOG_TAG, "[错误]" + e);
                    }
                }
                if (haveComeing && mediaRecorder != null) {
                    //stopRecord(incomingNumber);
                    mediaRecorder.stop();
                    haveComeing = false;
                }
                break;
            case TelephonyManager.CALL_STATE_RINGING://电话响铃
                Log.i(LOG_TAG, "[响铃状态]");
                if (mediaRecorder != null) {
                    haveComeing = true;
                }
                break;
        }
    }

    private void stopRecord(String incomingNumber) {
        Log.i(LOG_TAG, "电话挂断 incomingNumber" + incomingNumber);
        if (phoneNumber != null) {
            Log.i(LOG_TAG, "录音结束");
            phoneNumber = null;
            if (mediaRecorder != null) {
                Toast.makeText(activity, "录音结束", Toast.LENGTH_SHORT).show();
                if (mediaRecorder.isRecording()) {
                    mediaRecorder.stop();

                }
                mediaRecorder = null;
                //启动一个界面
                new Thread() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(HANDLE_DIALOG);
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            handler.sendEmptyMessage(HANDLE_CALL_AFTER);
                        }
                    }
                }.start();
            }
        }
        isCall = false;
    }//end stopRecord

    private void dealCallAfter() {
        switch (flag) {
            case PTCUSTOMER_FLAG://处理潜客
                delaPtCustomerCallAfter();
                break;
            case CLUE_FLAG://处理线索
                dealClueCallAfter();
                break;
            case FP_FLAG:
                delaFPCustomerCallAfter();
                break;
            case TP_FLAG:
                delaTPCustomerCallAfter();
                break;
            case TEXT_FLAG:
                delaTextCallAfter();
                break;
        }
    }


    /**
     * phonStateLisen.setPhoneNumber(time);
     * phonStateLisen.setNowCostomer(customer);
     * phonStateLisen.setRecordPath(new RecordFile(activity,time).getFilePath());
     * phonStateLisen.setFlag(true);
     */
    public static void setParamsForPtCustomer(Activity a, PtCustomer customer, String path, File recordFiles) {
        activity = a;
        recordFile = recordFiles;
        phoneNumber = customer.getCustomerPhone();
        nowCostomer = customer;
        recordPath = path;
        flag = PTCUSTOMER_FLAG;
        Log.i(LOG_TAG, "[setParamsForPtCustomer]");
    }


    public static void setParamsForFpCustomer(Activity a, FirstProtect firstProtect, String path, File recordFiles) {
        activity = a;
        phoneNumber = firstProtect.getCarOwnerPhone();
        recordFile = recordFiles;
        nowfirstProtect = firstProtect;
        recordPath = path;
        flag = FP_FLAG;
        Log.i(LOG_TAG, "[setParamsForFpCustomer]");
    }

    public static void setParamsForTpCustomer(Activity a, TimingProtect timingProtect, String path, File recordFiles) {
        activity = a;
        phoneNumber = timingProtect.getCustomerPhone();
        recordFile = recordFiles;
        nowtimeProtect = timingProtect;
        recordPath = path;
        flag = TP_FLAG;

    }


    public static void setParamsForText(Activity a, String path, File recordFiles) {
        activity = a;
        phoneNumber = "10010";
        recordFile = recordFiles;
        recordPath = path;
        flag = TEXT_FLAG;

    }


    public static void clear() {
        mediaRecorder = null;
        activity = null;
        phoneNumber = null;
        nowCostomer = null;
        nowClue = null;
        recordPath = null;
    }

    public static void setParamsForClue(Activity a, Clue clue, String path, File recordFiles) {
        Log.i(LOG_TAG, "[setParamsForClue]");
        activity = a;
        recordFile = recordFiles;
        phoneNumber = clue.getCluePhone();
        nowClue = clue;
        recordPath = path;
        flag = CLUE_FLAG;
    }

    private void dealClueCallAfter() {
        //获取通话记录
        Log.i(LOG_TAG, "[dealClueCallAfter]");
        ContentResolver resolver = activity.getContentResolver();
        Cursor query = resolver.query(CallLog.Calls.CONTENT_URI,
                null, "number=?", new String[]{nowClue.getCluePhone()}, CallLog.Calls.DATE + " desc limit 1");
        if (query == null || nowClue == null || !query.moveToFirst()) {
            Toast.makeText(activity, "读取通话记录失败", Toast.LENGTH_SHORT).show();
            return;
        }

        String number = query.getString(query.getColumnIndex(CallLog.Calls.NUMBER));
        long duration = query.getLong(query.getColumnIndex(CallLog.Calls.DURATION));//通话时长
        if (Config.isDebug)
            Log.i(LOG_TAG, "[读取到的通话记录]" + number + "  duration " + duration);
        if (number.equals(nowClue.getCluePhone()) && duration != 0) {
            //通话成功
            Log.i(LOG_TAG, "[ok]");
            Intent intent = new Intent();
            intent.putExtra("data", nowClue);
            intent.putExtra("path", recordPath);
            intent.putExtra("duration", duration);
            intent.setClass(activity, ClueReturnVisitOk.class);
//            ClueReturnVisitOk.clueActivity = activity;
            //ClueReturnVisitOk.clue = nowClue;
            activity.startActivity(intent);
        } else {
            //通话失败
            Intent intent = new Intent();
            intent.putExtra("data", nowClue);
            intent.putExtra("path", recordPath);
            intent.setClass(activity, ClueReturnVisitFail.class);
            //删除录音文件
            File temp = new File(recordPath);
            if (temp.exists() && temp.isFile()) {
                temp.delete();
            }
//            ClueReturnVisitFail.clueActivity = activity;
            //intent.setClass(this,ReturnVisitOk.class);
            activity.startActivity(intent);
        }
    }

    private void delaPtCustomerCallAfter() {

        ContentResolver resolver = activity.getContentResolver();
        Cursor query = resolver.query(CallLog.Calls.CONTENT_URI,
                null, "number=?", new String[]{nowCostomer.getCustomerPhone()}, CallLog.Calls.DATE + " desc limit 1");

        if (query == null || nowCostomer == null || !query.moveToFirst()) {
            Toast.makeText(activity, "读取通话记录失败", Toast.LENGTH_SHORT).show();
            return;
        }

        //query.moveToFirst();
        String num = query.getString(query.getColumnIndex(CallLog.Calls.NUMBER));
        long duration = query.getLong(query.getColumnIndex(CallLog.Calls.DURATION));//通话时长
        if (Config.isDebug)
            Log.i(LOG_TAG, "[读取到的通话记录]" + num + "  duration " + duration);
        if (num.equals(nowCostomer.getCustomerPhone()) && duration != 0) {
            Log.i(LOG_TAG, "[ok]");
            Intent intent = new Intent();
            intent.putExtra("data", nowCostomer);
            intent.putExtra("path", recordPath);
            intent.putExtra("duration", duration);
            intent.setClass(activity, ReturnVisitOk.class);
            //ReturnVisitOk.ptCustomer = nowCostomer;
//            ReturnVisitOk.ptCustomersActivity = activity;
            activity.startActivity(intent);
        } else {
            Intent intent = new Intent();
            intent.putExtra("data", nowCostomer);
            intent.putExtra("path", recordPath);
            intent.setClass(activity, ReturnVisitFail.class);

            //删除录音文件
            File f = new File(recordPath);
            if (f.exists()) {
                f.delete();
            }
//            ReturnVisitFail.ptCustomerActivity = activity;
            //intent.setClass(this,ReturnVisitOk.class);
            activity.startActivity(intent);
        }
    }


    private void delaFPCustomerCallAfter() {

        ContentResolver resolver = activity.getContentResolver();
        Cursor query = resolver.query(CallLog.Calls.CONTENT_URI,
                null, "number=?", new String[]{nowfirstProtect.getCarOwnerPhone()}, CallLog.Calls.DATE + " desc limit 1");

        if (query == null || nowfirstProtect == null || !query.moveToFirst()) {
            Toast.makeText(activity, "读取通话记录失败", Toast.LENGTH_SHORT).show();
            return;
        }

        //query.moveToFirst();
        String num = query.getString(query.getColumnIndex(CallLog.Calls.NUMBER));
        long duration = query.getLong(query.getColumnIndex(CallLog.Calls.DURATION));//通话时长
        if (Config.isDebug)
            Log.i(LOG_TAG, "[读取到的通话记录]" + num + "  duration " + duration);
        if (num.equals(nowfirstProtect.getCarOwnerPhone()) && duration != 0) {
            Log.i(LOG_TAG, "[ok]");
            Intent intent = new Intent();
            intent.putExtra("data", nowfirstProtect);
            intent.putExtra("path", recordPath);
            intent.putExtra("duration", duration);
            intent.setClass(activity, FirstProjectCallOutSuccessActivity.class);
//            FirstProjectCallOutSuccessActivity.firstProjectActivity = activity;
            //ReturnVisitOk.ptCustomer = nowCostomer;
            //  ReturnVisitOk.ptCustomersActivity = activity;
            activity.startActivity(intent);
        } else {
            Intent intent = new Intent();
            intent.putExtra("data", nowfirstProtect);
            intent.putExtra("path", recordPath);
            intent.setClass(activity, FirstProjectCallOutFailActivity.class);
            //删除录音文件
            File f = new File(recordPath);
            if (f.exists()) {
                f.delete();
            }
            //   ReturnVisitFail.ptCustomerActivity = activity;
//            FirstProjectCallOutFailActivity.firstProjectActivity = activity;
            //intent.setClass(this,ReturnVisitOk.class);
            activity.startActivity(intent);
        }
    }


    private void delaTPCustomerCallAfter() {

        ContentResolver resolver = activity.getContentResolver();
        Cursor query = resolver.query(CallLog.Calls.CONTENT_URI,
                null, "number=?", new String[]{nowtimeProtect.getCustomerPhone()}, CallLog.Calls.DATE + " desc limit 1");

        if (query == null || nowtimeProtect == null || !query.moveToFirst()) {
            Toast.makeText(activity, "读取通话记录失败", Toast.LENGTH_SHORT).show();
            return;
        }

        //query.moveToFirst();
        String num = query.getString(query.getColumnIndex(CallLog.Calls.NUMBER));
        long duration = query.getLong(query.getColumnIndex(CallLog.Calls.DURATION));//通话时长
        if (Config.isDebug)
            Log.i(LOG_TAG, "[读取到的通话记录]" + num + "  duration " + duration);
        if (num.equals(nowtimeProtect.getCustomerPhone()) && duration != 0) {
            Log.i(LOG_TAG, "[ok]");
            Intent intent = new Intent();
            intent.putExtra("data", nowtimeProtect);
            intent.putExtra("path", recordPath);
            intent.putExtra("duration", duration);
            intent.setClass(activity, TimingProjectCallOutSuccessActivity.class);
//            TimingProjectCallOutSuccessActivity.timingProtectActivity = activity;
            //ReturnVisitOk.ptCustomer = nowCostomer;
            //  ReturnVisitOk.ptCustomersActivity = activity;
            activity.startActivity(intent);
        } else {
            Intent intent = new Intent();
            intent.putExtra("data", nowtimeProtect);
            intent.putExtra("path", recordPath);
            intent.setClass(activity, TimingProjectCallOutFailActivity.class);
            //删除录音文件
            File f = new File(recordPath);
            if (f.exists()) {
                f.delete();
            }
//            TimingProjectCallOutFailActivity.timingProtectActivity = activity;
            //intent.setClass(this,ReturnVisitOk.class);
            activity.startActivity(intent);
        }
    }


    private void delaTextCallAfter() {

        ContentResolver resolver = activity.getContentResolver();
        Cursor query = resolver.query(CallLog.Calls.CONTENT_URI,
                null, "number=?", new String[]{"10010"}, CallLog.Calls.DATE + " desc limit 1");

        if (query == null || !query.moveToFirst()) {
            Toast.makeText(activity, "读取通话记录失败", Toast.LENGTH_SHORT).show();
            return;
        }

        //query.moveToFirst();
        String num = query.getString(query.getColumnIndex(CallLog.Calls.NUMBER));
        long duration = query.getLong(query.getColumnIndex(CallLog.Calls.DURATION));//通话时长
        if (Config.isDebug)
            Log.i(LOG_TAG, "[读取到的通话记录]" + num + "  duration " + duration);
        if (num.equals("10010") && duration != 0) {
            Log.i(LOG_TAG, "[ok]");
            Intent intent = new Intent();
            intent.putExtra("data", nowtimeProtect);
            intent.putExtra("path", recordPath);
            intent.putExtra("duration", duration);
            intent.setClass(activity, TextReturnVisitOKActivity.class);
            activity.startActivity(intent);
        } else {
            Intent intent = new Intent();
            intent.putExtra("data", nowtimeProtect);
            intent.putExtra("path", recordPath);
            intent.setClass(activity, TextReturnVisitFailActivity.class);
            //删除录音文件
            File f = new File(recordPath);
            if (f.exists()) {
                f.delete();
            }
            activity.startActivity(intent);
        }
    }


}
