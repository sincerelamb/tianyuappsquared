package com.tygas.tianyu.tianyu.ui.model;

import com.lidroid.xutils.db.annotation.Id;

/**
 * Created by Administrator on 2016/3/1.
 */
public class SearchCpCustomer {
    @Id(column = "id")
    private int id;
    private String customerName;
    private String customerPhone;
    private String empNmae;
    private String isSuppleState;
    private String fromeTime;
    private String toTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmpNmae() {
        return empNmae;
    }

    public void setEmpNmae(String empNmae) {
        this.empNmae = empNmae;
    }

    public String getIsSuppleState() {
        return isSuppleState;
    }

    public void setIsSuppleState(String isSuppleState) {
        this.isSuppleState = isSuppleState;
    }

    public String getFromeTime() {
        return fromeTime;
    }

    public void setFromeTime(String fromeTime) {
        this.fromeTime = fromeTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }
}
