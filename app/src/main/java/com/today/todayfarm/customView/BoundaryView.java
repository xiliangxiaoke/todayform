package com.today.todayfarm.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.today.todayfarm.dom.BoundaryInfo2Js;

import java.util.List;

public class BoundaryView extends View{

    // 定义画笔
      private Paint mPaint;



      BoundaryInfo2Js data = null;


    public BoundaryView(Context context) {
        super(context);
        init(context);
    }

    public BoundaryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BoundaryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // 初始化画笔、Rect
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaint.setColor(Color.parseColor("#89e49a"));

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(4f);

    }

    public void setData(BoundaryInfo2Js d){
        data=d;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        try {
            float devide = 10;

            float x = getWidth();
            float y = getHeight();
            float realX = x-devide*2;
            float realY = y-devide*2;

            if (data != null) {
                if ("circle".equals(data.getGeotype())){

                    float r = realX<=realY ? realX/2 : realY/2;
                    canvas.drawCircle(x/2,y/2,r,mPaint);
                    mPaint.setStyle(Paint.Style.STROKE);
                    mPaint.setColor(Color.parseColor("#01b327"));
                    canvas.drawCircle(x/2,y/2,r,mPaint);
                } else if ("polygon".equals(data.getGeotype())) {


                    // get map latlng extent
                    float minlat = 0;
                    float minlng = 0;
                    float maxlat =0;
                    float maxlng = 0;

                    for (int i=0;i<data.getCoordinates().size();i++) {
                        List<Float> ll = data.getCoordinates().get(i);
                        if (i==0){
                            minlat = ll.get(0);minlng = ll.get(1);
                            maxlat = ll.get(0);maxlng = ll.get(1);
                        }else{
                            minlat = minlat<ll.get(0)?minlat:ll.get(0);
                            minlng = minlng<ll.get(1)?minlng:ll.get(1);
                            maxlat = maxlat>ll.get(0)?maxlat:ll.get(0);
                            maxlng = maxlng>ll.get(1)?maxlng:ll.get(1);
                        }
                    }
                    Log.v("extent:",minlat+" "+maxlat+" "+minlng+" "+maxlng);

                    float dlat = maxlat-minlat;
                    float dlng = maxlng-minlng;

                    float clat = minlat+dlat/2;
                    float clng = minlng+dlng/2;
                    float cx = x/2;
                    float cy = y/2;

                    float dll = dlat>dlng?dlat:dlng;

                    // 以画布中心做参考进行绘制
                    Path path = new Path();
                    for (int i=0;i<data.getCoordinates().size();i++) {
                        List<Float> ll = data.getCoordinates().get(i);

                        float tx = (ll.get(1)-clng)*realX/dlng+cx;
                        float ty = (-ll.get(0)+clat)*realY/dlat+cy;

                        if (i==0){
                            path.moveTo(tx,ty);
                        }else{
                            path.lineTo(tx,ty);
                        }
                    }
                    path.close();




                    canvas.drawPath(path,mPaint);
                    mPaint.setStyle(Paint.Style.STROKE);
                    mPaint.setColor(Color.parseColor("#01b327"));
                    canvas.drawPath(path,mPaint);
                }
            }
        }catch (Exception e){
            Log.e("ondraw:",e.getMessage());
        }





    }
}