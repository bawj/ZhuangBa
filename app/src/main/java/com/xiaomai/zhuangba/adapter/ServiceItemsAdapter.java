package com.xiaomai.zhuangba.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OrderServiceItem;
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

        tvItemServiceTotalMoney.setText(mContext.getString(R.string.content_money , String.valueOf(multiply)));
        tvItemServiceTotalMoney.setTag(orderServiceItem);
    }
}
