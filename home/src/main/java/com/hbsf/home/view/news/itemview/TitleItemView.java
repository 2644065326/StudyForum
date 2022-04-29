package com.hbsf.home.view.news.itemview;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hbsf.base.view.BaseItemView;
import com.hbsf.home.R;
import com.hbsf.home.bean.NewsBean;

public class TitleItemView extends BaseItemView<NewsBean> {
    public TextView titleTextView;

    public TitleItemView(Context context) {
        super(context);
    }

    @Override
    public void setData(NewsBean data) {
        titleTextView.setText(data.getTitle());
    }

    @Override
    public void init() {
        super.init();
        titleTextView = getRootView().findViewById(R.id.news_txt_title);
    }

    @Override
    public int getLayoutId() {
        return R.layout.new_text_item;
    }

    @Override
    public void onRootClicked(View view) {

    }
}
