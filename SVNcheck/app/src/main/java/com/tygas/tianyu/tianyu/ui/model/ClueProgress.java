package com.tygas.tianyu.tianyu.ui.model;

import java.io.Serializable;

/**
 * Created by SJTY_YX on 2016/2/1.
 */
public class  ClueProgress implements Serializable {

    private String empName;
    private String intentLevel;
    private String callTime;
    private String cusComeDate;
    private String talkProcess;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getIntentLevel() {
        return intentLevel;
    }

    public void setIntentLevel(String intentLevel) {
        this.intentLevel = intentLevel;
    }

    public String getCallTime() {
        if(callTime != null && callTime.contains(":")){
            return callTime.substring(0, callTime.lastIndexOf(":"));
        }
        if(callTime == null || "null".equals(callTime)){
            return "";
        }
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getCusComeDate() {
        if(cusComeDate != null && cusComeDate.contains(" ")){
            String[] strings = cusComeDate.split(" ");
            return strings[0];
        }
        if(cusComeDate == null || "null".equals(cusComeDate)){
            return "";
        }
        return cusComeDate;
        //return cusComeDate;// 这里需要返回下次回访的时间
    }

    public void setCusComeDate(String cusComeDate) {
        this.cusComeDate = cusComeDate;
    }

    public String getTalkProcess() {
        return talkProcess;
    }

    public void setTalkProcess(String talkProcess) {
        this.talkProcess = talkProcess;
    }
}
