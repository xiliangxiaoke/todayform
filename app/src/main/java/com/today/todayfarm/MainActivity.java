package com.today.todayfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.dom.User;
import com.today.todayfarm.restapi.Doapi;
import com.today.todayfarm.restapi.MyApiService;
import com.today.todayfarm.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.register)
    Button register;
    @BindView(R.id.login)
    Button login;

    @BindView(R.id.inputUserName)
    EditText inputUserName;

    @BindView(R.id.inputPassWord)
    EditText inputPassWord;

    @OnClick(R.id.forget)
    void forget(){
        Intent intent = new Intent();
        intent.setClass(this,ForgetPasswordActivity.class);
        startActivity(intent);
    }



    @OnClick(R.id.register) void doRegister() {
        //open register page
        Intent intent = new Intent();
        intent.setClass(this,RegisterActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.login)
    void dologin() {
        // request login
        String username = inputUserName.getText().toString();
        String password = inputPassWord.getText().toString();
//        Log.d("dologin","username: "+username);
        if(username==null || "".equals(username)){
            ToastUtil.show(MainActivity.this,"请输入用户名！");
            return;
        }
        if(password==null || "".equals(password)){
            ToastUtil.show(MainActivity.this,"请输入密码！");
            return;
        }



        Call<ResultObj<User>> call =  Doapi.instance().login(username,password);
        call.enqueue(new Callback<ResultObj<User>>() {
            @Override
            public void onResponse(Call<ResultObj<User>> call, Response<ResultObj<User>> response) {
//                Log.d("call",""+response.body().data.getToken());
                if (response.isSuccessful()){
                    if (response.body().code==200){
                        MyApplication.token = response.body().data.getToken();
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, MainMapActivity.class);
                        startActivity(intent);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResultObj<User>> call, Throwable t) {
                ToastUtil.show(MainActivity.this,"登陆失败");
            }
        });

//        Intent intent = new Intent();
//        intent.setClass(this, MainMapActivity.class);
//        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }
}
