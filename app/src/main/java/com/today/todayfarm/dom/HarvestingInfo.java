package com.today.todayfarm.dom;


/**
 * 收割信息
 */
public class HarvestingInfo {

    // "harvestingActivityId":1,
    // "fieldId":"402881e66671a1c9016671a4e57a0000",
    // "cropId":"",
    // "harvestingStartTime":"2018-10-25",
    // "harvestingUnit":"",
    // "harvestingMachine":"",
    // "harvestingCount":"",
    // "pullTrackCount":"",
    // "totalYield":20.0,
    // "yieldPerAcre":"",
    // "harvestingPricePreAcre":20.0,
    // "totalCost":200.2,
    // "harvestingNote":"整地描述",
    // "userId":""


    String harvestingActivityId;
    String         fieldId;
    String cropId;
    String  harvestingStartTime;
    String  harvestingUnit;
    String  harvestingMachine;
    String harvestingCount;
    String         pullTrackCount;
    String totalYield;
    String        yieldPerAcre;
    String harvestingPricePreAcre;
    String         totalCost;
    String harvestingNote;
    String         userId;
    String         imgUrl;


    public String getHarvestingActivityId() {
        return harvestingActivityId;
    }

    public void setHarvestingActivityId(String harvestingActivityId) {
        this.harvestingActivityId = harvestingActivityId;
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

    public String getHarvestingStartTime() {
        return harvestingStartTime;
    }

    public void setHarvestingStartTime(String harvestingStartTime) {
        this.harvestingStartTime = harvestingStartTime;
    }

    public String getHarvestingUnit() {
        return harvestingUnit;
    }

    public void setHarvestingUnit(String harvestingUnit) {
        this.harvestingUnit = harvestingUnit;
    }

    public String getHarvestingMachine() {
        return harvestingMachine;
    }

    public void setHarvestingMachine(String harvestingMachine) {
        this.harvestingMachine = harvestingMachine;
    }

    public String getHarvestingCount() {
        return harvestingCount;
    }

    public void setHarvestingCount(String harvestingCount) {
        this.harvestingCount = harvestingCount;
    }

    public String getPullTrackCount() {
        return pullTrackCount;
    }

    public void setPullTrackCount(String pullTrackCount) {
        this.pullTrackCount = pullTrackCount;
    }

    public String getTotalYield() {
        return totalYield;
    }

    public void setTotalYield(String totalYield) {
        this.totalYield = totalYield;
    }

    public String getYieldPerAcre() {
        return yieldPerAcre;
    }

    public void setYieldPerAcre(String yieldPerAcre) {
        this.yieldPerAcre = yieldPerAcre;
    }

    public String getHarvestingPricePreAcre() {
        return harvestingPricePreAcre;
    }

    public void setHarvestingPricePreAcre(String harvestingPricePreAcre) {
        this.harvestingPricePreAcre = harvestingPricePreAcre;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getHarvestingNote() {
        return harvestingNote;
    }

    public void setHarvestingNote(String harvestingNote) {
        this.harvestingNote = harvestingNote;
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
