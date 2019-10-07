package com.xiaomai.zhuangba.fragment.orderdetail.employer.patrol.base;

import com.example.toollib.data.base.IBaseView;
import com.xiaomai.zhuangba.data.bean.OrderServiceDate;

/**
 * @author Administrator
 * @date 2019/10/7 0007
 */
public interface IBasePatrolView extends IBaseView{

    /**
     * 订单编号
     * @return string
     */
    String getOrderCode();

    /**
     * 订单状态
     * @return string
     */
    String getOrderType();


    /**
     * 查询成功
     * @param orderServiceDate 巡查任务详情
     */
    void requestOrderDetailSuccess(OrderServiceDate orderServiceDate);

    /**
     * 停止刷新
     */
    void finishRefresh();
}
