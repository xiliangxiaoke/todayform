package com.today.todayfarm.dom;

public class StageInfo {

    //{"stageId":1777,
    // "cropId":1,
    // "stageStartDay":"",
    // "stageEndDay":"",
    // "stageName":"Pre Sowing",
    // "stageDescription":"A soil management practice that is in charge of soil preparation for a new crop. This is the final preparation?stage before the sowing.\\r\\n",
    // "stageLabel":"PAST",
    // "stageImageUrl":"https://d3v24yjyxmlw9f.cloudfront.net/agronomy/crop_stages/thumbnail_images/000/001/777/original/Pre_sowing.png",
    // "sortId":1}

    String stageId;
    String cropId;
    String stageStartDay;
    String stageEndDay;
    String stageName;
    String stageDescription;
    String stageLabel;
    String stageImageUrl;
    String sortId;

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public String getCropId() {
        return cropId;
    }

    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    public String getStageStartDay() {
        return stageStartDay;
    }

    public void setStageStartDay(String stageStartDay) {
        this.stageStartDay = stageStartDay;
    }

    public String getStageEndDay() {
        return stageEndDay;
    }

    public void setStageEndDay(String stageEndDay) {
        this.stageEndDay = stageEndDay;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getStageDescription() {
        return stageDescription;
    }

    public void setStageDescription(String stageDescription) {
        this.stageDescription = stageDescription;
    }

    public String getStageLabel() {
        return stageLabel;
    }

    public void setStageLabel(String stageLabel) {
        this.stageLabel = stageLabel;
    }

    public String getStageImageUrl() {
        return stageImageUrl;
    }

    public void setStageImageUrl(String stageImageUrl) {
        this.stageImageUrl = stageImageUrl;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }
}
