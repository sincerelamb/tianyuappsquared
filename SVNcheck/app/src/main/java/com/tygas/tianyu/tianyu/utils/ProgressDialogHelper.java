package com.tygas.tianyu.tianyu.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Jerry on 2015/7/9.
 */
public class ProgressDialogHelper {
    private static ProgressDialog dialog;

    //Dialog窗体显示
    public static void showProgressDialog(Context context, String message) {
        dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.show();
    }

    //Dialog窗体显示
    public static void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
