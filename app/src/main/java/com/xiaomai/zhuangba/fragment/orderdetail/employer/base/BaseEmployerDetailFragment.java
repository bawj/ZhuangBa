package com.xiaomai.zhuangba.fragment.orderdetail.employer.base;

import android.text.TextUtils;
import android.widget.TextView;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.ServiceItemsAdapter;
import com.xiaomai.zhuangba.application.PretendApplication;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderServiceItem;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.fragment.employer.EmployerFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.BaseOrderDetailFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.module.EmployerOrderDetailModule;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.module.IEmployerOrderDetailModule;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.module.IEmployerOrderDetailView;

import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 */
public class BaseEmployerDetailFragment extends BaseOrderDetailFragment<IEmployerOrderDetailModule> implements IEmployerOrderDetailView {

    /**
     * 总金额
     */
    @BindView(R.id.tvBaseOrderDetailTotalMoney)
    TextView tvBaseOrderDetailTotalMoney;

    @Override
    public int getContentView() {
        return R.layout.fragment_base_employer_detail;
    }

    @Override
    protected IEmployerOrderDetailModule initModule() {
        return new EmployerOrderDetailModule();
    }

    @Override
    public void cancelOrderSuccess() {
        startFragment(EmployerFragment.newInstance());
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.order_detail);
    }

    @Override
    public void employerOngoingOrdersListSuccess(OngoingOrdersList ongoingOrdersList) {
        super.employerOngoingOrdersListSuccess(ongoingOrdersList);
        //总金额
        tvBaseOrderDetailTotalMoney.setText(getString(R.string.content_money, String.valueOf(ongoingOrdersList.getOrderAmount())));
    }

    @Override
    public boolean isFlag() {
        return true;
    }

    @Override
    public void goAuthentication() {

    }
}
