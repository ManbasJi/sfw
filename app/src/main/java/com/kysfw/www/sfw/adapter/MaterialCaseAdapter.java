package com.kysfw.www.sfw.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kysfw.www.sfw.R;
import com.kysfw.www.sfw.activity.CommonDetailTwoActivity;
import com.kysfw.www.sfw.model.DecorationCaseModel;
import com.kysfw.www.sfw.model.MaterialCaseModel;
import com.kysfw.www.sfw.utils.BaseUtils;

import java.util.List;

/**
 * Created by manbas on 17-5-25.
 */

public class MaterialCaseAdapter extends RecyclerView.Adapter<MaterialCaseAdapter.MyHolder>{

    private Context context;
    private List<MaterialCaseModel> list;

    public MaterialCaseAdapter(Context context, List<MaterialCaseModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_main_rv,parent,false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final MaterialCaseModel model=list.get(position);
        Glide.with(context).load(model.getPic()).placeholder(R.mipmap.defaulthouse_bg)
                .into(holder.iv);
        holder.tv1.setText(model.getMaterialName());
        holder.tv2.setText(model.getCreateTime());
        holder.tv3.setText(BaseUtils.calculationPrice(Float.parseFloat(model.getPrice())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CommonDetailTwoActivity.class);
                intent.putExtra("status","material");
                intent.putExtra("id",model.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv1,tv2,tv3;

        public MyHolder(View itemView) {
            super(itemView);
            iv= (ImageView) itemView.findViewById(R.id.iv_housePhoto);
            tv1= (TextView) itemView.findViewById(R.id.tv_houseName);
            tv2= (TextView) itemView.findViewById(R.id.tv_text3);
            tv3= (TextView) itemView.findViewById(R.id.tv_price);

        }
    }
}
