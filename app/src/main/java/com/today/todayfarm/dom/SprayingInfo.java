package com.today.todayfarm.dom;

/**
 * 植保记录详情
 */
public class SprayingInfo {

    //"sprayingActivityId":3,
    // "fieldId":"402881e66671a1c9016671a4e57a0000","
    // cropId":"402881e66671da29016671dadf4f0000","
    // sprayingType":"植保类型","
    // disasterType":"灾害类型","
    // sprayingMethod":"植保方式",
    // "drugName":"所用药物",
    // "sprayingEffect":"所用药物",
    // "sprayingStartTime":"2018-10-10",
    // "sprayingEndTime":"2018-10-10",
    // "quantityPerAcre":100.0,
    // "totalCost":100.0,
    // "sprayingNote":"备注消息",
    // "userId":"20181014150813"


    String sprayingActivityId;
    String         fieldId;
    String sprayingType;
    String cropId;
    String disasterType;
    String         sprayingMethod;
    String drugName;
    String         sprayingEffect;
    String sprayingStartTime;
    String         sprayingEndTime;
    String quantityPerAcre;
    String         totalCost;
    String sprayingNote;
    String        userId;
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

    public String getSprayingActivityId() {
        return sprayingActivityId;
    }

    public void setSprayingActivityId(String sprayingActivityId) {
        this.sprayingActivityId = sprayingActivityId;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getSprayingType() {
        return sprayingType;
    }

    public void setSprayingType(String sprayingType) {
        this.sprayingType = sprayingType;
    }

    public String getCropId() {
        return cropId;
    }

    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    public String getDisasterType() {
        return disasterType;
    }

    public void setDisasterType(String disasterType) {
        this.disasterType = disasterType;
    }

    public String getSprayingMethod() {
        return sprayingMethod;
    }

    public void setSprayingMethod(String sprayingMethod) {
        this.sprayingMethod = sprayingMethod;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getSprayingEffect() {
        return sprayingEffect;
    }

    public void setSprayingEffect(String sprayingEffect) {
        this.sprayingEffect = sprayingEffect;
    }

    public String getSprayingStartTime() {
        return sprayingStartTime;
    }

    public void setSprayingStartTime(String sprayingStartTime) {
        this.sprayingStartTime = sprayingStartTime;
    }

    public String getSprayingEndTime() {
        return sprayingEndTime;
    }

    public void setSprayingEndTime(String sprayingEndTime) {
        this.sprayingEndTime = sprayingEndTime;
    }

    public String getQuantityPerAcre() {
        return quantityPerAcre;
    }

    public void setQuantityPerAcre(String quantityPerAcre) {
        this.quantityPerAcre = quantityPerAcre;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getSprayingNote() {
        return sprayingNote;
    }

    public void setSprayingNote(String sprayingNote) {
        this.sprayingNote = sprayingNote;
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
