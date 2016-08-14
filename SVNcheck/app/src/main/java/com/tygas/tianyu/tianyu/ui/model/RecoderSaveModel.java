package com.tygas.tianyu.tianyu.ui.model;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/8/13.
 */
@Table
public class RecoderSaveModel {
    @Id(column="CallFollowID")
    private String CallFollowID;

    private String empName;
    private String customerName;
    private String time;
    private String path;
    private String length;


    public String getCallFollowID() {
        return CallFollowID;
    }

    public void setCallFollowID(String callFollowID) {
        CallFollowID = callFollowID;
    }


    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
