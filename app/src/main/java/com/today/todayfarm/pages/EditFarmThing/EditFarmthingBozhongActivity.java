package com.today.todayfarm.pages.EditFarmThing;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.pages.selectcrop.SelectCropActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.ToastUtil;

import java.lang.reflect.Field;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditFarmthingBozhongActivity extends BaseActivity {

    private static final int REQUEST_CODE_SELECT_CROP_ACTIVITY = 1001;
    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.edit)
    TextView edit;

    @BindView(R.id.fieldname)
    TextView fieldname;

    @BindView(R.id.cropInfo)
    TextView tvcropinfo;

//    品种名称
    @BindView(R.id.croptypename)
    EditText croptypename;

//    亩播种量
    @BindView(R.id.seednumberperunit)
    EditText seednumberpernuit;

//    播种方法
    @BindView(R.id.bozhongtype)
    EditText bozhongtype;

//    行距
    @BindView(R.id.bozhonglinedividersize)
    EditText bozhonglinedividersize;

//    株距
    @BindView(R.id.bozhongunitdevidersize)
    EditText bozhongunitdevidersize;

//    保苗株数
    @BindView(R.id.unitseednumber)
    EditText unitseednumber;

//    车头
    @BindView(R.id.bozhongcarhead)
    EditText bozhongcarhead;

//    播种机械
    @BindView(R.id.bozhongmechine)
    EditText bozhongmechine;

//    播种时间
    @BindView(R.id.bozhongstarttime)
    TextView bozhongstarttime;

//    结束时间
    @BindView(R.id.bozhongendtime)
    TextView bozhongendtime;

//    播种深度
    @BindView(R.id.bozhongdeepth)
    EditText bozhongdeepth;

//    亩成本
    @BindView(R.id.pricepermu)
    EditText pricepermu;

//    总成本
    @BindView(R.id.priceall)
    EditText priceall;

//    备注
    @BindView(R.id.beizhu)
    EditText beizhu;


    @OnClick(R.id.selectstarttime)
    public void selectstarttime() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        bozhongstarttime.setText(i+"-"+i1+"-"+i2);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        ).show();

    }

    @OnClick(R.id.selectendtime)
    public void selectendtime() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        bozhongendtime.setText(i+"-"+i1+"-"+i2);
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
    public void edit() {

        if (cropInfo==null){
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

        // 保存编辑内容
        API.sowingSaveOrUpdate(
                Hawk.get(HawkKey.TOKEN),
                fieldInfo.getFieldId(),
                sowingActivityId,
                cropInfo.getCropId(),
                croptypename.getText().toString(),
                seednumberpernuit.getText().toString(),
                bozhongtype.getText().toString(),
                bozhonglinedividersize.getText().toString(),
                bozhongunitdevidersize.getText().toString(),
                unitseednumber.getText().toString(),
                bozhongcarhead.getText().toString(),
                bozhongmechine.getText().toString(),
                bozhongstarttime.getText().toString(),
                bozhongendtime.getText().toString(),
                bozhongdeepth.getText().toString(),
                pricepermu.getText().toString(),
                priceall.getText().toString(),
                beizhu.getText().toString(),
                new ApiCallBack<Object>() {
                    @Override
                    public void onResponse(ResultObj<Object> resultObj) {
                        if (resultObj.getCode() == 0) {
                            //保存成功
                            ToastUtil.show(EditFarmthingBozhongActivity.this,"保存成功");
                            EditFarmthingBozhongActivity.this.finish();
                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }


        );

//        API.sowingSaveOrUpdate(
//
//        );
    }

    @OnClick(R.id.cropInfo)
    public void selectcropinfo() {
        Intent intent = new Intent(this, SelectCropActivity.class);
        intent.putExtra("fieldinfo_json",fieldinfo_json);
        this.startActivityForResult(intent,REQUEST_CODE_SELECT_CROP_ACTIVITY);
    }



    FieldInfo fieldInfo = null;
    String  fieldinfo_json;
    CropInfo cropInfo = null;

    String sowingActivityId = null;//播种详情id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_farmthing_bozhong);
        ButterKnife.bind(this);

        back.setTypeface(MyApplication.iconTypeFace);
        edit.setTypeface(MyApplication.iconTypeFace);

        fieldinfo_json = getIntent().getStringExtra("fieldinfo_json");
        fieldInfo = new Gson().fromJson(fieldinfo_json, FieldInfo.class);
        sowingActivityId = getIntent().getStringExtra("sowingid");

        // 显示地块名称
        if (fieldInfo != null) {
            fieldname.setText(fieldInfo.getFieldName()+"  播种");
        }

        if (sowingActivityId != null && sowingActivityId.length()>0) {
            // TODO: get sowing detail
        } else {
            tvcropinfo.setText("请选择作物");
            tvcropinfo.setTextColor(Color.parseColor("#FF0000"));
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_CROP_ACTIVITY) {
            //由选择作物页面返回
            if (resultCode == SelectCropActivity.RESULT_CODE_SELECT_CROP_ACTIVITY) {
               String cropjson =  data.getStringExtra("cropinfo_json");
                cropInfo = new Gson().fromJson(cropjson, CropInfo.class);

                tvcropinfo.setText(cropInfo.getCropName()+"  "+cropInfo.getPlantYear());
            }
        }
    }
}
