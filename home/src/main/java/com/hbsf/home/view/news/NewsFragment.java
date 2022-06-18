package com.hbsf.home.view.news;

import android.view.View;

import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.hbsf.base.mvp.view.BaseMVPFragment;
import com.hbsf.home.R;
import com.hbsf.home.api.NewsChannlesContract;
import com.hbsf.home.bean.ChannelBean;
import com.hbsf.home.presenter.NewsChannelsPresenter;
import com.hbsf.common.view.SFViewPager;
import com.hbsf.home.view.news.fragmentadapter.NewsFragmentAdapter;

import java.util.List;

public class NewsFragment extends BaseMVPFragment<NewsChannlesContract.Persenter> implements NewsChannlesContract.View {
    private TabLayout tabLayout;
    private SFViewPager viewPager;
    private NewsFragmentAdapter adapter;

    @Override
    protected void initView(View view) {
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(2);
        mPresenter = getPresenter();
        adapter = new NewsFragmentAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        //mPresenter.loadNewsChannelList();
        mPresenter.loadCacheChannels();
    }


    public NewsChannlesContract.Persenter getPresenter() {
        return new NewsChannelsPresenter(this, getType());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }



    @Override
    public void creatChannels(List<ChannelBean> channelBeanList) {
        //添加tab
        for (ChannelBean bean : channelBeanList) {
            tabLayout.addTab(tabLayout.newTab().setText(bean.getName()));
            adapter.getTabFragmentList().add(NewsChannelFragment.newInstance(bean.getChannelId(), bean.getName(), getType()));
            adapter.getTabsID().add(bean.getChannelId());
            adapter.getTabsName().add(bean.getName());
        }
        viewPager.setAdapter(adapter);
        //设置TabLayout和ViewPager联动
        tabLayout.setupWithViewPager(viewPager,false);

    }

    public NewsChannlesContract.Type getType() {
        return  NewsChannlesContract.Type.NEWS;
    }

    @Override
    public void onError(String errMessage) {
        super.onError(errMessage);
        mPresenter.loadCacheChannels();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
