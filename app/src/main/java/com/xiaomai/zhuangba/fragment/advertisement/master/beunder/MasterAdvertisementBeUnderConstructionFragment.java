package com.xiaomai.zhuangba.fragment.advertisement.master.beunder;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.fragment.ImgPreviewFragment;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.ImgExhibitionAdapter;
import com.xiaomai.zhuangba.data.bean.DeliveryContent;
import com.xiaomai.zhuangba.fragment.advertisement.base.BaseAdvertisementFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.NewSubmitAcceptanceFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.Util;
import com.xiaomai.zhuangba.weight.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/28 0028
 *
 * 师傅 广告单 施工中
 */
public class MasterAdvertisementBeUnderConstructionFragment extends BaseAdvertisementFragment {

    @BindView(R.id.recyclerScenePhoto)
    RecyclerView recyclerScenePhoto;
    private ImgExhibitionAdapter imgExhibitionAdapter;

    public static MasterAdvertisementBeUnderConstructionFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        MasterAdvertisementBeUnderConstructionFragment fragment = new MasterAdvertisementBeUnderConstructionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        imgExhibitionAdapter = new ImgExhibitionAdapter();
        recyclerScenePhoto.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerScenePhoto.addItemDecoration(new GridSpacingItemDecoration(4, 32, false));
        recyclerScenePhoto.setAdapter(imgExhibitionAdapter);
    }

    @Override
    public void setDeliveryContent(List<DeliveryContent> deliveryContents) {
        super.setDeliveryContent(deliveryContents);
        if (!deliveryContents.isEmpty()) {
            DeliveryContent deliveryContent = deliveryContents.get(0);
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

    @OnClick(R.id.btnSubmitForAcceptance)
    public void onViewBeUnderConstructionClicked() {
        //提交验收
        startFragment(MasterAdvertisementNewSubmitAcceptanceFragment.newInstance(getOrderCode()));
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_master_advertisement_be_under_construction;
    }

}
