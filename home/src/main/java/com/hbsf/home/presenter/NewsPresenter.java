package com.hbsf.home.presenter;

import com.hbsf.base.mvp.presenter.BasePresenter;
import com.hbsf.base.rx.BaseObserver;
import com.hbsf.common.rx.RxScheduler;
import com.hbsf.home.api.NewsContract;
import com.hbsf.home.model.NewsModel;

public class NewsPresenter extends BasePresenter<NewsContract.View, NewsContract.Model> implements NewsContract.Persenter{
    public NewsPresenter(NewsContract.View view) {
        super(view);
    }

    @Override
    public NewsContract.Model getModel() {
        return new NewsModel(this);
    }

    @Override
    public void loadNewsList() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached() || !isModelAttached()) {
            return;
        }
        mModel.getNewsList(0)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new BaseObserver(mView, mModel));
    }
}
