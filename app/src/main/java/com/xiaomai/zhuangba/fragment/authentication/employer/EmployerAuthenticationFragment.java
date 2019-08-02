package com.xiaomai.zhuangba.fragment.authentication.employer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.toollib.util.ToastUtil;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.authentication.AuthenticationFragment;
import com.xiaomai.zhuangba.fragment.employer.EmployerFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 */
public class EmployerAuthenticationFragment extends AuthenticationFragment {

    @BindView(R.id.tvAuthenticationMsg)
    TextView tvAuthenticationMsg;
    @BindView(R.id.tvAuthenticationTip)
    TextView tvAuthenticationTip;
    @BindView(R.id.tvAuditInProgress)
    TextView tvAuditInProgress;
    @BindView(R.id.layAuthentication)
    LinearLayout layAuthentication;
    @BindView(R.id.ivAuthenticationExplain)
    ImageView ivAuthenticationExplain;
    @BindView(R.id.btnGoAuthentication)
    QMUIButton btnGoAuthentication;

    public static EmployerAuthenticationFragment newInstance() {
        Bundle args = new Bundle();
        EmployerAuthenticationFragment fragment = new EmployerAuthenticationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_authentication;
    }

    @Override
    public void initView() {
        super.initView();
        UserInfo userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        //认证状态
        int authenticationStatue = userInfo.getAuthenticationStatue();
        if (authenticationStatue == StaticExplain.NO_CERTIFICATION.getCode()
                || authenticationStatue == StaticExplain.REJECT_AUDIT.getCode()) {
            //未认证 或者 认证不通过
            initData(R.drawable.bg_employer_authentication, getString(R.string.immediate_identity_authentication),
                    getString(R.string.eight_employers));
            tvAuditInProgress.setVisibility(View.GONE);
            btnGoAuthentication.setVisibility(View.VISIBLE);
        } else if (authenticationStatue == StaticExplain.IN_AUDIT.getCode()) {
            //审核中
            initData(R.drawable.bg_in_audit, getString(R.string.accelerated_audits_are_under_way),
                    getString(R.string.findings_of_audit));
            btnGoAuthentication.setVisibility(View.GONE);
            tvAuditInProgress.setVisibility(View.VISIBLE);
            //查询是否认证通过
            findAuthentication();
        }
    }

    private void initData(int bg, String msg, String tip) {
        layAuthentication.setBackgroundResource(bg);
        tvAuthenticationMsg.setText(msg);
        tvAuthenticationTip.setText(tip);
        ivAuthenticationExplain.setBackgroundResource(R.drawable.bg_employer_authentication_process);
    }

    @OnClick({R.id.btnGoAuthentication})
    public void btnEmployerAuthentication(View view) {
        switch (view.getId()) {
            case R.id.btnGoAuthentication:
                //去认证
                startFragment(BusinessLicenseFragment.newInstance());
                break;
            default:
        }
    }

    @Override
    public void findAuthenticationSuccess(UserInfo userInfo) {
        int authenticationStatue = userInfo.getAuthenticationStatue();
        if (authenticationStatue == StaticExplain.CERTIFIED.getCode()) {
            //认证成功
            startFragment(EmployerFragment.newInstance());
        } else if (authenticationStatue == StaticExplain.REJECT_AUDIT.getCode()) {
            //认证审核不通过
            ToastUtil.showShort(getString(R.string.audit_failed));
            initView();
        }
    }


}
