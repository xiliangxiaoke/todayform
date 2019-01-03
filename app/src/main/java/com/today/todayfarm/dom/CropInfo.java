package com.today.todayfarm.dom;

public class CropInfo {

    //{"cropId":"402881e66671da29016671dadf4f0000","fieldId":"402881e66671a1c9016671a4e57a0000","cropName":"玉米","plantYear":2018,"totalCost":"","totalRevenue":""}
    String cropId;
    String fieldId;
    String cropName;
    int plantYear;
    String totalCost;
    String totalRevenue;


    //{"cropId":1,"cropName":"Corn",
    // "cropLargeImageUrl":"https://d3v24yjyxmlw9f.cloudfront.net/agronomy/crops/large_images/000/000/001/original/large.png",
    // "cropMediumImageUrl":"https://d3v24yjyxmlw9f.cloudfront.net/agronomy/crops/medium_images/000/000/001/original/medium.png",
    // "cropSmallImageUrl":"https://d3v24yjyxmlw9f.cloudfront.net/agronomy/crops/small_images/000/000/001/original/small.png",
    // "isAdded":"",
    // "isAgronomyAvailable":"true",
    // "isIrrigated":"",
    // "plantingDate":""}


    String cropLargeImageUrl;
    String cropMediumImageUrl;
    String cropSmallImageUrl;
    String isAdded;
    String isAgronomyAvailable;
    String isIrrigated;
    String plantingDate;

    public String getCropLargeImageUrl() {
        return cropLargeImageUrl;
    }

    public void setCropLargeImageUrl(String cropLargeImageUrl) {
        this.cropLargeImageUrl = cropLargeImageUrl;
    }

    public String getCropMediumImageUrl() {
        return cropMediumImageUrl;
    }

    public void setCropMediumImageUrl(String cropMediumImageUrl) {
        this.cropMediumImageUrl = cropMediumImageUrl;
    }

    public String getCropSmallImageUrl() {
        return cropSmallImageUrl;
    }

    public void setCropSmallImageUrl(String cropSmallImageUrl) {
        this.cropSmallImageUrl = cropSmallImageUrl;
    }

    public String getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(String isAdded) {
        this.isAdded = isAdded;
    }

    public String getIsAgronomyAvailable() {
        return isAgronomyAvailable;
    }

    public void setIsAgronomyAvailable(String isAgronomyAvailable) {
        this.isAgronomyAvailable = isAgronomyAvailable;
    }

    public String getIsIrrigated() {
        return isIrrigated;
    }

    public void setIsIrrigated(String isIrrigated) {
        this.isIrrigated = isIrrigated;
    }

    public String getPlantingDate() {
        return plantingDate;
    }

    public void setPlantingDate(String plantingDate) {
        this.plantingDate = plantingDate;
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

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public int getPlantYear() {
        return plantYear;
    }

    public void setPlantYear(int plantYear) {
        this.plantYear = plantYear;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(String totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
