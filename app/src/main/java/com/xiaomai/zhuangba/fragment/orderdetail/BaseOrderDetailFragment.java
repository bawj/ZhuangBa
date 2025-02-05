package com.xiaomai.zhuangba.fragment.orderdetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.OrderDateListAdapter;
import com.xiaomai.zhuangba.adapter.ServiceItemsAdapter;
import com.xiaomai.zhuangba.data.bean.DeliveryContent;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderDateList;
import com.xiaomai.zhuangba.data.bean.OrderServiceDate;
import com.xiaomai.zhuangba.data.bean.OrderServiceItem;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.data.module.orderdetail.IOrderDetailModule;
import com.xiaomai.zhuangba.data.module.orderdetail.IOrderDetailView;
import com.xiaomai.zhuangba.data.module.orderdetail.OrderDetailModule;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.service.ServiceDetailFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.MapUtils;
import com.xiaomai.zhuangba.util.OrderStatusUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 * <p>
 * base 订单详情
 */
public class BaseOrderDetailFragment<T extends IOrderDetailModule> extends BaseFragment<T>
        implements OnRefreshListener, IOrderDetailView , BaseQuickAdapter.OnItemClickListener {

    /**
     * 服务大类
     */
    @BindView(R.id.tvBaseOrderDetailItemOrdersTitle)
    TextView tvBaseOrderDetailItemOrdersTitle;
    /**
     * 订单状态
     */
    @BindView(R.id.tvBaseOrderDetailItemOrdersType)
    TextView tvBaseOrderDetailItemOrdersType;
    /**
     * 任务数量
     */
    @BindView(R.id.tvBaseOrderDetailTaskQuantity)
    TextView tvBaseOrderDetailTaskQuantity;
    /**
     * 联系人姓名
     */
    @BindView(R.id.tvBaseOrderDetailName)
    TextView tvBaseOrderDetailName;
    /**
     * 联系人手机
     */
    @BindView(R.id.tvBaseOrderDetailPhone)
    TextView tvBaseOrderDetailPhone;
    /**
     * 预约时间
     */
    @BindView(R.id.tvBaseOrderDetailAppointment)
    TextView tvBaseOrderDetailAppointment;
    /**
     * 联系地址
     */
    @BindView(R.id.tvBaseOrderDetailLocation)
    TextView tvBaseOrderDetailLocation;
    /**
     * 联系地址
     */
    @BindView(R.id.ivBaseOrderDetailRight)
    ImageView ivBaseOrderDetailRight;
    /**
     * 联系地址
     */
    @BindView(R.id.relBaseOrderDetailLocation)
    RelativeLayout relBaseOrderDetailLocation;
    /**
     * 服务项目
     */
    @BindView(R.id.recyclerServiceItems)
    RecyclerView recyclerServiceItems;
    /**
     * 订单信息
     */
    @BindView(R.id.recyclerOrderInformation)
    RecyclerView recyclerOrderInformation;

    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshBaseList;

    @BindView(R.id.layBaseOrderDetail)
    RelativeLayout layBaseOrderDetail;

    private float latitude , longitude;
    /** 服务项目 */
    private ServiceItemsAdapter serviceItemsAdapter;
    /** 订单详细信息 */
    private OrderDateListAdapter orderDateListAdapter;

    @Override
    protected T initModule() {
        return (T) new OrderDetailModule();
    }

    @Override
    public void initView() {
        layBaseOrderDetail.setVisibility(View.GONE);
        refreshBaseList.setOnRefreshListener(this);
        //默认刷新
        refreshBaseList.autoRefresh();

        //服务项目adapter
        recyclerServiceItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        serviceItemsAdapter = new ServiceItemsAdapter();
        recyclerServiceItems.setAdapter(serviceItemsAdapter);
        serviceItemsAdapter.setOnItemClickListener(this);

        //订单信息
        recyclerOrderInformation.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderDateListAdapter = new OrderDateListAdapter();
        recyclerOrderInformation.setAdapter(orderDateListAdapter);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        iModule.requestOrderDetail();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_order_detail;
    }

    @OnClick({R.id.relBaseOrderDetailLocation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relBaseOrderDetailLocation:
                startMap();
                break;
            default:
        }
    }

    public void startMap() {
        MapUtils.mapNavigation(getActivity() , latitude , longitude);
    }

    @Override
    public void requestOrderDetailSuccess(Object object) {
        layBaseOrderDetail.setVisibility(View.VISIBLE);
        OrderServiceDate orderServiceDate = (OrderServiceDate) object;
        OngoingOrdersList ongoingOrdersList = orderServiceDate.getOngoingOrdersList();
        if (ongoingOrdersList != null) {
            ongoingOrdersListSuccess(ongoingOrdersList);
        }
        List<OrderServiceItem> orderServiceItems = orderServiceDate.getOrderServiceItems();
        if (orderServiceItems != null && !orderServiceItems.isEmpty()) {
            orderServiceItemsSuccess(orderServiceItems);
        }
        List<OrderDateList> orderDateLists = orderServiceDate.getOrderDateLists();
        if (orderDateLists != null && !orderDateLists.isEmpty()) {
            orderDateListsSuccess(orderDateLists);
        }
        List<DeliveryContent> deliveryContents = orderServiceDate.getDeliveryContents();
        if (deliveryContents != null && !deliveryContents.isEmpty()){
            orderDateListsDeliveryContent(deliveryContents);
        }
    }


    public void orderDateListsSuccess(List<OrderDateList> orderDateLists) {
        //订单时间信息
        orderDateLists.add(0, new OrderDateList(getOrderCode() == null ? "" : getOrderCode(), "", getString(R.string.order_code)));
        orderDateListAdapter.setNewData(orderDateLists);
    }

    public void orderServiceItemsSuccess(List<OrderServiceItem> orderServiceItems) {
        //服务项目
        serviceItemsAdapter.setNewData(orderServiceItems);
    }
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //服务项目 itemClick
        OrderServiceItem orderServiceItem = (OrderServiceItem) view.findViewById(R.id.tvItemServiceTotalMoney).getTag();
        startFragment(ServiceDetailFragment.newInstance(orderServiceItem.getServiceText() , orderServiceItem.getServiceStandard()));
    }

    public void orderDateListsDeliveryContent(List<DeliveryContent> deliveryContents) {
        //师傅提交的信息
    }

    public void ongoingOrdersListSuccess(OngoingOrdersList ongoingOrdersList) {
        //订单详情
        tvBaseOrderDetailItemOrdersTitle.setText(ongoingOrdersList.getServiceText());
        //任务数量
        tvBaseOrderDetailTaskQuantity.setText(String.valueOf(ongoingOrdersList.getNumber()));
        //联系人姓名
        tvBaseOrderDetailName.setText(ongoingOrdersList.getName());
        //联系人手机
        tvBaseOrderDetailPhone.setText(ongoingOrdersList.getTelephone());
        //预约时间
        tvBaseOrderDetailAppointment.setText(ongoingOrdersList.getAppointmentTime());
        //联系地址
        tvBaseOrderDetailLocation.setText(ongoingOrdersList.getAddress());
        latitude = ongoingOrdersList.getLatitude();
        longitude = ongoingOrdersList.getLongitude();
        //订单状态
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        if (unique.getRole().equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))) {
            //师傅端
            masterOngoingOrdersListSuccess(ongoingOrdersList);
            OrderStatusUtil.masterStatus(getActivity(), ongoingOrdersList.getOrderStatus(), tvBaseOrderDetailItemOrdersType);
        }else if (unique.getRole().equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))) {
            //雇主端
            employerOngoingOrdersListSuccess(ongoingOrdersList);
            OrderStatusUtil.employerStatus(getActivity(), ongoingOrdersList.getOrderStatus(), tvBaseOrderDetailItemOrdersType);
        }
    }

    public void masterOngoingOrdersListSuccess(OngoingOrdersList ongoingOrdersList) {

    }

    public void employerOngoingOrdersListSuccess(OngoingOrdersList ongoingOrdersList) {

    }

    @Override
    public void finishRefresh() {
        refreshBaseList.finishRefresh();
    }

    @Override
    public String getOrderCode() {
        if (getArguments() != null){
            return getArguments().getString(ConstantUtil.ORDER_CODE);
        }
        return "";
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

}

