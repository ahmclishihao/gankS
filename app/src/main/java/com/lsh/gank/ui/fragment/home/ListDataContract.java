package com.lsh.gank.ui.fragment.home;

import com.lsh.gank.bean.ListData;
import com.lsh.gank.ui.BasePresenter;
import com.lsh.gank.ui.BaseView;

import java.util.List;

public interface ListDataContract {

    interface ListDataView extends BaseView {
        // 填充数据
        void fillData(List<ListData.ListBean> obj);

        // 加载更多
        void loadMore(List<ListData.ListBean> obj);

        // 刷新
        void refresh();
    }

    interface Presenter extends BasePresenter {
        // 返回指定数据
        Object getData();

        // 从网络拉取数据
        void setData();

        // 加载更多
        void loadMore();

        // 刷新
        void refresh();

        // 保存指定数据
        void saveData(ListData.ListBean listBean);
    }
}
