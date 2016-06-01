package com.tygas.tianyu.tianyu.ui.model;

/**
 * Created by SJTY_YX on 2016/3/15.
 */
public class PersonHomeSetModel {

    private String name;
    private String isShow;


    @Override
    public String toString() {
        return "PersonHomeSetModel{" +
                "name='" + name + '\'' +
                ", isShow='" + isShow + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }
}
