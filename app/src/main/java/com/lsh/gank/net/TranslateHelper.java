package com.lsh.gank.net;

import com.lsh.gank.bean.WordData;
import com.lsh.gank.global.App;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 有道翻译的API
 */

public class TranslateHelper {
    static String host_address = "http://fanyi.youdao.com/";
    static String keyfrom = "ai-fan";// 可更换
    static String key = "1419528210";// 可更换
    static String type = "data";
    static String doctype = "json";
    static String version = "1.1";

    static Retrofit mRetrofit = null;
    static OkHttpClient sClient;
    static TranslateApi sTranslateApi;

    static {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        // 缓存路径
        File cacheDir = App.cacheDorectory;

        if (cacheDir != null) {
            File netData = new File(cacheDir, "NetData");
            if (!netData.exists()) {
                netData.mkdir();
                okBuilder.cache(new Cache(netData, 5 * 1024 * 1024));
            }
        }

        // 添加日志打印
//        okBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        sClient = okBuilder.build();


        mRetrofit = new Retrofit.Builder().baseUrl(host_address)
                .client(sClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        sTranslateApi = mRetrofit.create(TranslateApi.class);
    }

    public static Observable<WordData> getTranslateApi(String statement) {
        return ObservableDecorate.decortateIO(sTranslateApi.translate(keyfrom, key, type, doctype, version, statement));
    }

    public interface TranslateApi {
        @GET("openapi.do")
        Observable<WordData> translate(@Query("keyfrom") String keyfrom, @Query("key") String key, @Query("type") String type, @Query("doctype") String doctype,
                                       @Query("version") String version, @Query("q") String word);
    }
}
