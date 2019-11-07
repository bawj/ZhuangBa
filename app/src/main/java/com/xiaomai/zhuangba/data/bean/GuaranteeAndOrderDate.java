package com.xiaomai.zhuangba.data.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2019/11/7 0007
 */
public class GuaranteeAndOrderDate {

    private GuaranteeDeatil guaranteeDeatil;
    private List<OrderDateList> orderDateListList;

    public GuaranteeDeatil getGuaranteeDeatil() {
        return guaranteeDeatil;
    }

    public void setGuaranteeDeatil(GuaranteeDeatil guaranteeDeatil) {
        this.guaranteeDeatil = guaranteeDeatil;
    }

    public List<OrderDateList> getOrderDateListList() {
        if (orderDateListList == null) {
            return new ArrayList<>();
        }
        return orderDateListList;
    }

    public void setOrderDateListList(List<OrderDateList> orderDateListList) {
        this.orderDateListList = orderDateListList;
    }
}
