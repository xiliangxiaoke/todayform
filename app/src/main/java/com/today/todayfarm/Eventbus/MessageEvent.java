package com.today.todayfarm.Eventbus;

public  class MessageEvent {
    public String type;

    public String  data;

    public MessageEvent(String type, String data) {
        this.type = type;
        this.data = data;
    }
}
