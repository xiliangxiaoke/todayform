package com.today.todayfarm.pages.EditFarmThing;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.pages.selectcrop.SelectCropActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.ToastUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

        API.irrigatingSaveOrUpdate(
                Hawk.get(HawkKey.TOKEN),
                fieldInfo.getFieldId(),
                irrigatingId,
                cropInfo.getCropId(),

                tvstarttime.getText().toString(),
                tvendtime.getText().toString(),
                etwaterpress.getText().toString(),
                etepress.getText().toString(),
                etdevicespeed.getText().toString(),
                etprice.getText().toString(),
                etbeizhu.getText().toString(),
                new ApiCallBack<Object>() {
                    @Override
                    public void onResponse(ResultObj<Object> resultObj) {
                        if (resultObj.getCode() == 0) {
                            //保存成功
                            ToastUtil.show(EditFarmthingGuangaiActivity.this,"保存成功");
                            EditFarmthingGuangaiActivity.this.finish();
                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }

        );


    }

    @OnClick(R.id.cropInfo)
    public void setTvcropinfo() {
        Intent intent = new Intent(this, SelectCropActivity.class);
        intent.putExtra("fieldinfo_json",fieldinfo_json);
        this.startActivityForResult(intent,SelectCropActivity.REQUEST_CODE_SELECT_CROP_ACTIVITY);
    }


    FieldInfo fieldInfo = null;
    CropInfo cropInfo = null;
    String fieldinfo_json;
    String irrigatingId = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_farmthing_guangai);
        ButterKnife.bind(this);

        back.setTypeface(MyApplication.iconTypeFace);
        edit.setTypeface(MyApplication.iconTypeFace);

        fieldinfo_json = getIntent().getStringExtra("fieldinfo_json");
        fieldInfo = new Gson().fromJson(fieldinfo_json, FieldInfo.class);
        irrigatingId = getIntent().getStringExtra("irrigatingId");

        // 显示地块名称
        if (fieldInfo != null) {
            tvfieldname.setText(fieldInfo.getFieldName()+"  灌溉");
        }

        if (irrigatingId != null && irrigatingId.length()>0) {
            // TODO: get sowing detail
        } else {
            tvcropinfo.setText("请选择作物");
            tvcropinfo.setTextColor(Color.parseColor("#FF0000"));
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SelectCropActivity.REQUEST_CODE_SELECT_CROP_ACTIVITY) {
            //由选择作物页面返回
            if (resultCode == SelectCropActivity.RESULT_CODE_SELECT_CROP_ACTIVITY) {
                String cropjson =  data.getStringExtra("cropinfo_json");
                cropInfo = new Gson().fromJson(cropjson, CropInfo.class);

                tvcropinfo.setText(cropInfo.getCropName()+"  "+cropInfo.getPlantYear());
            }
        }
    }
}
