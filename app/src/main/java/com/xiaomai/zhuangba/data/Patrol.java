package com.xiaomai.zhuangba.data;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/9/29 0029
 */
public class Patrol {


    /**
     * pageNum : 1
     * pageSize : 20
     * total : 2
     * pages : 1
     * list : [{"orderCode":"231936155539587072","serviceId":0,"serviceText":"巡查任务","number":1,"orderAmount":300,"maintenanceAmount":0,"masterOrderAmount":0,"orderStatus":0,"name":null,"telephone":null,"address":"北京市北京市朝阳区11","appointmentTime":"2000-01-01 00:00:00","confirmationTime":null,"longitude":0,"latitude":0,"publisher":null,"receivingParty":null,"userText":null,"bareheadedPhotoUrl":null,"modifyTime":"2019-09-28 10:02:05","expireTime":null,"team":null,"maintenanceFlag":0,"orderServices":[],"maintenanceServices":[],"picturesUrl":null,"employerDescribe":null,"slottingStartLength":"month","slottingEndLength":"11","slottingPrice":0,"debugging":8,"debugPrice":0,"materialsStartLength":"","materialsEndLength":"","materialsPrice":0,"orderType":"3"},{"orderCode":null,"serviceId":0,"serviceText":"巡查任务","number":1,"orderAmount":0,"maintenanceAmount":0,"masterOrderAmount":0,"orderStatus":null,"name":null,"telephone":null,"address":null,"appointmentTime":"2000-01-01 00:00:00","confirmationTime":null,"longitude":0,"latitude":0,"publisher":null,"receivingParty":null,"userText":null,"bareheadedPhotoUrl":null,"modifyTime":null,"expireTime":null,"team":null,"maintenanceFlag":0,"orderServices":[],"maintenanceServices":[],"picturesUrl":null,"employerDescribe":null,"slottingStartLength":"month","slottingEndLength":"11","slottingPrice":0,"debugging":1,"debugPrice":0,"materialsStartLength":"","materialsEndLength":"","materialsPrice":0,"orderType":"3"}]
     * hasPreviousPage : false
     * hasNextPage : false
     */

    private int pageNum;
    private int pageSize;
    private int total;
    private int pages;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private List<ListBean> list;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * orderCode : 231936155539587072
         * serviceId : 0
         * serviceText : 巡查任务
         * number : 1
         * orderAmount : 300.0
         * maintenanceAmount : 0.0
         * masterOrderAmount : 0.0
         * orderStatus : 0
         * name : null
         * telephone : null
         * address : 北京市北京市朝阳区11
         * appointmentTime : 2000-01-01 00:00:00
         * confirmationTime : null
         * longitude : 0.0
         * latitude : 0.0
         * publisher : null
         * receivingParty : null
         * userText : null
         * bareheadedPhotoUrl : null
         * modifyTime : 2019-09-28 10:02:05
         * expireTime : null
         * team : null
         * maintenanceFlag : 0
         * orderServices : []
         * maintenanceServices : []
         * picturesUrl : null
         * employerDescribe : null
         * slottingStartLength : month
         * slottingEndLength : 11
         * slottingPrice : 0.0
         * debugging : 8
         * debugPrice : 0.0
         * materialsStartLength :
         * materialsEndLength :
         * materialsPrice : 0.0
         * orderType : 3
         */

        private String orderCode;
        private int serviceId;
        private String serviceText;
        private int number;
        private double orderAmount;
        private double maintenanceAmount;
        private double masterOrderAmount;
        private int orderStatus;
        private String name;
        private String telephone;
        private String address;
        private String appointmentTime;
        private String confirmationTime;
        private double longitude;
        private double latitude;
        private String publisher;
        private String receivingParty;
        private String userText;
        private String bareheadedPhotoUrl;
        private String modifyTime;
        private String expireTime;
        private String team;
        private int maintenanceFlag;
        private String picturesUrl;
        private String employerDescribe;
        private String slottingStartLength;
        private String slottingEndLength;
        private double slottingPrice;
        private int debugging;
        private double debugPrice;
        private String materialsStartLength;
        private String materialsEndLength;
        private double materialsPrice;
        private String orderType;

        public String getOrderCode() {
            return orderCode == null ? "" : orderCode;
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
            return serviceText == null ? "" : serviceText;
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

        public double getMaintenanceAmount() {
            return maintenanceAmount;
        }

        public void setMaintenanceAmount(double maintenanceAmount) {
            this.maintenanceAmount = maintenanceAmount;
        }

        public double getMasterOrderAmount() {
            return masterOrderAmount;
        }

        public void setMasterOrderAmount(double masterOrderAmount) {
            this.masterOrderAmount = masterOrderAmount;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTelephone() {
            return telephone == null ? "" : telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAppointmentTime() {
            return appointmentTime == null ? "" : appointmentTime;
        }

        public void setAppointmentTime(String appointmentTime) {
            this.appointmentTime = appointmentTime;
        }

        public String getConfirmationTime() {
            return confirmationTime == null ? "" : confirmationTime;
        }

        public void setConfirmationTime(String confirmationTime) {
            this.confirmationTime = confirmationTime;
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

        public String getPublisher() {
            return publisher == null ? "" : publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getReceivingParty() {
            return receivingParty == null ? "" : receivingParty;
        }

        public void setReceivingParty(String receivingParty) {
            this.receivingParty = receivingParty;
        }

        public String getUserText() {
            return userText == null ? "" : userText;
        }

        public void setUserText(String userText) {
            this.userText = userText;
        }

        public String getBareheadedPhotoUrl() {
            return bareheadedPhotoUrl == null ? "" : bareheadedPhotoUrl;
        }

        public void setBareheadedPhotoUrl(String bareheadedPhotoUrl) {
            this.bareheadedPhotoUrl = bareheadedPhotoUrl;
        }

        public String getModifyTime() {
            return modifyTime == null ? "" : modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getExpireTime() {
            return expireTime == null ? "" : expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }

        public String getTeam() {
            return team == null ? "" : team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public int getMaintenanceFlag() {
            return maintenanceFlag;
        }

        public void setMaintenanceFlag(int maintenanceFlag) {
            this.maintenanceFlag = maintenanceFlag;
        }

        public String getPicturesUrl() {
            return picturesUrl == null ? "" : picturesUrl;
        }

        public void setPicturesUrl(String picturesUrl) {
            this.picturesUrl = picturesUrl;
        }

        public String getEmployerDescribe() {
            return employerDescribe == null ? "" : employerDescribe;
        }

        public void setEmployerDescribe(String employerDescribe) {
            this.employerDescribe = employerDescribe;
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

        public double getSlottingPrice() {
            return slottingPrice;
        }

        public void setSlottingPrice(double slottingPrice) {
            this.slottingPrice = slottingPrice;
        }

        public int getDebugging() {
            return debugging;
        }

        public void setDebugging(int debugging) {
            this.debugging = debugging;
        }

        public double getDebugPrice() {
            return debugPrice;
        }

        public void setDebugPrice(double debugPrice) {
            this.debugPrice = debugPrice;
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

        public double getMaterialsPrice() {
            return materialsPrice;
        }

        public void setMaterialsPrice(double materialsPrice) {
            this.materialsPrice = materialsPrice;
        }

        public String getOrderType() {
            return orderType == null ? "" : orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }
    }

}
