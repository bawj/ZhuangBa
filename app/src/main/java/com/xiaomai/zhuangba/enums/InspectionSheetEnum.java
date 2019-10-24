package com.xiaomai.zhuangba.enums;

/**
 * @author Administrator
 * @date 2019/9/29 0029
 *
 * 巡查单状态
 */
public enum InspectionSheetEnum {

    /** 0：未分配 1：分配中 2：进行中 3 已完成*/
    INSPECTION_SHEET_UNALLOCATED(0 , "分配中"),
    INSPECTION_SHEET_IN_DISTRIBUTION(1 , "分配中"),
    INSPECTION_SHEET_HAVE_IN_HAND(2 , "进行中"),
    INSPECTION_SHEET_CANCELLED(3 , "已完成"),
    INSPECTION_APPLY_FOR_REFUND(4 , "申请退款"),
    INSPECTION_REFUNDED(5 , "已退款"),

    MASTER_INSPECTION_SHEET_IN_DISTRIBUTION(1 , "师傅 新任务"),
    MASTER_INSPECTION_SHEET_HAVE_IN_HAND(2 , "师傅 进行中"),
    MASTER_INSPECTION_SHEET_CANCELLED(3 , "师傅 已完成"),
    MASTER_APPLY_FOR_REFUND(4 , "申请退款"),
    MASTER_REFUNDED(5 , "已退款"),


    UNPAID(-1 , "未付款"),
    ;
    private int code;
    private String explain;

    InspectionSheetEnum(int code, String explain) {
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
