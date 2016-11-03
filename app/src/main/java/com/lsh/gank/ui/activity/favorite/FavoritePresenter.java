package com.lsh.gank.ui.activity.favorite;

import com.lsh.gank.bean.FavoriteData;
import com.lsh.gank.bean.ListData;
import com.lsh.gank.json.JsonHelper;

import java.io.IOException;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class FavoritePresenter implements FavoriteContract.Presenter {
    FavoriteContract.FavoriteView view;
    private FavoriteData mData;
    private Subscription mSubscribe;
    private Subscription mDelSubscribe;

    public FavoritePresenter(FavoriteContract.FavoriteView view) {
        this.view = view;
        view.setPresenter(this);
    }

    // 读取数据
    public void getData() {
        if (mSubscribe != null && !mSubscribe.isUnsubscribed())
            mSubscribe.unsubscribe();
        mSubscribe = Observable.create(new Observable.OnSubscribe<FavoriteData>() {
            @Override
            public void call(Subscriber<? super FavoriteData> subscriber) {
                try {
                    FavoriteData data = JsonHelper.getData();
                    subscriber.onStart();
                    subscriber.onNext(data);
                } catch (IOException e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FavoriteData>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.failData();
                    }

                    @Override
                    public void onNext(FavoriteData data) {
                        mData = data;
                        view.fillData(mData);
                    }
                });

    }

    // 删除数据
    public void deleteData(final Object obj, final int position) {
        if (mDelSubscribe != null && !mDelSubscribe.isUnsubscribed())
            mDelSubscribe.unsubscribe();
        mDelSubscribe = Observable.create(new Observable.OnSubscribe<ListData.ListBean>() {
            @Override
            public void call(Subscriber<? super ListData.ListBean> subscriber) {
                try {
                    JsonHelper.deleteData((ListData.ListBean) obj);
                    mData.mListBeen.remove(position);
                    subscriber.onStart();
                    subscriber.onNext(null);
                } catch (IOException e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ListData.ListBean>() {
                    public void onCompleted() {
                    }

                    public void onError(Throwable e) {
                        view.showTips("删除失败");
                    }

                    public void onNext(ListData.ListBean bean) {
                        view.showTips("删除成功");
                        view.deleteData(position);
                        if (mData.mListBeen.size() == 0) {
                            view.failData();
                        }
                    }
                });
    }

    //注销观察者节省资源
    @Override
    public void onDestroy() {
        if (mSubscribe != null && !mSubscribe.isUnsubscribed())
            mSubscribe.unsubscribe();
        if (mDelSubscribe != null && !mDelSubscribe.isUnsubscribed())
            mDelSubscribe.unsubscribe();
    }
}
