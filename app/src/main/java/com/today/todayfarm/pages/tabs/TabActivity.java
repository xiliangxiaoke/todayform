package com.today.todayfarm.pages.tabs;

import android.app.Activity;
import android.os.Bundle;

import com.today.todayfarm.R;
import com.today.todayfarm.base.BaseActivity;

import butterknife.ButterKnife;

public class TabActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ButterKnife.bind(this);
    }
}
