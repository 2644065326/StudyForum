package com.hbsf.home.view.news.itemview;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.hbsf.base.api.IBaseBean;
import com.hbsf.base.view.BaseItemView;
import com.hbsf.home.R;
import com.hbsf.home.bean.NewsListBean;
import com.hbsf.home.view.news.recycleradapter.NewsRecyclerAdapter;

public class MorePictureItemView extends TitleItemView {

    private AppCompatImageView leftImageView;
    private AppCompatImageView midImageView;
    private AppCompatImageView rightImageView;

    public MorePictureItemView(Context context) {
        super(context);
    }

    @Override
    public void init() {
        super.init();
        leftImageView = getRootView().findViewById(R.id.more_pic_left_img);
        midImageView = getRootView().findViewById(R.id.more_pic_mid_img);
        rightImageView = getRootView().findViewById(R.id.more_pic_right_img);
    }

    @Override
    public int getLayoutId() {
        return R.layout.new_more_pic_item;
    }

    @Override
    public void setData(NewsListBean.NewsBean data) {
        super.setData(data);
        Glide.with(getContext())
                .load(R.drawable.test)
                //.load(mItems.get(position).getImageurls().get(0))
                .transition(withCrossFade())
                .into(leftImageView);
        Glide.with(getContext())
                .load(R.drawable.test)
                //.load(mItems.get(position).getImageurls().get(1))
                .transition(withCrossFade())
                .into(midImageView);
        Glide.with(getContext())
                .load(R.drawable.test)
                //.load(mItems.get(position).getImageurls().get(2))
                .transition(withCrossFade())
                .into(rightImageView);
    }
}
