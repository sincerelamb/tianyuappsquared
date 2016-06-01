package com.tygas.tianyu.tianyu.ui.model;

import com.lidroid.xutils.db.annotation.Id;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/21.
 */
public class UserPtInfoModel implements Serializable{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id(column = "id")
    private int id;

    private String IDCODE;
    private String PID;
    private String Name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public String getID() {
        return IDCODE;
    }

    public void setID(String ID) {
        this.IDCODE = ID;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "UserPtInfoModel{" +
                "ID='" + IDCODE + '\'' +
                ", PID='" + PID + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
