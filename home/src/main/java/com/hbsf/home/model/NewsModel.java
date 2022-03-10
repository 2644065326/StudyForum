package com.hbsf.home.model;

import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.model.BaseModel;
import com.hbsf.common.net.RetrofitClient;
import com.hbsf.home.api.NewsApi;
import com.hbsf.home.api.NewsContract;
import com.hbsf.home.bean.NewsBean;

import io.reactivex.rxjava3.core.Observable;

public class NewsModel extends BaseModel<NewsBean, NewsContract.Persenter, NewsApi>  implements NewsContract.Model {

    public NewsModel(NewsContract.Persenter persenter) {
        super(persenter);
        setmApi(RetrofitClient.getInstance().create(NewsApi.class));
    }

    @Override
    public void handleResult(BaseObjectBean<NewsBean> t) {

    }


    @Override
    public Observable<BaseObjectBean<NewsBean>> getNewsList(int page) {
        return getmApi().loadNewsList(page);
    }
}
