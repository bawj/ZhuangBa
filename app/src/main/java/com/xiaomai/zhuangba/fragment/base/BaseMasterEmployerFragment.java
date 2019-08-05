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
import com.example.toollib.util.Log;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.PagerFragmentAdapter;
import com.xiaomai.zhuangba.adapter.TabCommonGoneNavigator;
import com.xiaomai.zhuangba.adapter.TabCommonNavigator;
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
    @BindView(R.id.tvOrderQuantity)
    TextView tvOrderQuantity;
    @BindView(R.id.tvTaskAmount)
    TextView tvTaskAmount;
    /**
     * 观察者管理
     */
    public EventManager eventManager;
    public String message = "";
    private List<BaseMasterEmployerContentFragment> listFragment;
    /**
     * 判断某个fragment 是否 刷新了
     */
    private Map<String, Integer> isRefreshFragment = new HashMap<>();

    @Override
    protected IMasterEmployerModule initModule() {
        return new MasterEmployerModule();
    }

    @Override
    public void initView() {
        refreshBaseList.setEnableLoadMore(false);
        refreshBaseList.setOnRefreshListener(this);
        refreshBaseList.setHeaderInsetStart(76);
        UserInfo unique = getUserInfo();
        if (unique != null) {
            GlideManager.loadCircleImage(getActivity(), unique.getBareHeadedPhotoUrl(), ivUserHead);
            tvUserName.setText(unique.getUserText());
        }

        listFragment = getListFragment();
        if (!listFragment.isEmpty() && getTabTitle().length > 0) {
            mViewPager.setAdapter(new PagerFragmentAdapter(getChildFragmentManager(), listFragment, getTabTitle()));
            CommonNavigator commonNavigator = new CommonNavigator(getActivity());
            if (getTabTitle() != null && getTabTitle().length == 1) {
                commonNavigator.setAdapter(new TabCommonGoneNavigator(getTabTitle(), mViewPager));
            } else {
                commonNavigator.setAdapter(new TabCommonNavigator(getTabTitle(), mViewPager));
            }
            magicIndicator.setNavigator(commonNavigator);
            ViewPagerHelper.bind(magicIndicator, mViewPager);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {
                    if (isRefreshFragment.get(String.valueOf(i)) == null) {
                        refreshBaseList.autoRefresh();
                        isRefreshFragment.put(String.valueOf(i), i);
                    }
                }

                @Override
                public void onPageSelected(int i) {
                    if (i == 0) {
                        roundButtonCheckCountryIsVisible(View.VISIBLE);
                    } else {
                        roundButtonCheckCountryIsVisible(View.GONE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int i) {
                }
            });
        }

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
    }

    @OnClick({R.id.ivUserHead, R.id.ivMessage,R.id.btnRectangle})
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
        if (currentItem == 0) {
            message = StringTypeExplain.REFRESH_NEW_TASK_FRAGMENT.getCode();
        } else if (currentItem == 1) {
            message = StringTypeExplain.REFRESH_NEED_DEAL_WITH_FRAGMENT.getCode();
        }
        Log.e("refresh currentItem = " + currentItem);
        eventManager.notifyObservers(message, getAddress(), handler);

        //请求 师傅 或 雇主 的统计
        iModule.requestOrderStatistics();
        //查询平台的师傅数量，订单数量，订单金额
        iModule.requestStatisticsData();
    }

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
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
        tvNumberOfTeachers.setText(String.valueOf(statisticsData.getUserNumber()));
        tvOrderQuantity.setText(String.valueOf(statisticsData.getOrderNumber()));
        tvTaskAmount.setText(String.valueOf(statisticsData.getTotalAmount()));
    }


    @Override
    public String getAddress() {
        return "";
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
    }
}
