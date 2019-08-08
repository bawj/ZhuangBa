package com.xiaomai.zhuangba.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.DensityUtils;
import com.qmuiteam.qmui.layout.QMUIButton;
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
        TextView tvServiceContentNumber = helper.getView(R.id.tvServiceContentNumber);
        GlideManager.loadImage(mContext, item.getIconUrl(), ivServiceContentIcon, R.drawable.ic_load_error);
        tvServiceContentName.setText(item.getServiceText());
        tvServiceContentMoney.setText(mContext.getString(R.string.content_money, String.valueOf(item.getPrice())));
        tvServiceContentMoney.setTag(item);
        ShopCarData shopCarData = ShopCarUtil.getShopCarData(item);
        if (shopCarData != null){
            //数量
            String number = shopCarData.getNumber();
            tvServiceContentNumber.setText(number);
            tvServiceContentNumber.setVisibility(View.VISIBLE);
        }else{
            tvServiceContentNumber.setVisibility(View.GONE);
        }
        QMUIButton btnServiceContent = helper.getView(R.id.btnServiceContent);
        btnServiceContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iServiceContentOnAddDelListener != null){
                    //选规格
                    iServiceContentOnAddDelListener.onAddOrDelSuccess(item);
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
         * @param item item
         */
        void onAddOrDelSuccess(ServiceSubcategoryProject item);

    }
}
