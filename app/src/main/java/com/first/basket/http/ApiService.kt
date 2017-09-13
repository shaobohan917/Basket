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
    @POST("get_district")
    fun getDistrict(@FieldMap map: Map<String, String>): Observable<ClassifyBean>

    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun getProducts(@Field("action") action: String, @Field("classificationid") classificationid: String): Observable<ClassifyContentBean>

    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun getAction(@Field("action") action: String): Call<Any>

    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun getClassify(@Field("action") action: String): Observable<ClassifyBean>

    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun getDetail(@Field("action") action: String, @Field("productid") id: String): Observable<GoodsDetailBean>

    //猜你想要
    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun getRecommend(@Field("action") action: String): Observable<RecommendBean>

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

    //热门推荐
    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun getHotRecommend(@Field("action") action: String): Observable<HttpResult<HotRecommendBean>>
}