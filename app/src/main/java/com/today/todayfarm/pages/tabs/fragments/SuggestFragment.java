package com.today.todayfarm.pages.tabs.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.Eventbus.MessageEvent;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.HealthImgInfo;
import com.today.todayfarm.dom.JSParamInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.dom.SatellateImgInfo;
import com.today.todayfarm.dom.TimeAxisItemInfo;
import com.today.todayfarm.pages.suggest.CropSuggestListActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.ToastUtil;
import com.today.todayfarm.util.WebUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class SuggestFragment extends Fragment {

//    private TextView btmenu;
    SpringView springView;
    RecyclerView recyclerView;

    int currentPage = 1;
    int pageSize = 20;

    List<CropInfo> datalist = new ArrayList<>();
    RecyclerviewAdapter adapter = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.mainpage_suggest_fragment,container,false);
//        btmenu = view.findViewById(R.id.menu);
//        btmenu.setTypeface(MyApplication.iconTypeFace);
        springView = view.findViewById(R.id.springview);
        recyclerView = view.findViewById(R.id.recyclerView);

        springView.setHeader(new DefaultHeader(this.getActivity()));
        springView.setFooter(new DefaultFooter(this.getActivity()));

        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(),2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerviewAdapter(SuggestFragment.this.getActivity());
        recyclerView.setAdapter(adapter);


        initlistener();

        // todo load data
        requestData();

        return view;
    }

    private void requestData() {
        API.getCropHelpList(
                Hawk.get(HawkKey.TOKEN),
                currentPage,
                pageSize,
                new ApiCallBack<CropInfo>() {
                    @Override
                    public void onResponse(ResultObj<CropInfo> resultObj) {
                        if (resultObj.getCode() == 0) {
                            if (currentPage == 1) {
                                datalist.clear();
                            }
                            datalist.addAll(resultObj.getList());
                            adapter.notifyDataSetChanged();
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

    private void initlistener() {
//        btmenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EventBus.getDefault().post(new MessageEvent("openMenuActivity",""));
//
//            }
//        });

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                // todo 刷新数据
                currentPage = 1;

                requestData();
            }

            @Override
            public void onLoadmore() {

                currentPage ++;
                requestData();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        //EventBus.getDefault().unregister(this);
    }

    public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.Viewholder>{

        Context context;


        public RecyclerviewAdapter(Context context) {
            this.context = context;

        }

        @NonNull
        @Override
        public RecyclerviewAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_crop_help,parent,false);
            return new RecyclerviewAdapter.Viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerviewAdapter.Viewholder holder, int position) {
            CropInfo cropInfo = datalist.get(position);
            Uri uri = Uri.parse(cropInfo.getCropMediumImageUrl());
            holder.img.setImageURI(uri);

            holder.name.setText(cropInfo.getCropName());

            holder.panel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  打开作物的建议详情
                    Intent intent = new Intent();
                    intent.setClass(SuggestFragment.this.getContext(), CropSuggestListActivity.class);
                    intent.putExtra("cropinfo_json",new Gson().toJson(cropInfo));
                    SuggestFragment.this.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return datalist.size();
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
