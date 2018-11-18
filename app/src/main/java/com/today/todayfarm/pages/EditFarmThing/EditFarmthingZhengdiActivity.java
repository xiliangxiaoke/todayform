package com.today.todayfarm.pages.EditFarmThing;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditFarmthingZhengdiActivity extends Activity {

    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.edit)
    TextView edit;
    @BindView(R.id.fieldname) TextView fieldname;
    @BindView(R.id.cropInfo) TextView cropinfo;
    @BindView(R.id.zhengditype)
    EditText zhengditype;
    @BindView(R.id.carhead) EditText carhead;
    @BindView(R.id.mechine) EditText mechine;
    @BindView(R.id.starttime) TextView starttime;
    @BindView(R.id.selectstarttime) TextView selectstarttime;
    @BindView(R.id.endtime) TextView endtime;
    @BindView(R.id.selectendtime) TextView selectendtime;
    @BindView(R.id.deepth) EditText deepth;
    @BindView(R.id.pricepermu) EditText pricepermu;
    @BindView(R.id.priceall) EditText priceall;
    @BindView(R.id.beizhu) EditText beizhu;
    @BindView(R.id.delete) TextView delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_farmthing_zhengdi);

        ButterKnife.bind(this);

        back.setTypeface(MyApplication.iconTypeFace);
        edit.setTypeface(MyApplication.iconTypeFace);
    }
}
