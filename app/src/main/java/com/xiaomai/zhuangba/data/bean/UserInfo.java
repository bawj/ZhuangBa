package com.xiaomai.zhuangba.data.bean;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/5/9 0009
 */
@Entity
public class UserInfo {
    @Id
    private Long id;
    /** 用户编码  */
    private String phoneNumber;
    /** 用户名称 */
    private String userText;
    /** 密码 */
    private String password;
    /** 是否锁定 */
    private int lockFlag;
    /** 邀请码 */
    private String invitationCode;
    /** token */
    private String token;
    /** 角色;0:师傅;1:雇主 */
    private String role;
    /** 注册时间 */
    private String registrationTime;
    /** 身份证号 */
    private String identityCard;
    /** 身份证有效期起 */
    private String validityDataStart;
    /** 身份证有效期止 */
    private String validityDataEnd;
    /** 身份证正面照地址 */
    private String idCardFrontPhoto;
    /** 身份证背面照地址 */
    private String idCardBackPhoto;
    /** 身份证有效期，以~分割  */
    private String validityData;
    /** 免冠照地址 */
    private String bareHeadedPhotoUrl;
    /** 认证状态:0:未认证;1:已认证;2:审核中 */
    private int authenticationStatue;
    /** 认证时间 */
    private String authenticationTime;
    /** 地址 */
    private String address;
    /** 地址的经度 */
    private double longitude;
    /** 地址的纬度 */
    private double latitude;

    /** 营业执照地址 */
    private String businessLicense;
    /** 紧急联系人电话 */
    private String emergencyContact;
    /** 紧急联系人地址 */
    private String contactAddress;

    private int startFlag;
    /** 是否缴了保证金 */
    private int payFlag;

    /** 1 观察员  2  实习  3 正式 */
    private String masterRankId;
    /** 师傅等级名称 */
    private String  masterRankName;

    @Transient
    private List<SkillList> skills;

    @Generated(hash = 83677249)
    public UserInfo(Long id, String phoneNumber, String userText, String password, int lockFlag,
                    String invitationCode, String token, String role, String registrationTime, String identityCard,
                    String validityDataStart, String validityDataEnd, String idCardFrontPhoto, String idCardBackPhoto,
                    String validityData, String bareHeadedPhotoUrl, int authenticationStatue, String authenticationTime,
                    String address, double longitude, double latitude, String businessLicense, String emergencyContact,
                    String contactAddress, int startFlag, int payFlag, String masterRankId, String masterRankName) {
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
        this.businessLicense = businessLicense;
        this.emergencyContact = emergencyContact;
        this.contactAddress = contactAddress;
        this.startFlag = startFlag;
        this.payFlag = payFlag;
        this.masterRankId = masterRankId;
        this.masterRankName = masterRankName;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserText() {
        return userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(int lockFlag) {
        this.lockFlag = lockFlag;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return TextUtils.isEmpty(role) ? "" : role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getValidityDataStart() {
        return validityDataStart;
    }

    public void setValidityDataStart(String validityDataStart) {
        this.validityDataStart = validityDataStart;
    }

    public String getValidityDataEnd() {
        return validityDataEnd;
    }

    public void setValidityDataEnd(String validityDataEnd) {
        this.validityDataEnd = validityDataEnd;
    }

    public String getIdCardFrontPhoto() {
        return idCardFrontPhoto;
    }

    public void setIdCardFrontPhoto(String idCardFrontPhoto) {
        this.idCardFrontPhoto = idCardFrontPhoto;
    }

    public String getIdCardBackPhoto() {
        return idCardBackPhoto;
    }

    public void setIdCardBackPhoto(String idCardBackPhoto) {
        this.idCardBackPhoto = idCardBackPhoto;
    }

    public String getValidityData() {
        return validityData;
    }

    public void setValidityData(String validityData) {
        this.validityData = validityData;
    }

    public String getBareHeadedPhotoUrl() {
        if (!TextUtils.isEmpty(bareHeadedPhotoUrl)){
            try {
                List<String> strings = new Gson().fromJson(bareHeadedPhotoUrl, new TypeToken<List<String>>() {
                }.getType());
                if (!strings.isEmpty()){
                    return strings.get(0);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return bareHeadedPhotoUrl;
    }

    public void setBareHeadedPhotoUrl(String bareHeadedPhotoUrl) {
        this.bareHeadedPhotoUrl = bareHeadedPhotoUrl;
    }

    public int getAuthenticationStatue() {
        return authenticationStatue;
    }

    public void setAuthenticationStatue(int authenticationStatue) {
        this.authenticationStatue = authenticationStatue;
    }

    public String getAuthenticationTime() {
        return authenticationTime;
    }

    public void setAuthenticationTime(String authenticationTime) {
        this.authenticationTime = authenticationTime;
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

    public int getStartFlag() {
        return startFlag;
    }

    public void setStartFlag(int startFlag) {
        this.startFlag = startFlag;
    }

    public int getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(int payFlag) {
        this.payFlag = payFlag;
    }

    public List<SkillList> getSkillLists() {
        return skills;
    }

    public void setSkillLists(List<SkillList> skillLists) {
        this.skills = skillLists;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getMasterRankId() {
        return TextUtils.isEmpty(masterRankId) ? "" : masterRankId;
    }

    public void setMasterRankId(String masterRankId) {
        this.masterRankId = masterRankId;
    }

    public String getMasterRankName() {
        return masterRankName;
    }

    public void setMasterRankName(String masterRankName) {
        this.masterRankName = masterRankName;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }
}
