package com.today.todayfarm;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.jaeger.library.StatusBarUtil;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.restapi.Doapi;
import com.today.todayfarm.util.ToastUtil;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.BaseResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditFieldinfoActivity extends Activity {

    public static FieldInfo fieldInfo = null;
    String picpath = "";
    String type = "1";

    @BindView(R.id.left)
    EditText left;

    @BindView(R.id.bottom)
    EditText bottom;
    @BindView(R.id.right)
    EditText right;
    @BindView(R.id.top)
    EditText top;

    @OnClick(R.id.uptmapimg)
    void uptmapimginfo(){
        Log.d("SHANGCHUAN:","uptmapimginfo");
        final File file = new File(picpath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        //上传图片
//        Call<ResultObj<Object>> call = Doapi.instance().uploadFieldSateliteImg(
//                MyApplication.token,
//                requestBody,
//                "desctiption",
//                fieldInfo.getFieldid()+"",
//                type,
//                new Date().getTime()+"",
//                left.getText().toString(),
//                bottom.getText().toString(),
//                right.getText().toString(),
//                top.getText().toString()
//
//
//
//        );
//
//
//        call.enqueue(new Callback<ResultObj<Object>>() {
//            @Override
//            public void onResponse(Call<ResultObj<Object>> call, Response<ResultObj<Object>> response) {
//                if (response.isSuccessful()){
//                    if (response.body().getCode()==200){
//                        ToastUtil.show(EditFieldinfoActivity.this,"上传成功");
//                    }else{
//                        Log.d("SHANGCHUAN:","fault:"+response.body().getCode());
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResultObj<Object>> call, Throwable t) {
//
//                Log.d("SHANGCHUAN:","fault:"+t.toString());
//            }
//        });
    }

    @OnClick(R.id.addgrowthdata)
    void addgrowthdata(){

    }

    @OnClick(R.id.addhumiddata)
    void addhumiddata(){

    }

    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;

    @BindView(R.id.selectpic)
    ImageView selectpic;

    @OnClick(R.id.selectpic)
    void setSelectpic(){
//        RxGalleryFinalApi instance = RxGalleryFinalApi.getInstance(this);
//        //打开单选图片，默认参数
//        instance
//
//                .setImageRadioResultEvent(new RxBusResultDisposable<ImageRadioResultEvent>() {
//                    @Override
//                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
//                        Log.d("sdf","sdf"+imageRadioResultEvent.getResult().getOriginalPath());
//                    }
//                }).open();
        //选择图片上传
        RxGalleryFinal
                .with(this)
                .image()
                .imageLoader(ImageLoaderType.FRESCO)
                .radio()
                .subscribe(new RxBusResultDisposable<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent baseResultEvent) throws Exception {
                        Log.d("sdf","sdf"+baseResultEvent.getResult().getOriginalPath());
                        picpath = baseResultEvent.getResult().getOriginalPath();
                    }
                })
                .openGallery();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fieldinfo);
        ButterKnife.bind(this);

        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.radiogrowth:
                        break;
                    case R.id.radiohumid:
                        break;
                }
            }
        });
    }
}
