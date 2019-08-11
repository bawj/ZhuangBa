package com.xiaomai.zhuangba.fragment.datadetails;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.DataDetailsContentAdapter;
import com.xiaomai.zhuangba.adapter.TabDataDetailsTitleAdapter;
import com.xiaomai.zhuangba.data.bean.DataDetailsBean;
import com.xiaomai.zhuangba.data.bean.DataDetailsContent;
import com.xiaomai.zhuangba.data.bean.DataDetailsContentBean;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/11 0011
 * 数据详情
 */
public class DataDetailsFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.topBarBase)
    QMUITopBarLayout topBarBase;
    @BindView(R.id.tabDataDetailsTitle)
    RecyclerView tabDataDetailsTitle;
    @BindView(R.id.tabDataDetailsContent)
    RecyclerView tabDataDetailsContent;

    private DataDetailsContentAdapter dataDetailsContentAdapter;
    private TabDataDetailsTitleAdapter tabDataDetailsTitleAdapter;


    public static DataDetailsFragment newInstance() {
        Bundle args = new Bundle();
        DataDetailsFragment fragment = new DataDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        //标题
        topBarBase.setTitle(getString(R.string.data_details));
        //返回
        topBarBase.addLeftImageButton(R.drawable.ic_back_white,
                com.example.toollib.R.id.qmui_topbar_item_left_back)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popBackStack();
                    }
                });
        statusBarWhite();

        //标题
        tabDataDetailsTitleAdapter = new TabDataDetailsTitleAdapter();
        tabDataDetailsTitle.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        tabDataDetailsTitleAdapter.setOnItemClickListener(this);
        tabDataDetailsTitle.setAdapter(tabDataDetailsTitleAdapter);
        tabDataDetailsTitleAdapter.setCheck(getDataDetailsTitleList().get(0).getData());
        List<DataDetailsBean> tabDataDetailsTitle = getDataDetailsTitleList();
        tabDataDetailsTitleAdapter.setNewData(tabDataDetailsTitle);

        //内容
        tabDataDetailsContent.setLayoutManager(new GridLayoutManager(getActivity() , 2));
        dataDetailsContentAdapter = new DataDetailsContentAdapter();
        tabDataDetailsContent.setAdapter(dataDetailsContentAdapter);
        //更新数据
        requestContent(StaticExplain.SAME_DAY.getCode());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter instanceof TabDataDetailsTitleAdapter){
            DataDetailsBean dataDetailsBean = (DataDetailsBean) view.findViewById(R.id.tvTabDataDetail).getTag();
            tabDataDetailsTitleAdapter.setCheck(dataDetailsBean.getData());
            //更新数据
            requestContent(dataDetailsBean.getType());
        }
    }

    private void requestContent(int type) {
        RxUtils.getObservable(ServiceUrl.getUserApi().getOrderStatisticsDetails(type))
                .compose(this.<HttpResult<DataDetailsContent>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<DataDetailsContent>(getActivity()) {
                    @Override
                    protected void onSuccess(DataDetailsContent dataDetailsContent) {
                        List<DataDetailsContentBean> dataDetailsContentBean = getDataDetailsContentBean(dataDetailsContent);
                        dataDetailsContentAdapter.setNewData(dataDetailsContentBean);
                    }
                });
    }

    private List<DataDetailsContentBean> getDataDetailsContentBean(DataDetailsContent dataDetailsContent){
        List<DataDetailsContentBean> dataDetailsContentBeans = new ArrayList<>();
        dataDetailsContentBeans.add(new DataDetailsContentBean(getString(R.string.published_orders) , String.valueOf(dataDetailsContent.getOrderNumber())));
        dataDetailsContentBeans.add(new DataDetailsContentBean(getString(R.string.expenditure_amount) , String.valueOf(dataDetailsContent.getExpenditureAmount())));
        dataDetailsContentBeans.add(new DataDetailsContentBean(getString(R.string.installation_list) , String.valueOf(dataDetailsContent.getInstallationsNumber())));
        dataDetailsContentBeans.add(new DataDetailsContentBean(getString(R.string.installation_amount) , String.valueOf(dataDetailsContent.getInstallationsAmount())));
        dataDetailsContentBeans.add(new DataDetailsContentBean(getString(R.string.maintenance_policy_title ) , String.valueOf(dataDetailsContent.getMaintenanceNumber())));
        dataDetailsContentBeans.add(new DataDetailsContentBean(getString(R.string.maintenance_amount ) , String.valueOf(dataDetailsContent.getMaintenanceAmount())));
        dataDetailsContentBeans.add(new DataDetailsContentBean(getString(R.string.unallocated_orders ) , String.valueOf(dataDetailsContent.getDistribution())));
        dataDetailsContentBeans.add(new DataDetailsContentBean(getString(R.string.pending_orders ) , String.valueOf(dataDetailsContent.getPendingDisposal())));

        return dataDetailsContentBeans;
    }

    private List<DataDetailsBean> getDataDetailsTitleList() {
        List<DataDetailsBean> stringList = new ArrayList<>();
        stringList.add(new DataDetailsBean(StaticExplain.SAME_DAY.getCode() , getString(R.string.same_day)));
        stringList.add(new DataDetailsBean(StaticExplain.THIS_WEEK.getCode() , getString(R.string.this_week)));
        stringList.add(new DataDetailsBean(StaticExplain.THIS_MONTH.getCode() , getString(R.string.this_month)));
        stringList.add(new DataDetailsBean(StaticExplain.THIS_QUARTER.getCode() , getString(R.string.this_quarter)));
        return stringList;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_data_details;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.data_details);
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }
}
