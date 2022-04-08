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
    private NewsChannlesContract.Type type;

    public NewsChannelModel(NewsChannlesContract.Persenter persenter, NewsChannlesContract.Type type) {
        super(persenter);
        this.type = type;
        setmApi(RetrofitClient.getInstance().create(NewsApi.class));
    }

    @Override
    public Observable<BaseObjectBean<NewsChannelsListBean>> loadNewsChannlesList() {
        if (type == NewsChannlesContract.Type.NEWS) {
            return getmApi().getNewsChannels("0");
        } else {
            return getmApi().getNewsChannels("1");
        }
    }

    @Override
    public void loadCacheChannels() {
        newsChannelsListBean = new NewsChannelsListBean();
        if (type == NewsChannlesContract.Type.NEWS) {
            newsChannelsListBean.getList().add(newsChannelsListBean.returnChannelBean("0", "升学"));
            newsChannelsListBean.getList().add(newsChannelsListBean.returnChannelBean("1", "考研"));
        } else {
            newsChannelsListBean.getList().add(newsChannelsListBean.returnChannelBean("2", "消息"));
            newsChannelsListBean.getList().add(newsChannelsListBean.returnChannelBean("3", "联系人"));
            newsChannelsListBean.getList().add(newsChannelsListBean.returnChannelBean("4", "动态"));
        }

        getPresenter().changeChannels(newsChannelsListBean);
    }


    @Override
    public void handleResult(BaseObjectBean t) {

        getPresenter().changeChannels((NewsChannelsListBean)t.getResult());

    }

}
