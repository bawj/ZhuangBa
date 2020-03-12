package com.xiaomai.zhuangba.fragment.personal.feedback;

import com.xiaomai.zhuangba.data.DryRunOrder;
import com.xiaomai.zhuangba.data.bean.EmployerAddProjectData;
import com.xiaomai.zhuangba.data.bean.OrderServiceCondition;
import com.xiaomai.zhuangba.data.module.base.IBaseListView;

import java.util.List;

/**
 * @author Bawj
 * CreateDate:     2020/3/11 0011 14:19
 */
public interface IOrderFeedBackView extends IBaseListView {


    /**
     * 刷新成功
     * @param dryRunOrderList data
     */
    void setNewData(List<DryRunOrder> dryRunOrderList);

    /**
     * 加载完成
     * @param dryRunOrderList data
     */
    void addData(List<DryRunOrder> dryRunOrderList);


    /**
     * 空跑申请通过
     * @param flag 是否显示月结
     * @param playStatus 状态
     */
    void dryRunOrderAdoptSuccess(boolean flag , String playStatus);

    /**
     * 空跑申请不通过
     */
    void viewRefresh();

    /**
     * 查询新增项目
     * @param employerAddProjectData date
     * @param orderServiceList date
     */
    void increaseDecreaseSuccess(EmployerAddProjectData employerAddProjectData,List<OrderServiceCondition> orderServiceList);
}
