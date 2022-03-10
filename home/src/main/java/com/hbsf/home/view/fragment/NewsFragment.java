package com.hbsf.home.view.fragment;

import android.view.View;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hbsf.base.mvp.view.BaseMVPFragment;
import com.hbsf.home.R;
import com.hbsf.home.api.NewsChannlesContract;
import com.hbsf.home.bean.NewsChannelsListBean;
import com.hbsf.home.presenter.NewsPresenter;
import com.hbsf.home.view.SFViewPager;
import com.hbsf.home.view.fragmentadapter.NewsChannelFragmentAdapter;

import java.util.List;

public class NewsFragment extends BaseMVPFragment<NewsChannlesContract.Persenter> implements NewsChannlesContract.View {
    private TabLayout tabLayout;
    private SFViewPager viewPager;
    private NewsChannelFragmentAdapter adapter;


    @Override
    protected void initView(View view) {
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        mPresenter = new NewsPresenter(this);
        adapter = new NewsChannelFragmentAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mPresenter.loadNewsChannelList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }



    @Override
    public void creatChannels(NewsChannelsListBean channelsBean) {
        //添加tab
        List<NewsChannelsListBean.ChannelBean> channelBeanList = channelsBean.getList();
        for (NewsChannelsListBean.ChannelBean bean : channelBeanList) {
            tabLayout.addTab(tabLayout.newTab().setText(bean.getName()));
            adapter.getTabFragmentList().add(NewsChannelFragment.newInstance(bean.getChannelId(), bean.getName()));
            adapter.getTabsID().add(bean.getChannelId());
            adapter.getTabsName().add(bean.getName());
        }

        viewPager.setAdapter(adapter);
        //设置TabLayout和ViewPager联动
        tabLayout.setupWithViewPager(viewPager,false);

    }

    @Override
    public void onError(String errMessage) {
        super.onError(errMessage);
        mPresenter.loadCacheChannels();
    }
}
