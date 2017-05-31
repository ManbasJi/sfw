package com.kysfw.www.sfw.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by manbas on 17-5-27.
 */

public class CustomViewPager extends ViewPager {

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = false;
        try {
            b = super.onInterceptTouchEvent(ev);
        } catch (Exception e) {

        }
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            super.onTouchEvent(ev);
        } catch (Exception e) {

        }
        return false;
    }
}
