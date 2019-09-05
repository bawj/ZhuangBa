package com.xiaomai.zhuangba.fragment.advertisement.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.manager.GlideManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.OrderDateListAdapter;
import com.xiaomai.zhuangba.data.bean.DeliveryContent;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderDateList;
import com.xiaomai.zhuangba.data.bean.OrderServiceDate;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.data.module.advertisement.BaseAdvertisementModule;
import com.xiaomai.zhuangba.data.module.advertisement.IBaseAdvertisementModule;
import com.xiaomai.zhuangba.data.module.advertisement.IBaseAdvertisementView;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.util.AdvertisingStatusUtil;
import com.xiaomai.zhuangba.util.ConstantUtil;

import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/27 0027
 * <p>
 * 雇主和师傅 广告详情base
 */
public class BaseAdvertisementFragment extends BaseFragment<IBaseAdvertisementModule> implements IBaseAdvertisementView, OnRefreshListener {

    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshBaseList;
    @BindView(R.id.tvBaseOrderDetailItemOrdersType)
    TextView tvBaseOrderDetailItemOrdersType;
    @BindView(R.id.tvBaseEquipmentNumber)
    TextView tvBaseEquipmentNumber;
    @BindView(R.id.tvBaseAdvertisementMoney)
    TextView tvBaseAdvertisementMoney;
    @BindView(R.id.tvBaseAdvertisementChangePlaces)
    TextView tvBaseAdvertisementChangePlaces;
    @BindView(R.id.tvBaseAdvertisementBatchQueryNumber)
    TextView tvBaseAdvertisementBatchQueryNumber;
    @BindView(R.id.tvBaseAdvertisementDateOfAppointment)
    TextView tvBaseAdvertisementDateOfAppointment;
    @BindView(R.id.tvBaseAdvertisementServiceCycle)
    TextView tvBaseAdvertisementServiceCycle;
    @BindView(R.id.tvBaseAdvertisementNotes)
    TextView tvBaseAdvertisementNotes;
    @BindView(R.id.tvBaseOrderDetailLocation)
    TextView tvBaseOrderDetailLocation;
    @BindView(R.id.ivBaseReplacementOfAdvertisingDrawings)
    ImageView ivBaseReplacementOfAdvertisingDrawings;
    @BindView(R.id.recyclerOrderInformation)
    RecyclerView recyclerOrderInformation;
    @BindView(R.id.layBaseOrderDetail)
    RelativeLayout layBaseOrderDetail;

    private OrderDateListAdapter orderDateListAdapter;
    @Override
    public void initView() {
        layBaseOrderDetail.setVisibility(View.GONE);
        //订单信息
        recyclerOrderInformation.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderDateListAdapter = new OrderDateListAdapter();
        recyclerOrderInformation.setAdapter(orderDateListAdapter);

        refreshBaseList.setOnRefreshListener(this);
        //默认刷新
        refreshBaseList.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        iModule.requestAdvertisementDetail();
    }

    @Override
    public void requestOrderDetailSuccess(Object object) {
        layBaseOrderDetail.setVisibility(View.VISIBLE);
        OrderServiceDate orderServiceDate = (OrderServiceDate) object;
        //订单详情
        OngoingOrdersList ongoingOrdersList = orderServiceDate.getOngoingOrdersList();
        //施工前后照片
        List<DeliveryContent> deliveryContents = orderServiceDate.getDeliveryContents();
        //订单信息
        List<OrderDateList> orderDateLists = orderServiceDate.getOrderDateLists();

        setOngoingOrder(ongoingOrdersList , orderDateLists);

        for (DeliveryContent deliveryContent : deliveryContents) {
            if (deliveryContent.getPicturesType().equals(String.valueOf(StaticExplain.BEFORE_THE_BEGINNING.getCode()))){
                setDeliveryContent(deliveryContent);
            }else if (deliveryContent.getPicturesType().equals(String.valueOf(StaticExplain.UPON_COMPLETION.getCode()))){
                setUponCompletion(deliveryContent);
            }
        }


    }

    public void setOngoingOrder(OngoingOrdersList ongoingOrdersList , List<OrderDateList> orderDateLists) {
        //广告单 状态
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        if (unique.getRole().equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))) {
            //师傅端
            AdvertisingStatusUtil.masterStatus(getActivity(), ongoingOrdersList.getOrderStatus(), tvBaseOrderDetailItemOrdersType);
        }else if (unique.getRole().equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))) {
            //雇主端
            AdvertisingStatusUtil.employerStatus(getActivity(), ongoingOrdersList.getOrderStatus(), tvBaseOrderDetailItemOrdersType);
        }
        //设备编号
        tvBaseEquipmentNumber.setText(ongoingOrdersList.getName());
        //更换位置
        tvBaseAdvertisementChangePlaces.setText(ongoingOrdersList.getTelephone());
        //批量查询号
        tvBaseAdvertisementBatchQueryNumber.setText(ongoingOrdersList.getMaterialsStartLength());
        //预约时间
        tvBaseAdvertisementDateOfAppointment.setText(ongoingOrdersList.getSlottingStartLength());
        //服务周期
        tvBaseAdvertisementServiceCycle.setText(ongoingOrdersList.getExpireTime());
        //备注
        tvBaseAdvertisementNotes.setText(ongoingOrdersList.getEmployerDescribe());
        //总金额
        tvBaseAdvertisementMoney.setText(getString(R.string.content_money , String.valueOf(ongoingOrdersList.getOrderAmount())));
        //安装地址
        tvBaseOrderDetailLocation.setText(ongoingOrdersList.getAddress());
        //广告图
        GlideManager.loadImage(getActivity() ,ongoingOrdersList.getPicturesUrl() ,ivBaseReplacementOfAdvertisingDrawings);

        //订单时间信息
        orderDateLists.add(0, new OrderDateList(ongoingOrdersList.getOrderCode(), "", getString(R.string.order_code)));
        orderDateListAdapter.setNewData(orderDateLists);
    }

    public void setDeliveryContent(DeliveryContent deliveryContents) {

    }

    public void setUponCompletion(DeliveryContent deliveryContent) {

    }

    @Override
    protected IBaseAdvertisementModule initModule() {
        return new BaseAdvertisementModule();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_base_advertisement;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.order_detail);
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
    public String getOrderType() {
        if (getArguments() != null){
            return getArguments().getString(ConstantUtil.ORDER_TYPE);
        }
        return "";
    }
}
