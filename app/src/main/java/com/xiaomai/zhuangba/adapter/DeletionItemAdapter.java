package com.xiaomai.zhuangba.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
 * @author Bawj
 * CreateDate:     2020/1/6 0006 11:52
 */
public class DeletionItemAdapter extends BaseQuickAdapter<OrderServiceItem , BaseViewHolder> {

    public DeletionItemAdapter() {
        super(R.layout.item_deletion_item, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder,final OrderServiceItem orderServiceItem) {
        ImageView ivItemServiceLogo = baseViewHolder.getView(R.id.ivItemServiceLogo);
        TextView tvItemServiceName = baseViewHolder.getView(R.id.tvItemServiceName);
        TextView tvItemServiceMoney = baseViewHolder.getView(R.id.tvItemServiceMoney);
        TextView tvServiceItemNumber = baseViewHolder.getView(R.id.tvServiceItemNumber);
        TextView tvItemServiceTotalMoney = baseViewHolder.getView(R.id.tvItemServiceTotalMoney);
        TextView tvOrderDetailMaintenance = baseViewHolder.getView(R.id.tvOrderDetailMaintenance);
        ImageView ivServiceItemRight = baseViewHolder.getView(R.id.ivServiceItemRight);

        CheckBox chkTaskAllElection = baseViewHolder.getView(R.id.chkTaskAllElection);

        //标题
        tvItemServiceName.setText(orderServiceItem.getServiceText());
        //图片
        GlideManager.loadImage(mContext, orderServiceItem.getIconUrl(), ivItemServiceLogo , R.drawable.ic_required_options);
        //项目金额
        tvItemServiceMoney.setText(mContext.getString(R.string.content_money, String.valueOf(orderServiceItem.getAmount())));
        //项目数量
        tvServiceItemNumber.setText(mContext.getString(R.string.number, String.valueOf(orderServiceItem.getNumber())));
        //项目总金额
        double multiply = ShopCarUtil.getTotalMoneys(orderServiceItem.getNumber(),
                orderServiceItem.getAmount(), orderServiceItem.getPrice2(), orderServiceItem.getPrice3(), 0);
        tvItemServiceTotalMoney.setText(mContext.getString(R.string.content_money , String.valueOf(multiply)));

        int adapterPosition = baseViewHolder.getAdapterPosition();
        if (adapterPosition == 0){
            tvOrderDetailMaintenance.setVisibility(View.GONE);
            ivServiceItemRight.setVisibility(View.GONE);
            //必选项
            // slottingStartLength 开槽  slottingEndLength
            //开槽 调试 辅材
            TextView tvSlotting = baseViewHolder.getView(R.id.tvSlotting);
            TextView tvDebugging = baseViewHolder.getView(R.id.tvDebugging);
            TextView tvAuxiliaryMaterials = baseViewHolder.getView(R.id.tvAuxiliaryMaterials);
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
            chkTaskAllElection.setVisibility(View.GONE);
        }else {
            ivServiceItemRight.setVisibility(View.VISIBLE);
            chkTaskAllElection.setVisibility(View.VISIBLE);
            tvOrderDetailMaintenance.setVisibility(View.VISIBLE);
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
        }

        chkTaskAllElection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                orderServiceItem.setCheck(isChecked);
            }
        });
    }
}
