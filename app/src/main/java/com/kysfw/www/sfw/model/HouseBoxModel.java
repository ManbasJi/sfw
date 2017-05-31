package com.kysfw.www.sfw.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2017/5/20.
 */

public class HouseBoxModel {
    @SerializedName("_id")
    public String houseId;
    @SerializedName("房屋位置")
    public String address;
    @SerializedName("房源类型")
    public String houseType;
    @SerializedName("房屋面积")
    public String houseArea;
    @SerializedName("房屋朝向")
    public String direction;
    @SerializedName("房型")
    public String houseTypeT;
    @SerializedName("销售情况")
    public String saleSituation;
    @SerializedName("销售价格")
    public String salePrice;
    @SerializedName("照片")
    public String houseImg;

}
