package com.first.basket.http

import com.first.basket.base.HttpResult
import com.first.basket.bean.WechatBean
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import rx.Observable

/**
 * Created by hanshaobo on 29/10/2017.
 */
interface WechatService {
    @FormUrlEncoded
    @POST("ClientAPI.php")
    fun doPayforwechat(@Field("action") action: String, @FieldMap map: HashMap<String, String?>): Observable<WechatBean>
}