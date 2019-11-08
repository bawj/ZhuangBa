package com.xiaomai.zhuangba.data.bean;

import com.xiaomai.zhuangba.data.AdvertisingBillsBean;

/**
 * @author Administrator
 * @date 2019/11/8 0008
 */
public class OuterLayerAdvertisingBills {

    private int num;
    private RefreshBaseList<AdvertisingBillsBean> list;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public RefreshBaseList<AdvertisingBillsBean> getList() {
        return list;
    }

    public void setList(RefreshBaseList<AdvertisingBillsBean> list) {
        this.list = list;
    }
}
