package com.lsh.gank.util;

import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;

/**
 * glide加载图片
 */
public class GlideUtil {

    /**
     * 加载静态图片，非gif
     */
    public static void loadStaticPic(ImageView view, String path) {
        DrawableTypeRequest<String> load = Glide.with(view.getContext())
                .load(path+"?imageView2/0/w/500");
        // 渐入效果
        load.crossFade();
        load.asBitmap().into(view);
    }

}
