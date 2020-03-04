package com.xiaomai.zhuangba.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.ServiceSampleEntity;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 */
public class NearbyPhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public NearbyPhotoAdapter() {
        super(R.layout.item_img_exhibition, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, String url) {
        ImageView ivItemImgExhibition = helper.getView(R.id.ivItemImgExhibition);
        GlideManager.loadImage(mContext , url , ivItemImgExhibition);
    }
}
