package com.hbsf.home.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hbsf.base.application.BaseApplication;
import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.model.BaseModel;
import com.hbsf.base.utils.SPUtils;
import com.hbsf.base.utils.StringUtils;
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
    private String channelType;

    private String url = "https://www.baidu.com/link?url=grJtHlL0BWSE47NFxOGv8gWYsFboAqn8-6alEIR5pTvWS0iNeD_9srA2Cs28-CZbuvpNtS-XesXqVYLKCeKr4ejAP7Ud5gIJBaipTks34fcWsmYnJT1KaHxYhgktPJiwGmTF9fiYckEMlnGF70uI4z4mB2jtHLVEeXIbYKcga1hy-Tv1JoDjsvcxC2V_KMkj15f_o0bnqPBKbgpzjw4BDUI_RgWvVBXzSNw6SNtTO-GNjKqMYUq3zzteryGcPJsZbDgvCQs303Uz0BVc6JsqijQUs13NViaceNPgNV8pLa-mbFHvgWI0bYdnwnQ2UOQEx6JKKPDlLBsa0QNba8ap57ZiboEWqBn70EqPbNJBOHPr7Kf_I4kD7OeEzxUYUne_Ss40w-WjakOmmg9aG8DLu-gU1NFVvMxggqQxHAvTFsfFt4BOkQE0UY_wNJTqLWjyzUhSJuXy0bN_6bsXN6m3oyU3Rd-nhq6H3VVL3IaYLta0tHKqmHxOHpoku45XzcbMmVWvmXvw11_110l9nkI8WKVvgL9s9C7SiOWRY26XotSWWTFmcMbpTF8xMF9S3nyG_qScuR9UIJfbttk1jIR_2KAMGHJx6t5ZYH8hXf7ARNG1tYYIDsxrlkkzu753lvyNt67VGKIowBkZJ7wWo3Ui1K&wd=&eqid=c600110d0003c4f500000002622abcfc";
    private String title = "欧盟成员国领导人举行非正式会议 或婉拒乌克兰“快速入盟”：乌总统泽连斯基呼吁欧盟迅速吸纳乌克兰";
    private String type = "news";
    private String dec = "当地时间3月10日晚，乌克兰总统泽连斯基在视频讲话中重申，吸纳乌克兰加入欧盟是对欧洲的最终考验。";


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
        NewsListBean savedData = new Gson().fromJson(str, new TypeToken<NewsListBean>() {}.getType());
        dataList.addAll(savedData.getList());
        getPresenter().showNews(dataList);
    }

}
