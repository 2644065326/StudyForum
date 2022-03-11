package com.hbsf.base.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hbsf.base.api.IBaseBean;
import com.hbsf.base.api.IBaseCustomView;


public class BaseViewHolder extends RecyclerView.ViewHolder {
    private IBaseCustomView itemView;
    public BaseViewHolder(@NonNull IBaseCustomView itemView) {
        super((View) itemView);
        this.itemView = itemView;
    }

    public void bind(IBaseBean bean){
        this.itemView.setData(bean);
    }
}
