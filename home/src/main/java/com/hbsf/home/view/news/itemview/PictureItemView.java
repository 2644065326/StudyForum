package com.hbsf.home.view.news.itemview;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;

import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.hbsf.home.R;
import com.hbsf.home.bean.NewsBean;

public class PictureItemView extends TitleItemView {

    public AppCompatImageView picutureImageView;

    public PictureItemView(Context context) {
        super(context);
    }

    @Override
    public void init() {
        super.init();
        picutureImageView = getRootView().findViewById(R.id.news_pic_img);
    }

    @Override
    public void setData(NewsBean data) {
        super.setData(data);
        Glide.with(getContext())
                .load(R.drawable.test)
                //.load(mItems.get(position).getImageurls().get(0))
                .transition(withCrossFade())
                .into(picutureImageView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.new_picture_item;
    }

}
