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
import com.kysfw.www.sfw.model.HouseBoxModel;
import com.kysfw.www.sfw.utils.BaseUtils;

import java.util.List;

/**
 * Created by admin on 2017/5/22.
 */

public class HouseListAdapter extends RecyclerView.Adapter<HouseListAdapter.HouseHolder>{
    private Context context;
    private List<HouseBoxModel> list;
    private OnItemClickListener onItemClickListener;

    public HouseListAdapter(Context context, List<HouseBoxModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    @Override
    public HouseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HouseHolder(LayoutInflater.from(context).inflate(R.layout.item_main_rv,parent,false));
    }

    @Override
    public void onBindViewHolder(HouseHolder holder, final int position) {
        HouseBoxModel model=list.get(position);
        String[] houseImgL=model.houseImg.split("\\|");
        Glide.with(context).load(Constants.HOST+houseImgL[0]).error(R.mipmap.defaulthouse_bg)
                .into( holder.ivHouse);
       holder.tvHouseName.setText(model.address);
        holder.tvText2.setText(model.houseTypeT+" "+model.houseArea+" "+model.direction);
         holder.tvText3.setText(model.houseType+" "+model.saleSituation);
         holder.tvPrice.setText(BaseUtils.calculationPrice(Float.parseFloat(model.salePrice)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(position,v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    class HouseHolder extends RecyclerView.ViewHolder{
        ImageView ivHouse;
        TextView tvHouseName,tvText2,tvText3,tvPrice;
        public HouseHolder(View itemView) {
            super(itemView);
            ivHouse= (ImageView) itemView.findViewById(R.id.iv_housePhoto);
            tvHouseName= (TextView) itemView.findViewById(R.id.tv_houseName);
            tvText2= (TextView) itemView.findViewById(R.id.tv_text2);
            tvText3= (TextView) itemView.findViewById(R.id.tv_text3);
            tvPrice= (TextView) itemView.findViewById(R.id.tv_price);
        }
    }
}
