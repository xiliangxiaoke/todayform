package com.today.todayfarm.pages.EditFarmThing;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditFarmthingZhibaoActivity extends Activity {

    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.edit)
    TextView edit;
    @BindView(R.id.fieldname) TextView fieldname;
    @BindView(R.id.cropInfo) TextView cropinfo;
    @BindView(R.id.zhibaotype)
    EditText zhibaotype;
    @BindView(R.id.zaihaitype)
    EditText zaihaitype;
    @BindView(R.id.zhibaostyle)
    EditText zhibaostyle;
    @BindView(R.id.useyaowu)
    EditText useyaowu;
    @BindView(R.id.zhibaoeffect)
    EditText zhibaoeffect;
    @BindView(R.id.starttime) TextView starttime;
    @BindView(R.id.selectstarttime) TextView selectstarttime;
    @BindView(R.id.endtime) TextView endtime;
    @BindView(R.id.selectendtime) TextView selectendtime;

    @BindView(R.id.sizepermu)
    EditText sizepermu;
    @BindView(R.id.zhibaoprice)
    EditText zhibaoprice;

    @BindView(R.id.beizhu) EditText beizhu;
    @BindView(R.id.delete) TextView delete;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_farmthing_zhibao);

        ButterKnife.bind(this);

        back.setTypeface(MyApplication.iconTypeFace);
        edit.setTypeface(MyApplication.iconTypeFace);
    }
}
