package com.kysfw.www.sfw.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manbas on 17-5-25.
 * 装修案例
 */

public class DecorationCaseModel implements Parcelable{

    /**
     * _id : 4
     * 案例名称 : 布悠公馆
     * 发布时间 : 2017-02-24
     * 预算 : 2097152
     * 照片 :
     */

    @SerializedName("_id")
    private String id;
    @SerializedName("案例名称")
    private String caseName;
    @SerializedName("发布时间")
    private String createTime;
    @SerializedName("预算")
    private String budget;
    @SerializedName("照片")
    private String picPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
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
        parcel.writeString(caseName);
        parcel.writeString(createTime);
        parcel.writeString(budget);
        parcel.writeString(picPath);
    }

    public static final Parcelable.Creator<DecorationCaseModel> CREATOR=new Parcelable.Creator<DecorationCaseModel>(){

        @Override
        public DecorationCaseModel createFromParcel(Parcel parcel) {
            DecorationCaseModel m=new DecorationCaseModel();
            m.id=parcel.readString();
            m.caseName=parcel.readString();
            m.createTime=parcel.readString();
            m.budget=parcel.readString();
            m.picPath=parcel.readString();
            return m;
        }

        @Override
        public DecorationCaseModel[] newArray(int i) {
            return new DecorationCaseModel[i];
        }
    };

}
