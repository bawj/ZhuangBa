package com.xiaomai.zhuangba.fragment.orderdetail.employer;

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
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/6 0006
 *
 * 雇主完成
 */
public class EmployerCompleteFragment extends EmployerUnderConstructionFragment{

    /**
     * 交付后的图片
     */
    @BindView(R.id.recyclerEmployerDeliveryContent)
    RecyclerView recyclerEmployerDeliveryContent;

    @BindView(R.id.ivEmployerSignature)
    ImageView ivEmployerSignature;

    /**
     * 任务提交后的照片
     */
    private ImgExhibitionAdapter imgExhibitionAfterAdapter;

    public static EmployerCompleteFragment newInstance(String orderCode) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE , orderCode);
        EmployerCompleteFragment fragment = new EmployerCompleteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        imgExhibitionAfterAdapter = new ImgExhibitionAdapter();
        recyclerEmployerDeliveryContent.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerEmployerDeliveryContent.addItemDecoration(new GridSpacingItemDecoration(4, 32, false));
        recyclerEmployerDeliveryContent.setAdapter(imgExhibitionAfterAdapter);
    }

    @Override
    public void orderDateListsDeliveryContent(List<DeliveryContent> deliveryContentList) {
        super.orderDateListsDeliveryContent(deliveryContentList);
        if (!deliveryContentList.isEmpty() && deliveryContentList.size() > 1) {
            DeliveryContent deliveryContent = deliveryContentList.get(1);
            //交付后的内容
            String picturesUrl = deliveryContent.getPicturesUrl();
            if (!TextUtils.isEmpty(picturesUrl)) {
                final List<String> urlList = Util.getList(picturesUrl);
                imgExhibitionAfterAdapter.setNewData(urlList);
                imgExhibitionAfterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        ArrayList<String> url = (ArrayList<String>) urlList;
                        if (url != null) {
                            startFragment(ImgPreviewFragment.newInstance(position, url));
                        }
                    }
                });
            }
            String electronicSignature = deliveryContent.getElectronicSignature();
            if (!TextUtils.isEmpty(electronicSignature)) {
                //负责人签名
                GlideManager.loadImage(getActivity(), electronicSignature, ivEmployerSignature);
            }
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_complete;
    }
}
