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

public class OrderPoolDetailAdapter extends BaseQuickAdapter<OrderServiceItem, BaseViewHolder> {

    public OrderPoolDetailAdapter() {
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

        ImageView ivServiceItemRight = helper.getView(R.id.ivServiceItemRight);
        int adapterPosition = helper.getAdapterPosition();
        if (adapterPosition == 0){
            ivServiceItemRight.setVisibility(View.GONE);
        }else {
            ivServiceItemRight.setVisibility(View.VISIBLE);
        }

        GlideManager.loadImage(mContext, orderServiceItem.getIconUrl(), ivItemServiceLogo , R.drawable.ic_required_options);

        tvItemServiceName.setText(orderServiceItem.getServiceText());

        //项目总金额
        double multiply = ShopCarUtil.getTotalMoneys(orderServiceItem.getNumber(),
                orderServiceItem.getAmount(), orderServiceItem.getPrice2(), orderServiceItem.getPrice3(), 0);

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

        if (mContext.getString(R.string.required_options).equals(orderServiceItem.getServiceText())) {
            multiply = orderServiceItem.getAmount();
            //项目金额
            tvItemServiceMoney.setText("");
            //项目数量
            tvServiceItemNumber.setText("");
            tvOrderDetailMaintenance.setVisibility(View.GONE);
        }else {
            //项目金额
            tvItemServiceMoney.setText(mContext.getString(R.string.content_money, String.valueOf(orderServiceItem.getAmount())));
            //项目数量
            tvServiceItemNumber.setText(mContext.getString(R.string.number, String.valueOf(orderServiceItem.getNumber())));
            tvOrderDetailMaintenance.setVisibility(View.VISIBLE);
        }

        //debugging 调试
        String debugging = orderServiceItem.getDebugging();
        if (debugging.equals(String.valueOf(StaticExplain.DEBUGGING.getCode()))) {
            tvDebugging.setVisibility(View.GONE);
        } else {
            tvDebugging.setText(mContext.getString(R.string.debugging));
            tvDebugging.setVisibility(View.VISIBLE);
        }
        String materialsEndLength = orderServiceItem.getMaterialsEndLength();
        if (DensityUtils.stringTypeInteger(materialsEndLength) > 0) {
            tvAuxiliaryMaterials.setVisibility(View.VISIBLE);
            tvSlotting.setText(mContext.getString(R.string.slotting_start_end_length,
                    orderServiceItem.getMaterialsStartLength(), orderServiceItem.getMaterialsEndLength()));
        } else {
            tvAuxiliaryMaterials.setVisibility(View.GONE);
        }

        tvItemServiceTotalMoney.setVisibility(View.GONE);
        tvItemServiceTotalMoney.setTag(orderServiceItem);
    }
}
