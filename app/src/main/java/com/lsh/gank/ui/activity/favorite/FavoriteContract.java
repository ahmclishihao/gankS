package com.lsh.gank.ui.activity.favorite;

import com.lsh.gank.bean.FavoriteData;
import com.lsh.gank.ui.BasePresenter;
import com.lsh.gank.ui.BaseView;

public interface FavoriteContract {

    interface FavoriteView extends BaseView {
        void fillData(FavoriteData data); // 填充数据

        void failData();// 失败时

        void deleteData(int position); // 删除数据

    }

    interface Presenter extends BasePresenter {
        void getData(); // 加载数据

        void deleteData(Object obj, int position); // 删除数据

        void onDestroy(); // 销毁观察者
    }

}
