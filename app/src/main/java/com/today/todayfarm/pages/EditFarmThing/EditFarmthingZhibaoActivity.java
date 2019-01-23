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
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.customView.PicHorizentalList;
import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.dom.SprayingInfo;
import com.today.todayfarm.pages.selectcrop.SelectCropActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.ToastUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditFarmthingZhibaoActivity extends Activity {

    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.edit)
    TextView edit;
    @BindView(R.id.fieldname) TextView tvfieldname;
    @BindView(R.id.cropInfo) TextView tvcropinfo;
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
    @BindView(R.id.starttime) TextView tvstarttime;
    @BindView(R.id.selectstarttime) TextView selectstarttime;
    @BindView(R.id.endtime) TextView tvendtime;
    @BindView(R.id.selectendtime) TextView selectendtime;

    @BindView(R.id.sizepermu)
    EditText sizepermu;
    @BindView(R.id.zhibaoprice)
    EditText zhibaoprice;

    @BindView(R.id.beizhu) EditText beizhu;
    @BindView(R.id.delete) TextView delete;
    @BindView(R.id.pics)
    PicHorizentalList pics;

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
        API.deleteSowingById(
                Hawk.get(HawkKey.TOKEN),
                sprayingActivityId,
                new ApiCallBack<Object>() {
                    @Override
                    public void onResponse(ResultObj<Object> resultObj) {
                        //删除成功
                        ToastUtil.show(EditFarmthingZhibaoActivity.this,"删除成功");
                        EditFarmthingZhibaoActivity.this.finish();
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }

    @OnClick(R.id.back)
    public void back(){
        this.finish();
    }


    @OnClick(R.id.edit)
    public void setEdit() {
        if (tvcropinfo.getText().toString().length()==0){
            new SweetAlertDialog(this)
                    .setTitleText("缺少作物信息")
                    .show();
            return;
        }

        if (fieldInfo==null){
            new SweetAlertDialog(this)
                    .setTitleText("缺少农田信息")
                    .show();
            return;
        }


        API.sprayingSaveOrUpdate(
                Hawk.get(HawkKey.TOKEN),
                fieldInfo.getFieldId(),
                cropInfo.getCropId(),
                sprayingActivityId,
                zhibaotype.getText().toString(),
                zaihaitype.getText().toString(),
                zhibaostyle.getText().toString(),
                useyaowu.getText().toString(),
                zhibaoeffect.getText().toString(),
                tvstarttime.getText().toString(),
                tvendtime.getText().toString(),
                sizepermu.getText().toString(),
                zhibaoprice.getText().toString(),
                beizhu.getText().toString(),
                pics.geturls(),// todo: img list
                new ApiCallBack<Object>() {
                    @Override
                    public void onResponse(ResultObj<Object> resultObj) {
                        if (resultObj.getCode() == 0) {
                            //保存成功
                            ToastUtil.show(EditFarmthingZhibaoActivity.this,"保存成功");
                            EditFarmthingZhibaoActivity.this.finish();
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
    String sprayingActivityId = null;
    String fieldname = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_farmthing_zhibao);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));

        ButterKnife.bind(this);

        back.setTypeface(MyApplication.iconTypeFace);
        edit.setTypeface(MyApplication.iconTypeFace);


        fieldinfo_json = getIntent().getStringExtra("fieldinfo_json");
        fieldInfo = new Gson().fromJson(fieldinfo_json, FieldInfo.class);
        sprayingActivityId = getIntent().getStringExtra("id");
        fieldname = getIntent().getStringExtra("fieldname");

        // 显示地块名称
        if (fieldInfo != null) {
            tvfieldname.setText(fieldInfo.getFieldName()+"  植保");
        }

        if (fieldname != null) {
            tvfieldname.setText(fieldname+"  植保");
        }

        if (sprayingActivityId != null && sprayingActivityId.length()>0) {
            // TODO: get sowing detail
            API.getSprayingById(
                    Hawk.get(HawkKey.TOKEN),
                    sprayingActivityId,
                    new ApiCallBack<SprayingInfo>() {
                        @Override
                        public void onResponse(ResultObj<SprayingInfo> resultObj) {
                            if (resultObj.getCode() == 0) {
                                SprayingInfo info = resultObj.getObject();
                                zhibaotype.setText(info.getSprayingType());
                                zaihaitype.setText(info.getDisasterType());
                                zhibaostyle.setText(info.getSprayingMethod());
                                useyaowu.setText(info.getDrugName());
                                zhibaoeffect.setText(info.getSprayingEffect());
                                tvstarttime.setText(info.getSprayingStartTime());
                                tvendtime.setText(info.getSprayingEndTime());
                                sizepermu.setText(info.getQuantityPerAcre());
                                zhibaoprice.setText(info.getTotalCost());
                                beizhu.setText(info.getSprayingNote());
                                pics.initdata(info.getImgUrl());

                                // 获取作物信息显示
                                API.getCropInfoById(
                                        Hawk.get(HawkKey.TOKEN), info.getCropId(),
                                        new ApiCallBack<CropInfo>() {
                                            @Override
                                            public void onResponse(ResultObj<CropInfo> resultObj) {
                                                if (resultObj.getCode() == 0) {
                                                    cropInfo = resultObj.getObject();
                                                    String t = cropInfo.getCropName()+" "+cropInfo.getPlantYear();
                                                    tvcropinfo.setText(t);
                                                }
                                            }

                                            @Override
                                            public void onError(int code) {

                                            }
                                        }
                                );

                            }
                        }

                        @Override
                        public void onError(int code) {

                        }
                    }
            );
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
