package com.today.todayfarm.pages.EditFarmThing;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditFarmthingShifeiActivity extends Activity {


    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.edit)
    TextView edit;


    @BindView(R.id.fieldname) TextView tvfieldname;
    @BindView(R.id.cropInfo) TextView tvcropinfo;
    @BindView(R.id.shifeitype)
    EditText etshifeitype;
    @BindView(R.id.feiliaoname) EditText etfeiliaoname;
    @BindView(R.id.shifeistyle) EditText etshifeistyle;
    @BindView(R.id.starttime) TextView tvstarttime;
    @BindView(R.id.selectstarttime) TextView tvselectstarttime;
    @BindView(R.id.endtime) TextView tvendtime;
    @BindView(R.id.selectendtime) TextView tvselectendtime;
    @BindView(R.id.feipermu) EditText etfeipermu;
    @BindView(R.id.feiall) EditText etfeiall;
    @BindView(R.id.priceall) EditText etpriceall;
    @BindView(R.id.beizhu) EditText beizhu;
    @BindView(R.id.delete) TextView delete;


    @OnClick(R.id.selectstarttime)
    public void setTvselectstarttime() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tvstarttime.setText(i+"-"+i1+"-"+i2);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        ).show();
    }

    @OnClick(R.id.selectendtime)
    public void setTvselectendtime() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tvendtime.setText(i+"-"+i1+"-"+i2);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        ).show();
    }

    @OnClick(R.id.delete)
    public void delete() {
        //TODO 删除
    }

    @OnClick(R.id.back)
    public void back(){
        this.finish();
    }


    @OnClick(R.id.edit)
    public void setEdit() {
        if (tvcropinfo==null){
            new SweetAlertDialog(this)
                    .setTitleText("缺少作物信息")
                    .show();
            return;
        }

        if (tvfieldname==null){
            new SweetAlertDialog(this)
                    .setTitleText("缺少农田信息")
                    .show();
            return;
        }




    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_farmthing_shifei);

        ButterKnife.bind(this);

        back.setTypeface(MyApplication.iconTypeFace);
        edit.setTypeface(MyApplication.iconTypeFace);
    }
}
