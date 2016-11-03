package com.lsh.gank.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.lsh.gank.R;

public class PopupWindowHelper {

    /**
     * 弹出一个popupwindow
     */
    public static void getPopupWindow(View anchor, Context context, String text, final View.OnClickListener cl) {

        Button button = new Button(context);
        button.setBackgroundResource(R.drawable.selector_btn_save);
        button.setText(text);
        button.setTextColor(Color.BLACK);
        button.setPadding(10, 5, 10, 5);
        button.setOnClickListener(cl);
        button.measure(0, 0);

        final PopupWindow popupWindow = new PopupWindow(button, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置背景和focusable使得可以点击消失
        popupWindow.setFocusable(true);
        if (Build.VERSION.SDK_INT >= 21)
            popupWindow.setElevation(5);
        popupWindow.showAsDropDown(anchor, anchor.getMeasuredWidth() / 2 - button.getMeasuredWidth() / 2, -anchor.getMeasuredHeight() / 2 - button.getMeasuredHeight() / 2);

        // 回调 提供的监听
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 两个View.OnClickListener不冲突
                cl.onClick(v);
                popupWindow.dismiss();
            }
        });
    }

}
