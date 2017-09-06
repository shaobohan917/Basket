package com.first.basket.http

import com.grapesnberries.curllogger.CurlLoggerInterceptor

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Administrator on 2016/9/7.
 */
class HttpMethods//私有化构造方法
private constructor() {

    private val retrofit: Retrofit
    private val apiService: ApiService
    private val builder: OkHttpClient.Builder

    init {
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
                .addConverterFactory(GsonConverterFactory.create())
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
        //    public static final String BASE_URL = Constant.BASE_URL_ONLINE;
        val BASE_URL = "https://oapi.dingtalk.com/"

        private val DEFAULT_TIMEOUT = 10

        //获取单例
        val instance: HttpMethods
            get() = singletonHolder.INSTANCE

        fun createService(): ApiService {
            return singletonHolder.INSTANCE.apiService
        }
    }
}
