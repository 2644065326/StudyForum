package com.hbsf.home.presenter;

import com.hbsf.base.mvp.presenter.BasePresenter;
import com.hbsf.base.rx.BaseObserver;
import com.hbsf.common.rx.RxScheduler;
import com.hbsf.home.api.NewsListContract;
import com.hbsf.home.bean.NewsListBean;
import com.hbsf.home.model.NewsModel;

import java.util.List;

public class NewsPresenter extends BasePresenter<NewsListContract.View, NewsListContract.Model> implements NewsListContract.Persenter {
    private String channelId;
    private String channelName;
    private String channelType;
    public NewsPresenter(NewsListContract.View view, String channelId, String channelName, String channelType) {
        super(view, false);
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelType = channelType;
        mModel = getModel();
    }


    @Override
    public NewsListContract.Model getModel() {
        return new NewsModel(this, channelId, channelName, channelType);
    }

    @Override
    public void showNews(List<NewsListBean.NewsBean> data) {
        mView.showNews(data);
    }

    @Override
    public void loadCacheNews() {
        mModel.loadCacheNews();
    }

    @Override
    public void loadNextPage(boolean isRefresh) {
        if (!isViewAttached() || !isModelAttached()) {
            return;
        }
        if (isRefresh) {
            mModel.loadNextNewsList(true)
                    .compose(RxScheduler.Obs_io_main())
                    .to(mView.bindAutoDispose())
                    .subscribe(new BaseObserver(mView, mModel));
        } else {
            mModel.loadNextNewsList(false)
                    .compose(RxScheduler.Obs_io_main())
                    .to(mView.bindAutoDispose())
                    .subscribe(new BaseObserver(mView, mModel));
        }

    }

}
