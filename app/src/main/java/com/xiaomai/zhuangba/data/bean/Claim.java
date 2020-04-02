package com.xiaomai.zhuangba.data.bean;

/**
 * @author Bawj
 * CreateDate:     2020/1/6 0006 16:15
 */
public class Claim {

    /**主键*/
    private int id;

    /**订单编号*/
    private String orderCode;

    /**编号*/
    private String code;

    /**来源:0 安装单，1 广告单*/
    private Integer source;

    /**索赔企业*/
    private String companyName;

    /**索赔企业id*/
    private String companyId;

    /**申请赔偿金额*/
    private Double price;

    /**雇主申请理赔图片上传*/
    private String pic;

    /**师傅罚款金额*/
    private Double masterPrice;

    /**雇主发起理赔原因*/
    private String reason;

    /**平台设置师傅罚款理由*/
    private String masterReason;

    /**雇主申请索赔状态：0未赔偿, 1已赔偿,2拒绝赔偿,3已取消*/
    private Integer businessStatus;

    /**师傅罚款状态:0未设置, 1未赔偿,2已赔偿,3已取消*/
    private Integer masterStatus;

    /**创建人姓名(发起人)*/
    private String createUserName;

    /**创建人联系电话*/
    private String phone;

    /**创建时间*/
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode == null ? "" : orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPic() {
        return pic == null ? "" : pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Double getMasterPrice() {
        return masterPrice;
    }

    public void setMasterPrice(Double masterPrice) {
        this.masterPrice = masterPrice;
    }

    public String getMasterReason() {
        return masterReason == null ? "" : masterReason;
    }

    public void setMasterReason(String masterReason) {
        this.masterReason = masterReason;
    }

    public String getReason() {
        return reason == null ? "" : reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(Integer businessStatus) {
        this.businessStatus = businessStatus;
    }

    public Integer getMasterStatus() {
        return masterStatus;
    }

    public void setMasterStatus(Integer masterStatus) {
        this.masterStatus = masterStatus;
    }

    public String getCreateUserName() {
        return createUserName == null ? "" : createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
