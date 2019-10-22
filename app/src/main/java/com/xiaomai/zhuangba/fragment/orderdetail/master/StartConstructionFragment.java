package com.xiaomai.zhuangba.fragment.orderdetail.master;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.function.BaseHttpConsumer;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.base.BaseAutographFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.MapUtils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 * <p>
 * 开始施工
 */
public class StartConstructionFragment extends BaseAutographFragment {

    /**
     * 定位点
     */
    @BindView(R.id.tvStartConstructionLocation)
    TextView tvStartConstructionLocation;

    public static StartConstructionFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        StartConstructionFragment fragment = new StartConstructionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        MapUtils.location(getActivity(), tvStartConstructionLocation);
    }

///    /**
//     * 去掉签名图片 提交
//     */
//    @Override
//    public void beforeInstallation() {
//        List<Uri> uriList = new ArrayList<>(mediaSelectorFiles);
//        uriList.remove(uriList.size() - 1);
//        if (uriList.isEmpty()) {
//            ToastUtil.showShort(getString(R.string.job_site_font_installation_img));
//        } else if (TextUtils.isEmpty(descriptionPhotoPath)) {
//            ToastUtil.showShort(getString(R.string.please_sign_electronically));
//        } else {
//            try {
//                //安装前 图片集合
//                List<MultipartBody.Part> parts = new ArrayList<>();
//                for (int i = 0; i < uriList.size(); i++) {
//                    Uri compressPath = uriList.get(i);
//                    File file = new File(new URI(compressPath.toString()));
//                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                    MultipartBody.Part body = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
//                    parts.add(body);
//                }
//                Observable<HttpResult<Object>> responseBodyObservable = RxUtils.getObservableZip(
//                        ServiceUrl.getUserApi().uploadFiles(parts).subscribeOn(Schedulers.io()));
//
//                //单图上传
//                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//                File file = new File(new URI(Uri.fromFile(new File(descriptionPhotoPath)).toString()));
//                builder.addFormDataPart("file", file.getName(),
//                        RequestBody.create(MediaType.parse("multipart/form-data"), file));
//                Observable<HttpResult<Object>> uploadFileObservable = RxUtils.getObservableZip(
//                        ServiceUrl.getUserApi().uploadFile(builder.build()).subscribeOn(Schedulers.io()));
//
//                DialogUtil.getTipLoading(getActivity() , "").show();
//                Observable<Object> zip = Observable.zip(responseBodyObservable, uploadFileObservable,
//                        new BiFunction<HttpResult<Object>, HttpResult<Object>, Object>() {
//                            @Override
//                            public Object apply(HttpResult<Object> httpResult, HttpResult<Object> httpResult2) throws Exception {
//                                StartConstructionBean startConstructionBean = new StartConstructionBean();
//                                startConstructionBean.setMultiGraph(httpResult.getData().toString());
//                                startConstructionBean.setSingleGraph(httpResult2.getData().toString());
//                                return startConstructionBean;
//                            }
//                        }).compose(this.bindToLifecycle());
//                BaseHttpZipRxObserver.getInstance().httpZipObserver(zip, new BaseCallback() {
//                    @Override
//                    public void onSuccess(Object obj) {
//                        StartConstructionBean startConstructionBean = (StartConstructionBean) obj;
//                        submit(startConstructionBean);
//                    }
//
//                    @Override
//                    public void onFail(Object obj) {
//                        super.onFail(obj);
//                        ToastUtil.showShort(obj.toString());
//                        DialogUtil.tipLoadingDismiss();
//                    }
//                });
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void submit(StartConstructionBean startConstructionBean) {
//        RxUtils.getObservable(ServiceUrl.getUserApi().startTaskOrder(getOrderCode(), startConstructionBean.getMultiGraph()
//                , startConstructionBean.getSingleGraph())).compose(this.<HttpResult<Object>>bindToLifecycle())
//                .subscribe(new BaseHttpRxObserver<Object>() {
//                    @Override
//                    protected void onSuccess(Object response) {
//                        DialogUtil.tipLoadingDismiss();
//                        //跳转到待开工
//                        startFragment(ToBeStartedFragment.newInstance(getOrderCode(), getOrderType()));
//                        popBackStack();
//                    }
//                    @Override
//                    public void onError(ApiException e) {
//                        super.onError(e);
//                        DialogUtil.tipLoadingDismiss();
//                    }
//                });
//   }


    @Override
    public void beforeInstallation() {
        //提交
        List<Uri> uriList = new ArrayList<>(mediaSelectorFiles);
        uriList.remove(uriList.size() - 1);
        if (uriList.isEmpty()) {
            ToastUtil.showShort(getString(R.string.job_site_font_installation_img));
        } /*else if (TextUtils.isEmpty(descriptionPhotoPath)) {
            ToastUtil.showShort(getString(R.string.please_sign_electronically));
        }*/ else {
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
                RxUtils.getObservable(responseBodyObservable)
                        .compose(this.<HttpResult<Object>>bindToLifecycle())
                        .doOnNext(new BaseHttpConsumer<Object>() {
                            @Override
                            public void httpConsumerAccept(HttpResult<Object> httpResult) {
                            }
                        })
                        .concatMap(new Function<HttpResult<Object>, ObservableSource<HttpResult<Object>>>() {
                            @Override
                            public ObservableSource<HttpResult<Object>> apply(HttpResult<Object> httpResult) throws Exception {
                                return RxUtils.getObservable(ServiceUrl.getUserApi().startTaskOrder(getOrderCode(),
                                        httpResult.getData().toString(), tvStartConstructionLocation.getText().toString()));
                            }
                        })
                        .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                            @Override
                            protected void onSuccess(Object object) {
                                //跳转到待开工
                                startFragmentAndDestroyCurrent(MasterWorkerFragment.newInstance());
                                //startFragmentAndDestroyCurrent(ToBeStartedFragment.newInstance(getOrderCode(), getOrderType()));
                            }
                        });

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    private String getOrderCode() {
        if (getArguments() != null) {
            return getArguments().getString(ConstantUtil.ORDER_CODE);
        }
        return "";
    }

    private String getOrderType() {
        if (getArguments() != null) {
            return getArguments().getString(ConstantUtil.ORDER_TYPE);
        }
        return "";
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.startup_confirmation);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_start_construction;
    }

    @Override
    public String getAutographImgTip() {
        return getString(R.string.job_site_font_installation_img);
    }

    @Override
    public String getAutographServiceTip() {
        return getString(R.string.agree_to_start_work);
    }


    @Override
    public void onStop() {
        super.onStop();
        MapUtils.stopLocation();
    }
}
