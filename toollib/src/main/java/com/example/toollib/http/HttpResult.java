package com.example.toollib.http;


import com.example.toollib.http.version.Version;
import com.google.gson.annotations.SerializedName;

/**
 * Time:2018/11/26 0026
 * @author 悄悄话
 */
public class HttpResult<T> {

    @SerializedName("msg")
    private String msg;

    @SerializedName("code")
    private String code;

    @SerializedName("data")
    private T data;

    private Version version;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public T getData() {
        return data == null ? (T) "" : data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
