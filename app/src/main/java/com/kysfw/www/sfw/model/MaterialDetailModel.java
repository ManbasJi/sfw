package com.kysfw.www.sfw.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manbas on 17-5-26.
 */

public class MaterialDetailModel {

    /**
     * _id : 1
     * 材料名称 : 琉璃
     * 品牌 : Lapis-Lazuli
     * 价格 : 420000
     * 发布时间 : 2017-03-29
     * 产品说明 : 建材
     * 照片 :
     * 发布公司 : 1
     */

    @SerializedName("_id")
    private String id;
    @SerializedName("材料名称")
    private String materialName;
    @SerializedName("品牌")
    private String brand;
    @SerializedName("价格")
    private String price;
    @SerializedName("发布时间")
    private String createTime;
    @SerializedName("产品说明")
    private String productMsg;
    @SerializedName("照片")
    private String pic;
    @SerializedName("发布公司")
    private String createComp;

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProductMsg() {
        return productMsg;
    }

    public void setProductMsg(String productMsg) {
        this.productMsg = productMsg;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCreateComp() {
        return createComp;
    }

    public void setCreateComp(String createComp) {
        this.createComp = createComp;
    }
}
