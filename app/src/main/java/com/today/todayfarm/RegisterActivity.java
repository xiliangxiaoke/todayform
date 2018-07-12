package com.today.todayfarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @OnClick(R.id.back) void back() {
        //返回上一页
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }
}
