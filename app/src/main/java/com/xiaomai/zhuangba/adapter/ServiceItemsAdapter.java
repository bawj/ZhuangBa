package com.xiaomai.zhuangba.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OrderServiceItem;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.util.ShopCarUtil;

/**
 * @author Administrator
 * @date 2019/7/15 0015
 */
public class ServiceItemsAdapter extends BaseQuickAdapter<OrderServiceItem , BaseViewHolder> {

    public ServiceItemsAdapter() {
        super(R.layout.item_service_item, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderServiceItem orderServiceItem) {
        ImageView ivItemServiceLogo = helper.getView(R.id.ivItemServiceLogo);
        TextView tvItemServiceName = helper.getView(R.id.tvItemServiceName);
        TextView tvItemServiceMoney = helper.getView(R.id.tvItemServiceMoney);
        TextView tvServiceItemNumber = helper.getView(R.id.tvServiceItemNumber);
        TextView tvItemServiceTotalMoney = helper.getView(R.id.tvItemServiceTotalMoney);
        TextView tvOrderDetailMaintenance = helper.getView(R.id.tvOrderDetailMaintenance);

        // TODO: 2019/9/3 0003 图片 默认站位图
        GlideManager.loadImage(mContext , orderServiceItem.getIconUrl() , ivItemServiceLogo);

        tvItemServiceName.setText(orderServiceItem.getServiceText());
        //项目金额
        tvItemServiceMoney.setText(mContext.getString(R.string.content_money , String.valueOf(orderServiceItem.getAmount())));
        //项目数量
        tvServiceItemNumber.setText(mContext.getString(R.string.number , String.valueOf(orderServiceItem.getNumber())));

        //项目总金额
        double multiply = ShopCarUtil.getTotalMoneys(orderServiceItem.getNumber() ,
                orderServiceItem.getAmount() , orderServiceItem.getPrice2() , orderServiceItem.getPrice3() , 0);

        if (orderServiceItem.getMonthNumber() == 0) {
            //没有维保
            tvOrderDetailMaintenance.setText(mContext.getString(R.string.not_choosing_maintenance));
            tvOrderDetailMaintenance.setBackgroundResource(R.drawable.gray_payment_radius_bg);
            tvOrderDetailMaintenance.setTextColor(mContext.getResources().getColor(R.color.tool_lib_gray_B1B1B1));
        } else {
            //有维保
            tvOrderDetailMaintenance.setText(mContext.getString(R.string.shop_car_maintenance
                    , String.valueOf(orderServiceItem.getMonthNumber()), String.valueOf(orderServiceItem.getMaintenanceAmount())));
            tvOrderDetailMaintenance.setBackgroundResource(R.drawable.green_radius_bg);
            tvOrderDetailMaintenance.setTextColor(mContext.getResources().getColor(R.color.tool_lib_color_199898));
        }

        //开槽 调试 辅材
        TextView tvSlotting = helper.getView(R.id.tvSlotting);
        TextView tvDebugging = helper.getView(R.id.tvDebugging);
        TextView tvAuxiliaryMaterials = helper.getView(R.id.tvAuxiliaryMaterials);

        // slottingStartLength 开槽  slottingEndLength
        if (DensityUtils.stringTypeInteger(orderServiceItem.getSlottingEndLength()) > 0) {
            tvSlotting.setVisibility(View.VISIBLE);
            tvSlotting.setText(mContext.getString(R.string.slotting_start_end_length,
                    orderServiceItem.getSlottingStartLength(), orderServiceItem.getSlottingEndLength()));
        } else {
            tvSlotting.setVisibility(View.GONE);
        }
        //debugging 调试
        String debugging = orderServiceItem.getDebugging();
        if (debugging.equals(String.valueOf(StaticExplain.DEBUGGING.getCode()))) {
            tvDebugging.setText(mContext.getString(R.string.debugging));
            tvDebugging.setVisibility(View.VISIBLE);
        } else {
            tvDebugging.setVisibility(View.GONE);
        }
        String materialsEndLength = orderServiceItem.getMaterialsEndLength();
        if (DensityUtils.stringTypeInteger(materialsEndLength) > 0) {
            tvAuxiliaryMaterials.setVisibility(View.VISIBLE);
            tvSlotting.setText(mContext.getString(R.string.slotting_start_end_length,
                    orderServiceItem.getMaterialsStartLength(), orderServiceItem.getMaterialsEndLength()));
        } else {
            tvAuxiliaryMaterials.setVisibility(View.GONE);
        }

        tvItemServiceTotalMoney.setText(mContext.getString(R.string.content_money , String.valueOf(multiply)));
        tvItemServiceTotalMoney.setTag(orderServiceItem);
    }
}
