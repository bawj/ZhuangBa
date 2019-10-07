package com.xiaomai.zhuangba.fragment.orderdetail.employer.patrol.base;

import com.example.toollib.data.IBaseModule;

/**
 * @author Administrator
 * @date 2019/10/7 0007
 */
public interface IBasePatrolModule<V extends IBasePatrolView> extends IBaseModule<V> {

    /**
     * 请求巡查详情
     */
    void requestPatrolDetail();

}
