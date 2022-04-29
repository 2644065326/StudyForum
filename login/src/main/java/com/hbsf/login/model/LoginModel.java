package com.hbsf.login.model;

import com.hbsf.base.bean.BaseBean;
import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.model.BaseModel;
import com.hbsf.common.net.RetrofitClient;
import com.hbsf.common.utils.UserUtils;
import com.hbsf.login.api.LoginApi;
import com.hbsf.login.api.LoginContract;
import com.hbsf.login.bean.LoginBean;
import com.hbsf.login.presenter.LoginPresenter;

import io.reactivex.rxjava3.core.Observable;

public class LoginModel extends BaseModel<LoginContract.Persenter, LoginApi> implements LoginContract.Model {


    public LoginModel(LoginContract.Persenter loginPresenter) {
        super(loginPresenter);
        setmApi(RetrofitClient.getInstance().create(LoginApi.class));
    }

    @Override
    public Observable<BaseObjectBean<LoginBean>> login(String username, String password) {
        return getmApi().login(username,password);
    }



    private void loginSuccess(LoginBean result) {
        updataUserInfo(result);
        getPresenter().loginSuccess();

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
        String desc = bean.getDesc();
        UserUtils.updataUserInfo(email,icon, id, password, type, username, desc);
    }

    @Override
    public void handleResult(BaseBean t) {

        if (t instanceof BaseObjectBean) {
            loginSuccess((LoginBean)((BaseObjectBean)t).getResult());
        }

    }
}
