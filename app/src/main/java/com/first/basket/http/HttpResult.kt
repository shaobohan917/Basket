package com.first.basket.http

/**
 * Created by Administrator on 2016/9/7.
 */
class HttpResult<T> {
    var code: Int = 0
    var reason: String? = null

    var result: T? = null

}
