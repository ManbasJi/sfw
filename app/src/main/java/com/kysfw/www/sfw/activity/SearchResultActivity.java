package com.kysfw.www.sfw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kysfw.www.sfw.R;
import com.kysfw.www.sfw.adapter.HouseListAdapter;
import com.kysfw.www.sfw.config.Constants;
import com.kysfw.www.sfw.interf.OnItemClickListener;
import com.kysfw.www.sfw.model.HouseBoxModel;
import com.kysfw.www.sfw.utils.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by admin on 2017/5/22.
 */

public class SearchResultActivity extends AppCompatActivity {
    @BindView(R.id.rv_searchResult)
    RecyclerView rvSearchResult;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private int page = 0;
    private int rownum = 10;

    private String houseAddress="";
    private String houseType="";
    private String hope_price1="", hope_price2="";
    private String house_size1="", house_size2="";
    private String createTime="", finishTime="";
    private String houseTypet="";
    private String saleSituation="";

    private List<HouseBoxModel> houseBoxModel;
    private boolean isRefresh = true;
    private HouseListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresult);
        ButterKnife.bind(this);
        houseBoxModel=new ArrayList<>();

        toolbar.setTitle("筛选结果");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        houseAddress = getIntent().getStringExtra("house_address");
        houseType = getIntent().getStringExtra("house_type");
        hope_price1 = getIntent().getStringExtra("hope_price1");
        hope_price2 = getIntent().getStringExtra("hope_price2");
        house_size1 = getIntent().getStringExtra("house_size1");
        house_size2 = getIntent().getStringExtra("house_size2");
        createTime = getIntent().getStringExtra("create_time");
        finishTime = getIntent().getStringExtra("finish_time");
        houseTypet = getIntent().getStringExtra("house_typet");
        saleSituation = getIntent().getStringExtra("sale_situation");

        getSearchResult();
        onListener();
    }

    private void onListener() {
        //加载更多
        rvSearchResult.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSlideToBottom(recyclerView)) {
                    // isRefresh 用来判断是否经过刷新，有刷新滚动到底部时再进行加载更多，
                    // 防止每次都进行加载更多，导致请求多次后台
                    if (isRefresh) {
                        page++;
                        getSearchResult();
                        isRefresh = false;
                    }
                }
            }
        });
        //刷新
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                getSearchResult();
            }
        });


    }

    private void onAdapter() {
        adapter = new HouseListAdapter(this, houseBoxModel);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSearchResult.setLayoutManager(manager);
        rvSearchResult.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View v) {
                Intent intent=new Intent(SearchResultActivity.this,HouseDetailActivity.class);
                intent.putExtra("houseId",houseBoxModel.get(position).houseId);
                startActivity(intent);
            }
        });
    }

    private void getSearchResult() {
        Map<String, String> map = new HashMap<>();
        if (!houseAddress.equals("")) map.put("uicti", houseAddress);
        if (!houseType.equals("")) map.put("pangheng", houseType);
        if (!hope_price1.equals("")) map.put("kecke1", hope_price1);
        if (!hope_price2.equals("")) map.put("kecke2", hope_price2);
        if (!house_size1.equals("")) map.put("mingtset1", house_size1);
        if (!house_size2.equals("")) map.put("mingtset2", house_size2);
        if (!createTime.equals("")) map.put("zikkhi1", createTime);
        if (!finishTime.equals("")) map.put("zikkhi2", finishTime);
        if (!houseTypet.equals("")) map.put("luiheng1", houseTypet);
        if (!saleSituation.equals("")) map.put("luiheng2", saleSituation);

        LogUtils.d("map.toString:"+map.toString());

        OkHttpUtils.get()
                .url(Constants.HOST + Constants.API.ADVANCED_SEARCH + "?page=" + page + "&rownum=" + rownum)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.d(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d(response);
                        List<HouseBoxModel> list = new Gson().fromJson(response,
                                new TypeToken<List<HouseBoxModel>>() {
                                }.getType());
                        if (list != null && list.size() > 0) {
                            if (page == 0) {
                                houseBoxModel = list;
                                onAdapter();
                            } else {
                                houseBoxModel.addAll(list);
                                if (adapter != null) {
                                    adapter.notifyDataSetChanged();
                                } else {
                                    onAdapter();
                                }
                            }
                        }
                        if (swipeRefresh.isRefreshing()) {
                            swipeRefresh.setRefreshing(false);
                        }
                    }
                });
    }

    //监听recyclerView是否滑动到底部，是则加载更多
    private boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
}
