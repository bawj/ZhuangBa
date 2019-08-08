package com.xiaomai.zhuangba.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.ShopCarData;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.ShopCarUtil;

/**
 * @author Administrator
 * @date 2019/7/12 0012
 */
public class PaymentDetailsAdapter extends BaseQuickAdapter<ShopCarData, BaseViewHolder> {

    public PaymentDetailsAdapter() {
        super(R.layout.item_payment_details, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ShopCarData shopCarData) {
        //项目图片
        ImageView ivServiceContentIcon = helper.getView(R.id.ivServiceContentIcon);
        GlideManager.loadImage(mContext, shopCarData.getIcon(), ivServiceContentIcon);
        //服务项目 名称
        TextView tvServiceContentName = helper.getView(R.id.tvServiceContentName);
        tvServiceContentName.setText(shopCarData.getText());
        //是否有维保
        TextView tvPaymentDetailMaintenance = helper.getView(R.id.tvPaymentDetailMaintenance);
        if (shopCarData.getMaintenanceId() == ConstantUtil.DEF_MAINTENANCE) {
            //没有维保
            tvPaymentDetailMaintenance.setText(mContext.getString(R.string.not_choosing_maintenance));
            tvPaymentDetailMaintenance.setBackgroundResource(R.drawable.gray_payment_radius_bg);
            tvPaymentDetailMaintenance.setTextColor(mContext.getResources().getColor(R.color.tool_lib_gray_B1B1B1));
        } else {
            //有维保
            tvPaymentDetailMaintenance.setText(mContext.getString(R.string.shop_car_maintenance
                    , shopCarData.getMaintenanceTime(), shopCarData.getMaintenanceMoney()));
            tvPaymentDetailMaintenance.setBackgroundResource(R.drawable.green_radius_bg);
            tvPaymentDetailMaintenance.setTextColor(mContext.getResources().getColor(R.color.tool_lib_color_199898));
        }
        //价格
        TextView tvServiceContentMoney = helper.getView(R.id.tvServiceContentMoney);
        tvServiceContentMoney.setText(mContext.getString(R.string.content_money, String.valueOf(shopCarData.getMoney())));
        //数量
        TextView tvServiceContentNumber = helper.getView(R.id.tvServiceContentNumber);
        tvServiceContentNumber.setText(mContext.getString(R.string.number , shopCarData.getNumber()));
        //总价格
        TextView tvPaymentMoney = helper.getView(R.id.tvPaymentMoney);
        Double totalMoneys = ShopCarUtil.getTotalMoneys(DensityUtils.stringTypeInteger(shopCarData.getNumber())
                , DensityUtils.stringTypeDouble(shopCarData.getMoney()) , DensityUtils.stringTypeDouble(shopCarData.getMoney2())
                , DensityUtils.stringTypeDouble(shopCarData.getMoney3()) , DensityUtils.stringTypeDouble(shopCarData.getMaintenanceMoney()));
        tvPaymentMoney.setText(mContext.getString(R.string.content_money , String.valueOf(totalMoneys)));

    }
}
