package com.hbsf.home.model;

import com.hbsf.base.bean.BaseArrayBean;
import com.hbsf.base.bean.BaseBean;
import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.model.BaseModel;
import com.hbsf.common.net.RetrofitClient;
import com.hbsf.home.api.NewsApi;
import com.hbsf.home.api.NewsChannlesContract;
import com.hbsf.home.bean.ChannelBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class NewsChannelModel extends BaseModel<NewsChannlesContract.Persenter, NewsApi>  implements NewsChannlesContract.Model {

    private List<ChannelBean> newsChannelsListBean;
    private NewsChannlesContract.Type type;

    public NewsChannelModel(NewsChannlesContract.Persenter persenter, NewsChannlesContract.Type type) {
        super(persenter);
        this.type = type;
        setmApi(RetrofitClient.getInstance().create(NewsApi.class));
    }

    @Override
    public Observable<BaseArrayBean<ChannelBean>> loadNewsChannlesList() {
        if (type == NewsChannlesContract.Type.NEWS) {
            return getmApi().getNewsChannels("0");
        } else {
            return getmApi().getNewsChannels("1");
        }
    }

    @Override
    public void loadCacheChannels() {
        newsChannelsListBean = new ArrayList<>();
        if (type == NewsChannlesContract.Type.NEWS) {
            newsChannelsListBean.add(new ChannelBean("0", "升学"));
            newsChannelsListBean.add(new ChannelBean("1", "考研"));
        } else {
            newsChannelsListBean.add(new ChannelBean("2", "消息"));
            newsChannelsListBean.add(new ChannelBean("3", "联系人"));
            newsChannelsListBean.add(new ChannelBean("4", "动态"));
        }
        getPresenter().changeChannels(newsChannelsListBean);
    }



    @Override
    public void handleResult(BaseBean t) {
        if (t instanceof BaseArrayBean) {
            getPresenter().changeChannels(((BaseArrayBean)t).getResult());
        }
    }
}
