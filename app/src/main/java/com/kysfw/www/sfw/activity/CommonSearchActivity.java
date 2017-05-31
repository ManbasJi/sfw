package com.kysfw.www.sfw.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.kysfw.www.sfw.R;
import com.kysfw.www.sfw.adapter.CommonSearchAdapter;
import com.kysfw.www.sfw.config.Constants;
import com.kysfw.www.sfw.interf.OnItemClickListener;
import com.kysfw.www.sfw.model.CommonSearchModel;
import com.kysfw.www.sfw.preinterface.CommonSearchView;
import com.kysfw.www.sfw.presenter.CommonSearchPresenter;
import com.kysfw.www.sfw.utils.LogUtils;
import com.kysfw.www.sfw.view.CustomDividerItemDecoration;
import com.kysfw.www.sfw.view.CustomLoading;
import com.kysfw.www.sfw.view.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by manbas on 17-5-25.
 * 找经纪人、找建材、找装修
 */

public class CommonSearchActivity extends AppCompatActivity implements CommonSearchView{


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.banner_search)
    Banner bannerSearch;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.rv_mainContent)
    RecyclerView rvMainContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private List<CommonSearchModel> datalist;
    private CommonSearchPresenter commonSearchPresenter;
    private CommonSearchAdapter adapter;

    private CustomLoading loading;

    private int page=0;
    private int rownum=10;
    private String url="";
    private String status="";//manager 经理人\decoration 装修\material 建材
    private boolean isRefresh;
    List<Integer> imgPic = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commonsearch);
        ButterKnife.bind(this);

        datalist=new ArrayList<>();
        loading=new CustomLoading(this,R.style.LoadingDialog);
        //实例化presenter
        commonSearchPresenter=new CommonSearchPresenter(this,this);
        //判断页面状态
        status=getIntent().getStringExtra("status");
        if(status.equals("找经纪人")){
            url= Constants.HOST+Constants.API.GET_MANAGER_LIST;
            edtSearch.setHint("请输入要查找经纪人的昵称...");
        }else if(status.equals("找装修")) {
            url= Constants.HOST+Constants.API.GET_DECORATION_COMPANY_LIST;
            edtSearch.setHint("请输入装修公司名称...");
        }else if(status.equals("买建材")){
            url= Constants.HOST+Constants.API.GET_MATERIAL_COMPANY_LIST;
            edtSearch.setHint("请输入建材公司名称...");
        }
        setToolbar();

        if(!url.equals("")){
            commonSearchPresenter.getData(url,page,rownum,edtSearch.getText().toString());
        }


        imgPic.add(R.mipmap.house1);
        imgPic.add(R.mipmap.house2);
        imgPic.add(R.mipmap.house3);
        bannerSearch.setImages(imgPic).setImageLoader(new GlideImageLoader()).start();
        onListener();
    }

    private void setToolbar(){
        toolbar.setTitle(status);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setAdapter(){
        adapter=new CommonSearchAdapter(this,datalist);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMainContent.setLayoutManager(layoutManager);
        rvMainContent.setAdapter(adapter);
        rvMainContent.addItemDecoration(new CustomDividerItemDecoration(this));
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View v) {
                Intent intent=new Intent(CommonSearchActivity.this,CommonDetailResultActivity.class);
                Bundle b=new Bundle();
                if(status.equals("找经纪人")){
                    b.putString("status","manager");
                }else if(status.equals("找装修")){
                    b.putString("status","decoration");
                }else if(status.equals("买建材")){
                    b.putString("status","material");
                }
                b.putParcelable("model",datalist.get(position));
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    private void onListener(){
//        bannerSearch.setOnBannerListener(new OnBannerListener() {
//            @Override
//            public void OnBannerClick(int position) {
//                Intent intent=new Intent(CommonSearchActivity.this,BigPhotoActivity.class);
////                intent.putStringArrayListExtra("imgPath", (ArrayList<String>)imgPic );
//                intent.putExtra("index",position);
////                startActivity(intent);
//                ActivityOptionsCompat compat=ActivityOptionsCompat.makeScaleUpAnimation(
//                        bannerSearch,bannerSearch.getWidth()/2,bannerSearch.getHeight()/2,0,0
//                );
//                ActivityCompat.startActivity(CommonSearchActivity.this,intent,compat.toBundle());
//            }
//        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=0;
                commonSearchPresenter.getData(url,page,rownum,edtSearch.getText().toString());
                isRefresh=true;
            }
        });

        rvMainContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(isSlideToBottom(recyclerView)){
                    if(isRefresh){
                        page++;
                        commonSearchPresenter.getData(url,page,rownum,edtSearch.getText().toString());
                        isRefresh=false;
                    }

                }
            }
        });
    }

    @OnClick(R.id.iv_search)
    public void onViewClicked() {
        InputMethodManager imm= (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtSearch.getWindowToken(),0);
        if(edtSearch.getText().toString().equals("")){
            Snackbar.make(bannerSearch.getRootView(),"请输入要查找的内容!",Snackbar.LENGTH_SHORT).show();
            return;
        }
        commonSearchPresenter.getData(url,page,rownum,edtSearch.getText().toString());
    }


    @Override
    public void onBefore() {
        loading.show();
    }

    @Override
    public void onError(String msg) {
        LogUtils.d(msg);
        Snackbar.make(bannerSearch.getRootView(),"网络连接失败!",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(List<CommonSearchModel> list,String response) {
        if(page==0)
            datalist.clear();
        datalist.addAll(list);
        if (adapter != null)
            adapter.notifyDataSetChanged();
        else
            setAdapter();
    }

    @Override
    public void onFinish() {
        loading.dismiss();
        if(swipeRefresh.isRefreshing()){
            swipeRefresh.setRefreshing(false);
        }
    }


    //监听recyclerView是否滑动到底部，是则加载更多
    private boolean isSlideToBottom(RecyclerView recyclerView){
        if(recyclerView==null) return false;
        if(recyclerView.computeVerticalScrollExtent()+recyclerView.computeVerticalScrollOffset()
                >=recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
}
