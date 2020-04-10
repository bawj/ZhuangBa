package com.xiaomai.zhuangba.fragment.base;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.AmountUtil;
import com.example.toollib.util.Log;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.PagerFragmentAdapter;
import com.xiaomai.zhuangba.adapter.TabCommonGoneNavigator;
import com.xiaomai.zhuangba.adapter.TabCommonNavigator;
import com.xiaomai.zhuangba.data.AdvertisingBillsBean;
import com.xiaomai.zhuangba.data.bean.InspectionSheetBean;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderStatistics;
import com.xiaomai.zhuangba.data.bean.StatisticsData;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.data.module.masteremployer.IMasterEmployerModule;
import com.xiaomai.zhuangba.data.module.masteremployer.IMasterEmployerView;
import com.xiaomai.zhuangba.data.module.masteremployer.MasterEmployerModule;
import com.xiaomai.zhuangba.data.observable.EventManager;
import com.xiaomai.zhuangba.data.observable.Observer;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.fragment.personal.MessageFragment;
import com.xiaomai.zhuangba.fragment.personal.PricingSheetFragment;
import com.xiaomai.zhuangba.util.Util;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 */
public class BaseMasterEmployerFragment extends BaseFragment<IMasterEmployerModule> implements IMasterEmployerView, OnRefreshListener {

    @BindView(R.id.ivUserHead)
    ImageView ivUserHead;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshBaseList;

    @BindView(R.id.tvNumberOfTeachers)
    TextView tvNumberOfTeachers;
    @BindView(R.id.tvNumberOfTeachers_)
    TextView tvNumberOfTeachers_;
    @BindView(R.id.tvOrderQuantity)
    TextView tvOrderQuantity;
    @BindView(R.id.tvOrderQuantity_)
    TextView tvOrderQuantity_;
    @BindView(R.id.tvTaskAmount_)
    TextView tvTaskAmount_;
    @BindView(R.id.tvTaskAmount)
    TextView tvTaskAmount;
    @BindView(R.id.tvNumberOfEmployers)
    TextView tvNumberOfEmployers;
    @BindView(R.id.tvNumberOfEmployers_)
    TextView tvNumberOfEmployers_;
    @BindView(R.id.tvExclusiveNumber_)
    TextView tvExclusiveNumber_;
    @BindView(R.id.tvExclusiveNumber)
    TextView tvExclusiveNumber;
    @BindView(R.id.tvCrowdSourcingNumber_)
    TextView tvCrowdSourcingNumber_;
    @BindView(R.id.tvCrowdSourcingNumber)
    TextView tvCrowdSourcingNumber;
    /**
     * 观察者管理
     */
    public EventManager eventManager;
    public String message = "";
    /**
     * 判断某个fragment 是否 刷新了
     */
    private Map<Integer, Integer> isRefreshFragment = new HashMap<>();
    private List<BaseMasterEmployerContentFragment> listFragment;

    @Override
    protected IMasterEmployerModule initModule() {
        return new MasterEmployerModule();
    }

    @Override
    public void initView() {
        listFragment = getListFragment();

        refreshBaseList.setEnableLoadMore(false);
        refreshBaseList.setOnRefreshListener(this);
        refreshBaseList.setHeaderInsetStart(76);
        UserInfo unique = getUserInfo();
        if (unique != null) {
            GlideManager.loadCircleImage(getActivity(), unique.getBareHeadedPhotoUrl(), ivUserHead , R.drawable.ic_employer_head);
            tvUserName.setText(unique.getUserText());
        }

        setFragment();

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    refreshBaseList.setEnabled(true);
                } else {
                    refreshBaseList.setEnabled(false);
                }
            }
        });

        //注册观察者
        eventManager = new EventManager(new ArrayList<Observer>());
        for (BaseMasterEmployerContentFragment baseMasterEmployerContentFragment : listFragment) {
            eventManager.registerObserver(baseMasterEmployerContentFragment);
        }

        refresh(0);
    }

    private void setFragment() {
        String[] tabTitle = getTabTitle();
        if (tabTitle != null && tabTitle.length > 0) {
            mViewPager.setAdapter(new PagerFragmentAdapter(getChildFragmentManager(), listFragment, tabTitle));
            CommonNavigator commonNavigator = new CommonNavigator(getActivity());
            if (tabTitle.length == 1) {
                //tab 只有一个 没有下划线
                commonNavigator.setAdapter(new TabCommonGoneNavigator(tabTitle, mViewPager));
            } else {
                commonNavigator.setAdapter(new TabCommonNavigator(tabTitle, mViewPager));
            }
            magicIndicator.setNavigator(commonNavigator);
            ViewPagerHelper.bind(magicIndicator, mViewPager);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {
                }

                @Override
                public void onPageSelected(int i) {
                    if (i == 0) {
                        roundButtonCheckCountryIsVisible(View.VISIBLE);
                    } else {
                        roundButtonCheckCountryIsVisible(View.GONE);
                    }
                    refresh(i);
                }

                @Override
                public void onPageScrollStateChanged(int i) {
                }
            });
        }
    }

    public void refresh(int i) {
        if (isRefreshFragment.get(i) == null) {
            refreshBaseList.autoRefresh();
            isRefreshFragment.put(i, i);
        }
    }

    public void refreshRefreshFragment(int i) {
        if (isRefreshFragment.get(i) != null) {
            refreshBaseList.autoRefresh();
            isRefreshFragment.put(i, i);
        }
    }

    @OnClick({R.id.ivUserHead, R.id.ivMessage, R.id.btnRectangle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivUserHead:
                //个人中心
                startPersonal();
                break;
            case R.id.ivMessage:
                //消息
                startFragment(MessageFragment.newInstance());
                break;
            case R.id.btnRectangle:
                startFragment(PricingSheetFragment.newInstance());
                break;
            default:
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        int currentItem = mViewPager.getCurrentItem();
        Log.e("currentItem = " + currentItem);
        if (currentItem == 0) {
            message = StringTypeExplain.REFRESH_NEW_TASK_FRAGMENT.getCode();
        } else if (currentItem == 1) {
            message = StringTypeExplain.REFRESH_NEED_DEAL_WITH_FRAGMENT.getCode();
        } else if (currentItem == 2) {
            message = StringTypeExplain.REFRESH_ADVERTISING_BILLS_FRAGMENT.getCode();
        }else if (currentItem == 3){
            message = StringTypeExplain.INSPECTION_SHEET_BILLS_FRAGMENT.getCode();
        }
        eventManager.notifyObservers(message, getAddress(), handler);

        //请求 师傅 或 雇主 的统计
        iModule.requestOrderStatistics();
        //查询平台的师傅数量，订单数量，订单金额
        iModule.requestStatisticsData();
    }

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.e("handleMessage = " + msg);
            if (msg.what == StaticExplain.STOP_REFRESH.getCode()) {
                refreshBaseList.finishRefresh();
            }
            return false;
        }
    });

    public List<BaseMasterEmployerContentFragment> getListFragment() {
        return new ArrayList<>();
    }

    public String[] getTabTitle() {
        return new String[]{};
    }

    public UserInfo getUserInfo() {
        return DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_master_employer;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

    @Override
    public void statisticsSuccess(StatisticsData statisticsData) {

        double totalAmount = statisticsData.getTotalAmount();
        if (totalAmount > 999999) {
            double div = AmountUtil.div(totalAmount, 10000, 2);
            tvTaskAmount.setText(String.valueOf(div));
            tvTaskAmount_.setText(getString(R.string.task_amount_));
        } else {
            tvTaskAmount_.setText(getString(R.string.task_amount));
            tvTaskAmount.setText(String.valueOf((int) Math.floor(statisticsData.getTotalAmount())));
        }
        Util.setTenThousand(tvNumberOfTeachers_ , tvNumberOfTeachers, getString(R.string.number_of_teachers_)
                ,getString(R.string.number_of_teachers) , statisticsData.getUserNumber());
        Util.setTenThousand(tvOrderQuantity_ , tvOrderQuantity, getString(R.string.order_quantity_),
                getString(R.string.order_quantity) , statisticsData.getOrderNumber());
        Util.setTenThousand(tvNumberOfEmployers_ , tvNumberOfEmployers, getString(R.string.number_of_employers_),
                getString(R.string.number_of_employers)  , statisticsData.getEmployerNumber());
        Util.setTenThousand(tvExclusiveNumber_ , tvExclusiveNumber , getString(R.string.exclusive_number_)
                , getString(R.string.exclusive_number), statisticsData.getExclusiveNumber());
        Util.setTenThousand(tvCrowdSourcingNumber_ , tvCrowdSourcingNumber , getString(R.string.crowd_sourcing_number_)
                , getString(R.string.crowd_sourcing_number), statisticsData.getCrowdSourcingNumber());


    }

    @Override
    public String getAddress() {
        return "";
    }

    @Override
    public void refreshAdvertisingSuccess(List<AdvertisingBillsBean> advertisingBillsBeans) {

    }

    @Override
    public void loadMoreAdvertisingSuccess(List<AdvertisingBillsBean> advertisingBillsBeans) {

    }

    @Override
    public void refreshInspectionSuccess(List<InspectionSheetBean> advertisingBillsBeans) {

    }

    @Override
    public void loadInspectionSuccess(List<InspectionSheetBean> advertisingBillsBeans) {

    }

    @Override
    public String getStatus() {
        return null;
    }

    @Override
    public void workingStateSwitchingSuccess() {

    }

    @Override
    public void orderStatisticsSuccess(OrderStatistics orderStatistics) {

    }

    public void startPersonal() {
        //跳转到个人中心
    }

    @Override
    public int getPage() {
        //方法用不上
        return 0;
    }

    @Override
    public void refreshSuccess(List<OngoingOrdersList> ordersLists) {
        //方法用不上
    }

    @Override
    public void refreshError() {
        //方法用不上
    }

    @Override
    public void loadMoreEnd() {
    }

    @Override
    public void loadMoreSuccess(List<OngoingOrdersList> ongoingOrdersLists) {
    }

    @Override
    public void loadMoreComplete() {
    }

    public void roundButtonCheckCountryIsVisible(int visible) {
        //item 切换
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (BaseMasterEmployerContentFragment baseMasterEmployerContentFragment : listFragment) {
            eventManager.removeObserver(baseMasterEmployerContentFragment);
        }
        isRefreshFragment.clear();
    }
}
