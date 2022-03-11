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
import com.hbsf.home.R;
import com.hbsf.home.bean.NewsListBean;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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

    private class PictureTitleViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public AppCompatImageView picutureImageView;

        public PictureTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.news_pic_title);
            picutureImageView = itemView.findViewById(R.id.news_pic_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    private class MorePictureTitleViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public AppCompatImageView leftImageView;
        public AppCompatImageView midImageView;
        public AppCompatImageView rightImageView;
        public MorePictureTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.more_pic_title);
            leftImageView = itemView.findViewById(R.id.more_pic_left_img);
            midImageView = itemView.findViewById(R.id.more_pic_mid_img);
            rightImageView = itemView.findViewById(R.id.more_pic_right_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    private class TitleViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.news_txt_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //holder.itemView.setTag(mItems.get(position).link);
        if(holder instanceof PictureTitleViewHolder){
            ((PictureTitleViewHolder) holder).titleTextView.setText(mItems.get(position).getTitle());
            Glide.with(holder.itemView.getContext())
            .load(R.drawable.test)
                    //.load(mItems.get(position).getImageurls().get(0))
                    .transition(withCrossFade())
                    .into(((PictureTitleViewHolder) holder).picutureImageView);
        } else if(holder instanceof TitleViewHolder) {
            ((TitleViewHolder) holder).titleTextView.setText(mItems.get(position).getTitle());
        } else if (holder instanceof MorePictureTitleViewHolder) {
            ((MorePictureTitleViewHolder) holder).titleTextView.setText(mItems.get(position).getTitle());
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.test)
                    //.load(mItems.get(position).getImageurls().get(0))
                    .transition(withCrossFade())
                    .into(((MorePictureTitleViewHolder) holder).leftImageView);
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.test)
                    //.load(mItems.get(position).getImageurls().get(1))
                    .transition(withCrossFade())
                    .into(((MorePictureTitleViewHolder) holder).midImageView);
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.test)
                    //.load(mItems.get(position).getImageurls().get(2))
                    .transition(withCrossFade())
                    .into(((MorePictureTitleViewHolder) holder).rightImageView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_PICTURE_TITLE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.new_picture_item, parent, false);
            return new PictureTitleViewHolder(view);
        } else if (viewType == VIEW_TYPE_TITLE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.new_text_item, parent, false);
            return new TitleViewHolder(view);
        } else if (viewType == VIEW_TYPE_MORE_PICTURE_TITLE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.new_more_pic_item, parent, false);
            return new MorePictureTitleViewHolder(view);
        }
        return null;
    }
}
