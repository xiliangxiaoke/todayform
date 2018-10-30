package com.today.todayfarm.dom;

public class JSParamInfo<T> {
    String type;
    T params;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }
}
