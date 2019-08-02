package com.xiaomai.zhuangba.data.module.authentication;

import android.net.Uri;
import android.text.TextUtils;

import com.example.toollib.data.BaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpZipRxObserver;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.ImgUrl;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
        } else {
            //上传正面图片
            try {
                //身份证正面
                Uri parse = Uri.parse("file:///" + idCardFrontPhoto);
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                File file = new File(new URI(parse.toString()));
                builder.addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file));
                Observable<HttpResult<Object>> httpResultObservable = ServiceUrl.getUserApi().uploadFile(builder.build());


                //身份证反面
                Uri back = Uri.parse("file:///" + idCardBackPhoto);
                MultipartBody.Builder builderBack = new MultipartBody.Builder().setType(MultipartBody.FORM);
                File fileBack = new File(new URI(back.toString()));
                builderBack.addFormDataPart("file", fileBack.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file));

                Observable<HttpResult<Object>> httpResultObservableBack = ServiceUrl.getUserApi().uploadFile(builderBack.build());
                Observable<Object> zip = Observable.zip(httpResultObservable, httpResultObservableBack
                        , new BiFunction<HttpResult<Object>, HttpResult<Object>, Object>() {
                            @Override
                            public Object apply(HttpResult<Object> httpResult, HttpResult<Object> httpResult2) throws Exception {
                                ImgUrl imgUrl = new ImgUrl();
                                imgUrl.setFrontPhoto(httpResult.getData().toString());
                                imgUrl.setIdCardBackPhoto(httpResult2.getData().toString());
                                return imgUrl;
                            }
                        }).compose(mViewRef.get().bindLifecycle());
                BaseHttpZipRxObserver instance = BaseHttpZipRxObserver.getInstance();
                instance.setContext(mContext.get());
                instance.httpZipObserver(zip, new BaseCallback() {
                    @Override
                    public void onSuccess(Object obj) {
                        ImgUrl imgUrl = (ImgUrl) obj;
                        mViewRef.get().uploadSuccess(imgUrl);
                    }
                });
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
