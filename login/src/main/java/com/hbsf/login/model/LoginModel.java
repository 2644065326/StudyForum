package com.hbsf.login.model;

import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.model.BaseModel;
import com.hbsf.common.net.RetrofitClient;
import com.hbsf.common.utils.UserUtils;
import com.hbsf.login.api.LoginApi;
import com.hbsf.login.api.LoginContract;
import com.hbsf.login.bean.LoginBean;
import com.hbsf.login.presenter.LoginPresenter;

import io.reactivex.rxjava3.core.Observable;

public class LoginModel extends BaseModel<LoginBean, LoginContract.Persenter> implements LoginContract.Model {
    private LoginApi loginApi;

    public LoginModel(LoginContract.Persenter loginPresenter) {
        super(loginPresenter);
        loginApi = RetrofitClient.getInstance().create(LoginApi.class);
    }

    @Override
    public Observable<BaseObjectBean<LoginBean>> login(String username, String password) {
        return loginApi.login(username,password);
    }



    private void loginSuccess(LoginBean result) {
        updataUserInfo(result);
        getPresenter().loginSuccess();

    }

    private void loginFail(String errorMsg) {
        getPresenter().loginFail(errorMsg);
    }

    private void updataUserInfo(LoginBean bean) {
        if (bean == null) {
            return;
        }
        String email = bean.getEmail();
        String icon = bean.getIcon();
        String id = bean.getId() + "";
        String password = bean.getPassword();
        int type = bean.getType();
        String username = bean.getUsername();
        UserUtils.updataUserInfo(email,icon, id, password, type, username);
    }


    @Override
    public void handleResult(BaseObjectBean<LoginBean> t) {
        if (t.getErrorCode() != 1) {
            loginFail(t.getErrorMsg());
        } else {
            loginSuccess(t.getResult());
        }
    }
}
