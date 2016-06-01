package com.tygas.tianyu.tianyu.ui.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/3.
 */
public class TimingProtect implements Serializable{

    private String CustomerName;
    private String CustomerPhone;
    private String FrameNum;
    private String CarNO;
    private String LatelyMaintainTime;
    private String LatelyMileage;
    private String LatelyInviteCallDate;
    private String PredictFitDate;
    private String LatelyMaintainDate;
    private String PredictDate;
    private String ShouldInviteCallDate;
    private String LatelyTalkProcess;

    @Override
    public String toString() {
        return "TimingProtect{" +
                "CustomerName='" + CustomerName + '\'' +
                ", CustomerPhone='" + CustomerPhone + '\'' +
                ", FrameNum='" + FrameNum + '\'' +
                ", CarNO='" + CarNO + '\'' +
                ", LatelyMaintainTime='" + LatelyMaintainTime + '\'' +
                ", LatelyMileage='" + LatelyMileage + '\'' +
                ", LatelyInviteCallDate='" + LatelyInviteCallDate + '\'' +
                ", PredictFitDate='" + PredictFitDate + '\'' +
                ", LatelyMaintainDate='" + LatelyMaintainDate + '\'' +
                ", PredictDate='" + PredictDate + '\'' +
                ", ShouldInviteCallDate='" + ShouldInviteCallDate + '\'' +
                ", LatelyTalkProcess='" + LatelyTalkProcess + '\'' +
                '}';
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerPhone() {
        return CustomerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        CustomerPhone = customerPhone;
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

    public String getLatelyMaintainTime() {
        return LatelyMaintainTime;
    }

    public void setLatelyMaintainTime(String latelyMaintainTime) {
        LatelyMaintainTime = latelyMaintainTime;
    }

    public String getLatelyMileage() {
        return LatelyMileage;
    }

    public void setLatelyMileage(String latelyMileage) {
        LatelyMileage = latelyMileage;
    }

    public String getLatelyInviteCallDate() {
        return LatelyInviteCallDate;
    }

    public void setLatelyInviteCallDate(String latelyInviteCallDate) {
        LatelyInviteCallDate = latelyInviteCallDate;
    }

    public String getPredictFitDate() {
        return PredictFitDate;
    }

    public void setPredictFitDate(String predictFitDate) {
        PredictFitDate = predictFitDate;
    }

    public String getLatelyMaintainDate() {
        return LatelyMaintainDate;
    }

    public void setLatelyMaintainDate(String latelyMaintainDate) {
        LatelyMaintainDate = latelyMaintainDate;
    }

    public String getPredictDate() {
        return PredictDate;
    }

    public void setPredictDate(String predictDate) {
        PredictDate = predictDate;
    }

    public String getLatelyTalkProcess() {
        return LatelyTalkProcess;
    }

    public void setLatelyTalkProcess(String latelyTalkProcess) {
        LatelyTalkProcess = latelyTalkProcess;
    }

    public String getShouldInviteCallDate() {
        return ShouldInviteCallDate;
    }

    public void setShouldInviteCallDate(String shouldInviteCallDate) {
        ShouldInviteCallDate = shouldInviteCallDate;
    }
}
