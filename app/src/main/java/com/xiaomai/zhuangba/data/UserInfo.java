package com.xiaomai.zhuangba.data;

import android.text.TextUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Administrator
 * @date 2019/5/9 0009
 */
@Entity
public class UserInfo {
    private Long id;
    /**
     * 用户编码
     */
    private String phoneNumber;
    /**
     * 用户名称
     */
    private String userText;
    /**
     * 密码
     */
    private String password;
    /**
     * 是否锁定
     */
    private int lockFlag;
    /**
     * 邀请码
     */
    private String invitationCode;
    /**
     * token
     */
    private String token;
    /**
     * 角色;0:师傅;1:雇主
     */
    private String role;
    /**
     * 注册时间
     */
    private String registrationTime;
    /**
     * 身份证号
     */
    private String identityCard;
    /**
     * 身份证有效期起
     */
    private String validityDataStart;
    /**
     * 身份证有效期止
     */
    private String validityDataEnd;
    /**
     * 身份证正面照地址
     */
    private String idCardFrontPhoto;
    /**
     * 身份证背面照地址
     */
    private String idCardBackPhoto;
    /**
     * 身份证有效期，以~分割
     */
    private String validityData;
    /**
     * 免冠照地址
     */
    private String bareHeadedPhotoUrl;
    /**
     * 认证状态:0:未认证;1:已认证;2:审核中
     */
    private int authenticationStatue;
    /**
     * 认证时间
     */
    private String authenticationTime;
    /**
     * 地址
     */
    private String address;
    /**
     * 地址的经度
     */
    private double longitude;
    /**
     * 地址的纬度
     */
    private double latitude;

    private int startFlag;
    /**
     * 是否缴了保证金
     */
    private int payFlag;

    @Generated(hash = 1934400062)
    public UserInfo(Long id, String phoneNumber, String userText, String password,
                    int lockFlag, String invitationCode, String token, String role,
                    String registrationTime, String identityCard, String validityDataStart,
                    String validityDataEnd, String idCardFrontPhoto, String idCardBackPhoto,
                    String validityData, String bareHeadedPhotoUrl,
                    int authenticationStatue, String authenticationTime, String address,
                    double longitude, double latitude, int startFlag, int payFlag) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.userText = userText;
        this.password = password;
        this.lockFlag = lockFlag;
        this.invitationCode = invitationCode;
        this.token = token;
        this.role = role;
        this.registrationTime = registrationTime;
        this.identityCard = identityCard;
        this.validityDataStart = validityDataStart;
        this.validityDataEnd = validityDataEnd;
        this.idCardFrontPhoto = idCardFrontPhoto;
        this.idCardBackPhoto = idCardBackPhoto;
        this.validityData = validityData;
        this.bareHeadedPhotoUrl = bareHeadedPhotoUrl;
        this.authenticationStatue = authenticationStatue;
        this.authenticationTime = authenticationTime;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.startFlag = startFlag;
        this.payFlag = payFlag;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return TextUtils.isEmpty(this.phoneNumber) ? "" : this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserText() {
        return TextUtils.isEmpty(this.userText) ? "" : this.userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLockFlag() {
        return this.lockFlag;
    }

    public void setLockFlag(int lockFlag) {
        this.lockFlag = lockFlag;
    }

    public String getInvitationCode() {
        return this.invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getToken() {
        return TextUtils.isEmpty(this.token) ? "" : this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRegistrationTime() {
        return this.registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getIdentityCard() {
        return this.identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getValidityDataStart() {
        return this.validityDataStart;
    }

    public void setValidityDataStart(String validityDataStart) {
        this.validityDataStart = validityDataStart;
    }

    public String getValidityDataEnd() {
        return this.validityDataEnd;
    }

    public void setValidityDataEnd(String validityDataEnd) {
        this.validityDataEnd = validityDataEnd;
    }

    public String getIdCardFrontPhoto() {
        return this.idCardFrontPhoto;
    }

    public void setIdCardFrontPhoto(String idCardFrontPhoto) {
        this.idCardFrontPhoto = idCardFrontPhoto;
    }

    public String getIdCardBackPhoto() {
        return this.idCardBackPhoto;
    }

    public void setIdCardBackPhoto(String idCardBackPhoto) {
        this.idCardBackPhoto = idCardBackPhoto;
    }

    public String getValidityData() {
        return this.validityData;
    }

    public void setValidityData(String validityData) {
        this.validityData = validityData;
    }

    public String getBareHeadedPhotoUrl() {
        return TextUtils.isEmpty(this.bareHeadedPhotoUrl) ? "" : this.bareHeadedPhotoUrl;
    }

    public void setBareHeadedPhotoUrl(String bareHeadedPhotoUrl) {
        this.bareHeadedPhotoUrl = bareHeadedPhotoUrl;
    }

    public int getAuthenticationStatue() {
        return this.authenticationStatue;
    }

    public void setAuthenticationStatue(int authenticationStatue) {
        this.authenticationStatue = authenticationStatue;
    }

    public String getAuthenticationTime() {
        return this.authenticationTime;
    }

    public void setAuthenticationTime(String authenticationTime) {
        this.authenticationTime = authenticationTime;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getStartFlag() {
        return this.startFlag;
    }

    public void setStartFlag(int startFlag) {
        this.startFlag = startFlag;
    }

    public int getPayFlag() {
        return this.payFlag;
    }

    public void setPayFlag(int payFlag) {
        this.payFlag = payFlag;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userText='" + userText + '\'' +
                ", password='" + password + '\'' +
                ", lockFlag=" + lockFlag +
                ", invitationCode='" + invitationCode + '\'' +
                ", token='" + token + '\'' +
                ", role='" + role + '\'' +
                ", registrationTime='" + registrationTime + '\'' +
                ", identityCard='" + identityCard + '\'' +
                ", validityDataStart='" + validityDataStart + '\'' +
                ", validityDataEnd='" + validityDataEnd + '\'' +
                ", idCardFrontPhoto='" + idCardFrontPhoto + '\'' +
                ", idCardBackPhoto='" + idCardBackPhoto + '\'' +
                ", validityData='" + validityData + '\'' +
                ", bareHeadedPhotoUrl='" + bareHeadedPhotoUrl + '\'' +
                ", authenticationStatue=" + authenticationStatue +
                ", authenticationTime='" + authenticationTime + '\'' +
                ", address='" + address + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", startFlag=" + startFlag +
                ", payFlag=" + payFlag +
                '}';
    }
}
