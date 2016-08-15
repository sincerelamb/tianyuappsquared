package com.tygas.tianyu.tianyu.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.tygas.tianyu.tianyu.ui.model.PtCustomer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SJTY_YX on 2016/2/17.
 */
public class PhoneValidate {

    public static boolean
    validateNumber(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            for (int i = 0; i < phone.length(); i++) {
                char c = phone.charAt(i);
                if (c < '0' || c > '9') {
                    return false;
                }
            }
            return true;
        }
        return false;

    }

    /**
     * date1 > date2 return true
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compareData(String date1, String date2) {
        Log.i("test", "[date1]" + date1 + "  [date2]" + date2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1 = simpleDateFormat.parse(date1);
            Date d2 = simpleDateFormat.parse(date2);
            if (d1.getTime() >= d2.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("test", "[转换异常]");
        }
        return false;
    }

    public static boolean compareDataTime(String s, String s1) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date d1 = simpleDateFormat.parse(s);
            Date d2 = simpleDateFormat.parse(s1);
            if (d1.getTime() >= d2.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }
}
