package com.xiaomai.zhuangba.data;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 */
public class PayDepositBean {

    /**
     * marginCategoryId : 1
     * bond : 0.01
     * masterRankId : 2
     * masterRankName : 实习师傅
     * explain : null
     */

    private int marginCategoryId;
    private double bond;
    private int masterRankId;
    private String masterRankName;
    private String explain;

    public int getMarginCategoryId() {
        return marginCategoryId;
    }

    public void setMarginCategoryId(int marginCategoryId) {
        this.marginCategoryId = marginCategoryId;
    }

    public double getBond() {
        return bond;
    }

    public void setBond(double bond) {
        this.bond = bond;
    }

    public int getMasterRankId() {
        return masterRankId;
    }

    public void setMasterRankId(int masterRankId) {
        this.masterRankId = masterRankId;
    }

    public String getMasterRankName() {
        return masterRankName;
    }

    public void setMasterRankName(String masterRankName) {
        this.masterRankName = masterRankName;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

}
