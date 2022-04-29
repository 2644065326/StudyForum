package com.hbsf.home.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hbsf.base.application.BaseApplication;
import com.hbsf.base.bean.BaseArrayBean;
import com.hbsf.base.bean.BaseBean;
import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.model.BaseModel;
import com.hbsf.base.utils.SPUtils;
import com.hbsf.base.utils.StringUtils;
import com.hbsf.common.net.RetrofitClient;
import com.hbsf.home.api.NewsApi;
import com.hbsf.home.api.NewsListContract;
import com.hbsf.home.bean.NewsBean;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class NewsModel extends BaseModel<NewsListContract.Persenter, NewsApi> implements NewsListContract.Model{
    private List<NewsBean> dataList;
    private int page;
    private String channelId;
    private String channelName;
    private boolean isFirstLoad;
    private String cacheKey;
    private String channelType;


    public NewsModel(NewsListContract.Persenter persenter, String channelId, String channelName, String channelType) {
        super(persenter);
        setmApi(RetrofitClient.getInstance().create(NewsApi.class));
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelType = channelType;
        cacheKey = channelId + channelName;
        if (channelType.equals("0")) {
            dataList = new ArrayList<>();
        }
        isFirstLoad = true;
    }



    @Override
    public void handleResult(BaseBean t) {
        if (!(t instanceof BaseArrayBean)) return;

        if (page != ((BaseArrayBean)t).getPage()) {
            dataList.clear();
        }
        dataList.addAll(((BaseArrayBean)t).getResult());
        page++;
        if (isFirstLoad) {
            isFirstLoad = false;
            //缓存第一页
            cacheNews(((BaseArrayBean)t).getResult());
        }
        getPresenter().showNews(dataList);
    }

    private void cacheNews(List<NewsBean> data) {
        String str =  new Gson().toJson(data);
        SPUtils.put(BaseApplication.sApplication, cacheKey, str);
    }


    @Override
    public Observable<BaseArrayBean<NewsBean>> loadNextNewsList(boolean isRefresh) {
        if (isRefresh) {
            return getmApi().loadNewsList(channelType, channelId, channelName, -1 + "");
        }
        return getmApi().loadNewsList(channelType, channelId, channelName, page + "");
    }

    @Override
    public void loadCacheNews() {
        if (!isFirstLoad) return;
        isFirstLoad = false;
        //加载缓存
        String str = (String) SPUtils.get(BaseApplication.sApplication, cacheKey, "");
        if (StringUtils.isEmpty(str)) return;
        List<NewsBean> savedData = new Gson().fromJson(str, new TypeToken<List<NewsBean>>() {}.getType());
        dataList.addAll(savedData);
        getPresenter().showNews(dataList);
    }

}
