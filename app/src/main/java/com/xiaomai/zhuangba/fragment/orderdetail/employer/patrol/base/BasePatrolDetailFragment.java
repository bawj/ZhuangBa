package com.xiaomai.zhuangba.fragment.orderdetail.employer.patrol.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.OrderDateListAdapter;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderDateList;
import com.xiaomai.zhuangba.data.bean.OrderServiceDate;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.DateUtil;
import com.xiaomai.zhuangba.util.PatrolStatusUtil;

import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/10/7 0007
 */
public class BasePatrolDetailFragment<T extends IBasePatrolModule> extends BaseFragment<T>
        implements OnRefreshListener, IBasePatrolView{
    /** 标题 */
    @BindView(R.id.tvBasePatrolTaskTitle)
    TextView tvBasePatrolTaskTitle;
    /** 订单状态 */
    @BindView(R.id.tvBasePatrolOrdersType)
    TextView tvBasePatrolOrdersType;
    /** 设备编号 */
    @BindView(R.id.tvBasePatrolEquipmentNumber)
    TextView tvBasePatrolEquipmentNumber;
    /** 价格 */
    @BindView(R.id.tvBaseOrderDetailTotalMoney)
    TextView tvBaseOrderDetailTotalMoney;
    /** 巡查区域 */
    @BindView(R.id.tvBasePatrolArea)
    TextView tvBasePatrolArea;
    /** 订单编号 */
    @BindView(R.id.tvBasePatrolOrderNumber)
    TextView tvBasePatrolOrderNumber;
    /** 服务时长 */
    @BindView(R.id.tvBasePatrolLengthOfService)
    TextView tvBasePatrolLengthOfService;
    /** 服务时间 */
    @BindView(R.id.tvBasePatrolLengthOfServiceTime)
    TextView tvBasePatrolLengthOfServiceTime;
    /** 备注说明 */
    @BindView(R.id.tvBasePatrolNotes)
    TextView tvBasePatrolNotes;
    /** 地址 */
    @BindView(R.id.tvBaseOrderDetailLocation)
    TextView tvBaseOrderDetailLocation;
    /** 订单信息 */
    @BindView(R.id.recyclerOrderInformation)
    RecyclerView recyclerOrderInformation;
    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshBaseList;
    @BindView(R.id.relBaseOrderDetail)
    RelativeLayout relBaseOrderDetail;
    /**
     * 订单详细信息
     */
    private OrderDateListAdapter orderDateListAdapter;

    public static BasePatrolDetailFragment newInstance() {
        Bundle args = new Bundle();
        BasePatrolDetailFragment fragment = new BasePatrolDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected T initModule() {
        return (T) new BasePatrolModule();
    }

    @Override
    public void initView() {
        relBaseOrderDetail.setVisibility(View.GONE);
        refreshBaseList.setOnRefreshListener(this);
        //默认刷新
        refreshBaseList.autoRefresh();
        //订单信息
        recyclerOrderInformation.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderDateListAdapter = new OrderDateListAdapter();
        recyclerOrderInformation.setAdapter(orderDateListAdapter);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_base_patrol_detail;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.order_detail);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        iModule.requestPatrolDetail();
    }
    @Override
    public String getOrderCode() {
        if (getArguments() != null) {
            return getArguments().getString(ConstantUtil.ORDER_CODE);
        }
        return "";
    }

    @Override
    public String getOrderType() {
        if (getArguments() != null) {
            return getArguments().getString(ConstantUtil.ORDER_TYPE);
        }
        return "";
    }

    @Override
    public void requestOrderDetailSuccess(OrderServiceDate orderServiceDate) {
        relBaseOrderDetail.setVisibility(View.VISIBLE);
        OngoingOrdersList ongoingOrdersList = orderServiceDate.getOngoingOrdersList();
        if (ongoingOrdersList != null) {
            ongoingOrdersListSuccess(ongoingOrdersList);
        }
        List<OrderDateList> orderDateLists = orderServiceDate.getOrderDateLists();
        if (orderDateLists != null){
            orderDateListsSuccess(orderDateLists);
        }
    }

    private void ongoingOrdersListSuccess(OngoingOrdersList ongoingOrdersList) {
        //标题
        tvBasePatrolTaskTitle.setText(getString(R.string.patrol_title));
        //订单状态
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        if (unique.getRole().equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))){
            PatrolStatusUtil.employerStatus(getActivity() , ongoingOrdersList.getOrderStatus() ,tvBasePatrolOrdersType);
        }else if (unique.getRole().equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))){
            PatrolStatusUtil.masterStatus(getActivity() , ongoingOrdersList.getOrderStatus() ,tvBasePatrolOrdersType);
        }
        //设备编号
        tvBasePatrolEquipmentNumber.setText(ongoingOrdersList.getName());
        //价格
        tvBaseOrderDetailTotalMoney.setText(getString(R.string.content_money,String.valueOf(ongoingOrdersList.getOrderAmount())));
        //巡查区域
        String telephone = ongoingOrdersList.getTelephone();
        ///tvBasePatrolArea.setText(Util.getNoodles(getActivity() , telephone));
        tvBasePatrolArea.setText(telephone);
        //订单编号
        tvBasePatrolOrderNumber.setText(ongoingOrdersList.getOrderCode());
        //服务时长
        tvBasePatrolLengthOfService.setText(getString(R.string.months , ongoingOrdersList.getDebugging()));
        //服务时间
        String slottingStartLength = ongoingOrdersList.getSlottingStartLength();
        //巡查日期
        String slottingEndLength = ongoingOrdersList.getSlottingEndLength();
        if (slottingStartLength.equals(ConstantUtil.MONTH)) {
            tvBasePatrolLengthOfServiceTime.setText(getString(R.string.sing_month , slottingEndLength));
        } else if (slottingStartLength.equals(ConstantUtil.WEEK)) {
            tvBasePatrolLengthOfServiceTime.setText(getString(R.string.weekly , DateUtil.getWeek(slottingEndLength)));
        }
        //备注说明
        tvBasePatrolNotes.setText(ongoingOrdersList.getEmployerDescribe());
        //地址
        tvBaseOrderDetailLocation.setText(ongoingOrdersList.getAddress());
    }

    public void orderDateListsSuccess(List<OrderDateList> orderDateLists) {
        //订单时间信息
        orderDateLists.add(0, new OrderDateList(getOrderCode() == null ? "" : getOrderCode(), "", getString(R.string.order_code)));
        orderDateListAdapter.setNewData(orderDateLists);
    }

    @Override
    public void finishRefresh() {
        refreshBaseList.finishRefresh();
    }
}
