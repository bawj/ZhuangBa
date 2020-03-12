package com.xiaomai.zhuangba.fragment.personal.feedback;

import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.data.DryRunOrder;

/**
 * @author Bawj
 * CreateDate:     2020/3/11 0011 14:19
 */
public interface IOrderFeedBackModule extends IBaseModule<IOrderFeedBackView> {

    /**
     * 查询反馈
     */
    void requestSelectAirRun();

    /**
     * 空跑申请通过
     */
    void dryRunOrderAdopt();

    /**
     * 空跑申请不通过
     * @param id id
     */
    void dryRunOrderApplicationFailed(int id);

    /**
     * 支付
     * @param id       id
     * @param payType type
     * @param password 密码
     * @param playStatus 状态
     * @param dryRunOrder date
     */
    void requestPay(int id,String payType, String password , String playStatus , DryRunOrder dryRunOrder);

    /**
     * 现场补充 自定义 不通过
     * @param dryRunOrder date
     */
    void customApplicationFailed(DryRunOrder dryRunOrder);

    /**
     * 现场补充 自定义 通过
     * @param dryRunOrder date
     */
    void customForApproval(DryRunOrder dryRunOrder);

    /**
     * 查询 新增项目
     * @param orderCode 订单编号
     */
    void requestIncreaseDecrease(String orderCode);

    /**
     * 雇主不通过增减项申请
     * @param dryRunOrder date
     */
    void failIncreaseDecrease(DryRunOrder dryRunOrder);

    /**
     * 新增项目 通过
     * @param dryRunOrder date
     */
    void customFailIncreaseDecrease(DryRunOrder dryRunOrder);
}
