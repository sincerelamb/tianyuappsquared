package com.tygas.tianyu.tianyu.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tygas.tianyu.tianyu.cheapmp3.CheapSoundFile;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.model.PtCustomer;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Created by SJTY_YX on 2016/1/26.
 */
public class UploadFileService extends IntentService {


    private NotificationManager manager;
    private PtCustomer customer;

    private static final String LOG_TAG = "UploadFileService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UploadFileService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String path = intent.getStringExtra("path");//录音文件的路径
        long duration = intent.getLongExtra("duration", 0);//通话时长
        String callId = intent.getStringExtra("callFollowId");
        customer = (PtCustomer) intent.getSerializableExtra("customer");//通话的对象

        MediaPlayer player = MediaPlayer.create(this, Uri.fromFile(new File(path)));
        int length = player.getDuration() / 1000;
        if(duration > 0){
            uploadSoundToNet(path,duration,length,path);
        }
    }


    //上传录音文件
    private void uploadSoundToNet(String callFollowId,long duration,int length,String recordPath) {
        //int length = mediaPlayer.getDuration()/1000;
       // Log.i(LOG_TAG, "[录音文件的长度] " + length);

        if(length > duration){
            final File outputFile = cheapMap3((int) (length-duration),length,recordPath);
            //final File outputFile = new File(recordPath);

            if(outputFile != null){
                //上传文件
                RequestParams requestParams = new RequestParams();
                requestParams.addBodyParameter("recodeing",outputFile);

                String ts = String.valueOf(System.currentTimeMillis());
                //getSign(String type, String pc, String time, String data, String key)
                String sign = XutilsRequest.getSign("", XutilsRequest.PC, ts, "", XutilsRequest.KEY);

                String queryStr = "&sign="+sign+"&ts="+ts+"&CallFollowID"+callFollowId+"&timeInt"+duration;

                HttpUtils httpUtils = HttpUtilsHelper.getInstance();
                //&sign=8989732&ts=7778776677&CallFollowID=29226&timeInt=35
                httpUtils.send(HttpRequest.HttpMethod.POST, UrlData.UPLOADFILE_URL+queryStr, requestParams, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                        Log.i(LOG_TAG, "[上传录音返回的数据]" + responseInfo.result);

                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            String status = jsonObject.getString("status");
                            if("Success".equals(status)){
                                String dataStr = jsonObject.getString("data");
                                JSONObject jsonobj = new JSONObject(dataStr);
                                String sta = jsonobj.getString("Status");
                                if("Success".equals(sta)){
                                    Toast.makeText(UploadFileService.this,"录音上传成功",Toast.LENGTH_SHORT).show();
                                    if(outputFile.exists()){
                                        outputFile.delete();//删除文件
                                    }
                                }else{
                                    Toast.makeText(UploadFileService.this,"录音上传失败"+jsonobj.getString("Message"),Toast.LENGTH_SHORT).show();

                                }
                            }else{
                                Toast.makeText(UploadFileService.this,"录音上传失败"+jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Log.i(LOG_TAG, "上传录音失败" + s);
                        Toast.makeText(UploadFileService.this,"录音上传失败"+s,Toast.LENGTH_SHORT).show();
                        //startService()
                    }
                });
            }else{
                Toast.makeText(this,"录音处理出错",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"录音出错",Toast.LENGTH_SHORT).show();
        }
    }

    private File cheapMap3(int start,int end,String recordPath){
        File outputFile = new File(Environment.getExternalStorageDirectory(),"tempmp3.mp3");
        try {
            CheapSoundFile cheapSoundFile = CheapSoundFile.create(recordPath,null);
            int startFrame = (int) (1.0 * start * cheapSoundFile.getSampleRate() / cheapSoundFile.getSamplesPerFrame() + 0.5);
            int endFrame = (int) (1.0 * end * cheapSoundFile.getSampleRate() / cheapSoundFile.getSamplesPerFrame() + 0.5);
            Log.i(LOG_TAG,"[截取录音文件] startFrame "+startFrame+"   endFrame "+endFrame+"   start "+start+"   end "+end);
            cheapSoundFile.WriteFile(outputFile,startFrame,endFrame-startFrame);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile;
    }

    //文件上传失败的时候发送通知
    private void sendNotificationWhenFail(){
        Notification notification = new Notification();
        notification.when = System.currentTimeMillis();
        notification.tickerText = customer.getCustomerName()+" 录音上传失败 点击重新上传";

    }

}
