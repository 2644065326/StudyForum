package com.hbsf.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class SFViewPager extends ViewPager {
    private boolean isCanScroll = true;
    public SFViewPager(@NonNull Context context) {
        super(context);
    }

    public SFViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        if (isCanScroll) return super.onTouchEvent(ev);
//        else return isCanScroll;
//    }

    public boolean isScroll() {
        return isCanScroll;
    }

    public void setCanScroll(boolean scroll) {
        this.isCanScroll = scroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (!isCanScroll) {
            return false;
        } else {
            return super.onInterceptTouchEvent(arg0);
        }
    }

}
