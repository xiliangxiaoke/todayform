package com.today.todayfarm.dom;

public class SubStageInfo {
    //{"contentId":12226,
    // "stageId":1772,
    // "cropId":1,
    // "contentHeading":"Pest Management: Stalk Borer",
    // "contentDescription":"",
    // "contentLargeImageUrl":"https://d3v24yjyxmlw9f.cloudfront.net/agronomy/attachments/attachments/000/017/468/layer_two_image/Pest_Management_Stalk_Borer.jpg",
    // "contentSmallImageUrl":"https://d3v24yjyxmlw9f.cloudfront.net/agronomy/attachments/attachments/000/017/468/layer_one_image/Pest_Management_Stalk_Borer.jpg",
    // "sortId":1}

    String contentId;
    String stageId;
    String cropId;
    String contentHeading;
    String contentDescription;
    String contentLargeImageUrl;
    String contentSmallImageUrl;
    String sortId;


    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

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

    public String getContentHeading() {
        return contentHeading;
    }

    public void setContentHeading(String contentHeading) {
        this.contentHeading = contentHeading;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    public String getContentLargeImageUrl() {
        return contentLargeImageUrl;
    }

    public void setContentLargeImageUrl(String contentLargeImageUrl) {
        this.contentLargeImageUrl = contentLargeImageUrl;
    }

    public String getContentSmallImageUrl() {
        return contentSmallImageUrl;
    }

    public void setContentSmallImageUrl(String contentSmallImageUrl) {
        this.contentSmallImageUrl = contentSmallImageUrl;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }
}
