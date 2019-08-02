package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 * 技能列表
 */
public class SkillList {

    /**
     * phoneNumber : string
     * skillId : 0
     * skillText : string
     */

    /** 用户编码 技能列表中有用户编码，代表之前选择过*/
    private String phoneNumber;
    /** 技能编码  */
    private int skillId;
    /** 技能名称 */
    private String skillText;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getSkillText() {
        return skillText;
    }

    public void setSkillText(String skillText) {
        this.skillText = skillText;
    }

}
