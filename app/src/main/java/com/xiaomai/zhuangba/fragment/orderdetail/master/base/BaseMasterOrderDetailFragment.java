package com.xiaomai.zhuangba.fragment.orderdetail.master.base;

import android.widget.TextView;

import com.example.toollib.util.AmountUtil;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.module.orderdetail.IOrderDetailModule;
import com.xiaomai.zhuangba.data.module.orderdetail.OrderDetailModule;
import com.xiaomai.zhuangba.enums.OrdersEnum;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
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
        tvBaseOrderDetailTotalMoney.setText(getString(R.string.content_money, ongoingOrdersList.getMasterOrderAmount()));
        //总金额
        String percentage = AmountUtil.percentage(DensityUtils.stringTypeDouble(ongoingOrdersList.getMasterOrderAmount()),
                DensityUtils.stringTypeDouble(String.valueOf(ongoingOrdersList.getOrderAmount())));
        String s = getString(R.string.proportion, String.valueOf(ongoingOrdersList.getOrderAmount()), percentage) + "%";
        tvBaseOrderDetailDivideIntoColumns.setText(s);
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
        startFragment(CompleteFragment.newInstance(getOrderCode()));
    }
}
