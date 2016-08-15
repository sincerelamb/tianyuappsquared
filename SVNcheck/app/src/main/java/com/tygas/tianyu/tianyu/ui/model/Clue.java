package com.tygas.tianyu.tianyu.ui.model;


import java.io.Serializable;

/**
 * Created by SJTY_YX on 2016/1/28.
 */
public class Clue implements Serializable {

    private String clueId; //4762

    private String clueName; // 李雪琴
    private String cluePhone; // 155213654201
    private String empName; //唐丽梅
    private String intentLevel; // C
    private String lastContratTime; // 2015-11-03 15:35:10
    private String carseriesName; //D20
    private String carModelName; // 1.5L MT 舒适型
    private String sourceChannelName; //汽车点评网"
    private String liveProvince; // 四川省
    private String liveCity; // 成都市

    public String getIntroducer() {
        return Introducer;
    }

    public void setIntroducer(String introducer) {
        Introducer = introducer;
    }

    private String liveArea; //锦江区
    private String channelName; // 转介绍
    private String nextCallTime; // 2015-12-30
    private String Introducer;

    private String TalkProcess;

    public String getTalkProcess() {
        return TalkProcess;
    }

    public void setTalkProcess(String talkProcess) {
        TalkProcess = talkProcess;
    }

    private static final String LOG_TAG = "Clue";

    public Clue() {
        /*cluePhone = "10086";
        clueName = "xx";
        empName = "xx";
        intentLevel = "H";*/
    }

    public String getClueId() {
        return clueId;
    }

    public void setClueId(String clueId) {
        this.clueId = clueId;
    }

    public String getClueName() {
        return clueName;
    }

    public void setClueName(String clueName) {
        this.clueName = clueName;
    }

    public String getCluePhone() {

//           return /*cluePhone;*/"10086";
       return cluePhone;
    }

    public void setCluePhone(String cluePhone) {
        this.cluePhone = cluePhone;
    }

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

    public String getLastContratTime() {
//        if(lastContratTime == null || "null".equals(lastContratTime)){
//            return "";
//        }
//        return lastContratTime.split("\\ ")[0];

        return lastContratTime;
    }

    public void setLastContratTime(String lastContratTime) {
        this.lastContratTime = lastContratTime;
    }

    public String getCarseriesName() {
        return carseriesName;
    }

    public void setCarseriesName(String carseriesName) {
        this.carseriesName = carseriesName;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public String getSourceChannelName() {
        return sourceChannelName;
    }

    public void setSourceChannelName(String sourceChannelName) {
        this.sourceChannelName = sourceChannelName;
    }

    public String getLiveProvince() {
        return liveProvince;
    }

    public void setLiveProvince(String liveProvince) {
        this.liveProvince = liveProvince;
    }

    public String getLiveCity() {

        return liveCity;
    }

    public void setLiveCity(String liveCity) {
        this.liveCity = liveCity;
    }

    public String getLiveArea() {
        return liveArea;
    }

    public void setLiveArea(String liveArea) {
        this.liveArea = liveArea;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getNextCallTime() {
        if (nextCallTime == null || "null".equals(nextCallTime)) {
            return "";
        }
        return nextCallTime.split("\\ ")[0];
    }

    public void setNextCallTime(String nextCallTime) {
        this.nextCallTime = nextCallTime;
    }
}
