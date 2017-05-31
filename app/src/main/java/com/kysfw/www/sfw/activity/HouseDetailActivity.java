package com.kysfw.www.sfw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kysfw.www.sfw.R;
import com.kysfw.www.sfw.config.Constants;
import com.kysfw.www.sfw.fragment.HouseDetailOneFragment;
import com.kysfw.www.sfw.fragment.HouseDetailThreeFragment;
import com.kysfw.www.sfw.fragment.HouseDetailTwoFragment;
import com.kysfw.www.sfw.model.HouseDetailModel;
import com.kysfw.www.sfw.utils.LogUtils;
import com.kysfw.www.sfw.view.GlideImageLoader;
import com.kysfw.www.sfw.view.NoScrollViewPager;
import com.kysfw.www.sfw.view.WrapContentHeightViewPager;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by admin on 2017/5/23.
 */

public class HouseDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.banner_houseDetail)
    Banner bannerHouseDetail;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.vp_content)
    WrapContentHeightViewPager vpContent;

    List<String> tabTitle;
    List<String> housePic;
    List<HouseDetailModel> houseDetailModel;
    private List<Fragment> fragmentList;
    private String response="";
    private String houseId="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housedetail);
        ButterKnife.bind(this);

        houseId=getIntent().getStringExtra("houseId");
        toolbar.setTitle("房源详细信息");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getData(houseId);

        bannerHouseDetail.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(HouseDetailActivity.this, BigPhotoActivity.class);
                intent.putStringArrayListExtra("imgPath", (ArrayList<String>) housePic);
                intent.putExtra("index", position);
//                startActivity(intent);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(
                        bannerHouseDetail, bannerHouseDetail.getWidth() / 2, bannerHouseDetail.getHeight() / 2, 0, 0
                );
                ActivityCompat.startActivity(HouseDetailActivity.this, intent, compat.toBundle());
            }
        });
    }

    private void initData(){
        tabTitle=new ArrayList<>();
        tabTitle.add("房源描述");
        tabTitle.add("房源价格");
        tabTitle.add("经纪人");
        //banner
        housePic=new ArrayList<>();
        String[] picArray=houseDetailModel.get(0).getHousePic().split("\\|");
        for(String str:picArray){
            housePic.add(Constants.HOST+str);
        }
        //fragmentlist
        fragmentList=new ArrayList<>();
        fragmentList.add(HouseDetailOneFragment.getInstance(response));
        Bundle b=new Bundle();
        b.putString("photo","");
        b.putString("name",houseDetailModel.get(0).getAgentName());
        b.putString("phone",houseDetailModel.get(0).getPhone());
        String str=houseDetailModel.get(0).getHouseTypet()+","+houseDetailModel.get(0).getSalePrice()+","+
                houseDetailModel.get(0).getHouseSize()+","+houseDetailModel.get(0).getHouseType()+","+houseDetailModel.get(0).getCreateTime()+
                "," +houseDetailModel.get(0).getSaleSituation();
        fragmentList.add(HouseDetailTwoFragment.getInstance(str));
        fragmentList.add(HouseDetailThreeFragment.getInstance(b));

        setData();
    }

    private void setData(){

        bannerHouseDetail.setImages(housePic).setImageLoader(new GlideImageLoader()).start();
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(tabTitle.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(tabTitle.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(tabTitle.get(2)));

        HousePageAdapter adapter=new HousePageAdapter(getSupportFragmentManager());
        vpContent.setAdapter(adapter);
        vpContent.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(vpContent);
    }

    private void getData(String houseId){
        OkHttpUtils.get()
                .url(Constants.HOST+Constants.API.GET_HOUSE_DETAIL)
                .addParams("id",houseId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.d(e.getMessage());
                    }

                    @Override
                    public void onResponse(String responses, int id) {
                        LogUtils.d(responses);
                        response=responses;
                        houseDetailModel=new Gson().fromJson(responses,new TypeToken<List<HouseDetailModel>>(){}.getType());
                        initData();
                    }
                });
    }

    public class HousePageAdapter extends FragmentPagerAdapter{

        public HousePageAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return tabTitle.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return  tabTitle.get(position % tabTitle.size());
        }
    }


}
