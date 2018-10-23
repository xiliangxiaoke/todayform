package com.today.todayfarm.pages.regist;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;

import com.today.todayfarm.R;
import com.today.todayfarm.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistActivity extends BaseActivity {

    @BindView(R.id.close)
    ImageView btnclose;

    @OnClick(R.id.close)
    void closepage(){
        this.finish();
    }

    @BindView(R.id.etphonenum)
    EditText etphone;

    @BindView(R.id.etcode) EditText etcode;


    /**
     * 获取手机验证码
     */
    @OnClick(R.id.tvgetcode)
    void getcode(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);

        //限制输入手机号
        etphone.setInputType(InputType.TYPE_CLASS_NUMBER);


    }
}
