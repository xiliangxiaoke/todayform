package com.today.todayfarm.dom;


public class GeoValue{

    String geotype;
    float[] center;
    String radius;


    public String getGeotype() {
        return geotype;
    }

    public void setGeotype(String geotype) {
        this.geotype = geotype;
    }

    public float[] getCenter() {
        return center;
    }

    public void setCenter(float[] center) {
        this.center = center;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }
}