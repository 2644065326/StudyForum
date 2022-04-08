package com.hbsf.home.presenter;

import com.hbsf.base.mvp.presenter.BasePresenter;
import com.hbsf.base.rx.BaseObserver;
import com.hbsf.common.rx.RxScheduler;
import com.hbsf.home.api.NewsChannlesContract;
import com.hbsf.home.bean.NewsChannelsListBean;
import com.hbsf.home.model.NewsChannelModel;

public class NewsChannelsPresenter extends BasePresenter<NewsChannlesContract.View, NewsChannlesContract.Model> implements NewsChannlesContract.Persenter{
    private NewsChannlesContract.Type type;
    public NewsChannelsPresenter(NewsChannlesContract.View view, NewsChannlesContract.Type type) {
        super(view, false);
        this.type = type;
        mModel = getModel();
    }

    @Override
    public NewsChannlesContract.Model getModel() {
        return new NewsChannelModel(this, type);
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
