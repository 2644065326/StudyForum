package com.hbsf.base.model;

import com.hbsf.base.api.IBaseModel;
import com.hbsf.base.api.IBasePresenter;
import com.hbsf.base.bean.BaseObjectBean;

public abstract class BaseModel<T, P extends IBasePresenter> implements IBaseModel<T>{
    private P mPresenter;
    public BaseModel(P p) {
        this.mPresenter = p;
    }
    public P getPresenter() {
        if (mPresenter != null);
        return mPresenter;
    }

}
