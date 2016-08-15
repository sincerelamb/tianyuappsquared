package com.tygas.tianyu.tianyu.utils;

import android.content.Context;
import android.util.Log;

import com.lidroid.xutils.http.RequestParams;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SJTY_YX on 2016/1/25.
 * 操作录音文件的utils
 *
 */
public class RecordFile {

    private File rootFile;
    private File recordFile;//录音文件

    private String fileName;

    private static final String root = "tianyurecord";
    private static final String LOG_TAG = "FileUtils";

    public RecordFile(Context context,String fname) {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("HH_mm_ss");
        String time = format.format(date);

       // this.fileName = XutilsRequest.getSign(fname+time,"","","","")+".raw";
        this.fileName = XutilsRequest.getSign(fname+time,"","","","")+".mp3";
        Log.i(LOG_TAG,"[加密后的文件名]"+this.fileName);

        rootFile = new File(context.getExternalCacheDir(),root);
//        context.getExternalFilesDir()
        //rootFile = Environment.getExternalStorageDirectory();
        if(!rootFile.exists()){
            rootFile.mkdirs();
            Log.i(LOG_TAG,"创建目录");
        }
        recordFile = new File(rootFile,fileName);
    }




    public String getFilePath(){
        if(recordFile != null){
            return recordFile.getAbsolutePath();
        }
        return null;
    }

    public File getRecordFile(){
        return recordFile;
    }

}
