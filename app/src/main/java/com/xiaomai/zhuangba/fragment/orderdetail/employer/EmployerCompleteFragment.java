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
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.ImgExhibitionAdapter;
import com.xiaomai.zhuangba.data.bean.DeliveryContent;
import com.xiaomai.zhuangba.data.bean.OrderServiceItem;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.Util;
import com.xiaomai.zhuangba.weight.GridSpacingItemDecoration;
import com.xiaomai.zhuangba.weight.dialog.CompensateDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/6 0006
 * <p>
 * 雇主完成
 */
public class EmployerCompleteFragment extends EmployerUnderConstructionFragment {

    /**
     * 交付后的图片
     */
    @BindView(R.id.recyclerEmployerDeliveryContent)
    RecyclerView recyclerEmployerDeliveryContent;

    @BindView(R.id.ivEmployerSignature)
    ImageView ivEmployerSignature;
    @BindView(R.id.tvEmployerSignature)
    TextView tvEmployerSignature;

    /**
     * 任务提交后的照片
     */
    private ImgExhibitionAdapter imgExhibitionAfterAdapter;
    /**
     * 服务项目
     */
    private List<OrderServiceItem> orderServiceItemList;

    public static EmployerCompleteFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
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
    public void masterDeliveryContent(DeliveryContent deliveryContent) {
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
            tvEmployerSignature.setVisibility(View.VISIBLE);
            ivEmployerSignature.setVisibility(View.VISIBLE);
            GlideManager.loadImage(getActivity(), electronicSignature, ivEmployerSignature);
        } else {
            tvEmployerSignature.setVisibility(View.GONE);
            ivEmployerSignature.setVisibility(View.GONE);
        }
    }

    @Override
    public void orderServiceItemsSuccess(List<OrderServiceItem> orderServiceItems) {
        super.orderServiceItemsSuccess(orderServiceItems);
        //服务项目
        this.orderServiceItemList = orderServiceItems;
    }

    @OnClick({R.id.btnAddMaintenance, R.id.layCompensate})
    public void onViewEmployerCompleteClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAddMaintenance:
                DBHelper.getInstance().getOrderServiceItemDao().deleteAll();
                //新增维保
                List<OrderServiceItem> orderServiceItems = new ArrayList<>();
                for (OrderServiceItem orderServiceItem : orderServiceItemList) {
                    if (orderServiceItem.getMonthNumber() == 0 && !orderServiceItem.getServiceText().equals(getString(R.string.required_options))) {
                        orderServiceItems.add(orderServiceItem);
                    }
                }
                if (!orderServiceItems.isEmpty()) {
                    DBHelper.getInstance().getOrderServiceItemDao().insertInTx(orderServiceItems);
                    String address = ongoingOrdersList.getAddress();
                    String[] provinceCity = Util.getProvinceCity(address);
                    String province;
                    String city = "";
                    province = provinceCity[0];
                    if (provinceCity.length > 1) {
                        city = provinceCity[1];
                    }
                    startFragment(AddMaintenanceFragment.newInstance(province, city));
                }
                break;
            case R.id.layCompensate:
                //发起索赔
                new CompensateDialog<EmployerCompleteFragment>()
                        .initView(getActivity(), this)
                        .setOrderCode(getOrderCode())
                        .isRecyclerCompensateVisibility(View.GONE)
                        .showDialog();
                break;
            default:
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_complete;
    }
}
