package com.xiaomai.zhuangba.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.ServiceSubcategoryProject;
import com.xiaomai.zhuangba.data.bean.ShopCarData;
import com.xiaomai.zhuangba.data.module.IOnAddDelListeners;
import com.xiaomai.zhuangba.util.ShopCarUtil;
import com.xiaomai.zhuangba.weight.AnimShopsButton;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 */
public class ServiceContentAdapter extends BaseQuickAdapter<ServiceSubcategoryProject, BaseViewHolder> {

    private IServiceContentOnAddDelListener iServiceContentOnAddDelListener;
    public ServiceContentAdapter() {
        super(R.layout.item_service_content, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,final ServiceSubcategoryProject item) {
        ImageView ivServiceContentIcon = helper.getView(R.id.ivServiceContentIcon);
        TextView tvServiceContentName = helper.getView(R.id.tvServiceContentName);
        TextView tvServiceContentMoney = helper.getView(R.id.tvServiceContentMoney);
        AnimShopsButton animShopButton = helper.getView(R.id.animShop);
        GlideManager.loadImage(mContext, item.getIconUrl(), ivServiceContentIcon, R.drawable.ic_load_error);
        tvServiceContentName.setText(item.getServiceText());
        tvServiceContentMoney.setText(mContext.getString(R.string.content_money, String.valueOf(item.getPrice())));
        tvServiceContentMoney.setTag(item);
        ShopCarData shopCarData = ShopCarUtil.getShopCarData(item);
        if (shopCarData != null) {
            animShopButton.setIsStatus(DensityUtils.stringTypeInteger(shopCarData.getNumber()));
        } else {
            animShopButton.setIsDefStatus();
        }
        animShopButton.setOnAddDelListener(new IOnAddDelListeners() {
            @Override
            public void onAddSuccess(int count) {
                ShopCarUtil.saveShopCar(item, count);
                if (iServiceContentOnAddDelListener != null){
                    iServiceContentOnAddDelListener.onAddOrDelSuccess();
                }
            }

            @Override
            public void onDelSuccess(int count) {
                ShopCarUtil.saveShopCar(item, count);
                if (iServiceContentOnAddDelListener != null){
                    iServiceContentOnAddDelListener.onAddOrDelSuccess();
                }
            }
        });
    }

    public void setOnAddDelListener(IServiceContentOnAddDelListener iServiceContentOnAddDelListener) {
        this.iServiceContentOnAddDelListener = iServiceContentOnAddDelListener;
    }
    public interface IServiceContentOnAddDelListener {
        /**
         * 购物车改变刷新界面
         */
        void onAddOrDelSuccess();

    }
}
