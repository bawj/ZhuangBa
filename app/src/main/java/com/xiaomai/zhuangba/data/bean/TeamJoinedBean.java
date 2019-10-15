package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/10/15 0015
 */
public class TeamJoinedBean {

    /**
     * id : 1
     * teamid : null
     * usernumber : 15375366744
     * userName : 佘鹏飞
     * createtime : 2019-10-15 11:46:53
     * isdelete : null
     * isAgree : null
     */

    /** 1团长 2 团员 */
    private int id;
    private String teamid;
    private String usernumber;
    private String userName;
    private String createtime;
    private String isdelete;
    private String isAgree;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamid() {
        return teamid == null ? "" : teamid;
    }

    public void setTeamid(String teamid) {
        this.teamid = teamid;
    }

    public String getUsernumber() {
        return usernumber == null ? "" : usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public String getUserName() {
        return userName == null ? "" : userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreatetime() {
        return createtime == null ? "" : createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getIsdelete() {
        return isdelete == null ? "" : isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

    public String getIsAgree() {
        return isAgree == null ? "" : isAgree;
    }

    public void setIsAgree(String isAgree) {
        this.isAgree = isAgree;
    }
}
