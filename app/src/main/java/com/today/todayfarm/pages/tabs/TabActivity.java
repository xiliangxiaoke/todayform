package com.today.todayfarm.pages.tabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.pages.tabs.fragments.FarmlandFragment;
import com.today.todayfarm.pages.tabs.fragments.FarmworkFragment;
import com.today.todayfarm.pages.tabs.fragments.MapFragment;
import com.today.todayfarm.pages.tabs.fragments.SettingFragment;
import com.today.todayfarm.pages.tabs.fragments.SuggestFragment;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主功能页面，底部菜单切换
 */
public class TabActivity extends BaseActivity {

    private static final String[] CHANNELS = new String[]{"首页","农田","建议","农事","设置"};
    private List<String> mdatalist = Arrays.asList(CHANNELS);
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper();


    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ButterKnife.bind(this);

        initfragments();
        initmagicindicator();

        fragmentContainerHelper.handlePageSelected(0,false);
        switchPages(0);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Hawk.get(HawkKey.MAIN_PAGE_INDEX_TO_SHOW)!=null){
            int index = Hawk.get(HawkKey.MAIN_PAGE_INDEX_TO_SHOW);
            fragmentContainerHelper.handlePageSelected(index,false);
            switchPages(index);
            Hawk.put(HawkKey.MAIN_PAGE_INDEX_TO_SHOW,0);
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
                    titletext.setText("建议");
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
                        titleimg.setTextColor(Color.parseColor("#259b24"));
                        titletext.setTextColor(Color.parseColor("#259b24"));
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
}
