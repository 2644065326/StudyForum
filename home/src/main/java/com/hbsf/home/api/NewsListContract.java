package com.hbsf.home.api;

import com.hbsf.base.api.IBaseModel;
import com.hbsf.base.api.IBasePresenter;
import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.mvp.view.IBaseMVPView;
import com.hbsf.home.bean.NewsListBean;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface NewsListContract {

    interface Model extends IBaseModel {
        Observable<BaseObjectBean<NewsListBean>> loadNextNewsList(boolean isRefresh);
        void loadCacheNews();

    }

    interface View extends IBaseMVPView {
        void showNews(List<NewsListBean.NewsBean> data);
    }


    interface Persenter extends IBasePresenter<NewsListContract.View, NewsListContract.Model> {
        void showNews(List<NewsListBean.NewsBean> data);
        void loadCacheNews();
        void loadNextPage(boolean isRefresh);
    }
}
