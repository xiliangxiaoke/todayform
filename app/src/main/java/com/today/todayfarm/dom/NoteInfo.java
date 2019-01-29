package com.today.todayfarm.dom;

import java.util.List;

/**
 * 注記
 */
public class NoteInfo {

    //{"scoutingNoteId":4,
    // "cropId":"402881e66671da29016671dadf4f0000",
    // "fieldId":"402881e66671a1c9016671a4e57a0000",
    // "scoutingNoteInfo":"植保类型",
    // "scoutingTime":"2018-10-10",
    // "scoutingPosition":"{lon:23.24,lat:123.456}",
    // "userId":"20181014150813",
    // "photos":[]}

    String scoutingNoteId;
    String cropId;
    String fieldId;
    String scoutingNoteInfo;
    String scoutingTime;
    String scoutingPosition;
    String userId;
//    String photos;


    public String getScoutingNoteId() {
        return scoutingNoteId;
    }

    public void setScoutingNoteId(String scoutingNoteId) {
        this.scoutingNoteId = scoutingNoteId;
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

    public String getScoutingNoteInfo() {
        return scoutingNoteInfo;
    }

    public void setScoutingNoteInfo(String scoutingNoteInfo) {
        this.scoutingNoteInfo = scoutingNoteInfo;
    }

    public String getScoutingTime() {
        return scoutingTime;
    }

    public void setScoutingTime(String scoutingTime) {
        this.scoutingTime = scoutingTime;
    }

    public String getScoutingPosition() {
        return scoutingPosition;
    }

    public void setScoutingPosition(String scoutingPosition) {
        this.scoutingPosition = scoutingPosition;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhotos() {
        return "";//photos;
    }

//    public void setPhotos(String photos) {
//        this.photos = photos;
//    }
}
