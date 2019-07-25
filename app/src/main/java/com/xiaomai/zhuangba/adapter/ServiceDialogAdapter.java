package com.xiaomai.zhuangba.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.ServiceData;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 */
public class ServiceDialogAdapter extends BaseQuickAdapter<ServiceData , BaseViewHolder> {

    public ServiceDialogAdapter(int layoutResId, @Nullable List<ServiceData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceData item) {
        ImageView ivServiceIcon = helper.getView(R.id.ivServiceIcon);
        TextView tvServiceName = helper.getView(R.id.tvServiceName);
        tvServiceName.setText(item.getServiceText());
        GlideManager.loadCircleImage(mContext , item.getIconUrl() , ivServiceIcon);
        tvServiceName.setTag(item);
    }
}
