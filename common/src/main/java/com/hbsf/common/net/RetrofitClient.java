package com.hbsf.common.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static volatile Retrofit instance;
    private static String baseUrl = "https://111";
    private static OkHttpClient okHttpClient;

    public static Retrofit getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new Retrofit.Builder()
                            //设置网络请求的Url地址
                            .baseUrl(baseUrl)
                            //设置数据解析器
                            .addConverterFactory(GsonConverterFactory.create())
                            //设置网络请求适配器，使其支持RxJava与RxAndroid
                            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                            .client(getOkHttpClient())
                            .build();
                }
            }
        }
        return instance;
    }


    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(1, TimeUnit.SECONDS)
                    .readTimeout(1, TimeUnit.SECONDS)
                    .build();

        }
        return okHttpClient;
    }


}
