package com.lsh.gank.ui;

public interface BaseView {
    void setPresenter(BasePresenter presenter); // 设置协调者

    void showTips(String message); // 弹出提示
}
