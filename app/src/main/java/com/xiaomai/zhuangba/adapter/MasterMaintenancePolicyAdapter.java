package com.xiaomai.zhuangba.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toollib.util.AmountUtil;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.MaintenancePolicyBean;
import com.xiaomai.zhuangba.util.DateUtil;
import com.xiaomai.zhuangba.util.MaintenanceUtil;

import org.joda.time.DateTime;

/**
 * @author Administrator
 * @date 2019/8/9 0009
 */
public class MasterMaintenancePolicyAdapter extends BaseQuickAdapter<MaintenancePolicyBean, BaseViewHolder> {

    public MasterMaintenancePolicyAdapter() {
        super(R.layout.item_master_maintenance_policy, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, MaintenancePolicyBean item) {
        //维保 名称
        TextView tvItemMaintenanceTitle = helper.getView(R.id.tvItemMaintenanceTitle);
        tvItemMaintenanceTitle.setText(item.getServiceName());
        //维保状态
        TextView tvItemMaintenanceType = helper.getView(R.id.tvItemMaintenanceType);
        MaintenanceUtil.maintenanceType(mContext, item.getEndTime(), tvItemMaintenanceType);
        //维保时长
        TextView tvChargeMaintenance = helper.getView(R.id.tvChargeMaintenance);
        tvChargeMaintenance.setText(mContext.getString(R.string.maintenance_time, String.valueOf(item.getNumber())));
        //客户姓名
        TextView tvCustomerName = helper.getView(R.id.tvCustomerName);
        tvCustomerName.setText(item.getEmployerName());
        //联系方式
        TextView tvContactInformation = helper.getView(R.id.tvContactInformation);
        tvContactInformation.setText(item.getEmployerPhone());
        //总金额
        TextView tvMoney = helper.getView(R.id.tvMoney);
        tvMoney.setText(mContext.getString(R.string.content_money , item.getAmount()));
        //结束时间
        String endTime = item.getEndTime();
        if (!TextUtils.isEmpty(endTime)){
            TextView tvEndingTime = helper.getView(R.id.tvEndingTime);
            tvEndingTime.setText(DateUtil.dateToFormat(endTime , "yyyy-MM-dd" , "yyyy/MM/dd"));
        }
        //入账时间
        TextView tvAccountingTime = helper.getView(R.id.tvAccountingTime);
        if (!TextUtils.isEmpty(endTime)){
            DateTime dateTime = DateUtil.strToDate(endTime, "yyyy-MM-dd");
            //往后推两天 为入账时间
            dateTime = dateTime.plusDays(2);
            int dayOfYear = dateTime.getDayOfMonth();
            tvAccountingTime.setText(mContext.getString(R.string.accounting_time , String.valueOf(dayOfYear)));
        }
        //入账金额
        TextView tvAccountingAmount = helper.getView(R.id.tvAccountingAmount);
        double earningsMoney = item.getEarningsMoney();
        tvAccountingAmount.setText(mContext.getString(R.string.accounting_amount , String.valueOf(AmountUtil.getDoubleValue(earningsMoney , 2))));
    }
}
