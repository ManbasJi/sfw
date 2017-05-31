package com.kysfw.www.sfw.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2017/5/23.
 */

public class HouseDetailModel {


    /**
     * _id : 2
     * 房屋位置 : 汕头市利安花园8栋102
     * 房源类型 : 一手房
     * 销售情况 : 开发中
     * 销售价格 : 1200000
     * 房屋面积 : 90
     * 房型 : 二房一
     * 发布日期 : 2017-01-14
     * 竣工年份 : 2010
     * 房屋朝向 : 东南
     * 所在楼层 : 9/20
     * 房屋结构 : 钢，钢筋混凝土结构
     * 装修程度 :
     * 房屋用途 : 多层住宅
     * 权属类别 : 公寓式住房
     * 装饰/设备 : 采光通透，家具齐全
     * 配套设施 : 小阳台，户外花园
     * 社区情况 : 24小时保安巡逻
     * 物管情况 : 24小时
     * 交通线路 : 14路，24路，12路公交车
     * 特别说明 : 需要每个月都交物业管理费
     * 照片 : housePics/201489802276.jpg|housePics/211489802276.jpg|housePics/221489802276.jpg
     * 状态 : 0
     * 经纪人 : 2
     * 昵称 : 蜜瓜
     * 联系电话 : 13417053830
     */

    @SerializedName("_id")
    private String houseId;
    @SerializedName("房屋位置")
    private String houseAddress;
    @SerializedName("房源类型")
    private String houseTypet;
    @SerializedName("销售情况")
    private String saleSituation;
    @SerializedName("销售价格")
    private String salePrice;
    @SerializedName("房屋面积")
    private String houseSize;
    @SerializedName("房型")
    private String houseType;
    @SerializedName("发布日期")
    private String createTime;
    @SerializedName("竣工年份")
    private String finishYear;
    @SerializedName("房屋朝向")
    private String houseDirection;
    @SerializedName("所在楼层")
    private String floor;
    @SerializedName("房屋结构")
    private String construction;
    @SerializedName("装修程度")
    private String finishLevel;
    @SerializedName("房屋用途")
    private String houseUse;
    @SerializedName("权属类别")
    private String qsType;
    @SerializedName("装饰/设备")
    private String equipment;
    @SerializedName("配套设施")
    private String pt;
    @SerializedName("社区情况")
    private String communitySituation;
    @SerializedName("物管情况")
    private String tenement;
    @SerializedName("交通线路")
    private String traffic;
    @SerializedName("特别说明")
    private String especiallyMsg;
    @SerializedName("照片")
    private String housePic;
    @SerializedName("状态")
    private String situation;
    @SerializedName("经纪人")
    private String agent;
    @SerializedName("昵称")
    private String agentName;
    @SerializedName("联系电话")
    private String phone;

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getHouseTypet() {
        return houseTypet;
    }

    public void setHouseTypet(String houseTypet) {
        this.houseTypet = houseTypet;
    }

    public String getSaleSituation() {
        return saleSituation;
    }

    public void setSaleSituation(String saleSituation) {
        this.saleSituation = saleSituation;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getHouseSize() {
        return houseSize;
    }

    public void setHouseSize(String houseSize) {
        this.houseSize = houseSize;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFinishYear() {
        return finishYear;
    }

    public void setFinishYear(String finishYear) {
        this.finishYear = finishYear;
    }

    public String getHouseDirection() {
        return houseDirection;
    }

    public void setHouseDirection(String houseDirection) {
        this.houseDirection = houseDirection;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getConstruction() {
        return construction;
    }

    public void setConstruction(String construction) {
        this.construction = construction;
    }

    public String getFinishLevel() {
        return finishLevel;
    }

    public void setFinishLevel(String finishLevel) {
        this.finishLevel = finishLevel;
    }

    public String getHouseUse() {
        return houseUse;
    }

    public void setHouseUse(String houseUse) {
        this.houseUse = houseUse;
    }

    public String getQsType() {
        return qsType;
    }

    public void setQsType(String qsType) {
        this.qsType = qsType;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public String getCommunitySituation() {
        return communitySituation;
    }

    public void setCommunitySituation(String communitySituation) {
        this.communitySituation = communitySituation;
    }

    public String getTenement() {
        return tenement;
    }

    public void setTenement(String tenement) {
        this.tenement = tenement;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public String getEspeciallyMsg() {
        return especiallyMsg;
    }

    public void setEspeciallyMsg(String especiallyMsg) {
        this.especiallyMsg = especiallyMsg;
    }

    public String getHousePic() {
        return housePic;
    }

    public void setHousePic(String housePic) {
        this.housePic = housePic;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
