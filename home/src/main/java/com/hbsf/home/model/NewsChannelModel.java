package com.hbsf.home.model;

import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.model.BaseModel;
import com.hbsf.common.net.RetrofitClient;
import com.hbsf.home.api.NewsApi;
import com.hbsf.home.api.NewsChannlesContract;
import com.hbsf.home.bean.NewsChannelsListBean;
import com.hbsf.home.bean.NewsListBean;

import io.reactivex.rxjava3.core.Observable;

public class NewsChannelModel extends BaseModel<NewsChannlesContract.Persenter, NewsApi>  implements NewsChannlesContract.Model {

    private NewsChannelsListBean newsChannelsListBean;
    public NewsChannelModel(NewsChannlesContract.Persenter persenter) {
        super(persenter);
        setmApi(RetrofitClient.getInstance().create(NewsApi.class));
    }

    @Override
    public Observable<BaseObjectBean<NewsChannelsListBean>> loadNewsChannlesList() {
        return getmApi().getNewsChannels();
    }

    @Override
    public void loadCacheChannels() {
        newsChannelsListBean = new NewsChannelsListBean();
        newsChannelsListBean.getList().add(newsChannelsListBean.returnChannelBean("0", "升学"));
        newsChannelsListBean.getList().add(newsChannelsListBean.returnChannelBean("1", "考研"));
        getPresenter().changeChannels(newsChannelsListBean);
    }


    @Override
    public void handleResult(BaseObjectBean t) {

        getPresenter().changeChannels((NewsChannelsListBean)t.getResult());

    }

}
