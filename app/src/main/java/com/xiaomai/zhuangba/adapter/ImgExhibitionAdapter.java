package com.xiaomai.zhuangba.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 */
public class ImgExhibitionAdapter extends BaseQuickAdapter<String , BaseViewHolder> {

    public ImgExhibitionAdapter() {
        super(R.layout.item_img_exhibition, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView ivItemImgExhibition = helper.getView(R.id.ivItemImgExhibition);
        GlideManager.loadImage(mContext , item , ivItemImgExhibition);
    }
}
