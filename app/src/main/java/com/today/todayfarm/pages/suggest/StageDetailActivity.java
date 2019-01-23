package com.today.todayfarm.pages.suggest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.dom.StageInfo;
import com.today.todayfarm.dom.SubStageInfo;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StageDetailActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<SubStageInfo> datalist = new ArrayList<>();
    RecyclerviewAdapter adapter = null;

    @OnClick(R.id.back)
    public void setback() {
        this.finish();
    }

    StageInfo stageInfo = null;
    String cropName = "";
    String cropImgUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_detail);
        ButterKnife.bind(this);

        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));

        String stageInfoJson = getIntent().getStringExtra("stageinfo_json");
        cropName = getIntent().getStringExtra("cropname");
        cropImgUrl = getIntent().getStringExtra("cropimgurl");
        try {
            stageInfo = new Gson().fromJson(stageInfoJson,StageInfo.class);
        } catch (Exception e) {

        }

        if (stageInfo != null) {
            title.setText(stageInfo.getStageName());
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
        API.findCropSubStages(
                Hawk.get(HawkKey.TOKEN),
                stageInfo.getStageId(),
                new ApiCallBack<SubStageInfo>() {
                    @Override
                    public void onResponse(ResultObj<SubStageInfo> resultObj) {
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
        int VIEW_TYPE_NOTE=3;
        int VIEW_TYPE_ITEM = 4;


        public RecyclerviewAdapter(Context context) {
            this.context = context;

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = null;
            if (viewType == VIEW_TYPE_TITLE) {
                view = LayoutInflater.from(context).inflate(R.layout.item_substage_title,parent,false);
                return new  ViewholderTitle(view);
            } else if (viewType == VIEW_TYPE_IMG) {
                view = LayoutInflater.from(context).inflate(R.layout.item_stage_img,parent,false);
                return new ViewholderImg(view);
            } else if (viewType == VIEW_TYPE_NOTE) {
                view = LayoutInflater.from(context).inflate(R.layout.item_substage_note,parent,false);
                return new ViewholderNote(view);
            } else if (viewType == VIEW_TYPE_ITEM) {
                view = LayoutInflater.from(context).inflate(R.layout.item_substage_item, parent, false);
                return new Viewholder(view);
            } else {
                return new RecyclerviewAdapter.Viewholder(view);
            }



        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ViewholderTitle) {
                ((ViewholderTitle) holder).title.setText(cropName);
            } else if (holder instanceof ViewholderImg) {

                ((ViewholderImg)holder).img.setImageURI( Uri.parse(cropImgUrl));
            } else if (holder instanceof ViewholderNote) {
                ((ViewholderNote) holder).note.setText(stageInfo.getStageDescription());
            } else if (holder instanceof Viewholder) {
                // data
                SubStageInfo subStageInfo = datalist.get(position - 3);
                ((Viewholder) holder).img.setImageURI(Uri.parse(subStageInfo.getContentLargeImageUrl()));
                ((Viewholder) holder).contenthead.setText(subStageInfo.getContentHeading());
                ((Viewholder) holder).contentdesc.setText(subStageInfo.getContentDescription());
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
            return datalist.size()+3;
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

        public class ViewholderNote extends RecyclerView.ViewHolder {
            TextView note;
            public ViewholderNote(View itemView) {
                super(itemView);
                note = itemView.findViewById(R.id.note);
            }
        }

        public class Viewholder extends RecyclerView.ViewHolder{



            SimpleDraweeView img;
            TextView contenthead;
            TextView contentdesc;



            public Viewholder(View itemView) {
                super(itemView);



                img = itemView.findViewById(R.id.img);
                contenthead = itemView.findViewById(R.id.contentHead);
                contentdesc = itemView.findViewById(R.id.contentDesc);


            }
        }
    }
}
