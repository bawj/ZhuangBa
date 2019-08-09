package com.xiaomai.zhuangba.data.bean;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/8 0008
 */
public class MaintenanceBean {

    /**
     * total : 4
     * list : [{"id":null,"serviceId":null,"orderCode":"212439943627259904","number":3,"amount":1000,"residualQuantity":null,"residualAmount":null,"overmanName":null,"overmanPhone":null,"serviceName":"咳咳咳可","startTime":"2019-08-06","endTime":"2019-08-30","startDate":null,"endDate":null,"status":"0","serviceImg":"https://zb.4000750222.com//zbimages/test/2019/7/24/2c90ef856c21ec0e016c221ed8b00002.png"},{"id":null,"serviceId":null,"orderCode":"212439943627259904","number":2,"amount":500,"residualQuantity":null,"residualAmount":null,"overmanName":null,"overmanPhone":null,"serviceName":"咳咳咳可","startTime":"2019-08-06","endTime":"2019-08-30","startDate":null,"endDate":null,"status":"0","serviceImg":"https://zb.4000750222.com//zbimages/test/2019/7/24/2c90ef856c21ec0e016c221ed8b00002.png"},{"id":null,"serviceId":null,"orderCode":"212439943627259904","number":10,"amount":10254,"residualQuantity":null,"residualAmount":null,"overmanName":null,"overmanPhone":null,"serviceName":"1234567890123456789012s@是","startTime":"2019-08-06","endTime":"2019-08-30","startDate":null,"endDate":null,"status":"0","serviceImg":null},{"id":null,"serviceId":null,"orderCode":"212439943627259904","number":1,"amount":100,"residualQuantity":null,"residualAmount":null,"overmanName":null,"overmanPhone":null,"serviceName":"1234567890123456789012s@是","startTime":"2019-08-06","endTime":"2019-08-30","startDate":null,"endDate":null,"status":"0","serviceImg":null}]
     * pageNum : 1
     * pageSize : 20
     * size : 4
     * startRow : 1
     * endRow : 4
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
    private List<MaintenancePolicyBean> list;
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

    public List<MaintenancePolicyBean> getList() {
        return list;
    }

    public void setList(List<MaintenancePolicyBean> list) {
        this.list = list;
    }

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

}
