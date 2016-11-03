package com.lsh.gank.ui.behavior;

import android.animation.Animator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;


/**
 * 自定义向下隐藏的behavior，可用于fab中
 */

public class AnimatorDownBehavior extends CoordinatorLayout.Behavior<View> {
    public boolean isOpen = true;
    public boolean isAnima = false;

    public AnimatorDownBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        // 根据坐标系方向判断是否响应事件
        return (nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        // 类似于scrollOver
        if (dyConsumed < 0) {
            // 小于0 手指向下划 , fab显示
            animatIn(child);
        } else {
            // fab 隐藏
            animatOut(child);
        }

    }

    //fba显示的动画
    private void animatIn(View target) {
        if (!isAnima && !isOpen) {
            ViewPropertyAnimator viewPropertyAnimator = target.animate().setInterpolator(new FastOutSlowInInterpolator()).translationY(0).setDuration(300);
            viewPropertyAnimator.start();
            viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isAnima = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isOpen = true;
                    isAnima = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }

    //fba隐藏的动画
    private void animatOut(View target) {
        if (!isAnima && isOpen) {
            ViewPropertyAnimator viewPropertyAnimator = target.animate().setInterpolator(new FastOutSlowInInterpolator()).translationY(300).setDuration(300);
            viewPropertyAnimator.start();
            viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isAnima = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isOpen = false;
                    isAnima = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }
}
