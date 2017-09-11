package com.first.basket.http

/**
 * Created by Administrator on 2016/9/7.
 */
class HttpResult<T> {
    var status: Int = 0
    var info: String = ""

    var result: T? = null

}
