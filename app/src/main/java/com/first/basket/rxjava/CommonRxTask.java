package com.first.basket.rxjava;


import rx.Observable;

/**
 * 通用的Rx执行任务
 * Created by z2wenfa on 2016/3/30.
 */
public abstract class CommonRxTask<T> {
    public CommonRxTask(T t) {
        setT(t);
    }

    public CommonRxTask() {

    }

    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public abstract void doInIOThread();

    public abstract void doInUIThread();


    public abstract class MyOnSubscribe<C> implements Observable.OnSubscribe {
        private C t;

        public MyOnSubscribe(C c) {
            setT(t);
        }

        public C getT() {
            return t;
        }

        public void setT(C c) {
            this.t = c;
        }

    }

}
