package com.xiaomai.zhuangba.fragment.personal.wallet.paydeposit;

import com.example.toollib.data.IBaseModule;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 */
public interface IPayDepositModule extends IBaseModule<IPayDepositView> {

    /**
     * 请求保证金
     */
    void requestPayDeposit();

    /**
     * 缴纳保证金
     */
    void payDeposit();
}
