package com.tygas.tianyu.tianyu.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;

import java.util.List;

/**
 * Created by Administrator on 2016/4/25.
 */
public class ActivityUtils {

    public static boolean isForeground(String PackageName,Activity context){
        // Get the Activity Manager
        ActivityManager manager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);

        // Get a list of running tasks, we are only interested in the last one,
        // the top most so we give a 1 as parameter so we only get the topmost.
        List< ActivityManager.RunningTaskInfo > task = manager.getRunningTasks(1);

        // Get the info we need for comparison.
        ComponentName componentInfo = task.get(0).topActivity;

        // Check if it matches our package name.
        if(componentInfo.getPackageName().equals(PackageName)) return true;

        // If not then our app is not on the foreground.
        return false;
    }
}
