package com.today.todayfarm.pages.EditFarmThing;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditFarmthingGuangaiActivity extends Activity {

    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.edit)
    TextView edit;

    @BindView(R.id.fieldname) TextView tvfieldname;
    @BindView(R.id.cropInfo) TextView tvcropinfo;
    @BindView(R.id.starttime) TextView tvstarttime;
    @BindView(R.id.selectstarttime) TextView tvselectstarttime;
    @BindView(R.id.waterpress)
    EditText etwaterpress;
    @BindView(R.id.epress) EditText etepress;
    @BindView(R.id.devicespeed) EditText etdevicespeed;
    @BindView(R.id.endtime) TextView tvendtime;
    @BindView(R.id.selectendtime) TextView tvselectendtime;
    @BindView(R.id.price) EditText etprice;
    @BindView(R.id.beizhu) EditText etbeizhu;
    @BindView(R.id.delete) TextView tvdelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_farmthing_guangai);
        ButterKnife.bind(this);

        back.setTypeface(MyApplication.iconTypeFace);
        edit.setTypeface(MyApplication.iconTypeFace);
    }
}
