package com.lsh.gank.ui.fragment.all;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsh.gank.R;
import com.lsh.gank.ui.adapter.BaseFragmentPageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * "所有"的fragment
 */

public class AllFragment extends Fragment {

    @BindView(R.id.tl_tablayout)
    TabLayout mTlTablayout;
    @BindView(R.id.vp_viewpager)
    ViewPager mVpViewpager;
    private View mRootView;

    private BaseFragmentPageAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.layout_main_content_all, null);
            ButterKnife.bind(this, mRootView);

            // 设置tablayout的初始化，和其他的indicate很相似
            mTlTablayout.setupWithViewPager(mVpViewpager);
            mAdapter = new BaseFragmentPageAdapter(getChildFragmentManager());
            mVpViewpager.setAdapter(mAdapter);
        }
        return mRootView;
    }

}
