package com.kysfw.www.sfw.view;

import android.content.Context;
import android.support.annotation.Px;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by manbas on 17-5-31.
 */

public class NoScrollViewPager extends ViewPager {
    private boolean noScroll=true;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setNoScroll(boolean noScroll){
        this.noScroll=noScroll;
    }

    @Override
    public void scrollTo(@Px int x, @Px int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(noScroll){
            return false;
        }else{
            return super.onTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        if(noScroll){
            return  false;
        }else{
            return super.onInterceptHoverEvent(event);
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }
}
