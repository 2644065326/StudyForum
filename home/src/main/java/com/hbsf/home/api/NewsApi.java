package com.hbsf.home.api;

import com.hbsf.base.bean.BaseArrayBean;
import com.hbsf.home.bean.ChannelBean;
import com.hbsf.home.bean.NewsBean;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    //@FormUrlEncoded
    @GET("studyforum/news")
    Observable<BaseArrayBean<NewsBean>> loadNewsList(@Query("channelType") String channelType,
                                                     @Query("channelId") String channelId,
                                                     @Query("channelName") String channelName,
                                                     @Query("page") String page);

    @GET("studyforum/channel")
    Observable<BaseArrayBean<ChannelBean>> getNewsChannels(@Query("type") String channelType);



}
