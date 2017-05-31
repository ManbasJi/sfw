package com.kysfw.www.sfw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by manbas on 17-5-25.
 */

public class MViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private Context context;

    public MViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        this.context=context;
        this.mConvertView=itemView;
        mViews=new SparseArray<>();
    }

    public static MViewHolder get(Context context,ViewGroup parent,int layoutId){
        View itemView= LayoutInflater.from(context).inflate(layoutId,parent,false);
        MViewHolder holder=new MViewHolder(context,itemView,parent);
        return holder;
    }

    public <T extends View>T getView(int viewId){
        View view=mViews.get(viewId);
        if(view==null){
            view=mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }

    public MViewHolder setText(int viewId, String text)
    {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public MViewHolder setImageResource(int viewId, int resId)
    {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public MViewHolder setOnClickListener(int viewId,
                                         View.OnClickListener listener)
    {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }


}
