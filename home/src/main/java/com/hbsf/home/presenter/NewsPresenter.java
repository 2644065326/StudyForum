package com.hbsf.home.presenter;

import com.hbsf.base.mvp.presenter.BasePresenter;
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
}
