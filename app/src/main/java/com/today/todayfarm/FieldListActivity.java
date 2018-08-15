package com.today.todayfarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FieldListActivity extends Activity {

    @OnClick(R.id.back)void back(){
        FieldListActivity.this.finish();
    }

    @OnClick(R.id.add) void addfield(){
        Intent intent = new Intent();
        intent.setClass(FieldListActivity.this,AddNewFieldActivity.class);
        FieldListActivity.this.startActivity(intent);

    }

    @BindView(R.id.fieldlist)RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_list);

        ButterKnife.bind(this);


    }
}
