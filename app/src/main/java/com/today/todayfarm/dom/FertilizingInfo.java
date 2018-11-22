package com.today.todayfarm.dom;

/**
 * 施肥
 */
public class FertilizingInfo {
    //"fertilizingActivityId":1,
    // "fieldId":"402881e66671a1c9016671a4e57a0000",
    // "cropId":"402881e66671da29016671dadf4f0000",
    // "fertilizingType":"无人机施肥",
    // "fertilizingMethod":"无人机",
    // "fertilizingAcre":40.25,
    // "fertilizingStartTime":"2018-10-10",
    // "fertilizingEndTime":"2018-10-10",
    // "totalQuantity":100.0,"
    // totalCost":100.0,"
    // fertilizingNote":"备注消息","
    // userId":"20181014150813"
    String fertilizingActivityId;
    String         cropId;
    String  fieldId;
    String          fertilizingType;
    String fertilizingName;
    String fertilizingMethod;
    String  fertilizingAcre;
    String fertilizingStartTime;
    String  fertilizingEndTime;
    String totalQuantity;
    String  totalCost;
    String fertilizingNote;
    String         userId;
    String imgUrl;


    public String getFertilizingName() {
        return fertilizingName;
    }

    public void setFertilizingName(String fertilizingName) {
        this.fertilizingName = fertilizingName;
    }

    public String getFertilizingActivityId() {
        return fertilizingActivityId;
    }

    public void setFertilizingActivityId(String fertilizingActivityId) {
        this.fertilizingActivityId = fertilizingActivityId;
    }

    public String getCropId() {
        return cropId;
    }

    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFertilizingType() {
        return fertilizingType;
    }

    public void setFertilizingType(String fertilizingType) {
        this.fertilizingType = fertilizingType;
    }

    public String getFertilizingMethod() {
        return fertilizingMethod;
    }

    public void setFertilizingMethod(String fertilizingMethod) {
        this.fertilizingMethod = fertilizingMethod;
    }

    public String getFertilizingAcre() {
        return fertilizingAcre;
    }

    public void setFertilizingAcre(String fertilizingAcre) {
        this.fertilizingAcre = fertilizingAcre;
    }

    public String getFertilizingStartTime() {
        return fertilizingStartTime;
    }

    public void setFertilizingStartTime(String fertilizingStartTime) {
        this.fertilizingStartTime = fertilizingStartTime;
    }

    public String getFertilizingEndTime() {
        return fertilizingEndTime;
    }

    public void setFertilizingEndTime(String fertilizingEndTime) {
        this.fertilizingEndTime = fertilizingEndTime;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getFertilizingNote() {
        return fertilizingNote;
    }

    public void setFertilizingNote(String fertilizingNote) {
        this.fertilizingNote = fertilizingNote;
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
