package com.xiaomai.zhuangba.fragment.employer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OrderStatistics;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.authentication.employer.BusinessLicenseFragment;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerContentFragment;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerFragment;
import com.xiaomai.zhuangba.fragment.datadetails.DataDetailsFragment;
import com.xiaomai.zhuangba.fragment.personal.employer.EmployerPersonalFragment;
import com.xiaomai.zhuangba.fragment.service.SubmitOrderInformationFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.weight.dialog.AuthenticationDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 * <p>
 * 雇主端
 */
public class EmployerFragment extends BaseMasterEmployerFragment {

    @BindView(R.id.tvUnallocatedOrders)
    TextView tvUnallocatedOrders;
    @BindView(R.id.tvPendingOrders)
    TextView tvPendingOrders;

    public static EmployerFragment newInstance() {
        Bundle args = new Bundle();
        EmployerFragment fragment = new EmployerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        statusBarWhite();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer;
    }

    @OnClick({R.id.relEmployerRelease, R.id.layEmployerOrder})
    public void onViewEmployerClicked(View view) {
        switch (view.getId()) {
            case R.id.relEmployerRelease:
                //未认证 弹窗
                UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
                int authenticationStatue = unique.getAuthenticationStatue();
                if (StaticExplain.CERTIFIED.getCode() != authenticationStatue) {
                    //查询是否认证
                    findIsAuthentication();
                }else {
                    startFragment(SubmitOrderInformationFragment.newInstance());
                }
                break;
            case R.id.layEmployerOrder:
                startFragment(DataDetailsFragment.newInstance());
                break;
            default:
        }
    }

    private void findIsAuthentication() {
        RxUtils.getObservable(ServiceUrl.getUserApi().verifyAccountNumber())
                .compose(this.<HttpResult<String>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<String>(getActivity()) {
                    @Override
                    protected void onSuccess(String status) {
                        serviceCategorySuccess(status);
                    }
                });
    }

    public void serviceCategorySuccess(String msg) {
        if (!msg.equals(String.valueOf(StaticExplain.CERTIFIED.getCode()))) {
            showDialog(msg);
        } else {
            startFragment(SubmitOrderInformationFragment.newInstance());
        }
    }

    private void showDialog(String msg) {
        //0 || 4 || 3 去认证   2审核中  5认证已通过
        AuthenticationDialog instance = AuthenticationDialog.getInstance();
        instance.initView(getActivity());
        if (DensityUtils.stringTypeInteger(msg) == StaticExplain.NO_CERTIFICATION.getCode()
                || DensityUtils.stringTypeInteger(msg) == StaticExplain.SELECTED_ROLES.getCode()
                || DensityUtils.stringTypeInteger(msg) == StaticExplain.REJECT_AUDIT.getCode()) {
            //未认证
            instance.setTvAuthenticationTitle(getString(R.string.need_to_go_for_certification))
                    .setTvAuthenticationContent(getString(R.string.please_go_to_the_certification_office))
                    .setIvAuthenticationLog(R.drawable.ic_shape_log)
                    .setICallBase(new AuthenticationDialog.BaseCallback() {
                        @Override
                        public void ok() {
                            startFragment(BusinessLicenseFragment.newInstance());
                        }
                    }).showDialog();
        } else if (DensityUtils.stringTypeInteger(msg) == StaticExplain.IN_AUDIT.getCode()) {
            //审核中
            instance.setTvAuthenticationTitle(getString(R.string.audit_in_progress))
                    .setTvAuthenticationContent(getString(R.string.in_the_process_of_auditing_tip))
                    .setIvAuthenticationLog(R.drawable.ic_shape_log)
                    .setTvDialogVersionOk(getString(R.string.ok))
                    .isTvDialogVersionClose()
                    .showDialog();
        } else if (DensityUtils.stringTypeInteger(msg) == StaticExplain.ONE_SELECTED_ROLES.getCode()) {
            //审核通过 第一次进入
            instance.setTvAuthenticationTitle(getString(R.string.certification_has_been_passed))
                    .setTvAuthenticationContent(getString(R.string.certification_has_been_passed_tip))
                    .setIvAuthenticationLog(R.drawable.ic_green_complete)
                    .setTvDialogVersionOk(getString(R.string.go_to))
                    .isTvDialogVersionClose()
                    .setICallBase(new AuthenticationDialog.BaseCallback() {
                        @Override
                        public void ok() {
                            startFragment(SubmitOrderInformationFragment.newInstance());
                        }
                    }).showDialog();
        }
    }

    @Override
    public void orderStatisticsSuccess(OrderStatistics orderStatistics) {
        if (orderStatistics != null) {
            //未分配
            tvUnallocatedOrders.setText(String.valueOf(orderStatistics.getDistribution()));
            //待处理
            tvPendingOrders.setText(String.valueOf(orderStatistics.getPendingDisposal()));
        }
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == ForResultCode.RESULT_OK.getCode()){
            if (resultCode == ForResultCode.START_FOR_RESULT_CODE.getCode()){
                refreshRefreshFragment(0);
            }
        }
    }

    @Override
    public void startPersonal() {
        startFragment(EmployerPersonalFragment.newInstance());
    }

    @Override
    public List<BaseMasterEmployerContentFragment> getListFragment() {
        List<BaseMasterEmployerContentFragment> fragmentList = new ArrayList<>();
        fragmentList.add(OngoingOrdersFragment.newInstance());
        return fragmentList;
    }

    @Override
    public String[] getTabTitle() {
        return new String[]{getString(R.string.ongoing_orders)};
    }

}
