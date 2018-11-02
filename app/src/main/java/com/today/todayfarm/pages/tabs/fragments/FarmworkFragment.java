package com.today.todayfarm.pages.tabs.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.pages.selectfarmthing.SelectFarmThingActivity;

/**
 * 农事记录
 */
public class FarmworkFragment extends Fragment {
    private TextView btmenu;
    private Button addfarmthing;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.mainpage_farmwork_fragment,container,false);
        btmenu = view.findViewById(R.id.menu);
        btmenu.setTypeface(MyApplication.iconTypeFace);
        addfarmthing = view.findViewById(R.id.addfarmthing);
        setlistener();
        return view;
    }

    private void setlistener() {
        addfarmthing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开选择农事类型页面
                Intent intent = new Intent();
                intent.setClass(FarmworkFragment.this.getContext(), SelectFarmThingActivity.class);
                FarmworkFragment.this.getContext().startActivity(intent);
            }
        });
    }
}
