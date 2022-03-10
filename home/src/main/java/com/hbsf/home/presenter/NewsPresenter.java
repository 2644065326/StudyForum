package com.hbsf.home.presenter;

import com.hbsf.base.mvp.presenter.BasePresenter;
import com.hbsf.base.rx.BaseObserver;
import com.hbsf.common.rx.RxScheduler;
import com.hbsf.home.api.NewsChannlesContract;
import com.hbsf.home.bean.NewsChannelsListBean;
import com.hbsf.home.model.NewsModel;

public class NewsPresenter extends BasePresenter<NewsChannlesContract.View, NewsChannlesContract.Model> implements NewsChannlesContract.Persenter{
    public NewsPresenter(NewsChannlesContract.View view) {
        super(view);
    }

    @Override
    public NewsChannlesContract.Model getModel() {
        return new NewsModel(this);
    }




    @Override
    public void loadNewsChannelList() {
        if (!isViewAttached() || !isModelAttached()) {
            return;
        }
        mModel.loadNewsChannlesList()
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new BaseObserver(mView, mModel));
    }

    @Override
    public void changeChannels(NewsChannelsListBean listBean) {
        mView.creatChannels(listBean);
    }

    @Override
    public void loadCacheChannels() {
        mModel.loadCacheChannels();
    }

}
