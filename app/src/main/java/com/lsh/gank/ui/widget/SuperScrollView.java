package com.lsh.gank.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * 仿qq滑动效果
 */

public class SuperScrollView extends ScrollView {

    private View mChild;
    
    public SuperScrollView(Context context) {
        super(context);
        init();
    }

    public SuperScrollView(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
    }

    public SuperScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        setOverScrollMode(ScrollView.OVER_SCROLL_NEVER);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mChild = getChildAt(0);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        // 让内部的子控件统一向下移动，只需判断是否为手拖动即可，注意deltaY需要取反
        if (isTouchEvent) {
            ViewCompat.setTranslationY(mChild, mChild.getTranslationY() - deltaY/3);
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // 松开手指后子控件回到原位
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            mChild.animate().translationY(0).start();
        }
        return super.onTouchEvent(ev);
    }
}
