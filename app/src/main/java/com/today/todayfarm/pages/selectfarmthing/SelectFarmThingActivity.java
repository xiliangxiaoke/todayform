package com.today.todayfarm.pages.selectfarmthing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.today.todayfarm.R;
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
        this.startActivity(new Intent(this, EditFarmthingZhengdiActivity.class));
    }

    @OnClick(R.id.bozhong)
    public void bozhong() {
        this.startActivity(new Intent(this, EditFarmthingBozhongActivity.class));
    }

    @OnClick(R.id.shifei)
    public void shifei() {
        this.startActivity(new Intent(this, EditFarmthingShifeiActivity.class));
    }

    @OnClick(R.id.guangai)
    public void guangai() {
        this.startActivity(new Intent(this, EditFarmthingGuangaiActivity.class));
    }

    @OnClick(R.id.zhibao)
    public void zhibao() {
        this.startActivity(new Intent(this, EditFarmthingZhibaoActivity.class));
    }

    @OnClick(R.id.shouge)
    public void shouge() {
        this.startActivity(new Intent(this, EditFarmthingShougeActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_farm_thing);
        ButterKnife.bind(this);
    }
}
