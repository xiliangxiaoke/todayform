package com.today.todayfarm;

import android.app.Activity;
import android.os.Bundle;

import android.widget.EditText;

import com.jaeger.library.StatusBarUtil;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.dom.FarmInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.restapi.Doapi;
import com.today.todayfarm.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddFarmActivity extends Activity {

    @BindView(R.id.farmname)
    EditText farmnameET;
    @BindView(R.id.farmaddress)
    EditText farmaddressET;

    @OnClick(R.id.addfarm)
    void addfarm(){
        String farmname = farmnameET.getText().toString();
        String farmaddress = farmaddressET.getText().toString();

        if (farmname==null || "".equals(farmname)){
            ToastUtil.show(this,"请输入农场名称");
            return;
        }
        if (farmaddress==null || "".equals(farmaddress)){
            ToastUtil.show(this,"请输入地址");
            return;
        }

        Call<ResultObj<Object>> call = Doapi.instance().addfarm(
                MyApplication.token,
                farmname,
                farmaddress
        );

        call.enqueue(new Callback<ResultObj<Object>>() {
            @Override
            public void onResponse(Call<ResultObj<Object>> call, Response<ResultObj<Object>> response) {
                if (response.isSuccessful()){
                    if (response.body().code==200){
                        ToastUtil.show(AddFarmActivity.this,"添加成功");

                        AddFarmActivity.this.finish();
                    }
                }else{
                    ToastUtil.show(AddFarmActivity.this,"添加失败");
                }
            }

            @Override
            public void onFailure(Call<ResultObj<Object>> call, Throwable t) {

                ToastUtil.show(AddFarmActivity.this,"添加失败");
            }
        });


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farm);
        ButterKnife.bind(this);

        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));
    }
}
