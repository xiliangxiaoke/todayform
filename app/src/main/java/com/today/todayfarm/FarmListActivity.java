package com.today.todayfarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FarmListActivity extends AppCompatActivity {

    @OnClick(R.id.back)void back(){
        FarmListActivity.this.finish();
    }

    @OnClick(R.id.add) void addfield(){
        Intent intent = new Intent();
        intent.setClass(FarmListActivity.this,AddNewFieldActivity.class);
        FarmListActivity.this.startActivity(intent);

    }

    @BindView(R.id.farmlist)RecyclerView recyclerView;

    SpringView springView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_list);
        ButterKnife.bind(this);

        springView = findViewById(R.id.springview);

        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadmore() {

            }
        });
    }
}
