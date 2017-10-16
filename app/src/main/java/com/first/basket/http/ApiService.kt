package com.first.basket.http

import com.first.basket.base.HttpResult
import com.first.basket.bean.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import rx.Observable

/**
 * Created by hanshaobo on 05/09/2017.
 */
interface ApiService {
    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun getProducts(@Field("action") action: String, @Field("channel") channel: String, @Field("leveltwoid") leveltwoid: String): Observable<ClassifyContentBean>

    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun getClassify(@Field("action") action: String, @Field("channel") channel: String): Observable<HttpResult<ClassifyBean>>

    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun getDetail(@Field("action") action: String, @Field("productid") id: String): Observable<GoodsDetailBean>

    //猜你想要
    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun getRecommend(@Field("action") action: String): Observable<RecommendBean>


    //热门推荐
    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun getHotRecommend(@Field("action") action: String): Observable<HotRecommendBean>

    //获取订单列表
    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun getOrderList(@Field("action") action: String, @Field("userid") userid: String): Observable<OrderListBean>

    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun getHome(@Field("action") action: String): Observable<HomeBean>

    //计算商品价格
    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun getPrice(@Field("action") action: String, @Field("productids") productids: String, @Field("productnum") productnum: String): Observable<HttpResult<PriceBean>>


    //立即下单
    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun doPlaceOrder(@Field("action") action: String, @Field("productsid") productsid: String, @Field("productsNumber") productsNumber: String, @Field("userid") userid: String, @Field("addressid") addressid: String): Observable<HttpResult<CodeBean>>


    //发送验证码
    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun getCode(@Field("action") action: String, @Field("phonenumber") phonenumber: String): Observable<HttpResult<CodeBean>>


    //注册，修改密码
    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun doRegister(@Field("action") action: String, @Field("phonenumber") phonenumber: String, @Field("code") code: String, @Field("password") password: String): Observable<HttpResult<LoginBean>>


    //登录
    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun doLogin(@Field("action") action: String, @Field("phonenumber") phonenumber: String, @Field("code") code: String, @Field("password") password: String): Observable<HttpResult<LoginBean>>

    //添加地址
    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun addAddress(@Field("action") action: String, @FieldMap map: HashMap<String, String>): Observable<HttpResult<LoginBean>>

    //获取街道/镇
    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun getSubdistrict(@Field("action") action: String, @Field("districtname") districtname: String): Observable<HttpResult<DistrictBean>>


}