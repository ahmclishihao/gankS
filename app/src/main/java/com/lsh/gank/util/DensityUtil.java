package com.lsh.gank.util;

import android.content.Context;

public class DensityUtil {
	public static float dp2px(Context context, float dp) {
		float density = context.getResources().getDisplayMetrics().density;
		return dp * density + 0.5f;// 四舍五入
	}

	public static float px2dp(Context context, float px) {
		float density = context.getResources().getDisplayMetrics().density;
		return px / density + 0.5f;
	}
}
