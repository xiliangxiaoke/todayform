package com.today.todayfarm.pages.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.customView.PicHorizentalList;
import com.today.todayfarm.dom.BoundaryInfo2Js;
import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.GeoPoint;
import com.today.todayfarm.dom.JS2AndroidParam;
import com.today.todayfarm.dom.JSParamInfo;
import com.today.todayfarm.dom.NoteInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.pages.pagedetail.FarmDetailActivity;
import com.today.todayfarm.pages.selectcrop.SelectCropActivity;
import com.today.todayfarm.pages.selectfarm.SelectFarmActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.restapi.MyApiService;
import com.today.todayfarm.util.Android2JS;
import com.today.todayfarm.util.Common;
import com.today.todayfarm.util.ToastUtil;
import com.today.todayfarm.util.WebUtil;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditNoteActivity extends BaseActivity {

    @BindView(R.id.close)
    TextView close;

    @BindView(R.id.save)
    TextView save;

    @BindView(R.id.title)TextView title;
    @BindView(R.id.notename)EditText notename;
    @BindView(R.id.picsview)PicHorizentalList picslist;
    @BindView(R.id.fieldname)TextView fieldname;
    @BindView(R.id.cropname)TextView cropname;
    @BindView(R.id.notetime)TextView notetime;
    @BindView(R.id.webview)WebView webView;
    public static int REQUEST_CODE = 1007;
    public static int REQUEST_CODE_CROP = 1008;

    @OnClick(R.id.cropname)
    public void selectcrop() {
        Intent intent = new Intent();
        intent.setClass(this, SelectCropActivity.class);
        intent.putExtra("from","EditNoteActivity");
        this.startActivityForResult(intent,REQUEST_CODE_CROP);
    }
    @OnClick(R.id.fieldname)
    public void selectfield() {
        // 打开选择地块 页面 选完后返回地块信息
        Intent intent = new Intent();
        intent.setClass(this, SelectFarmActivity.class);
        intent.putExtra("from","EditNoteActivity");
        this.startActivityForResult(intent,REQUEST_CODE);
    }

    @OnClick(R.id.close)
    public void setClose() {
        this.finish();
    }

    @OnClick(R.id.save)
    public void setSave() {
        // TODO: 保存注记
        if ("edit".equals(pagetype)) {
            //  edit
            API.updateScoutingNote(
                    Hawk.get(HawkKey.TOKEN),
                    noteInfo.getScoutingNoteId(),
                    noteInfo.getFieldId(),
                    noteInfo.getCropId(),
                    notename.getText().toString(),
                    Common.getSimpleDateFormatTime("yyyy-MM-dd", calendar),
                    new Gson().toJson(noteInfo.getScoutingPosition()),
                    picslist.geturls(),
                    new ApiCallBack<Object>() {
                        @Override
                        public void onResponse(ResultObj<Object> resultObj) {
                            // 保存成功
                            new SweetAlertDialog(EditNoteActivity.this)
                                    .setTitleText("保存成功")
                                    .show();
                        }

                        @Override
                        public void onError(int code) {

                        }
                    }

            );
        } else {
            // add

            // 先判断地块和农作物是否设置过
            if (fieldid == null) {
                ToastUtil.show(this,"请选择地块");
                return;
            }

            if (cropid == null) {
                ToastUtil.show(this,"请选择作物");
                return;
            }

            API.addScoutingNote(
                    Hawk.get(HawkKey.TOKEN),
                    fieldid,
                    cropid,
                    notename.getText().toString(),
                    Common.getSimpleDateFormatTime("yyyy-MM-dd", calendar),
                    position!=null?new Gson().toJson(position):null,
                    picslist.geturls(),
                    new ApiCallBack<Object>() {
                        @Override
                        public void onResponse(ResultObj<Object> resultObj) {
                            // 保存成功
                            new SweetAlertDialog(EditNoteActivity.this)
                                    .setTitleText("添加成功")
                                    .show();
                        }

                        @Override
                        public void onError(int code) {

                        }
                    }

            );
        }

    }

    NoteInfo noteInfo=null;

    String pagetype = null; //"add"添加注记 "edit"编辑注记
    Calendar calendar = Calendar.getInstance();
    GeoPoint position = null;
    String fieldid = null;
    String cropid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));


        initwebview();

        close.setTypeface(MyApplication.iconTypeFace);
        save.setTypeface(MyApplication.iconTypeFace);

        fieldid = getIntent().getStringExtra("fieldid");
        cropid = getIntent().getStringExtra("cropid");
        String noteinfostr = getIntent().getStringExtra("noteinfo_json");
        pagetype = getIntent().getStringExtra("pagetype");
        try {
            noteInfo = new Gson().fromJson(noteinfostr, NoteInfo.class);
        } catch (Exception e) {

        }

        // 设置信息
        if ("edit".equals(pagetype)) {
            title.setText("编辑注记");





            notename.setText(noteInfo.getScoutingNoteInfo());
            picslist.initdata(noteInfo.getPhotos());
            // set fieldname and webview info
            setfieldnameAndWebviewinfo();
            // set cropname
            setcropname();
            // set note date yyyy-MM-dd
            String[] ymd = noteInfo.getScoutingTime().split("-");
            if (ymd.length > 2) {
                calendar.set(
                        Integer.parseInt(ymd[0]),
                        Integer.parseInt(ymd[1]),
                        Integer.parseInt(ymd[2])
                );
            }
            notetime.setText(
                    calendar.get(Calendar.YEAR)+"年"
                    +(calendar.get(Calendar.MONTH)+1)+"月"
                    +calendar.get(Calendar.DATE)+"日"
            );



        }else{
            title.setText("添加注记");
            calendar.setTime(new Date());
        }

    }

    private void initwebview() {
        WebSettings webSettings1 = webView.getSettings();
        webSettings1.setJavaScriptEnabled(true);
        webSettings1.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new Android2JS(new Android2JS.CallBack() {
            @Override
            public void callback(String json) {
                if (json!=null){
                    JS2AndroidParam js2AndroidParam = new Gson().fromJson(json,JS2AndroidParam.class);
                    if (js2AndroidParam!=null){
                        // do something
                        if ("returnpoint".equals(js2AndroidParam.getType())) {
                            String gpjson = js2AndroidParam.getValue();
                            GeoPoint gp = null;
                            try {
                                gp = new Gson().fromJson(gpjson,GeoPoint.class);
                            } catch (Exception e) {

                            }
                            position = gp;
                        }
                    }
                }
            }
        }),"androidjs");
    }

    private void setcropname() {
        API.getCropInfoById(
                Hawk.get(HawkKey.TOKEN),
                noteInfo.getCropId(),
                new ApiCallBack<CropInfo>() {
                    @Override
                    public void onResponse(ResultObj<CropInfo> resultObj) {
                        if (resultObj.getCode() == 0) {
                            CropInfo cropInfo = resultObj.getObject();
                            if (cropInfo != null) {
                                cropname.setText(cropInfo.getCropName());
                            }
                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }

    // 设置农田名称
    private void setfieldnameAndWebviewinfo() {
        API.getFieldById(
                Hawk.get(HawkKey.TOKEN),
                noteInfo.getFieldId(),
                new ApiCallBack<FieldInfo>() {
                    @Override
                    public void onResponse(ResultObj<FieldInfo> resultObj) {
                        if (resultObj.getCode() == 0) {
                            FieldInfo fieldInfo = resultObj.getObject();
                            if (fieldInfo != null) {
                                // 设置农田名称
                                fieldname.setText(fieldInfo.getFieldName());
                                // TODO 设置webview中的农田边界和注记位置
                                showfieldboundary(fieldInfo.getFieldBoundary());


                            }
                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }

    private void showNoteLocation(GeoPoint scoutingPosition) {
        JSParamInfo<GeoPoint> jsParamInfo = new JSParamInfo<>();
        jsParamInfo.setType("notelocation");
        jsParamInfo.setParams(scoutingPosition);
        WebUtil.callJS(webView,new Gson().toJson(jsParamInfo));
    }

    private void showfieldboundary(String boundary) {
        JSParamInfo<BoundaryInfo2Js> jsParamInfo = new JSParamInfo<>();
        jsParamInfo.setType("showgeo");
        BoundaryInfo2Js boundaryInfo2Js =null;
        Log.v("boundary:",boundary);

        try{
            boundaryInfo2Js = new Gson().fromJson(boundary,BoundaryInfo2Js.class);
        }catch (Exception e){
            Log.e("boundary err",e.getMessage());
        }

        jsParamInfo.setParams(boundaryInfo2Js);



        //Log.v("jsParamInfo:",new Gson().toJson(jsParamInfo));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 */
                WebUtil.callJS(webView,new Gson().toJson(jsParamInfo));
                showNoteLocation(noteInfo.getScoutingPosition());
            }
        }, 1000);//3秒后执行Runnable中的run方法
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            String json = data.getStringExtra("data");
            FieldInfo fieldInfo = null;
            try {
                fieldInfo = new Gson().fromJson(json,FieldInfo.class);
            } catch (Exception e) {

            }
            if (fieldInfo != null) {
                // 显示地块名称 显示地图中的地块边界
                fieldid = fieldInfo.getFieldId();
                fieldname.setText(fieldInfo.getFieldName());
                showfieldboundary(fieldInfo.getFieldBoundary());

            }
        } else if (requestCode == REQUEST_CODE_CROP) {
            String cropjson =  data.getStringExtra("cropinfo_json");
            CropInfo cropInfo = null;
            try {
                cropInfo = new Gson().fromJson(cropjson,CropInfo.class);
            } catch (Exception e) {

            }
            cropid = cropInfo.getCropId();
            cropname.setText(cropInfo.getCropName());
        }
    }




}
