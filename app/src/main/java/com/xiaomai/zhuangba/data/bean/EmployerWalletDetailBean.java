package com.xiaomai.zhuangba.data.bean;

import android.text.TextUtils;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/9 0009
 */
public class EmployerWalletDetailBean {

    /**
     * total : 3
     * list : [{"phoneNumber":"13757188697","amount":0.03,"streamType":1,"wallerType":8,"externalAccountNumber":null,"accountNumber":"213475652494712832","withdrawalsAccount":null,"times":"2019-08-08 11:26:46","team":"2"},{"phoneNumber":"13757188697","amount":0.03,"streamType":1,"wallerType":8,"externalAccountNumber":null,"accountNumber":"213474806126108672","withdrawalsAccount":null,"times":"2019-08-08 11:23:29","team":"2"},{"phoneNumber":"13757188697","amount":0.05,"streamType":1,"wallerType":8,"externalAccountNumber":null,"accountNumber":"213461282851172352","withdrawalsAccount":null,"times":"2019-08-08 10:30:29","team":"2"}]
     * pageNum : 1
     * pageSize : 3
     * size : 3
     * startRow : 0
     * endRow : 2
     * pages : 1
     * prePage : 0
     * nextPage : 0
     * isFirstPage : true
     * isLastPage : true
     * hasPreviousPage : false
     * hasNextPage : false
     * navigatePages : 8
     * navigatepageNums : [1]
     * navigateFirstPage : 1
     * navigateLastPage : 1
     */

    private int total;
    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private int pages;
    private int prePage;
    private int nextPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int navigatePages;
    private int navigateFirstPage;
    private int navigateLastPage;
    private List<ListBean> list;
    private List<Integer> navigatepageNums;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
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

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public void setNavigateFirstPage(int navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public int getNavigateLastPage() {
        return navigateLastPage;
    }

    public void setNavigateLastPage(int navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public static class ListBean {
        /**
         * phoneNumber : 13757188697
         * amount : 0.03
         * streamType : 1
         * wallerType : 8
         * externalAccountNumber : null
         * accountNumber : 213475652494712832
         * withdrawalsAccount : null
         * times : 2019-08-08 11:26:46
         * team : 2
         */

        private String phoneNumber;
        private double amount;
        private int streamType;
        private int wallerType;
        private Object externalAccountNumber;
        private String accountNumber;
        private Object withdrawalsAccount;
        private String times;
        private String team;

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
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

        public Object getExternalAccountNumber() {
            return externalAccountNumber;
        }

        public void setExternalAccountNumber(Object externalAccountNumber) {
            this.externalAccountNumber = externalAccountNumber;
        }

        public String getAccountNumber() {
            return TextUtils.isEmpty(accountNumber) ? "" : accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public Object getWithdrawalsAccount() {
            return withdrawalsAccount;
        }

        public void setWithdrawalsAccount(Object withdrawalsAccount) {
            this.withdrawalsAccount = withdrawalsAccount;
        }

        public String getTimes() {
            return TextUtils.isEmpty(times) ? "" : times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }
    }
}
