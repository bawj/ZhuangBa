package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/10/15 0015
 */
public class CreateTeamBean {

    /**
     * enumCode : null
     * enumName : 测试
     * enumVal : 1
     */

    private String enumCode;
    private String enumName;
    private int enumVal;

    public String getEnumCode() {
        return enumCode == null ? "" : enumCode;
    }

    public void setEnumCode(String enumCode) {
        this.enumCode = enumCode;
    }

    public String getEnumName() {
        return enumName == null ? "" : enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public int getEnumVal() {
        return enumVal;
    }

    public void setEnumVal(int enumVal) {
        this.enumVal = enumVal;
    }
}
