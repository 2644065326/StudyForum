package com.hbsf.login.presenter;

import com.hbsf.base.mvp.presenter.BasePresenter;

import com.hbsf.base.rx.BaseObserver;
import com.hbsf.common.rx.RxScheduler;
import com.hbsf.login.api.LoginContract;
import com.hbsf.login.model.LoginModel;


public class LoginPresenter extends BasePresenter<LoginContract.View, LoginContract.Model> implements LoginContract.Persenter {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String userName, String passWord) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached() || !isModelAttached()) {
            return;
        }
        mModel.login(userName, passWord)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new BaseObserver(mView, mModel));
    }

    @Override
    public void loginSuccess() {
        mView.loginSuccess();
    }


    @Override
    public LoginContract.Model getModel() {
        return new LoginModel(this);
    }

}
