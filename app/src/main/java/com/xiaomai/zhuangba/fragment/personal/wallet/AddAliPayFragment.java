package com.xiaomai.zhuangba.fragment.personal.wallet;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/7/31 0031
 * 添加支付宝账户
 */
public class AddAliPayFragment extends BaseFragment {

    @BindView(R.id.editAccountName)
    EditText editAccountName;
    @BindView(R.id.editAccountNumber)
    EditText editAccountNumber;

    public static AddAliPayFragment newInstance() {
        Bundle args = new Bundle();
        AddAliPayFragment fragment = new AddAliPayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @OnClick({R.id.tv_add})
    public void onAddClick(View view){
        String name = editAccountName.getText().toString();
        String account = editAccountNumber.getText().toString();
        if (TextUtils.isEmpty(name)){
            ToastUtil.showShort("姓名为空");
        }else if (TextUtils.isEmpty(account)){
            ToastUtil.showShort("账号为空");
        }else {
            HashMap<String , Object> hashMap = new HashMap<>();
            hashMap.put("name", name);
            hashMap.put("withdrawalsAccount", account);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8") , new Gson().toJson(hashMap));
            RxUtils.getObservable(ServiceUrl.getUserApi().addAccount(requestBody))
                    .compose(this.<HttpResult<String>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<String>(getActivity()) {
                        @Override
                        protected void onSuccess(String response) {
                            ToastUtil.showShort("添加成功");
                            setFragmentResult(ForResultCode.RESULT_OK.getCode(),null);
                            popBackStack();
                        }
                    });
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_add_alipay;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.wallet_withdraw_account_add);
    }
}
