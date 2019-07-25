package com.xiaomai.zhuangba.enums;

/**
 * @author Administrator
 * @date 2019/5/13 0013
 * <p>
 * 订单状态
 */
public enum OrdersEnum {
    /**
     * 雇主
     */
    EMPLOYER_IN_DISTRIBUTION(0, "雇主端  分配中"),
    EMPLOYER_RECEIVED_ORDERS(1, "雇主端  已接单"),
    EMPLOYER_IN_PROCESSING(2, "雇主端  处理中"),
    EMPLOYER_CHECK_AND_ACCEPT(3, "雇主端 去验收"),
    EMPLOYER_COMPLETED(4, "雇主端 已完成  未评价"),
    EMPLOYER_CANCELLED(5, "雇主端 已取消"),
    EMPLOYER_UNPAID(6, "雇主端 未支付"),
    EMPLOYER_ALLOCATION_FAILED(7, "雇主端 分配失败"),
    EMPLOYER_ACCEPTANCE_IS_NOT_ACCEPTABLE(8, "雇主端 验收不通过处理中"),
    EMPLOYER_COMPLETED_ALREADY_EVALUATED(9, "雇主端 已完成  已评价"),
    EMPLOYER_COMPLETED_CANCEL(10, "师傅取消订单"),
    /**
     * 师傅
     */
    MASTER_NEW_TASK(0, "师傅端 新任务"),
    MASTER_PENDING_DISPOSAL(1, "师傅端 待处理"),
    MASTER_IN_PROCESSING(2, "师傅端 处理中"),
    MASTER_ACCEPTANCE(3, "师傅端 验收中"),
    MASTER_COMPLETED(4, "师傅端 已完成 未评价"),
    MASTER_CANCELLED(5, "师傅端 已取消"),
    MASTER_EXPIRED(7, "师傅端 已过期"),
    MASTER_ACCEPTANCE_IS_NOT_ACCEPTABLE(8, "师傅端 验收不通过"),
    MASTER_COMPLETED_(9, "师傅端 已完成 已评价"),
    MASTER_REST(2 , "休息中"),
    MASTER_WORK(1 , "工作中"),
    ;

    private int code;
    private String explain;

    OrdersEnum(int code, String explain) {
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
