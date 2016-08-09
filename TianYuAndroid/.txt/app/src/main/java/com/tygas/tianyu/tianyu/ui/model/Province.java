package com.tygas.tianyu.tianyu.ui.model;

import java.util.ArrayList;

/**
 * Created by SJTY_YX on 2016/2/29.
 */
public class Province {

    private String name;
    private ArrayList<Permanent> permanents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Permanent> getPermanents() {
        return permanents;
    }

    public void setPermanents(ArrayList<Permanent> permanents) {
        this.permanents = permanents;
    }
}
