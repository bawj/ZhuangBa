package com.xiaomai.zhuangba.data.module.verification;

import android.text.TextUtils;

import com.example.toollib.data.BaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.RegexUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.appilcation.PretendApplication;
import com.xiaomai.zhuangba.http.ServiceUrl;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 */
public class VerificationCodeModule<V extends IVerificationCodeView> extends BaseModule<V> implements IVerificationCodeModule<V> {

    private String phone;
    private String verificationCode;
    @Override
    public void getAuthenticationCode(final String phoneNumber, String type) {
        if (TextUtils.isEmpty(phoneNumber)) {
            mViewRef.get().showToast(PretendApplication.getInstance().getString(R.string.please_enter_your_cell_phone_number));
        } else if (!RegexUtils.isMobileSimple(phoneNumber)) {
            mViewRef.get().showToast(PretendApplication.getInstance().getString(R.string.incorrect_format_of_mobile_phone_number));
        } else {
            //开始计时
            RxUtils.getObservable(ServiceUrl.getUserApi().getAuthenticationCode(phoneNumber, type)
                    .compose(mViewRef.get().<HttpResult<Object>>bindLifecycle()))
                    .subscribe(new BaseHttpRxObserver<Object>(mContext.get()) {
                        @Override
                        protected void onSuccess(Object response) {
                            phone = phoneNumber;
                            verificationCode = response.toString();
                            mViewRef.get().startSingleVerificationCode();
                        }
                        @Override
                        public void onError(ApiException e) {
                            super.onError(e);
                            mViewRef.get().retSingleVerificationCode();
                        }
                    });
        }
    }

    @Override
    public void requestProvingVerificationCode(String phoneNumber, String verificationCode) {
        if (TextUtils.isEmpty(verificationCode) || !verificationCode.equals(this.verificationCode)){
            mViewRef.get().showToast(PretendApplication.getInstance().getString(R.string.verification_code_input_incorrectly));
        }else if (TextUtils.isEmpty(phoneNumber) || !this.phone.equals(phoneNumber)){
            mViewRef.get().showToast(PretendApplication.getInstance().getString(R.string.incorrect_format_of_mobile_phone_number));
        }else {
            mViewRef.get().next();
        }
    }
}
