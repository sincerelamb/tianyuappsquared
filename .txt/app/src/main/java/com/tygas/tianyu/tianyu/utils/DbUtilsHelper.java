package com.tygas.tianyu.tianyu.utils;

import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.tygas.tianyu.tianyu.ui.model.CpCustomer;
import com.tygas.tianyu.tianyu.ui.model.PID;
import com.tygas.tianyu.tianyu.ui.model.SearchCpCustomer;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;

/**
 * Created by Administrator on 2016/1/28.
 */
public class DbUtilsHelper {

    public static DbUtils dbUtils;

    public static DbUtils newInstance(Context context) {
        if (dbUtils == null) {
            dbUtils = DbUtils.create(context);
            dbUtils.configAllowTransaction(true);
            try {
                dbUtils.createTableIfNotExist(User.class);
                dbUtils.createTableIfNotExist(UserPtInfoModel.class);
                dbUtils.createTableIfNotExist(PID.class);
                dbUtils.createTableIfNotExist(SearchCpCustomer.class);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return dbUtils;
    }

    public static void closeDbutils(){
        if(dbUtils!=null){
            dbUtils.close();
        }
    }

}
