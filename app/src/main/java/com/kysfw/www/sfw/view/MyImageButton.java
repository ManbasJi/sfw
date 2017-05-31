package com.kysfw.www.sfw.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by admin on 2017/2/18.
 */

public class MyImageButton extends LinearLayout {
    ImageView imageView;
    TextView textView;
    public MyImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        imageView = new ImageView(context, attrs);

        imageView.setPadding(0, 0, 0, 0);

        imageView.setClickable(false);

        textView =new TextView(context, attrs);
        //水平居中
        textView.setGravity(android.view.Gravity.CENTER);

        textView.setPadding(0, 0, 0, 0);

        textView.setClickable(false);

        setClickable(true);

        setFocusable(true);

        setOrientation(LinearLayout.VERTICAL);

        addView(imageView);

        addView(textView);
    }

    public CharSequence getText(){
        return textView.getText();
    }
    public int getTextColor(){
        return textView.getCurrentTextColor();
    }
    public void setTextColor(int color){
        textView.setTextColor(0xFFFF0000);
    }
}
