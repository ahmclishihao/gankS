package com.lsh.gank.ui.fragment.all;

import android.os.Bundle;

import com.lsh.gank.ui.fragment.home.ListDataFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于生产page页面的工厂。
 * 注意：主题切换会导致activity重新更换，所以不能使用静态工厂，fragment不能被静态保存
 */

public class PageFactory {


    private Map<String, ListDataFragment> sFragmentMap = new HashMap<>();

    // 根据对应的标题，获取对应的fragment
    public ListDataFragment getFragment(String title) {
        ListDataFragment fragment = sFragmentMap.get(title);

        if (fragment == null) {
            // 填入参数，指定page对应的api
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            fragment = new ListDataFragment();
            fragment.setArguments(bundle);
            sFragmentMap.put(title, fragment);
        }
        return fragment;
    }

}
