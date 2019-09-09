package com.xiaomai.zhuangba.data.module.authentication;

import android.text.TextUtils;

import com.example.toollib.data.BaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/8/2 0002
 */
public class MasterAuthenticationModule extends BaseModule<IMasterAuthenticationView> implements IMasterAuthenticationModule {


    @Override
    public void requestIdCardImg() {
        //姓名
        String userText = mViewRef.get().getUserText();
        //身份证号码
        String identityCard = mViewRef.get().getIdentityCard();
        //身份证正面 图片地址
        String idCardFrontPhoto = mViewRef.get().getIdCardFrontPhoto();
        //身份证反面 图片地址
        String idCardBackPhoto = mViewRef.get().getIdCardBackPhoto();
        //身份证有效期
        String validityData = mViewRef.get().getValidityData();
        //紧急联系电话
        String emergencyContact = mViewRef.get().getEmergencyContact();

        String address = mViewRef.get().getAddress();

        if (TextUtils.isEmpty(userText)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.user_name_null));
        } else if (TextUtils.isEmpty(identityCard)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.identity_card_null));
        } else if (TextUtils.isEmpty(idCardFrontPhoto)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.id_card_front_photo_null));
        } else if (TextUtils.isEmpty(idCardBackPhoto)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.id_card_back_photo));
        } else if (TextUtils.isEmpty(validityData)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.validity_data));
        } else if (TextUtils.isEmpty(emergencyContact)) {
            ToastUtil.showShort(mContext.get().getString(R.string.phone_number_hint));
        } else if (TextUtils.isEmpty(address)) {
            ToastUtil.showShort(mContext.get().getString(R.string.please_input_address));
        } else {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserText(userText);
            userInfo.setIdentityCard(identityCard);
            userInfo.setValidityData(validityData);
            userInfo.setEmergencyContact(emergencyContact);
            userInfo.setContactAddress(address);
            userInfo.setRole(String.valueOf(StaticExplain.FU_FU_SHI.getCode()));

            userInfo.setIdCardFrontPhoto(idCardFrontPhoto);
            userInfo.setIdCardBackPhoto(idCardBackPhoto);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(userInfo));
            RxUtils.getObservable(ServiceUrl.getUserApi().certification(requestBody))
                    .compose(mViewRef.get().<HttpResult<UserInfo>>bindLifecycle())
                    .subscribe(new BaseHttpRxObserver<UserInfo>(mContext.get()) {
                        @Override
                        protected void onSuccess(UserInfo userInfo1) {
                            DBHelper.getInstance().getUserInfoDao().deleteAll();
                            DBHelper.getInstance().getUserInfoDao().insert(userInfo1);
                            mViewRef.get().uploadSuccess();
                        }
                    });

        }
    }
}
