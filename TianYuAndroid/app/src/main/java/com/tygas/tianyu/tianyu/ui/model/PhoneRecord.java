package com.tygas.tianyu.tianyu.ui.model;

import com.lidroid.xutils.db.annotation.Id;

import java.io.Serializable;

/**
 * Created by SJTY_YX on 2016/8/12.
 */
public class PhoneRecord implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id(column = "id")
    private int id;

    private String PHONENUM;
    private String Name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public String getID() {
        return PHONENUM;
    }

    public void setID(String PHONENUM) {
        this.PHONENUM = PHONENUM;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "PhoneRecord{" +
                "PHONENUM='" + PHONENUM + '\''+
                ", Name='" + Name + '\'' +
                '}';
    }
}
