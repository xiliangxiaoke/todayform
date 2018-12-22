package com.today.todayfarm.dom;

import java.util.List;

/**
 * 地图页面上的时间轴数据信息
 */
public class TimeAxisItemInfo {
    long timestamp; //时间戳
    int dateText; // 在时间轴上显示的日期
    int monthText; // 月份
    List<HealthImgInfo> healthImgInfos; // 当天的健康监测影像信息

    // TODO 补充卫星影像的


    public int getMonthText() {
        return monthText;
    }

    public void setMonthText(int monthText) {
        this.monthText = monthText;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getDateText() {
        return dateText;
    }

    public void setDateText(int dateText) {
        this.dateText = dateText;
    }

    public List<HealthImgInfo> getHealthImgInfos() {
        return healthImgInfos;
    }

    public void setHealthImgInfos(List<HealthImgInfo> healthImgInfos) {
        this.healthImgInfos = healthImgInfos;
    }
}
