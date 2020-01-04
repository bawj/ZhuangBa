package com.xiaomai.zhuangba.fragment.orderdetail.master.installation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.fragment.ImgPreviewFragment;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.Log;
import com.google.gson.Gson;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.ImgExhibitionAdapter;
import com.xiaomai.zhuangba.data.bean.DeliveryContent;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderAddress;
import com.xiaomai.zhuangba.data.bean.ServiceData;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.fragment.orderdetail.master.base.BaseMasterOrderDetailFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.Util;
import com.xiaomai.zhuangba.weight.GridSpacingItemDecoration;
import com.xiaomai.zhuangba.weight.popup.QMUIPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 * <p>
 * 施工中
 */
public class BeUnderConstructionFragment extends BaseMasterOrderDetailFragment {

    @BindView(R.id.recyclerMasterWorkerScenePhoto)
    RecyclerView recyclerMasterWorkerScenePhoto;
    @BindView(R.id.ivMasterConfirmation)
    ImageView ivMasterConfirmation;

    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshBaseList;

    /**
     * 任务开始前的照片
     */
    private ImgExhibitionAdapter imgExhibitionAdapter;
    private OrderAddress orderAddress = new OrderAddress();

    public static BeUnderConstructionFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        BeUnderConstructionFragment fragment = new BeUnderConstructionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        //现场照
        imgExhibitionAdapter = new ImgExhibitionAdapter();
        recyclerMasterWorkerScenePhoto.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerMasterWorkerScenePhoto.addItemDecoration(new GridSpacingItemDecoration(4, 32, false));
        recyclerMasterWorkerScenePhoto.setAdapter(imgExhibitionAdapter);


        final Button button = topBarBase.addRightTextButton(getString(R.string.site_replenishment), QMUIViewHelper.generateViewId());
        button.setTextColor(getRightTitleColor());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QMUIPopupWindow qmuiPopupWindow = QMUIPopupWindow.newInstance();
                qmuiPopupWindow.setIQMUIPopupCallBack(new QMUIPopupWindow.IQMUIPopupCallBack() {
                    @Override
                    public void addProject() {
                        addProjectRequest();
                    }

                    @Override
                    public void deletionItem() {

                    }

                    @Override
                    public void custom() {

                    }
                });
                qmuiPopupWindow.initPopup(getActivity()).show(button);
            }
        });
    }

    private void addProjectRequest() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getServiceCategory())
                .compose(this.<HttpResult<List<ServiceData>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<List<ServiceData>>(getActivity()) {
                    @Override
                    protected void onSuccess(List<ServiceData> serviceDataList) {
                        if (serviceDataList != null && serviceDataList.size() > 0) {
                            int serviceId = serviceDataList.get(0).getServiceId();
                            String orderAddressGson = new Gson().toJson(orderAddress);
                            startFragmentForResult(AddProjectInstallationList.newInstance(String.valueOf(serviceId),
                                    "", orderAddressGson , getOrderCode(),ongoingOrdersList.getPublisher())
                                    , ForResultCode.START_FOR_RESULT_CODE.getCode());
                        }
                    }
                });
    }

    @Override
    public void ongoingOrdersListSuccess(OngoingOrdersList ongoingOrdersList) {
        super.ongoingOrdersListSuccess(ongoingOrdersList);
        String address = ongoingOrdersList.getAddress();
        if (!TextUtils.isEmpty(address) && address.contains("/")){
            String city[] = address.split("/");
            if (city.length > 1){
                orderAddress.setProvince(city[0]);
                orderAddress.setCity(city[1]);
            }
        }
    }

    @Override
    public void masterScenePhoto(DeliveryContent deliveryContent) {
        String picturesUrl = deliveryContent.getPicturesUrl();
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
        Log.e("开工确认图片 地址 " + deliveryContent.getElectronicSignature());
        GlideManager.loadImage(getActivity(), deliveryContent.getElectronicSignature(), ivMasterConfirmation);
    }

    @OnClick(R.id.btnSubmitForAcceptance)
    public void onViewBeUnderConstructionClicked() {
        //提交验收
        startFragment(NewSubmitAcceptanceFragment.newInstance(getOrderCode(), getOrderType()));
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
    protected String getActivityTitle() {
        return getString(R.string.order_detail);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_be_under_construction;
    }

}
