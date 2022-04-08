package com.hbsf.home.api;

import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.home.bean.NewsChannelsListBean;
import com.hbsf.home.bean.NewsListBean;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NewsApi {

    //@FormUrlEncoded
    @GET("studyforum/news")
    Observable<BaseObjectBean<NewsListBean>> loadNewsList(@Query("channelType") String channelType,
                                                          @Query("channelId") String channelId,
                                                          @Query("channelName") String channelName,
                                                          @Query("page") String page);

    @GET("studyforum/channel")
    Observable<BaseObjectBean<NewsChannelsListBean>> getNewsChannels(@Query("type") String channelType);



}
