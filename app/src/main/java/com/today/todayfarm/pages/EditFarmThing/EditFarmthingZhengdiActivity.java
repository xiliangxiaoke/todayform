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
import com.today.todayfarm.dom.TillingInfo;
import com.today.todayfarm.pages.selectcrop.SelectCropActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.ToastUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditFarmthingZhengdiActivity extends Activity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;


    @BindView(R.id.edit)
    TextView edit;
    @BindView(R.id.fieldname) TextView tvfieldname;
    @BindView(R.id.cropInfo) TextView tvcropinfo;
    @BindView(R.id.zhengditype)
    EditText zhengditype;
    @BindView(R.id.carhead) EditText carhead;
    @BindView(R.id.mechine) EditText mechine;
    @BindView(R.id.starttime) TextView tvstarttime;
    @BindView(R.id.selectstarttime) TextView selectstarttime;
    @BindView(R.id.endtime) TextView tvendtime;
    @BindView(R.id.selectendtime) TextView selectendtime;
    @BindView(R.id.deepth) EditText deepth;
    @BindView(R.id.pricepermu) EditText pricepermu;
    @BindView(R.id.priceall) EditText priceall;
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
                tillingActivityId,
                new ApiCallBack<Object>() {
                    @Override
                    public void onResponse(ResultObj<Object> resultObj) {
                        //删除成功
                        ToastUtil.show(EditFarmthingZhengdiActivity.this,"删除成功");
                        EditFarmthingZhengdiActivity.this.finish();
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

//        if (fieldInfo==null){
//            new SweetAlertDialog(this)
//                    .setTitleText("缺少农田信息")
//                    .show();
//            return;
//        }


        API.tillingSaveOrUpdate(
                Hawk.get(HawkKey.TOKEN),
                fieldInfo==null? tillingInfo.getFieldId():fieldInfo.getFieldId(),
                cropInfo.getCropId(),
                tillingActivityId,
                fieldInfo==null? tillingInfo.getUserId():fieldInfo.getUserId(),// TODO: USERID
                zhengditype.getText().toString(),
                carhead.getText().toString(),
                mechine.getText().toString(),
                tvstarttime.getText().toString(),
                tvendtime.getText().toString(),
                deepth.getText().toString(),
                pricepermu.getText().toString(),
                priceall.getText().toString(),
                beizhu.getText().toString(),
                pics.geturls(),// todo: img list
                        new ApiCallBack<Object>() {
            @Override
            public void onResponse(ResultObj<Object> resultObj) {
                if (resultObj.getCode() == 0) {
                    //保存成功
                    ToastUtil.show(EditFarmthingZhengdiActivity.this,"保存成功");
                    EditFarmthingZhengdiActivity.this.finish();
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
        intent.putExtra("fieldid",fieldInfo==null? tillingInfo.getFieldId():fieldInfo.getFieldId());
        this.startActivityForResult(intent,SelectCropActivity.REQUEST_CODE_SELECT_CROP_ACTIVITY);
    }


    FieldInfo fieldInfo = null;
    CropInfo cropInfo = null;
    TillingInfo tillingInfo = null;
    String fieldinfo_json;
    String tillingActivityId = null;
    String fieldname = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_farmthing_zhengdi);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));

        ButterKnife.bind(this);

        back.setTypeface(MyApplication.iconTypeFace);
        edit.setTypeface(MyApplication.iconTypeFace);

        fieldinfo_json = getIntent().getStringExtra("fieldinfo_json");
        fieldInfo = new Gson().fromJson(fieldinfo_json, FieldInfo.class);
        tillingActivityId = getIntent().getStringExtra("id");
        fieldname = getIntent().getStringExtra("fieldname");

        // 显示地块名称




        if (tillingActivityId != null && tillingActivityId.length()>0) {


            title.setText("编辑农事记录");
            if (fieldname != null) {
                tvfieldname.setText(fieldname+"  整地");
            }
            // TODO: get sowing detail
            API.getTillingById(
                    Hawk.get(HawkKey.TOKEN),
                    tillingActivityId,
                    new ApiCallBack<TillingInfo>() {
                        @Override
                        public void onResponse(ResultObj<TillingInfo> resultObj) {
                            if (resultObj.getCode() == 0) {
                                TillingInfo info = resultObj.getObject();
                                tillingInfo = info;
                                zhengditype.setText(info.getTillingType());
                                carhead.setText(info.getTillingTractor());
                                mechine.setText(info.getTillingMechanical());
                                tvstarttime.setText(info.getTillingStartTime());
                                tvendtime.setText(info.getTillingEndTime());
                                deepth.setText(info.getTillingDepth());
                                pricepermu.setText(info.getPricePerAcre());
                                priceall.setText(info.getTotalCost());
                                beizhu.setText(info.getTillingNote());
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
            title.setText("新建农事记录");
            tvcropinfo.setText("请选择作物");
            tvcropinfo.setTextColor(Color.parseColor("#FF0000"));

            if (fieldInfo != null) {
                tvfieldname.setText(fieldInfo.getFieldName()+"  整地");
            }
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


                if (cropInfo!=null){
                    tvcropinfo.setText(cropInfo.getCropName()+"  "+cropInfo.getPlantYear());
                }

            }
        }
    }
}
