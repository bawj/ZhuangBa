package com.xiaomai.zhuangba.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.LocationSearch;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 */
public class LocationAdapter extends BaseQuickAdapter<LocationSearch, BaseViewHolder> {

    public LocationAdapter() {
        super(R.layout.item_inputs, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocationSearch locationSearch) {
        TextView tvItemInputsName = helper.getView(R.id.tvItemInputsName);
        TextView tvItemInputAddress = helper.getView(R.id.tvItemInputAddress);
        TextView tvItemInputsDistance = helper.getView(R.id.tvItemInputsDistance);
        tvItemInputsDistance.setVisibility(View.GONE);

        ImageView ivItemInputsSel = helper.getView(R.id.ivItemInputsSel);
        if (helper.getAdapterPosition() == 0){
            ivItemInputsSel.setVisibility(View.VISIBLE);
        }else{
            ivItemInputsSel.setVisibility(View.GONE);
        }

        tvItemInputsName.setText(locationSearch.getName());
        tvItemInputAddress.setText(locationSearch.getAddress());
        tvItemInputAddress.setTag(locationSearch);
    }
}
