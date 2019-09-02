package com.xiaomai.zhuangba.fragment.orderdetail.master;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.fragment.ImgPreviewFragment;
import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.ImgExhibitionAdapter;
import com.xiaomai.zhuangba.data.bean.DeliveryContent;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.Util;
import com.xiaomai.zhuangba.weight.GridSpacingItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 *
 * 订单 完成 详情
 */
public class MasterCompleteFragment extends BeUnderConstructionFragment {

    @BindView(R.id.recyclerMasterWorkerDeliveryContent)
    RecyclerView recyclerMasterWorkerDeliveryContent;
    @BindView(R.id.ivMasterWorkerSignature)
    ImageView ivMasterWorkerSignature;

    /**
     * 任务提交后的照片
     */
    private ImgExhibitionAdapter imgExhibitionAfterAdapter;

    public static MasterCompleteFragment newInstance(String orderCode , String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        MasterCompleteFragment fragment = new MasterCompleteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        //任务提交后的照片
        imgExhibitionAfterAdapter = new ImgExhibitionAdapter();
        recyclerMasterWorkerDeliveryContent.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerMasterWorkerDeliveryContent.addItemDecoration(new GridSpacingItemDecoration(4, 32, false));
        recyclerMasterWorkerDeliveryContent.setAdapter(imgExhibitionAfterAdapter);
    }

    @Override
    public void masterScenePhoto(DeliveryContent deliveryContent) {
        //交付后的内容
        String picturesUrl = deliveryContent.getPicturesUrl();
        if (!TextUtils.isEmpty(picturesUrl)) {
            final ArrayList<String> urlList = (ArrayList<String>) Util.getList(picturesUrl);
            imgExhibitionAfterAdapter.setNewData(urlList);
            imgExhibitionAfterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (urlList != null) {
                        startFragment(ImgPreviewFragment.newInstance(position, urlList));
                    }
                }
            });
        }
        String electronicSignature = deliveryContent.getElectronicSignature();
        if (!TextUtils.isEmpty(electronicSignature)) {
            //负责人签名
            GlideManager.loadImage(getActivity(), electronicSignature, ivMasterWorkerSignature);
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_master_complete;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.order_detail);
    }

}
