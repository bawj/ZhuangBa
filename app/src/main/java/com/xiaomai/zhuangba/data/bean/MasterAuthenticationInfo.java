package com.xiaomai.zhuangba.data.bean;

import android.text.TextUtils;

/**
 * @author Administrator
 * @date 2019/8/2 0002
 */
public class MasterAuthenticationInfo {

    /** 姓名 */
    private String userText;
    /** 身份证号码 */
    private String identityCard;
    /** 身份证有效日期 */
    private String validityData;
    /** 身份证正面 */
    private String idCardFrontPhoto;
    /** 身份证反面 */
    private String idCardBackPhoto;
    /** 头像 */
    private String photoPath;

    /** 服务地址 */
    private String address;
    /** longitude */
    private double longitude;
    /** latitude */
    private double latitude;

    private String emergencyContact;
    private String contactAddress;


    public String getUserText() {
        return TextUtils.isEmpty(userText) ? "" : userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public String getIdentityCard() {
        return TextUtils.isEmpty(identityCard) ? "" : identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getValidityData() {
        return TextUtils.isEmpty(validityData) ? "" : validityData;
    }

    public void setValidityData(String validityData) {
        this.validityData = validityData;
    }

    public String getIdCardFrontPhoto() {
        return TextUtils.isEmpty(idCardFrontPhoto) ? "" : idCardFrontPhoto;
    }

    public void setIdCardFrontPhoto(String idCardFrontPhoto) {
        this.idCardFrontPhoto = idCardFrontPhoto;
    }

    public String getIdCardBackPhoto() {
        return TextUtils.isEmpty(idCardBackPhoto) ? "" : idCardBackPhoto;
    }

    public void setIdCardBackPhoto(String idCardBackPhoto) {
        this.idCardBackPhoto = idCardBackPhoto;
    }

    public String getPhotoPath() {
        return TextUtils.isEmpty(photoPath) ? "" : photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getAddress() {
        return TextUtils.isEmpty(address) ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getEmergencyContact() {
        return TextUtils.isEmpty(emergencyContact) ? "" : emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getContactAddress() {
        return TextUtils.isEmpty(contactAddress) ? "" : contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }
}
