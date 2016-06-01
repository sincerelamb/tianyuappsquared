package com.tygas.tianyu.tianyu.ui.model;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/21.
 */

public class CpCustomer implements Serializable {
    @Id(column = "id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String reciveComeId;
    private String empName;
    private String ComeTime;
    private String LeaveTime;
    private String customerName;
    private String customerPhone;
    private String carseriesName;
    private String FollowPeo;


    private String liveProvince;
    private String liveCity;
    private String liveArea;
    private String channelId;
    private String sourceChannelId;
    private String oldCusCarNO;
    private String isDrive;
    private String intentLevel;
    private String carSeriesName;
    private String carModelName;
    private String carColor;
    private String UseageID;
    private String IsChange;
    private String FocusCarmodelID;
    private String TalkProcess;
    private String IsSuppleState;
    private String paytype;
    private String QuoteSituation;

    private String IntentLevelID;
    private String IsChangeID;
    private String CarColorID;
    private String CarSeriesID;
    private String CarModelID;

    private String Fromtime;
    private String Totime;

    public String getFromtime() {
        return Fromtime;
    }

    public void setFromtime(String fromtime) {
        Fromtime = fromtime;
    }

    public String getTotime() {
        return Totime;
    }

    public void setTotime(String totime) {
        Totime = totime;
    }


    public String getIntentLevelID() {
        return IntentLevelID;
    }

    public void setIntentLevelID(String intentLevelID) {
        IntentLevelID = intentLevelID;
    }

    public String getIsChangeID() {
        return IsChangeID;
    }

    public void setIsChangeID(String isChangeID) {
        IsChangeID = isChangeID;
    }

    public String getCarColorID() {
        return CarColorID;
    }

    public void setCarColorID(String carColorID) {
        CarColorID = carColorID;
    }

    public String getCarSeriesID() {
        return CarSeriesID;
    }

    public void setCarSeriesID(String carSeriesID) {
        CarSeriesID = carSeriesID;
    }

    public String getCarModelID() {
        return CarModelID;
    }

    public void setCarModelID(String carModelID) {
        CarModelID = carModelID;
    }

    public String getQuoteSituation() {
        return QuoteSituation;
    }

    public void setQuoteSituation(String quoteSituation) {
        QuoteSituation = quoteSituation;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getIsSuppleState() {
        return IsSuppleState;
    }

    public void setIsSuppleState(String isSuppleState) {
        IsSuppleState = isSuppleState;
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

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getSourceChannelId() {
        return sourceChannelId;
    }

    public void setSourceChannelId(String sourceChannelId) {
        this.sourceChannelId = sourceChannelId;
    }

    public String getOldCusCarNO() {
        return oldCusCarNO;
    }

    public void setOldCusCarNO(String oldCusCarNO) {
        this.oldCusCarNO = oldCusCarNO;
    }

    public String getIsDrive() {
        return isDrive;
    }

    public void setIsDrive(String isDrive) {
        this.isDrive = isDrive;
    }

    public String getIntentLevel() {
        return intentLevel;
    }

    public void setIntentLevel(String intentLevel) {
        this.intentLevel = intentLevel;
    }

    public String getCarSeriesName() {
        return carSeriesName;
    }

    public void setCarSeriesName(String carSeriesName) {
        this.carSeriesName = carSeriesName;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getUseageID() {
        return UseageID;
    }

    public void setUseageID(String useageID) {
        UseageID = useageID;
    }

    public String getIsChange() {
        return IsChange;
    }

    public void setIsChange(String isChange) {
        IsChange = isChange;
    }

    public String getFocusCarmodelID() {
        return FocusCarmodelID;
    }

    public void setFocusCarmodelID(String focusCarmodelID) {
        FocusCarmodelID = focusCarmodelID;
    }

    public String getTalkProcess() {
        return TalkProcess;
    }

    public void setTalkProcess(String talkProcess) {
        TalkProcess = talkProcess;
    }

    @Override
    public String toString() {
        return "CpCustomer{" +
                "reciveComeId='" + reciveComeId + '\'' +
                ", empName='" + empName + '\'' +
                ", ComeTime='" + ComeTime + '\'' +
                ", LeaveTime='" + LeaveTime + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", carseriesName='" + carseriesName + '\'' +
                ", FollowPeo='" + FollowPeo + '\'' +
                ", liveProvince='" + liveProvince + '\'' +
                ", liveCity='" + liveCity + '\'' +
                ", liveArea='" + liveArea + '\'' +
                ", channelId='" + channelId + '\'' +
                ", sourceChannelId='" + sourceChannelId + '\'' +
                ", oldCusCarNO='" + oldCusCarNO + '\'' +
                ", isDrive='" + isDrive + '\'' +
                ", intentLevel='" + intentLevel + '\'' +
                ", carSeriesName='" + carSeriesName + '\'' +
                ", carModelName='" + carModelName + '\'' +
                ", carColor='" + carColor + '\'' +
                ", UseageID='" + UseageID + '\'' +
                ", IsChange='" + IsChange + '\'' +
                ", FocusCarmodelID='" + FocusCarmodelID + '\'' +
                ", TalkProcess='" + TalkProcess + '\'' +
                ", IsSuppleState='" + IsSuppleState + '\'' +
                ", paytype='" + paytype + '\'' +
                ", QuoteSituation='" + QuoteSituation + '\'' +
                ", IntentLevelID='" + IntentLevelID + '\'' +
                ", IsChangeID='" + IsChangeID + '\'' +
                ", CarColorID='" + CarColorID + '\'' +
                ", CarSeriesID='" + CarSeriesID + '\'' +
                ", CarModelID='" + CarModelID + '\'' +
                '}';
    }

    public String getReciveComeId() {
        return reciveComeId;
    }

    public void setReciveComeId(String reciveComeId) {
        this.reciveComeId = reciveComeId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getComeTime() {
        return ComeTime;
    }

    public void setComeTime(String comeTime) {
        ComeTime = comeTime;
    }

    public String getLeaveTime() {
        return LeaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        LeaveTime = leaveTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCarseriesName() {
        return carseriesName;
    }

    public void setCarseriesName(String carseriesName) {
        this.carseriesName = carseriesName;
    }

    public String getFollowPeo() {
        return FollowPeo;
    }

    public void setFollowPeo(String followPeo) {
        FollowPeo = followPeo;
    }


}
