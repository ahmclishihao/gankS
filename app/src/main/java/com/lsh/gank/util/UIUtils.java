package com.lsh.gank.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsh.gank.global.App;

public class UIUtils {

    public static Context getContext() {
        return App.sContext;
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static View inflateView(Context context, int layoutId, ViewGroup parent) {
        if (context == null)
            context = getContext();
        if (parent == null) {
            return View.inflate(context, layoutId, null);
        }
        // recycleView在设置item的时候需要设置父控件
        return LayoutInflater.from(context).inflate(layoutId, parent, false);
    }

}
