package com.xiaomai.zhuangba.data.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/27 0027
 * 钱包明细
 */
public class WalletDetailBean {

    /**
     * pageNum : 1
     * pageSize : 1
     * total : 4
     * pages : 4
     * list : [{"orderCode":"201905161557987826","orderAmount":700,"masterOrderAmount":0,"modifyTime":"2019-03-01T05:24:54.000+0000","withdrawalsAccount":"12345678910","streamType":1,"wallerType":2}]
     * hasPreviousPage : false
     * hasNextPage : true
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

    public static class ListBean implements Serializable{
        /**
         * orderCode : 201905161557987826
         * orderAmount : 700
         * masterOrderAmount : 0
         * modifyTime : 2019-03-01T05:24:54.000+0000
         * withdrawalsAccount : 12345678910
         * streamType : 1
         * wallerType : 2
         */

        private String orderCode;
        private double orderAmount;
        private double masterOrderAmount;
        private String modifyTime;
        private String withdrawalsAccount;
        private int streamType;
        private int wallerType;

        /**
         * 提现
         */
        private double amount;
        private String accountNumber;
        private String times;


        private String phoneNumber;
        private String externalAccountNumber;
        private String team;

        public String getOrderCode() {
            return TextUtils.isEmpty(orderCode) ? "" : orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public double getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(double orderAmount) {
            this.orderAmount = orderAmount;
        }

        public double getMasterOrderAmount() {
            return masterOrderAmount;
        }

        public void setMasterOrderAmount(double masterOrderAmount) {
            this.masterOrderAmount = masterOrderAmount;
        }

        public String getModifyTime() {
            return TextUtils.isEmpty(modifyTime) ? "" : modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getWithdrawalsAccount() {
            return TextUtils.isEmpty(withdrawalsAccount) ? "" : withdrawalsAccount;
        }

        public void setWithdrawalsAccount(String withdrawalsAccount) {
            this.withdrawalsAccount = withdrawalsAccount;
        }

        public int getStreamType() {
            return streamType;
        }

        public void setStreamType(int streamType) {
            this.streamType = streamType;
        }

        public int getWallerType() {
            return wallerType;
        }

        public void setWallerType(int wallerType) {
            this.wallerType = wallerType;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getAccountNumber() {
            return TextUtils.isEmpty(accountNumber) ? "" : accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getTimes() {
            return TextUtils.isEmpty(times) ? "" : times;
        }

        public String getExternalAccountNumber() {
            return externalAccountNumber;
        }

        public void setExternalAccountNumber(String externalAccountNumber) {
            this.externalAccountNumber = externalAccountNumber;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }


        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }
    }
}
