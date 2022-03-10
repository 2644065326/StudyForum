package com.hbsf.home.api;

import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.home.bean.NewsBean;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NewsApi {

    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseObjectBean<NewsBean>> loadNewsList(@Field("page") int page);
}
