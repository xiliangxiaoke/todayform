package com.today.todayfarm.pages.tabs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnButtonClickListener;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.manager.DownloadManager;
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
import com.today.todayfarm.pages.zhuji.ZhujiActivity;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawerTabActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnButtonClickListener, OnDownloadListener {


    private static final String[] CHANNELS = new String[]{"首页","农田","农事","农艺","设置"};
    private List<String> mdatalist = Arrays.asList(CHANNELS);
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper();


    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;

    @BindView(R.id.maintitle)
            TextView maintitle;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_tab);

        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        maintitle.setText("首页");
        toolbar.setBackgroundColor(getResources().getColor(R.color.mainTitleColor));
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        StatusBarUtil.setColorForDrawerLayout(this,drawer,getResources().getColor(R.color.mainTitleColor));
        //StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));


        // 业务代码



        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));


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
                        //ToastUtil.show(DrawerTabActivity.this,"获取版本："+resultObj.getObject().getVersionName());
                        // 判断版本是否最新

                        // 显示更新窗口提示
//                        DownloadManager manager = DownloadManager.getInstance(DrawerTabActivity.this);
//                        manager.setApkName("Farmlogs."+resultObj.getObject().getVersionName())
//                                .setApkUrl(resultObj.getObject().getUrl())
//                                .setSmallIcon(R.mipmap.ic_launcher)
//                                .setAuthorities("com.today.todayfarm")
//                                .download();


                        downloadapk(resultObj.getObject());

                    }

                    @Override
                    public void onError(int code) {

                    }
                });
            }
        }, 1000);


    }

    private void downloadapk(AppVersionInfo info) {
        /*
         * 整个库允许配置的内容
         * 非必选
         */
        UpdateConfiguration configuration = new UpdateConfiguration()
                //输出错误日志
                .setEnableLog(true)
                //设置自定义的下载
                //.setHttpManager()
                //下载完成自动跳动安装页面
                .setJumpInstallPage(true)
                //设置对话框背景图片 (图片规范参照demo中的示例图)
                //.setDialogImage(R.drawable.ic_dialog)
                //设置按钮的颜色
                //.setDialogButtonColor(Color.parseColor("#E743DA"))
                //设置按钮的文字颜色
                .setDialogButtonTextColor(Color.WHITE)
                //支持断点下载
                .setBreakpointDownload(true)
                //设置是否显示通知栏进度
                .setShowNotification(true)
                //设置强制更新
                .setForcedUpgrade(false)
                //设置对话框按钮的点击监听
                .setButtonClickListener(this)
                //设置下载过程的监听
                .setOnDownloadListener(this);

        DownloadManager manager = DownloadManager.getInstance(DrawerTabActivity.this);
        manager.setApkName("Farmlogs."+info.getVersionName()+".apk")
                .setApkUrl(info.getUrl())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setShowNewerToast(true)
                .setConfiguration(configuration)
//                .setDownloadPath(Environment.getExternalStorageDirectory() + "/AppUpdate")
                .setApkVersionCode(Integer.parseInt(info.getVersionCode()))
                .setApkVersionName(info.getVersionName())
                //.setApkSize("20.4")
                .setAuthorities(getPackageName())
                .setApkDescription(info.getDescription())
                .download();
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
            maintitle.setText("首页");
            fragmentContainerHelper.handlePageSelected(0,false);
            switchPages(0);
        } else if ("menu_field".equals(event.type)) {
            maintitle.setText("农田列表");
            fragmentContainerHelper.handlePageSelected(1,false);
            switchPages(1);
        } else if ("menu_suggest".equals(event.type)) {
            maintitle.setText("农艺咨询");
            fragmentContainerHelper.handlePageSelected(2,false);
            switchPages(2);
        } else if ("menu_farmthing".equals(event.type)) {
            maintitle.setText("农事记录");
            fragmentContainerHelper.handlePageSelected(3,false);
            switchPages(3);
        } else if ("menu_setting".equals(event.type)) {
            maintitle.setText("设置");
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

        // 用来计算返回键的点击间隔时间
     private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                       && event.getAction() == KeyEvent.ACTION_DOWN) {
                         if ((System.currentTimeMillis() - exitTime) > 2000) {
                                 //弹出提示，可以有多种方式
                             Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                                 exitTime = System.currentTimeMillis();
                             } else {
                                this.finish();
                             }
                         return true;
                     }

        return super.onKeyDown(keyCode, event);
    }

    private void switchPages(int index) {



        switch (index) {
            case 0:
                maintitle.setText("首页");
                break;
            case 1:
                maintitle.setText("农田列表");
                break;
            case 2:
                maintitle.setText("农事记录");
                break;
            case 3:
                maintitle.setText("农艺咨询");
                break;
            case 4:
                maintitle.setText("设置");
                break;

        }


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
                final ImageView titleimg = customlayout.findViewById(R.id.title_img);
                final TextView titletext = customlayout.findViewById(R.id.title_text);

                //设置图标
                if (index ==0){
//                    titleimg.setText(R.string.icon_mainpage);
                    titleimg.setImageResource(R.mipmap.icon_main);
                    titletext.setText("首页");
                }else if(index==1){
//                    titleimg.setText(R.string.icon_farmland);
                    titleimg.setImageResource(R.mipmap.icon_farmland);
                    titletext.setText("农田");
                } else if (index == 2) {
//                    titleimg.setText(R.string.icon_suggestion);
                    titleimg.setImageResource(R.mipmap.icon_farmthing);
                    titletext.setText("农事");
                } else if (index == 3) {
//                    titleimg.setText(R.string.icon_farmwork);
                    titleimg.setImageResource(R.mipmap.icon_suggestion);
                    titletext.setText("农艺");
                } else if (index == 4) {
//                    titleimg.setText(R.string.icon_setting);
                    titleimg.setImageResource(R.mipmap.icon_setting);
                    titletext.setText("设置");
                }

//                titleimg.setTypeface(MyApplication.iconTypeFace);
                commonPagerTitleView.setContentView(customlayout);
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int i, int i1) {

                        // 34 141 28
//                        titleimg.setTextColor(Color.parseColor("#46A05A"));
                        titletext.setTextColor(Color.parseColor("#46A05A"));
                    }

                    @Override
                    public void onDeselected(int i, int i1) {
//                        titleimg.setTextColor(Color.parseColor("#9f9696"));
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
        fragments.add(farmworkFragment);
        fragments.add(suggestFragment);
        fragments.add(settingFragment);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.meun_main_page) {
            // Handle the camera action
            fragmentContainerHelper.handlePageSelected(0,false);
            switchPages(0);
        } else if (id == R.id.meun_farm_land) {
            fragmentContainerHelper.handlePageSelected(1,false);
            switchPages(1);
        } else if (id == R.id.meun_farm_thing) {
            fragmentContainerHelper.handlePageSelected(2,false);
            switchPages(2);
        } else if (id == R.id.meun_suggestion) {
            fragmentContainerHelper.handlePageSelected(3,false);
            switchPages(3);
        } else if (id == R.id.meun_setting) {
            fragmentContainerHelper.handlePageSelected(4,false);
            switchPages(4);
        } else if (id == R.id.meun_note) {
            Intent intent = new Intent();
            intent.setClass(this,ZhujiActivity.class);
            startActivity(intent);
        } else if (id == R.id.meun_weather){
            Intent intent = new Intent();
            intent.setClass(this,WeatherSearchActivity.class);
            intent.putExtra("city",""+Hawk.get(HawkKey.CITY));
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    @Override
    public void onButtonClick(int id) {

    }

    @Override
    public void start() {

    }

    @Override
    public void downloading(int max, int progress) {

    }

    @Override
    public void done(File apk) {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void error(Exception e) {

    }
}
