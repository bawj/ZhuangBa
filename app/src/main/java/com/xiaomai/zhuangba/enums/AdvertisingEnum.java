package com.xiaomai.zhuangba.enums;

/**
 * @author Administrator
 * @date 2019/8/28 0028
 */
public enum AdvertisingEnum {
    /**
     * 雇主
     */
    EMPLOYER_IN_DISTRIBUTION(0, "雇主端  分配中"),
    EMPLOYER_RECEIVED_ORDERS(1, "雇主端  已接单"),
    EMPLOYER_IN_PROCESSING(2, "已出发"),
    EMPLOYER_CHECK_AND_ACCEPT(3, "施工中"),
    EMPLOYER_COMPLETED(4, "已取消"),
    EMPLOYER_CANCELLED(5, "已完成"),
    EMPLOYER_UNPAID(6, "雇主端 未支付"),
    EMPLOYER_ACCEPTANCE(9 , "验收中"),
    EMPLOYER_FAILURE_OF_ACCEPTANCE(10 , "验收不通过"),
    EMPLOYER_CANCELLATION_UNDER_WAY(11 , "正在取消"),

    /**
     * 师傅
     */
    MASTER_NEW_TASK(0, "师傅端 新任务"),
    MASTER_PENDING_DISPOSAL(1, "已接单"),
    MASTER_IN_PROCESSING(2, "已出发"),
    MASTER_BE_UNDER_CONSTRUCTION(3, "施工中"),
    MASTER_COMPLETED(4, "已取消"),
    MASTER_CANCELLED(5, "已完成"),
    MASTER_EXPIRED(7, "已过期"),
    MASTER_ACCEPTANCE_IS_NOT_ACCEPTABLE(8, "用户取消"),
    MASTER_ACCEPTANCE(9 , "验收中"),
    MASTER_FAILURE_OF_ACCEPTANCE(10 , "验收不通过"),
    MASTER_CANCELLATION_UNDER_WAY(11 , "正在取消"),
    ;

    private int code;
    private String explain;

    AdvertisingEnum(int code, String explain) {
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
