package com.xiaomai.zhuangba.fragment.masterworker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.Log;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.GuaranteeAdapter;
import com.xiaomai.zhuangba.data.OuterLayerMaintenanceOverman;
import com.xiaomai.zhuangba.data.bean.MaintenanceOverman;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.GuaranteeUtil;

import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/11/7 0007
 * 维保单
 */
public class GuaranteeFragment extends BaseListFragment<IBaseModule , GuaranteeAdapter> {

    @BindView(R.id.tvGuaranteeNumber)
    TextView tvGuaranteeNumber;
    @BindView(R.id.tvGuaranteeGrossIncome)
    TextView tvGuaranteeGrossIncome;

    private GuaranteeAdapter guaranteeAdapter;

    public static GuaranteeFragment newInstance() {
        Bundle args = new Bundle();
        GuaranteeFragment fragment = new GuaranteeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        Log.e("维保单刷新");
        getMasterMaintenanceOrderList();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        getMasterMaintenanceOrderList();
    }

    private void getMasterMaintenanceOrderList() {
        Log.e("维保单刷新");
        RxUtils.getObservable(ServiceUrl.getUserApi().getMasterMaintenanceOrderList(String.valueOf(getPage())
                , String.valueOf(StaticExplain.PAGE_NUM.getCode())))
                .compose(this.<HttpResult<OuterLayerMaintenanceOverman>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<OuterLayerMaintenanceOverman>() {
                    @Override
                    protected void onSuccess(OuterLayerMaintenanceOverman outerLayerMaintenanceOverman) {
                        setGuaranteeNumberGrossIncome(outerLayerMaintenanceOverman);
                        List<MaintenanceOverman> list = outerLayerMaintenanceOverman.getList().getList();
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            guaranteeAdapter.addData(list);
                        } else {
                            //刷新
                            guaranteeAdapter.setNewData(list);
                            finishRefresh();
                        }
                        if (list.size() < StaticExplain.PAGE_NUM.getCode()) {
                            //加载结束
                            loadMoreEnd();
                        } else {
                            //加载完成
                            loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(ApiException apiException) {
                        super.onError(apiException);
                        finishRefresh();
                        loadError();
                    }
                });
    }

    private void setGuaranteeNumberGrossIncome(OuterLayerMaintenanceOverman outerLayerMaintenanceOverman) {
        double num = outerLayerMaintenanceOverman.getNum();
        double amount = outerLayerMaintenanceOverman.getAmount();
        if (num == 0){
            tvGuaranteeNumber.setVisibility(View.GONE);
            tvGuaranteeGrossIncome.setVisibility(View.GONE);
        }else {
            tvGuaranteeNumber.setText(getString(R.string.total_quantity , String.valueOf(num)));
            tvGuaranteeGrossIncome.setText(getString(R.string.gross_income , String.valueOf(amount)));
            tvGuaranteeNumber.setVisibility(View.VISIBLE);
            tvGuaranteeGrossIncome.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        MaintenanceOverman maintenanceOverman = (MaintenanceOverman) view.findViewById(R.id.tvItemOrdersMoney).getTag();
        String orderCode = maintenanceOverman.getOrderCode();
        String orderType = maintenanceOverman.getOrderType();
        String status = maintenanceOverman.getStatus();
        GuaranteeUtil.startGuaranteeOrderDetail(getBaseFragmentActivity() , orderCode , orderType , status);
    }


    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == ForResultCode.RESULT_OK.getCode()){
            if (resultCode == ForResultCode.START_FOR_RESULT_CODE.getCode())     {
                refresh();
            }
        }
    }

    @Override
    public GuaranteeAdapter getBaseListAdapter() {
        guaranteeAdapter = new GuaranteeAdapter();
        return guaranteeAdapter;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_guarantee;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

}
