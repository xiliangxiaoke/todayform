package com.today.todayfarm.dom;

import java.util.List;

public class BoundaryInfo2Js {
    String geotype;
    List<Float> center;
    float radius;
    List<List<Float>> coordinates;
    String markertitle;
    String color;


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarkertitle() {
        return markertitle;
    }

    public void setMarkertitle(String markertitle) {
        this.markertitle = markertitle;
    }

    public String getGeotype() {
        return geotype;
    }

    public void setGeotype(String geotype) {
        this.geotype = geotype;
    }

    public List<Float> getCenter() {
        return center;
    }

    public void setCenter(List<Float> center) {
        this.center = center;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public List<List<Float>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<Float>> coordinates) {
        this.coordinates = coordinates;
    }
}
