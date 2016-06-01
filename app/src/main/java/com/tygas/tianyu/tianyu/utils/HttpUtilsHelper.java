package com.tygas.tianyu.tianyu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.data.SharedPreferencesDate;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SJTY_YX on 2016/1/20.
 */
public class HttpUtilsHelper {

    public static HttpUtils httpUtils;
    public static HttpUtils httpUtils_updata;

    public static HttpUtils getInstance() {
        if (httpUtils == null) {
            httpUtils = new HttpUtils();
            httpUtils.configResponseTextCharset("utf-8");
        }
        return httpUtils;
    }

    public static HttpUtils getInstanceUpData() {
        if (httpUtils_updata == null) {
            httpUtils_updata = new HttpUtils();
        }
        return httpUtils_updata;
    }

    public static void downLoadUpdataUI(final Activity context, final boolean isSetResult, final int resposecoede, final boolean isDismissdialog, final boolean isfinish, final boolean isToast) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesDate.SHAREDPREFERENCES_NAME_USERINFO, Context.MODE_PRIVATE);
        String usn = sharedPreferences.getString(SharedPreferencesDate.USER_NAME, "");
        String psw = sharedPreferences.getString(SharedPreferencesDate.PASSWORD, "");
        HttpUtilsHelper.getInstance().send(HttpRequest.HttpMethod.POST, UrlData.LOGIN_URL, XutilsRequest.getLoginParams(usn, psw), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                List<UserPtInfoModel> userPtInfoModels = JsonParser.updataUItotal(responseInfo.result);
                Log.d("stus", responseInfo.result);
                if (userPtInfoModels.size() > 0) {
                    Intent i = new Intent("ty.updataUI");
                    i.putExtra("upUIdata", (Serializable) userPtInfoModels);
                    context.sendBroadcast(i);
                    try {
//                        DbUtilsHelper.dbUtils.delete(UserPtInfoModel.class, WhereBuilder.b("type", "=", "UserNum"));
//                        DbUtilsHelper.dbUtils.saveAll(userPtInfoModels);
                        List<UserPtInfoModel> stus = DbUtilsHelper.dbUtils.findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "UserNum"));
                        Log.d("stus", stus.toString());
                        Log.d("stus", userPtInfoModels.toString());
                        DbUtilsHelper.dbUtils.deleteAll(stus);
                        DbUtilsHelper.dbUtils.saveAll(userPtInfoModels);
                        ((MyAppCollection) context.getApplicationContext()).getUser().setList_UserNum(stus);
//                        db.deleteById(Grade.class, 2);
//                        db.delete(stus.get(3));
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                }
                if (isToast) {
                    Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                }

                if (isSetResult) {
                    context.setResult(resposecoede);
                }
                if (isfinish) {
                    context.finish();
                }

                if (isDismissdialog) {
                    ProgressDialogHelper.dismissProgressDialog();
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                if (isToast) {
                    Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                }

                if (isSetResult) {
                    context.setResult(resposecoede);
                }
                if (isfinish) {
                    context.finish();
                }

                if (isDismissdialog) {
                    ProgressDialogHelper.dismissProgressDialog();
                }

            }
        });
    }

}
