package com.xiaomai.zhuangba.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OrderServiceItem;

/**
 * @author Administrator
 * @date 2019/8/8 0008
 */
public class BaseContinuedMaintenanceAdapter extends BaseQuickAdapter<OrderServiceItem, BaseViewHolder> {


    private IBaseContinuedAdapter iBaseContinuedAdapter;

    public BaseContinuedMaintenanceAdapter() {
        super(R.layout.item_base_continued_maintenance, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderServiceItem item) {
        ImageView ivServiceImg = helper.getView(R.id.ivServiceImg);
        GlideManager.loadImage(mContext, item.getIconUrl(), ivServiceImg);

        //项目名称
        TextView tvServiceName = helper.getView(R.id.tvServiceName);
        tvServiceName.setText(item.getServiceText());
        //项目维保金额 和 数量
        TextView tvShopCarMaintenance = helper.getView(R.id.tvShopCarMaintenance);

        int monthNumber = item.getMonthNumber();
        //维保服务 价格 和 数量  维保服务 箭头
        if (monthNumber == 0) {
            // 没有选择维保
            tvShopCarMaintenance.setText(mContext.getString(R.string.no_maintenance));
        } else {
            //有选择维保
            tvShopCarMaintenance.setText(mContext.getString(R.string.shop_car_maintenance
                    , String.valueOf(item.getMonthNumber()), String.valueOf(item.getMaintenanceAmount())));
        }
        final LinearLayout layShopCarMaintenance = helper.getView(R.id.layShopCarMaintenance);
        layShopCarMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iBaseContinuedAdapter != null) {
                    iBaseContinuedAdapter.layShopCarMaintenanceClick(item);
                }
            }
        });

    }

    public interface IBaseContinuedAdapter {
        void layShopCarMaintenanceClick(OrderServiceItem item);
    }

    public void setLayShopCarMaintenanceClick(IBaseContinuedAdapter iBaseContinuedAdapter) {
        this.iBaseContinuedAdapter = iBaseContinuedAdapter;
    }

}
