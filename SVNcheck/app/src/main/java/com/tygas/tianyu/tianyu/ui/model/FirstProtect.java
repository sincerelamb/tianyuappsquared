package com.tygas.tianyu.tianyu.ui.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/2.
 */
public class FirstProtect implements Serializable {

    private String CarOwnerName;
    private String CarOwnerPhone;
    private String FrameNum;
    private String CarNO;
    private String CarSalesTime;
    private String EnterMileage;
    private String PredictDate;
    private String PredictFitDate;

    public String getPredictFitDate() {
        return PredictFitDate;
    }

    public void setPredictFitDate(String predictFitDate) {
        PredictFitDate = predictFitDate;
    }

    private String LatelyMaintainDate;
    private String InviteCallDate;
    private String ShouldInviteCallDate;
    private String TalkProcess;

    @Override
    public String toString() {
        return "FirstProtect{" +
                "CarOwnerName='" + CarOwnerName + '\'' +
                ", CarOwnerPhone='" + CarOwnerPhone + '\'' +
                ", FrameNum='" + FrameNum + '\'' +
                ", CarNO='" + CarNO + '\'' +
                ", CarSalesTime='" + CarSalesTime + '\'' +
                ", EnterMileage='" + EnterMileage + '\'' +
                ", PredictDate='" + PredictDate + '\'' +
                ", LatelyMaintainDate='" + LatelyMaintainDate + '\'' +
                ", InviteCallDate='" + InviteCallDate + '\'' +
                ", ShouldInviteCallDate='" + ShouldInviteCallDate + '\'' +
                ", TalkProcess='" + TalkProcess + '\'' +
                '}';
    }

    public String getCarOwnerName() {
        return CarOwnerName;
    }

    public void setCarOwnerName(String carOwnerName) {
        CarOwnerName = carOwnerName;
    }

    public String getCarOwnerPhone() {
         return CarOwnerPhone;
//        return "10086";
    }

    public void setCarOwnerPhone(String carOwnerPhone) {
        CarOwnerPhone = carOwnerPhone;
    }

    public String getFrameNum() {
        return FrameNum;
    }

    public void setFrameNum(String frameNum) {
        FrameNum = frameNum;
    }

    public String getCarNO() {
        return CarNO;
    }

    public void setCarNO(String carNO) {
        CarNO = carNO;
    }

    public String getCarSalesTime() {
        return CarSalesTime;
    }

    public void setCarSalesTime(String carSalesTime) {
        CarSalesTime = carSalesTime;
    }

    public String getEnterMileage() {
        return EnterMileage;
    }

    public void setEnterMileage(String enterMileage) {
        EnterMileage = enterMileage;
    }

    public String getPredictDate() {
        return PredictDate;
    }

    public void setPredictDate(String predictDate) {
        PredictDate = predictDate;
    }

    public String getLatelyMaintainDate() {
        return LatelyMaintainDate;
    }

    public void setLatelyMaintainDate(String latelyMaintainDate) {
        LatelyMaintainDate = latelyMaintainDate;
    }

    public String getInviteCallDate() {
        return InviteCallDate;
    }

    public void setInviteCallDate(String inviteCallDate) {
        InviteCallDate = inviteCallDate;
    }

    public String getShouldInviteCallDate() {
        return ShouldInviteCallDate;
    }

    public void setShouldInviteCallDate(String shouldInviteCallDate) {
        ShouldInviteCallDate = shouldInviteCallDate;
    }

    public String getTalkProcess() {
        return TalkProcess;
    }

    public void setTalkProcess(String talkProcess) {
        TalkProcess = talkProcess;
    }
}
