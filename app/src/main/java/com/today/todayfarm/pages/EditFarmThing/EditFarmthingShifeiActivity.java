package com.today.todayfarm.pages.EditFarmThing;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.today.todayfarm.dom.FertilizingInfo;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.pages.note.EditNoteActivity;
import com.today.todayfarm.pages.selectcrop.SelectCropActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.ToastUtil;

import java.util.Arrays;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.shinichi.library.ImagePreview;

public class EditFarmthingShifeiActivity extends Activity {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;

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
    @BindView(R.id.pics)
    PicHorizentalList pics;

    @OnClick(R.id.selectstarttime)
    public void setTvselectstarttime() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tvstarttime.setText(i+"-"+(i1+1)+"-"+i2);
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
                        tvendtime.setText(i+"-"+(i1+1)+"-"+i2);
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
        API.deleteFertilizingById(
                Hawk.get(HawkKey.TOKEN),
                fertilizingActivityId,
                new ApiCallBack<Object>() {
                    @Override
                    public void onResponse(ResultObj<Object> resultObj) {
                        //删除成功
                        ToastUtil.show(EditFarmthingShifeiActivity.this,"删除成功");
                        EditFarmthingShifeiActivity.this.finish();
                    }

                    @Override
                    public void onError(int code) {
                        ToastUtil.show(EditFarmthingShifeiActivity.this,"删除失败");
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
        try{
            if (tvcropinfo.getText().toString().length()==0){
                new SweetAlertDialog(this)
                        .setTitleText("缺少作物信息")
                        .show();
                return;
            }

//            if (fieldInfo==null){
//                new SweetAlertDialog(this)
//                        .setTitleText("缺少农田信息")
//                        .show();
//                return;
//            }


            API.fertilizingSaveOrUpdate(
                    Hawk.get(HawkKey.TOKEN),
                    fieldInfo==null?fertilizingInfo.getFieldId():fieldInfo.getFieldId(),
                    cropInfo.getCropId(),
                    fertilizingActivityId,
                    etfeiliaoname.getText().toString(),
                    etshifeitype.getText().toString(),
                    etshifeistyle.getText().toString(),
                    etfeipermu.getText().toString(),
                    tvstarttime.getText().toString(),
                    tvendtime.getText().toString(),
                    etfeiall.getText().toString(),
                    etpriceall.getText().toString(),
                    beizhu.getText().toString(),
                    pics.geturls(),// todo: img list
                    new ApiCallBack<Object>() {
                        @Override
                        public void onResponse(ResultObj<Object> resultObj) {
                            if (resultObj.getCode() == 0) {
                                //保存成功
                                ToastUtil.show(EditFarmthingShifeiActivity.this,"保存成功");
                                EditFarmthingShifeiActivity.this.finish();
                            }
                        }

                        @Override
                        public void onError(int code) {

                        }
                    }


            );
        }catch (Exception e){
            Log.e("ERROR:",e.getMessage(),e.getCause());
        }



    }


    @OnClick(R.id.cropInfo)
    public void setTvcropinfo() {
        Intent intent = new Intent(this, SelectCropActivity.class);
        intent.putExtra("fieldinfo_json",fieldinfo_json);
        intent.putExtra("fieldid",fieldInfo==null?fertilizingInfo.getFieldId():fieldInfo.getFieldId());
        this.startActivityForResult(intent,SelectCropActivity.REQUEST_CODE_SELECT_CROP_ACTIVITY);
    }


    FieldInfo fieldInfo = null;
    CropInfo cropInfo = null;
    FertilizingInfo fertilizingInfo = null;
    String fieldinfo_json;
    String fertilizingActivityId = null;
    String fieldname=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_farmthing_shifei);

        ButterKnife.bind(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));

        back.setTypeface(MyApplication.iconTypeFace);
        edit.setTypeface(MyApplication.iconTypeFace);

        fieldinfo_json = getIntent().getStringExtra("fieldinfo_json");
        fieldInfo = new Gson().fromJson(fieldinfo_json, FieldInfo.class);
        fertilizingActivityId = getIntent().getStringExtra("id");
        fieldname = getIntent().getStringExtra("fieldname");

        // 显示地块名称



        if (fertilizingActivityId != null && fertilizingActivityId.length()>0) {
            title.setText("编辑农事记录");
            if (fieldname != null) {
                tvfieldname.setText(fieldname+"  施肥");
            }

            // TODO: get sowing detail
            API.getFertilizingById(
                    Hawk.get(HawkKey.TOKEN),
                    fertilizingActivityId,
                    new ApiCallBack<FertilizingInfo>() {
                        @Override
                        public void onResponse(ResultObj<FertilizingInfo> resultObj) {
                            if (resultObj.getCode() == 0) {
                                FertilizingInfo info = resultObj.getObject();
                                fertilizingInfo = info;
                                etshifeitype.setText(info.getFertilizingType());
                                etfeiliaoname.setText(info.getFertilizingName());
                                etshifeistyle.setText(info.getFertilizingMethod());
                                tvstarttime.setText(info.getFertilizingStartTime());
                                tvendtime.setText(info.getFertilizingEndTime());
                                etfeipermu.setText(info.getFertilizingAcre());
                                etfeiall.setText(info.getTotalQuantity());
                                etpriceall.setText(info.getTotalCost());
                                beizhu.setText(info.getFertilizingNote());
                                pics.initdata(info.getImgUrl());
                                // 获取作物信息显示
                                API.getCropInfoById(
                                        Hawk.get(HawkKey.TOKEN), info.getCropId(),
                                        new ApiCallBack<CropInfo>() {
                                            @Override
                                            public void onResponse(ResultObj<CropInfo> resultObj) {
                                                if (resultObj.getCode() == 0) {
                                                    cropInfo = resultObj.getObject();
                                                    Log.e("cropinfo",new Gson().toJson(cropInfo));
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
            if (fieldInfo != null) {
                tvfieldname.setText(fieldInfo.getFieldName()+"  施肥");
            }
            title.setText("新建农事记录");
            tvcropinfo.setText("请选择作物");
            tvcropinfo.setTextColor(Color.parseColor("#FF0000"));
        }


        pics.setPicClickListener(new PicHorizentalList.PicOnclickEventListener() {
            @Override
            public void click(int index) {
                //TODO PIC
                String[] ss = pics.geturls().split(";");

                ImagePreview
                        .getInstance()
                        .setContext(EditFarmthingShifeiActivity.this)
                        .setIndex(index)
                        .setImageList(Arrays.asList(ss))
                        .start();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SelectCropActivity.REQUEST_CODE_SELECT_CROP_ACTIVITY) {
            //由选择作物页面返回
            if (resultCode == SelectCropActivity.RESULT_CODE_SELECT_CROP_ACTIVITY) {
                String cropjson =  data.getStringExtra("cropinfo_json");
                cropInfo = new Gson().fromJson(cropjson, CropInfo.class);

                if (cropInfo!=null){
                    tvcropinfo.setText(cropInfo.getCropName()+"  "+cropInfo.getPlantYear());
                }

            }
        }
    }
}
