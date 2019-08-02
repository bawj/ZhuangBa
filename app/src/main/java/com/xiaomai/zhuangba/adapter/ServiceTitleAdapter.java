package com.xiaomai.zhuangba.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.ServiceSubcategory;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 */
public class ServiceTitleAdapter extends BaseQuickAdapter<ServiceSubcategory, BaseViewHolder> {

    private String serviceText;

    public ServiceTitleAdapter() {
        super(R.layout.item_service_title, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceSubcategory item) {
        View viewServiceTitleAdapterLine = helper.getView(R.id.viewServiceTitleAdapterLine);
        TextView tvServiceTitleAdapterName = helper.getView(R.id.tvServiceTitleAdapterName);
        RelativeLayout relServiceTitleAdapter = helper.getView(R.id.relServiceTitleAdapter);
        if (!serviceText.equals(item.getServiceText())) {
            viewServiceTitleAdapterLine.setVisibility(View.GONE);
            relServiceTitleAdapter.setBackgroundResource(R.color.tool_lib_gray_F1F1F1);
            tvServiceTitleAdapterName.setTextColor(mContext.getResources().getColor(R.color.tool_lib_gray_777777));
        } else {
            viewServiceTitleAdapterLine.setVisibility(View.VISIBLE);
            relServiceTitleAdapter.setBackgroundColor(Color.WHITE);
            tvServiceTitleAdapterName.setTextColor(Color.BLACK);
        }
        tvServiceTitleAdapterName.setText(item.getServiceText());
        tvServiceTitleAdapterName.setTag(item);
    }

    public void setItemChecked(String serviceText) {
        this.serviceText = serviceText;
        notifyDataSetChanged();
    }


}
