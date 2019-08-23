package com.xiaomai.zhuangba.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.util.DensityUtils;
import com.example.toollib.util.Log;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.ShopCarData;
import com.xiaomai.zhuangba.data.module.IOnAddDelListeners;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.ShopCarUtil;
import com.xiaomai.zhuangba.weight.AnimShopsButton;

/**
 * @author Administrator
 * @date 2019/8/7 0007
 */
public class ShopCarAdapter extends BaseQuickAdapter<ShopCarData , BaseViewHolder> {

    public ShopCarAdapter() {
        super(R.layout.item_shop_car , null);
    }

    @Override
    protected void convert(BaseViewHolder helper,final ShopCarData shopCarData) {
        TextView tvShopCarName = helper.getView(R.id.tvShopCarName);
        TextView tvShopMoney = helper.getView(R.id.tvShopMoney);
        TextView tvShopCarTallMoney = helper.getView(R.id.tvShopCarTallMoney);
        AnimShopsButton animShopCar = helper.getView(R.id.animShopCar);
        TextView tvShopCarMaintenance = helper.getView(R.id.tvShopCarMaintenance);
        ImageView ivShopCarRight = helper.getView(R.id.ivShopCarRight);
        LinearLayout layShopCarMaintenance = helper.getView(R.id.layShopCarMaintenance);

        //服务项目 名称
        tvShopCarName.setText(shopCarData.getText());
        //服务项目 单价
        tvShopMoney.setText(mContext.getString(R.string.shop_car_money, shopCarData.getMoney()));
        //服务项目 总价
        Double totalMoneys = ShopCarUtil.getTotalMoneys(DensityUtils.stringTypeInteger(shopCarData.getNumber()),
                DensityUtils.stringTypeDouble(shopCarData.getMoney()), DensityUtils.stringTypeDouble(shopCarData.getMoney2()),
                DensityUtils.stringTypeDouble(shopCarData.getMoney3()), DensityUtils.stringTypeDouble(shopCarData.getMaintenanceMoney()));
        tvShopCarTallMoney.setText(mContext.getString(R.string.content_money, String.valueOf(totalMoneys)));
        //服务项目 数量
        animShopCar.setIsStatus(DensityUtils.stringTypeInteger(shopCarData.getNumber()));

        Log.e("是否 有维保 " + shopCarData.getMaintenanceId());
        //维保服务 价格 和 数量  维保服务 箭头
        if (shopCarData.getMaintenanceId() == ConstantUtil.DEF_MAINTENANCE) {
            // 没有选择维保
            tvShopCarMaintenance.setText(mContext.getString(R.string.no_maintenance));
            tvShopCarMaintenance.setTextColor(mContext.getResources().getColor(R.color.tool_lib_gray_222222));
            ivShopCarRight.setBackgroundResource(R.drawable.ic_right_back);
        } else {
            //有选择维保
            tvShopCarMaintenance.setText(mContext.getString(R.string.shop_car_maintenance
                    , shopCarData.getMaintenanceTime(), shopCarData.getMaintenanceMoney()));
            tvShopCarMaintenance.setTextColor(mContext.getResources().getColor(R.color.tool_lib_gray_B1B1B1));
            ivShopCarRight.setBackgroundResource(R.drawable.ic_right_gray);
        }
        animShopCar.setOnAddDelListener(new IOnAddDelListeners() {
            @Override
            public void onAddSuccess(int count) {
                if (iShopCarAdapterCallBack != null) {
                    iShopCarAdapterCallBack.updateShopCarFragment(shopCarData, count);
                }
            }

            @Override
            public void onDelSuccess(int count) {
                if (iShopCarAdapterCallBack != null) {
                    iShopCarAdapterCallBack.updateShopCarFragment(shopCarData, count);
                }
            }
        });
        layShopCarMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iShopCarAdapterCallBack != null) {
                    iShopCarAdapterCallBack.showMaintenanceDialog(shopCarData);
                }
            }
        });

    }


    private IShopCarAdapterCallBack iShopCarAdapterCallBack;


    public interface IShopCarAdapterCallBack {
        /**
         * 项目数量 增加或者 减少
         *
         * @param shopCarData 商品
         * @param count       数量
         */
        void updateShopCarFragment(ShopCarData shopCarData, int count);

        /**
         * 弹出 选择维保dialog
         *
         * @param shopCarData 商品
         */
        void showMaintenanceDialog(ShopCarData shopCarData);
    }

    public void setIShopCarAdapterCallBack(IShopCarAdapterCallBack iShopCarAdapterCallBack) {
        this.iShopCarAdapterCallBack = iShopCarAdapterCallBack;
    }
}
