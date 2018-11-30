package com.today.todayfarm.dom;

public class FieldBoundary {

    //{"geotype":"circle","center":[120.27225139026999,31.61661615834012],"radius":5569.976183498781}"

    //{"geotype":"polygon","geojson":{"type":"Feature","properties":{},"geometry":{"type":"Polygon",
    // "coordinates":[[[120.31744,31.64004],[120.327072,31.607779],[120.327415,31.56976],[120.265961,31.5604],[120.260124,31.632337],[120.31744,31.64004]]]}}}

    String geotype;
    float[] center;
    float radius;
    Geojson geojson;

    public Geojson getGeojson() {
        return geojson;
    }

    public void setGeojson(Geojson geojson) {
        this.geojson = geojson;
    }

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

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
