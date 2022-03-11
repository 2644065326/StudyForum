package com.hbsf.home.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hbsf.base.application.BaseApplication;
import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.model.BaseModel;
import com.hbsf.base.utils.SPUtils;
import com.hbsf.common.net.RetrofitClient;
import com.hbsf.home.api.NewsApi;
import com.hbsf.home.api.NewsListContract;
import com.hbsf.home.bean.NewsListBean;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class NewsModel extends BaseModel<NewsListContract.Persenter, NewsApi> implements NewsListContract.Model{
    private List<NewsListBean.NewsBean> dataList;
    private int page;
    private String channelId;
    private String channelName;
    private boolean isFirstLoad;
    private String cacheKey;
    public NewsModel(NewsListContract.Persenter persenter, String channelId, String channelName) {
        super(persenter);
        setmApi(RetrofitClient.getInstance().create(NewsApi.class));
        this.channelId = channelId;
        this.channelName = channelName;
        cacheKey = channelId + channelName;
        dataList = new ArrayList<>();
        isFirstLoad = true;
    }



    @Override
    public void handleResult(BaseObjectBean t) {
        if (page != Integer.parseInt(((NewsListBean)t.getResult()).getPage())) {
            dataList.clear();
        }
        dataList.addAll(((NewsListBean)t.getResult()).getList());
        page++;
        if (isFirstLoad) {
            isFirstLoad = false;
            //缓存第一页
            cacheNews((NewsListBean)t.getResult());
        }
        getPresenter().showNews(dataList);
    }

    private void cacheNews(NewsListBean data) {
        String str =  new Gson().toJson(data);
        SPUtils.put(BaseApplication.sApplication, cacheKey, str);
    }


    @Override
    public Observable<BaseObjectBean<NewsListBean>> loadNextNewsList(boolean isRefresh) {
        if (isRefresh) {
            return getmApi().loadNewsList(channelId, channelName, 0 +"");
        }
        return getmApi().loadNewsList(channelId, channelName, page +"");
    }

    @Override
    public void loadCacheNews() {
        if (!isFirstLoad) return;
        isFirstLoad = false;
        //加载缓存
        String str = (String) SPUtils.get(BaseApplication.sApplication, cacheKey, " ");
        NewsListBean savedData = new Gson().fromJson(str, new TypeToken<NewsListBean>() {}.getType());
        dataList.addAll(savedData.getList());
        getPresenter().showNews(dataList);
    }

}
