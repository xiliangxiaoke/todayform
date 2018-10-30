package com.today.todayfarm.pages.tabs.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;

public class SuggestFragment extends Fragment {

    private TextView btmenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.mainpage_suggest_fragment,container,false);
        btmenu = view.findViewById(R.id.menu);
        btmenu.setTypeface(MyApplication.iconTypeFace);
        return view;
    }
}
