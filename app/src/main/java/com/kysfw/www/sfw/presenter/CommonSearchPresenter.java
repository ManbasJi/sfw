package com.kysfw.www.sfw.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kysfw.www.sfw.model.CommonSearchModel;
import com.kysfw.www.sfw.preinterface.CommonSearchView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by manbas on 17-5-25.
 */

public class CommonSearchPresenter {

    List<CommonSearchModel> list;
    private Context context;
    private CommonSearchView commonSearchView;
    private String url;

    public CommonSearchPresenter(Context context,CommonSearchView view){
        this.context=context;
        this.commonSearchView=view;
    }


    public void getData(String url,int page,int rownum,String search){
        OkHttpUtils.get()
                .url(url)
                .addParams("page",page+"")
                .addParams("rownum",rownum+"")
                .addParams("search",search+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request, int id) {

                        commonSearchView.onBefore();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        commonSearchView.onError(e.getMessage());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        list=new Gson().fromJson(response
                                ,new TypeToken<List<CommonSearchModel>>(){}.getType());
                        commonSearchView.onSuccess(list,"");
                    }

                    @Override
                    public void onAfter(int id) {
                        commonSearchView.onFinish();
                    }
                });
    }


    public void getCaseData(String url, Map<String,String> map){
        OkHttpUtils.get()
                .url(url)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request, int id) {

                        commonSearchView.onBefore();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        commonSearchView.onError(e.getMessage());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        commonSearchView.onSuccess(null,response);
                    }

                    @Override
                    public void onAfter(int id) {
                        commonSearchView.onFinish();
                    }
                });
    }
}
