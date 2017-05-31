package com.kysfw.www.sfw.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2017/5/19.
 */

public class UserInfo {
    @SerializedName("_id")
    public String userId;
    @SerializedName("昵称")
    public String userName;
    @SerializedName("照片")
    public String image;
    @SerializedName("密码")
    public String password;
    @SerializedName("联系电话")
    public String phone;
    @SerializedName("地址")
    public String address;

}
