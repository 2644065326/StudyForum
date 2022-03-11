package com.hbsf.home.view.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hbsf.base.mvp.view.BaseMVPFragment;
import com.hbsf.home.bean.NewsListBean;
import com.hbsf.home.view.news.recycleradapter.NewsRecyclerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hbsf.home.R;

import java.util.ArrayList;
import java.util.List;


public class NewsChannelFragment extends BaseMVPFragment {
    private String url = "https://www.baidu.com/link?url=grJtHlL0BWSE47NFxOGv8gWYsFboAqn8-6alEIR5pTvWS0iNeD_9srA2Cs28-CZbuvpNtS-XesXqVYLKCeKr4ejAP7Ud5gIJBaipTks34fcWsmYnJT1KaHxYhgktPJiwGmTF9fiYckEMlnGF70uI4z4mB2jtHLVEeXIbYKcga1hy-Tv1JoDjsvcxC2V_KMkj15f_o0bnqPBKbgpzjw4BDUI_RgWvVBXzSNw6SNtTO-GNjKqMYUq3zzteryGcPJsZbDgvCQs303Uz0BVc6JsqijQUs13NViaceNPgNV8pLa-mbFHvgWI0bYdnwnQ2UOQEx6JKKPDlLBsa0QNba8ap57ZiboEWqBn70EqPbNJBOHPr7Kf_I4kD7OeEzxUYUne_Ss40w-WjakOmmg9aG8DLu-gU1NFVvMxggqQxHAvTFsfFt4BOkQE0UY_wNJTqLWjyzUhSJuXy0bN_6bsXN6m3oyU3Rd-nhq6H3VVL3IaYLta0tHKqmHxOHpoku45XzcbMmVWvmXvw11_110l9nkI8WKVvgL9s9C7SiOWRY26XotSWWTFmcMbpTF8xMF9S3nyG_qScuR9UIJfbttk1jIR_2KAMGHJx6t5ZYH8hXf7ARNG1tYYIDsxrlkkzu753lvyNt67VGKIowBkZJ7wWo3Ui1K&wd=&eqid=c600110d0003c4f500000002622abcfc";
    private String title = "欧盟成员国领导人举行非正式会议 或婉拒乌克兰“快速入盟”：乌总统泽连斯基呼吁欧盟迅速吸纳乌克兰";
    private String type = "news";
    private String dec = "当地时间3月10日晚，乌克兰总统泽连斯基在视频讲话中重申，吸纳乌克兰加入欧盟是对欧洲的最终考验。";
    private RecyclerView recyclerView;
    private NewsRecyclerAdapter recyclerAdapter;

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
        recyclerAdapter = new NewsRecyclerAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerAdapter);
        NewsListBean factory = new NewsListBean();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        list1.add(url);
        list1.add(url);
        list1.add(url);
        list2.add(url);
        NewsListBean.NewsBean bean1 = factory.creatNewsBean(title,"kaoyan", list1, dec, "0", "2");
        NewsListBean.NewsBean bean2 = factory.creatNewsBean(title,"kaoyan", list2, dec, "0", "1");
        NewsListBean.NewsBean bean3 = factory.creatNewsBean(title,"kaoyan", null, dec, "0", "0");
        List<NewsListBean.NewsBean> mItems = new ArrayList<>();
        mItems.add(bean1);
        mItems.add(bean2);
        mItems.add(bean3);
        recyclerAdapter.setData(mItems);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_channel;
    }


}
