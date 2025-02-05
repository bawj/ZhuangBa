package com.xiaomai.zhuangba.fragment.orderdetail.master;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.observer.BaseHttpZipRxObserver;
import com.example.toollib.http.util.DialogUtil;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.StartConstructionBean;
import com.xiaomai.zhuangba.fragment.orderdetail.master.base.BaseAutographFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 * <p>
 * 验收确认
 */
public class NewSubmitAcceptanceFragment extends BaseAutographFragment {

    public static NewSubmitAcceptanceFragment newInstance(String orderCode) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        NewSubmitAcceptanceFragment fragment = new NewSubmitAcceptanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 提交
     */
    @Override
    public void beforeInstallation() {
        List<Uri> uriList = new ArrayList<>(mediaSelectorFiles);
        uriList.remove(uriList.size() - 1);
        if (uriList.isEmpty()) {
            ToastUtil.showShort(getString(R.string.job_site_font_installation_img));
        } else if (TextUtils.isEmpty(descriptionPhotoPath)) {
            ToastUtil.showShort(getString(R.string.please_sign_electronically));
        } else {
            try {
                //安装前 图片集合
                List<MultipartBody.Part> parts = new ArrayList<>();
                for (int i = 0; i < uriList.size(); i++) {
                    Uri compressPath = uriList.get(i);
                    File file = new File(new URI(compressPath.toString()));
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
                    parts.add(body);
                }
                Observable<HttpResult<Object>> responseBodyObservable = RxUtils.getObservableZip(
                        ServiceUrl.getUserApi().uploadFiles(parts).subscribeOn(Schedulers.io()));

                //单图上传
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                File file = new File(new URI(Uri.fromFile(new File(descriptionPhotoPath)).toString()));
                builder.addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file));
                Observable<HttpResult<Object>> uploadFileObservable = RxUtils.getObservableZip(
                        ServiceUrl.getUserApi().uploadFile(builder.build()).subscribeOn(Schedulers.io()));

                DialogUtil.getTipLoading(getActivity(), "").show();
                Observable<Object> zip = Observable.zip(responseBodyObservable, uploadFileObservable,
                        new BiFunction<HttpResult<Object>, HttpResult<Object>, Object>() {
                            @Override
                            public Object apply(HttpResult<Object> httpResult, HttpResult<Object> httpResult2) throws Exception {
                                StartConstructionBean startConstructionBean = new StartConstructionBean();
                                startConstructionBean.setMultiGraph(httpResult.getData().toString());
                                startConstructionBean.setSingleGraph(httpResult2.getData().toString());
                                return startConstructionBean;
                            }
                        }).compose(this.bindToLifecycle());
                BaseHttpZipRxObserver.getInstance().httpZipObserver(zip, new BaseCallback() {
                    @Override
                    public void onSuccess(Object obj) {
                        StartConstructionBean startConstructionBean = (StartConstructionBean) obj;
                        submit(startConstructionBean);
                    }

                    @Override
                    public void onFail(Object obj) {
                        super.onFail(obj);
                        DialogUtil.tipLoadingDismiss();
                    }
                });
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void submit(StartConstructionBean startConstructionBean) {
        HashMap<String, Object> hashMap = new HashMap<>();
        //现场负责人签名图片
        hashMap.put("electronicSignature", startConstructionBean.getSingleGraph());
        //订单编号
        hashMap.put("orderCode", getOrderCode());
        //图片地址
        hashMap.put("picturesUrl", startConstructionBean.getMultiGraph());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
        RxUtils.getObservable(ServiceUrl.getUserApi().submitValidation(requestBody)).compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>() {
                    @Override
                    protected void onSuccess(Object response) {
                        DialogUtil.tipLoadingDismiss();
                        startFragment(NewSubmitCompleteFragment.newInstance(getOrderCode()));
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        DialogUtil.tipLoadingDismiss();
                    }
                });
    }

    private String getOrderCode() {
        if (getArguments() != null) {
            return getArguments().getString(ConstantUtil.ORDER_CODE);
        }
        return "";
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.acceptance_confirmation);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_new_submit_acceptance;
    }

    @Override
    public String getAutographImgTip() {
        return getString(R.string.job_site_font_installation_img);
    }

    @Override
    public String getAutographServiceTip() {
        return getString(R.string.agree_to_start_work);
    }

}
