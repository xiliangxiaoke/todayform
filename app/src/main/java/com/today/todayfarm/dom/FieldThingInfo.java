package com.today.todayfarm.dom;

/**
 * 农事信息
 */
public class FieldThingInfo {
    //{"id":2,"startDate":"2018年10月10日","status":"已完成","endDate":"2018年10月10日","type":"施肥"}
    String id;
    String startDate;
    String status;
    String endDate;
    String type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
