package com.first.basket.http

import com.first.basket.constants.Constants
import com.google.gson.GsonBuilder
import com.grapesnberries.curllogger.CurlLoggerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Administrator on 2016/9/7.
 */
class HttpMethods//私有化构造方法
private constructor() {

    private val retrofit: Retrofit
    private val apiService: ApiService
    private val builder: OkHttpClient.Builder

    init {

        var gson = GsonBuilder().setLenient().create()

        //手动创建一个OkHttpCicent并设置超时时间
        builder = OkHttpClient.Builder()
                //添加token过期处理
//                .addInterceptor(HeaderInterceptor())
                //打印log
                .addInterceptor(CurlLoggerInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)

        retrofit = Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    //在访问HttpMethods时创建单例
    private object singletonHolder {
        val INSTANCE = HttpMethods()
    }

    companion object {
        val BASE_URL = Constants.BASE_API

        private val DEFAULT_TIMEOUT = 10

        //获取单例
        val instance: HttpMethods
            get() = singletonHolder.INSTANCE

        fun createService(): ApiService {
            return singletonHolder.INSTANCE.apiService
        }
    }
}
