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
 * @date 2019/10/21 0021
 *
 * 雇主待开工
 */
public class EmployerToBeStartedFragment extends BaseEmployerDetailFragment {

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
     * 服务项目
     */
    private ImgExhibitionAdapter imgExhibitionAdapter;

    public static EmployerToBeStartedFragment newInstance(String orderCode , String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        EmployerToBeStartedFragment fragment = new EmployerToBeStartedFragment();
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
    public void masterScenePhoto(DeliveryContent deliveryContent) {
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

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_to_be_started;
    }
}
