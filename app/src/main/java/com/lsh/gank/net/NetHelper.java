package com.lsh.gank.net;

import com.lsh.gank.bean.ListData;
import com.lsh.gank.global.App;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public class NetHelper {
    private static Retrofit sRetrofit = null;
    private static OkHttpClient sOkHttpClient = null;
    public static String baseUrl = "http://gank.io/api/";
    private static GankApi sGankApi;

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
        sOkHttpClient = okBuilder.build();


        Retrofit.Builder builder = new Retrofit.Builder();
        sRetrofit = builder.baseUrl(baseUrl)
                .client(sOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        sGankApi = sRetrofit.create(GankApi.class);
    }


    public static GankApi getApi() {
        return sGankApi;
    }

    public interface GankApi {
        @GET("data/{category}/{count}/{page}")
        Observable<ListData> getProjectAll(@Path("category") String category, @Path("count") int count, @Path("page") int page);

    }

}
