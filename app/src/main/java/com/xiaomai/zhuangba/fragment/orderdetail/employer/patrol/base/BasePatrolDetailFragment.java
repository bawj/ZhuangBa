package com.xiaomai.zhuangba.fragment.orderdetail.employer.patrol.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomai.zhuangba.R;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/10/7 0007
 */
public class BasePatrolDetailFragment<T extends IBasePatrolModule> extends BaseFragment<T>
        implements OnRefreshListener, IBasePatrolView{
    /** 标题 */
    @BindView(R.id.tvBasePatrolTaskTitle)
    TextView tvBasePatrolTaskTitle;
    /** 订单状态 */
    @BindView(R.id.tvBasePatrolOrdersType)
    TextView tvBasePatrolOrdersType;
    /** 设备编号 */
    @BindView(R.id.tvBasePatrolEquipmentNumber)
    TextView tvBasePatrolEquipmentNumber;
    /** 价格 */
    @BindView(R.id.tvBaseOrderDetailTotalMoney)
    TextView tvBaseOrderDetailTotalMoney;
    /** 巡查区域 */
    @BindView(R.id.tvBasePatrolArea)
    TextView tvBasePatrolArea;
    /** 订单编号 */
    @BindView(R.id.tvBasePatrolOrderNumber)
    TextView tvBasePatrolOrderNumber;
    /** 服务时长 */
    @BindView(R.id.tvBasePatrolLengthOfService)
    TextView tvBasePatrolLengthOfService;
    /** 服务时间 */
    @BindView(R.id.tvBasePatrolLengthOfServiceTime)
    TextView tvBasePatrolLengthOfServiceTime;
    /** 备注说明 */
    @BindView(R.id.tvBasePatrolNotes)
    TextView tvBasePatrolNotes;
    /** 地址 */
    @BindView(R.id.tvBaseOrderDetailLocation)
    TextView tvBaseOrderDetailLocation;
    /** 订单信息 */
    @BindView(R.id.recyclerOrderInformation)
    RecyclerView recyclerOrderInformation;

    @Override
    protected T initModule() {
        return (T) new BasePatrolModule();
    }

    @Override
    public void initView() {

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_base_patrol_detail;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.order_detail);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        iModule.requestPatrolDetail();
    }
}
