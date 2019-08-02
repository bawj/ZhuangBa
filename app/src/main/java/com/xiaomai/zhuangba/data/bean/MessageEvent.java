package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public class MessageEvent {

    private int errCode;

    public MessageEvent(int errCode) {
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }

}
