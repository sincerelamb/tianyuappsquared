package com.tygas.tianyu.tianyu.context;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.tygas.tianyu.tianyu.data.SharedPreferencesDate;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.model.PID;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;
import com.tygas.tianyu.tianyu.utils.DbUtilsHelper;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

import java.util.List;

/**
 * Created by Administrator on 2016/1/21.
 */
public class MyAppCollection extends Application {

    public User getUser() {
        if (user == null) {
            return new User();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User user;


    private PhonStateLisen phonStateLisen;

    public PhonStateLisen getPhonStateLisen() {
        return phonStateLisen;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferencesDate.SHAREDPREFERENCES_NAME_SET, MODE_PRIVATE);
        UrlData.APP_IP = sharedPreferences.getString(SharedPreferencesDate.URL_SET, UrlData.APP_IP);
        UrlData.reloadURL();
        XutilsRequest.PC=sharedPreferences.getString(SharedPreferencesDate.SHOP_CODE,XutilsRequest.PC);
        Log.d("MyAppCollection", "进入了");
        HttpUtilsHelper.getInstance();
        HttpUtilsHelper.getInstanceUpData();
        DbUtils dbUtils = DbUtilsHelper.newInstance(this);
        Log.d("stus++++++", "=====");
        try {
            //
            User user = dbUtils.findFirst(User.class);
            if (user != null) {
                user.setList_PID(dbUtils.findAll(PID.class));
                user.setList_CarSeries(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "CarSeries")));
                user.setList_Brand(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "OtherBrandList")));
                user.setList_Channel(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "ChannelList")));
                user.setList_CheckColorList(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "CheckColorList")));
                user.setList_FailType(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "FailType")));
                user.setList_Focus(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "FocusCarmodelList")));
                user.setList_SourceChannel(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "SourceChannel")));
                user.setList_Useage(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "UseageList")));
                user.setList_CustomerLevel(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "CustomerLevel")));
                user.setList_CarModels(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "CarModels")));
                user.setList_OtherCarSeries(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "OtherSeries")));
                user.setList_UserNum(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "UserNum")));
                user.setList_LevelNotDefeat(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "LevelNotDefeat")));
                user.setList_LevelNotClinch(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "LevelNotClinch")));
                user.setList_LevelAll(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "LevelAll")));
                user.setList_SubscribeType(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "SubscribeType")));
                user.setList_LostResult(dbUtils.<UserPtInfoModel>findAll(Selector.from(UserPtInfoModel.class).where("type", "=", "LostResult")));

                setUser(user);
            }


        } catch (DbException e) {

        }
        phonStateLisen = new PhonStateLisen();
    }

}
