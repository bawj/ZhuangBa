package com.xiaomai.zhuangba.fragment.employer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.manager.GlideManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OrderStatistics;
import com.xiaomai.zhuangba.data.bean.ServiceData;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.datadetails.DataDetailsFragment;
import com.xiaomai.zhuangba.fragment.SelectServiceFragment;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerContentFragment;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerFragment;
import com.xiaomai.zhuangba.fragment.personal.employer.EmployerPersonalFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.weight.dialog.AuthenticationDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

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
    public int getContentView() {
        return R.layout.fragment_employer;
    }

    @OnClick({R.id.relEmployerRelease, R.id.layEmployerOrder})
    public void onViewEmployerClicked(View view) {
        switch (view.getId()) {
            case R.id.relEmployerRelease:
                RxUtils.getObservable(ServiceUrl.getUserApi().getServiceCategory())
                        .compose(this.<HttpResult<List<ServiceData>>>bindToLifecycle())
                        .subscribe(new BaseHttpRxObserver<List<ServiceData>>(getActivity()) {
                            @Override
                            protected void onSuccess(List<ServiceData> serviceDataList) {
                                serviceCategorySuccess(serviceDataList);
                            }
                        });
                break;
            case R.id.layEmployerOrder:
                startFragment(DataDetailsFragment.newInstance());
                break;
            default:
        }
    }

    public void serviceCategorySuccess(List<ServiceData> serviceDataList) {
        if (serviceDataList != null && !serviceDataList.isEmpty()) {
            ServiceData serviceData = serviceDataList.get(0);
            startFragment(SelectServiceFragment.newInstance(String.valueOf(serviceData.getServiceId()), serviceData.getServiceText()));
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        /*UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        if (unique.getAuthenticationStatue() != StaticExplain.CERTIFIED.getCode()){
            Observable<HttpResult<UserInfo>> userObservable = ServiceUrl.getUserApi().getUser();
            RxUtils.getObservable(userObservable)
                    .compose(this.<HttpResult<UserInfo>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<UserInfo>(getActivity()) {
                        @Override
                        protected void onSuccess(UserInfo userInfo) {
                            DBHelper.getInstance().getUserInfoDao().deleteAll();
                            DBHelper.getInstance().getUserInfoDao().insert(userInfo);
                            showDialog(userInfo);
                        }
                    });
        }*/
    }

    private void showDialog(UserInfo userInfo) {
        AuthenticationDialog instance = AuthenticationDialog.getInstance();
        instance.initView(getActivity());
        if (userInfo.getAuthenticationStatue() == StaticExplain.NO_CERTIFICATION.getCode()) {
            //未认证
            instance.setTvAuthenticationTitle(getString(R.string.need_to_go_for_certification));
            instance.setTvAuthenticationContent(getString(R.string.please_go_to_the_certification_office));
            instance.setICallBase(new AuthenticationDialog.BaseCallback() {
                        @Override
                        public void ok() {
                        }
                    }).showDialog();
        } else if (userInfo.getAuthenticationStatue() == StaticExplain.IN_AUDIT.getCode()) {
            //审核中
        } else if (userInfo.getAuthenticationStatue() == StaticExplain.REJECT_AUDIT.getCode()) {
            //审核不通过
        } else if (userInfo.getAuthenticationStatue() == StaticExplain.SELECTED_ROLES.getCode()) {
            //审核通过
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
