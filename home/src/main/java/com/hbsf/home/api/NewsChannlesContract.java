package com.hbsf.home.api;

import com.hbsf.base.api.IBaseModel;
import com.hbsf.base.api.IBasePresenter;
import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.mvp.view.IBaseMVPView;
import com.hbsf.home.bean.NewsChannelsListBean;
import com.hbsf.home.bean.NewsListBean;

import io.reactivex.rxjava3.core.Observable;

public interface NewsChannlesContract {

    interface Model extends IBaseModel {
        Observable<BaseObjectBean<NewsChannelsListBean>> loadNewsChannlesList();
        void loadCacheChannels();
    }

    interface View extends IBaseMVPView {
        void creatChannels(NewsChannelsListBean channelsBean);

    }


    interface Persenter extends IBasePresenter<NewsChannlesContract.View, NewsChannlesContract.Model> {
        void loadNewsChannelList();
        void changeChannels(NewsChannelsListBean listBean);
        void loadCacheChannels();
    }
}
