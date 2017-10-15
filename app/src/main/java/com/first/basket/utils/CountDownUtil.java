package com.first.basket.utils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Created by hanshaobo on 2016/12/29.
 */

public class CountDownUtil {

    private static Subscription subscription;

    /**
     * 倒计时
     *
     * @param seconds  单位为秒
     * @param listener 监听
     */
    public static void countDown(int seconds, final onCountDownListener listener) {
        if (seconds < 0) seconds = 0;

        final int countTime = seconds;

        Observable<Integer> observable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long increaseTime) {
                        return countTime - increaseTime.intValue();
                    }
                }).take(countTime + 1);
        subscription = observable.doOnSubscribe(new Action0() {
            @Override
            public void call() {

            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                listener.onCountDownComplete();
            }

            @Override
            public void onError(Throwable e) {
                listener.onCountDownError(e);
            }

            @Override
            public void onNext(Integer integer) {
                listener.onCountDownNext(integer);
            }
        });
    }

    public static void unsubscribe(){

        if(subscription!=null){
            subscription.unsubscribe();
        }
    }

    public interface onCountDownListener {
        void onCountDownNext(Integer integer);

        void onCountDownError(Throwable e);

        void onCountDownComplete();
    }
}
