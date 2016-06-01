package com.tygas.tianyu.tianyu.ui.model;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by SJTY_YX on 2016/3/16.
 *
 * 用户的kpi
 *
 */
@Table
public class UserKPI {
    @Id(column = "id")
    private String id;
    private String title;
    private String Num;
    private boolean isShow;//是否在工作主页上面显示 该kpi

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    @Override
    public String toString() {
        return "UserKPI{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", Num='" + Num + '\'' +
                ", isShow=" + isShow +
                '}';
    }


}
