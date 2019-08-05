package com.xiaomai.zhuangba.data.module.masteremployer;

import com.example.toollib.data.base.IBaseView;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderStatistics;
import com.xiaomai.zhuangba.data.bean.StatisticsData;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 */
public interface IMasterEmployerView extends IBaseView {

    /**
     * 订单首页统计
     *
     * @param orderStatistics 统计信息
     */
    void orderStatisticsSuccess(OrderStatistics orderStatistics);

    /**
     * 分页 页码
     *
     * @return page
     */
    int getPage();

    /**
     * 刷新成功
     *
     * @param ordersLists list
     */
    void refreshSuccess(List<OngoingOrdersList> ordersLists);

    /**
     * 刷新失败
     */
    void refreshError();

    /**
     * 数据不足"15"(不一定一页15条)条 不能加载
     */
    void loadMoreEnd();


    /**
     * 加载成功
     *
     * @param ongoingOrdersLists list
     */
    void loadMoreSuccess(List<OngoingOrdersList> ongoingOrdersLists);

    /**
     * 加载完成
     */
    void loadMoreComplete();


    /**
     * 查询平台的师傅数量，订单数量，订单金额
     * @param statisticsData  data
     */
    void statisticsSuccess(StatisticsData statisticsData);

    /**
     * 1 工作 2 休息
     * @return string
     */
    String getStatus();

    /**
     * 修改成功
     */
    void workingStateSwitchingSuccess();

    /**
     * 订单池查询地址
     * @return string
     */
    String getAddress();
}
