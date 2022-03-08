package com.hbsf.login.api;


import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.api.IBaseView;
import com.hbsf.base.model.BaseModel;
import com.hbsf.base.mvp.view.IBaseMVPView;
import com.hbsf.login.bean.LoginBean;

import io.reactivex.rxjava3.core.Observable;

public interface LoginContract {

    interface Model {
        Observable<BaseObjectBean<LoginBean>> login(String username, String password);
    }

    interface View {
        void onSuccess(BaseObjectBean<LoginBean> bean);

    }
    interface Persenter {
        void login(String userName, String passWord);
    }
}
