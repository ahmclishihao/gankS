package com.lsh.gank.ui.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsh.gank.R;
import com.lsh.gank.ui.activity.favorite.FavoriteActivity;

/**
 * "home"的fragment
 */

public class HomeFragment extends Fragment {
    private View mRootView;
    private String mTitle;
    // FIXME 因为主题切换导致activity更换，所以此处不能使用static保存fragment，因为fragment有对应的activity
    private ListDataFragment mListDataFragment;
    private FloatingActionButton mFAB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString("title");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.layout_main_content_home, null);
            initFab();
            initFragment();
        }
        return mRootView;
    }

    /**
     * 初始时fab的状态
     */
    private void initFab() {
        mFAB = (FloatingActionButton) mRootView.findViewById(R.id.fab);
        mFAB.setVisibility(View.GONE);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FavoriteActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * fragment的添加
     */
    private void initFragment() {
        if (mListDataFragment == null) {
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();

            // 添加ListDataFragment
            mListDataFragment = new ListDataFragment();

            // 当内部布局加载成功后显示fab，避免加载时fab的位移
            mListDataFragment.setOnLoadingSuccessListener(new ListDataFragment.OnLoadingSuccessListener() {
                @Override
                public void onLoadingSuccess() {
                    mFAB.setVisibility(View.VISIBLE);
                }
            });
            // fragment添加的参数
            Bundle bundle = new Bundle();
            bundle.putString("title", mTitle);
            mListDataFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.fl_content, mListDataFragment, "home");
            fragmentTransaction.commit();

        }
    }
}
