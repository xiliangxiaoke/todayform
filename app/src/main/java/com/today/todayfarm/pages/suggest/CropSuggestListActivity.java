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
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.dom.StageInfo;
import com.today.todayfarm.pages.tabs.fragments.SuggestFragment;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CropSuggestListActivity extends BaseActivity {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    CropInfo cropInfo = null;
    List<StageInfo> datalist = new ArrayList<>();
    RecyclerviewAdapter adapter = null;

    @OnClick(R.id.back)
    public void setback() {
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_suggest_list);
        ButterKnife.bind(this);

        back.setTypeface(MyApplication.iconTypeFace);

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
        adapter = new RecyclerviewAdapter(this);
        recyclerView.setAdapter(adapter);

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }

            @Override
            public void onLoadmore() {

            }
        });


        requestData();

    }

    private void requestData() {
        API.findCropStages(
                Hawk.get(HawkKey.TOKEN),
                cropInfo.getCropId(),
                new ApiCallBack<StageInfo>() {
                    @Override
                    public void onResponse(ResultObj<StageInfo> resultObj) {
                        if (resultObj.getCode() == 0) {
                            if (resultObj.getList() != null) {
                                datalist = resultObj.getList();
                                adapter.notifyDataSetChanged();
                            }
                        }
                        springView.onFinishFreshAndLoad();
                    }

                    @Override
                    public void onError(int code) {
                        springView.onFinishFreshAndLoad();
                    }
                }
        );
    }


    public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        Context context;
        int VIEW_TYPE_TITLE = 1;
        int VIEW_TYPE_IMG = 2;
        int VIEW_TYPE_ITEM = 3;


        public RecyclerviewAdapter(Context context) {
            this.context = context;

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = null;
            if (viewType == VIEW_TYPE_TITLE) {
                view = LayoutInflater.from(context).inflate(R.layout.item_stage_title,parent,false);
                return new ViewholderTitle(view);
            } else if (viewType == VIEW_TYPE_IMG) {
                view = LayoutInflater.from(context).inflate(R.layout.item_stage_img,parent,false);
                return new ViewholderImg(view);
            } else if (viewType == VIEW_TYPE_ITEM) {
                view = LayoutInflater.from(context).inflate(R.layout.item_stage_item,parent,false);
                return new Viewholder(view);
            }

            return new RecyclerviewAdapter.Viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ViewholderTitle) {
                ((ViewholderTitle) holder).title.setText(cropInfo.getCropName());
            } else if (holder instanceof ViewholderImg) {

                ((ViewholderImg)holder).img.setImageURI( Uri.parse(cropInfo.getCropLargeImageUrl()));
            } else if (holder instanceof Viewholder) {
                // data
                StageInfo stageInfo = datalist.get(position-2);
                ((Viewholder) holder).img.setImageURI(Uri.parse(stageInfo.getStageImageUrl()));
                ((Viewholder) holder).name.setText(stageInfo.getStageName());
                ((Viewholder) holder).panel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CropSuggestListActivity.this,StageDetailActivity.class);
                        intent.putExtra("stageinfo_json",new Gson().toJson(stageInfo));
                        intent.putExtra("cropname",cropInfo.getCropName());
                        intent.putExtra("cropimgurl",cropInfo.getCropLargeImageUrl());
                        CropSuggestListActivity.this
                                .startActivity(intent);
                    }
                });
            }
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

            TextView title;
            public ViewholderTitle(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
            }
        }

        public class ViewholderImg extends RecyclerView.ViewHolder{
            SimpleDraweeView img;
            public ViewholderImg(View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.img);
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
