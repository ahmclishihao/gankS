package com.lsh.gank.ui.activity.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.lsh.gank.global.App;
import com.lsh.gank.util.GlideUtil;
import com.lsh.gank.util.MD5Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PhotoPresenter implements PhotoContract.Presenter {
    PhotoContract.PhotoView view;
    String mUrl;
    private Subscription mSubscribe;

    public PhotoPresenter(PhotoContract.PhotoView view) {
        this.view = view;
    }

    @Override
    public void setPhoto(Intent intent, ImageView view) {
        mUrl = intent.getStringExtra("url");
        GlideUtil.loadStaticPic(view, mUrl);
        this.view.showPhoto();
    }

    @Override
    public void savePhoto(ImageView view) {
        final BitmapDrawable drawable = (BitmapDrawable) view.getDrawable();
        if (drawable != null) {
            this.view.showTips("开始保存");
            // 保存图片

            mSubscribe = Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    FileOutputStream fileOutputStream = null;
                    try {
                        Bitmap bitmap = drawable.getBitmap();

                        File pics = App.picDirectory;
                        // 文件名以时间为名
                        String fileName = MD5Util.md5(mUrl) + ".png";
                        File file = new File(pics, fileName);
                        fileOutputStream = new FileOutputStream(file);
                        // 保存
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                        subscriber.onNext(file.getAbsolutePath());

                    } catch (FileNotFoundException e) {
                        subscriber.onError(e);
                    } finally {
                        try {
                            if (fileOutputStream != null)
                                fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        subscriber.onCompleted();
                    }
                }
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            PhotoPresenter.this.view.showTips("啊哦！保存失败,可能内存不足了哦~");
                        }

                        @Override
                        public void onNext(String s) {
                            PhotoPresenter.this.view.showTips("保存在:" + s);
                        }
                    });
        } else {
            this.view.showTips("保存失败，可能未加载");
        }
    }

    @Override
    public void destroy() {
        if (mSubscribe != null && !mSubscribe.isUnsubscribed()) {
            mSubscribe.unsubscribe();
        }
    }

}
