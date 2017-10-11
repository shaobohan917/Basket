package com.first.basket.http

import com.first.basket.utils.LogUtils
import rx.Subscriber

/**
 * Created by hanshaobo on 2016/12/22.
 */

open abstract class HttpResultSubscriber<T> : Subscriber<T>() {
//    private var t: T? = null


    override fun onCompleted() {
    }

    override fun onError(e: Throwable) {
    }

    override fun onNext(t: T) {
    }

//    override fun onNext(tHttpResult: HttpResult<T>) {
//        if (tHttpResult.status === 0) {
//            onSuccess(tHttpResult.result!!, tHttpResult.info!!)
//        } else {
//            this.t = tHttpResult.result
//            _onError(tHttpResult.status, Throwable(tHttpResult.info), tHttpResult.result)
//        }
//    }
}


