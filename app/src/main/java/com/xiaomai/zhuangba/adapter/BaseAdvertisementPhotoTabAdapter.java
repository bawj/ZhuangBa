package com.xiaomai.zhuangba.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.ServiceSampleEntity;

public class BaseAdvertisementPhotoTabAdapter extends BaseQuickAdapter<ServiceSampleEntity, BaseViewHolder> {

    private int checkPosition;

    public BaseAdvertisementPhotoTabAdapter() {
        super(R.layout.item_base_advertisement_photo_tab, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceSampleEntity serviceSampleEntity) {
        ImageView ivBaseAdvertisementPhotoTab = helper.getView(R.id.ivBaseAdvertisementPhotoTab);
        String url = serviceSampleEntity.getPicUrl();
        if (!TextUtils.isEmpty(url)){
            GlideManager.loadImage(mContext , url ,ivBaseAdvertisementPhotoTab , R.drawable.ic_notice_img_add);
        }
        TextView tvPhotoTapExplain = helper.getView(R.id.tvPhotoTapExplain);
        String explain = serviceSampleEntity.getAdverName();
        tvPhotoTapExplain.setText(explain);
        int adapterPosition = helper.getAdapterPosition();
        if (checkPosition == adapterPosition){
            ivBaseAdvertisementPhotoTab.setBackground(mContext.getResources().getDrawable(R.drawable.red_bg));
        }else {
            ivBaseAdvertisementPhotoTab.setBackground(mContext.getResources().getDrawable(R.drawable.transparent));
        }
    }

    public void setCheckPosition(int checkPosition){
        this.checkPosition = checkPosition;
        notifyDataSetChanged();
    }
}
