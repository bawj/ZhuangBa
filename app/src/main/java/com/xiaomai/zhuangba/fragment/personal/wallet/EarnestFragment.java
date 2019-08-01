package com.xiaomai.zhuangba.fragment.personal.wallet;

import android.os.Bundle;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.EarnestBean;
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
        try {
            tvEarnestMoney.setText(getString(R.string.content_money, bean.getAmount()));
            StringBuilder builder = new StringBuilder();
            String[] strs = bean.getTimes().split("T");
            String[] ds = strs[0].split("-");
            String[] hs = strs[1].split("\\.");
            hs = hs[0].split(":");
            builder.append(ds[0])
                    .append("年")
                    .append(ds[1])
                    .append("月")
                    .append(ds[2])
                    .append("日")
                    .append(hs[0])
                    .append("点")
                    .append(hs[1])
                    .append("分");
            tvEarnestHint.setText(String.format(getString(R.string.wallet_earnest_return_hint),
                    builder.toString(),
                    bean.getAmount()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tvReturn)
    public void onReturnClick(){
        startFragmentAndDestroyCurrent(EarnestPasswordFragment.newInstance());
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
