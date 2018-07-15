package com.today.todayfarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.register)
    Button register;
    @BindView(R.id.login)
    Button login;


    @OnClick(R.id.register) void doRegister() {
        //open register page
        Intent intent = new Intent();
        intent.setClass(this,RegisterActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.login)
    void dologin() {
        // open login page
        Intent intent = new Intent();
        intent.setClass(this, MainMapActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }
}
