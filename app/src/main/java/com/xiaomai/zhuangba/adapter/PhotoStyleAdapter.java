package com.xiaomai.zhuangba.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.BaseAdvertisementPhotoTabEntity;

public class PhotoStyleAdapter extends BaseQuickAdapter<BaseAdvertisementPhotoTabEntity, BaseViewHolder> {

    public PhotoStyleAdapter() {
        super(R.layout.item_photo_style, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseAdvertisementPhotoTabEntity baseAdvertisementPhotoTabEntity) {
        ImageView ivBaseAdvertisementPhotoTab = helper.getView(R.id.ivBaseAdvertisementPhotoTab);
        String url = baseAdvertisementPhotoTabEntity.getUrl();
        if (!TextUtils.isEmpty(url)){
            GlideManager.loadImage(mContext , url ,ivBaseAdvertisementPhotoTab);
        }
        TextView tvPhotoTapExplain = helper.getView(R.id.tvPhotoTapExplain);
        String explain = baseAdvertisementPhotoTabEntity.getExplain();
        tvPhotoTapExplain.setText(explain);
    }
}
