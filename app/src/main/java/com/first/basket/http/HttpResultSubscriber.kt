package com.first.basket.http

import rx.Subscriber

/**
 * Created by hanshaobo on 2016/12/22.
 */

abstract class HttpResultSubscriber<T> : Subscriber<HttpResult<T>>() {
    private var t: T? = null

    override fun onCompleted() {

    }

    override fun onError(e: Throwable) {
        _onError(9999, e, t)
        onCompleted()
    }

    override fun onNext(tHttpResult: HttpResult<T>) {
        if (tHttpResult.code === 1000) {
            onSuccess(tHttpResult.result, tHttpResult.reason)
        } else {
            this.t = tHttpResult.result
            _onError(tHttpResult.code, Throwable(tHttpResult.reason), tHttpResult.result)
        }
    }

    protected abstract fun onSuccess(result: T?, reason: String?)

    protected abstract fun _onError(code: Int, throwable: Throwable, result: T?)
}
