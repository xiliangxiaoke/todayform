package com.today.todayfarm.pages.CropList;

import android.app.Activity;
import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;
import com.today.todayfarm.R;
import com.today.todayfarm.dom.CropInfo;

import java.util.ArrayList;
import java.util.List;

public class CropListActivity extends Activity {

    String data;

    List<CropInfo> listdata = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_list);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));

        data = getIntent().getStringExtra("listdata");
//        ResultObj<CropInfo> resultObj = new Gson().fromJson(data,ResultObj<CropInfo>.)
    }
}
