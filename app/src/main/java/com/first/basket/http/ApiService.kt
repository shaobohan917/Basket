package com.first.basket.http

import com.first.basket.bean.BaseBean
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
    fun getDistrict(@FieldMap map: Map<String, String>): Observable<BaseBean>

}