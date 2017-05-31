package com.kysfw.www.sfw.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manbas on 17-5-25.
 *
 */

public class CommonSearchModel implements Parcelable{

    /**
     * _id : 4
     * 昵称 : manbas
     * 联系电话 : 15918881666
     * 地址 : 广东省汕头市
     * 照片 : avatar/manbas.jpg
     */

    @SerializedName("_id")
    private String id;
    @SerializedName("昵称")
    private String name="";
    @SerializedName("联系电话")
    private String phone;
    @SerializedName("地址")
    private String address;
    @SerializedName("照片")
    private String picPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(address);
        parcel.writeString(picPath);
    }

    public static final Parcelable.Creator<CommonSearchModel> CREATOR=new
            Parcelable.Creator<CommonSearchModel>(){

                @Override
                public CommonSearchModel createFromParcel(Parcel parcel) {
                    return new CommonSearchModel(parcel);
                }

                @Override
                public CommonSearchModel[] newArray(int i) {
                    return new CommonSearchModel[i];
                }
            };

            private CommonSearchModel(Parcel in){
                id=in.readString();
                name=in.readString();
                phone=in.readString();
                address=in.readString();
                picPath=in.readString();
            }
}


