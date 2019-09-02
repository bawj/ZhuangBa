package com.xiaomai.zhuangba.data.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 * 服务项目
 */
@Entity
public class OrderServiceItem{

    /**
     * orderCode : 20190517124905903415
     * serviceId : 13
     * serviceText : 门禁综合布线
     * number : 2
     * amount : 140.0
     * iconUrl : https://zb.4000750222.com//zbimages/test/2019/5/14/2c90ef856ab3eef8016ab3eef89a0000.png
     */
    @Id(autoincrement = true)
    private Long id;
    private String orderCode;
    private int serviceId;
    private String serviceText;
    private int number;
    private double amount;
    private double price2;
    private double price3;
    private String iconUrl;
    private String serviceStandard;
    private String video;

    private String slottingStartLength;
    private String slottingEndLength;
    private String debugging;
    private String materialsStartLength;
    private String materialsEndLength;
    /**
     * 维保时间 单位（月）
     */
    private int monthNumber;

    /**
     * 维保的金额
     */
    private double maintenanceAmount;


    /** 维保选中ID */
    private int maintenanceId;

    public OrderServiceItem() {
    }

    protected OrderServiceItem(Parcel in) {
        orderCode = in.readString();
        serviceId = in.readInt();
        serviceText = in.readString();
        number = in.readInt();
        amount = in.readDouble();
        price2 = in.readDouble();
        price3 = in.readDouble();
        iconUrl = in.readString();
        serviceStandard = in.readString();
        monthNumber = in.readInt();
        maintenanceAmount = in.readDouble();
    }

    @Generated(hash = 412746038)
    public OrderServiceItem(Long id, String orderCode, int serviceId, String serviceText, int number,
            double amount, double price2, double price3, String iconUrl, String serviceStandard, String video,
            String slottingStartLength, String slottingEndLength, String debugging, String materialsStartLength,
            String materialsEndLength, int monthNumber, double maintenanceAmount, int maintenanceId) {
        this.id = id;
        this.orderCode = orderCode;
        this.serviceId = serviceId;
        this.serviceText = serviceText;
        this.number = number;
        this.amount = amount;
        this.price2 = price2;
        this.price3 = price3;
        this.iconUrl = iconUrl;
        this.serviceStandard = serviceStandard;
        this.video = video;
        this.slottingStartLength = slottingStartLength;
        this.slottingEndLength = slottingEndLength;
        this.debugging = debugging;
        this.materialsStartLength = materialsStartLength;
        this.materialsEndLength = materialsEndLength;
        this.monthNumber = monthNumber;
        this.maintenanceAmount = maintenanceAmount;
        this.maintenanceId = maintenanceId;
    }

    public String getOrderCode() {
        return orderCode;
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
        return serviceText;
    }

    public void setServiceText(String serviceText) {
        this.serviceText = serviceText;
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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getServiceStandard() {
        return TextUtils.isEmpty(serviceStandard) ? "" : serviceStandard;
    }

    public void setServiceStandard(String serviceStandard) {
        this.serviceStandard = serviceStandard;
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

    public int getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(int maintenanceId) {
        this.maintenanceId = maintenanceId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
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

    public String getDebugging() {
        return debugging == null ? "" : debugging;
    }

    public void setDebugging(String debugging) {
        this.debugging = debugging;
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
}
