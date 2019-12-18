package com.xiaomai.zhuangba.data.bean;

/**
 * Author: Bawj
 * CreateDate: 2019/12/17 0017 11:19
 * 验收不通过理由
 */
public class Cause {
    private Integer id;
    private String cause;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCause() {
        return cause == null ? "" : cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
