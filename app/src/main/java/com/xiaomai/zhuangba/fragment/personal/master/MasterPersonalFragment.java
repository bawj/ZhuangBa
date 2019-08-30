package com.xiaomai.zhuangba.fragment.personal.master;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.authentication.master.IDCardScanningFragment;
import com.xiaomai.zhuangba.fragment.authentication.master.RealAuthenticationFragment;
import com.xiaomai.zhuangba.fragment.personal.PersonalFragment;
import com.xiaomai.zhuangba.fragment.personal.agreement.WebViewFragment;
import com.xiaomai.zhuangba.fragment.personal.wallet.WalletFragment;
import com.xiaomai.zhuangba.fragment.personal.wallet.paydeposit.PayDepositFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/19 0019
 * <p>
 * 师傅端 个人中心
 */
public class MasterPersonalFragment extends PersonalFragment {

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
    @BindView(R.id.relPlatformMaster)
    RelativeLayout relPlatformMaster;

    /**
     * 师傅端显示 今日接单
     */
    public static final String ORDER_TODAY = "order_today";
    /**
     * 师傅端显示 今日收入
     */
    public static final String TODAY_FLOWING_RMB = "today_flowing_rmb";

    public static MasterPersonalFragment newInstance(String orderToday, String todayFlowingRmb) {
        Bundle args = new Bundle();
        args.putString(ORDER_TODAY, orderToday);
        args.putString(TODAY_FLOWING_RMB, todayFlowingRmb);
        MasterPersonalFragment fragment = new MasterPersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        //今日收入
        tvPersonalTodayIncome.setText(getTodayFlowingRmb());
        //今日单数
        tvPersonalTodayNumbers.setText(getOrderToday());
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
    }

    @OnClick({R.id.relWallet, R.id.relPersonalScopeOfService, R.id.relPlatformMaster, R.id.relMasterMaintenance})
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
                if (unique.getAuthenticationStatue() == StaticExplain.CERTIFIED.getCode()){
                    startFragment(PayDepositFragment.newInstance());
                }else {
                    startFragment(IDCardScanningFragment.newInstance());
                }
                break;
            default:
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

    /**
     * 今日单数
     */
    private String getOrderToday() {
        if (getArguments() != null) {
            return getArguments().getString(ORDER_TODAY);
        }
        return "0";
    }

    /**
     * 今日收入
     */
    private String getTodayFlowingRmb() {
        if (getArguments() != null) {
            return getArguments().getString(TODAY_FLOWING_RMB);
        }
        return "0";
    }
}
