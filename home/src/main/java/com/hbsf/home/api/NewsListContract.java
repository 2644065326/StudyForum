package com.hbsf.home.api;

import com.hbsf.base.api.IBaseModel;
import com.hbsf.base.api.IBasePresenter;
import com.hbsf.base.bean.BaseArrayBean;
import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.mvp.view.IBaseMVPView;
import com.hbsf.home.bean.NewsBean;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface NewsListContract {

    interface Model extends IBaseModel {
        Observable<BaseArrayBean<NewsBean>> loadNextNewsList(boolean isRefresh);
        void loadCacheNews();

    }

    interface View extends IBaseMVPView {
        void showNews(List<NewsBean> data);
    }


    interface Persenter extends IBasePresenter<NewsListContract.View, NewsListContract.Model> {
        void showNews(List<NewsBean> data);
        void loadCacheNews();
        void loadNextPage(boolean isRefresh);
    }
}
