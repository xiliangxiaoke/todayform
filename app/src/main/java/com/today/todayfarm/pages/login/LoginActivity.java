package com.today.todayfarm.pages.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.PhoneCode;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.pages.tabs.DrawerTabActivity;
import com.today.todayfarm.pages.tabs.TabActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.PhoneUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.close)
    ImageView btnclose;

    @BindView(R.id.tvgetcode)
    TextView tvgetcode;

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
            doCount(60*1000);
        }else {
            // 提示手机号不正确
            new SweetAlertDialog(this)
                    .setTitleText("请输入正确手机号")
                    .setConfirmText("我知道了")
                    .show();
        }


    }

    @OnClick(R.id.btregist)
    public void login(){
        // 登陆
        String phone = etphone.getText().toString();
        String code = etcode.getText().toString();
        if(phone==null || phone.length()==0){
            new SweetAlertDialog(this)
                    .setTitleText("请输入正确手机号")
                    .setConfirmText("我知道了")
                    .show();
            return;
        }

        if (code == null || code.length()==0){
            new SweetAlertDialog(this)
                    .setTitleText("请输入验证码")
                    .setConfirmText("我知道了")
                    .show();
            return;
        }

        API.login(phone, code, new ApiCallBack<Object>() {
            @Override
            public void onResponse(ResultObj<Object> resultObj) {
                if (resultObj.getCode()==0){
                    // login success !!
                    //保存token
                    Hawk.put(HawkKey.TOKEN,resultObj.token);
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, DrawerTabActivity.class);
                    LoginActivity.this.startActivity(intent);
                }else {
                    new SweetAlertDialog(LoginActivity.this)
                            .setTitleText("登录失败")
                            .setContentText(resultObj.getMsg())
                            .show();
                }

            }

            @Override
            public void onError(int code) {
                new SweetAlertDialog(LoginActivity.this)
                        .setTitleText("登录失败")
                        .show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
            long now = System.currentTimeMillis();
            long past = pc.getCreateTimestamp();
            if(now-past<60*1000){
                //进行倒计时
                doCount(now-past);
            }

        }


    }

    /**
     * 倒计时
     * @param millisecond
     */
    private void doCount(long millisecond){
        CountDownTimer timer = new CountDownTimer(millisecond,1000) {
            @Override
            public void onTick(long l) {
                tvgetcode.setEnabled(false);
                tvgetcode.setText("请等待("+l/1000+")");
            }

            @Override
            public void onFinish() {
                tvgetcode.setEnabled(true);
                tvgetcode.setText("获取验证码");
            }
        }.start();
    }
}
