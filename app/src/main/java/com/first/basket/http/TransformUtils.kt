package com.first.basket.http

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by hanshaobo on 2016/12/23.
 *
 * http://www.jianshu.com/p/93f8c9ae8819
 */

class TransformUtils {

    companion object {

        fun <T> defaultSchedulers(): Observable.Transformer<T, T> {
            return Observable.Transformer { tObservable -> tObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()) }
        }

        fun <T> all_io(): Observable.Transformer<T, T> {
            return Observable.Transformer { tObservable -> tObservable.observeOn(Schedulers.io()).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()) }
        }
    }
}
