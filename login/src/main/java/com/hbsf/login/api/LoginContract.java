package com.hbsf.login.api;


import com.hbsf.base.api.IBaseModel;
import com.hbsf.base.api.IBasePresenter;
import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.mvp.view.IBaseMVPView;
import com.hbsf.login.bean.LoginBean;

import io.reactivex.rxjava3.core.Observable;

public interface LoginContract {

    interface Model extends IBaseModel {
        Observable<BaseObjectBean<LoginBean>> login(String username, String password);
    }

    interface View extends IBaseMVPView {
        void loginSuccess();
    }


    interface Persenter extends IBasePresenter<LoginContract.View, LoginContract.Model> {
        void login(String userName, String passWord);
        void loginSuccess();
    }
}
