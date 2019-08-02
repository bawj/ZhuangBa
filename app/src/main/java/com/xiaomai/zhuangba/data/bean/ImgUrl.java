package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/8/2 0002
 */
public class ImgUrl {

    /**
     * 身份证正面
     */
    private String frontPhoto;

    /**
     * 身份证反面
     */
    private String idCardBackPhoto;


    public String getFrontPhoto() {
        return frontPhoto;
    }

    public void setFrontPhoto(String frontPhoto) {
        this.frontPhoto = frontPhoto;
    }

    public String getIdCardBackPhoto() {
        return idCardBackPhoto;
    }

    public void setIdCardBackPhoto(String idCardBackPhoto) {
        this.idCardBackPhoto = idCardBackPhoto;
    }
}
