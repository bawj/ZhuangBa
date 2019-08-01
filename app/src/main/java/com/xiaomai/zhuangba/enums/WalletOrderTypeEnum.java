package com.xiaomai.zhuangba.enums;

/**
 * @author Administrator
 * @date 2019/7/30 0030
 */
public enum  WalletOrderTypeEnum {
    /**
     * 1.明细 2.收入 3.已提现
     */
    DETAIL_ALL(1 , "明细"),
    DETAIL_INCOME(2 , "收入"),
    DETAIL_OUT(3 , "已提现"),
    YES_PRESENTATION_PASSWORD(1 , "设置过交易密码"),
    NULL_PRESENTATION_PASSWORD(2 , "未设置交易密码"),
    ;
    private int code;
    private String explain;

    WalletOrderTypeEnum(int code, String explain) {
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
