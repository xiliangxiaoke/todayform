package com.today.todayfarm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Polygon;
import com.amap.api.maps2d.model.PolygonOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNewFieldActivity extends Activity {

    public static Polygon polygon = null;
    int requestcode = 11;

    boolean insavemode = false;

    @BindView(R.id.map)
    MapView mapView;

    @OnClick(R.id.drawfield) void drawfield(){

        if(insavemode){
            //保存农田

        }else {
            Intent intent = new Intent();
            intent.setClass(this , DrawNewFieldActivity.class);
            this.startActivityForResult(intent,requestcode);
        }

    }

    @BindView(R.id.drawfield)
    Button drawbtn;

    AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_field);
        ButterKnife.bind(this);

        mapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mapView.getMap();
        }


        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 设置卫星地图模式，aMap是地图控制器对象


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (polygon!=null){
            insavemode = true;
            drawbtn.setText("保存");
            mapView.setVisibility(View.VISIBLE);
            double minlng,maxlng,minlat,maxlat;
            minlat = polygon.getPoints().get(0).latitude;
            maxlat = polygon.getPoints().get(0).latitude;
            minlng = polygon.getPoints().get(0).longitude;
            maxlng = polygon.getPoints().get(0).longitude;

            for (int i=0;i<polygon.getPoints().size();i++){
                LatLng ll = polygon.getPoints().get(i);
                if(ll.latitude<minlat) minlat = ll.latitude;
                if(ll.latitude>maxlat) maxlat = ll.latitude;
                if(ll.longitude<minlng) minlng = ll.longitude;
                if(ll.longitude>maxlng) maxlng = ll.longitude;
            }


            LatLngBounds llb = new LatLngBounds.Builder()
                .include(new LatLng(minlat,minlng))
                .include(new LatLng(maxlat,maxlng)).build();

            aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(llb,20));

            // 声明 多边形参数对象
            PolygonOptions polygonOptions = new PolygonOptions();
            polygonOptions.strokeWidth(2) // 多边形的边框
                    .strokeColor(Color.GREEN) // 边框颜色
                    .fillColor(Color.GREEN);   // 多边形的填充色
            polygonOptions.addAll(polygon.getPoints());
            aMap.addPolygon(polygonOptions);
        }
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
