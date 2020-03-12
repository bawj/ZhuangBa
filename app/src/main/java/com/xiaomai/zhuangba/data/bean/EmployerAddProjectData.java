package com.xiaomai.zhuangba.data.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bawj
 * CreateDate:     2020/3/12 0012 09:08
 */
public class EmployerAddProjectData {

    /**主键*/
    private Integer id;

    /**订单编号*/
    private String orderCode;

    /**服务集合*/
    private List<OrderServiceCondition> orderServiceList = new ArrayList<>();

    /**开始的长度*/
    private String slottingStartLength;

    /**结束长度*/
    private String slottingEndLength;

    /**开槽价格*/
    private double slottingPrice;

    /**是否调试;0为不调试；1为调试*/
    private Integer debugging;

    /**调试价格*/
    private double debugPrice;

    /**辅材开始的长度*/
    private String materialsStartLength;

    /**辅材结束长度*/
    private String materialsEndLength;

    /**辅材价格*/
    private double materialsPrice;

    /**创建时间*/
    private String createTime;

    /**确认人*/
    private String confirmor;

    /**支付CODE*/
    private String code;

    /**总金额*/
    private Double totalAmount;

    /**师傅得到的金额*/
    private double masterOrderAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode == null ? "" : orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public List<OrderServiceCondition> getOrderServiceList() {
        if (orderServiceList == null) {
            return new ArrayList<>();
        }
        return orderServiceList;
    }

    public void setOrderServiceList(List<OrderServiceCondition> orderServiceList) {
        this.orderServiceList = orderServiceList;
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

    public Integer getDebugging() {
        return debugging;
    }

    public void setDebugging(Integer debugging) {
        this.debugging = debugging;
    }

    public double getDebugPrice() {
        return debugPrice;
    }

    public void setDebugPrice(double debugPrice) {
        this.debugPrice = debugPrice;
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

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getMasterOrderAmount() {
        return masterOrderAmount;
    }

    public void setMasterOrderAmount(double masterOrderAmount) {
        this.masterOrderAmount = masterOrderAmount;
    }
}
