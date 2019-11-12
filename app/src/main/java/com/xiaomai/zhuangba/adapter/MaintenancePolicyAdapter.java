package com.xiaomai.zhuangba.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.MaintenancePolicyBean;
import com.xiaomai.zhuangba.util.DateUtil;
import com.xiaomai.zhuangba.util.GuaranteeUtil;
import com.xiaomai.zhuangba.util.MaintenanceUtil;

/**
 * @author Administrator
 * @date 2019/8/8 0008
 * <p>
 * 维保单
 */
public class MaintenancePolicyAdapter extends BaseQuickAdapter<MaintenancePolicyBean, BaseViewHolder> {

    private IMaintenance iMaintenance;

    public MaintenancePolicyAdapter() {
        super(R.layout.item_maintenance_policy, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MaintenancePolicyBean item) {
        //维保 名称
        TextView tvItemMaintenanceTitle = helper.getView(R.id.tvItemMaintenanceTitle);
        tvItemMaintenanceTitle.setText(item.getServiceName());
        //维保状态
        TextView tvItemMaintenanceType = helper.getView(R.id.tvItemMaintenanceType);

        String status = item.getStatus();
        GuaranteeUtil.guaranteeStatus(mContext,status , tvItemMaintenanceType);
        //MaintenanceUtil.maintenanceType(mContext, item.getEndTime(), tvItemMaintenanceType);

        //维保时长
        TextView tvChargeMaintenance = helper.getView(R.id.tvChargeMaintenance);
        tvChargeMaintenance.setText(mContext.getString(R.string.maintenance_time, String.valueOf(item.getNumber())));
        //客户姓名
        TextView tvCustomerName = helper.getView(R.id.tvCustomerName);
        tvCustomerName.setText(item.getOvermanName());
        //联系方式
        TextView tvContactInformation = helper.getView(R.id.tvContactInformation);
        tvContactInformation.setText(item.getOvermanPhone());
        //结束时间
        TextView tvEndingTime = helper.getView(R.id.tvEndingTime);
        tvEndingTime.setText(DateUtil.dateToFormat(item.getEndTime() , "yyyy-MM-dd" , "yyyy/MM/dd"));
        //维保总价
        TextView tvTotalMaintenancePrice = helper.getView(R.id.tvTotalMaintenancePrice);
        tvTotalMaintenancePrice.setText(mContext.getString(R.string.total_maintenance_price, String.valueOf(item.getAmount())));
        //续维保
        QMUIButton btnContinuedMaintenance = helper.getView(R.id.btnContinuedMaintenance);
        btnContinuedMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iMaintenance != null) {
                    iMaintenance.continuedMaintenance(item);
                }
            }
        });

    }

    public interface IMaintenance {
        void continuedMaintenance(MaintenancePolicyBean item);
    }

    public void setMaintenance(IMaintenance iMaintenance) {
        this.iMaintenance = iMaintenance;
    }

}
