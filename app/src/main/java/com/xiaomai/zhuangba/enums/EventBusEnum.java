package com.xiaomai.zhuangba.enums;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public enum EventBusEnum {

    /** eventBus 首页 */
    STOP_REFRESH(1 , "停止刷新"),
    REFRESH_NOT(2 , "加载时不允许下拉刷新"),
    REFRESH_YES(3 , "加载完成允许下拉刷新"),
    REFRESH(4 , "下拉刷新"),
    /** 通知列表刷新 */
    NOTIFICATION_REFRESH(5 , "通知列表刷新"),
    START_FLAG(6 , "退保证金 修改首页"),
    WITHDRAWAL_PASSWORD(7 , "提现密码设置成功 更新状态"),
    CASH_SUCCESS(8 , "提现成功 更新状态"),
    ALLOCATION_LIST_REFRESH(9 , "广告刷新"),
    ;
    private int code;
    private String explain;

    EventBusEnum(int code, String explain) {
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
