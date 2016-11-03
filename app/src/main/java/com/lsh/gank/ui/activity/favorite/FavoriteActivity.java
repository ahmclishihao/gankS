package com.lsh.gank.ui.activity.favorite;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.lsh.gank.R;
import com.lsh.gank.bean.FavoriteData;
import com.lsh.gank.bean.ListData;
import com.lsh.gank.ui.BasePresenter;
import com.lsh.gank.ui.activity.MainActivity;
import com.lsh.gank.ui.adapter.ListDataAdapter;
import com.lsh.gank.ui.widget.PopupWindowHelper;
import com.lsh.gank.util.SnackBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

/**
 * 个人收藏界面
 */

public class FavoriteActivity extends Activity implements FavoriteContract.FavoriteView {
    @BindView(R.id.rl_error)
    RelativeLayout mRlError;
    @BindView(R.id.rl_recycler)
    RecyclerView mRlRecycler;
    private FavoritePresenter mFavoritePresenter;
    private ListDataAdapter mListDataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mFavoritePresenter = new FavoritePresenter(this);
        mFavoritePresenter.getData();
    }

    private void initView() {
        setTheme(MainActivity.getThemeId());

        setContentView(R.layout.layout_favorite_activity);
        ButterKnife.bind(this);
        mRlError.setVisibility(View.VISIBLE);
        mRlRecycler.setVisibility(GONE);
    }

    // 填充数据
    @Override
    public void fillData(FavoriteData data) {
        if (data == null || data.mListBeen == null || data.mListBeen.size() == 0) {
            failData();
            return;
        }
        mRlError.setVisibility(View.GONE);
        mRlRecycler.setVisibility(View.VISIBLE);
        if (mListDataAdapter == null) {
            mListDataAdapter = new ListDataAdapter(this, data.mListBeen);
            mRlRecycler.setLayoutManager(new LinearLayoutManager(this));
            mRlRecycler.setAdapter(mListDataAdapter);
            mListDataAdapter.setOnItemLongClickListener(new ListDataAdapter.OnItemLongClickListener() {
                @Override
                public void onItemLongClick(View v, final ListData.ListBean listBean, final int position) {
                    PopupWindowHelper.getPopupWindow(v, FavoriteActivity.this, "删除", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mFavoritePresenter.deleteData(listBean, position);
                        }
                    });
                }
            });
        }
    }

    // 没数据时
    @Override
    public void failData() {
        mRlError.setVisibility(View.VISIBLE);
        mRlRecycler.setVisibility(View.GONE);
    }

    @Override
    public void deleteData(int position) {
        if (mListDataAdapter != null) {
            mListDataAdapter.notifyItemRemoved(position);
        }
    }

    @Override
    public void showTips(String message) {
        SnackBarUtil.show(mRlRecycler, message);
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        this.mFavoritePresenter = (FavoritePresenter) presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFavoritePresenter != null)
            mFavoritePresenter.onDestroy();
    }
}
