package com.xiaomai.zhuangba.data;

/**
 * @author Administrator
 * @date 2019/7/31 0031
 */
public class WalletOrderDetailBean {

    String title;
    String content;

    public WalletOrderDetailBean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
