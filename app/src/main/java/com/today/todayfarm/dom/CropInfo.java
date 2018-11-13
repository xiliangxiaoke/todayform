package com.today.todayfarm.dom;

public class CropInfo {

    //{"cropId":"402881e66671da29016671dadf4f0000","fieldId":"402881e66671a1c9016671a4e57a0000","cropName":"玉米","plantYear":2018,"totalCost":"","totalRevenue":""}
    String cropId;
    String fieldId;
    String cropName;
    String plantYear;
    String totalCost;
    String totalRevenue;


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

    public String getPlantYear() {
        return plantYear;
    }

    public void setPlantYear(String plantYear) {
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
