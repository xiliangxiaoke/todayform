package com.today.todayfarm.util;


import android.webkit.JavascriptInterface;

/***
 * 返回的json 遵循统一的结构
 * {
 *     type:"",
 *          1 "returngeojson"  返回绘制的边界
 *     value:string,
 * }
 */
public class Android2JS extends Object {

    private CallBack callBack = null;

    public Android2JS(CallBack callBack) {
        this.callBack = callBack;
    }

    @JavascriptInterface
    public void call(String json){
        if (this.callBack!=null){
            this.callBack.callback(json);
        }
    }

    //--------------



    public interface CallBack{
        void callback(String json);
    }
}
