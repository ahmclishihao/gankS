package com.lsh.gank.enums;

import android.support.v4.app.Fragment;

import com.lsh.gank.R;
import com.lsh.gank.ui.fragment.all.AllFragment;
import com.lsh.gank.ui.fragment.home.HomeFragment;
import com.lsh.gank.ui.fragment.profile.ProfileFragment;
import com.lsh.gank.ui.fragment.translate.TranslateFragment;


public enum TabMenus {
    TAB_HOME("home", R.drawable.ic_bottomtabbar_feed, HomeFragment.class, new KeyValue("title", "all")),
    TAB_ALL("all", R.drawable.ic_bottomtabbar_discover, AllFragment.class),
    TAB_CUSTOM("custom", R.drawable.ic_bottomtabbar_message, TranslateFragment.class),
    TAB_MORE("more", R.drawable.ic_bottomtabbar_more, ProfileFragment.class);

    String tag;
    Class<? extends Fragment> clazz;
    int iconId;
    KeyValue[] arguments = null;

    TabMenus(String tag, int iconId, Class<? extends Fragment> clazz, KeyValue... arguments) {
        this.tag = tag;
        this.iconId = iconId;
        this.clazz = clazz;
        this.arguments = arguments;
    }

    public String getTag() {
        return tag;
    }

    public Class<? extends Fragment> getClazz() {
        return clazz;
    }

    public int getIconId() {
        return iconId;
    }

    public KeyValue[] getArguments() {
        return arguments;
    }
}
