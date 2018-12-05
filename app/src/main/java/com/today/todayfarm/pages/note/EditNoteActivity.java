package com.today.todayfarm.pages.note;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
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
import com.today.todayfarm.dom.JSParamInfo;
import com.today.todayfarm.dom.NoteInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.restapi.MyApiService;
import com.today.todayfarm.util.WebUtil;

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


    @OnClick(R.id.close)
    public void setClose() {
        this.finish();
    }

    @OnClick(R.id.save)
    public void setSave() {
        // TODO: 保存注记
    }

    NoteInfo noteInfo=null;

    String pagetype = null; //"add"添加注记 "edit"编辑注记

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        ButterKnife.bind(this);

        close.setTypeface(MyApplication.iconTypeFace);
        save.setTypeface(MyApplication.iconTypeFace);

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
            // set note date
            notetime.setText(noteInfo.getScoutingTime());

        }else{
            title.setText("添加注记");
        }

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
}
