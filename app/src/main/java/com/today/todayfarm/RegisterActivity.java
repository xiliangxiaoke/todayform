package com.today.todayfarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.EditText;

import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.dom.User;
import com.today.todayfarm.restapi.Doapi;
import com.today.todayfarm.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @OnClick(R.id.back) void back() {
        //返回上一页
        this.finish();
    }

    @BindView(R.id.inputUserName)
    EditText inputUserName;

    @BindView(R.id.inputPassWord)
    EditText inputPassWord;
    @BindView(R.id.inputPassWordAgain)
    EditText inputPassWordAgain;

    @BindView(R.id.inputkey)
    EditText inputkey;

    @OnClick(R.id.register)
    void register(){
        String username = inputUserName.getText().toString();
        String pw = inputPassWord.getText().toString();
        String pwagain = inputPassWordAgain.getText().toString();
        String key = inputkey.getText().toString();
        if (username==null || "".equals(username)){
            ToastUtil.show(this,"请输入用户名！");
        }
        if (pw==null || "".equals(pw)){
            ToastUtil.show(this,"请输入密码！");
        }
        if (pwagain==null || "".equals(pwagain)){
            ToastUtil.show(this,"请再次输入密码！");
        }
        if (! pw.equals(pwagain)){
            ToastUtil.show(this,"两次输入的密码不一致！");
        }
        if (key==null || "".equals(key)){
            ToastUtil.show(this,"请输入key！");
        }

        Call<ResultObj<User>> call =  Doapi.instance().register(username,pw,key);
        call.enqueue(new Callback<ResultObj<User>>() {
            @Override
            public void onResponse(Call<ResultObj<User>> call, Response<ResultObj<User>> response) {
//                Log.d("call",""+response.body().data.getToken());
                if (response.isSuccessful()){
                    if (response.body().code==200){
                        MyApplication.token = response.body().data.getToken();
                        Log.d("calleee",""+response.body().data.getToken());
                        RegisterActivity.this.finish();
                        Intent intent = new Intent();
                        intent.setClass(RegisterActivity.this, MainMapActivity.class);
                        startActivity(intent);
                    }else {
                        ToastUtil.show(RegisterActivity.this,"注册失败");
                    }
                }

            }

            @Override
            public void onFailure(Call<ResultObj<User>> call, Throwable t) {
                ToastUtil.show(RegisterActivity.this,"注册失败");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }
}
