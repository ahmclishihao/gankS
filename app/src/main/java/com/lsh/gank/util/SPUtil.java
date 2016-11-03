package com.lsh.gank.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Li on 2016/10/27.
 */

public class SPUtil {
    private static String spName = "sp_setting";
    private static SharedPreferences mSharedPreferences;

    public static String getString(Context context, String key, String def) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getString(key, def);
    }

    public static void setString(Context context, String key, String value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putString(key, value).commit();
    }

}
