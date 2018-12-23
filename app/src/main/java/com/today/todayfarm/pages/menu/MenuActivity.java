package com.today.todayfarm.pages.menu;

import android.app.Activity;
import android.app.usage.UsageEvents;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.today.todayfarm.Eventbus.MessageEvent;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends Activity {


    @BindView(R.id.panel)
    RelativeLayout panel;
    @BindView(R.id.mainpage)
    TextView mainpage;

    @BindView(R.id.field)
    TextView field;
    @BindView(R.id.suggest)
    TextView suggest;
    @BindView(R.id.farmthing)
    TextView farmthing;
    @BindView(R.id.zhuji)
    TextView zhuji;
    @BindView(R.id.weather)
    TextView weather;
    @BindView(R.id.setting)
    TextView setting;

    @OnClick(R.id.panel)
    public void setPanel(){
        this.finish();
        //overridePendingTransition(R.anim.out_to_left,R.anim.in_from_left);
    }


    @OnClick({R.id.mainpage,R.id.field,R.id.suggest,R.id.farmthing,R.id.zhuji,R.id.weather,R.id.setting})
    public void menuclick(View view){

        String type = "";

        switch (view.getId()){
            case R.id.mainpage:
                type = "menu_mainpage";
                break;
            case R.id.field:
                type = "menu_field";
                break;
            case R.id.suggest:
                type = "menu_suggest";
                break;
            case R.id.farmthing:
                type = "menu_farmthing";
                break;
            case R.id.zhuji:
                type = "menu_zhuji";
                break;
            case R.id.weather:
                type = "menu_weather";
                break;
            case R.id.setting:
                type = "menu_setting";
                break;
        }

        MessageEvent messageEvent= new MessageEvent(type,"");


        this.finish();

        EventBus.getDefault().postSticky(messageEvent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);

        mainpage.setTypeface(MyApplication.iconTypeFace);
        field.setTypeface(MyApplication.iconTypeFace);
        suggest.setTypeface(MyApplication.iconTypeFace);
        farmthing.setTypeface(MyApplication.iconTypeFace);
        zhuji.setTypeface(MyApplication.iconTypeFace);
        weather.setTypeface(MyApplication.iconTypeFace);
        setting.setTypeface(MyApplication.iconTypeFace);



    }
}
