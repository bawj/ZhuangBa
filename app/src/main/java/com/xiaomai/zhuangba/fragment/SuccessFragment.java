package com.xiaomai.zhuangba.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;
import com.xiaomai.zhuangba.fragment.personal.wallet.detailed.WalletDetailFragment;

import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/31 0031
 */
public class SuccessFragment extends BaseFragment {

    public static SuccessFragment newInstance() {
        Bundle args = new Bundle();
        SuccessFragment fragment = new SuccessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.btnCompleteMissionDetails, R.id.btnCompleteBackHome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCompleteMissionDetails:
                startFragment(WalletDetailFragment.newInstance());
                break;
            case R.id.btnCompleteBackHome:
                startFragmentAndDestroyCurrent(MasterWorkerFragment.newInstance());
                break;
            default:
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_success;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.apply_success);
    }
}
