package com.xiaomai.zhuangba.data.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 */
public class OngoingOrdersList implements Parcelable{

    /** 订单编号 */
    private String orderCode;
    /** 服务 类型 大类*/
    private int serviceId;
    /** 公共安全-门禁  大类名称-小类名称 */
    private String serviceText;
    /** 任务数量 */
    private int number;
    /** 订单金额 */
    private double orderAmount;
    /** 订单状态  */
    private int orderStatus;
    /** 现场联系人姓名 */
    private String name;
    /** 现场联系人电话 */
    private String telephone;
    /** 现场联系人地址 */
    private String address;
    /** 预约时间 */
    private String appointmentTime;
    /** 经度 */
    private float longitude;
    /** 纬度 */
    private float latitude;
    /** 发布人 */
    private String publisher;
    /** 服务项目 */
    private List<Object> orderServices;

    /** 师傅端的部分字段 */
    /**  */
    private double masterOrderAmount;
    /** 师傅确认时间 */
    private String confirmationTime;
    /** 修改时间 */
    private String modifyTime;
    /** 已超时 订单状态 = 7 已过期  内容 18:22 */
    /** 已超时 内容 已超时 */
    private String expireTime;

    /** 接受方名称 */
    private String userText;
    /** 接受方头像 */
    private String bareheadedPhotoUrl;

    /** 是否有维保 */
    private int maintenanceFlag;

    /** 开槽  如果是广告单 则是 预约时间 */
    private String slottingStartLength;
    private String slottingEndLength;
    private double slottingPrice;

    private String debugging;
    private double debugPrice;

    private String materialsStartLength;
    private String materialsEndLength;
    private double materialsPrice;

    /** 1安装单 2 广告单 */
    private String orderType;

    /** 备注说明 */
    private String employerDescribe;
    /** 广告图 */
    private String picturesUrl;

    /** 团长手机号 如果不为空 则是团队的订单 否则不是*/
    private String assigner;

    /** 0：代表没有发起过空跑，其余状态为发起过空跑 */
    private Integer flag;
    /**
     * 是否选中
     */
    private boolean isCheck;

    /** 是否加急 y 是 n 不是 */
    private String urgent;

    /**合同编号**/
    private String contractNo;

    /**客户经理*/
    private String accountManager;

    /**项目名称*/
    private String projectName;

    /**项目特点*/
    private String projectFeatures;

    /**店铺名称*/
    private String shopName;

    /**第三方订单编号*/
    private String orderNumber;

    /**新增项实际金额*/
    private double increasedeCreaseAmount;

    /**新增项师傅得到的金额*/
    private double increasedeCreaseMasterAmount;

    public OngoingOrdersList() {
    }

    protected OngoingOrdersList(Parcel in) {
        orderCode = in.readString();
        serviceId = in.readInt();
        serviceText = in.readString();
        number = in.readInt();
        orderAmount = in.readDouble();
        orderStatus = in.readInt();
        name = in.readString();
        telephone = in.readString();
        address = in.readString();
        appointmentTime = in.readString();
        longitude = in.readFloat();
        latitude = in.readFloat();
        publisher = in.readString();
        masterOrderAmount = in.readDouble();
        modifyTime = in.readString();
        expireTime = in.readString();
    }

    public static final Parcelable.Creator<OngoingOrdersList> CREATOR = new Parcelable.Creator<OngoingOrdersList>() {
        @Override
        public OngoingOrdersList createFromParcel(Parcel in) {
            return new OngoingOrdersList(in);
        }

        @Override
        public OngoingOrdersList[] newArray(int size) {
            return new OngoingOrdersList[size];
        }
    };

    public String getOrderCode() {
        return TextUtils.isEmpty(orderCode) ? "" : orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getSlottingStartLength() {
        return TextUtils.isEmpty(slottingStartLength) ? "" : slottingStartLength;
    }

    public void setSlottingStartLength(String slottingStartLength) {
        this.slottingStartLength = slottingStartLength;
    }

    public String getSlottingEndLength() {
        return TextUtils.isEmpty(slottingEndLength) ? "" : slottingEndLength;
    }

    public String getUrgent() {
        return urgent == null ? "" : urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public void setSlottingEndLength(String slottingEndLength) {
        this.slottingEndLength = slottingEndLength;
    }

    public String getMaterialsStartLength() {
        return TextUtils.isEmpty(materialsStartLength) ? "" : materialsStartLength;
    }

    public void setMaterialsStartLength(String materialsStartLength) {
        this.materialsStartLength = materialsStartLength;
    }

    public String getMaterialsEndLength() {
        return materialsEndLength;
    }

    public void setMaterialsEndLength(String materialsEndLength) {
        this.materialsEndLength = materialsEndLength;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceText() {
        return TextUtils.isEmpty(serviceText) ? "" : serviceText;
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

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getName() {
        return TextUtils.isEmpty(name) ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return TextUtils.isEmpty(telephone) ? "" : telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return TextUtils.isEmpty(address) ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmployerDescribe() {
        return TextUtils.isEmpty(employerDescribe) ? "" : employerDescribe;
    }

    public void setEmployerDescribe(String employerDescribe) {
        this.employerDescribe = employerDescribe;
    }

    /** 格式化预约时间 */
    public String getAppointmentTime() {
        return TextUtils.isEmpty(appointmentTime) ? appointmentTime : appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getMasterOrderAmount() {
        return masterOrderAmount;
    }

    public String getDebugging() {
        return debugging;
    }

    public void setDebugging(String debugging) {
        this.debugging = debugging;
    }

    public void setMasterOrderAmount(double masterOrderAmount) {
        this.masterOrderAmount = masterOrderAmount;
    }

    public List<Object> getOrderServices() {
        return orderServices;
    }

    public void setOrderServices(List<Object> orderServices) {
        this.orderServices = orderServices;
    }

    public String getUserText() {
        return TextUtils.isEmpty(userText) ? "" : userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public String getBareheadedPhotoUrl() {
        return TextUtils.isEmpty(bareheadedPhotoUrl) ? "" : bareheadedPhotoUrl;
    }

    public void setBareheadedPhotoUrl(String bareheadedPhotoUrl) {
        this.bareheadedPhotoUrl = bareheadedPhotoUrl;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getExpireTime() {
        return TextUtils.isEmpty(expireTime) ? "" : expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getConfirmationTime() {
        return TextUtils.isEmpty(confirmationTime) ? "" : confirmationTime;
    }

    public void setConfirmationTime(String confirmationTime) {
        this.confirmationTime = confirmationTime;
    }

    public String getPicturesUrl() {
        return TextUtils.isEmpty(picturesUrl) ? "" : picturesUrl;
    }

    public void setPicturesUrl(String picturesUrl) {
        this.picturesUrl = picturesUrl;
    }

    public int getMaintenanceFlag() {
        return maintenanceFlag;
    }

    public void setMaintenanceFlag(int maintenanceFlag) {
        this.maintenanceFlag = maintenanceFlag;
    }

    public String getOrderType() {
        return TextUtils.isEmpty(orderType) ? "" : orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public double getSlottingPrice() {
        return slottingPrice;
    }

    public void setSlottingPrice(double slottingPrice) {
        this.slottingPrice = slottingPrice;
    }

    public double getDebugPrice() {
        return debugPrice;
    }

    public void setDebugPrice(double debugPrice) {
        this.debugPrice = debugPrice;
    }

    public double getMaterialsPrice() {
        return materialsPrice;
    }

    public void setMaterialsPrice(double materialsPrice) {
        this.materialsPrice = materialsPrice;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getAssigner() {
        return assigner == null ? "" : assigner;
    }

    public void setAssigner(String assigner) {
        this.assigner = assigner;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getContractNo() {
        return contractNo == null ? "" : contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getAccountManager() {
        return accountManager == null ? "" : accountManager;
    }

    public void setAccountManager(String accountManager) {
        this.accountManager = accountManager;
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectFeatures() {
        return projectFeatures == null ? "" : projectFeatures;
    }

    public void setProjectFeatures(String projectFeatures) {
        this.projectFeatures = projectFeatures;
    }

    public String getShopName() {
        return shopName == null ? "" : shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOrderNumber() {
        return orderNumber == null ? "" : orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public double getIncreasedeCreaseAmount() {
        return increasedeCreaseAmount;
    }

    public void setIncreasedeCreaseAmount(double increasedeCreaseAmount) {
        this.increasedeCreaseAmount = increasedeCreaseAmount;
    }

    public double getIncreasedeCreaseMasterAmount() {
        return increasedeCreaseMasterAmount;
    }

    public void setIncreasedeCreaseMasterAmount(double increasedeCreaseMasterAmount) {
        this.increasedeCreaseMasterAmount = increasedeCreaseMasterAmount;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderCode);
        dest.writeInt(serviceId);
        dest.writeString(serviceText);
        dest.writeInt(number);
        dest.writeDouble(orderAmount);
        dest.writeInt(orderStatus);
        dest.writeString(name);
        dest.writeString(telephone);
        dest.writeString(address);
        dest.writeString(appointmentTime);
        dest.writeFloat(longitude);
        dest.writeFloat(latitude);
        dest.writeString(publisher);
        dest.writeDouble(masterOrderAmount);
        dest.writeString(modifyTime);
        dest.writeString(expireTime);
    }
}
