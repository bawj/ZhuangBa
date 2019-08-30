package com.xiaomai.zhuangba.data.module.masteremployer;

import com.example.toollib.data.IBaseModule;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 */
public interface IMasterEmployerModule extends IBaseModule<IMasterEmployerView> {

    /**
     * 请求师傅 统计
     */
    void requestOrderStatistics();

    /**
     * 新任务
     */
    void requestMasterNewTaskOrderList();


    /**
     * 需处理
     */
    void requestOngoingOrders();


    /**
     * 雇主端订单列表
     */
    void employerOrderList();


    /**
     * 查询平台的师傅数量，订单数量，订单金额
     */
    void requestStatisticsData();


    /**
     * 开工中 和 休息中切换
     */
    void requestWorkingStateSwitching();


    /**
     * 广告单
     */
    void requestAdvertisingBills();
}
