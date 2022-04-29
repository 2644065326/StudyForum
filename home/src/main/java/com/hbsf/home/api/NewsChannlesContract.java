package com.hbsf.home.api;

import com.hbsf.base.api.IBaseModel;
import com.hbsf.base.api.IBasePresenter;
import com.hbsf.base.bean.BaseArrayBean;
import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.mvp.view.IBaseMVPView;
import com.hbsf.home.bean.ChannelBean;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface NewsChannlesContract {
    public enum Type {
        NEWS,
        COMMUNITY;
    }

    interface Model extends IBaseModel {
        Observable<BaseArrayBean<ChannelBean>> loadNewsChannlesList();
        void loadCacheChannels();
    }

    interface View extends IBaseMVPView {
        void creatChannels(List<ChannelBean> channelsBean);

    }


    interface Persenter extends IBasePresenter<NewsChannlesContract.View, NewsChannlesContract.Model> {
        void loadNewsChannelList();
        void changeChannels(List<ChannelBean>  listBean);
        void loadCacheChannels();
    }
}
