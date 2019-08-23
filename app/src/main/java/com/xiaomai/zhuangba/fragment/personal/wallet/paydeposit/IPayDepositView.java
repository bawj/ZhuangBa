package com.xiaomai.zhuangba.fragment.personal.wallet.paydeposit;

import com.example.toollib.data.base.IBaseView;
import com.xiaomai.zhuangba.data.bean.PayDepositBean;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 */
public interface IPayDepositView extends IBaseView {

    /**
     *  缴纳保证金
     * @return 金额
     */
    String getMoney();


    /**
     * 支付宝支付
     * @return boolean
     */
    boolean chkPaymentPlayIsChecked();

    /**
     * 微信支付
     * @return boolean
     */
    boolean chkPaymentWeChatIsChecked();


    /**
     *  支付成功
     */
    void playSuccess();


    /**
     * 保证金
     * @param depositBeanList 保证金
     */
    void requestPayDeposit(List<PayDepositBean> depositBeanList);
}
