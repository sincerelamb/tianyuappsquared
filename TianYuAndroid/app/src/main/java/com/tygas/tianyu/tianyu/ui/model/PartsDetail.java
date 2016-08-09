package com.tygas.tianyu.tianyu.ui.model;

import java.io.Serializable;

/**
 * Created by SJTY_YX on 2016/3/2.
 *
 * 配件明细
 *
 */
public class PartsDetail implements Serializable {

    private String PartName;
    private String PartCode;
    private String TrueSalePriceTotal;

    public String getPartName() {
        if(PartName == null || "null".equals(PartName)){
            return "";
        }
        return PartName;
    }

    public void setPartName(String partName) {
        PartName = partName;
    }

    public String getPartCode() {
        if(PartCode == null || "null".equals(PartCode)){

        }
        return PartCode;
    }

    public void setPartCode(String partCode) {
        PartCode = partCode;
    }

    public String getTrueSalePriceTotal() {
        if(TrueSalePriceTotal == null || "null".equals(TrueSalePriceTotal)){
            return "";
        }
        return TrueSalePriceTotal;
    }

    public void setTrueSalePriceTotal(String trueSalePriceTotal) {
        TrueSalePriceTotal = trueSalePriceTotal;
    }
}
