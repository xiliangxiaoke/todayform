package com.today.todayfarm.pages.regist;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.PhoneCode;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.PhoneUtil;

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

    String phonecodestr = "";


    /**
     * 获取手机验证码
     */
    @OnClick(R.id.tvgetcode)
    void getcode(){
        //验证手机号
        String ph = etphone.getText().toString().trim();
        if(PhoneUtil.isMobileNO(ph)){
            //发送请求
            API.sendRandomCode(ph, new ApiCallBack<PhoneCode>() {
                @Override
                public void onResponse(ResultObj<PhoneCode> resultObj) {

                    // 返回的验证码要持久化一下，包括验证码的生成时间 ，保存页面状态
                    Log.v("getcode",resultObj.getObject().getCode());
                    phonecodestr = resultObj.getObject().getCode();
                    PhoneCode pc = resultObj.getObject();
                    pc.setCreateTimestamp(System.currentTimeMillis());
                    pc.setPhonenum(ph);
                    Hawk.put(HawkKey.PHONE_CODE_INFO,pc);

                }

                @Override
                public void onError(int code) {
                    Log.d("my","get code failed");
                }
            });
        }else {
            // 提示手机号不正确
            new SweetAlertDialog(this)
                    .setTitleText("请输入正确手机号")
                    .setConfirmText("我知道了")
                    .show();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);

        //限制输入手机号
        etphone.setInputType(InputType.TYPE_CLASS_NUMBER);

        // 恢复缓存
        PhoneCode pc =  Hawk.get(HawkKey.PHONE_CODE_INFO);
        if (pc !=null){
            //手机号
            if (pc.getPhonenum()!=null){
                etphone.setText(pc.getPhonenum());
            }
            // 检测验证码请求失效倒计时 60秒判断

        }


    }
}
