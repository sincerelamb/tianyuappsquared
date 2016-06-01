package com.tygas.tianyu.tianyu.ui.model;

import android.support.v4.app.NavUtils;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/21.
 */

@Table
public class User implements Serializable {
    @Id(column = "empId")
    private String empId;
    private String empName;
    private String PID;
    private Boolean isLoginSuccess;
    private String loginInformation;


    private List<PID> list_PID;
    private List<UserPtInfoModel> list_Channel;
    private List<UserPtInfoModel> list_Useage;
    private List<UserPtInfoModel> list_Brand;
    private List<UserPtInfoModel> list_CarSeries;
    private List<UserPtInfoModel> list_CheckColorList;
    private List<UserPtInfoModel> list_SourceChannel;
    private List<UserPtInfoModel> list_FailType;
    private List<UserPtInfoModel> list_Focus;
    private List<UserPtInfoModel> list_UserNum;
    private List<UserPtInfoModel> list_LevelNotDefeat;
    private List<UserPtInfoModel> list_LevelNotClinch;
    private List<UserPtInfoModel> list_LevelAll;
    private List<UserPtInfoModel> list_SubscribeType;
    private List<UserPtInfoModel> list_LostResult;

    public List<UserPtInfoModel> getList_LostResult() {
        if (list_LostResult == null) {
            return new ArrayList<UserPtInfoModel>();
        }
        return list_LostResult;
    }

    public void setList_LostResult(List<UserPtInfoModel> list_LostResult) {
        this.list_LostResult = list_LostResult;
    }

    public List<UserPtInfoModel> getList_SubscribeType() {
        if (list_SubscribeType == null) {
            return new ArrayList<UserPtInfoModel>();
        }
        return list_SubscribeType;
    }

    public void setList_SubscribeType(List<UserPtInfoModel> list_SubscribeType) {
        this.list_SubscribeType = list_SubscribeType;
    }

    public List<UserPtInfoModel> getList_LevelNotDefeat() {
        if (list_LevelNotDefeat == null) {
            return new ArrayList<UserPtInfoModel>();
        }
        return list_LevelNotDefeat;
    }

    public void setList_LevelNotDefeat(List<UserPtInfoModel> list_LevelNotDefeat) {
        this.list_LevelNotDefeat = list_LevelNotDefeat;
    }

    public List<UserPtInfoModel> getList_LevelNotClinch() {
        if (list_LevelNotClinch == null) {
            return new ArrayList<UserPtInfoModel>();
        }
        return list_LevelNotClinch;
    }

    public void setList_LevelNotClinch(List<UserPtInfoModel> list_LevelNotClinch) {
        this.list_LevelNotClinch = list_LevelNotClinch;
    }

    public List<UserPtInfoModel> getList_LevelAll() {
        if (list_LevelAll == null) {
            return new ArrayList<UserPtInfoModel>();
        }
        return list_LevelAll;
    }

    public void setList_LevelAll(List<UserPtInfoModel> list_LevelAll) {
        this.list_LevelAll = list_LevelAll;
    }

    public List<UserPtInfoModel> getList_UserNum() {

        if (list_UserNum == null) {
            return new ArrayList<UserPtInfoModel>();
        }
        return list_UserNum;
    }

    public void setList_UserNum(List<UserPtInfoModel> list_UserNum) {
        this.list_UserNum = list_UserNum;
    }

    public List<UserPtInfoModel> getList_OtherCarSeries() {
        if (list_OtherCarSeries == null) {
            return new ArrayList<UserPtInfoModel>();
        }
        return list_OtherCarSeries;
    }

    public void setList_OtherCarSeries(List<UserPtInfoModel> list_OtherCarSeries) {
        this.list_OtherCarSeries = list_OtherCarSeries;
    }

    private List<UserPtInfoModel> list_OtherCarSeries;

    public List<UserPtInfoModel> getList_CarModels() {
        if (list_CarModels == null) {
            return new ArrayList<UserPtInfoModel>();
        }
        return list_CarModels;
    }

    @Override
    public String toString() {
        return "User{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", PID='" + PID + '\'' +
                ", isLoginSuccess=" + isLoginSuccess +
                ", loginInformation='" + loginInformation + '\'' +
                ", list_PID=" + list_PID +
                ", list_Channel=" + list_Channel +
                ", list_Useage=" + list_Useage +
                ", list_Brand=" + list_Brand +
                ", list_CarSeries=" + list_CarSeries +
                ", list_CheckColorList=" + list_CheckColorList +
                ", list_SourceChannel=" + list_SourceChannel +
                ", list_FailType=" + list_FailType +
                ", list_Focus=" + list_Focus +
                ", list_CarModels=" + list_CarModels +
                ", list_CustomerLevel=" + list_CustomerLevel +
                '}';
    }

    public void setList_CarModels(List<UserPtInfoModel> list_CarModels) {
        this.list_CarModels = list_CarModels;
    }

    private List<UserPtInfoModel> list_CarModels;

    public List<UserPtInfoModel> getList_CustomerLevel() {
//        boolean isHave = false;
//        if (list_CustomerLevel != null) {
//            for (int i = 0; i < list_CustomerLevel.size(); i++) {
//                if ("F".equals(list_CustomerLevel.get(i).getName())) {
//                    isHave = true;
//                }
//            }
//            if (!isHave) {
//                UserPtInfoModel model = new UserPtInfoModel();
//                model.setName("F");
//                list_CustomerLevel.add(model);
//            }
//        }
        if (list_CustomerLevel == null) {
            return new ArrayList<UserPtInfoModel>();
        }
        return list_CustomerLevel;
    }

    public void setList_CustomerLevel(List<UserPtInfoModel> list_CustomerLevel) {
        this.list_CustomerLevel = list_CustomerLevel;
    }

    private List<UserPtInfoModel> list_CustomerLevel;


    public List<UserPtInfoModel> getList_SourceChannel() {
        if (list_SourceChannel == null) {
            return new ArrayList<UserPtInfoModel>();
        }
        return list_SourceChannel;
    }

    public void setList_SourceChannel(List<UserPtInfoModel> list_SourceChannel) {
        this.list_SourceChannel = list_SourceChannel;
    }


    public List<com.tygas.tianyu.tianyu.ui.model.PID> getList_PID() {
        return list_PID;
    }

    public void setList_PID(List<com.tygas.tianyu.tianyu.ui.model.PID> list_PID) {
        this.list_PID = list_PID;
    }

    public List<UserPtInfoModel> getList_FailType() {

        if (list_FailType == null) {
            return new ArrayList<UserPtInfoModel>();
        }
        return list_FailType;
    }

    public void setList_FailType(List<UserPtInfoModel> list_FailType) {
        this.list_FailType = list_FailType;
    }

    public List<UserPtInfoModel> getList_CheckColorList() {

        if (list_CheckColorList == null) {
            return new ArrayList<UserPtInfoModel>();
        }
        return list_CheckColorList;
    }

    public void setList_CheckColorList(List<UserPtInfoModel> list_CheckColorList) {
        this.list_CheckColorList = list_CheckColorList;
    }


    public List<UserPtInfoModel> getList_Focus() {

        if (list_Focus == null) {
            return new ArrayList<UserPtInfoModel>();
        }
        return list_Focus;
    }

    public void setList_Focus(List<UserPtInfoModel> list_Focus) {
        this.list_Focus = list_Focus;
    }

    public List<UserPtInfoModel> getList_CarSeries() {
        if (list_CarSeries == null) {
            return new ArrayList<UserPtInfoModel>();
        }
        return list_CarSeries;
    }

    public void setList_CarSeries(List<UserPtInfoModel> list_CarSeries) {
        this.list_CarSeries = list_CarSeries;
    }

    public List<UserPtInfoModel> getList_Brand() {

        if (list_Brand == null) {
            return new ArrayList<UserPtInfoModel>();
        }
        return list_Brand;
    }

    public void setList_Brand(List<UserPtInfoModel> list_Brand) {
        this.list_Brand = list_Brand;
    }

    public List<UserPtInfoModel> getList_Channel() {
        if (list_Channel == null) {
            return new ArrayList<UserPtInfoModel>();
        }

        return list_Channel;
    }

    public void setList_Channel(List<UserPtInfoModel> list_Channel) {
        this.list_Channel = list_Channel;
    }

    public List<UserPtInfoModel> getList_Useage() {
        if (list_Useage == null) {
            return new ArrayList<UserPtInfoModel>();
        }
        return list_Useage;
    }

    public void setList_Useage(List<UserPtInfoModel> list_Useage) {
        this.list_Useage = list_Useage;
    }


    public String getLoginInformation() {
        if (loginInformation == null) {
            return "";
        }
        return loginInformation;
    }

    public void setLoginInformation(String loginInformation) {
        this.loginInformation = loginInformation;
    }


    public String getEmpId() {
        if (empId == null) {
            return "";
        }
        return empId;
    }

    public void setEmpId(String empId) {

        this.empId = empId;
    }

    public String getEmpName() {

        if (empName == null) {
            return "";
        }
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getPID() {
        if (PID == null) {
            return "";
        }
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public Boolean getIsLoginSuccess() {
        if (isLoginSuccess == null) {
            return false;
        }
        return isLoginSuccess;
    }

    public void setIsLoginSuccess(Boolean isLoginSuccess) {
        this.isLoginSuccess = isLoginSuccess;
    }

}
