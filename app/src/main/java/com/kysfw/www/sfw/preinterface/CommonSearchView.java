package com.kysfw.www.sfw.preinterface;

import com.kysfw.www.sfw.model.CommonSearchModel;

import java.util.List;

/**
 * Created by manbas on 17-5-25.
 */

public interface CommonSearchView {

    public void onBefore();

    public void onError(String msg);

    /**
     * response:当返回的参数类型不是List<CommonSearchModel>的时候，返回response
     */
    public void onSuccess(List<CommonSearchModel> list,String response);

    public void onFinish();
}
