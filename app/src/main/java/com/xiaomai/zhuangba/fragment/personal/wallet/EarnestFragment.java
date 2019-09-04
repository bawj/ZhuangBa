package com.xiaomai.zhuangba.fragment.personal.wallet;

import android.os.Bundle;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.EarnestBean;
import com.xiaomai.zhuangba.http.ServiceUrl;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 */
public class EarnestFragment extends BaseFragment {


    @BindView(R.id.tv_earnest_money)
    TextView tvEarnestMoney;
    @BindView(R.id.tv_earnest_hint)
    TextView tvEarnestHint;

    public static EarnestFragment newInstance() {
        Bundle args = new Bundle();
        EarnestFragment fragment = new EarnestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        Observable<HttpResult<EarnestBean>> observable = ServiceUrl.getUserApi().getEarnest();
        RxUtils.getObservable(observable)
                .compose(this.<HttpResult<EarnestBean>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<EarnestBean>(getActivity()) {
                    @Override
                    protected void onSuccess(EarnestBean response) {
                        earnestSuccess(response);
                    }
                });
    }

    public void earnestSuccess(EarnestBean bean) {
        tvEarnestMoney.setText(getString(R.string.content_money, bean.getAmount()));
        tvEarnestHint.setText(String.format(getString(R.string.wallet_earnest_return_hint),
                bean.getTimes(), bean.getAmount()));
    }

    @OnClick(R.id.tvReturn)
    public void onReturnClick(){
        startFragment(EarnestPasswordFragment.newInstance());
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_earnest;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.wallet_earnest_money);
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }
}
