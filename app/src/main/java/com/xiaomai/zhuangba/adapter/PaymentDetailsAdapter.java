package com.xiaomai.zhuangba.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.ShopCarDataDao;
import com.xiaomai.zhuangba.data.ShopCarData;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.data.module.IOnAddDelListeners;
import com.xiaomai.zhuangba.weight.AnimShopsButton;

/**
 * @author Administrator
 * @date 2019/7/12 0012
 */
public class PaymentDetailsAdapter extends BaseQuickAdapter<ShopCarData, BaseViewHolder> {

    public PaymentDetailsAdapter() {
        super(R.layout.item_service_content, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ShopCarData shopCarData) {
        ImageView ivServiceContentIcon = helper.getView(R.id.ivServiceContentIcon);
        TextView tvServiceContentName = helper.getView(R.id.tvServiceContentName);
        TextView tvServiceContentMoney = helper.getView(R.id.tvServiceContentMoney);
        AnimShopsButton animShopButton = helper.getView(R.id.animShop);

        GlideManager.loadImage(mContext, shopCarData.getIcon(), ivServiceContentIcon);
        tvServiceContentName.setText(shopCarData.getText());

        tvServiceContentMoney.setText(mContext.getString(R.string.content_money, String.valueOf(shopCarData.getMoney())));
        animShopButton.setIsStatus(DensityUtils.stringTypeInteger(shopCarData.getNumber()));
        animShopButton.setFlag(false);
        animShopButton.setOnAddDelListener(new IOnAddDelListeners() {
            @Override
            public void onAddSuccess(int count) {
                //修改订单数量
                updateShopCar(count, shopCarData);
                if (baseCallback != null) {
                    baseCallback.onAddSuccess(DensityUtils.stringTypeDouble(shopCarData.getMoney()));
                }
            }

            @Override
            public void onDelSuccess(int count) {
                //修改订单数量
                updateShopCar(count, shopCarData);
                baseCallback.onDelSuccess(DensityUtils.stringTypeDouble(shopCarData.getMoney()));
            }
        });
    }

    private BaseCallBack baseCallback;

    public void setBaseCallback(BaseCallBack baseCallback) {
        this.baseCallback = baseCallback;
    }

    public interface BaseCallBack {
        /**
         * 添加
         *
         * @param price 单价
         */
        void onAddSuccess(Double price);


        /**
         * 减少
         *
         * @param price 单价
         */
        void onDelSuccess(Double price);
    }


    private void updateShopCar(int count, ShopCarData shopCarData) {
        ShopCarDataDao shopCarDataDao = DBHelper.getInstance().getShopCarDataDao();
        ShopCarData unique = shopCarDataDao.queryBuilder().where(ShopCarDataDao.Properties.Id.eq(shopCarData.getId())).unique();
        if (unique != null) {
            unique.setNumber(String.valueOf(count));
            shopCarDataDao.update(unique);
        }
    }
}
