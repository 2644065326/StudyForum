package com.hbsf.home.view.community.recycleradapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hbsf.base.view.BaseViewHolder;
import com.hbsf.home.bean.NewsListBean;
import com.hbsf.home.view.news.itemview.MorePictureItemView;
import com.hbsf.home.view.news.itemview.PictureItemView;
import com.hbsf.home.view.news.itemview.TitleItemView;

import java.util.List;

public class CommunityRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context mContext;
    private List mItems;

    public CommunityRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List items) {

        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {

        return -1;
    }

    private int itemTypePolicy() {

        return -1;
    }




    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

    }
}
