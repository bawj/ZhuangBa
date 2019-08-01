package com.xiaomai.zhuangba.fragment.personal.wallet.paydeposit;

import com.example.toollib.data.base.IBaseView;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 */
public interface IPayDepositView extends IBaseView {

    /**
     * 实习师傅缴纳的保证金
     * @param s 保证金
     */
    void setTvPayDepositMoney(String s);

    /**
     *  实习师傅 名称
     * @param masterRankName 实习师傅
     */
    void setTvDepositTitle(String masterRankName);


    /**
     * 实习师傅
     * @param explain 实习师傅说明
     */
    void setTvPayDepositMsg(String explain);

    /**
     * 正式师傅缴纳的保证金
     * @param s 保证金
     */
    void setTvPayDepositMoneyTwo(String s);

    /**
     *  名称
     * @param masterRankName 正式师傅
     */
    void setTvDepositTitleTwo(String masterRankName);


    /**
     * 说明
     * @param explain 说明
     */
    void setTvPayDepositMsgTwo(String explain);

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
}
