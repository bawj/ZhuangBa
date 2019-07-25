package com.xiaomai.zhuangba.data;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 */
public class Orders {
    private int pageNum;
    private int pageSize;
    private int total;
    private int pages;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private List<OngoingOrdersList> list;

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

    public List<OngoingOrdersList> getList() {
        return list;
    }

    public void setList(List<OngoingOrdersList> list) {
        this.list = list;
    }
}
