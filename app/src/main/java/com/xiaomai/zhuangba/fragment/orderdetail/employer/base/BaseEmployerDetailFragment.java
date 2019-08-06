package com.xiaomai.zhuangba.fragment.orderdetail.employer.base;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.employer.EmployerFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.BaseOrderDetailFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.module.EmployerOrderDetailModule;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.module.IEmployerOrderDetailModule;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.module.IEmployerOrderDetailView;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 */
public class BaseEmployerDetailFragment extends BaseOrderDetailFragment<IEmployerOrderDetailModule> implements IEmployerOrderDetailView {

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
}
