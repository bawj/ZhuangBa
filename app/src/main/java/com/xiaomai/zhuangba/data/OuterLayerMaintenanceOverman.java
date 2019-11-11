package com.xiaomai.zhuangba.data;

import com.xiaomai.zhuangba.data.bean.MaintenanceOverman;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;

/**
 * @author Administrator
 * @date 2019/11/8 0008
 */
public class OuterLayerMaintenanceOverman {

    private double num;
    private double amount;
    private RefreshBaseList<MaintenanceOverman> list;

    public double getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public RefreshBaseList<MaintenanceOverman> getList() {
        return list;
    }

    public void setList(RefreshBaseList<MaintenanceOverman> list) {
        this.list = list;
    }
}
