package com.lsh.gank.ui.activity.photo;

import android.content.Intent;
import android.widget.ImageView;

import com.lsh.gank.ui.BasePresenter;
import com.lsh.gank.ui.BaseView;

public interface PhotoContract {

    interface PhotoView extends BaseView {
        void showPhoto(); // 展示图片
    }

    interface Presenter extends BasePresenter {
        void setPhoto(Intent intent, ImageView view); // 设置图片

        void savePhoto(ImageView view); // 保存图片

        void destroy(); // 销毁生命周期
    }

}
