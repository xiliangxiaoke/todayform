package com.today.todayfarm.CropList;

import android.app.Activity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.today.todayfarm.R;
import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.ResultObj;

import java.util.ArrayList;
import java.util.List;

public class CropListActivity extends Activity {

    String data;

    List<CropInfo> listdata = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_list);

        data = getIntent().getStringExtra("listdata");
//        ResultObj<CropInfo> resultObj = new Gson().fromJson(data,ResultObj<CropInfo>.)
    }
}
