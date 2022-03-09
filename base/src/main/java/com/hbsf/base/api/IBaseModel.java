package com.hbsf.base.api;

import com.hbsf.base.bean.BaseObjectBean;

public interface IBaseModel<T> {
    void handleResult(BaseObjectBean<T> t);
}
