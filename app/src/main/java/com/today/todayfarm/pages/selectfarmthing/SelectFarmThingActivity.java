package com.today.todayfarm.pages.selectfarmthing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingBozhongActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingGuangaiActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingShifeiActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingShougeActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingZhengdiActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingZhibaoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectFarmThingActivity extends Activity {

    @BindView(R.id.back)
    TextView back;

    @OnClick(R.id.back)
    public void back() {
        this.finish();
    }

    @OnClick(R.id.zhengdi)
    public void zhengdi() {
        Intent intent = new Intent(this, EditFarmthingZhengdiActivity.class);
        intent.putExtra("fieldinfo_json",fieldinfo_json);
        this.startActivity(intent);
        this.finish();
    }

    @OnClick(R.id.bozhong)
    public void bozhong() {
        Intent intent = new Intent(this, EditFarmthingBozhongActivity.class);
        intent.putExtra("fieldinfo_json",fieldinfo_json);
        this.startActivity(intent);
        this.finish();
    }

    @OnClick(R.id.shifei)
    public void shifei() {
        Intent intent = new Intent(this, EditFarmthingShifeiActivity.class);
        intent.putExtra("fieldinfo_json",fieldinfo_json);
        this.startActivity(intent);
        this.finish();
    }

    @OnClick(R.id.guangai)
    public void guangai() {
        Intent intent = new Intent(this, EditFarmthingGuangaiActivity.class);
        intent.putExtra("fieldinfo_json",fieldinfo_json);
        this.startActivity(intent);
        this.finish();
    }

    @OnClick(R.id.zhibao)
    public void zhibao() {
        Intent intent = new Intent(this, EditFarmthingZhibaoActivity.class);
        intent.putExtra("fieldinfo_json",fieldinfo_json);
        this.startActivity(intent);
        this.finish();
    }

    @OnClick(R.id.shouge)
    public void shouge() {
        Intent intent = new Intent(this, EditFarmthingShougeActivity.class);
        intent.putExtra("fieldinfo_json",fieldinfo_json);
        this.startActivity(intent);
        this.finish();
    }



    String fieldid;
    String fieldinfo_json;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_farm_thing);
        ButterKnife.bind(this);

        back.setTypeface(MyApplication.iconTypeFace);

        Intent intent = getIntent();
        fieldinfo_json = intent.getStringExtra("fieldinfo_json");
    }
}
