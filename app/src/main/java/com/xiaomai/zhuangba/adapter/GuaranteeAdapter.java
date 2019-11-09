package com.xiaomai.zhuangba.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.MaintenanceOverman;
import com.xiaomai.zhuangba.util.DateUtil;
import com.xiaomai.zhuangba.util.GuaranteeUtil;

import org.joda.time.DateTime;

/**
 * @author Administrator
 * @date 2019/11/7 0007
 */
public class GuaranteeAdapter extends BaseQuickAdapter<MaintenanceOverman, BaseViewHolder> {

    public GuaranteeAdapter() {
        super(R.layout.item_guarantee, null);
    }

    @Override
    protected void convert(BaseViewHolder holder, MaintenanceOverman maintenanceOverman) {
        //标题
        TextView tvItemOrdersTitle = holder.getView(R.id.tvItemOrdersTitle);
        tvItemOrdersTitle.setText(maintenanceOverman.getServiceName());
        //订单状态
        TextView tvItemOrdersType = holder.getView(R.id.tvItemOrdersType);
        String status = maintenanceOverman.getStatus();
        GuaranteeUtil.guaranteeStatus(mContext, status, tvItemOrdersType);

        //收费维保
        TextView tvTollMaintenance = holder.getView(R.id.tvTollMaintenance);
        tvTollMaintenance.setText(mContext.getString(R.string.toll_maintenance, String.valueOf(maintenanceOverman.getNumber())));
        //客户姓名
        TextView tvName = holder.getView(R.id.tvName);
        tvName.setText(mContext.getString(R.string.customer_name_order, maintenanceOverman.getEmployerName()));
        //联系方式
        TextView tvPhone = holder.getView(R.id.tvPhone);
        tvPhone.setText(mContext.getString(R.string.contact_information_order, maintenanceOverman.getEmployerPhone()));
        //结束时间
        TextView tvEndTime = holder.getView(R.id.tvEndTime);
        //结束时间
        String endTime = maintenanceOverman.getEndTime();
        //入账时间
        TextView tvAccountingTime = holder.getView(R.id.tvAccountingTime);
        //入账时间
        String accountingTime;
        if (!TextUtils.isEmpty(endTime)) {
            endTime = DateUtil.dateToFormat(endTime, "yyyy-MM-dd", "yyyy/MM/dd");
            //往后推两天 为入账时间
            DateTime dateTime = DateUtil.strToDate(endTime, "yyyy/MM/dd");
            dateTime = dateTime.plusDays(2);
            int dayOfYear = dateTime.getDayOfMonth();
            accountingTime = mContext.getString(R.string.accounting_time, String.valueOf(dayOfYear));
        } else {
            endTime = "--";
            accountingTime = mContext.getString(R.string.accounting_time_null, "--");
        }
        tvEndTime.setText(mContext.getString(R.string.end_time, endTime));
        tvAccountingTime.setText(accountingTime);
        //维保总计
        TextView tvItemOrdersMoney = holder.getView(R.id.tvItemOrdersMoney);
        tvItemOrdersMoney.setText(String.valueOf(maintenanceOverman.getAmount()));

        tvItemOrdersMoney.setTag(maintenanceOverman);
    }
}
