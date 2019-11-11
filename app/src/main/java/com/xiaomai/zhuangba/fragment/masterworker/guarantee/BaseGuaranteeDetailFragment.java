package com.xiaomai.zhuangba.fragment.masterworker.guarantee;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpZipRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.OrderDateListAdapter;
import com.xiaomai.zhuangba.data.bean.GuaranteeAndOrderDate;
import com.xiaomai.zhuangba.data.bean.GuaranteeDeatil;
import com.xiaomai.zhuangba.data.bean.OrderDateList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.DateUtil;
import com.xiaomai.zhuangba.util.GuaranteeUtil;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Administrator
 * @date 2019/11/7 0007
 */
public class BaseGuaranteeDetailFragment extends BaseFragment implements OnRefreshListener {

    @BindView(R.id.tvBaseGuaranteeTitle)
    TextView tvBaseGuaranteeTitle;
    @BindView(R.id.tvBaseGuaranteeType)
    TextView tvBaseGuaranteeType;
    @BindView(R.id.tvBaseGuaranteeName)
    TextView tvBaseGuaranteeName;
    @BindView(R.id.tvBaseGuaranteeMoney)
    TextView tvBaseGuaranteeMoney;
    @BindView(R.id.tvBaseGuaranteeInformation)
    TextView tvBaseGuaranteeInformation;
    @BindView(R.id.tvBaseGuaranteeLengthOfService)
    TextView tvBaseGuaranteeLengthOfService;
    @BindView(R.id.tvBaseGuaranteeEndingTime)
    TextView tvBaseGuaranteeEndingTime;
    @BindView(R.id.tvBaseGuaranteeEquipmentNumber)
    TextView tvBaseGuaranteeEquipmentNumber;
    @BindView(R.id.tvBaseOrderDetailLocation)
    TextView tvBaseOrderDetailLocation;
    @BindView(R.id.recyclerOrderInformation)
    RecyclerView recyclerOrderInformation;
    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshBaseList;
    @BindView(R.id.layGuaranteeDetail)
    LinearLayout layGuaranteeDetail;

    public static final String ORDER_CODE = "order_code";
    public static final String ORDER_TYPE = "order_type";
    /**
     * 订单详细信息
     */
    private OrderDateListAdapter orderDateListAdapter;

    public static BaseGuaranteeDetailFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODE, orderCode);
        args.putString(ORDER_TYPE, orderType);
        BaseGuaranteeDetailFragment fragment = new BaseGuaranteeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        layGuaranteeDetail.setVisibility(View.GONE);
        //订单信息
        recyclerOrderInformation.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderDateListAdapter = new OrderDateListAdapter();
        recyclerOrderInformation.setAdapter(orderDateListAdapter);

        refreshBaseList.setOnRefreshListener(this);
        //默认刷新
        refreshBaseList.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        String orderCode = getOrderCode();
        String orderType = getOrderType();

        //订单时间信息
        Observable<HttpResult<List<OrderDateList>>> orderDateListZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().getOrderDateList(orderCode).subscribeOn(Schedulers.io()));
        //维保单详情
        Observable<HttpResult<GuaranteeDeatil>> httpResultObservable = RxUtils.getObservable(
                ServiceUrl.getUserApi().getMasterMaintenanceOrderDetails(orderCode, orderType)).subscribeOn(Schedulers.io());

        Observable<Object> compose = Observable.zip(httpResultObservable, orderDateListZip
                , new BiFunction<HttpResult<GuaranteeDeatil>, HttpResult<List<OrderDateList>>, Object>() {
                    @Override
                    public Object apply(HttpResult<GuaranteeDeatil> httpResult, HttpResult<List<OrderDateList>> listHttpResult) {
                        GuaranteeAndOrderDate guaranteeAndOrderDate = new GuaranteeAndOrderDate();
                        guaranteeAndOrderDate.setGuaranteeDeatil(httpResult.getData());
                        guaranteeAndOrderDate.setOrderDateListList(listHttpResult.getData());
                        return guaranteeAndOrderDate;
                    }
                }).compose(this.bindToLifecycle());
        BaseHttpZipRxObserver.getInstance().httpZipObserver(compose, new BaseCallback() {
            @Override
            public void onSuccess(Object object) {
                if (object != null){
                    layGuaranteeDetail.setVisibility(View.VISIBLE);
                    GuaranteeAndOrderDate guaranteeAndOrderDate = (GuaranteeAndOrderDate) object;
                    setOrderInfo(guaranteeAndOrderDate.getGuaranteeDeatil());
                    setOrderDateInfo(guaranteeAndOrderDate.getOrderDateListList() , guaranteeAndOrderDate.getGuaranteeDeatil());
                }
                refreshBaseList.finishRefresh();
            }

            @Override
            public void onFail(Object obj) {
                super.onFail(obj);
                refreshBaseList.finishRefresh();
            }
        });
    }

    public void setOrderInfo(GuaranteeDeatil guaranteeDeatil) {
        //设备名称
        String serviceName = guaranteeDeatil.getServiceName();
        tvBaseGuaranteeTitle.setText(serviceName);
        //订单状态
        String status = guaranteeDeatil.getStatus();
        GuaranteeUtil.guaranteeStatus(getActivity(), status, tvBaseGuaranteeType);
        //客户姓名
        String employerName = guaranteeDeatil.getEmployerName();
        tvBaseGuaranteeName.setText(employerName);
        //联系方式
        tvBaseGuaranteeInformation.setText(guaranteeDeatil.getEmployerPhone());
        //服务时长
        tvBaseGuaranteeLengthOfService.setText(getString(R.string.maintenance_time , String.valueOf(guaranteeDeatil.getNumber())));
        //结束时间
        String endTime = guaranteeDeatil.getEndTime();
        if (!TextUtils.isEmpty(endTime)) {
            endTime = DateUtil.dateToFormat(endTime, "yyyy-MM-dd", "yyyy/MM/dd");
        } else {
            endTime = "--";
        }
        tvBaseGuaranteeEndingTime.setText(endTime);
        //设备编号
        String orderType = guaranteeDeatil.getOrderType();
        //如果orderType 是广告单 则 code代表 设备编号
        if (orderType.equals(String.valueOf(StaticExplain.ADVERTISING_BILLS.getCode()))){
            tvBaseGuaranteeEquipmentNumber.setText(String.valueOf(guaranteeDeatil.getCode()));
        }else {
            tvBaseGuaranteeEquipmentNumber.setText("--");
        }
        //项目金额
        tvBaseGuaranteeMoney.setText(getString(R.string.content_money , String.valueOf(guaranteeDeatil.getAmount())));
        //服务地址
        tvBaseOrderDetailLocation.setText(guaranteeDeatil.getAddress());
    }

    private void setOrderDateInfo(List<OrderDateList> orderDateListList , GuaranteeDeatil guaranteeDeatil) {
        //订单时间信息
        orderDateListList.add(0, new OrderDateList(guaranteeDeatil.getCode(), "", getString(R.string.order_code)));
        orderDateListAdapter.setNewData(orderDateListList);
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_cuarantee_detail;
    }

    public String getOrderCode() {
        if (getArguments() != null) {
            return getArguments().getString(ORDER_CODE);
        }
        return "";
    }

    public String getOrderType() {
        if (getArguments() != null) {
            return getArguments().getString(ORDER_TYPE);
        }
        return "";
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.order_detail);
    }

}
