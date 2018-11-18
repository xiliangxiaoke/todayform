package com.today.todayfarm.pages.EditFarmThing;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditFarmthingShougeActivity extends Activity {

    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.edit)
    TextView edit;

    @BindView(R.id.fieldname) TextView fieldname;
    @BindView(R.id.cropInfo) TextView cropinfo;
    @BindView(R.id.starttime) TextView starttime;
    @BindView(R.id.selectstarttime) TextView selectstarttime;
    @BindView(R.id.company)
    EditText company;
    @BindView(R.id.mechine) EditText mechine;
    @BindView(R.id.mechinenum) EditText mechinenum;
    @BindView(R.id.layuncarnum) EditText layuncarnum;
    @BindView(R.id.allnum) EditText allnum;
    @BindView(R.id.average) EditText average;
    @BindView(R.id.priceofunit) EditText priceofunit;
    @BindView(R.id.priceall) EditText priceall;
    @BindView(R.id.beizhu) EditText beizhu;
    @BindView(R.id.delete) TextView delete;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_farmthing_shouge);

        ButterKnife.bind(this);

        back.setTypeface(MyApplication.iconTypeFace);
        edit.setTypeface(MyApplication.iconTypeFace);
    }
}
