package com.tygas.tianyu.tianyu.ui.model;

import java.io.Serializable;

/**
 * Created by SJTY_YX on 2016/1/19.
 */
public class PtCustomer implements Serializable {

    private String customerId; // "4762"
    private String customerInfoID; // "李雪琴"
    private String customerName; // "155213654201"
    private String customerPhone; // "唐丽梅"
    private String empName; // "C"
    private String intentLevel; //"2015-11-03 15:35:10"
    private String lastContratTime; //"D20"
    private String lastTalkProcess; //"1.5L MT舒适型"
    private String carseriesName; // "汽车点评网"
    private String carModelName; //  "四川省"
    private String colorName;  // "成都市"
    private String sourceChannelName; // "锦江区"
    private String turnToIntroduce; // "转介绍 "
    private String oldCusCarNO; // "2015-12-30"
    private String otherBrandName;
    private String otherSeriesName;
    private String failType;
    private String payType;
    private String liveProvince;
    private String liveCity;
    private String liveArea;
    private String channelName;
    private String contactType;
    private String useageName;
    private String focusCarModel;
    private String nextCallTime;

    private String Introducer;
    private String IsChange;

    private String QuoteSituation;

    public String getQuoteSituation() {
        return QuoteSituation;
    }

    public void setQuoteSituation(String quoteSituation) {
        QuoteSituation = quoteSituation;
    }

    public String getIsChange() {
        return IsChange;
    }

    public void setIsChange(String isChange) {
        IsChange = isChange;
    }

    public String getIntroducer() {
        if(Introducer == null || "null".equals(Introducer)){
            return "";
        }else{
            return Introducer;
        }
    }

    public void setIntroducer(String introducer) {
        Introducer = introducer;
    }

    @Override
    public String toString() {
        return "PtCustomer{" +
                "customerId='" + customerId + '\'' +
                ", customerInfoID='" + customerInfoID + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", empName='" + empName + '\'' +
                ", intentLevel='" + intentLevel + '\'' +
                ", lastContratTime='" + lastContratTime + '\'' +
                ", lastTalkProcess='" + lastTalkProcess + '\'' +
                ", carseriesName='" + carseriesName + '\'' +
                ", carModelName='" + carModelName + '\'' +
                ", colorName='" + colorName + '\'' +
                ", sourceChannelName='" + sourceChannelName + '\'' +
                ", turnToIntroduce='" + turnToIntroduce + '\'' +
                ", oldCusCarNO='" + oldCusCarNO + '\'' +
                ", otherBrandName='" + otherBrandName + '\'' +
                ", otherSeriesName='" + otherSeriesName + '\'' +
                ", failType='" + failType + '\'' +
                ", payType='" + payType + '\'' +
                ", liveProvince='" + liveProvince + '\'' +
                ", liveCity='" + liveCity + '\'' +
                ", liveArea='" + liveArea + '\'' +
                ", channelName='" + channelName + '\'' +
                ", contactType='" + contactType + '\'' +
                ", useageName='" + useageName + '\'' +
                ", focusCarModel='" + focusCarModel + '\'' +
                ", nextCallTime='" + nextCallTime + '\'' +
                '}';
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerInfoID() {
        return customerInfoID;
    }

    public void setCustomerInfoID(String customerInfoID) {
        this.customerInfoID = customerInfoID;
    }

    public String getCustomerName() {

        //return "11111111111111111";
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
       // return "18810951137";
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getIntentLevel() {
        if (intentLevel == null || intentLevel.length() == 0) {
            return "";
        }
        return intentLevel;
    }

    public void setIntentLevel(String intentLevel) {
        this.intentLevel = intentLevel;
    }

    public String getLastContratTime() {
        return lastContratTime;
    }

    public void setLastContratTime(String lastContratTime) {
        this.lastContratTime = lastContratTime;
    }

    public String getLastTalkProcess() {
        return lastTalkProcess;
    }

    public void setLastTalkProcess(String lastTalkProcess) {
        this.lastTalkProcess = lastTalkProcess;
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

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getSourceChannelName() {
        return sourceChannelName;
    }

    public void setSourceChannelName(String sourceChannelName) {
        this.sourceChannelName = sourceChannelName;
    }

    public String getTurnToIntroduce() {
        return turnToIntroduce;
    }

    public void setTurnToIntroduce(String turnToIntroduce) {
        this.turnToIntroduce = turnToIntroduce;
    }

    public String getOldCusCarNO() {
        return oldCusCarNO;
    }

    public void setOldCusCarNO(String oldCusCarNO) {
        this.oldCusCarNO = oldCusCarNO;
    }

    public String getOtherBrandName() {
        return otherBrandName;
    }

    public void setOtherBrandName(String otherBrandName) {
        this.otherBrandName = otherBrandName;
    }

    public String getOtherSeriesName() {
        return otherSeriesName;
    }

    public void setOtherSeriesName(String otherSeriesName) {
        this.otherSeriesName = otherSeriesName;
    }

    public String getFailType() {
        return failType;
    }

    public void setFailType(String failType) {
        this.failType = failType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getUseageName() {
        return useageName;
    }

    public void setUseageName(String useageName) {
        this.useageName = useageName;
    }

    public String getFocusCarModel() {
        return focusCarModel;
    }

    public void setFocusCarModel(String focusCarModel) {
        this.focusCarModel = focusCarModel;
    }

    public String getNextCallTime() {
        if(nextCallTime == null || "null".equals(nextCallTime)){
            return "";
        }
        return nextCallTime;
    }

    public void setNextCallTime(String nextCallTime) {
        this.nextCallTime = nextCallTime;
    }
}
