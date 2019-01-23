package com.today.todayfarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends AppCompatActivity {

    @OnClick(R.id.back)void back(){
        this.finish();
    }

    @OnClick(R.id.call)void call(){
        // call phone
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));
    }
}
