package com.xiaomai.zhuangba.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.InspectionSheetDetailBean;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.PatrolStatusUtil;

/**
 * @author Administrator
 * @date 2019/10/8 0008
 */
public class InspectionSheetDetailAdapter extends BaseQuickAdapter<InspectionSheetDetailBean , BaseViewHolder> {

    public InspectionSheetDetailAdapter() {
        super(R.layout.item_inspection_sheet_detail, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectionSheetDetailBean inspectionSheetDetailBean) {
        //标题
        TextView tvItemInspectionSheetTitle = helper.getView(R.id.tvItemInspectionSheetTitle);
        tvItemInspectionSheetTitle.setText(mContext.getString(R.string.patrol_title));
        //订单状态
        TextView tvItemInspectionSheetType = helper.getView(R.id.tvItemInspectionSheetType);

        PatrolStatusUtil.masterStatus(mContext , inspectionSheetDetailBean.getOrderStatus() , tvItemInspectionSheetType);

        //设备编号
        TextView tvItemInspectionSheetTime = helper.getView(R.id.tvItemInspectionSheetTime);
        tvItemInspectionSheetTime.setText(mContext.getString(R.string.device_number , inspectionSheetDetailBean.getName()));
        //服务时长
        TextView tvItemPatrolTime = helper.getView(R.id.tvItemPatrolTime);
        //巡查时长
        int debugging = inspectionSheetDetailBean.getDebugging();
        tvItemPatrolTime.setText(mContext.getString(R.string.length_of_inspection, String.valueOf(debugging)));
        //服务日期
        TextView tvItemInspectionDate = helper.getView(R.id.tvItemInspectionDate);
        //巡查时长 单位 month 月  week周
        String slottingStartLength = inspectionSheetDetailBean.getSlottingStartLength();
        //巡查日期
        String slottingEndLength = inspectionSheetDetailBean.getSlottingEndLength();
        if (slottingStartLength.equals(ConstantUtil.MONTH)) {
            tvItemInspectionDate.setText(mContext.getString(R.string.inspection_date, slottingEndLength));
        } else if (slottingStartLength.equals(ConstantUtil.WEEK)) {
            tvItemInspectionDate.setText(mContext.getString(R.string.inspection_date_week, slottingEndLength));
        }
        //A B C D 面
        TextView tvItemPatrolNoodles = helper.getView(R.id.tvItemPatrolNoodles);
        //巡查区域
        String telephone = inspectionSheetDetailBean.getTelephone();
        if (!TextUtils.isEmpty(telephone)){
            telephone = inspectionSheetDetailBean.getTelephone();
            tvItemPatrolNoodles.setText(telephone);
        }
        //地址
        TextView tvItemInspectionSheetLocation = helper.getView(R.id.tvItemInspectionSheetLocation);
        tvItemInspectionSheetLocation.setText(inspectionSheetDetailBean.getAddress());

        //金额
        TextView tvItemInspectionSheetMoney = helper.getView(R.id.tvItemInspectionSheetMoney);
        tvItemInspectionSheetMoney.setText(mContext.getString(R.string.content_money , String.valueOf(inspectionSheetDetailBean.getOrderAmount())));

        tvItemInspectionSheetTitle.setTag(inspectionSheetDetailBean);
    }
}
