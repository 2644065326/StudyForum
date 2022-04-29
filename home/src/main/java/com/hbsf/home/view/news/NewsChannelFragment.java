package com.hbsf.home.view.news;

import android.os.Bundle;

import android.view.View;


import com.hbsf.base.mvp.view.BaseMVPFragment;

import com.hbsf.home.api.NewsChannlesContract;
import com.hbsf.home.api.NewsListContract;
import com.hbsf.home.bean.NewsBean;
import com.hbsf.home.presenter.NewsPresenter;
import com.hbsf.home.view.news.recycleradapter.NewsRecyclerAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hbsf.home.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;


public class NewsChannelFragment extends BaseMVPFragment<NewsListContract.Persenter> implements NewsListContract.View {

    private RecyclerView recyclerView;
    private NewsRecyclerAdapter recyclerAdapter;
    private String channelId;
    private String channelName;
    private String channelType;
    private SmartRefreshLayout refreshLayout;

    public static NewsChannelFragment newInstance(String channelId, String channelName, NewsChannlesContract.Type type) {
        Bundle args = new Bundle();
        args.putString("id", channelId);
        args.putString("name", channelName);
        if (type == NewsChannlesContract.Type.NEWS) {
            args.putString("type", "0");
        } else {
            args.putString("type", "1");
        }
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
        channelType = getArguments().getString("type");
        mPresenter = new NewsPresenter(this, channelId, channelName, channelType);
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
    public void showNews(List<NewsBean> data) {
        recyclerAdapter.setData(data);
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onError(String errMessage) {
        super.onError(errMessage);
        if (channelType.equals("0")) {
            mPresenter.loadCacheNews();
        } else {
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
        }
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
