package com.xiaomai.zhuangba.http;

import com.example.toollib.http.HttpResult;
import com.xiaomai.zhuangba.data.DeliveryContent;
import com.xiaomai.zhuangba.data.OngoingOrdersList;
import com.xiaomai.zhuangba.data.OrderDateList;
import com.xiaomai.zhuangba.data.OrderServiceItem;
import com.xiaomai.zhuangba.data.OrderStatistics;
import com.xiaomai.zhuangba.data.Orders;
import com.xiaomai.zhuangba.data.PayData;
import com.xiaomai.zhuangba.data.ServiceData;
import com.xiaomai.zhuangba.data.ServiceSubcategory;
import com.xiaomai.zhuangba.data.StatisticsData;
import com.xiaomai.zhuangba.data.UserInfo;
import com.xiaomai.zhuangba.data.WalletBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Administrator
 * @date 2019/5/7 0007
 */
public interface IApi {

    /**
     * 登录地址拦截时使用
     */
    public static final String LOGIN = "userLogin/login";

    /**
     * 登录
     *
     * @param phoneNumber 手机号
     * @param password    密码
     * @return observable
     */
    @FormUrlEncoded
    @POST(LOGIN)
    Observable<HttpResult<UserInfo>> login(@Field("phoneNumber") String phoneNumber,
                                           @Field("password") String password);

    /**
     * 获取验证码
     *
     * @param phoneNumber 手机号
     * @param type        type
     * @return Observable
     */
    @FormUrlEncoded
    @POST("userLogin/getAuthenticationCode")
    Observable<HttpResult<Object>> getAuthenticationCode(@Field("phoneNumber") String phoneNumber,
                                                         @Field("type") String type);

    /**
     * 注册
     *
     * @param phoneNumber 手机号
     * @param password    密码
     * @return Observable
     */
    @FormUrlEncoded
    @POST("userLogin/register")
    Observable<HttpResult<Object>> register(@Field("phoneNumber") String phoneNumber,
                                            @Field("password") String password);


    /**
     * 修改密码
     *
     * @param phoneNumber 手机号
     * @param password    密码
     * @return Observable
     */
    @FormUrlEncoded
    @POST("userLogin/forgetPassword")
    Observable<HttpResult<Object>> forgetPassword(@Field("phoneNumber") String phoneNumber,
                                                  @Field("password") String password);


    /**
     * 选择角色
     *
     * @param role 0:师傅;1:雇主
     * @return observable
     */
    @FormUrlEncoded
    @POST("user/selectionRole")
    Observable<HttpResult<UserInfo>> role(@Field("role") String role);


    /**
     * 登出
     *
     * @return observable
     */
    @POST("userLogin/loginOut")
    Observable<HttpResult<String>> logout();


    /**
     * 跳过此版本
     *
     * @param versionId 版本 versionCode
     * @return observable
     */
    @FormUrlEncoded
    @POST("userLogin/skipVersion")
    Observable<HttpResult<Object>> skipVersion(@Field("versionId") int versionId);


    /**
     * 请求服务大类
     *
     * @return observable
     */
    @GET("userRole/getServiceCategory")
    Observable<HttpResult<List<ServiceData>>> getServiceCategory();


    /**
     * 订单首页统计雇主和师傅
     *
     * @return observable
     */
    @GET("order/getOrderStatistics")
    Observable<HttpResult<OrderStatistics>> getOrderStatistics();

    /**
     * 师傅新任务订单首页
     *
     * @param pageNum  页码
     * @param pageSize 一页显示多少条
     * @return Observable
     */
    @GET("order/getMasterOrderList")
    Observable<HttpResult<Orders>> getMasterOrderList(@Query("pageNum") String pageNum, @Query("pageSize") String pageSize);

    /**
     * 师傅需处理订单首页
     *
     * @param pageNum  页码
     * @param pageSize 一页显示多少条
     * @return Observable
     */
    @GET("order/getMasterHandleOrderList")
    Observable<HttpResult<Orders>> getMasterHandleOrderList(@Query("pageNum") String pageNum, @Query("pageSize") String pageSize);


    /**
     * 订单详情
     *
     * @param orderCode 订单code
     * @return observable
     */
    @GET("order/getOrderDetails/{orderCode}")
    Observable<HttpResult<OngoingOrdersList>> getOrderDetails(@Path("orderCode") String orderCode);

    /**
     * 查询服务项目
     *
     * @param orderCode 订单code
     * @return observable
     */
    @GET("order/getOrderServiceList/{orderCode}")
    Observable<HttpResult<List<OrderServiceItem>>> getOrderServiceList(@Path("orderCode") String orderCode);

    /**
     * 查询订单时间信息
     *
     * @param orderCode 订单code
     * @return observable
     */
    @GET("order/getOrderDateList/{orderCode}")
    Observable<HttpResult<List<OrderDateList>>> getOrderDateList(@Path("orderCode") String orderCode);


    /**
     * 任务开始前的现场照 和 任务开始完成后的现场照 雇主的评价
     * 返回 list集合 下标0 开始前 1完成后
     * 有评价不是null
     *
     * @param orderCode 订单code
     * @return observable
     */
    @GET("order/getOrderValidation/{orderCode}")
    Observable<HttpResult<List<DeliveryContent>>> getOrderValidation(@Path("orderCode") String orderCode);


    /**
     * 雇主端订单列表
     * @param pageNum  当前页
     * @param pageSize 一页的数量
     * @return observable
     */
    @GET("order/getOrderList")
    Observable<HttpResult<Orders>> getOrderList(@Query("pageNum") String pageNum, @Query("pageSize") String pageSize);


    /**
     * 根据大类ID查询小类，根据服务小类查询服务项目
     * @param serviceId 服务大类ID
     * @return observable
     */
    @GET("userRole/getServiceSubcategory/{serviceId}")
    Observable<HttpResult<List<ServiceSubcategory>>> getServiceSubcategory(@Path("serviceId") String serviceId);


    /**
     * 提交订单
     *
     * @param body body
     * @return observable
     */
    @POST("order/generateOrder")
    Observable<HttpResult<String>> postGenerateOrder(@Body RequestBody body);

    /**
     * 修改订单
     * @param body body
     * @return observable
     */
    @POST("order/updateOrder")
    Observable<HttpResult<Object>> updateOrder(@Body RequestBody body);


    /**
     * 微信和支付宝支付
     * 订单支付
     * @param orderCode 订单编号
     * @param payType     支付类型
     * @return observable
     */
    @FormUrlEncoded
    @POST("order/orderPay")
    Observable<HttpResult<PayData>> orderPay(@Field("orderCode") String orderCode, @Field("payType") String payType);


    /**
     * 请求历史订单
     *
     * @param pageNum  页码
     * @param pageSize 每页显示数量
     * @return observable
     */
    @GET("order/getHistoryOrderList")
    Observable<HttpResult<Orders>> getHistoryOrderList(@Query("pageNum") String pageNum, @Query("pageSize") String pageSize);

    /**
     * 查询平台的师傅数量，订单数量，订单金额
     * @return observable
     */
    @GET("order/getStatisticalData")
    Observable<HttpResult<StatisticsData>> getStatisticalData();

    /**
     * 获取钱包数据
     *
     * @return
     */
    @POST("wallet/getWallet")
    Observable<HttpResult<WalletBean>> getWallet();
}
