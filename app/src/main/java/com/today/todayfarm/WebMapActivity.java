package com.today.todayfarm;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;


//import com.tencent.smtt.sdk.WebSettings;
//import com.tencent.smtt.sdk.WebView;

//import com.mapbox.mapboxsdk.maps.MapView;
//import com.mapbox.mapboxsdk.maps.MapboxMap;
//import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebMapActivity extends Activity {

//    @BindView(R.id.tbswebView)
//    WebView tbsWebView;

//    @BindView(R.id.mapView)
//    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_map);
        ButterKnife.bind(this);



//        WebSettings webSettings = tbsWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);

//        tbsWebView.loadUrl("file:///android_asset/V520openlayer/examples/mobile-full-screen.html");
//        tbsWebView.loadUrl("file:///android_asset/V520openlayer/examples/drag-rotate-and-zoom.html");
//        tbsWebView.loadUrl("file:///android_asset/V520openlayer/index.html");


        //-----mapbox
//        mapView = (MapView) findViewById(R.id.mapView);
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(MapboxMap mapboxMap) {
//                // Customize map with markers , polylines, etc.
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mapView.onDestroy();
    }
}
