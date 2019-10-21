package com.xiaomai.zhuangba.fragment.authentication.master;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpZipRxObserver;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.application.PretendApplication;
import com.xiaomai.zhuangba.data.bean.ImgUrl;
import com.xiaomai.zhuangba.data.bean.MasterAuthenticationInfo;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.FileUtil;
import com.xiaomai.zhuangba.util.RxPermissionsUtils;
import com.xiaomai.zhuangba.util.Util;

import java.io.File;
import java.net.URI;

import activity.IDCardRecognitionActivity;
import butterknife.BindView;
import butterknife.OnClick;
import exocr.exocrengine.EXOCRModel;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static fragment.IdCardRecognitionFragment.RESULT;
import static fragment.IdCardRecognitionFragment.RESULT_CODE;

/**
 * @author Administrator
 * @date 2019/8/23 0023
 *
 * 身份证扫描
 */
public class IDCardScanningFragment extends BaseFragment {

    @BindView(R.id.ivAuthenticationFont)
    ImageView ivAuthenticationFont;
    @BindView(R.id.ivAuthenticationBack)
    ImageView ivAuthenticationBack;
    @BindView(R.id.btnAuthenticationNext)
    Button btnAuthenticationNext;

    /**
     * 身份证正面
     */
    private String absolutePath;
    /**
     * 身份证反面
     */
    private String absoluteBlackPath;
    /** 姓名 */
    private String name;
    /** 身份证号码 */
    private String cardnum;
    /** 身份证过期时间 */
    private String idCardDate;

    public static IDCardScanningFragment newInstance() {
        Bundle args = new Bundle();
        IDCardScanningFragment fragment = new IDCardScanningFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.layClickIdCard, R.id.layClickIdCardBack, R.id.btnAuthenticationNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layClickIdCard:
                RxPermissionsUtils.applyPermission(getActivity(), new BaseCallback<String>() {
                    @Override
                    public void onSuccess(String obj) {
                        // TODO: 2019/10/14 0014 身份证
                        Intent scanIntent = new Intent(getActivity(), IDCardRecognitionActivity.class);
                        scanIntent.putExtra(IDCardRecognitionActivity.FRONT, true);
                        startActivityForResult(scanIntent, ForResultCode.START_FOR_RESULT_CODE.getCode());
                    }

                    @Override
                    public void onFail(Object obj) {
                        showToast(getString(R.string.please_open_permissions));
                    }
                }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.layClickIdCardBack:
                RxPermissionsUtils.applyPermission(getActivity(), new BaseCallback<String>() {
                    @Override
                    public void onSuccess(String obj) {
                        Intent scanIntent = new Intent(getActivity(), IDCardRecognitionActivity.class);
                        scanIntent.putExtra(IDCardRecognitionActivity.FRONT, false);
                        startActivityForResult(scanIntent, ForResultCode.START_FOR_RESULT_CODE_.getCode());
                    }

                    @Override
                    public void onFail(Object obj) {
                        showToast(getString(R.string.please_open_permissions));
                    }
                }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.btnAuthenticationNext:
                next();
                break;
            default:
        }
    }

    private void next(){
        if (TextUtils.isEmpty(absolutePath) || TextUtils.isEmpty(absoluteBlackPath)){
            ToastUtil.showShort(getString(R.string.please_upload_id_card));
        }else {
            //身份证正面
            try {
                Uri parse = Uri.parse("file:///" + absolutePath);
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                File file = new File(new URI(parse.toString()));
                builder.addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file));
                Observable<HttpResult<Object>> httpResultObservable = ServiceUrl.getUserApi().uploadFile(builder.build());


                //身份证反面
                Uri back = Uri.parse("file:///" + absoluteBlackPath);
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
                        }).compose(this.bindToLifecycle());
                BaseHttpZipRxObserver instance = BaseHttpZipRxObserver.getInstance();
                instance.setContext(getActivity());
                instance.httpZipObserver(zip, new BaseCallback() {
                    @Override
                    public void onSuccess(Object obj) {
                        ImgUrl imgUrl = (ImgUrl) obj;
                        MasterAuthenticationInfo masterAuthenticationInfo = new MasterAuthenticationInfo();
                        masterAuthenticationInfo.setUserText(name);
                        masterAuthenticationInfo.setValidityData(idCardDate);
                        masterAuthenticationInfo.setIdentityCard(cardnum);
                        masterAuthenticationInfo.setIdCardFrontPhoto(imgUrl.getFrontPhoto());
                        masterAuthenticationInfo.setIdCardBackPhoto(imgUrl.getIdCardBackPhoto());
                        startFragment(RealAuthenticationFragment.newInstance(new Gson().toJson(masterAuthenticationInfo)));
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE) {
            if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode() && null != data) {
                final EXOCRModel result = (EXOCRModel) data.getSerializableExtra(RESULT);
                this.name = result.name;
                this.cardnum = result.cardnum;

                String absolutePath = FileUtil.getSaveFile(PretendApplication.getInstance()).getAbsolutePath();

                byte[] decode = Base64.decode(result.base64bitmap, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
                FileUtil.saveBitmap(absolutePath, bitmap);
                this.absolutePath = absolutePath;
                ivAuthenticationFont.setImageBitmap(bitmap);

            } else if (requestCode == ForResultCode.START_FOR_RESULT_CODE_.getCode() && null != data) {
                final EXOCRModel result = (EXOCRModel) data.getSerializableExtra(RESULT);
                byte[] decode = Base64.decode(result.base64bitmap, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
                String absolutePath = FileUtil.getSaveFile(PretendApplication.getInstance()).getAbsolutePath();
                FileUtil.saveBitmap(absolutePath, bitmap);
                absoluteBlackPath = absolutePath;
                ivAuthenticationBack.setImageBitmap(bitmap);
                String[] valDate = Util.getValDate(result.validdate);
                if (valDate != null && valDate.length > 1) {
                    idCardDate = getString(R.string.id_card_date, Util.getDate(valDate[0]), Util.getDate(valDate[1]));
                }
            }
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_id_card_scanning;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.real_authentication);
    }
}
