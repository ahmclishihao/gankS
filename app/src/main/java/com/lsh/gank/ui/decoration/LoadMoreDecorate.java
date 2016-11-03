package com.lsh.gank.ui.decoration;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lsh.gank.R;
import com.lsh.gank.util.UIUtils;

/**
 * 加载更多的装饰器，可以给adapter添加加载更多的选项
 */

public class LoadMoreDecorate extends RecyclerView.Adapter {
    // 是否加载
    public boolean isLoading = false;
    // 加载状态
    public static int state_loading = 0;
    public static int state_error = -1;
    public static int state_no_data = 1;
    public int currentState = state_loading;

    public int loadMoreType = 2;

    // 加载监听器
    private OnLoaderMoreListener listener;

    // 被装饰的adapter
    public RecyclerView.Adapter mAdapter = null;
    private LoadMoreHolder mLoadMoreHolder;

    public LoadMoreDecorate(RecyclerView.Adapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 返回对应类型的holder
        if (viewType == loadMoreType) {
            return new LoadMoreHolder(UIUtils.inflateView(parent.getContext(), R.layout.layout_load_more, parent));
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadMoreHolder) {
            // 根据holder类型来判断是否为加载更多
            if (mLoadMoreHolder == null)
                mLoadMoreHolder = (LoadMoreHolder) holder;
            loadMore();
        } else {
            mAdapter.onBindViewHolder(holder, position);
        }
    }

    public int getItemCount() {
        return mAdapter.getItemCount() + 1;
    }

    public int getItemViewType(int position) {
        // 统listView中的ViewType
        if (position == mAdapter.getItemCount()) {
            return loadMoreType;
        } else
            return mAdapter.getItemViewType(position);
    }

    // 刷新回调
    public void loadMore() {
        if (!isLoading && currentState == state_loading) {
            // 回调刷新的监听
            if (listener != null) {
                isLoading = true;
                listener.onLoader();
            }
        }
    }

    // 设置加载是否成功
    public void setLoadingState(int state) {
        this.currentState = state;
        isLoading = false;
        setLoadView();
    }

    // 设置加载的view的显示和监听
    public void setLoadView() {
        if (mLoadMoreHolder == null)
            return;
        mLoadMoreHolder.mTextView.setText(currentState == state_loading ? "正在加载。。。" : currentState == state_error ? "加载失败，点击文字重试" : "没有更多了，点击文字重试");

        mLoadMoreHolder.mProgressBar.setVisibility(currentState == state_loading ? View.VISIBLE : View.GONE);

        mLoadMoreHolder.itemView.setOnClickListener(currentState == state_loading ? null : new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentState = state_loading;
                loadMore();
            }
        });

    }

    // 加载更多的监听
    public void setOnLoaderMoreListener(OnLoaderMoreListener listener) {
        this.listener = listener;
    }

    public interface OnLoaderMoreListener {
        void onLoader();
    }

    public static class LoadMoreHolder extends RecyclerView.ViewHolder {
        TextView mTextView = null;
        ProgressBar mProgressBar = null;

        public LoadMoreHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_load_desc);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.pb_progress);
        }
    }
}
