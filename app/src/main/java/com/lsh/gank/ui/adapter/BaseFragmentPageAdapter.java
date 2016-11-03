package com.lsh.gank.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lsh.gank.ui.fragment.all.PageFactory;

/**
 * 主界面中，“所有”fragment的adapter
 */

public class BaseFragmentPageAdapter extends FragmentPagerAdapter {
    public static String[] mTitles = {"Android", "iOS", "前端", "拓展资源", "福利", "休息视频"};
    private final PageFactory mPageFactory;


    public BaseFragmentPageAdapter(FragmentManager fm) {
        super(fm);
        // 创建一个工厂
        mPageFactory = new PageFactory();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mPageFactory.getFragment(getPageTitle(position).toString());
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
