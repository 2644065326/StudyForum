package com.hbsf.home.view.community;


import com.hbsf.home.R;
import com.hbsf.home.api.NewsChannlesContract;
import com.hbsf.home.view.news.NewsFragment;


public class CommunityFragment  extends NewsFragment {



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community;
    }

    @Override
    public NewsChannlesContract.Type getType() {
        return NewsChannlesContract.Type.COMMUNITY;
    }
}
