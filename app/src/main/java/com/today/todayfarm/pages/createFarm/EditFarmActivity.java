package com.today.todayfarm.pages.createFarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
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
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.BoundaryInfo2Js;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.JSParamInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.pages.AddFarmMap.EditFarmMapActivity;
import com.today.todayfarm.pages.tabs.DrawerTabActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.Common;
import com.today.todayfarm.util.WebUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditFarmActivity extends Activity {

    public static final int REQUESTCODE_EditFarmMapActivity = 1001;
    String geojson;
    String cropName = "";
    FieldInfo fieldInfo = null;

    @BindView(R.id.map)
    WebView map;

    @BindView(R.id.close )
    TextView tvclose;

    @BindView(R.id.save)
    TextView tvsave;

    @BindView(R.id.mapedit)
    TextView mapedit;

    @BindView(R.id.editfarmname)
    EditText editfarmname;

    @BindView(R.id.editfarmarea)
    EditText editfarmarea;
    private boolean firstloaded=false;
    public static int RESULTCODE_FarmDetailActivity = 4786;
    public static  int RESULTCODE_DELETE_FarmDetailActivity = 9987;


    @OnClick(R.id.mapedit)
    public void mapedit() {
        // todo 打开地图边界 编辑页面
        Intent intent = new Intent();
        intent.setClass(this,EditFarmMapActivity.class);
        intent.putExtra("fieldinfo_json",new Gson().toJson(fieldInfo));
        this.startActivityForResult(intent,REQUESTCODE_EditFarmMapActivity);
    }


    @OnClick(R.id.close)
    public void close(){
        this.finish();
    }

    @OnClick(R.id.save)
    public void save(){
        // 保存农田
        String farmname = editfarmname.getText().toString();
//        String cropname = editcropname.getText().toString();
        if (farmname==null){
            new SweetAlertDialog(this)
                    .setTitleText("农田名称为空")
                    .show();
            return;
        }
//        if (cropName == null) {
//            new SweetAlertDialog(this)
//                    .setTitleText("作物名称为空")
//                    .show();
//            return;
//        }

        fieldInfo.setFieldName(editfarmname.getText().toString());
        fieldInfo.setFieldArea(""+(Double.parseDouble(editfarmarea.getText().toString())*666.666));

        API.updateField(
                Hawk.get(HawkKey.TOKEN),
                fieldInfo.getFieldId(),
                editfarmname.getText().toString(),
                Double.parseDouble(editfarmarea.getText().toString())*666.666,
                fieldInfo.getFieldBoundary(),
                "",//cropName 要不要填
                new ApiCallBack<Object>() {
                    @Override
                    public void onResponse(ResultObj<Object> resultObj) {
                        if (resultObj.getCode() == 0) {
                            //保存成功
                            new SweetAlertDialog(EditFarmActivity.this)
                                    .setTitleText("保存成功")
                                    .showConfirmButton(true)
                                    .setConfirmText("好的")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                                            Intent intent = new Intent();
                                            intent.putExtra("fieldinfo_json",new Gson().toJson(fieldInfo));
                                            EditFarmActivity.this.setResult(RESULTCODE_FarmDetailActivity,intent);
                                            EditFarmActivity.this.finish();
//                                            Hawk.put(HawkKey.MAIN_PAGE_INDEX_TO_SHOW,1);
//                                            Intent intent = new Intent();
//                                            intent.setClass(EditFarmActivity.this, DrawerTabActivity.class);
//                                            EditFarmActivity.this.startActivity(intent);
                                        }
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );


    }

    @OnClick(R.id.delete)
    public void setdelete() {
        API.deleteFieldById(
                Hawk.get(HawkKey.TOKEN),
                fieldInfo.getFieldId(),
                new ApiCallBack<Object>() {
                    @Override
                    public void onResponse(ResultObj<Object> resultObj) {
                        new SweetAlertDialog(EditFarmActivity.this)
                                .setTitleText("删除成功")
                                .showConfirmButton(true)
                                .setConfirmText("好的")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        EditFarmActivity.this.setResult(RESULTCODE_DELETE_FarmDetailActivity);
                                        EditFarmActivity.this.finish();
                                    }
                                })
                                .show();
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_farm);

        ButterKnife.bind(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));

        tvclose.setTypeface(MyApplication.iconTypeFace);
        tvsave.setTypeface(MyApplication.iconTypeFace);
        mapedit.setTypeface(MyApplication.iconTypeFace);

        String fieldinfo_json = getIntent().getStringExtra("fieldinfo_json");
        try {
            fieldInfo = new Gson().fromJson(fieldinfo_json,FieldInfo.class);
        } catch (Exception e) {

        }

        // 显示名称和面积
        editfarmname.setText(fieldInfo.getFieldName());
        editfarmarea.setText(Common.getAreaStr(fieldInfo.getFieldArea()));

        loadmap();

    }

    private void loadmap() {
        WebSettings webSettings = map.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);


        map.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100 && firstloaded == false){
                    Log.v("webview","totalChartWebview 加载完成");
                    firstloaded = true;
                    showBoundary();
                }
            }
        });


        map.loadUrl("file:///android_asset/index.html");
    }



    private void showBoundary() {
        JSParamInfo<BoundaryInfo2Js> jsParamInfo = new JSParamInfo<>();
        jsParamInfo.setType("showgeo");
        BoundaryInfo2Js boundaryInfo2Js =null;
        //Log.v("boundary:",geojson);

        try{
            boundaryInfo2Js = new Gson().fromJson(fieldInfo.getFieldBoundary(),BoundaryInfo2Js.class);
        }catch (Exception e){
            Log.e("boundary err",e.getMessage());
        }

        jsParamInfo.setParams(boundaryInfo2Js);


        WebUtil.callJS(map,new Gson().toJson(jsParamInfo));


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE_EditFarmMapActivity
                && resultCode == EditFarmMapActivity.RESULTCODE_EditFarmMapActivity) {
            String geojson = data.getStringExtra("geojson");
            String area = data.getStringExtra("area");
            fieldInfo.setFieldArea(area);
            fieldInfo.setFieldBoundary(geojson);

            showBoundary();
            editfarmarea.setText(Common.getAreaStr(area));
        }
    }
}
