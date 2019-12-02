package com.xiaomai.zhuangba.fragment.orderdetail.master.base;

import android.text.TextUtils;
import android.widget.TextView;

import com.example.toollib.util.AmountUtil;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.OrdersEnum;
import com.xiaomai.zhuangba.fragment.authentication.master.IDCardScanningFragment;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.BaseOrderDetailFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.CompleteFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.module.IMasterOrderDetailModule;
import com.xiaomai.zhuangba.fragment.orderdetail.master.module.IMasterOrderDetailView;
import com.xiaomai.zhuangba.fragment.orderdetail.master.module.MasterOrderDetailModule;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/7/15 0015
 *
 * 师傅端订单详情 base
 */
public class BaseMasterOrderDetailFragment extends BaseOrderDetailFragment<IMasterOrderDetailModule> implements IMasterOrderDetailView {

    /**
     * 订单中总金额¥4000.00，您当前分成比例为80% 只有师傅端显示
     */
    @BindView(R.id.tvBaseOrderDetailDivideIntoColumns)
    TextView tvBaseOrderDetailDivideIntoColumns;
    /**
     * 总金额
     */
    @BindView(R.id.tvBaseOrderDetailTotalMoney)
    TextView tvBaseOrderDetailTotalMoney;

    @Override
    public int getContentView() {
        return R.layout.fragment_base_master_order_detail;
    }

    @Override
    public void masterOngoingOrdersListSuccess(OngoingOrdersList ongoingOrdersList) {
        //师傅得到的金额
        String assigner = ongoingOrdersList.getAssigner();
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        String phoneNumber = unique.getPhoneNumber();

        String percentage;
        if (TextUtils.isEmpty(assigner) || phoneNumber.equals(assigner)){
            tvBaseOrderDetailTotalMoney.setText(getString(R.string.content_money, String.valueOf(ongoingOrdersList.getMasterOrderAmount())));
            //总金额
            percentage = AmountUtil.percentage(ongoingOrdersList.getMasterOrderAmount(),
                    DensityUtils.stringTypeDouble(String.valueOf(ongoingOrdersList.getOrderAmount())));
            percentage = getString(R.string.proportion, String.valueOf(ongoingOrdersList.getOrderAmount()), percentage) + "%";
        }else {
            tvBaseOrderDetailTotalMoney.setText(getString(R.string.asterisk));
            percentage = getString(R.string.proportion_, getString(R.string.asterisk), getString(R.string.asterisk));
        }
        tvBaseOrderDetailDivideIntoColumns.setText(percentage);
    }

    @Override
    protected IMasterOrderDetailModule initModule() {
        return new MasterOrderDetailModule();
    }

    @Override
    public void cancelOrderSuccess() {
        //取消订单
        startFragment(MasterWorkerFragment.newInstance());
    }

    @Override
    public void receiptOrderSuccess() {
        //接单成功
        startFragment(CompleteFragment.newInstance(getOrderCode() , getOrderType() , String.valueOf(OrdersEnum.MASTER_PENDING_DISPOSAL.getCode())));
    }

    @Override
    public void goAuthentication() {
        //去认证
        startFragment(IDCardScanningFragment.newInstance());
    }
}
