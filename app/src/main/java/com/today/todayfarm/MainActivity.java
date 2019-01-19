package com.today.todayfarm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.dom.User;
import com.today.todayfarm.pages.login.LoginActivity;
import com.today.todayfarm.pages.main.MainPageGalleryPagerAdapter;
import com.today.todayfarm.pages.main.ext.ScaleCircleNavigator;
import com.today.todayfarm.pages.regist.RegistActivity;
import com.today.todayfarm.pages.tabs.DrawerTabActivity;
import com.today.todayfarm.pages.tabs.TabActivity;
import com.today.todayfarm.restapi.Doapi;
import com.today.todayfarm.restapi.MyApiService;
import com.today.todayfarm.util.ToastUtil;
import com.today.todayfarm.util.md5util;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {

    @BindView(R.id.register)
    Button register;
    @BindView(R.id.login)
    Button login;


    @BindView(R.id.bannerViewPager)
    ViewPager mViewPager;
    @BindView(R.id.magic_indicator)
    MagicIndicator indicator;

    Integer[] ress = new Integer[]{
            R.mipmap.splash1,
            R.mipmap.splash2,
            R.mipmap.splash3,
            R.mipmap.splash4
    };

    private List<Integer> datalist = Arrays.asList(ress);


    private MainPageGalleryPagerAdapter mPagerAdapter = new MainPageGalleryPagerAdapter(datalist);



    @OnClick(R.id.register) void doRegister() {
        //open register page

        Intent intent = new Intent();
        intent.setClass(this,RegistActivity.class);
        startActivity(intent);
        this.finish();

    }

    @OnClick(R.id.login)
    void dologin()  {

        Intent intent = new Intent();
        intent.setClass(this,LoginActivity.class);
        startActivity(intent);
        this.finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //先看看有没有登陆token，有就直接进入主界面
        String token = Hawk.get(HawkKey.TOKEN);
        if (token !=null && token.length()>0){
            Intent intent = new Intent();
            intent.setClass(this, DrawerTabActivity.class);
            this.startActivity(intent);
            this.finish();
        }



        mViewPager.setAdapter(mPagerAdapter);

        initMagicIndicator();




    }

    private void initMagicIndicator() {
        ScaleCircleNavigator circleNavigator = new ScaleCircleNavigator(this);
        circleNavigator.setCircleCount(ress.length);
        circleNavigator.setNormalCircleColor(Color.LTGRAY);
        circleNavigator.setSelectedCircleColor(Color.DKGRAY);
        circleNavigator.setCircleClickListener(new ScaleCircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                mViewPager.setCurrentItem(index);
            }
        });
        indicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(indicator,mViewPager);
    }
}
