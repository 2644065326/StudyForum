package com.hbsf.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hbsf.base.api.IBaseBean;
import com.hbsf.base.api.IBaseCustomView;

public abstract class BaseItemView<T extends IBaseBean> extends LinearLayout implements IBaseCustomView<T> {
    private View rootView;
    public BaseItemView(Context context) {
        super(context);
        init();
    }

    public BaseItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public BaseItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public View getRootView() {
        return rootView;
    }


    public void init(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(getLayoutId(), this, false);
        rootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRootClicked(v);
            }
        });
        addView(rootView);
    }

    public abstract int getLayoutId();

    public abstract void onRootClicked(View view);

    
}

