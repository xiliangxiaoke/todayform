package com.today.todayfarm.dom;

public class GeojsonGeometry {
    //{"type":"Polygon","coordinates":[[[120.281754,31.61772],[120.309563,31.624737],[120.318489,31.606317],[120.309219,31.58731],[120.281754,31.61772]]]}
    String type;
    float[][][] coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float[][][] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(float[][][] coordinates) {
        this.coordinates = coordinates;
    }
}
