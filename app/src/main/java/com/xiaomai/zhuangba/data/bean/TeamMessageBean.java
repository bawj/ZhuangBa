package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/10/18 0018
 */
public class TeamMessageBean {

    /**
     * id : 35
     * teamId : 24
     * userNumber : 15513399008
     * isAgree : 1
     * createTime : 2019-10-18 15:18:01
     * isDelete : 0
     */

    private int id;
    private int teamId;
    private String userNumber;
    private int isAgree;
    private String createTime;
    private int isDelete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public int getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(int isAgree) {
        this.isAgree = isAgree;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}
