package com.today.todayfarm.pages.tabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.Eventbus.MessageEvent;
import com.today.todayfarm.R;
import com.today.todayfarm.WeatherSearchActivity;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
//import com.today.todayfarm.pages.menu.MenuActivity;
import com.today.todayfarm.dom.AppVersionInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.pages.tabs.fragments.FarmlandFragment;
import com.today.todayfarm.pages.tabs.fragments.FarmworkFragment;
import com.today.todayfarm.pages.tabs.fragments.MapFragment;
import com.today.todayfarm.pages.tabs.fragments.SettingFragment;
import com.today.todayfarm.pages.tabs.fragments.SuggestFragment;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.ToastUtil;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主功能页面，底部菜单切换
 */
public class TabActivity extends BaseActivity {

    private static final String[] CHANNELS = new String[]{"首页","农田","农艺","农事","设置"};
    private List<String> mdatalist = Arrays.asList(CHANNELS);
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper();


    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));
        ButterKnife.bind(this);

        initfragments();
        initmagicindicator();

        fragmentContainerHelper.handlePageSelected(0,false);
        switchPages(0);



        // 延迟请求获取版本信息
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                API.getAppNewVersion(new ApiCallBack<AppVersionInfo>() {
                    @Override
                    public void onResponse(ResultObj<AppVersionInfo> resultObj) {
                        ToastUtil.show(TabActivity.this,"获取版本："+resultObj.getObject().getVersionName());
                    }

                    @Override
                    public void onError(int code) {

                    }
                });
            }
        }, 1000);



    }


    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event){
        if ("openMenuActivity".equals(event.type)){
            //打开侧边菜单栏页面
//            try{
//                Intent intent = new Intent();
//                intent.setClass(this, MenuActivity.class);
//                this.startActivity(intent);
//                this.overridePendingTransition(0,0);
//                //overridePendingTransition(R.anim.in_from_left,R.anim.out_to_left);
//            }catch (Exception e){
//                Log.e("ERROR",e.getMessage());
//            }

        } else if ("menu_mainpage".equals(event.type)) {
            fragmentContainerHelper.handlePageSelected(0,false);
            switchPages(0);
        } else if ("menu_field".equals(event.type)) {
            fragmentContainerHelper.handlePageSelected(1,false);
            switchPages(1);
        } else if ("menu_suggest".equals(event.type)) {
            fragmentContainerHelper.handlePageSelected(2,false);
            switchPages(2);
        } else if ("menu_farmthing".equals(event.type)) {
            fragmentContainerHelper.handlePageSelected(3,false);
            switchPages(3);
        } else if ("menu_setting".equals(event.type)) {
            fragmentContainerHelper.handlePageSelected(4,false);
            switchPages(4);
        } else if("menu_weather".equals(event.type)){
            Intent intent = new Intent();
            intent.setClass(this,WeatherSearchActivity.class);
            intent.putExtra("city",""+Hawk.get(HawkKey.CITY));
            startActivity(intent);
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
        if (Hawk.get(HawkKey.MAIN_PAGE_INDEX_TO_SHOW)!=null){
            int index = Hawk.get(HawkKey.MAIN_PAGE_INDEX_TO_SHOW);
            fragmentContainerHelper.handlePageSelected(index,false);
            switchPages(index);
            Hawk.delete(HawkKey.MAIN_PAGE_INDEX_TO_SHOW);
        }

    }

    private void switchPages(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment;
        for (int i = 0, j = fragments.size(); i < j; i++) {
            if (i == index) {
                continue;
            }
            fragment = fragments.get(i);
            if (fragment.isAdded()) {
                fragmentTransaction.hide(fragment);
            }
        }
        fragment = fragments.get(index);
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void initmagicindicator() {
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);

        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mdatalist.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);

                View customlayout = LayoutInflater.from(context).inflate(R.layout.mainpage_magic_indicator_item, null);
                final TextView titleimg = customlayout.findViewById(R.id.title_img);
                final TextView titletext = customlayout.findViewById(R.id.title_text);

                //设置图标
                if (index ==0){
                    titleimg.setText(R.string.icon_mainpage);
                    titletext.setText("首页");
                }else if(index==1){
                    titleimg.setText(R.string.icon_farmland);
                    titletext.setText("农田");
                } else if (index == 2) {
                    titleimg.setText(R.string.icon_suggestion);
                    titletext.setText("农艺");
                } else if (index == 3) {
                    titleimg.setText(R.string.icon_farmwork);
                    titletext.setText("农事");
                } else if (index == 4) {
                    titleimg.setText(R.string.icon_setting);
                    titletext.setText("设置");
                }

                titleimg.setTypeface(MyApplication.iconTypeFace);
                commonPagerTitleView.setContentView(customlayout);
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int i, int i1) {

                        // 34 141 28
                        titleimg.setTextColor(Color.parseColor("#46A05A"));
                        titletext.setTextColor(Color.parseColor("#46A05A"));
                    }

                    @Override
                    public void onDeselected(int i, int i1) {
                        titleimg.setTextColor(Color.parseColor("#9f9696"));
                        titletext.setTextColor(Color.parseColor("#9f9696"));
                    }

                    @Override
                    public void onLeave(int i, int i1, float v, boolean b) {

                    }

                    @Override
                    public void onEnter(int i, int i1, float v, boolean b) {

                    }
                });

                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fragmentContainerHelper.handlePageSelected(index);
                        switchPages(index);
                    }
                });

                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });

        magicIndicator.setNavigator(commonNavigator);
        fragmentContainerHelper.attachMagicIndicator(magicIndicator);

    }

    private void initfragments() {
        // 1
        MapFragment mapFragment = new MapFragment();
        FarmlandFragment farmlandFragment = new FarmlandFragment();
        SuggestFragment suggestFragment = new SuggestFragment();
        FarmworkFragment farmworkFragment = new FarmworkFragment();
        SettingFragment settingFragment = new SettingFragment();
        fragments.add(mapFragment);
        fragments.add(farmlandFragment);
        fragments.add(suggestFragment);
        fragments.add(farmworkFragment);
        fragments.add(settingFragment);

    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
