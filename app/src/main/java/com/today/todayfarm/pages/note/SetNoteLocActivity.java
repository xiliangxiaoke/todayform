package com.today.todayfarm.pages.note;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.dom.BoundaryAndNote;
import com.today.todayfarm.dom.BoundaryInfo2Js;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.GeoPoint;
import com.today.todayfarm.dom.JS2AndroidParam;
import com.today.todayfarm.dom.JSParamInfo;
import com.today.todayfarm.dom.NoteInfo;
import com.today.todayfarm.util.Android2JS;
import com.today.todayfarm.util.ToastUtil;
import com.today.todayfarm.util.WebUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetNoteLocActivity extends BaseActivity {


    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.back)
    TextView back;


    private boolean firstloaded = false;
    public static int RESULTCODE_SetNoteLocActivity = 9008;


    @OnClick(R.id.back)
    public void back() {
        this.finish();
    }

    @OnClick(R.id.remove)
    public void remove() {
        JSParamInfo<Object> jsParamInfo = new JSParamInfo<>();
        jsParamInfo.setType("clearnoteloc");

        WebUtil.callJS(webView,new Gson().toJson(jsParamInfo));
        newNoteloc = null;
    }

    @OnClick(R.id.save)
    public void save() {
        if (newNoteloc != null) {
            Intent intent = new Intent();
            intent.putExtra("geopoint_json",new Gson().toJson(newNoteloc));
            this.setResult(RESULTCODE_SetNoteLocActivity,intent);
            this.finish();
        }else{
            ToastUtil.show(this,"请标记位置");
        }

    }


    NoteInfo noteInfo = null;
    GeoPoint newNoteloc = null;
    FieldInfo fieldInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_note_loc);

        ButterKnife.bind(this);

        back.setTypeface(MyApplication.iconTypeFace);

        String noteinfo_json = getIntent().getStringExtra("noteinfo_json");


        if (noteinfo_json != null && noteinfo_json.length() > 0) {
            noteInfo = new Gson().fromJson(noteinfo_json,NoteInfo.class);
        }

        String fieldinfo_json = getIntent().getStringExtra("fieldinfo_json");
        if (fieldinfo_json != null && fieldinfo_json.length() > 0) {
            fieldInfo = new Gson().fromJson(fieldinfo_json,FieldInfo.class);
        }

        String position_json = getIntent().getStringExtra("position_json");
        if (position_json != null && position_json.length() > 0) {
            newNoteloc = new Gson().fromJson(position_json,GeoPoint.class);
        }



        initwebview();
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

                            try {
                                newNoteloc = new Gson().fromJson(gpjson,GeoPoint.class);
                            } catch (Exception e) {

                            }

                        }
                    }
                }
            }
        }),"androidjs");

        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100 && firstloaded == false){
//                    Log.v("webview","totalChartWebview 加载完成");
                    firstloaded = true;
                    showBoundaryAndNote();
//                    if (fieldInfo != null) {
//                        showboundary();
//                    }
//                    if (noteInfo != null) {
//                        showpoint();
//                    }

                    startloc();

                }
            }
        });

        webView.loadUrl("file:///android_asset/index.html");
    }

    private void showBoundaryAndNote() {
        JSParamInfo<BoundaryAndNote> jsParamInfo = new JSParamInfo<>();
        jsParamInfo.setType("showBoundaryAndNote");
        BoundaryAndNote boundaryAndNote =new BoundaryAndNote();
        if (fieldInfo != null) {
            boundaryAndNote.setBoubdary(new Gson().fromJson(fieldInfo.getFieldBoundary(),BoundaryInfo2Js.class));
        }
        if (noteInfo != null) {
            boundaryAndNote.setNote(new Gson().fromJson(noteInfo.getScoutingPosition(),GeoPoint.class));
        } else if (newNoteloc != null) {
            boundaryAndNote.setNote(newNoteloc);
        }

        jsParamInfo.setParams(boundaryAndNote);
        WebUtil.callJS(webView,new Gson().toJson(jsParamInfo));
    }

    private void showboundary() {
        JSParamInfo<BoundaryInfo2Js> jsParamInfo = new JSParamInfo<>();
        jsParamInfo.setType("showgeo");
        BoundaryInfo2Js boundaryInfo2Js =null;
        Log.v("boundary:",fieldInfo.getFieldBoundary());

        try{
            boundaryInfo2Js = new Gson().fromJson(fieldInfo.getFieldBoundary(),BoundaryInfo2Js.class);
        }catch (Exception e){
            Log.e("boundary err",e.getMessage());
        }

        jsParamInfo.setParams(boundaryInfo2Js);
        WebUtil.callJS(webView,new Gson().toJson(jsParamInfo));
    }

    private void startloc() {
        JSParamInfo<Object> jsParamInfo = new JSParamInfo<>();
        jsParamInfo.setType("setnoteloc");

        WebUtil.callJS(webView,new Gson().toJson(jsParamInfo));
    }

    private void showpoint() {
        JSParamInfo<GeoPoint> jsParamInfo = new JSParamInfo<>();
        jsParamInfo.setType("notelocation");




        jsParamInfo.setParams(new Gson().fromJson(noteInfo.getScoutingPosition(),GeoPoint.class) );
        WebUtil.callJS(webView,new Gson().toJson(jsParamInfo));
    }
}
