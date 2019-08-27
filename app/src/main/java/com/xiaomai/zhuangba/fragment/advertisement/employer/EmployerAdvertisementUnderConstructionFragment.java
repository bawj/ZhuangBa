package com.xiaomai.zhuangba.fragment.advertisement.employer;

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
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.ImgExhibitionAdapter;
import com.xiaomai.zhuangba.data.bean.DeliveryContent;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderDateList;
import com.xiaomai.zhuangba.fragment.advertisement.base.BaseAdvertisementFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.Util;
import com.xiaomai.zhuangba.weight.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/27 0027
 *
 * 广告单施工中
 */
public class EmployerAdvertisementUnderConstructionFragment extends BaseAdvertisementFragment {

    @BindView(R.id.ivEmployerDetailMasterHeader)
    ImageView ivEmployerDetailMasterHeader;
    @BindView(R.id.tvEmployerDetailMasterName)
    TextView tvEmployerDetailMasterName;
    @BindView(R.id.recyclerEmployerScenePhoto)
    RecyclerView recyclerEmployerScenePhoto;

    private ImgExhibitionAdapter imgExhibitionAdapter;
    public static EmployerAdvertisementUnderConstructionFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        EmployerAdvertisementUnderConstructionFragment fragment = new EmployerAdvertisementUnderConstructionFragment();
        fragment.setArguments(args);
        return fragment;
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
    public void setOngoingOrder(OngoingOrdersList ongoingOrdersList, List<OrderDateList> orderDateLists) {
        super.setOngoingOrder(ongoingOrdersList, orderDateLists);
        GlideManager.loadImage(getActivity(), ongoingOrdersList.getBareheadedPhotoUrl(), ivEmployerDetailMasterHeader, R.drawable.bg_def_head);
        tvEmployerDetailMasterName.setText(ongoingOrdersList.getUserText());
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

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_advertisement_under_construction;
    }


}
