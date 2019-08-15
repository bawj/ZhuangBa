package com.xiaomai.zhuangba.fragment.personal.employer;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.RechargeRecordAdapter;
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
 * 充值记录
 */
public class RechargeRecordFragment extends BaseEmployerWalletDetailFragment {

    public static RechargeRecordFragment newInstance() {
        Bundle args = new Bundle();
        RechargeRecordFragment fragment = new RechargeRecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Observable<HttpResult<EmployerWalletDetailBean>> getObservable() {
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        String phoneNumber = unique.getPhoneNumber();
        String team = unique.getTeam();
        return ServiceUrl.getUserApi().rechargeRecord(phoneNumber, team, String.valueOf(StaticExplain.RECHARGE_RECORD.getCode())
                , getPage(), StaticExplain.PAGE_NUM.getCode());
    }

    @Override
    public BaseQuickAdapter getBaseListAdapter() {
        return new RechargeRecordAdapter();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_recharge_record;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.recharge_record);
    }
}
