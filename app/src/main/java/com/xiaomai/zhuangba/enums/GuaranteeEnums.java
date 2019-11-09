package com.xiaomai.zhuangba.enums;

/**
 * @author Administrator
 * @date 2019/11/7 0007
 * 维保单 状态
 */
public enum GuaranteeEnums {

    /** 维保单状态 1新任务 2进行中 3.未开始 4.已结束*/
    GUARANTEE_NEW_TASK(1 , "新任务"),
    GUARANTEE_HAVE_IN_HAND(2 , "进行中"),
    GUARANTEE_NOT_YET_BEGUN(3 , "未开始"),
    GUARANTEE_HAS_ENDED(4 , "已结束"),

    GUARANTEE__IN_DISTRIBUTION(1,"雇主 分配中"),
    ;
    private int code;
    private String explain;

    GuaranteeEnums(int code, String explain) {
        this.code = code;
        this.explain = explain;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
