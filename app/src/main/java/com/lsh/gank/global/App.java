package com.lsh.gank.global;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.File;


public class App extends Application {
    public static Context sContext;
    public static File cacheDorectory;
    public static File picDirectory;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        initDir();
//        initX5();

    }

    /**
     * 初始缓存路径
     */
    private void initDir() {
        // 缓存路径
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDorectory = sContext.getExternalCacheDir();
            picDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "gank_pics");
        } else {
            cacheDorectory = sContext.getCacheDir();
            picDirectory = new File(sContext.getFilesDir(), "gank_pics");
        }
        mkDir(picDirectory);
    }

    public void mkDir(File dir) {
        if (dir.exists() && !dir.isDirectory())
            dir.delete();
        else if (!dir.exists())
            dir.mkdir();
    }

    private void initX5() {
//        QbSdk.PreInitCallback callback = new QbSdk.PreInitCallback() {
//
//            @Override
//            public void onCoreInitFinished() {
//                Log.i("Application_X5", "onCoreInitFinished");
//            }
//
//            @Override
//            public void onViewInitFinished(boolean b) {
//                Log.i("Application_X5", "onViewInitFinished " + b);
//            }
//        };
//        QbSdk.initX5Environment(sContext, callback);
    }
}
