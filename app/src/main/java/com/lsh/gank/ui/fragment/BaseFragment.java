package com.lsh.gank.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsh.gank.ui.widget.LoadingPager;

/**
 * 抽取的具备 加载状态的页面
 */

public abstract class BaseFragment extends Fragment {

    private LoadingPager mLoadingPager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 状态页面
        if (mLoadingPager == null) {
            mLoadingPager = new LoadingPager(getContext());
            mLoadingPager.setSuccessView(initView(getContext(), inflater, container));
        }
        return mLoadingPager;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoadingPager.setRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        setListener();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    // 子类实现数据初始加载
    protected abstract void initData();

    // 子类实现监听设置
    protected abstract void setListener();

    // 暴露给子类获取根布局
    protected LoadingPager getLoadingPager() {
        return mLoadingPager;
    }

    // 子类实现加载成功界面的设置
    protected abstract View initView(Context context, LayoutInflater inflater, ViewGroup container);

    // 子类实现获取加载成功的布局的方法
    public abstract View getSuccessView();
}
