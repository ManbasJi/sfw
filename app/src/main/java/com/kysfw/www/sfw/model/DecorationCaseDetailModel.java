package com.kysfw.www.sfw.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manbas on 17-5-26.
 */

public class DecorationCaseDetailModel {


    /**
     * _id : 1
     * 案例名称 : 叶流公馆
     * 预算 : 2097152
     * 面积 : 500
     * 发布时间 : 2017-02-24
     * 设计说明 : 叶流公馆的装修方案
     * 照片 :
     * 发布公司 : 1
     */

    @SerializedName("_id")
    private String id;
    @SerializedName("案例名称")
    private String caseName;
    @SerializedName("预算")
    private String budget;
    @SerializedName("面积")
    private String size;
    @SerializedName("发布时间")
    private String createTime;
    @SerializedName("设计说明")
    private String designMsg;
    @SerializedName("照片")
    private String pic;
    private String 发布公司;

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

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDesignMsg() {
        return designMsg;
    }

    public void setDesignMsg(String designMsg) {
        this.designMsg = designMsg;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String get发布公司() {
        return 发布公司;
    }

    public void set发布公司(String 发布公司) {
        this.发布公司 = 发布公司;
    }
}
