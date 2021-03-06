package com.kysfw.www.sfw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kysfw.www.sfw.R;

/**
 * Created by manbas on 17-5-26.
 */

public class CustomDividerItemDecoration extends RecyclerView.ItemDecoration {
    private float mDividerHeight;
    private Paint mPaint;
    private Context context;

    public CustomDividerItemDecoration(Context context) {
        this.context=context;
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(context.getResources().getColor(R.color.line01));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //判断是否为第一个
        if(parent.getChildAdapterPosition(view)!=0){
            outRect.top=1;
            mDividerHeight=1;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount=parent.getChildCount();
        for(int i=0;i<childCount;i++){
            View view=parent.getChildAt(i);
            int index=parent.getChildAdapterPosition(view);
            if (index ==0){
                continue;
            }
            float dividerTop=view.getTop()-mDividerHeight;
            float dividerLeft=parent.getPaddingLeft();
            float dividerBottom=view.getTop();
            float dividerRight=view.getRight()-parent.getPaddingRight();
            c.drawRect(dividerLeft,dividerTop,dividerRight,dividerBottom,mPaint);
        }
    }
}
