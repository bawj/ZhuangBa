package com.xiaomai.zhuangba.fragment.orderdetail.master.installation;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.fragment.ImgPreviewFragment;
import com.example.toollib.weight.dialog.CommonlyDialog;
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
 * @date 2019/10/21 0021
 * <p>
 * 师傅 待开工
 */
public class ToBeStartedFragment extends BaseMasterOrderDetailFragment {

    @BindView(R.id.recyclerMasterWorkerScenePhoto)
    RecyclerView recyclerMasterWorkerScenePhoto;

    /**
     * 任务开始前的照片
     */
    private ImgExhibitionAdapter imgExhibitionAdapter;

    public static ToBeStartedFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        ToBeStartedFragment fragment = new ToBeStartedFragment();
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

    @OnClick(R.id.btnCancelToBeStarted)
    public void onViewClicked() {
        //取消订单
        CommonlyDialog.getInstance().initView(getActivity())
                .setTvDialogCommonlyContent(getString(R.string.cancel_order_tip))
                .setICallBase(new CommonlyDialog.BaseCallback() {
                    @Override
                    public void sure() {
                        iModule.requestCancelOrder();
                    }
                }).showDialog();
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
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.order_detail);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_to_be_started;
    }

}
