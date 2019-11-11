package com.xiaomai.zhuangba.fragment.personal.master;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpZipRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.manager.GlideManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.CreateTeamBean;
import com.xiaomai.zhuangba.data.bean.MasterPersonalZip;
import com.xiaomai.zhuangba.data.bean.OrderStatistics;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.authentication.master.IDCardScanningFragment;
import com.xiaomai.zhuangba.fragment.personal.PersonalFragment;
import com.xiaomai.zhuangba.fragment.personal.agreement.WebViewFragment;
import com.xiaomai.zhuangba.fragment.personal.master.patrol.PatrolMissionFragment;
import com.xiaomai.zhuangba.fragment.personal.master.team.TheTeamJoinedFragment;
import com.xiaomai.zhuangba.fragment.personal.master.team.create.CreateJoinFragment;
import com.xiaomai.zhuangba.fragment.personal.master.team.join.JoinTheTeamFragment;
import com.xiaomai.zhuangba.fragment.personal.wallet.WalletFragment;
import com.xiaomai.zhuangba.fragment.personal.wallet.paydeposit.PayDepositFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.Util;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Administrator
 * @date 2019/7/19 0019
 * <p>
 * 师傅端 个人中心
 */
public class MasterPersonalFragment extends PersonalFragment implements OnRefreshListener {

    @BindView(R.id.ivUserHead)
    ImageView ivUserHead;
    @BindView(R.id.tvPersonalName)
    TextView tvPersonalName;
    @BindView(R.id.tvPersonalTodayNumbers)
    TextView tvPersonalTodayNumbers;
    @BindView(R.id.tvPersonalTodayIncome)
    TextView tvPersonalTodayIncome;
    @BindView(R.id.tvPersonalStatus)
    TextView tvPersonalStatus;
    @BindView(R.id.tvMasterPersonalTeamName)
    TextView tvMasterPersonalTeamName;
    @BindView(R.id.relPlatformMaster)
    RelativeLayout relPlatformMaster;
    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshBaseList;

    private String status = "";
    private String teamTile = "";

    public static MasterPersonalFragment newInstance() {
        Bundle args = new Bundle();
        MasterPersonalFragment fragment = new MasterPersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        UserInfo userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        int authenticationStatue = userInfo.getAuthenticationStatue();
        if (authenticationStatue == StaticExplain.IN_AUDIT.getCode()
                || authenticationStatue == StaticExplain.NO_CERTIFICATION.getCode()
                || authenticationStatue == StaticExplain.REJECT_AUDIT.getCode()) {
            relPlatformMaster.setVisibility(View.GONE);
        }
        tvPersonalStatus.setText(userInfo.getMasterRankName());
        tvPersonalName.setText(userInfo.getUserText());
        GlideManager.loadCircleImage(getActivity(), userInfo.getBareHeadedPhotoUrl(), ivUserHead, R.drawable.bg_def_head);

        //刷新
        refreshBaseList.setOnRefreshListener(this);
        //默认刷新
        refreshBaseList.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshMasterPersonal();
    }

    @OnClick({R.id.relWallet, R.id.relPersonalScopeOfService, R.id.relPlatformMaster, R.id.relMasterMaintenance
            , R.id.relPersonalPatrolMission, R.id.relPersonalTeam})
    public void onMasterViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relWallet:
                //钱包
                startFragment(WalletFragment.newInstance());
                break;
            case R.id.relMasterMaintenance:
                //我的维保单
                startFragment(ContinuousIncomeFragment.newInstance());
                break;
            case R.id.relPersonalScopeOfService:
                //修改服务范围
                startFragment(UpdateScopeOfServiceFragment.newInstance());
                break;
            case R.id.relPlatformMaster:
                //缴纳保证金
                UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
                if (unique.getAuthenticationStatue() == StaticExplain.CERTIFIED.getCode()) {
                    startFragment(PayDepositFragment.newInstance());
                } else {
                    startFragment(IDCardScanningFragment.newInstance());
                }
                break;
            case R.id.relPersonalPatrolMission:
                //巡查任务
                startFragment(PatrolMissionFragment.newInstance());
                break;
            case R.id.relPersonalTeam:
                //我的团队  查询是否加入了团队  创建了团队
                if (!TextUtils.isEmpty(status) && status.equals(String.valueOf(StaticExplain.NO_TEAM_WAS_CREATED_JOINED.getCode()))) {
                    //没有创建或者加入团队
                    startFragment(CreateJoinFragment.newInstance());
                } else if (!TextUtils.isEmpty(status) && status.equals(String.valueOf(StaticExplain.CREATE_TEAM.getCode()))) {
                    //创建了团队
                    startFragmentForResult(TheTeamJoinedFragment.newInstance(), ForResultCode.START_FOR_RESULT_CODE.getCode());
                } else if (!TextUtils.isEmpty(status) && status.equals(String.valueOf(StaticExplain.JOIN_THE_TEAM.getCode()))) {
                    //加入了团队
                    startFragment(JoinTheTeamFragment.newInstance(teamTile));
                }
                break;
            default:
        }
    }

    private void refreshMasterPersonal() {
        //团队
        Observable<HttpResult<CreateTeamBean>> teamObservable = RxUtils.getObservable(ServiceUrl.getUserApi().selectByTeam())
                .subscribeOn(Schedulers.io());
        //统计雇主和师傅
        Observable<HttpResult<OrderStatistics>> httpResultObservable = RxUtils.getObservable(ServiceUrl.getUserApi().getOrderStatistics())
                .subscribeOn(Schedulers.io());
        Observable<Object> objectObservable = Observable.zip(teamObservable, httpResultObservable, new BiFunction<HttpResult<CreateTeamBean>
                , HttpResult<OrderStatistics>, Object>() {
            @Override
            public Object apply(HttpResult<CreateTeamBean> createTeamBeanHttpResult, HttpResult<OrderStatistics> orderStatisticsHttpResult) {
                MasterPersonalZip masterPersonalZip = new MasterPersonalZip();
                masterPersonalZip.setCreateTeamBean(createTeamBeanHttpResult.getData());
                masterPersonalZip.setOrderStatistics(orderStatisticsHttpResult.getData());
                return masterPersonalZip;
            }
        }).compose(this.bindToLifecycle());
        BaseHttpZipRxObserver.getInstance().httpZipObserver(objectObservable, new BaseCallback() {
            @Override
            public void onSuccess(Object obj) {
                MasterPersonalZip masterPersonalZip = (MasterPersonalZip) obj;
                initDataTeam(masterPersonalZip);
                refreshBaseList.finishRefresh();
            }

            @Override
            public void onFail(Object obj) {
                super.onFail(obj);
                refreshBaseList.finishRefresh();
            }
        });
    }

    private void initDataTeam(MasterPersonalZip masterPersonalZip) {
        CreateTeamBean createTeamBean = masterPersonalZip.getCreateTeamBean();
        OrderStatistics orderStatistics = masterPersonalZip.getOrderStatistics();
        status = createTeamBean.getEnumCode();
        if (status.equals(String.valueOf(StaticExplain.CREATE_TEAM.getCode()))) {
            tvMasterPersonalTeamName.setText(getString(R.string.enum_name_val
                    , createTeamBean.getEnumName(), String.valueOf(createTeamBean.getEnumVal())));
            teamTile = createTeamBean.getEnumName();
        } else if (status.equals(String.valueOf(StaticExplain.JOIN_THE_TEAM.getCode()))) {
            tvMasterPersonalTeamName.setText(getString(R.string.enum_name_val
                    , createTeamBean.getEnumName(), String.valueOf(createTeamBean.getEnumVal())));
            teamTile = createTeamBean.getEnumName();
        } else if (status.equals(String.valueOf(StaticExplain.NO_TEAM_WAS_CREATED_JOINED.getCode()))) {
            tvMasterPersonalTeamName.setText("");
            teamTile = "";
        }
        //今日收入
        tvPersonalTodayIncome.setText(Util.getZero(orderStatistics.getMasterOrderAmount()));
        //今日单数
        tvPersonalTodayNumbers.setText(String.valueOf(orderStatistics.getPendingDisposal()));
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == ForResultCode.RESULT_OK.getCode()) {
            if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode()) {
                refreshBaseList.autoRefresh();
            }
        }
    }

    @Override
    public void startServiceAgreement() {
        startFragment(WebViewFragment.newInstance(ConstantUtil.MASTER_FABRICATING_USER_SERVICE_PROTOCOL,
                getString(R.string.fabricating_user_service_protocol)));
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_master_personal;
    }

    @Override
    public boolean isBackArrow() {
        return false;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }
}
