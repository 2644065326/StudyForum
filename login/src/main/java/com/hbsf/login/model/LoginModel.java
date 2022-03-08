package com.hbsf.login.model;

import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.model.BaseModel;
import com.hbsf.common.net.RetrofitClient;
import com.hbsf.login.api.LoginApi;
import com.hbsf.login.api.LoginContract;
import com.hbsf.login.bean.LoginBean;

import io.reactivex.rxjava3.core.Observable;

public class LoginModel extends BaseModel implements LoginContract.Model {
    private LoginApi loginApi;
    public LoginModel() {
        if (loginApi == null) {
            loginApi = RetrofitClient.getInstance().create(LoginApi.class);
        }
    }

    @Override
    public Observable<BaseObjectBean<LoginBean>> login(String username, String password) {
        return loginApi.login(username,password);
    }
}
