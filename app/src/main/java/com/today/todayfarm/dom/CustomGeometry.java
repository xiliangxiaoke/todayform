package com.today.todayfarm.dom;

import java.util.List;

public class CustomGeometry {

    String type;
    List<Custompoint> coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Custompoint> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Custompoint> coordinates) {
        this.coordinates = coordinates;
    }
}
