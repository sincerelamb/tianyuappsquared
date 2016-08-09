package com.tygas.tianyu.tianyu.ui.model;

import java.io.Serializable;

/**
 * Created by SJTY_YX on 2016/1/19.
 */
public class PtCustomer implements Serializable {

    private String CustomerId; // "4762"
//    private String customerInfoID; // "李雪琴"
    private String CustomerName; // "唐丽梅"
    private String CustomerPhone; // "1220232"
    private String SparePhone;
    private String EmpName; // "C"
    private String IntentLevel;
    private String LastContratTime; //"D20"
    private String LastTalkProcess; //"1.5L MT舒适型"
    private String CarBrandName;
    private String CarseriesName; // "汽车点评网"
    private String CarModelName; //  "四川省"
    private String ColorName;  // "成都市"
    private String SourceChannelName; // "锦江区"
//    private String turnToIntroduce; // "转介绍 "
//    private String oldCusCarNO; // "2015-12-30"
//    private String otherBrandName;
//    private String otherSeriesName;
//    private String failType;
    private String PayType;
    private String LiveProvince;
    private String LiveCity;
    private String LiveArea;
    private String ChannelName;
//    private String contactType;
    private String UseageName;
    private String FocusCarModel;
    private String NextCallTime;

//    private String Introducer;
    private String IsChange;
    private String QuoteSituation;

    @Override
    public String toString() {
        return "PtCustomer{" +
                "CustomerId='" + CustomerId + '\'' +
                ", CustomerName='" + CustomerName + '\'' +
                ", CustomerPhone='" + CustomerPhone + '\'' +
                ", SparePhone='" + SparePhone + '\'' +
                ", EmpName='" + EmpName + '\'' +
                ", IntentLevel='" + IntentLevel + '\'' +
                ", LastContratTime='" + LastContratTime + '\'' +
                ", LastTalkProcess='" + LastTalkProcess + '\'' +
                ", CarBrandName='" + CarBrandName + '\'' +
                ", CarseriesName='" + CarseriesName + '\'' +
                ", CarModelName='" + CarModelName + '\'' +
                ", ColorName='" + ColorName + '\'' +
                ", SourceChannelName='" + SourceChannelName + '\'' +
                ", PayType='" + PayType + '\'' +
                ", LiveProvince='" + LiveProvince + '\'' +
                ", LiveCity='" + LiveCity + '\'' +
                ", LiveArea='" + LiveArea + '\'' +
                ", ChannelName='" + ChannelName + '\'' +
                ", UseageName='" + UseageName + '\'' +
                ", FocusCarModel='" + FocusCarModel + '\'' +
                ", NextCallTime='" + NextCallTime + '\'' +
                ", IsChange='" + IsChange + '\'' +
                ", QuoteSituation='" + QuoteSituation + '\'' +
                '}';
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
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
//        CustomerPhone = "10086";
    }

    public String getSparePhone() {
        return SparePhone;
    }

    public void setSparePhone(String sparePhone) {
        SparePhone = sparePhone;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        EmpName = empName;
    }

    public String getIntentLevel() {
        return IntentLevel;
    }

    public void setIntentLevel(String intentLevel) {
        IntentLevel = intentLevel;
    }

    public String getLastContratTime() {
        return LastContratTime;
    }

    public void setLastContratTime(String lastContratTime) {
        LastContratTime = lastContratTime;
    }

    public String getLastTalkProcess() {
        return LastTalkProcess;
    }

    public void setLastTalkProcess(String lastTalkProcess) {
        LastTalkProcess = lastTalkProcess;
    }

    public String getCarBrandName() {
        return CarBrandName;
    }

    public void setCarBrandName(String carBrandName) {
        CarBrandName = carBrandName;
    }

    public String getCarseriesName() {
        return CarseriesName;
    }

    public void setCarseriesName(String carseriesName) {
        CarseriesName = carseriesName;
    }

    public String getCarModelName() {
        return CarModelName;
    }

    public void setCarModelName(String carModelName) {
        CarModelName = carModelName;
    }

    public String getColorName() {
        return ColorName;
    }

    public void setColorName(String colorName) {
        ColorName = colorName;
    }

    public String getSourceChannelName() {
        return SourceChannelName;
    }

    public void setSourceChannelName(String sourceChannelName) {
        SourceChannelName = sourceChannelName;
    }

    public String getPayType() {
        return PayType;
    }

    public void setPayType(String payType) {
        PayType = payType;
    }

    public String getLiveProvince() {
        return LiveProvince;
    }

    public void setLiveProvince(String liveProvince) {
        LiveProvince = liveProvince;
    }

    public String getLiveArea() {
        return LiveArea;
    }

    public void setLiveArea(String liveArea) {
        LiveArea = liveArea;
    }

    public String getLiveCity() {
        return LiveCity;
    }

    public void setLiveCity(String liveCity) {
        LiveCity = liveCity;
    }

    public String getChannelName() {
        return ChannelName;
    }

    public void setChannelName(String channelName) {
        ChannelName = channelName;
    }

    public String getUseageName() {
        return UseageName;
    }

    public void setUseageName(String useageName) {
        UseageName = useageName;
    }

    public String getFocusCarModel() {
        return FocusCarModel;
    }

    public void setFocusCarModel(String focusCarModel) {
        FocusCarModel = focusCarModel;
    }

    public String getNextCallTime() {
        return NextCallTime;
    }

    public void setNextCallTime(String nextCallTime) {
        NextCallTime = nextCallTime;
    }

    public String getIsChange() {
        return IsChange;
    }

    public void setIsChange(String isChange) {
        IsChange = isChange;
    }

    public String getQuoteSituation() {
        return QuoteSituation;
    }

    public void setQuoteSituation(String quoteSituation) {
        QuoteSituation = quoteSituation;
    }
}
