package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/8/11 0011
 */
public class DataDetailsBean {

    private int type;
    private String data;

    public DataDetailsBean() {
    }

    public DataDetailsBean(int type, String data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
