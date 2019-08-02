package com.xiaomai.zhuangba.data.bean;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 */
public class OrderServiceDate {
    OngoingOrdersList ongoingOrdersList;
    List<OrderServiceItem> orderServiceItems;
    List<OrderDateList> orderDateLists;
    List<DeliveryContent> deliveryContents;

    public OngoingOrdersList getOngoingOrdersList() {
        return ongoingOrdersList;
    }

    public void setOngoingOrdersList(OngoingOrdersList ongoingOrdersList) {
        this.ongoingOrdersList = ongoingOrdersList;
    }

    public List<OrderServiceItem> getOrderServiceItems() {
        return orderServiceItems;
    }

    public void setOrderServiceItems(List<OrderServiceItem> orderServiceItems) {
        this.orderServiceItems = orderServiceItems;
    }

    public List<OrderDateList> getOrderDateLists() {
        return orderDateLists;
    }

    public void setOrderDateLists(List<OrderDateList> orderDateLists) {
        this.orderDateLists = orderDateLists;
    }

    public List<DeliveryContent> getDeliveryContents() {
        return deliveryContents;
    }

    public void setDeliveryContents(List<DeliveryContent> deliveryContents) {
        this.deliveryContents = deliveryContents;
    }
}
