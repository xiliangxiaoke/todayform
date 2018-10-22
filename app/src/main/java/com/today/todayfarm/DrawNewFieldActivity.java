package com.today.todayfarm;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Polygon;
import com.amap.api.maps2d.model.PolygonOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DrawNewFieldActivity extends Activity {

    @BindView(R.id.map)MapView mapView;
    @BindView(R.id.addpoint)
    Button addpointbutton;

    @OnClick(R.id.addpoint)
            void addPoint(){

        polygonOptions.add(aMap.getCameraPosition().target);
//        Log.d("drawnewfieldactivity", "addPoint: "+polygonOptions.getPoints());
        polygon.setPoints(polygonOptions.getPoints());

        aMap.invalidate();

    }

    @OnClick(R.id.savepolygon)
            void savePolygon(){
        AddNewFieldActivity.polygon = polygon;
        this.finish();
    }


    AMap aMap;
    Polygon polygon;


    // 声明 多边形参数对象
    PolygonOptions polygonOptions = new PolygonOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_new_field);
        ButterKnife.bind(this);


        mapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mapView.getMap();
        }


        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 设置卫星地图模式，aMap是地图控制器对象




        polygonOptions.strokeWidth(1) // 多边形的边框

                .strokeColor(Color.GREEN) // 边框颜色
                .fillColor(Color.parseColor("#ffffff00"));   // 多边形的填充色
        polygon = aMap.addPolygon(polygonOptions);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mapView.onSaveInstanceState(outState);
    }
}
