package com.xiaomai.zhuangba.fragment.orderdetail.master;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.fragment.ImgPreviewFragment;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.Log;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.ImgExhibitionAdapter;
import com.xiaomai.zhuangba.data.bean.DeliveryContent;
import com.xiaomai.zhuangba.fragment.orderdetail.master.base.BaseMasterOrderDetailFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.Util;
import com.xiaomai.zhuangba.weight.GridSpacingItemDecoration;

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

    /**
     * 任务开始前的照片
     */
    private ImgExhibitionAdapter imgExhibitionAdapter;

    public static BeUnderConstructionFragment newInstance(String orderCode , String orderType) {
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
        startFragment(NewSubmitAcceptanceFragment.newInstance(getOrderCode() , getOrderType()));
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
