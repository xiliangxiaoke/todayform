package com.today.todayfarm.dom;

public class SoilInfo {
    //{"sl1":"160","sl2":"17","sl3":"12","phValue":"6.9","majorName":"单相淋溶土"}
    String sl1;
    String sl2;
    String sl3;
    String phValue;
    String majorName;


    public String getSl1() {
        return sl1;
    }

    public void setSl1(String sl1) {
        this.sl1 = sl1;
    }

    public String getSl2() {
        return sl2;
    }

    public void setSl2(String sl2) {
        this.sl2 = sl2;
    }

    public String getSl3() {
        return sl3;
    }

    public void setSl3(String sl3) {
        this.sl3 = sl3;
    }

    public String getPhValue() {
        return phValue;
    }

    public void setPhValue(String phValue) {
        this.phValue = phValue;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }
}
