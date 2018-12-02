package com.today.todayfarm.util;

import android.os.Build;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;

public class WebUtil {
    /**
     * 本地调用JS
     * @param json {type:functiontype,params:params obj}
     *
     *             1 定位 {type:"location",params:{longitude:117,latitude:36}}
     *             2 绘制 {type:"draw",params:{action:"drawcircle|drawpolygon|getgeojson"}}
     *             3 显示一个geo {type:"showgeo",params:"geojson string"}
     */
    public static void callJS(WebView map, String json) {
        //Log.v("boundary--",json);
        //Log.v("boundary--loadurl","javascript:callJS('"+json+"')");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            map.evaluateJavascript("javascript:callJS('"+json+"')", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    // JS 回调
                }
            });
        }else{
            map.loadUrl("javascript:callJS('"+json+"')");
        }

    }
}
