package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/11/9 0009
 */
public class MasterPersonalZip {

    private CreateTeamBean createTeamBean;
    private OrderStatistics orderStatistics;
    private Boolean aBoolean;
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Boolean getaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public CreateTeamBean getCreateTeamBean() {
        return createTeamBean;
    }

    public void setCreateTeamBean(CreateTeamBean createTeamBean) {
        this.createTeamBean = createTeamBean;
    }

    public OrderStatistics getOrderStatistics() {
        return orderStatistics;
    }

    public void setOrderStatistics(OrderStatistics orderStatistics) {
        this.orderStatistics = orderStatistics;
    }
}
