package com.xiaomai.zhuangba.http;

import com.example.toollib.http.HttpResult;
import com.xiaomai.zhuangba.data.bean.AliPayAccountBean;
import com.xiaomai.zhuangba.data.bean.DeliveryContent;
import com.xiaomai.zhuangba.data.bean.EarnestBean;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderDateList;
import com.xiaomai.zhuangba.data.bean.OrderServiceItem;
import com.xiaomai.zhuangba.data.bean.OrderStatistics;
import com.xiaomai.zhuangba.data.bean.Orders;
import com.xiaomai.zhuangba.data.bean.PayData;
import com.xiaomai.zhuangba.data.bean.PayDepositBean;
import com.xiaomai.zhuangba.data.bean.ProvincialBean;
import com.xiaomai.zhuangba.data.bean.ServiceData;
import com.xiaomai.zhuangba.data.bean.ServiceSubcategory;
import com.xiaomai.zhuangba.data.bean.SkillList;
import com.xiaomai.zhuangba.data.bean.StatisticsData;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.bean.WalletBean;
import com.xiaomai.zhuangba.data.bean.WalletDetailBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @POST("user/getRole")
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
     * 订单池
     *
     * @param pageNum  页码
     * @param pageSize 一页显示多少条
     * @param address  地址
     * @return Observable
     */
    @GET("order/selectOrder")
    Observable<HttpResult<Orders>> getMasterSelectOrder(@Query("pageNum") String pageNum, @Query("pageSize") String pageSize , @Query("address") String address);

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
     * 订单池详情
     * @param orderCode 订单编号
     * @return observable
     */
    @GET("order/getOrderByOrderCode")
    Observable<HttpResult<OngoingOrdersList>> getOrderByOrderCode(@Query("orderCode") String orderCode);

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
     *
     * @param pageNum  当前页
     * @param pageSize 一页的数量
     * @return observable
     */
    @GET("order/getOrderList")
    Observable<HttpResult<Orders>> getOrderList(@Query("pageNum") String pageNum, @Query("pageSize") String pageSize);


    /**
     * 根据大类ID查询小类，根据服务小类查询服务项目
     *
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
     *
     * @param body body
     * @return observable
     */
    @POST("order/updateOrder")
    Observable<HttpResult<Object>> updateOrder(@Body RequestBody body);


    /**
     * 微信和支付宝支付
     * 订单支付
     *
     * @param orderCode 订单编号
     * @param payType   支付类型
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
     *
     * @return observable
     */
    @GET("order/getStatisticalData")
    Observable<HttpResult<StatisticsData>> getStatisticalData();

    /**
     * 获取钱包数据
     *
     * @return observable
     */
    @POST("wallet/getWallet")
    Observable<HttpResult<WalletBean>> getWallet();


    /**
     * 获取钱包明细
     *
     * @param pageNum 每页显示数量
     * @param pageSize 每页显示
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return observable
     */
    @GET("wallet/getWalletDetailed")
    Observable<HttpResult<WalletDetailBean>> getWalletDetail(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize,
                                                             @Query("startDate") String startDate, @Query("endDate") String endDate);

    /**
     * 获取收入列表
     *
     * @param pageNum 每页显示数量
     * @param pageSize 每页显示
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return observable
     */
    @GET("wallet/getIncomeList")
    Observable<HttpResult<WalletDetailBean>> getIncomeList(@Query("pageNum") int pageNum,
                                                           @Query("pageSize") int pageSize,
                                                           @Query("startDate") String startDate,
                                                           @Query("endDate") String endDate);

    /**
     * 获取已提现列表
     *
     * @param pageNum 每页显示数量
     * @param pageSize 每页显示
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return observable
     */
    @GET("wallet/getHaveWithdrawal")
    Observable<HttpResult<WalletDetailBean>> getHaveWithdrawal(@Query("pageNum") int pageNum,
                                                               @Query("pageSize") int pageSize,
                                                               @Query("startDate") String startDate,
                                                               @Query("endDate") String endDate);

    /**
     * 设置交易密码
     *
     * @param password 密码
     * @return observable
     */
    @FormUrlEncoded
    @POST("wallet/updatePresentationPassword")
    Observable<HttpResult<String>> setTradePassword(@Field("presentationPassword") String password);


    /**
     * 提现
     *
     * @param body body
     * @return observable
     */
    @POST("wallet/cashWithdrawal")
    Observable<HttpResult<String>> cashWithdrawal(@Body RequestBody body);


    /**
     * 获取支付宝账号
     * @return observable
     */
    @GET("wallet/getCashWithdrawalAccount")
    Observable<HttpResult<List<AliPayAccountBean>>> getAliPayAccount();


    /**
     * 添加支付宝账号
     *
     * @param body body
     * @return observable
     */
    @POST("wallet/addCashWithdrawalAccount")
    Observable<HttpResult<String>> addAccount(@Body RequestBody body);


    /**
     * 获取押金数据
     *
     * @return observable
     */
    @GET("wallet/inquiryMargin")
    Observable<HttpResult<EarnestBean>> getEarnest();


    /**
     * 退押金
     * @param password 密码
     * @return observable
     */
    @FormUrlEncoded
    @POST("user/refundDeposit")
    Observable<HttpResult<String>> returnEarnest(@Field("password") String password);

    /**
     * 查询保证金的金额
     *
     * @param flag flag
     * @return observable
     */
    @FormUrlEncoded
    @POST("user/getBond")
    Observable<HttpResult<List<PayDepositBean>>> getBond(@Field("flag") String flag);

    /**
     * 微信和支付宝支付
     * 缴纳保证金
     *
     * @param orderAmount 支付金额
     * @param payType     支付类型
     * @return observable
     */
    @FormUrlEncoded
    @POST("user/payDeposit")
    Observable<HttpResult<PayData>> payDeposit(@Field("orderAmount") String orderAmount, @Field("payType") String payType);

    /**
     * 查询用户
     *
     * @return observable
     */
    @GET("user/getUser")
    Observable<HttpResult<UserInfo>> getUser();

    /**
     * 查询价目表
     * @param enumCode String
     * @return observable
     */
    @GET("communal/getEnumerate")
    Observable<HttpResult<String>> getEnumerate(@Query("enumCode") String enumCode);


    /**
     * 单图上传
     *
     * @param body body
     * @return Observable
     */
    @POST("ftp/uploadFile")
    Observable<HttpResult<Object>> uploadFile(@Body RequestBody body);

    /**
     * 多图上传
     *
     * @param parts parts
     * @return Observable
     */
    @Multipart
    @POST("ftp/uploadFiles")
    Observable<HttpResult<Object>> uploadFiles(@Part() List<MultipartBody.Part> parts);

    /**
     * 技能列表
     *
     * @return observable
     */
    @GET("skill/getSkillList")
    Observable<HttpResult<List<SkillList>>> getSkillList();

    /**
     * 请求省市区
     * @param parentId parentId
     * @param level level
     * @return observable
     */
    @GET("communal/getRegion")
    Observable<HttpResult<List<ProvincialBean>>> getRegion(@Query("parentId") int parentId , @Query("level") int level);


    /**
     * 提交身份认证
     *
     * @param requestBody body
     * @return observable
     */
    @POST("user/updateRegistrationInformation")
    Observable<HttpResult<UserInfo>> updateRegistrationInformation(@Body RequestBody requestBody);


    /**
     * 修改服务范围
     * @param requestBody body
     * @return observable
     */
    @POST("user/userAuthenticationAddress")
    Observable<HttpResult<Object>> userAuthenticationAddress(@Body RequestBody requestBody);

    /**
     * 修改开工中和休息中
     *
     * @param status 状态 1 工作 2 休息
     * @return observable
     */
    @GET("user/updateStatus")
    Observable<HttpResult<Object>> updateStatus(@Query("status") String status);


    /**
     * 师傅取消任务
     *
     * @param orderCode        订单编号
     * @param cancellationCauses 取消说明
     * @return observable
     */
    @GET("order/masterCancelOrder/{orderCode}")
    Observable<HttpResult<Object>> masterCancelOrder(@Path("orderCode") String orderCode, @Query("cancellationCauses") String cancellationCauses);

    /**
     * 雇主取消任务
     *
     * @param orderCode        订单编号
     * @return observable
     */
    @GET("order/cancelOrder/{orderCode}")
    Observable<HttpResult<Object>> cancelOrder(@Path("orderCode") String orderCode);

    /**
     * 修改订单 地址
     *
     * @param body body
     * @return observable
     */
    @POST("order/updateOrderAddress")
    Observable<HttpResult<String>> updateOrderAddress(@Body RequestBody body);


    /**
     * 师傅接受任务
     *
     * @param orderCode        订单编号
     * @return observable
     */
    @GET("order/acceptOrder/{orderCode}")
    Observable<HttpResult<Object>> acceptOrder(@Path("orderCode") String orderCode);


    /**
     * 师傅确认订单时间
     *
     * @param orderCode    订单编号
     * @param confirmationTime 时间
     * @return observable
     */
    @GET("order/confirmationOrder/{orderCode}")
    Observable<HttpResult<Object>> getConfirmationOrder(@Path("orderCode") String orderCode, @Query("confirmationTime") String confirmationTime);


    /**
     * 现在出发
     *
     * @param orderCode 订单编号
     * @return observable
     */
    @GET("order/nowWeLeave/{orderCode}")
    Observable<HttpResult<Object>> nowWeLeave(@Path("orderCode") String orderCode);


    /**
     * 开始施工
     * @param orderCode 订单编号
     * @param beforePicturesUrl 多图
     * @param electronicSignature 单图
     * @return observable
     */
    @GET("order/startTaskOrder/{orderCode}")
    Observable<HttpResult<Object>> startTaskOrder(@Path("orderCode") String orderCode,
                                                  @Query("beforePicturesUrl") String beforePicturesUrl, @Query("electronicSignature") String electronicSignature);

    /**
     * 师傅提交验证
     *
     * @param requestBody body
     * @return observable
     */
    @POST("order/submitValidation")
    Observable<HttpResult<Object>> submitValidation(@Body RequestBody requestBody);
}
