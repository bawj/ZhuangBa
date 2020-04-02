package com.xiaomai.zhuangba.fragment.personal.wallet.detailed;

import android.os.Bundle;

import com.example.toollib.http.HttpResult;
import com.example.toollib.util.Log;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.WalletDetailBean;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.enums.WalletOrderTypeEnum;
import com.xiaomai.zhuangba.http.ServiceUrl;

import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/7/27 0027
 *
 * 明细
 */
public class WalletDetailFragment extends BaseWalletDetailedFragment{

    public static WalletDetailFragment newInstance() {
        Bundle args = new Bundle();
        WalletDetailFragment fragment = new WalletDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Observable<HttpResult<WalletDetailBean>> getObservable() {
        int page = getPage();
        Log.e("page = " + page);
        return ServiceUrl.getUserApi().getWalletDetail(getPage() , StaticExplain.PAGE_NUM.getCode() , startDate , endDate);
    }

    @Override
    public int getType() {
        return WalletOrderTypeEnum.DETAIL_ALL.getCode();
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.wallet_detail);
    }
}
