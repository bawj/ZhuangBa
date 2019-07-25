package com.xiaomai.zhuangba.enums;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 */
public enum ForResultCode {
    /**
     * startForResult
     */
    START_FOR_RESULT_CODE(1000, "startForResult code"),
    RESULT_OK(-1 , "setFragmentResult code"),
    RESULT_KEY(100 , "result_key"),
    LONGITUDE(101 , "longitude"),
    LATITUDE(102 , "latitude"),
    ;

    private int code;
    private String explain;

    ForResultCode(int code, String explain) {
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
