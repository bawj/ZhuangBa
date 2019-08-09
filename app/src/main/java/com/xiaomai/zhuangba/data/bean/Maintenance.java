package com.xiaomai.zhuangba.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Administrator
 * @date 2019/8/6 0006
 *
 * 维保服务
 */
public class Maintenance {

    /** 主键id */
    private Integer id;

    /** 服务id */
    private Integer serviceId;

    /** 维保数量(月份) */
    private Integer number;

    /** 维保金额 */
    private Double amount;

    /** 剩余维保数量 */
    private Integer residualQuantity;

    /** 剩余维保金额 */
    private Double residualAmount;

    /** 师傅姓名 */
    private String overmanName;

    /** 师傅手机号 */
    private String overmanPhone;

    /** 服务名 */
    private String serviceName;

    /** 开始时间 */
    private String startTime;

    /** 结束时间 */
    private String endTime;

    /** 支付状态 0已支付,1未支付 */
    private String status;

    /**
     * 暂不选中维保
     */
    private String notChoosingMaintenance;

    @Generated(hash = 2115835481)
    public Maintenance(Integer id, Integer serviceId, Integer number, Double amount,
            Integer residualQuantity, Double residualAmount, String overmanName,
            String overmanPhone, String serviceName, String startTime,
            String endTime, String status, String notChoosingMaintenance) {
        this.id = id;
        this.serviceId = serviceId;
        this.number = number;
        this.amount = amount;
        this.residualQuantity = residualQuantity;
        this.residualAmount = residualAmount;
        this.overmanName = overmanName;
        this.overmanPhone = overmanPhone;
        this.serviceName = serviceName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.notChoosingMaintenance = notChoosingMaintenance;
    }

    @Generated(hash = 1111935477)
    public Maintenance() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getResidualQuantity() {
        return residualQuantity;
    }

    public void setResidualQuantity(Integer residualQuantity) {
        this.residualQuantity = residualQuantity;
    }

    public Double getResidualAmount() {
        return residualAmount;
    }

    public void setResidualAmount(Double residualAmount) {
        this.residualAmount = residualAmount;
    }

    public String getOvermanName() {
        return overmanName;
    }

    public void setOvermanName(String overmanName) {
        this.overmanName = overmanName;
    }

    public String getOvermanPhone() {
        return overmanPhone;
    }

    public void setOvermanPhone(String overmanPhone) {
        this.overmanPhone = overmanPhone;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotChoosingMaintenance() {
        return notChoosingMaintenance;
    }

    public void setNotChoosingMaintenance(String notChoosingMaintenance) {
        this.notChoosingMaintenance = notChoosingMaintenance;
    }
}
