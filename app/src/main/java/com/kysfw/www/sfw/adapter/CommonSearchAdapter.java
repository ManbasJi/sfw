package com.kysfw.www.sfw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kysfw.www.sfw.R;
import com.kysfw.www.sfw.config.Constants;
import com.kysfw.www.sfw.interf.OnItemClickListener;
import com.kysfw.www.sfw.model.CommonSearchModel;
import com.kysfw.www.sfw.view.GlideCircleTransform;

import java.util.List;

/**
 * Created by manbas on 17-5-25.
 */

public class CommonSearchAdapter extends RecyclerView.Adapter<CommonSearchAdapter.MyHolder>{
    private Context context;
    private List<CommonSearchModel> list;
    private OnItemClickListener onItemClickListener;

    public CommonSearchAdapter(Context context, List<CommonSearchModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_commonsearch,parent,false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        CommonSearchModel model=list.get(position);
        Glide.with(context).load(Constants.HOST+model.getPicPath())
                .transform(new GlideCircleTransform(context)).into(holder.iv);
        holder.tv1.setText(model.getName());
        holder.tv2.setText(model.getPhone());
        holder.tv3.setText(model.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.OnItemClick(position,view);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv1,tv2,tv3;

        public MyHolder(View itemView) {
            super(itemView);
            iv= (ImageView) itemView.findViewById(R.id.iv_msg);
            tv1= (TextView) itemView.findViewById(R.id.tv_text1);
            tv2= (TextView) itemView.findViewById(R.id.tv_text2);
            tv3= (TextView) itemView.findViewById(R.id.tv_text3);
        }
    }
}
