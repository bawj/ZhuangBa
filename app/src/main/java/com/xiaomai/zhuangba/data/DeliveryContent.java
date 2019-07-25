package com.xiaomai.zhuangba.data;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 * 任务开始前
 * 任务完成后
 * 雇主评价
 */
public class DeliveryContent {

    /** 创建时间 */
    private String createTime;
    /** 现场负责人签名图片 */
    private String electronicSignature;
    /** 雇主说明 */
    private String employerDescribe;
    /** 师傅描述 */
    private String masterDescribe;
    /** 评价等级 */
    private String opinionRating;
    /** 订单编号 */
    private String orderCode;
    /** 图片类型:1:开始前的图片地址;2:完成后的图片地址; */
    private String picturesType;
    /** 图片地址 */
    private String picturesUrl;
    /** 主键 */
    private String validation;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getElectronicSignature() {
        return electronicSignature;
    }

    public void setElectronicSignature(String electronicSignature) {
        this.electronicSignature = electronicSignature;
    }

    public String getEmployerDescribe() {
        return employerDescribe;
    }

    public void setEmployerDescribe(String employerDescribe) {
        this.employerDescribe = employerDescribe;
    }

    public String getMasterDescribe() {
        return masterDescribe;
    }

    public void setMasterDescribe(String masterDescribe) {
        this.masterDescribe = masterDescribe;
    }

    public String getOpinionRating() {
        return opinionRating;
    }

    public void setOpinionRating(String opinionRating) {
        this.opinionRating = opinionRating;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPicturesType() {
        return picturesType;
    }

    public void setPicturesType(String picturesType) {
        this.picturesType = picturesType;
    }

    public String getPicturesUrl() {
        return picturesUrl;
    }

    public void setPicturesUrl(String picturesUrl) {
        this.picturesUrl = picturesUrl;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }
}

