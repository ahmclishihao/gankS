package com.lsh.gank.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.lsh.gank.R;
import com.lsh.gank.util.UIUtils;

public class LoadingPager extends FrameLayout {

    public enum PAGE_STATE {
        LOADING,
        LOADING_ERROR,
        LOADING_NODATA,
        LOADING_SUCCESS;
    }

    public PAGE_STATE currentState = PAGE_STATE.LOADING;

    private View mLoadingView, mErrorView, mNotDataView, mSuccessView;

    public LoadingPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (mLoadingView == null) {
            mLoadingView = UIUtils.inflateView(getContext(), R.layout.loadingpager_loading, null);
            addView(mLoadingView);
        }

        if (mErrorView == null) {
            mErrorView = UIUtils.inflateView(getContext(), R.layout.loadingpager_error, null);
            addView(mErrorView);
        }

        if (mNotDataView == null) {
            mNotDataView = UIUtils.inflateView(getContext(), R.layout.loadingpage_nodata, null);
            addView(mNotDataView);
        }

        setCurrentView();
    }

    private void setCurrentView() {
        mLoadingView.setVisibility(currentState == PAGE_STATE.LOADING ? VISIBLE : GONE);

        mErrorView.setVisibility(currentState == PAGE_STATE.LOADING_ERROR ? VISIBLE : GONE);

        mNotDataView.setVisibility(currentState == PAGE_STATE.LOADING_NODATA ? VISIBLE : GONE);

        if (mSuccessView != null) {
            mSuccessView.setVisibility(currentState == PAGE_STATE.LOADING_SUCCESS ? VISIBLE : GONE);
        }
    }

    // 使用者应设置一个加载成功的布局
    public void setSuccessView(View view) {
        this.mSuccessView = view;
        addView(mSuccessView);
        mSuccessView.setVisibility(GONE);
    }

    public void setCurrentState(PAGE_STATE state) {
        this.currentState = state;
        setCurrentView();
    }

    // 设置加载失败的刷新事件
    public void setRefreshListener(OnClickListener listener) {
        mErrorView.setOnClickListener(listener);
        mNotDataView.setOnClickListener(listener);
    }
}
