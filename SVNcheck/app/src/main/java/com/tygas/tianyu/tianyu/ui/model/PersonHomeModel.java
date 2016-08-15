package com.tygas.tianyu.tianyu.ui.model;

/**
 * Created by SJTY_YX on 2016/3/15.
 *
 * 个人工作主页
 *
 */
public class PersonHomeModel {

    private String modelName;//模块的名字   当前库存   在途数
    private String totalNumber; //总的记录数
    private String tongbiNumber;//同比
    private String huanbiNumber;//环比

    @Override
    public String toString() {
        return "PersonHomeModel{" +
                "modelName='" + modelName + '\'' +
                ", totalNumber='" + totalNumber + '\'' +
                ", tongbiNumber='" + tongbiNumber + '\'' +
                ", huanbiNumber='" + huanbiNumber + '\'' +
                '}';
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getHuanbiNumber() {
        return huanbiNumber;
    }

    public void setHuanbiNumber(String huanbiNumber) {
        this.huanbiNumber = huanbiNumber;
    }

    public String getTongbiNumber() {
        return tongbiNumber;
    }

    public void setTongbiNumber(String tongbiNumber) {
        this.tongbiNumber = tongbiNumber;
    }

    public String getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(String totalNumber) {
        this.totalNumber = totalNumber;
    }
}
