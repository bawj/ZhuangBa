package com.xiaomai.zhuangba.enums;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 */
public enum StringTypeExplain {

    /**String 类型 type*/
    REGISTERED_REGISTER("register","注册账号 获取验证码时的type "),
    REGISTERED_FORGET_PASSWORD("forgetPassword","忘记密码 获取验证码时的type "),
    REFRESH_NEW_TASK_FRAGMENT("0" , "首页 刷新 第一个fragment"),
    REFRESH_NEED_DEAL_WITH_FRAGMENT("1" , "首页 刷新 第二个fragment"),
    REFRESH_ADVERTISING_BILLS_FRAGMENT("2" , "首页 刷新 第三个fragment"),
    INSPECTION_SHEET_BILLS_FRAGMENT("3" , "首页 刷新 第四个fragment"),
    A_MAP_PACKAGE_NAME("com.autonavi.minimap" , "高德地图包名"),
    A_MAP_BAI_DU_PACKAGE_NAME("com.baidu.BaiduMap" , "百度地图包名"),
    ORDER_STATUS("2" , "推送状态 = 2 跳转到订单详情"),
    A_ALIPAY_PAYMENT("1" , "支付宝支付"),
    WE_CHAT_PAYMENT("2" , "微信支付"),
    WE_WALLET("3" , "钱包支付"),
    MONTHLY_KNOTS("4" , "月结挂账"),
    RECEIPT_ORDER_SUCCESS("1" , "接单成功"),
    PROCESSING("processing" , "巡查任务未处理"),
    PROCESSED("processed" , "巡查任务已处理"),
    CURRENT("current" , "巡查任务"),
    PATROL_RECORD("0" , "巡查记录"),
    YES("y" , "yes"),
    NO("n" , "no"),
    FULL_TIME_MASTER("2" , "2专职师傅"),
    EXTERNAL_MASTER("1" , "外部师傅"),
    ADD_PROJECT("1" , "新增项目"),
    DEL_PROJECT("2" , "删减项目"),
    CUSTOM_PROJECT("3" , "自定义项"),
    DRY_RUN_PROJECT("4" , "空跑"),
    ;

    private String code;
    private String message;

    StringTypeExplain(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
