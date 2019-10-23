package com.xiaomai.zhuangba.http;

import com.example.toollib.http.HttpResult;
import com.xiaomai.zhuangba.data.AdvertisingBillsBean;
import com.xiaomai.zhuangba.data.Patrol;
import com.xiaomai.zhuangba.data.bean.AdvertisingReplacementBean;
import com.xiaomai.zhuangba.data.bean.AdvertisingReplacementDetailBean;
import com.xiaomai.zhuangba.data.bean.AliPayAccountBean;
import com.xiaomai.zhuangba.data.bean.DataDetailsContent;
import com.xiaomai.zhuangba.data.bean.DeliveryContent;
import com.xiaomai.zhuangba.data.bean.EarnestBean;
import com.xiaomai.zhuangba.data.bean.EmployerAdvertisingReplacement;
import com.xiaomai.zhuangba.data.bean.EmployerWalletBean;
import com.xiaomai.zhuangba.data.bean.EmployerWalletDetailBean;
import com.xiaomai.zhuangba.data.bean.FrozenAmountBean;
import com.xiaomai.zhuangba.data.bean.InspectionSheetBean;
import com.xiaomai.zhuangba.data.bean.InspectionSheetDetailBean;
import com.xiaomai.zhuangba.data.bean.Maintenance;
import com.xiaomai.zhuangba.data.bean.MaintenanceBean;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderDateList;
import com.xiaomai.zhuangba.data.bean.OrderServiceItem;
import com.xiaomai.zhuangba.data.bean.OrderStatistics;
import com.xiaomai.zhuangba.data.bean.Orders;
import com.xiaomai.zhuangba.data.bean.PatrolBean;
import com.xiaomai.zhuangba.data.bean.PatrolInspectionRecordsDetailImgBean;
import com.xiaomai.zhuangba.data.bean.PatrolMissionDetailListBean;
import com.xiaomai.zhuangba.data.bean.PayData;
import com.xiaomai.zhuangba.data.bean.PayDepositBean;
import com.xiaomai.zhuangba.data.bean.ProvincialBean;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.data.bean.ServiceData;
import com.xiaomai.zhuangba.data.bean.ServiceSubcategory;
import com.xiaomai.zhuangba.data.bean.SkillList;
import com.xiaomai.zhuangba.data.bean.Slotting;
import com.xiaomai.zhuangba.data.bean.StatisticsData;
import com.xiaomai.zhuangba.data.bean.TeamJoinedBean;
import com.xiaomai.zhuangba.data.bean.TeamMessageBean;
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
    Observable<HttpResult<Orders>> getMasterSelectOrder(@Query("pageNum") String pageNum, @Query("pageSize") String pageSize, @Query("address") String address);

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
     * @param orderType 订单类型 1 安装单 2 广告单
     * @return observable
     */
    @GET("order/getOrderDetails/{orderCode}")
    Observable<HttpResult<OngoingOrdersList>> getOrderDetails(@Path("orderCode") String orderCode, @Query("orderType") String orderType);

    /**
     * 订单池详情
     *
     * @param orderCode 订单编号
     * @param orderType 订单类型 1安装单 2 广告单
     * @return observable
     */
    @GET("order/getOrderByOrderCode")
    Observable<HttpResult<OngoingOrdersList>> getOrderByOrderCode(@Query("orderCode") String orderCode, @Query("orderType") String orderType);

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
    Observable<HttpResult<List<DeliveryContent>>> getOrderValidation(@Path("orderCode") String orderCode, @Query("orderType") String orderType);


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
     * @param code      钱包支付密码
     * @return observable
     */
    @FormUrlEncoded
    @POST("order/orderPay")
    Observable<HttpResult<PayData>> orderPay(@Field("orderCode") String orderCode, @Field("payType") String payType, @Field("code") String code);


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
    Observable<HttpResult<StatisticsData>> getStatisticalData(@Query("address") String address);

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
     * @param pageNum   每页显示数量
     * @param pageSize  每页显示
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return observable
     */
    @GET("wallet/getWalletDetailed")
    Observable<HttpResult<WalletDetailBean>> getWalletDetail(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize,
                                                             @Query("startDate") String startDate, @Query("endDate") String endDate);

    /**
     * 获取收入列表
     *
     * @param pageNum   每页显示数量
     * @param pageSize  每页显示
     * @param startDate 开始时间
     * @param endDate   结束时间
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
     * @param pageNum   每页显示数量
     * @param pageSize  每页显示
     * @param startDate 开始时间
     * @param endDate   结束时间
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
     *
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
     *
     * @param password 密码
     * @return observable
     */
    @FormUrlEncoded
    @POST("user/refundDeposit")
    Observable<HttpResult<String>> returnEarnest(@Field("password") String password);

    /**
     * 查询保证金的金额
     *
     * @return observable
     */
    @POST("user/getBond")
    Observable<HttpResult<List<PayDepositBean>>> getBond();

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
     *
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
     * 单图上传
     *
     * @param body body
     * @return Observable
     */
    @POST("uploadIng/uploadImg")
    Observable<HttpResult<Object>> uploadImg(@Body RequestBody body);

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
     *
     * @param parentId parentId
     * @param level    level
     * @return observable
     */
    @GET("communal/getRegion")
    Observable<HttpResult<List<ProvincialBean>>> getRegion(@Query("parentId") int parentId, @Query("level") int level);


    /**
     * 提交身份认证
     *
     * @param requestBody body
     * @return observable
     */
    @POST("user/updateRegistrationInformation")
    Observable<HttpResult<UserInfo>> updateRegistrationInformation(@Body RequestBody requestBody);

    /**
     * 企业认证
     *
     * @param requestBody body
     * @return observable
     */
    @POST("user/certification")
    Observable<HttpResult<UserInfo>> certification(@Body RequestBody requestBody);


    /**
     * 修改服务范围
     *
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
     * @param orderCode          订单编号
     * @param cancellationCauses 取消说明
     * @return observable
     */
    @GET("order/masterCancelOrder/{orderCode}")
    Observable<HttpResult<Object>> masterCancelOrder(@Path("orderCode") String orderCode, @Query("cancellationCauses") String cancellationCauses);

    /**
     * 雇主取消任务
     *
     * @param orderCode 订单编号
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
     * @param orderCode 订单编号
     * @return observable
     */
    @GET("order/acceptOrder/{orderCode}")
    Observable<HttpResult<Object>> acceptOrder(@Path("orderCode") String orderCode);


    /**
     * 师傅确认订单时间
     *
     * @param orderCode        订单编号
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
     *
     * @param orderCode           订单编号
     * @param beforePicturesUrl   多图
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

    /**
     * 根据服务项目查询维保信息
     *
     * @param serviceId 小类服务ID
     * @return observable
     */
    @FormUrlEncoded
    @POST("maintenance/getMaintenance")
    Observable<HttpResult<List<Maintenance>>> getMaintenance(@Field("serviceId") String serviceId);


    /**
     * 雇主端 钱包查询
     *
     * @param phoneNumber 账号
     * @return observable
     */
    @FormUrlEncoded
    @POST("wallet/selectWalletBalance")
    Observable<HttpResult<EmployerWalletBean>> selectWalletBalance(@Field("phoneNumber") String phoneNumber);


    /**
     * 雇主端 钱包查询
     *
     * @param money   充值金额
     * @param payType 1 支付宝 2 微信
     * @return observable
     */
    @FormUrlEncoded
    @POST("wallet/walletPay")
    Observable<HttpResult<PayData>> walletPay(@Field("money") String money, @Field("payType") String payType);

    /**
     * 雇主端 设置交易密码
     *
     * @param presentationPassword 交易密码
     * @return observable
     */
    @FormUrlEncoded
    @POST("wallet/updatePresentationPassword")
    Observable<HttpResult<String>> updatePresentationPassword(@Field("presentationPassword") String presentationPassword);


    /**
     * 雇主维保查询
     *
     * @param phoneNumber 手机号
     * @param pageNum     当前页
     * @param pageSize    每页加载数量
     * @return observable
     */
    @GET("maintenance/selectEmployerMaintenance")
    Observable<HttpResult<MaintenanceBean>> selectEmployerMaintenance(@Query("phoneNumber") String phoneNumber
            , @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 师傅维保查询
     *
     * @param phoneNumber 手机号
     * @param pageNum     当前页
     * @param pageSize    每页加载数量
     * @return observable
     */
    @GET("maintenance/selectOvermanMaintenance")
    Observable<HttpResult<MaintenanceBean>> selectOvermanMaintenance(@Query("phoneNumber") String phoneNumber
            , @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 续维保
     *
     * @param requestBody body
     * @return observable
     */
    @POST("maintenance/continuedMaintenance")
    Observable<HttpResult<PayData>> continuedMaintenance(@Body RequestBody requestBody);

    /**
     * 新增维保
     *
     * @param requestBody body
     * @return observable
     */
    @POST("maintenance/addMaintenance")
    Observable<HttpResult<PayData>> addMaintenance(@Body RequestBody requestBody);

    /**
     * 雇主明细查询
     *
     * @param phoneNumber 手机号
     * @param team        团队
     * @param pageNum     当前页
     * @param pageSize    一页数量
     * @return observable
     */
    @FormUrlEncoded
    @POST("wallet/getRunningAccountDetail")
    Observable<HttpResult<EmployerWalletDetailBean>> getRunningAccountDetail(@Field("phoneNumber") String phoneNumber,
                                                                             @Field("team") String team, @Field("pageNum") int pageNum,
                                                                             @Field("pageSize") int pageSize);

    /**
     * 充值记录 和 消费记录
     *
     * @param phoneNumber 手机号
     * @param team        团队
     * @param wallerType  充值记录类型
     * @param pageNum     当前页
     * @param pageSize    一页数量
     * @return observable
     */
    @FormUrlEncoded
    @POST("wallet/rechargeRecord")
    Observable<HttpResult<EmployerWalletDetailBean>> rechargeRecord(@Field("phoneNumber") String phoneNumber,
                                                                    @Field("team") String team,
                                                                    @Field("wallerType") String wallerType,
                                                                    @Field("pageIndex") int pageNum,
                                                                    @Field("pageSize") int pageSize);

    /**
     * 订单首页统计详细信息
     *
     * @param type 类型 0:当日（默认）；1：本周；2:本月；3：本季度
     * @return observable
     */
    @GET("order/getOrderStatisticsDetails")
    Observable<HttpResult<DataDetailsContent>> getOrderStatisticsDetails(@Query("type") int type);


    /**
     * 查询开槽、辅材和调试
     *
     * @return observable
     */
    @GET("userRole/getSlottingAndDebug")
    Observable<HttpResult<Slotting>> getSlottingAndDebug();


    /**
     * 广告更换
     *
     * @param phoneNumber 手机号
     * @param pageNum     页码
     * @param pageSize    一页显示
     * @return observable
     */
    @FormUrlEncoded
    @POST("advertising/getAdvertisingList")
    Observable<HttpResult<AdvertisingReplacementBean>> getAdvertisingList(@Field("phone") String phoneNumber
            , @Field("pageIndex") int pageNum, @Field("pageSize") int pageSize);

    /**
     * 雇主广告更换
     *
     * @param phoneNumber 手机号
     * @param pageNum     页码
     * @param pageSize    一页显示
     * @return observable
     */
    @FormUrlEncoded
    @POST("advertising/getAdvertisingElList")
    Observable<HttpResult<RefreshBaseList<EmployerAdvertisingReplacement>>> getAdvertisingElList(@Field("phone") String phoneNumber
            , @Field("pageIndex") int pageNum, @Field("pageSize") int pageSize);

    /**
     * 雇主广告更换详情
     *
     * @param batchCode 批量号
     * @param pageNum   页码
     * @param pageSize  一页显示
     * @return observable
     */
    @FormUrlEncoded
    @POST("advertising/getAdvertisingElDetails")
    Observable<HttpResult<RefreshBaseList<AdvertisingReplacementDetailBean>>> getAdvertisingElDetails(@Field("batchCode") String batchCode
            , @Field("pageIndex") int pageNum, @Field("pageSize") int pageSize);


    /**
     * 雇主取消广告单
     *
     * @param orderCode 订单编号
     * @return observable
     */
    @GET("order/cancelAdvertisingOrderOrder/{orderCode}")
    Observable<HttpResult<Object>> cancelAdvertisingOrderOrder(@Path("orderCode") String orderCode);

    /**
     * 广告单验收不通过
     *
     * @param orderCode        订单编号
     * @param employerDescribe 验收不通过理由
     * @return observable
     */
    @GET("order/notPassedAdvertisingOrder/{orderCode}")
    Observable<HttpResult<Object>> notPassedAdvertisingOrder(@Path("orderCode") String orderCode, @Query("employerDescribe") String employerDescribe);

    /**
     * 广告单验收通过
     *
     * @param orderCode 订单编号
     * @return observable
     */
    @GET("order/passedAdvertisingOrder/{orderCode}")
    Observable<HttpResult<Object>> passedAdvertisingOrder(@Path("orderCode") String orderCode);


    /**
     * 广告单 师傅取消任务
     *
     * @param orderCode 订单编号
     * @return observable
     */
    @GET("order/masterCancelAdvertisingOrder/{orderCode}")
    Observable<HttpResult<Object>> masterCancelAdvertisingOrder(@Path("orderCode") String orderCode);

    /**
     * 广告单 师傅接受任务
     *
     * @param orderCode 订单编号
     * @return observable
     */
    @GET("order/acceptAdvertisingOrder/{orderCode}")
    Observable<HttpResult<Object>> acceptAdvertisingOrder(@Path("orderCode") String orderCode);

    /**
     * 广告单 师傅 现在出发
     *
     * @param orderCode 订单编号
     * @return observable
     */
    @GET("order/nowWeLeaveAdvertising/{orderCode}")
    Observable<HttpResult<Object>> nowWeLeaveAdvertising(@Path("orderCode") String orderCode);


    /**
     * 广告单 师傅 开始施工
     *
     * @param orderCode         订单编号
     * @param beforePicturesUrl 开始施工前的照片
     * @return observable
     */
    @GET("order/startTaskAdvertisingOrder/{orderCode}")
    Observable<HttpResult<Object>> startTaskAdvertisingOrder(@Path("orderCode") String orderCode, @Query("beforePicturesUrl") String beforePicturesUrl);

    /**
     * 广告单 师傅 完成提交
     *
     * @param requestBody body
     * @return observable
     */
    @POST("order/submitAdvertisingValidation")
    Observable<HttpResult<Object>> submitAdvertisingValidation(@Body RequestBody requestBody);

    /**
     * 广告单 分类
     *
     * @param requestBody body
     * @return observable
     */
    @POST("order/getMasterHandleAdvertisingOrderList")
    Observable<HttpResult<RefreshBaseList<OngoingOrdersList>>> getMasterHandleAdvertisingOrderList(@Body RequestBody requestBody);

    /**
     * 广告单 师傅 完成提交
     *
     * @param pageNum  页码
     * @param pageSize 一页显示行数
     * @return observable
     */
    @GET("order/getMasterHandleOrder")
    Observable<HttpResult<RefreshBaseList<AdvertisingBillsBean>>> getMasterHandleOrder(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 巡查单 师傅
     *
     * @param pageNum  页码
     * @param pageSize 一页显示行数
     * @return observable
     */
    @GET("order/getMasterHandleInspectionOrder ")
    Observable<HttpResult<RefreshBaseList<InspectionSheetBean>>> getMasterHandleInspectionOrder(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);


    /**
     * 师傅批量接受广告订单
     *
     * @param requestBody body
     * @return observable
     */
    @POST("order/acceptAllAdvertisingOrder")
    Observable<HttpResult<Object>> acceptAllAdvertisingOrder(@Body RequestBody requestBody);

    /**
     * 师傅广告批量出发
     *
     * @param requestBody body
     * @return observable
     */
    @POST("order/nowWeLeaveAllAdvertising")
    Observable<HttpResult<Object>> nowWeLeaveAllAdvertising(@Body RequestBody requestBody);


    /**
     * 查询是否认证
     *
     * @return observable
     */
    @GET("/userRole/verifyAccountNumber")
    Observable<HttpResult<String>> verifyAccountNumber();


    /**
     * 雇主 巡查任务
     *
     * @param pageNum  页
     * @param pageSize 显示多少条
     * @return observable
     */
    @FormUrlEncoded
    @POST("order/getPatrolOrderList")
    Observable<HttpResult<Patrol>> getPatrolOrderList(@Field("pageNum") String pageNum, @Field("pageSize") String pageSize);

    /**
     * 巡查任务
     *
     * @param requestBody body
     * @return observable
     */
    @POST("order/getMasterHandleInspectionOrderList")
    Observable<HttpResult<RefreshBaseList<InspectionSheetDetailBean>>> getMasterHandleInspectionOrderList(@Body RequestBody requestBody);


    /**
     * 查询团队
     *
     * @param requestBody body
     * @return observable
     */
    @POST("teamwork/selectByTeam")
    Observable<HttpResult<Object>> selectByTeam(@Body RequestBody requestBody);


    /**
     * 师傅巡查任务 列表
     *
     * @param pageNum  页
     * @param pageSize 显示多少条
     * @param current  巡查任务传：current ，记录传0
     * @return observable
     */
    @FormUrlEncoded
    @POST("task/selectByMentor")
    Observable<HttpResult<RefreshBaseList<PatrolBean>>> selectByMentor(@Field("pageNum") String pageNum,
                                                                       @Field("pageSize") String pageSize,
                                                                       @Field("current") String current);

    /**
     * 师傅巡查任务 列表
     *
     * @param pageNum  页
     * @param pageSize 显示多少条
     * @param detailNo 子订单编号
     * @param current  巡查任务传：current ，记录传0
     * @return observable
     */
    @FormUrlEncoded
    @POST("task/selectByDetailNo")
    Observable<HttpResult<RefreshBaseList<PatrolMissionDetailListBean>>> selectByDetailNo(@Field("pageNum") String pageNum,
                                                                                          @Field("pageSize") String pageSize,
                                                                                          @Field("detailNo") String detailNo,
                                                                                          @Field("current") String current);

    /**
     * 师傅巡查任务 列表
     *
     * @param id id
     * @return observable
     */
    @FormUrlEncoded
    @POST("task/selectByTaskId")
    Observable<HttpResult<PatrolInspectionRecordsDetailImgBean>> selectByTaskId(@Field("id") String id);



    /**
     * 师傅巡查任务 列表
     *
     * @param requestBody body
     * @return observable
     */
    @POST("task/save")
    Observable<HttpResult<Object>> save(@Body RequestBody requestBody);

    /**
     * 查询 是否加入了团队
     * @return observable
     */
    @POST("teamwork/selectByTeam")
    Observable<HttpResult<Object>> selectByTeam();

    /**
     * 创建团队
     * @param nameTeam 团队名称
     * @return observable
     */
    @FormUrlEncoded
    @POST("teamwork/save")
    Observable<HttpResult<Object>> saveTeam(@Field("name") String nameTeam);

    /**
     * 查询团队成员
     * @param type 1:团长；2:团员
     * @return observable
     */
    @FormUrlEncoded
    @POST("teamwork/getTeamPersonnel")
    Observable<HttpResult<List<TeamJoinedBean>>> getTeamPersonnel(@Field("type") String type);

    /**
     * 解散团队
     * @param type 1:团长；2:团员
     * @return observable
     */
    @FormUrlEncoded
    @POST("teamwork/dissolutionTeam")
    Observable<HttpResult<Object>> dissolutionTeam(@Field("type") String type);

    /**
     * 加入团队
     * @param phone 团长手机号
     * @return observable
     */
    @FormUrlEncoded
    @POST("teamwork/insertTeamMember")
    Observable<HttpResult<Object>> insertTeamMember(@Field("phone") String phone);

    /**
     * 退出团队
     * @param phone 团长手机号
     * @return observable
     */
    @FormUrlEncoded
    @POST("teamwork/dropOutTeam")
    Observable<HttpResult<Object>> dropOutTeam(@Field("phone") String phone);

    /**
     * 邀请团员
     * @param member 团员手机号
     * @return observable
     */
    @FormUrlEncoded
    @POST("teamwork/saveMember")
    Observable<HttpResult<Object>> saveMember(@Field("member") String member);


    /**
     * 删除团队成员
     * @param phone 团员手机号
     * @return observable
     */
    @FormUrlEncoded
    @POST("teamwork/deleteMember")
    Observable<HttpResult<Object>> deleteMember(@Field("phone") String phone);

    /**
     * 安装单
     * @param staffNumber 团员手机号
     * @param pageNum  页
     * @param pageSize 显示多少条
     * @return observable
     */
    @GET("order/getOrderListByStaff")
    Observable<HttpResult<RefreshBaseList<OngoingOrdersList>>> getOrderListByStaff(@Query("staffNumber") String staffNumber,
                                                       @Query("pageNum") String pageNum,
                                                       @Query("pageSize") String pageSize);

    /**
     * 广告单
     * @param staffNumber 团员手机号
     * @param pageNum  页
     * @param pageSize 显示多少条
     * @return observable
     */
    @GET("order/getAdvertisingOrderByStaff")
    Observable<HttpResult<RefreshBaseList<AdvertisingBillsBean>>> getAdvertisingOrderByStaff(@Query("staffNumber") String staffNumber,
                                                       @Query("pageNum") String pageNum,
                                                       @Query("pageSize") String pageSize);


    /**
     * 订单详情
     *
     * @param orderCode 订单code
     * @param type      订单类型 1安装单 2 广告单
     * @return observable
     */
    @GET("order/deleteOrder/{orderCode}")
    Observable<HttpResult<Object>> deleteOrder(@Path("orderCode") String orderCode, @Query("type") String type);

    /**
     * 分配任务
     *
     * @param body body
     * @return observable
     */
    @POST("order/addOrder")
    Observable<HttpResult<Object>> addOrder(@Body RequestBody body);

    /**
     * 广告单 分配任务 列表
     *
     * @param requestBody body
     * @return observable
     */
    @POST("order/getAdvertisingOrderListByStaff")
    Observable<HttpResult<RefreshBaseList<OngoingOrdersList>>> getAdvertisingOrderListByStaff(@Body RequestBody requestBody);


    /**
     * 冻结金额
     * @param phoneNumber 手机号
     * @return observable
     */
    @FormUrlEncoded
    @POST("wallet/selectFreezeOrder")
    Observable<HttpResult<List<FrozenAmountBean>>> selectFreezeOrder(@Field("phoneNumber") String phoneNumber);

    /**
     * 系统通知列表
     * @param userNumber 师傅手机号
     * @param isAgree    3:拒绝:拒绝加入时将删除状态改为y;4同意;  不传 返回通知列表
     * @return observable
     */
    @FormUrlEncoded
    @POST("user/selectTeamUserByPhone")
    Observable<HttpResult<List<TeamMessageBean>>> selectTeamUserByPhone(@Field("userNumber") String userNumber , @Field("isAgree") String isAgree);

    /**
     * 是否同意师傅加入团队
     * @param userNumber 师傅手机号
     * @param isAgree    3:拒绝:拒绝加入时将删除状态改为y;4同意;  不传 返回通知列表
     * @return observable
     */
    @FormUrlEncoded
    @POST("user/updateTeam")
    Observable<HttpResult<Object>> updateTeam(@Field("userNumber") String userNumber , @Field("isAgree") String isAgree);

    /**
     * 清空消息
     * @param userNumber 师傅手机号
     * @param isAgree    5 清空
     * @return observable
     */
    @FormUrlEncoded
    @POST("user/updateAll")
    Observable<HttpResult<Object>> updateAll(@Field("userNumber") String userNumber , @Field("isAgree") String isAgree);

    /**
     * 师傅巡查任务接受订单
     * @param orderCode 订单编号
     * @return observable
     */
    @GET("order/acceptInspectionOrder/{orderCode}")
    Observable<HttpResult<Object>> acceptInspectionOrder(@Path("orderCode") String orderCode);


    /**
     * 师傅巡查任务取消订单
     * @param orderCode 订单编号
     * @return observable
     */
    @GET("order/masterCancelInspectionOrder/{orderCode}")
    Observable<HttpResult<Object>> masterCancelInspectionOrder(@Path("orderCode") String orderCode);


    /**
     * 雇主巡查任务取消订单
     * @param orderCode 订单编号
     * @return observable
     */
    @GET("order/cancelInspectionOrder/{orderCode}")
    Observable<HttpResult<Object>> cancelInspectionOrder(@Path("orderCode") String orderCode);

    /**
     * 团队 删除 广告单
     * @param requestBody body
     * @return observable
     */
    @POST("order/deleteAllOrder")
    Observable<HttpResult<Object>> deleteAllOrder(@Body RequestBody requestBody);


    /**
     * 查询该用户是否有月结单选项
     * @return observable
     */
    @GET("user/getUserMonthly")
    Observable<HttpResult<Boolean>> getUserMonthly();
}
