package com.xiaomai.zhuangba.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OrderServiceCondition;

/**
 * @author Bawj
 * CreateDate:     2020/3/12 0012 09:08
 */
public class EmployerAddProjectAdapter extends BaseQuickAdapter<OrderServiceCondition , BaseViewHolder> {

    public EmployerAddProjectAdapter() {
        super(R.layout.item_employer_add_project, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderServiceCondition item) {
        ImageView ivItemServiceLogo = helper.getView(R.id.ivItemServiceLogo);
        GlideManager.loadImage(mContext , item.getIconUrl() , ivItemServiceLogo , R.drawable.ic_required_options);
        TextView tvItemServiceName = helper.getView(R.id.tvItemServiceName);
        tvItemServiceName.setText(item.getServiceText());

        //数量 x 单价
        TextView tvItemServiceMoney = helper.getView(R.id.tvItemServiceMoney);
        TextView tvServiceItemNumber = helper.getView(R.id.tvServiceItemNumber);

        //金额
        TextView tvItemServiceTotalMoney = helper.getView(R.id.tvItemServiceTotalMoney);
        tvItemServiceTotalMoney.setText(mContext.getString(R.string.content_money , String.valueOf(item.getAmount())));

        int adapterPosition = helper.getAdapterPosition();
        //必选项
        if (adapterPosition == 0){
            tvItemServiceMoney.setVisibility(View.GONE);
            tvServiceItemNumber.setVisibility(View.GONE);
            //开槽
            TextView tvSlotting = helper.getView(R.id.tvSlotting);
            String slottingStartLength = item.getSlottingStartLength();
            String slottingEndLength = item.getSlottingEndLength();
            if (TextUtils.isEmpty(slottingStartLength) && TextUtils.isEmpty(slottingEndLength)){
                tvSlotting.setVisibility(View.GONE);
            }else {
                tvSlotting.setVisibility(View.VISIBLE);
                tvSlotting.setText(mContext.getString(R.string.slotting_start_end_length_service_text , slottingStartLength , slottingEndLength));
            }
            //辅材
            TextView tvAuxiliaryMaterials = helper.getView(R.id.tvAuxiliaryMaterials);
            String materialsStartLength = item.getMaterialsStartLength();
            String materialsEndLength = item.getMaterialsEndLength();
            if (TextUtils.isEmpty(materialsStartLength) && TextUtils.isEmpty(materialsEndLength)){
                tvAuxiliaryMaterials.setVisibility(View.GONE);
            }else {
                tvAuxiliaryMaterials.setVisibility(View.VISIBLE);
                tvAuxiliaryMaterials.setText(mContext.getString(R.string.materials_start_end_length_service_text , materialsStartLength , materialsEndLength));
            }
        }else {
            tvItemServiceMoney.setVisibility(View.VISIBLE);
            tvServiceItemNumber.setVisibility(View.VISIBLE);
            tvItemServiceMoney.setText(mContext.getString(R.string.content_money , String.valueOf(item.getPrice())));
            tvServiceItemNumber.setText(mContext.getString(R.string.number , String.valueOf(item.getNumber())));
        }
    }
}
