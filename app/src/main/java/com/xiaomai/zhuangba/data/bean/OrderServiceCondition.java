package com.xiaomai.zhuangba.data.bean;

/**
 * @author Bawj
 * CreateDate:     2020/3/12 0012 09:13
 */
public class OrderServiceCondition {

    /**订单编号*/
    private String orderCode;

    /**服务项目ID*/
    private int serviceId;

    /**服务项目名称*/
    private String serviceText;

    /**图标地址*/
    private String iconUrl;

    /**数量*/
    private int number;

    /**项目金额*/
    private double amount;

    /**项目金额*/
    private double price;

    /**项目金额*/
    private double price2;

    /**项目金额*/
    private double price3;

    /**月的数量*/
    private int monthNumber;

    /**维保的金额*/
    private double maintenanceAmount;

    /**辅材的金额*/
    private double materialPrice;

    /**视频链接*/
    private String video;

    /**服务标准*/
    private String serviceStandard;

    /**确认人*/
    private String confirmor;

    /**支付CODE*/
    private String code;

    /**抽成比例*/
    private Integer rake;

    /**师傅得到的金额*/
    private double masterOrderAmount;


    /**开始的长度*/
    private String slottingStartLength;

    /**结束长度*/
    private String slottingEndLength;

    /**开槽价格*/
    private double slottingPrice;

    /**辅材开始的长度*/
    private String materialsStartLength;

    /**辅材结束长度*/
    private String materialsEndLength;

    /**辅材价格*/
    private double materialsPrice;

    public String getOrderCode() {
        return orderCode == null ? "" : orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceText() {
        return serviceText == null ? "" : serviceText;
    }

    public void setServiceText(String serviceText) {
        this.serviceText = serviceText;
    }

    public String getIconUrl() {
        return iconUrl == null ? "" : iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice2() {
        return price2;
    }

    public void setPrice2(double price2) {
        this.price2 = price2;
    }

    public double getPrice3() {
        return price3;
    }

    public void setPrice3(double price3) {
        this.price3 = price3;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    public double getMaintenanceAmount() {
        return maintenanceAmount;
    }

    public void setMaintenanceAmount(double maintenanceAmount) {
        this.maintenanceAmount = maintenanceAmount;
    }

    public double getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(double materialPrice) {
        this.materialPrice = materialPrice;
    }

    public String getVideo() {
        return video == null ? "" : video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getServiceStandard() {
        return serviceStandard == null ? "" : serviceStandard;
    }

    public void setServiceStandard(String serviceStandard) {
        this.serviceStandard = serviceStandard;
    }

    public String getConfirmor() {
        return confirmor == null ? "" : confirmor;
    }

    public void setConfirmor(String confirmor) {
        this.confirmor = confirmor;
    }

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getRake() {
        return rake;
    }

    public void setRake(Integer rake) {
        this.rake = rake;
    }

    public double getMasterOrderAmount() {
        return masterOrderAmount;
    }

    public void setMasterOrderAmount(double masterOrderAmount) {
        this.masterOrderAmount = masterOrderAmount;
    }

    public String getSlottingStartLength() {
        return slottingStartLength == null ? "" : slottingStartLength;
    }

    public void setSlottingStartLength(String slottingStartLength) {
        this.slottingStartLength = slottingStartLength;
    }

    public String getSlottingEndLength() {
        return slottingEndLength == null ? "" : slottingEndLength;
    }

    public void setSlottingEndLength(String slottingEndLength) {
        this.slottingEndLength = slottingEndLength;
    }

    public double getSlottingPrice() {
        return slottingPrice;
    }

    public void setSlottingPrice(double slottingPrice) {
        this.slottingPrice = slottingPrice;
    }

    public String getMaterialsStartLength() {
        return materialsStartLength == null ? "" : materialsStartLength;
    }

    public void setMaterialsStartLength(String materialsStartLength) {
        this.materialsStartLength = materialsStartLength;
    }

    public String getMaterialsEndLength() {
        return materialsEndLength == null ? "" : materialsEndLength;
    }

    public void setMaterialsEndLength(String materialsEndLength) {
        this.materialsEndLength = materialsEndLength;
    }

    public double getMaterialsPrice() {
        return materialsPrice;
    }

    public void setMaterialsPrice(double materialsPrice) {
        this.materialsPrice = materialsPrice;
    }
}
