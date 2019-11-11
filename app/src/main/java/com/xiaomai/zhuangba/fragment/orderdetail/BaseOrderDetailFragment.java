package com.xiaomai.zhuangba.fragment.orderdetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.fragment.ImgPreviewFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.ImgExhibitionAdapter;
import com.xiaomai.zhuangba.adapter.OrderDateListAdapter;
import com.xiaomai.zhuangba.adapter.ServiceItemsAdapter;
import com.xiaomai.zhuangba.application.PretendApplication;
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
import com.xiaomai.zhuangba.util.Util;
import com.xiaomai.zhuangba.weight.GridSpacingItemDecoration;

import java.util.ArrayList;
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
        implements OnRefreshListener, IOrderDetailView, BaseQuickAdapter.OnItemClickListener {

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
     * 确认时间
     */
    @BindView(R.id.tvBaseOrderConfirmationTime)
    TextView tvBaseOrderConfirmationTime;
    @BindView(R.id.tvBaseOrderConfirmationTime_)
    TextView tvBaseOrderConfirmationTime_;
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

    /**
     * 现场照片
     */
    @BindView(R.id.relLivePhotos)
    RelativeLayout relLivePhotos;
    @BindView(R.id.recyclerLivePhotos)
    RecyclerView recyclerLivePhotos;
    private ImgExhibitionAdapter imgExhibitionAdapter;
    /**
     * 现场描述
     */
    @BindView(R.id.relFieldDescription)
    RelativeLayout relFieldDescription;
    @BindView(R.id.tvFiledDescription)
    TextView tvFiledDescription;

    private float latitude, longitude;
    /**
     * 服务项目
     */
    private ServiceItemsAdapter serviceItemsAdapter;
    /**
     * 订单详细信息
     */
    private OrderDateListAdapter orderDateListAdapter;
    /** 详细信息 */
    private OngoingOrdersList ongoingOrdersList;
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

        //订单信息
        recyclerOrderInformation.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderDateListAdapter = new OrderDateListAdapter();
        recyclerOrderInformation.setAdapter(orderDateListAdapter);

        imgExhibitionAdapter = new ImgExhibitionAdapter();
        recyclerLivePhotos.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerLivePhotos.addItemDecoration(new GridSpacingItemDecoration(4, 32, false));
        recyclerLivePhotos.setAdapter(imgExhibitionAdapter);
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
        MapUtils.mapNavigation(getActivity(), latitude, longitude);
    }

    @Override
    public void requestOrderDetailSuccess(Object object) {
        layBaseOrderDetail.setVisibility(View.VISIBLE);
        OrderServiceDate orderServiceDate = (OrderServiceDate) object;
        ongoingOrdersList = orderServiceDate.getOngoingOrdersList();
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
        for (DeliveryContent deliveryContent : deliveryContents) {
            //雇主提交的现场照片
            if (deliveryContent.getPicturesType().equals(String.valueOf(StaticExplain.EMPLOYER_LIVE_PHOTOS.getCode()))) {
                livePhotos(deliveryContent);
            } else {
                relLivePhotos.setVisibility(View.GONE);
                relFieldDescription.setVisibility(View.GONE);
            }
            //施工前照片
            if (deliveryContent.getPicturesType().equals(String.valueOf(StaticExplain.BEFORE_THE_BEGINNING.getCode()))) {
                masterScenePhoto(deliveryContent);
            }
            //施工后照片
            if (deliveryContent.getPicturesType().equals(String.valueOf(StaticExplain.UPON_COMPLETION.getCode()))) {
                masterDeliveryContent(deliveryContent);
            }
        }
    }

    /**
     * 现场照片 和 描述
     *
     * @param deliveryContent list
     */
    public void livePhotos(DeliveryContent deliveryContent) {
        String picturesUrl = deliveryContent.getPicturesUrl();
        if (!TextUtils.isEmpty(picturesUrl)) {
            final List<String> urlList = Util.getList(picturesUrl);
            imgExhibitionAdapter.setNewData(urlList);
            imgExhibitionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ArrayList<String> url = (ArrayList<String>) urlList;
                    if (url != null) {
                        startFragment(ImgPreviewFragment.newInstance(position, url));
                    }
                }
            });
            relLivePhotos.setVisibility(View.VISIBLE);
        } else {
            relLivePhotos.setVisibility(View.GONE);
        }
        String electronicSignature = deliveryContent.getElectronicSignature();
        if (!TextUtils.isEmpty(electronicSignature)) {
            relFieldDescription.setVisibility(View.VISIBLE);
            tvFiledDescription.setText(electronicSignature);
        } else {
            relFieldDescription.setVisibility(View.GONE);
        }
    }


    public void orderDateListsSuccess(List<OrderDateList> orderDateLists) {
        //订单时间信息
        orderDateLists.add(0, new OrderDateList(getOrderCode() == null ? "" : getOrderCode(), "", getString(R.string.order_code)));
        orderDateListAdapter.setNewData(orderDateLists);
    }

    public void orderServiceItemsSuccess(List<OrderServiceItem> orderServiceItems) {
        if (ongoingOrdersList != null) {
            OrderServiceItem orderServiceItem = new OrderServiceItem();
            orderServiceItem.setServiceText(getString(R.string.required_options));
            orderServiceItem.setSlottingStartLength(ongoingOrdersList.getSlottingStartLength());
            orderServiceItem.setSlottingEndLength(ongoingOrdersList.getSlottingEndLength());
            orderServiceItem.setDebugging(ongoingOrdersList.getDebugging());
            orderServiceItem.setMaterialsStartLength(ongoingOrdersList.getMaterialsStartLength());
            orderServiceItem.setMaterialsEndLength(ongoingOrdersList.getMaterialsEndLength());
            orderServiceItem.setAmount(ongoingOrdersList.getOrderAmount());
            double slottingPrice = ongoingOrdersList.getSlottingPrice();
            double debugPrice = ongoingOrdersList.getDebugPrice();
            double materialsPrice = ongoingOrdersList.getMaterialsPrice();
            orderServiceItem.setAmount((slottingPrice + debugPrice + materialsPrice));
            orderServiceItem.setIconUrl(PretendApplication.BASE_URL);
            orderServiceItems.add(0, orderServiceItem);
        }
        boolean flag = false;
        if (ongoingOrdersList != null){
            String assigner = ongoingOrdersList.getAssigner();
            UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
            String phoneNumber = unique.getPhoneNumber();
            flag = TextUtils.isEmpty(assigner) || phoneNumber.equals(assigner);
        }
        serviceItemsAdapter = new ServiceItemsAdapter(flag);
        recyclerServiceItems.setAdapter(serviceItemsAdapter);
        serviceItemsAdapter.setOnItemClickListener(this);
        //服务项目
        serviceItemsAdapter.setNewData(orderServiceItems);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //服务项目 itemClick
        if (position != 0) {
            OrderServiceItem orderServiceItem = (OrderServiceItem) view.findViewById(R.id.tvItemServiceTotalMoney).getTag();
            startFragment(ServiceDetailFragment.newInstance(orderServiceItem.getServiceText(),
                    orderServiceItem.getServiceStandard(), orderServiceItem.getVideo(), orderServiceItem.getIconUrl()));
        }
    }

    public void masterDeliveryContent(DeliveryContent deliveryContent) {
    }

    public void masterScenePhoto(DeliveryContent deliveryContent) {
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
        //确认时间
        String confirmationTime = ongoingOrdersList.getConfirmationTime();
        if (!TextUtils.isEmpty(confirmationTime)) {
            tvBaseOrderConfirmationTime_.setText(confirmationTime);
            tvBaseOrderConfirmationTime.setVisibility(View.VISIBLE);
            tvBaseOrderConfirmationTime_.setVisibility(View.VISIBLE);
        } else {
            tvBaseOrderConfirmationTime.setVisibility(View.GONE);
            tvBaseOrderConfirmationTime_.setVisibility(View.GONE);
        }
        latitude = ongoingOrdersList.getLatitude();
        longitude = ongoingOrdersList.getLongitude();
        //订单状态
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        if (unique.getRole().equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))) {
            //师傅端
            masterOngoingOrdersListSuccess(ongoingOrdersList);
            OrderStatusUtil.masterStatus(getActivity(), ongoingOrdersList.getOrderStatus(), tvBaseOrderDetailItemOrdersType);
        } else if (unique.getRole().equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))) {
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
    public void goAuthentication() {

    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

}

