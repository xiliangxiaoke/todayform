package com.today.todayfarm.pages.pagedetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.constValue.HawkKey;
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
                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }

    private void initwebview() {
        WebSettings webSettings = map.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        map.loadUrl("file:///android_asset/index.html");
    }
}
