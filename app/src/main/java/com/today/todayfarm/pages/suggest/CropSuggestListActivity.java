package com.today.todayfarm.pages.suggest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.today.todayfarm.R;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.StageInfo;
import com.today.todayfarm.pages.tabs.fragments.SuggestFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CropSuggestListActivity extends BaseActivity {


    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;



    @OnClick(R.id.back)
    public void setback() {
        this.finish();
    }


    CropInfo cropInfo = null;
    List<StageInfo> datalist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_suggest_list);
        ButterKnife.bind(this);

        String cropinfojson = getIntent().getStringExtra("cropinfo_json");
        try {
            cropInfo = new Gson().fromJson(cropinfojson,CropInfo.class);
        } catch (Exception e) {

        }

        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }



    public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.Viewholder>{

        Context context;
        int VIEW_TYPE_TITLE = 1;
        int VIEW_TYPE_IMG = 2;
        int VIEW_TYPE_ITEM = 3;


        public RecyclerviewAdapter(Context context) {
            this.context = context;

        }

        @NonNull
        @Override
        public RecyclerviewAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            if (viewType == VIEW_TYPE_TITLE) {
                view = LayoutInflater.from(context).inflate(R.layout.item_stage_title,parent,false);
            } else if (viewType == VIEW_TYPE_IMG) {
                view = LayoutInflater.from(context).inflate(R.layout.item_stage_img,parent,false);
            } else if (viewType == VIEW_TYPE_ITEM) {
                view = LayoutInflater.from(context).inflate(R.layout.item_stage_item,parent,false);
            }

            return new RecyclerviewAdapter.Viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerviewAdapter.Viewholder holder, int position) {

        }

        @Override
        public int getItemViewType(int position) {
            int type = 1;
            if (position == 0) {
                type = VIEW_TYPE_TITLE;
            } else if (position == 1) {
                type = VIEW_TYPE_IMG;
            } else {
                type = VIEW_TYPE_ITEM;
            }
            return type;
        }

        @Override
        public int getItemCount() {
            return datalist.size()+2;
        }

        public class ViewholderTitle extends RecyclerView.ViewHolder{

            public ViewholderTitle(View itemView) {
                super(itemView);
            }
        }

        public class ViewholderImg extends RecyclerView.ViewHolder{

            public ViewholderImg(View itemView) {
                super(itemView);
            }
        }

        public class Viewholder extends RecyclerView.ViewHolder{

            View panel;

            SimpleDraweeView img;
            TextView name;



            public Viewholder(View itemView) {
                super(itemView);

                panel = itemView.findViewById(R.id.panel);

                img = itemView.findViewById(R.id.img);
                name = itemView.findViewById(R.id.name);


            }
        }
    }
}
