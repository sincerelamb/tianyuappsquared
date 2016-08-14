package com.tygas.tianyu.tianyu.ui.model;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/4/7.
 */
@Table
public class UserKPIS {
    @Id(column = "title")
   // private int id;
    private String title;
    private String Num;
    private boolean isShow;//是否在工作主页上面显示 该kpi

    @Override
    public String toString() {
        return "UserKPIS{" +
                //"id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", Num='" + Num + '\'' +
                ", isShow=" + isShow +
                '}';
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

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



}
