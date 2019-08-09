package com.xiaomai.zhuangba.fragment.personal.employer;

import android.os.Bundle;

import com.example.toollib.http.HttpResult;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.EmployerWalletDetailBean;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.personal.employer.base.BaseEmployerWalletDetailFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/8/9 0009
 *
 * 消费记录
 */
public class RecordsOfConsumptionFragment extends BaseEmployerWalletDetailFragment {

    public static RecordsOfConsumptionFragment newInstance() {
        Bundle args = new Bundle();
        RecordsOfConsumptionFragment fragment = new RecordsOfConsumptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Observable<HttpResult<EmployerWalletDetailBean>> getObservable() {
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        String phoneNumber = unique.getPhoneNumber();
        String team = unique.getTeam();
        return ServiceUrl.getUserApi().rechargeRecord(phoneNumber, team, String.valueOf(StaticExplain.RECORDS_OF_CONSUMPTION.getCode())
                , getPage(), StaticExplain.PAGE_NUM.getCode());
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.records_of_consumption);
    }
}