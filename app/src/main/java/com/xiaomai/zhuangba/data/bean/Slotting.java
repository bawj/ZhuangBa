package com.xiaomai.zhuangba.data.bean;

import com.xiaomai.zhuangba.data.bean.db.MaterialsListDB;
import com.xiaomai.zhuangba.data.bean.db.SlottingListDB;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/20 0020
 */
public class Slotting {

    /**
     * slottingList : [{"slottingId":1,"startLength":"0","endLength":"5","slottingPrice":12},{"slottingId":2,"startLength":"5","endLength":"10","slottingPrice":23},{"slottingId":3,"startLength":"15","endLength":"20","slottingPrice":34},{"slottingId":4,"startLength":"20","endLength":"25","slottingPrice":45},{"slottingId":5,"startLength":"25","endLength":"30","slottingPrice":56},{"slottingId":6,"startLength":"1","endLength":"2","slottingPrice":12},{"slottingId":7,"startLength":"1","endLength":"2","slottingPrice":12},{"slottingId":8,"startLength":"3","endLength":"2","slottingPrice":14},{"slottingId":9,"startLength":"1","endLength":"2","slottingPrice":12},{"slottingId":10,"startLength":"3","endLength":"3","slottingPrice":3},{"slottingId":12,"startLength":"1","endLength":"1","slottingPrice":4}]
     * materialsList : [{"slottingId":1,"startLength":"1","endLength":"2","slottingPrice":3},{"slottingId":2,"startLength":"4","endLength":"5","slottingPrice":6},{"slottingId":3,"startLength":"7","endLength":"8","slottingPrice":9},{"slottingId":4,"startLength":"10","endLength":"11","slottingPrice":12},{"slottingId":5,"startLength":"1","endLength":"1","slottingPrice":4},{"slottingId":6,"startLength":"1","endLength":"1","slottingPrice":4},{"slottingId":7,"startLength":"1","endLength":"1","slottingPrice":4},{"slottingId":8,"startLength":"1","endLength":"1","slottingPrice":4},{"slottingId":9,"startLength":"2","endLength":"2","slottingPrice":2}]
     * debugPrice : 11.0
     */

    /**
     * 调试价格
     */
    private double debugPrice;

    /**
     * 开槽长度
     */
    private List<SlottingListDB> slottingList;

    /**
     * 辅材
     */
    private List<MaterialsListDB> materialsList;

    public double getDebugPrice() {
        return debugPrice;
    }

    public void setDebugPrice(double debugPrice) {
        this.debugPrice = debugPrice;
    }

    public List<SlottingListDB> getSlottingList() {
        return slottingList;
    }

    public void setSlottingList(List<SlottingListDB> slottingList) {
        this.slottingList = slottingList;
    }

    public List<MaterialsListDB> getMaterialsList() {
        return materialsList;
    }

    public void setMaterialsList(List<MaterialsListDB> materialsList) {
        this.materialsList = materialsList;
    }
}
