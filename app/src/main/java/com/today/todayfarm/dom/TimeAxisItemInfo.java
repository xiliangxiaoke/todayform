package com.today.todayfarm.dom;

import java.util.List;

/**
 * 地图页面上的时间轴数据信息
 */
public class TimeAxisItemInfo {
    long timestamp; //时间戳
    int dateText; // 在时间轴上显示的日期
    int monthText; // 月份
    int year;
    List<HealthImgInfo> healthImgInfos; // 当天的健康监测影像信息
    List<SatellateImgInfo> satellateImgInfos; //当天的卫星影像信息

    boolean selected;


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<SatellateImgInfo> getSatellateImgInfos() {
        return satellateImgInfos;
    }

    public void setSatellateImgInfos(List<SatellateImgInfo> satellateImgInfos) {
        this.satellateImgInfos = satellateImgInfos;
    }

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
