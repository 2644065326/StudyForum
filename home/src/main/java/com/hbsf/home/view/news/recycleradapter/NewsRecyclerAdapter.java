package com.hbsf.home.view.news.recycleradapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hbsf.base.api.IBaseModel;
import com.hbsf.base.view.BaseViewHolder;
import com.hbsf.home.R;
import com.hbsf.home.bean.NewsListBean;
import com.hbsf.home.view.news.itemview.MorePictureItemView;
import com.hbsf.home.view.news.itemview.PictureItemView;
import com.hbsf.home.view.news.itemview.TitleItemView;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<NewsListBean.NewsBean> mItems;

    private final int VIEW_TYPE_TITLE = 0;
    private final int VIEW_TYPE_PICTURE_TITLE = 1;
    private final int VIEW_TYPE_MORE_PICTURE_TITLE = 2;
    private RecyclerView.ViewHolder holder;
    private int position;

    public NewsRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<NewsListBean.NewsBean> items) {
        this.mItems = items;
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
        if (mItems == null) return -1;
        NewsListBean.NewsBean data = mItems.get(position);
        return itemTypePolicy(data);
    }

    private int itemTypePolicy(NewsListBean.NewsBean data) {
        if (data == null) return -1;
        if (data.getImageurls() == null) return VIEW_TYPE_TITLE;
        int type = Integer.parseInt(data.getType());
        int picNums = data.getImageurls().size();
        if (type == VIEW_TYPE_TITLE) return VIEW_TYPE_TITLE;
        if (type == VIEW_TYPE_PICTURE_TITLE) {
            if (picNums >= 1) {
                return VIEW_TYPE_PICTURE_TITLE;
            }
            else if (picNums == 1) {
                return VIEW_TYPE_TITLE;
            }
        }

        if (type == VIEW_TYPE_MORE_PICTURE_TITLE) {
            if (picNums >= 3) {
                return VIEW_TYPE_MORE_PICTURE_TITLE;
            }
            else if (picNums >= 1 && picNums < 3) {
                return VIEW_TYPE_PICTURE_TITLE;
            } else {
                return VIEW_TYPE_TITLE;
            }
        }
        return -1;
    }




    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_PICTURE_TITLE) {
            return new BaseViewHolder(new PictureItemView(parent.getContext()));
        } else if (viewType == VIEW_TYPE_TITLE) {
            return new BaseViewHolder(new TitleItemView(parent.getContext()));
        } else if (viewType == VIEW_TYPE_MORE_PICTURE_TITLE) {
            return new BaseViewHolder(new MorePictureItemView(parent.getContext()));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(mItems.get(position));
    }
}
