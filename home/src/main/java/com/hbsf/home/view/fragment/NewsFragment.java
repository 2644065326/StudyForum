package com.hbsf.home.view.fragment;

import android.view.View;

import com.hbsf.base.mvp.view.BaseMVPFragment;
import com.hbsf.home.R;
import com.hbsf.home.api.NewsContract;
import com.hbsf.home.bean.NewsBean;
import com.hbsf.home.presenter.NewsPresenter;

public class NewsFragment extends BaseMVPFragment<NewsContract.Persenter> implements NewsContract.View {


    @Override
    protected void initView(View view) {
        mPresenter = new NewsPresenter(this);
        mPresenter.loadNewsList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void showNews(NewsBean newsBean) {

    }
}
