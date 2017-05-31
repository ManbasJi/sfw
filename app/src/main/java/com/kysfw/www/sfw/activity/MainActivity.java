package com.kysfw.www.sfw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kysfw.www.sfw.R;
import com.kysfw.www.sfw.adapter.MainAdapter;
import com.kysfw.www.sfw.config.Constants;
import com.kysfw.www.sfw.interf.OnItemClickListener;
import com.kysfw.www.sfw.model.HouseBoxModel;
import com.kysfw.www.sfw.utils.LogUtils;
import com.kysfw.www.sfw.utils.ToastUtils;
import com.kysfw.www.sfw.view.CustomLoading;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.lv_left_main)
    ListView lvLeftMain;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    TextView tvNoHouse;

    private List<Integer> imgPathList;
    private MainAdapter adapter;

    private int page=0;
    private int showNum=10;
    private boolean isRefresh=true;

    private List<HouseBoxModel> houseBoxModels=new ArrayList<>();
    private String url="";
    private CustomLoading customLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tvNoHouse= (TextView) findViewById(R.id.tv_nohouse);
        customLoading=new CustomLoading(this,R.style.LoadingDialog);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.mipmap.personal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("首页");
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //设置轮播图 源
        imgPathList=new ArrayList<>();
        imgPathList.add(R.mipmap.house1);
        imgPathList.add(R.mipmap.house2);
        imgPathList.add(R.mipmap.house3);

        url=Constants.HOST+Constants.API.GET_HOUSES_LIST;
        getData();
        onListener();

    }

    private Toolbar.OnMenuItemClickListener onMenuItemClickListener=new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.action_search:
                    Intent intent=new Intent(MainActivity.this,SearchHouseActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    private void onListener(){
        rvMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(isSlideToBottom(recyclerView)){
                    // isRefresh 用来判断是否经过刷新，有刷新滚动到底部时再进行加载更多，
                    // 防止每次都进行加载更多，导致请求多次后台
                    if(isRefresh){
                        page++;
                        getData();
                        isRefresh=false;
                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh=true;
                page=0;
                getData();
            }
        });


    }

    private void onAdapter(){
        adapter=new MainAdapter(this,houseBoxModels,imgPathList);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMain.setLayoutManager(manager);
        rvMain.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View v) {
                switch (position){
                    case 0:
                        url=Constants.HOST+Constants.API.GET_HOUSES_LIST;
                        page=0;
                        getSupportActionBar().setTitle("首页");
                        getData();
                        break;
                    case 1:
                        url=Constants.HOST+Constants.API.GET_NEW_HOUSES_LIST;
                        page=0;
                        getSupportActionBar().setTitle("新房");
                        getData();
                        break;
                    case 2:
                        url=Constants.HOST+Constants.API.GET_USED_HOUSES_LIST;
                        page=0;
                        getSupportActionBar().setTitle("二手房");
                        getData();
                        break;
                    case 3:
                        url=Constants.HOST+Constants.API.GET_RENTAL_HOUSES_LIST;
                        page=0;
                        getSupportActionBar().setTitle("租房");
                        getData();
                        break;
                    case 4:
                        break;
                    case 5:
                        Intent intent=new Intent(MainActivity.this,CommonSearchActivity.class);
                        intent.putExtra("status","找经纪人");
                        startActivity(intent);
                        break;
                    case 6:
                        Intent intent1=new Intent(MainActivity.this,CommonSearchActivity.class);
                        intent1.putExtra("status","找装修");
                        startActivity(intent1);
                        break;
                    case 7:
                        Intent intent2=new Intent(MainActivity.this,CommonSearchActivity.class);
                        intent2.putExtra("status","买建材");
                        startActivity(intent2);
                        break;
                }
            }
        });
    }

    private void getData(){
        OkHttpUtils.get()
                .url(url)
                .addParams("page",page+"")
                .addParams("rownum",showNum+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request, int id) {
                        customLoading.show();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.d(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        List<HouseBoxModel> resultlist=new Gson().fromJson(response,
                                new TypeToken<List<HouseBoxModel>>(){}.getType());
                            if(page==0){
                                //刷新

                                houseBoxModels.clear();
                                houseBoxModels.addAll(resultlist);
                                if(adapter!=null){
//                                    adapter.notifyItemRangeChanged(1,houseBoxModels.size()-1);
                                    adapter.notifyDataSetChanged();
                                }else{
                                    onAdapter();
                                }
                                if(resultlist==null || resultlist.size()<=0){
                                    tvNoHouse.setVisibility(View.VISIBLE);
                                }else{
                                    tvNoHouse.setVisibility(View.GONE);
                                }
                            }else{
                                //加载更多
                                houseBoxModels.addAll(resultlist);
                                if(adapter!=null)
                                    adapter.notifyDataSetChanged();
                            }
                        if(swipeRefreshLayout.isRefreshing())
                            swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onAfter(int id) {
                        customLoading.dismiss();
                    }
                });
    }

    //侧滑栏
    private ImageView ivPhoto;
    private TextView tvName;
    private void setLeftView(){
        View leftHeardView=getLayoutInflater().inflate(R.layout.main_left_view,null);
        lvLeftMain.addHeaderView(leftHeardView);
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
