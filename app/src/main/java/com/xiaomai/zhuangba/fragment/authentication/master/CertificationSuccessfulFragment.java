package com.xiaomai.zhuangba.fragment.authentication.master;

import android.os.Bundle;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.authentication.employer.EmployerAuthenticationFragment;
import com.xiaomai.zhuangba.fragment.employer.EmployerFragment;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;

import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/2 0002
 */
public class CertificationSuccessfulFragment extends BaseFragment {

    public static CertificationSuccessfulFragment newInstance() {
        Bundle args = new Bundle();
        CertificationSuccessfulFragment fragment = new CertificationSuccessfulFragment();
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

    @OnClick(R.id.btnCompleteMissionDetails)
    public void onViewClicked() {
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        String role = unique.getRole();
        if (role.equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))){
            startFragment(MasterWorkerFragment.newInstance());
        }else if (role.equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))){
            startFragment(EmployerFragment.newInstance());
        }
    }

    @Override
    public boolean isBackArrow() {
        return false;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_certification_successful;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.certification_successful);
    }
}
