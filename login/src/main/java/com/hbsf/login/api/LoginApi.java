package com.hbsf.login.api;

import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.login.bean.LoginBean;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApi {

    @FormUrlEncoded
    @POST("studyforum/login")
    Observable<BaseObjectBean<LoginBean>> login(@Field("username") String username,
                                                @Field("password") String password);

}
