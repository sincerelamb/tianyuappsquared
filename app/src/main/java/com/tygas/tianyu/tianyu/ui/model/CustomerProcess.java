package com.tygas.tianyu.tianyu.ui.model;

/**
 * Created by SJTY_YX on 2016/1/20.
 */
public class CustomerProcess{

    private String empName;
    private String intentLevel;
    private String callTime;
    private String nextCallTime;
    private String talkProcess;
    private String quoteSituation;//报价

    /*public CustomerProcess(String empName) {
        this.empName = empName;
        intentLevel = "A";
        callTime = "2015-11-04 15:32:12";
        nextCallTime = "2015-11-15";
        talkProcess = "试驾来不到，平时休息，时间再约";
    }*/

    public String getQuoteSituation() {
        return quoteSituation;
    }

    public void setQuoteSituation(String quoteSituation) {
        this.quoteSituation = quoteSituation;
    }

    public String getEmpName() {
        if(empName !=null  && empName.length() > 1){
            return empName;
        }else{
            return "";
        }

    }

    public void setEmpName(String empName) {

        this.empName = empName;
    }

    public String getIntentLevel() {
        if(intentLevel == null || intentLevel.length() == 0){
            return "";
        }
        return intentLevel;
    }

    public void setIntentLevel(String intentLevel) {
        this.intentLevel = intentLevel;
    }

    public String getCallTime() {
        if(callTime != null && callTime.contains("T")){
            return callTime.split("T")[0];
        }
        if(callTime == null || "null".equals(callTime)){
            return "";
        }
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getNextCallTime() {
        if(nextCallTime != null && nextCallTime.contains("T")){
            return nextCallTime.split("T")[0];
        }
        if(nextCallTime != null && nextCallTime.contains(" ")){
            return nextCallTime.split(" ")[0];
        }
        if(nextCallTime == null || "null".equals(nextCallTime)){
            return "";
        }
        return nextCallTime;
    }

    public void setNextCallTime(String nextCallTime) {
        this.nextCallTime = nextCallTime;
    }

    public String getTalkProcess() {
        return talkProcess;
    }

    public void setTalkProcess(String talkProcess) {
        this.talkProcess = talkProcess;
    }

    @Override
    public String toString() {
        return "CustomerProcess{" +
                "empName='" + empName + '\'' +
                ", intentLevel='" + intentLevel + '\'' +
                ", callTime='" + callTime + '\'' +
                ", nextCallTime='" + nextCallTime + '\'' +
                ", talkProcess='" + talkProcess + '\'' +
                '}';
    }
}
