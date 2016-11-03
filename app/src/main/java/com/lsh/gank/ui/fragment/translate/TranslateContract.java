package com.lsh.gank.ui.fragment.translate;

import android.content.ClipboardManager;

import com.lsh.gank.bean.WordData;
import com.lsh.gank.ui.BasePresenter;
import com.lsh.gank.ui.BaseView;

public interface TranslateContract {

    interface TranslateView extends BaseView {
        void fillData(WordData wordData); // 填充数据
        void clear(); // 清理数据
    }

    interface Presenter extends BasePresenter {
        void translate(String string); // 翻译数据
        void clear(); // 清理数据
        void copy(CharSequence str, ClipboardManager cm); // copy数据
    }
}
