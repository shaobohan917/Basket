package com.first.basket.http

import com.first.basket.bean.ClassifyBean
import com.first.basket.bean.ClassifyContentBean
import com.first.basket.bean.GoodsDetailBean
import com.first.basket.bean.HomeBean
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

    @FormUrlEncoded
    @POST("ClintAPI.php")
    fun getHome(@Field("action") action: String): Observable<HomeBean>
}