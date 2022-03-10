package com.hbsf.base.model;

import com.hbsf.base.api.IBaseModel;
import com.hbsf.base.api.IBasePresenter;
import com.hbsf.base.bean.BaseObjectBean;

public abstract class BaseModel<T, P extends IBasePresenter, A> implements IBaseModel<T>{
    private P mPresenter;
    private A mApi;

    public BaseModel(P p) {
        this.mPresenter = p;
    }
    public P getPresenter() {
        if (mPresenter != null);
        return mPresenter;
    }

    public void setmPresenter(P mPresenter) {
        this.mPresenter = mPresenter;
    }

    public void setmApi(A mApi) {
        this.mApi = mApi;
    }

    public A getmApi() {
        return mApi;
    }
}
