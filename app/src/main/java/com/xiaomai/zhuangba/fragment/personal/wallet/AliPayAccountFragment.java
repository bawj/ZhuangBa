package com.xiaomai.zhuangba.fragment.personal.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.AliPayAccountAdapter;
import com.xiaomai.zhuangba.data.bean.AliPayAccountBean;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

import static com.xiaomai.zhuangba.enums.ForResultCode.START_FOR_RESULT_CODE_;

/**
 * @author Administrator
 * @date 2019/7/31 0031
 *
 * 1.提现账号管理 2.选择提现账号
 */
public class AliPayAccountFragment extends BaseFragment implements  BaseQuickAdapter.OnItemClickListener{

    /**
     * 1.提现账号管理 2.选择提现账号
     */
    private int type = 1;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.rl_add)
    RelativeLayout rlAdd;

    private AliPayAccountAdapter adapter;
    private List<AliPayAccountBean> list;

    public static AliPayAccountFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        AliPayAccountFragment fragment = new AliPayAccountFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        list = new ArrayList<>();
        adapter = new AliPayAccountAdapter(null);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        request();
    }

    private void request() {
        Observable<HttpResult<List<AliPayAccountBean>>> observable = ServiceUrl.getUserApi().getAliPayAccount();
        RxUtils.getObservable(observable)
                .compose(this.<HttpResult<List<AliPayAccountBean>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<List<AliPayAccountBean>>(getActivity()) {
                    @Override
                    protected void onSuccess(List<AliPayAccountBean> response) {
                        list = response;
                        adapter.setNewData(list);
                        rlAdd.setVisibility(type == 1 ? View.VISIBLE : View.GONE);
                    }
                });
    }

    @OnClick({R.id.rl_add})
    public void onAddClicked(View view) {
        startFragmentForResult(AddAliPayFragment.newInstance(), ForResultCode.START_FOR_RESULT_CODE.getCode());
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == ForResultCode.RESULT_OK.getCode()){
            if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode()){
                request();
            }
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_alipay_account;
    }

    @Override
    protected String getActivityTitle() {
        type = getArguments().getInt("type");
        return getString(type == 1 ? R.string.wallet_withdraw_account_manager : R.string.wallet_withdraw_account_choose);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (type == 2) {
            Intent bundle = new Intent();
            bundle.putExtra("name", list.get(position).getName());
            bundle.putExtra("account", list.get(position).getWithdrawalsAccount());
            setFragmentResult(START_FOR_RESULT_CODE_.getCode(), bundle);
            popBackStack();
        }
    }

    @Override
    protected void popBackStack() {
        statusBarWhite();
        super.popBackStack();
    }

    @Override
    public boolean isInSwipeBack() {
        statusBarWhite();
        return super.isInSwipeBack();
    }
}
