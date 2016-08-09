package com.tygas.tianyu.tianyu.ui.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/2.
 */
public class MaintenanceHistory implements Serializable{

    private String ReciveComeDate;
    private String ServiceEmpName;
    private String ReciveTypeName;
    private String ReciveComeID;

    public String getReciveComeDate() {
        return ReciveComeDate;
    }

    public void setReciveComeDate(String reciveComeDate) {
        ReciveComeDate = reciveComeDate;
    }

    public String getServiceEmpName() {
        return ServiceEmpName;
    }

    public void setServiceEmpName(String serviceEmpName) {
        ServiceEmpName = serviceEmpName;
    }

    public String getReciveTypeName() {
        return ReciveTypeName;
    }

    public void setReciveTypeName(String reciveTypeName) {
        ReciveTypeName = reciveTypeName;
    }

    public String getReciveComeID() {
        return ReciveComeID;
    }

    public void setReciveComeID(String reciveComeID) {
        ReciveComeID = reciveComeID;
    }
}
