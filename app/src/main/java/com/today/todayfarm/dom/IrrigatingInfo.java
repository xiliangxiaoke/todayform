package com.today.todayfarm.dom;

/**
 * 农事 灌溉信息
 */
public class IrrigatingInfo {
    //"irrigatingActivityId":1,
    // "fieldId":"402881e66671a1c9016671a4e57a0000",
    // "cropId":"402881e66671da29016671dadf4f0000",
    // "irrigatingStartTime":"2018-10-10",
    // "irrigatingEndTime":"2018-10-10",
    // "waterPressure":0.25,
    // "voltage":"",
    // "equipmentSpeed":100.0,
    // "totalCost":100.0,
    // "irrigatingNote":"备注消息",
    // "userId":"20181014150813"

    String irrigatingActivityId;
    String fieldId;
    String        cropId;
    String irrigatingStartTime;
    String        irrigatingEndTime;
    String waterPressure;
    String         voltage;
    String equipmentSpeed;
    String         totalCost;
    String irrigatingNote;
    String         userId;
    String         imgUrl;

    public String getIrrigatingActivityId() {
        return irrigatingActivityId;
    }

    public void setIrrigatingActivityId(String irrigatingActivityId) {
        this.irrigatingActivityId = irrigatingActivityId;
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

    public String getIrrigatingStartTime() {
        return irrigatingStartTime;
    }

    public void setIrrigatingStartTime(String irrigatingStartTime) {
        this.irrigatingStartTime = irrigatingStartTime;
    }

    public String getIrrigatingEndTime() {
        return irrigatingEndTime;
    }

    public void setIrrigatingEndTime(String irrigatingEndTime) {
        this.irrigatingEndTime = irrigatingEndTime;
    }

    public String getWaterPressure() {
        return waterPressure;
    }

    public void setWaterPressure(String waterPressure) {
        this.waterPressure = waterPressure;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getEquipmentSpeed() {
        return equipmentSpeed;
    }

    public void setEquipmentSpeed(String equipmentSpeed) {
        this.equipmentSpeed = equipmentSpeed;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getIrrigatingNote() {
        return irrigatingNote;
    }

    public void setIrrigatingNote(String irrigatingNote) {
        this.irrigatingNote = irrigatingNote;
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
