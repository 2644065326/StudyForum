package com.hbsf.home.api;

import com.hbsf.base.api.IBaseModel;
import com.hbsf.base.api.IBasePresenter;
import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.mvp.view.IBaseMVPView;
import com.hbsf.home.bean.NewsBean;

import io.reactivex.rxjava3.core.Observable;

public interface NewsContract {

    interface Model extends IBaseModel<NewsBean> {
        Observable<BaseObjectBean<NewsBean>> getNewsList(int page);
    }

    interface View extends IBaseMVPView {

    }


    interface Persenter extends IBasePresenter<NewsContract.View, NewsContract.Model> {

    }
}
