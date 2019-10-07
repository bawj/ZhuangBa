package com.xiaomai.zhuangba.enums;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 */
public enum StaticExplain {

    /**
     * 枚举状态
     */
    FU_FU_SHI(0, "师傅端"),
    EMPLOYER(1, "雇主端"),
    OBSERVER_(0, "显示申请成为师傅"),
    OBSERVER(1, "观察员"),
    INTERNSHIP(2, "实习"),
    FORMAL_MASTER(3, "正式师傅"),
    NO_CERTIFICATION(0, "未认证"),
    CERTIFIED(1, "已认证"),
    IN_AUDIT(2, "审核中"),
    REJECT_AUDIT(3, "已驳回 审核不通过"),
    SELECTED_ROLES(4,"已选择角色"),
    ONE_SELECTED_ROLES(5,"第一次审核通过"),
    STOP_REFRESH(1, "停止刷新 handler"),
    PAGE_NUMBER(1, "加载 page  默认 1"),
    PAGE_NUM(20, "刷新加载一次加载15条数据"),
    NEW_TASK(1, "新任务"),
    NEED_DEAL_WITH(2, "需处理"),
    ONGOING_ORDERS(3, "进行中的订单"),
    SUPER_ADMINISTRATOR(1, "超级管理员"),
    ADMINISTRATOR(2, "管理员"),
    ORDINARY_STAFF(3, "普通员工"),
    YES_MAINTENANCE(1, "有维保"),
    NO_MAINTENANCE(0, "没有维保"),
    EXPENDITURE(1, "收入"),
    INCOME(2, "支出"),
    PAYMENT_OF_SERVICE_ITEMS(3, "雇主服务项目支付"),
    RECHARGE_RECORD(7, "充值记录"),
    RECORDS_OF_CONSUMPTION(8, "消费记录"),
    SAME_DAY(0, "当日"),
    THIS_WEEK(1, "本周"),
    THIS_MONTH(2, "本月"),
    THIS_QUARTER(3, "本季度"),
    INSTALLATION_LIST(1, "安装单"),
    ADVERTISING_BILLS(2, "广告单"),
    PATROL(3,"巡查"),
    DEBUGGING(0, "不需要调试"),
    NOT_DEBUGGING(1, "需要调试"),
    SINGLE_SERVICE(0, "单次服务"),
    CONTINUOUS_SERVICE(1, "持续服务"),
    BEFORE_THE_BEGINNING(1, "开始前的图片地址"),
    UPON_COMPLETION(2, "完成后的图片地址"),
    EMPLOYER_LIVE_PHOTOS(3, "雇主提交的现场照片"),
    ;
    /**
     * 状态
     */
    private int code;
    /**
     * code 说明
     */
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
