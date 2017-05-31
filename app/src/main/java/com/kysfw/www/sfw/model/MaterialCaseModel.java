package com.kysfw.www.sfw.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manbas on 17-5-25.
 * 建材案例
 */

public class MaterialCaseModel implements Parcelable{

    /**
     * _id : 1
     * 材料名称 : 琉璃
     * 发布时间 : 2017-03-29
     * 价格 : 420000
     * 照片 :
     */

    @SerializedName("_id")
    private String id;
    @SerializedName("材料名称")
    private String materialName;
    @SerializedName("发布时间")
    private String createTime;
    @SerializedName("价格")
    private String price;
    @SerializedName("照片")
    private String pic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(materialName);
        parcel.writeString(createTime);
        parcel.writeString(price);
        parcel.writeString(pic);
    }

    public static final Parcelable.Creator<MaterialCaseModel> CREATOR=new Parcelable.Creator<MaterialCaseModel>(){

        @Override
        public MaterialCaseModel createFromParcel(Parcel parcel) {
            MaterialCaseModel m=new MaterialCaseModel();
            m.id=parcel.readString();
            m.createTime=parcel.readString();
            m.materialName=parcel.readString();
            m.pic=parcel.readString();
            m.price=parcel.readString();
            return m;
        }

        @Override
        public MaterialCaseModel[] newArray(int i) {
            return new MaterialCaseModel[0];
        }
    } ;
}
