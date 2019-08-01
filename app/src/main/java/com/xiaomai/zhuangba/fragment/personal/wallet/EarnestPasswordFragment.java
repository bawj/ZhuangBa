package com.xiaomai.zhuangba.fragment.personal.wallet;

import android.os.Bundle;
import android.text.TextUtils;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.MessageEvent;
import com.xiaomai.zhuangba.data.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.EventBusEnum;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.weight.CodeEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 */
public class EarnestPasswordFragment extends BaseFragment {


    @BindView(R.id.codeTxt)
    CodeEditText editText;

    String password = "";

    public static EarnestPasswordFragment newInstance() {
        Bundle args = new Bundle();
        EarnestPasswordFragment fragment = new EarnestPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        editText.setOnTextChangeListener(new CodeEditText.OnTextChangeListener() {
            @Override
            public void onTextChange(String pwd) {
                password = pwd;
            }
        });
    }

    @OnClick(R.id.btnOk)
    public void onOkClick(){
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showShort(getString(R.string.input_wallet_trade_password_null));
        }else{
            Observable<HttpResult<String>> observable = ServiceUrl.getUserApi().returnEarnest(password);
            RxUtils.getObservable(observable)
                    .compose(this.<HttpResult<String>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<String>(getActivity()) {
                        @Override
                        protected void onSuccess(String response) {
                            ToastUtil.showShort(getString(R.string.successful_refund_of_deposit));
                            UserInfo userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
                            userInfo.setMasterRankId(String.valueOf(StaticExplain.OBSERVER.getCode()));
                            DBHelper.getInstance().getUserInfoDao().update(userInfo);
                            EventBus.getDefault().post(new MessageEvent(EventBusEnum.CASH_SUCCESS.getCode()));
                            popBackStack();
                        }
                    });
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_earnest_password;
    }

    @Override
    protected String getActivityTitle() {
        return "";
    }
}
