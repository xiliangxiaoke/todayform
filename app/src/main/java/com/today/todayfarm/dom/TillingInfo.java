package com.today.todayfarm.dom;


/**
 * 整地记录详情
 */
public class TillingInfo {

    //"tillingActivityId":2,
    // "fieldId":"402881e66671a1c9016671a4e57a0000",
    // "cropId":"402881e66671da29016671dadf4f0000",
    // "tillingType":"整地方式",
    // "tillingTractor":"整地车头",
    // "tillingMechanical":"整地机械",
    // "tillingStartTime":"2018-10-10",
    // tillingEndTime":"2018-10-10",
    // "tillingDepth":0.25,
    // "pricePerAcre":100.0,
    // "totalCost":100.0,
    // "tillingNote":"备注消息",
    // "userId":"20181014150813"

    String tillingActivityId;
    String         fieldId;
    String cropId;
    String         tillingType;
    String tillingTractor;
    String        tillingMechanical;
    String tillingDepth;
    String tillingStartTime;
    String        tillingEndTime;
    String                pricePerAcre;
    String totalCost;
    String         tillingNote;
    String userId;
    String cropName;
    String plantYear;


    String imgUrl;

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getPlantYear() {
        return plantYear;
    }

    public void setPlantYear(String plantYear) {
        this.plantYear = plantYear;
    }

    public String getTillingActivityId() {
        return tillingActivityId;
    }

    public void setTillingActivityId(String tillingActivityId) {
        this.tillingActivityId = tillingActivityId;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getCropId() {
        return cropId;
    }

    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    public String getTillingType() {
        return tillingType;
    }

    public void setTillingType(String tillingType) {
        this.tillingType = tillingType;
    }

    public String getTillingTractor() {
        return tillingTractor;
    }

    public void setTillingTractor(String tillingTractor) {
        this.tillingTractor = tillingTractor;
    }

    public String getTillingMechanical() {
        return tillingMechanical;
    }

    public void setTillingMechanical(String tillingMechanical) {
        this.tillingMechanical = tillingMechanical;
    }

    public String getTillingDepth() {
        return tillingDepth;
    }

    public void setTillingDepth(String tillingDepth) {
        this.tillingDepth = tillingDepth;
    }

    public String getTillingStartTime() {
        return tillingStartTime;
    }

    public void setTillingStartTime(String tillingStartTime) {
        this.tillingStartTime = tillingStartTime;
    }

    public String getTillingEndTime() {
        return tillingEndTime;
    }

    public void setTillingEndTime(String tillingEndTime) {
        this.tillingEndTime = tillingEndTime;
    }

    public String getPricePerAcre() {
        return pricePerAcre;
    }

    public void setPricePerAcre(String pricePerAcre) {
        this.pricePerAcre = pricePerAcre;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getTillingNote() {
        return tillingNote;
    }

    public void setTillingNote(String tillingNote) {
        this.tillingNote = tillingNote;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
