package com.kysfw.www.sfw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kysfw.www.sfw.R;
import com.kysfw.www.sfw.adapter.DecorationCaseAdapter;
import com.kysfw.www.sfw.adapter.HouseListAdapter;
import com.kysfw.www.sfw.adapter.MaterialCaseAdapter;
import com.kysfw.www.sfw.config.Constants;
import com.kysfw.www.sfw.interf.OnItemClickListener;
import com.kysfw.www.sfw.model.CommonSearchModel;
import com.kysfw.www.sfw.model.DecorationCaseModel;
import com.kysfw.www.sfw.model.HouseBoxModel;
import com.kysfw.www.sfw.model.MaterialCaseModel;
import com.kysfw.www.sfw.preinterface.CommonSearchView;
import com.kysfw.www.sfw.presenter.CommonSearchPresenter;
import com.kysfw.www.sfw.view.CustomLoading;
import com.kysfw.www.sfw.view.GlideCircleTransform;
import com.kysfw.www.sfw.view.GlideImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by manbas on 17-5-25.
 */

public class CommonDetailResultActivity extends AppCompatActivity implements CommonSearchView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_mainContent)
    RecyclerView rvMainContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.tv_contentTitle)
    TextView tvContentTitle;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.tv_topMsg)
    TextView tvTopMsg;

    private List<HouseBoxModel> houseList = new ArrayList<>();
    private List<MaterialCaseModel> materialList = new ArrayList<>();
    private List<DecorationCaseModel> decorationList = new ArrayList<>();

    private int page = 0;
    private int rownum = 10;
    private boolean isRefresh;
    private String status = "";//manager/decoration/material
    private String title;
    private String url;
    private Map<String, String> map;
    private String manager_id;
    private String id;

    private CustomLoading loading;
    private CommonSearchPresenter commonSearchPresenter;

    private HouseListAdapter houseListAdapter;
    private DecorationCaseAdapter decorationCaseAdapter;
    private MaterialCaseAdapter materialCaseAdapter;
    private CommonSearchModel model;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commondetail);
        ButterKnife.bind(this);

        map = new HashMap<>();
        commonSearchPresenter = new CommonSearchPresenter(this, this);
        loading = new CustomLoading(this, R.style.LoadingDialog);

        status = getIntent().getStringExtra("status");
        model = getIntent().getParcelableExtra("model");

        map.put("page", page + "");
        map.put("rownum", rownum + "");
        if (status.equals("manager")) {
            title = "经纪人";
            url = Constants.HOST + Constants.API.GET_HOUSES_LIST;
            map.put("manager_id", model.getId());
            tvContentTitle.setText("已发布房源");
        } else if (status.equals("decoration")) {
            title = "装修公司";
            url = Constants.HOST + Constants.API.GET_DECORATION_CASE_LIST;
            map.put("id", model.getId());
            tvContentTitle.setText("已发布案例");
        } else if (status.equals("material")) {
            title = "建材公司";
            url = Constants.HOST + Constants.API.GET_MATERIAL_CASE_LIST;
            map.put("id", model.getId());
            tvContentTitle.setText("已发布建材");
        }

        //toolbar
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

        //TopView
        Glide.with(this).load(Constants.HOST+model.getPicPath())
                .transform(new GlideCircleTransform(this)).into(ivPhoto);
        tvTopMsg.setText(model.getName()+"\n"+model.getPhone()+"\n"+model.getAddress());


        onListener();
        commonSearchPresenter.getCaseData(url, map);
    }

    private void onListener() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                map.clear();
                map.put("page", page + "");
                map.put("rownum", rownum + "");
                if (status.equals("manager")) {
                    map.put("manager_id", model.getId());
                } else {
                    map.put("id", model.getId());
                }
                commonSearchPresenter.getCaseData(url, map);
                isRefresh = true;
            }
        });

        rvMainContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (isSlideToBottom(recyclerView)) {
                    if (isRefresh) {
                        page++;
                        map.clear();
                        map.put("page", page + "");
                        map.put("rownum", rownum + "");
                        if (status.equals("manager")) {
                            map.put("manager_id", model.getId());
                        } else {
                            map.put("id", model.getId());
                        }
                        commonSearchPresenter.getCaseData(url, map);
                        isRefresh = false;
                    }

                }
            }
        });
    }

    private void setAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMainContent.setLayoutManager(layoutManager);
        if (status.equals("manager")) {
            houseListAdapter = new HouseListAdapter(this, houseList);
            rvMainContent.setAdapter(houseListAdapter);
            houseListAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void OnItemClick(int position, View v) {
                    Intent intent=new Intent(CommonDetailResultActivity.this, HouseDetailActivity.class);
                    intent.putExtra("houseId",houseList.get(position).houseId);
                    startActivity(intent);
                }
            });
        } else if (status.equals("decoration")) {
            decorationCaseAdapter = new DecorationCaseAdapter(this, decorationList);
            rvMainContent.setAdapter(decorationCaseAdapter);
        } else if (status.equals("material")) {
            materialCaseAdapter = new MaterialCaseAdapter(this, materialList);
            rvMainContent.setAdapter(materialCaseAdapter);
        }
    }


    @Override
    public void onBefore() {
        loading.show();
    }

    @Override
    public void onError(String msg) {
        Snackbar.make(rvMainContent.getRootView(), "网络连接失败！", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(List<CommonSearchModel> list, String response) {
        //这里list为null，不需要用到
        if (status.equals("manager")) {
            List<HouseBoxModel> datalist = new Gson().fromJson(response, new TypeToken<List<HouseBoxModel>>() {
            }.getType());
            if (page == 0) {
                houseList.clear();
            }
            houseList.addAll(datalist);
            if (houseListAdapter != null) {
                houseListAdapter.notifyDataSetChanged();
            } else {
                setAdapter();
            }
        } else if (status.equals("decoration")) {
            List<DecorationCaseModel> datalist = new Gson().fromJson(response, new TypeToken<List<DecorationCaseModel>>() {
            }.getType());
            if (page == 0) {
                decorationList.clear();
            }
            decorationList.addAll(datalist);
            if (decorationCaseAdapter != null) {
                decorationCaseAdapter.notifyDataSetChanged();
            } else {
                setAdapter();
            }
        } else if (status.equals("material")) {
            List<MaterialCaseModel> datalist = new Gson().fromJson(response, new TypeToken<List<MaterialCaseModel>>() {
            }.getType());
            if (page == 0) {
                materialList.clear();
            }
            materialList.addAll(datalist);
            if (materialCaseAdapter != null) {
                materialCaseAdapter.notifyDataSetChanged();
            } else {
                setAdapter();
            }
        }
    }

    @Override
    public void onFinish() {
        loading.dismiss();
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
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
