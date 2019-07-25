package com.xiaomai.zhuangba.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.ShopCarData;
import com.xiaomai.zhuangba.data.module.IOnAddDelListeners;
import com.xiaomai.zhuangba.util.ShopCarUtil;
import com.xiaomai.zhuangba.weight.AnimShopsButton;

/**
 * @author Administrator
 * @date 2019/7/10 0010
 */
public class SheetBehaviorAdapter extends BaseQuickAdapter<ShopCarData, BaseViewHolder> {

    public SheetBehaviorAdapter() {
        super(R.layout.item_sheet_behavior, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,final ShopCarData shopCarData) {
        TextView tvShopName = helper.getView(R.id.tvShopName);
        TextView tvShopMoney = helper.getView(R.id.tvShopMoney);
        AnimShopsButton animShopDialog = helper.getView(R.id.animShopDialog);
        if (shopCarData != null) {
            tvShopName.setText(shopCarData.getText());
            tvShopMoney.setText(shopCarData.getMoney());
            animShopDialog.setIsStatus(DensityUtils.stringTypeInteger(shopCarData.getNumber()));
        } else {
            animShopDialog.setIsDefStatus();
        }
        animShopDialog.setOnAddDelListener(new IOnAddDelListeners() {
            @Override
            public void onAddSuccess(int count) {
                if (iSheetBehaviorListener != null && shopCarData != null) {
                    ShopCarUtil.saveDialogShopCar(shopCarData, count);
                    iSheetBehaviorListener.sheetBehaviorUpdate();
                }
            }
            @Override
            public void onDelSuccess(int count) {
                if (iSheetBehaviorListener != null && shopCarData != null) {
                    ShopCarUtil.saveDialogShopCar(shopCarData, count);
                    iSheetBehaviorListener.sheetBehaviorUpdate();
                }
            }
        });
    }

    private ISheetBehaviorListener iSheetBehaviorListener;

    public void setOnAddDelListener(ISheetBehaviorListener iSheetBehaviorListener) {
        this.iSheetBehaviorListener = iSheetBehaviorListener;
    }
    public interface ISheetBehaviorListener {
        /**
         * 更新界面
         */
        void sheetBehaviorUpdate();
    }
}
