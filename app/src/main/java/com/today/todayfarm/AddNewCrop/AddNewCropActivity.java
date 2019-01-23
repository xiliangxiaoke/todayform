package com.today.todayfarm.AddNewCrop;

import android.app.Activity;
import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;
import com.today.todayfarm.R;

public class AddNewCropActivity extends Activity {

    String fieldid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_crop);

        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));
        fieldid = getIntent().getStringExtra("fieldid");




    }
}
