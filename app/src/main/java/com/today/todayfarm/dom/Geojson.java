package com.today.todayfarm.dom;

public class Geojson {


    //"geojson":{"type":"Feature","properties":{},
    // "geometry":{"type":"Polygon","coordinates":[[[120.281754,31.61772],[120.309563,31.624737],[120.318489,31.606317],[120.309219,31.58731],[120.281754,31.61772]]]}}

    String type;

    GeojsonGeometry geometry;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GeojsonGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(GeojsonGeometry geometry) {
        this.geometry = geometry;
    }
}
