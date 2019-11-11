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
import com.xiaomai.zhuangba.weight.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/28 0028
 */
public class EmployerAdvertisementNoPassageFragment extends BaseAdvertisementFragment {

    @BindView(R.id.ivEmployerDetailMasterHeader)
    ImageView ivEmployerDetailMasterHeader;
    @BindView(R.id.tvEmployerDetailMasterName)
    TextView tvEmployerDetailMasterName;
    @BindView(R.id.recyclerEmployerScenePhoto)
    RecyclerView recyclerEmployerScenePhoto;
    @BindView(R.id.recyclerEmployerPostConstruction)
    RecyclerView recyclerEmployerPostConstruction;

    /**
     * 任务提交前的照片
     */
    private ImgExhibitionAdapter imgExhibitionAdapter;
    /**
     * 任务提交后的照片
     */
    private ImgExhibitionAdapter imgExhibitionAfterAdapter;

    public static EmployerAdvertisementNoPassageFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        EmployerAdvertisementNoPassageFragment fragment = new EmployerAdvertisementNoPassageFragment();
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

        imgExhibitionAfterAdapter = new ImgExhibitionAdapter();
        recyclerEmployerPostConstruction.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerEmployerPostConstruction.addItemDecoration(new GridSpacingItemDecoration(4, 32, false));
        recyclerEmployerPostConstruction.setAdapter(imgExhibitionAfterAdapter);
    }

    @Override
    public void setOngoingOrder(OngoingOrdersList ongoingOrdersList, List<OrderDateList> orderDateLists) {
        super.setOngoingOrder(ongoingOrdersList, orderDateLists);
        GlideManager.loadImage(getActivity(), ongoingOrdersList.getBareheadedPhotoUrl(), ivEmployerDetailMasterHeader, R.drawable.bg_def_head);
        tvEmployerDetailMasterName.setText(ongoingOrdersList.getUserText());
    }

    @Override
    public void setDeliveryContent(DeliveryContent deliveryContents) {
        super.setDeliveryContent(deliveryContents);
        if (deliveryContents != null) {
            String picturesUrl = deliveryContents.getPicturesUrl();
            if (!TextUtils.isEmpty(picturesUrl)) {
                ///final List<String> urlList = Util.getList(picturesUrl);
                final List<String> urlList = new ArrayList<>();
                urlList.add(picturesUrl);
                imgExhibitionAdapter.setNewData(urlList);
                imgExhibitionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (!urlList.isEmpty()) {
                            startFragment(ImgPreviewFragment.newInstance(position, (ArrayList<String>) urlList));
                        }
                    }
                });
            }
        }
    }

    @Override
    public void setUponCompletion(DeliveryContent deliveryContent) {
        super.setUponCompletion(deliveryContent);
        if (deliveryContent != null) {
            //交付后的内容
            String picturesUrl = deliveryContent.getPicturesUrl();
            if (!TextUtils.isEmpty(picturesUrl)) {
///                final List<String> urlList = Util.getList(picturesUrl);
                final List<String> urlList = new ArrayList<>();
                urlList.add(picturesUrl);
                imgExhibitionAfterAdapter.setNewData(urlList);
                imgExhibitionAfterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (!urlList.isEmpty()) {
                            startFragment(ImgPreviewFragment.newInstance(position, (ArrayList<String>) urlList));
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_advertisement_no_passage;
    }

}
