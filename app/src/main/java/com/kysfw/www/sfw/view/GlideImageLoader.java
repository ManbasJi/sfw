package com.kysfw.www.sfw.view;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kysfw.www.sfw.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by admin on 2017/5/20.
 * 图片加载器
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).error(R.mipmap.defaulthouse_bg).into(imageView);
    }}
