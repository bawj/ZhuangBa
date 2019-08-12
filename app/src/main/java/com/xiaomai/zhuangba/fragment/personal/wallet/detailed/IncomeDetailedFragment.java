package com.xiaomai.zhuangba.fragment.personal.wallet.detailed;

import android.os.Bundle;

import com.example.toollib.http.HttpResult;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.WalletDetailBean;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.enums.WalletOrderTypeEnum;
import com.xiaomai.zhuangba.http.ServiceUrl;

import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 *
 * 收入详情
 */
public class IncomeDetailedFragment extends BaseWalletDetailedFragment{

    public static IncomeDetailedFragment newInstance() {
        Bundle args = new Bundle();
        IncomeDetailedFragment fragment = new IncomeDetailedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Observable<HttpResult<WalletDetailBean>> getObservable() {
        return ServiceUrl.getUserApi().getIncomeList(getPage() , StaticExplain.PAGE_NUM.getCode() , startDate , endDate);
    }

    @Override
    public int getType() {
        return WalletOrderTypeEnum.DETAIL_INCOME.getCode();
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.wallet_detail_income);
    }
}
