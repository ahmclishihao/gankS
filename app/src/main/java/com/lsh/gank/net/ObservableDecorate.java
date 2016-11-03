package com.lsh.gank.net;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Observer的装饰器
 */
public class ObservableDecorate {

    //执行在io线程观察在主线程
    public static Observable decortateIO(Observable observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
