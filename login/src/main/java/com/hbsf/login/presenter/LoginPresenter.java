package com.hbsf.login.presenter;

import com.hbsf.base.api.IBaseView;
import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.model.BaseModel;
import com.hbsf.base.mvp.presenter.BasePresenter;

import com.hbsf.base.rx.BaseObserver;
import com.hbsf.common.rx.RxScheduler;
import com.hbsf.login.api.LoginContract;
import com.hbsf.login.bean.LoginBean;
import com.hbsf.login.model.LoginModel;
import com.hbsf.login.view.LoginActivity;

import autodispose2.AutoDispose;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
    public void loginFail(String msg) {
        mView.loginFail(msg);
    }


    @Override
    public LoginContract.Model getModel() {
        return new LoginModel(this);
    }

}
