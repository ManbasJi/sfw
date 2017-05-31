package com.kysfw.www.sfw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kysfw.www.sfw.R;
import com.kysfw.www.sfw.config.Constants;
import com.kysfw.www.sfw.model.DecorationCaseDetailModel;
import com.kysfw.www.sfw.model.MaterialDetailModel;
import com.kysfw.www.sfw.utils.BaseUtils;
import com.kysfw.www.sfw.view.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by manbas on 17-5-26.
 */

public class CommonDetailTwoActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_contentTitle)
    TextView tvContentTitle;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.tv_msgone)
    TextView tvMsgOne;

    private String status;

    private String title="";
    private List<String> imgpic=new ArrayList<>();
    private String url="";
    private String id="";

    private MaterialDetailModel materialDetailModel;
    private DecorationCaseDetailModel decorationCaseDetailModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commondetailtwo);
        ButterKnife.bind(this);

        status=getIntent().getStringExtra("status");
        id=getIntent().getStringExtra("id");
        if(status.equals("material")){
            tvContentTitle.setText("建材描述");
            url= Constants.HOST+Constants.API.GET_MATERIAL_CASE_DETAIL;
            title="建材详细信息";
        }else if(status.equals("decoration")){
            tvContentTitle.setText("案例描述");
            tvMsgOne.setText("案例名称：\n预算：\n面积：\n发布时间：\n设计说明：\n");
            title="案例详细信息";
            url=Constants.HOST+Constants.API.GET_DECORATION_CASE_DETAIL;
        }

        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getData();

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(CommonDetailTwoActivity.this, BigPhotoActivity.class);
                intent.putStringArrayListExtra("imgPath", (ArrayList<String>) imgpic);
                intent.putExtra("index", position);
//                startActivity(intent);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(
                        banner, banner.getWidth() / 2, banner.getHeight() / 2, 0, 0
                );
                ActivityCompat.startActivity(CommonDetailTwoActivity.this, intent, compat.toBundle());
            }
        });

    }

    private void setData(){
        String img[] = new String[0];
        if(status.equals("material")){
            tvMsg.setText(materialDetailModel.getMaterialName()+"\n"
            +materialDetailModel.getBrand()+"\n"
            +BaseUtils.calculationPrice(Float.parseFloat(materialDetailModel.getPrice()))+"\n"
            +materialDetailModel.getCreateTime()+"\n"
            +materialDetailModel.getProductMsg());
            img=materialDetailModel.getPic().split("\\|");
        }else{
            tvMsg.setText(decorationCaseDetailModel.getCaseName()+"\n"+
                    BaseUtils.calculationPrice(Float.parseFloat(decorationCaseDetailModel.getBudget()))+"\n"+
            decorationCaseDetailModel.getSize()+"㎡\n"+
            decorationCaseDetailModel.getCreateTime()+"\n"+
            decorationCaseDetailModel.getDesignMsg());
            img=decorationCaseDetailModel.getPic().split("\\|");
        }
        for(int i=0;i<img.length;i++){
            imgpic.add(Constants.HOST+img[i]);
        }
        banner.setImages(imgpic).setImageLoader(new GlideImageLoader()).start();
    }

    private void getData(){
        OkHttpUtils.get()
                .url(url)
                .addParams("id",id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Snackbar.make(banner.getRootView(),"网络连接失败！",Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if(status.equals("material")){
                            List<MaterialDetailModel> list=new Gson().fromJson(response
                                    ,new TypeToken<List<MaterialDetailModel>>(){}.getType());
                            materialDetailModel=list.get(0);
                        }else if(status.equals("decoration")){
                            List<DecorationCaseDetailModel> list=new Gson().fromJson(response,
                                    new TypeToken<List<DecorationCaseDetailModel>>(){}.getType());
                            decorationCaseDetailModel=list.get(0);
                        }
                        setData();
                    }
                });
    }
}
