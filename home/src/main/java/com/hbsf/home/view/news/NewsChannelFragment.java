package com.hbsf.home.view.news;

import android.os.Bundle;

import android.view.View;


import com.hbsf.base.mvp.view.BaseMVPFragment;

import com.hbsf.home.api.NewsListContract;
import com.hbsf.home.bean.NewsListBean;
import com.hbsf.home.presenter.NewsPresenter;
import com.hbsf.home.view.news.recycleradapter.NewsRecyclerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hbsf.home.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class NewsChannelFragment extends BaseMVPFragment<NewsListContract.Persenter> implements NewsListContract.View {

    private RecyclerView recyclerView;
    private NewsRecyclerAdapter recyclerAdapter;
    private String channelId;
    private String channelName;
    private SmartRefreshLayout refreshLayout;

    public static NewsChannelFragment newInstance(String channelId, String channelName) {
        Bundle args = new Bundle();
        args.putString("id", channelId);
        args.putString("name", channelName);
        NewsChannelFragment fragment = new NewsChannelFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.list_view);
        refreshLayout = view.findViewById(R.id.refresh_view);
        recyclerAdapter = new NewsRecyclerAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerAdapter);
        channelId = getArguments().getString("id");
        channelName = getArguments().getString("name");
        mPresenter = new NewsPresenter(this, channelId, channelName);
        mPresenter.loadNextPage(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadNextPage(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadNextPage(false);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_channel;
    }


    @Override
    public void showNews(List<NewsListBean.NewsBean> data) {
        recyclerAdapter.setData(data);
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onError(String errMessage) {
        super.onError(errMessage);
        mPresenter.loadCacheNews();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }
}
