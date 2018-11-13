package com.today.todayfarm.pages.pagedetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.AddNewCrop.AddNewCropActivity;
import com.today.todayfarm.pages.CropList.CropListActivity;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.Common;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 农田详情
 */
public class FarmDetailActivity extends Activity {

    String fieldid = null;
    FieldInfo fieldInfo = null;

    @BindView(R.id.map)
    WebView map;

    @BindView(R.id.farmname)
    TextView farmname;

    @BindView(R.id.farmarea)
    TextView farmarea;

    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.edit)
    TextView edit;

    @BindView(R.id.nocroptip)
    TextView nocroptip;

    @BindView(R.id.cropdatapanel)
    LinearLayout cropdatapanel;

    @BindView(R.id.showallfarmcrop)
    TextView showallfarmcrop;


    @OnClick(R.id.back)
    public void back() {
        this.finish();
    }

    @OnClick(R.id.edit)
    public void edit() {
        // TODO
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        fieldid = intent.getStringExtra("fieldid");

        back.setTypeface(MyApplication.iconTypeFace);
        edit.setTypeface(MyApplication.iconTypeFace);


        initwebview();

        // 获取农田信息
        API.getFieldById(
                Hawk.get(HawkKey.TOKEN),
                fieldid,
                new ApiCallBack<FieldInfo>() {
                    @Override
                    public void onResponse(ResultObj<FieldInfo> resultObj) {
                        if (resultObj.getCode()==0){
                            fieldInfo = resultObj.getObject();
                            // 向地图 中加载农田边界

                            // 显示名称
                            farmname.setText(fieldInfo.getFieldName());

                            farmarea.setText(Common.getAreaStr(fieldInfo.getFieldArea())+"亩");



                            // 获取农作物信息
                            API.findCropInfosByFieldId(Hawk.get(HawkKey.TOKEN), fieldid, new ApiCallBack<CropInfo>() {
                                @Override
                                public void onResponse(ResultObj<CropInfo> resultObj) {
                                    if (resultObj.getCode() == 0) {
                                        // 显示作物信息
                                        cropdatapanel.setVisibility(View.VISIBLE);
                                        //

                                        // SHOW ALL FARM CROP
                                        showallfarmcrop.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(FarmDetailActivity.this, CropListActivity.class);
                                                intent.putExtra("listdata",new Gson().toJson(resultObj));
                                                FarmDetailActivity.this.startActivity(intent);
                                            }
                                        });
                                    }else {
                                        shownocroptip();
                                    }
                                }

                                @Override
                                public void onError(int code) {
                                    shownocroptip();
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }

    private void shownocroptip() {
        nocroptip.setVisibility(View.VISIBLE);
        cropdatapanel.setVisibility(View.GONE);
        nocroptip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 添加新作物
                Intent intent = new Intent(FarmDetailActivity.this, AddNewCropActivity.class);
                intent.putExtra("fieldid",fieldid);
                FarmDetailActivity.this.startActivity(intent);
            }
        });
    }

    private void initwebview() {
        WebSettings webSettings = map.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        map.loadUrl("file:///android_asset/index.html");
    }
}
