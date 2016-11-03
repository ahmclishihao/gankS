package com.lsh.gank.ui.fragment.home;

import android.util.Log;

import com.lsh.gank.bean.ListData;
import com.lsh.gank.json.JsonHelper;
import com.lsh.gank.net.NetHelper;
import com.lsh.gank.net.ObservableDecorate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class ListDataPresenter implements ListDataContract.Presenter {
    // 对应要解析json的种类标题
    private String mTitle;

    ListDataContract.ListDataView mListDataView = null;
    List<ListData.ListBean> mAllBeanList = null;

    public ListDataPresenter(String title, ListDataContract.ListDataView mListDataView) {
        this.mTitle = title;
        this.mListDataView = mListDataView;
        // 设置主持者
        this.mListDataView.setPresenter(this);

    }

    @Override
    public List<ListData.ListBean> getData() {
        if (mAllBeanList == null)
            mAllBeanList = new ArrayList<>();
        return mAllBeanList;
    }

    @Override
    public void setData() {
        Observable<ListData> projectAll = NetHelper.getApi().getProjectAll(mTitle, 20, 1);
        ObservableDecorate.decortateIO(projectAll)
                .subscribe(new Subscriber<ListData>() {
                    @Override
                    public void onCompleted() {
                        Log.i("ListDataPresenter", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("ListDataPresenter", "error");
                        mListDataView.fillData(null);
                    }

                    @Override
                    public void onNext(ListData listData) {
                        mAllBeanList.clear();
                        mAllBeanList.addAll(listData.results);
                        mListDataView.fillData(listData.results);
                    }
                });
    }

    @Override
    public void loadMore() {
        Observable<ListData> projectAll = NetHelper.getApi().getProjectAll(mTitle, 20, mAllBeanList.size() / 20 + 1);
        ObservableDecorate.decortateIO(projectAll)
                .subscribe(new Subscriber<ListData>() {
                    @Override
                    public void onCompleted() {
                        Log.i("ListDataPresenter", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("ListDataPresenter", "error_load_more");
                        mListDataView.loadMore(null);
                    }

                    @Override
                    public void onNext(ListData listData) {
                        mAllBeanList.addAll(listData.results);
                        mListDataView.loadMore(listData.results);
                    }
                });
    }

    @Override
    public void refresh() {
        setData();
    }

    @Override
    public void saveData(ListData.ListBean listBean) {
        try {
            JsonHelper.saveData(listBean);
            mListDataView.showTips("保存成功");
        } catch (IOException e) {
            e.printStackTrace();
            mListDataView.showTips("保存失败");
        }
    }
}
