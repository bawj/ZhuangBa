package com.xiaomai.zhuangba.fragment.personal.employer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.authentication.employer.BusinessLicenseFragment;
import com.xiaomai.zhuangba.fragment.personal.PersonalFragment;
import com.xiaomai.zhuangba.fragment.personal.agreement.WebViewFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/19 0019
 *
 * 雇主端个人中心
 */
public class EmployerPersonalFragment extends PersonalFragment {

    @BindView(R.id.ivUserHead)
    ImageView ivUserHead;
    @BindView(R.id.tvPersonalName)
    TextView tvPersonalName;
    @BindView(R.id.relWallet)
    RelativeLayout relWallet;

    public static EmployerPersonalFragment newInstance() {
        Bundle args = new Bundle();
        EmployerPersonalFragment fragment = new EmployerPersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        int roleId = unique.getRoleId();
        if (roleId == StaticExplain.SUPER_ADMINISTRATOR.getCode()){
            //超级管理员 显示 钱包
            relWallet.setVisibility(View.VISIBLE);
        }else if (roleId == StaticExplain.ADMINISTRATOR.getCode()){
            //管理员  显示钱包
            relWallet.setVisibility(View.VISIBLE);
            //显示钱包
        }else if (roleId == StaticExplain.ORDINARY_STAFF.getCode()){
            //普通员工 不显示 钱包
            relWallet.setVisibility(View.GONE);
        }

        //未认证 隐藏 钱包
        int authenticationStatue = unique.getAuthenticationStatue();
        if (authenticationStatue != StaticExplain.CERTIFIED.getCode()){
            relWallet.setVisibility(View.GONE);
        }

        UserInfo userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        if (userInfo != null){
            tvPersonalName.setText(TextUtils.isEmpty(userInfo.getUserText()) ? getString(R.string.no_certification) : userInfo.getUserText());
            GlideManager.loadCircleImage(getActivity(),userInfo.getBareHeadedPhotoUrl(), ivUserHead , R.drawable.ic_employer_head);
        }
    }

    @Override
    public void startServiceAgreement() {
        startFragment(WebViewFragment.newInstance(ConstantUtil.EMPLOYER_FABRICATING_USER_SERVICE_PROTOCOL,
                getString(R.string.fabricating_user_service_protocol)));
    }

    @OnClick({R.id.relWallet ,R.id.relEmployerMaintenance,R.id.relEmployerAdvertisingReplacement,
            R.id.relEnterpriseCertification , R.id.relEmployerPatrol})
    public void onViewEmployerClicked(View view) {
        switch (view.getId()) {
            case R.id.relWallet:
                //钱包
                startFragment(EmployerWalletFragment.newInstance());
                break;
            case R.id.relEmployerMaintenance:
                //我的维保单
                startFragment(MaintenancePolicyFragment.newInstance());
                break;
            case R.id.relEmployerAdvertisingReplacement:
                //广告更换
                startFragment(EmployerAdvertisingReplacementFragment.newInstance());
                break;
            case R.id.relEnterpriseCertification:
                //企业认证
                startFragment(BusinessLicenseFragment.newInstance());
                break;
            case R.id.relEmployerPatrol:
                //巡查
                startFragment(EmployerPatrolFragment.newInstance());
            break;
            default:
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_personal;
    }

}
