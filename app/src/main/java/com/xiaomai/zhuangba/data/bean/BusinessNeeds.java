package com.xiaomai.zhuangba.data.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bawj
 * CreateDate: 2020/1/2 0002 11:51
 */
public class BusinessNeeds {

    /**标识是否选中，0 未选择  1 已选择*/
    private int flag;

    /**主键*/
    private Integer id;

    /**名称*/
    private String name;

    /**子企业需求*/
    private List<BusinessNeeds> childDemand;


    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BusinessNeeds> getChildDemand() {
        if (childDemand == null) {
            return new ArrayList<>();
        }
        return childDemand;
    }

    public void setChildDemand(List<BusinessNeeds> childDemand) {
        this.childDemand = childDemand;
    }


}
