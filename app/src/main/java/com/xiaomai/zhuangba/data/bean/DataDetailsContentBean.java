package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/8/11 0011
 */
public class DataDetailsContentBean {

    private String explain;
    private String explainContent;

    public DataDetailsContentBean(String explain, String explainContent) {
        this.explain = explain;
        this.explainContent = explainContent;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getExplainContent() {
        return explainContent;
    }

    public void setExplainContent(String explainContent) {
        this.explainContent = explainContent;
    }
}
