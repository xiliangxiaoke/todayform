package com.today.todayfarm.restapi;

import com.today.todayfarm.dom.ResultObj;

public interface ApiCallBack<T> {
    void onResponse(ResultObj<T> resultObj);
    void onError(int code);
}
