package com.today.todayfarm.pages.zhuji;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.today.todayfarm.R;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.pages.menu.MenuActivity;
import com.today.todayfarm.pages.selectfarm.SelectFarmActivity;
import com.today.todayfarm.pages.selectfarmthing.SelectFarmThingActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZhujiActivity extends BaseActivity {

    @BindView(R.id.springview)
    SpringView springView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @OnClick(R.id.menu)
    public void menuclick() {
        Intent intent = new Intent();
        intent.setClass(this, MenuActivity.class);
        this.startActivity(intent);
    }


    int pageidx = 1;
    int pagesize = 20;


    RecyclerviewAdapter adapter = null;
    List<FieldInfo> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuji);
        ButterKnife.bind(this);


        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));

        adapter = new RecyclerviewAdapter(this, listData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageidx = 1;
                listData.clear();
                requestlist();
            }

            @Override
            public void onLoadmore() {
                requestlist();

            }
        });

        requestlist();


    }


    public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.Viewholder>{

        Context context;
        List<FieldInfo> data;

        public RecyclerviewAdapter(Context context, List<FieldInfo> data) {
            this.context = context;
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerviewAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_zhuji,parent,false);
            return new RecyclerviewAdapter.Viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerviewAdapter.Viewholder holder, int position) {
            FieldInfo info = data.get(position);


            holder.fieldname.setText(info.getFieldName());


        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class Viewholder extends RecyclerView.ViewHolder{

            View panel;
            TextView fieldname;



            public Viewholder(View itemView) {
                super(itemView);
                fieldname = itemView.findViewById(R.id.fieldname);
                panel = itemView.findViewById(R.id.panel);

            }
        }
    }
}
