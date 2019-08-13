package com.xiaomai.zhuangba.fragment.masterworker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OrderStatistics;
import com.xiaomai.zhuangba.data.bean.ProvinceBean;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.OrdersEnum;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerContentFragment;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerFragment;
import com.xiaomai.zhuangba.fragment.personal.master.MasterPersonalFragment;
import com.xiaomai.zhuangba.fragment.personal.wallet.paydeposit.PayDepositFragment;
import com.xiaomai.zhuangba.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 * <p>
 * 师傅端
 */
public class MasterWorkerFragment extends BaseMasterEmployerFragment implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshBaseList;
    @BindView(R.id.tvTodayFlowingRMB)
    TextView tvTodayFlowingRMB;
    @BindView(R.id.tvOrderToday)
    TextView tvOrderToday;
    @BindView(R.id.tvFinishToday)
    TextView tvFinishToday;
    @BindView(R.id.toggleButton)
    ToggleButton toggleButton;
    @BindView(R.id.roundButtonCheckCountry)
    QMUIRoundButton roundButtonCheckCountry;
    @BindView(R.id.btnMasterApply)
    QMUIButton btnMasterApply;

    private int optionsOne = 0, optionsTwo = 0;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

    public static MasterWorkerFragment newInstance() {
        Bundle args = new Bundle();
        MasterWorkerFragment fragment = new MasterWorkerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_master_worker;
    }

    @Override
    public void initView() {
        super.initView();
        UserInfo userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        if (userInfo != null) {
            int startFlag = userInfo.getStartFlag();
            toggleButton.setChecked(OrdersEnum.MASTER_WORK.getCode() == startFlag);
            toggleButton.setOnCheckedChangeListener(this);
            isBtnMasterApplyVisible(userInfo);

        }
        //省市 联动数据组装
        Util.getProvincialAssemble(getActivity(), options1Items, options2Items);
    }

    private void isBtnMasterApplyVisible(UserInfo userInfo) {
        //是否显示 申请成为师傅 缴纳保证金
        if (!userInfo.getMasterRankId().equals(String.valueOf(StaticExplain.OBSERVER.getCode()))) {
            btnMasterApply.setVisibility(View.GONE);
        } else {
            btnMasterApply.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.roundButtonCheckCountry, R.id.btnMasterApply})
    public void onMasterViewClicked(View view) {
        switch (view.getId()) {
            case R.id.roundButtonCheckCountry:
                showAddress();
                break;
            case R.id.btnMasterApply:
                startFragment(PayDepositFragment.newInstance());
                break;
            default:
        }
    }

    private void showAddress() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String s = options2Items.get(options1).get(options2);
                roundButtonCheckCountry.setText(s);
                refreshContent();
            }
        })
                .setTitleText("城市选择")
                .setContentTextSize(20)
                .setDividerColor(Color.LTGRAY)
                .setSelectOptions(optionsOne, optionsTwo)
                .setBgColor(Color.WHITE)
                .setTitleBgColor(getResources().getColor(R.color.tool_lib_gray_E8E8E8))
                .setTitleColor(Color.BLACK)
                .setCancelColor(getResources().getColor(R.color.tool_lib_red_EF2B2B))
                .setSubmitColor(getResources().getColor(R.color.tool_lib_red_EF2B2B))
                .setTextColorCenter(Color.BLACK)
                .isRestoreItem(false)
                .isCenterLabel(false)
                .setLabels("", "", "")
                .setOutSideColor(0x00000000)
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        optionsOne = options1;
                        optionsTwo = options2;
                    }
                })
                .build();
        pvOptions.setPicker(options1Items, options2Items);
        pvOptions.show();
    }

    /**
     * 刷新 订单池
     */
    private void refreshContent() {
        refreshBaseList.autoRefresh();
    }

    @Override
    public String getAddress() {
        return roundButtonCheckCountry.getText().toString();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
    }

    @Override
    public void orderStatisticsSuccess(OrderStatistics orderStatistics) {
        if (orderStatistics != null) {
            tvTodayFlowingRMB.setText(Util.getZero(orderStatistics.getMasterOrderAmount()));
            tvOrderToday.setText(String.valueOf(orderStatistics.getPendingDisposal()));
            tvFinishToday.setText(String.valueOf(orderStatistics.getComplete()));
        }

        isBtnMasterApplyVisible(DBHelper.getInstance().getUserInfoDao().queryBuilder().unique());
    }

    @Override
    public void startPersonal() {
        String orderToday = tvOrderToday.getText().toString();
        String todayFlowingRMB = tvTodayFlowingRMB.getText().toString();
        startFragment(MasterPersonalFragment.newInstance(orderToday, todayFlowingRMB));
    }

    @Override
    public List<BaseMasterEmployerContentFragment> getListFragment() {
        List<BaseMasterEmployerContentFragment> fragmentList = new ArrayList<>();
        fragmentList.add(OrderPoolFragment.newInstance());
        fragmentList.add(NeedDealWithFragment.newInstance());
        return fragmentList;
    }

    @Override
    public String[] getTabTitle() {
        return new String[]{getString(R.string.order_pool), getString(R.string.need_to_be_dealt_with)};
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        iModule.requestWorkingStateSwitching();
    }

    @Override
    public String getStatus() {
        return toggleButton.isChecked() ? String.valueOf(OrdersEnum.MASTER_WORK.getCode()) : String.valueOf(OrdersEnum.MASTER_REST.getCode());
    }

    @Override
    public void roundButtonCheckCountryIsVisible(int visible) {
        roundButtonCheckCountry.setVisibility(visible);
    }



    @Override
    public void workingStateSwitchingSuccess() {
        final QMUITipDialog tipDialog = new QMUITipDialog.CustomBuilder(getContext())
                .setContent(R.layout.dialog_tip_custom)
                .create();
        tipDialog.show();
        toggleButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                tipDialog.dismiss();
            }
        }, 1500);
    }
}

