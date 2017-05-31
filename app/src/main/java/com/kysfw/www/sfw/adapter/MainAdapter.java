package com.kysfw.www.sfw.adapter;

import android.content.Context;
import android.content.Intent;
import android.sax.RootElement;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kysfw.www.sfw.R;
import com.kysfw.www.sfw.activity.HouseDetailActivity;
import com.kysfw.www.sfw.config.Constants;
import com.kysfw.www.sfw.interf.OnItemClickListener;
import com.kysfw.www.sfw.model.HouseBoxModel;
import com.kysfw.www.sfw.utils.BaseUtils;
import com.kysfw.www.sfw.utils.LogUtils;
import com.kysfw.www.sfw.view.GlideImageLoader;
import com.kysfw.www.sfw.view.MyImageButton;
import com.youth.banner.Banner;

import java.util.List;

/**
 * Created by admin on 2017/5/20.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int ITEM_HEARD=1;
    private int ITEM_CONTENT=2;

    private List<HouseBoxModel> houseBoxModels;
    private OnItemClickListener onItemClickListener;

    private List<Integer> imgPath;


    public MainAdapter(Context context,List<HouseBoxModel> houseBoxModels,List<Integer> imgPath){
        this.context=context;
        this.houseBoxModels=houseBoxModels;
        this.imgPath=imgPath;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ITEM_HEARD){
            return new HeardViewHolder(LayoutInflater.from(context).inflate(R.layout.heardview_main_rv,parent,false));
        }else if(viewType==ITEM_CONTENT){
            return new ContentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_main_rv,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeardViewHolder){
            ((HeardViewHolder) holder).banner.setImages(imgPath).setImageLoader(new GlideImageLoader()).start();
            View.OnClickListener onClickListener=new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClick((Integer) v.getTag(),v);
                }
            };

            for(int i=0;i<((HeardViewHolder) holder).ibL.length;i++){
                //为每个按钮设置点击事件和标识
                ((HeardViewHolder) holder).ibL[i].setOnClickListener(onClickListener);
                ((HeardViewHolder) holder).ibL[i].setTag(i);
            }

        }else if(holder instanceof ContentViewHolder){
            final HouseBoxModel model=houseBoxModels.get(position-1);
            String[] houseImgL=model.houseImg.split("\\|");
            Glide.with(context).load(Constants.HOST+houseImgL[0]).error(R.mipmap.defaulthouse_bg).into(((ContentViewHolder) holder).ivHouse);
            ((ContentViewHolder) holder).tvHouseName.setText(model.address);
            ((ContentViewHolder) holder).tvText2.setText(model.houseTypeT+" "+model.houseArea+" "+model.direction);
            ((ContentViewHolder) holder).tvText3.setText(model.houseType+" "+model.saleSituation);
            ((ContentViewHolder) holder).tvPrice.setText(BaseUtils.calculationPrice(Float.parseFloat(model.salePrice)));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, HouseDetailActivity.class);
                    intent.putExtra("houseId",model.houseId);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
//        super.onBindViewHolder(holder, position, payloads);
        LogUtils.d("lay"+payloads.toString());
        if(payloads.isEmpty()){
            onBindViewHolder(holder,position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return ITEM_HEARD;
        }else{
            return ITEM_CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        return houseBoxModels!=null?houseBoxModels.size()+1:1;
    }

    class HeardViewHolder extends RecyclerView.ViewHolder{
        Banner banner;
        LinearLayout ibSy,ibXf,ibEsf,ibZf,ibSf,ibJjr,ibZx,ibJc;
        LinearLayout[] ibL={ibSy,ibXf,ibEsf,ibZf,ibSf,ibJjr,ibZx,ibJc};
        public HeardViewHolder(View itemView) {
            super(itemView);
            banner= (Banner) itemView.findViewById(R.id.banner_main);
            ibL[0]= (LinearLayout) itemView.findViewById(R.id.ib_sy);
            ibL[1]= (LinearLayout) itemView.findViewById(R.id.ib_xf);
            ibL[2]= (LinearLayout) itemView.findViewById(R.id.ib_esf);
            ibL[3]= (LinearLayout) itemView.findViewById(R.id.ib_zf);
            ibL[4]= (LinearLayout) itemView.findViewById(R.id.ib_sf);
            ibL[5]= (LinearLayout) itemView.findViewById(R.id.ib_jjr);
            ibL[6]= (LinearLayout) itemView.findViewById(R.id.ib_zx);
            ibL[7]= (LinearLayout) itemView.findViewById(R.id.ib_jc);
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder{
        ImageView ivHouse;
        TextView tvHouseName,tvText2,tvText3,tvPrice;
        public ContentViewHolder(View itemView) {
            super(itemView);
            ivHouse= (ImageView) itemView.findViewById(R.id.iv_housePhoto);
            tvHouseName= (TextView) itemView.findViewById(R.id.tv_houseName);
            tvText2= (TextView) itemView.findViewById(R.id.tv_text2);
            tvText3= (TextView) itemView.findViewById(R.id.tv_text3);
            tvPrice= (TextView) itemView.findViewById(R.id.tv_price);
        }
    }
}
