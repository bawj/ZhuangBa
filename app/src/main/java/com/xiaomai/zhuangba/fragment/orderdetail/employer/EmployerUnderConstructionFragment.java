package com.xiaomai.zhuangba.fragment.orderdetail.employer;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.fragment.ImgPreviewFragment;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.Log;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.ImgExhibitionAdapter;
import com.xiaomai.zhuangba.data.bean.DeliveryContent;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.base.BaseEmployerDetailFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.Util;
import com.xiaomai.zhuangba.weight.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 *
 * 雇主 施工中
 */
public class EmployerUnderConstructionFragment extends BaseEmployerDetailFragment {

    /**
     * 接单师傅的头像
     */
    @BindView(R.id.ivEmployerDetailMasterHeader)
    ImageView ivEmployerDetailMasterHeader;
    /**
     * 接单师傅的名称
     */
    @BindView(R.id.tvEmployerDetailMasterName)
    TextView tvEmployerDetailMasterName;

    /**
     * 现场照片
     */
    @BindView(R.id.recyclerEmployerScenePhoto)
    RecyclerView recyclerEmployerScenePhoto;

    /**
     * 确认开工签名
     */
    @BindView(R.id.ivEmployerStartConfirmation)
    ImageView ivEmployerStartConfirmation;
    private ImgExhibitionAdapter imgExhibitionAdapter;
    
    public static EmployerUnderConstructionFragment newInstance(String orderCode) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE , orderCode);
        EmployerUnderConstructionFragment fragment = new EmployerUnderConstructionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_under_construction;
    }

    @Override
    public void initView() {
        super.initView();
        imgExhibitionAdapter = new ImgExhibitionAdapter();
        recyclerEmployerScenePhoto.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerEmployerScenePhoto.addItemDecoration(new GridSpacingItemDecoration(4, 32, false));
        recyclerEmployerScenePhoto.setAdapter(imgExhibitionAdapter);
    }

    @Override
    public void employerOngoingOrdersListSuccess(OngoingOrdersList ongoingOrdersList) {
        super.employerOngoingOrdersListSuccess(ongoingOrdersList);
        String bareheadedPhotoUrl = ongoingOrdersList.getBareheadedPhotoUrl();
        //接受订单师傅
        String userText = ongoingOrdersList.getUserText();
        tvEmployerDetailMasterName.setText(userText);
        Log.e("师傅头像 " + bareheadedPhotoUrl);
        //接受订单师傅的头像
        GlideManager.loadCircleImage(getActivity(), bareheadedPhotoUrl,
                ivEmployerDetailMasterHeader, R.drawable.bg_def_head);
    }

    @Override
    public void orderDateListsDeliveryContent(List<DeliveryContent> deliveryContentList) {
        super.orderDateListsDeliveryContent(deliveryContentList);
        if (!deliveryContentList.isEmpty()) {
            DeliveryContent deliveryContent = deliveryContentList.get(0);
            GlideManager.loadImage(getActivity(), deliveryContent.getElectronicSignature(), ivEmployerStartConfirmation);
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
            }
        }
    }

    @Override
    public boolean isInSwipeBack() {
        statusBarWhite();
        return super.isInSwipeBack();
    }
}
