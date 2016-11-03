package com.lsh.gank.ui.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsh.gank.R;
import com.lsh.gank.bean.ListData;
import com.lsh.gank.ui.BasePresenter;
import com.lsh.gank.ui.activity.MainActivity;
import com.lsh.gank.ui.adapter.ListDataAdapter;
import com.lsh.gank.ui.decoration.LoadMoreDecorate;
import com.lsh.gank.ui.fragment.BaseFragment;
import com.lsh.gank.ui.widget.LoadingPager;
import com.lsh.gank.ui.widget.PopupWindowHelper;
import com.lsh.gank.util.SnackBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 列表数据，因为要提供给其他地方使用，所以从homefragment抽取了出来
 */

public class ListDataFragment extends BaseFragment implements ListDataContract.ListDataView, SwipeRefreshLayout.OnRefreshListener {
    // 主持者
    ListDataPresenter mPresenter = null;

    @BindView(R.id.rv_recycler)
    RecyclerView mRvRecycler;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    private View mRootView;
    private ListDataAdapter mListDataAdapter;
    private LoadMoreDecorate mLoadMoreDecorate;
    private String mTitle;
    // 加载成功的回调监听
    private OnLoadingSuccessListener onLoadingSuccessListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取参数
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString("title");
        }
        mPresenter = new ListDataPresenter(mTitle, this);
    }

    @Override
    protected View initView(Context context, LayoutInflater inflater, ViewGroup container) {
        // 添加ListDataFragment
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.layout_simple_swipe_list, null);
            ButterKnife.bind(this, mRootView);
            mSrlRefresh.setColorSchemeResources(R.color.colorPrimary);

            // 当黑色主题时转换为黑色，因为不知道如何用XML来指定其下拉刷新的颜色。。。。
            if (MainActivity.getThemeId() == R.style.AppTheme_black) {
                mSrlRefresh.setProgressBackgroundColorSchemeResource(R.color.black_theme);
            } else {
                mSrlRefresh.setProgressBackgroundColorSchemeResource(R.color.white);
            }
            initRecycle();
        }
        return mRootView;
    }

    // 初始化recycleView，子类重写可以更换item
    private void initRecycle() {
        // 设置布局方向
        mRvRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mListDataAdapter = new ListDataAdapter(getActivity(), mPresenter.getData());
        // 装饰上加载更多
        mLoadMoreDecorate = new LoadMoreDecorate(mListDataAdapter);
        // 设置适配器
        mRvRecycler.setAdapter(mLoadMoreDecorate);
        // 设置间距，后发现使用CardView不需要设置边距，因为CardView自身为了显示阴影留有边距
        // mRvRecycler.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
    }

    boolean isFirst = true;

    @Override
    protected void initData() {
        if (isFirst) {
            isFirst = false;
            mPresenter.setData();
        } else {
            // 不必每次都重新加载
            getLoadingPager().setCurrentState(LoadingPager.PAGE_STATE.LOADING_SUCCESS);
        }
    }

    @Override
    protected void setListener() {
        mSrlRefresh.setOnRefreshListener(this);
        mLoadMoreDecorate.setOnLoaderMoreListener(new LoadMoreDecorate.OnLoaderMoreListener() {
            @Override
            public void onLoader() {
                mPresenter.loadMore();
            }
        });
        // recycle长按的回调
        mListDataAdapter.setOnItemLongClickListener(new ListDataAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, final ListData.ListBean listBean, int position) {
                // 弹出保存按钮
                PopupWindowHelper.getPopupWindow(v, getContext(), "保存", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.saveData(listBean);
                    }
                });
            }
        });
    }

    @Override
    public View getSuccessView() {
        return mRootView;
    }

    @Override
    public void fillData(List<ListData.ListBean> data) {
        // 如果已经有数据，不去刷掉老数据
        if (data == null && mListDataAdapter.getItemCount() == 0) {
            getLoadingPager().setCurrentState(LoadingPager.PAGE_STATE.LOADING_ERROR);
        } else {
            mListDataAdapter.notifyDataSetChanged();
            getLoadingPager().setCurrentState(LoadingPager.PAGE_STATE.LOADING_SUCCESS);
            // 回调加载成功
            if (onLoadingSuccessListener != null) {
                onLoadingSuccessListener.onLoadingSuccess();
            }
        }
        mSrlRefresh.setRefreshing(false);
    }

    @Override
    public void loadMore(List<ListData.ListBean> data) {
        if (data == null)
            mLoadMoreDecorate.setLoadingState(LoadMoreDecorate.state_error);
        else if (data.size() == 0) {
            mLoadMoreDecorate.setLoadingState(LoadMoreDecorate.state_no_data);
        } else {
            mListDataAdapter.notifyDataSetChanged();
            mLoadMoreDecorate.setLoadingState(LoadMoreDecorate.state_loading);
        }
    }

    @Override
    public void refresh() {
        getLoadingPager().setCurrentState(LoadingPager.PAGE_STATE.LOADING);
    }

    @Override
    public void showTips(String message) {
        SnackBarUtil.show(mRvRecycler, message);
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        this.mPresenter = (ListDataPresenter) presenter;
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    public void setOnLoadingSuccessListener(OnLoadingSuccessListener lsl) {
        this.onLoadingSuccessListener = lsl;
    }

    public interface OnLoadingSuccessListener {
        void onLoadingSuccess();
    }
}
