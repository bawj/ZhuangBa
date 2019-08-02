package com.xiaomai.zhuangba.fragment.personal.wallet.paydeposit;

import com.example.toollib.data.BaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.data.bean.PayData;
import com.xiaomai.zhuangba.data.bean.PayDepositBean;
import com.xiaomai.zhuangba.data.bean.PlayModule;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.data.module.IPlayModule;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 */
public class PayDepositModule extends BaseModule<IPayDepositView> implements IPayDepositModule {


    @Override
    public void requestPayDeposit() {
        final UserInfo userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        String flag = "";
        if (userInfo.getMasterRankId().equals(String.valueOf(StaticExplain.OBSERVER.getCode()))) {
            flag = "0";
        }
        if (userInfo.getMasterRankId().equals(String.valueOf(StaticExplain.INTERNSHIP.getCode()))) {
            flag = "1";
        }
        RxUtils.getObservable(ServiceUrl.getUserApi().getBond(flag))
                .compose(mViewRef.get().<HttpResult<List<PayDepositBean>>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<List<PayDepositBean>>(mContext.get()) {
                    @Override
                    protected void onSuccess(List<PayDepositBean> depositBeanList) {
                        if (depositBeanList != null && !depositBeanList.isEmpty()) {
                            if (userInfo.getMasterRankId().equals(String.valueOf(StaticExplain.INTERNSHIP.getCode()))) {
                                PayDepositBean payDepositBean1 = depositBeanList.get(0);
                                mViewRef.get().setTvPayDepositMoneyTwo(String.valueOf(payDepositBean1.getBond()));
                                mViewRef.get().setTvDepositTitleTwo(payDepositBean1.getMasterRankName());
                                mViewRef.get().setTvPayDepositMsgTwo(payDepositBean1.getExplain());
                            } else {
                                PayDepositBean payDepositBean = depositBeanList.get(0);
                                mViewRef.get().setTvPayDepositMoney(String.valueOf(payDepositBean.getBond()));
                                mViewRef.get().setTvDepositTitle(payDepositBean.getMasterRankName());
                                mViewRef.get().setTvPayDepositMsg(payDepositBean.getExplain());
                                if (depositBeanList.size() > 1) {
                                    PayDepositBean payDepositBean1 = depositBeanList.get(1);
                                    mViewRef.get().setTvPayDepositMoneyTwo(String.valueOf(payDepositBean1.getBond()));
                                    mViewRef.get().setTvDepositTitleTwo(payDepositBean1.getMasterRankName());
                                    mViewRef.get().setTvPayDepositMsgTwo(payDepositBean1.getExplain());
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public void payDeposit() {
        String money = mViewRef.get().getMoney();
        boolean chkPaymentPlayIsChecked = mViewRef.get().chkPaymentPlayIsChecked();
        boolean chkPaymentWeChatIsChecked = mViewRef.get().chkPaymentWeChatIsChecked();
        final IPlayModule iPlayModule = new PlayModule();
        if (chkPaymentPlayIsChecked){
            //支付宝支付
            RxUtils.getObservable(ServiceUrl.getUserApi().payDeposit(money, StringTypeExplain.A_ALIPAY_PAYMENT.getCode()))
                    .compose(mViewRef.get().<HttpResult<PayData>>bindLifecycle())
                    .subscribe(new BaseHttpRxObserver<PayData>() {
                        @Override
                        protected void onSuccess(PayData payData) {
                            iPlayModule.aplipayOrderPayment(mContext.get(), payData, new BaseCallback() {
                                @Override
                                public void onSuccess(Object obj) {
                                    mViewRef.get().playSuccess();
                                }
                            });
                        }
                    });
        }else if (chkPaymentWeChatIsChecked){
            //微信支付
            RxUtils.getObservable(ServiceUrl.getUserApi().payDeposit(money, StringTypeExplain.WE_CHAT_PAYMENT.getCode()))
                    .compose(mViewRef.get().<HttpResult<PayData>>bindLifecycle())
                    .subscribe(new BaseHttpRxObserver<PayData>() {
                        @Override
                        protected void onSuccess(PayData response) {
                            iPlayModule.weChatOrderPayment(mContext.get() , response);
                        }
                    });
        }

    }
}
