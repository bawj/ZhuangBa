package com.xiaomai.zhuangba.enums;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 */
public enum  StaticExplain {

    /** 枚举状态 */
    FU_FU_SHI(0 , "师傅端"),
    EMPLOYER(1 , "雇主端"),
    NO_CERTIFICATION(0 , "未认证"),
    CERTIFIED(1 , "已认证"),
    IN_AUDIT(2 , "审核中"),
    REJECT_AUDIT(3 , "已驳回 审核不通过"),
    STOP_REFRESH(1 , "停止刷新 handler"),
    PAGE_NUMBER(1,"加载 page  默认 1"),
    PAGE_NUM(15 , "刷新加载一次加载15条数据"),
    NEW_TASK(1,"新任务"),
    NEED_DEAL_WITH(2, "需处理"),
    ONGOING_ORDERS(3 , "进行中的订单"),
    ;
    /** 服务器状态码 或 自定义状态 */
    private int code;
    /** code 说明 */
    private String explain;

    StaticExplain(int code, String explain) {
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
