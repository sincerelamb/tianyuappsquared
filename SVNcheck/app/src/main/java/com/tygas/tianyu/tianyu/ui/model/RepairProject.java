package com.tygas.tianyu.tianyu.ui.model;

import java.io.Serializable;

/**
 * Created by SJTY_YX on 2016/3/2.
 * 维修项目
 *
 */
public class RepairProject implements Serializable{
    private String ProjectName;
    private String AmountTotal = "10.00";
    private String ServiceGroupName;

    public String getProjectName() {
        if(ProjectName == null || "null".equals(ProjectName)){
            return "";
        }
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getAmountTotal() {
        if(AmountTotal == null || "null".equals(AmountTotal)){
            return "";
        }
        return AmountTotal;
    }

    public void setAmountTotal(String amountTotal) {
        AmountTotal = amountTotal;
    }

    public String getServiceGroupName() {
        if(ServiceGroupName == null || "null".equals(ServiceGroupName)){
            return "";
        }
        return ServiceGroupName;
    }

    public void setServiceGroupName(String serviceGroupName) {
        ServiceGroupName = serviceGroupName;
    }
}
