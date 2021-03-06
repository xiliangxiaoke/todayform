package com.today.todayfarm.pages.tabs.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.Eventbus.MessageEvent;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.constValue.HawkKey;

import com.today.todayfarm.dom.FieldThingInfo;
import com.today.todayfarm.dom.ResultObj;

import com.today.todayfarm.pages.EditFarmThing.EditFarmthingBozhongActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingGuangaiActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingShifeiActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingShougeActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingZhengdiActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingZhibaoActivity;
import com.today.todayfarm.pages.selectfarm.SelectFarmActivity;
import com.today.todayfarm.pages.selectfarmthing.SelectFarmThingActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 农事记录
 */
public class FarmworkFragment extends Fragment {
//    private TextView btmenu;
    private Button addfarmthing;

    private SpringView springView;
    private RecyclerView recyclerView;

    int pageidx = 1;
    int pagesize = 10;

    RecyclerviewAdapter adapter = null;
    List<FieldThingInfo> listData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.mainpage_farmwork_fragment,container,false);
//        btmenu = view.findViewById(R.id.menu);
//        btmenu.setTypeface(MyApplication.iconTypeFace);
        addfarmthing = view.findViewById(R.id.addfarmthing);
        addfarmthing.setTypeface(MyApplication.iconTypeFace);
        springView= view.findViewById(R.id.springview);
        recyclerView= view.findViewById(R.id.recyclerView);

        adapter = new RecyclerviewAdapter(FarmworkFragment.this.getActivity(), listData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);

        springView.setHeader(new DefaultHeader(FarmworkFragment.this.getActivity()));
        springView.setFooter(new DefaultFooter(FarmworkFragment.this.getActivity()));


        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageidx = 1;
                listData.clear();
                requestFieldthinglist();
            }

            @Override
            public void onLoadmore() {
                requestFieldthinglist();

            }
        });



        setlistener();
        return view;
    }

    private void setlistener() {
        addfarmthing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开选择农事类型页面
                Intent intent = new Intent();
                intent.setClass(FarmworkFragment.this.getContext(), SelectFarmActivity.class);
                FarmworkFragment.this.getContext().startActivity(intent);
            }
        });


//        btmenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EventBus.getDefault().post(new MessageEvent("openMenuActivity",""));
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        requestFieldthinglist();
    }

    private void requestFieldthinglist() {

        API.findMyFieldsAllActivity(
                Hawk.get(HawkKey.TOKEN),
                pageidx,
                pagesize,
                new ApiCallBack<FieldThingInfo>() {
                    @Override
                    public void onResponse(ResultObj<FieldThingInfo> resultObj) {
                        if (resultObj.getCode()==0){
                            if (resultObj.getList()!=null && resultObj.getList().size()>0){
                                listData.addAll(resultObj.getList());
                                pageidx++;
                                adapter.notifyDataSetChanged();
                            }
                            double area=0;
                            if (resultObj.getProp() != null && resultObj.getProp().getTotalArea() != null && resultObj.getProp().getTotalArea().length() > 0) {
                                try {
                                    area = Double.parseDouble(resultObj.getProp().getTotalArea())/666.666;
                                } catch (Exception e) {
                                }


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
        List<FieldThingInfo> data;

        public RecyclerviewAdapter(Context context, List<FieldThingInfo> data) {
            this.context = context;
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerviewAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_field_thing_info,parent,false);
            return new RecyclerviewAdapter.Viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerviewAdapter.Viewholder holder, int position) {
            FieldThingInfo info = data.get(position);

            holder.farmthingstatus.setText(info.getStatus());
            holder.farmthingtype.setText(info.getFieldName()+":"+info.getType());
            holder.farmthingstarttime.setText("开始日期："+info.getStartDate());
            holder.farmthingendtime.setText("完成日期："+info.getEndDate());

            holder.panel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 跳转到指定的农事详情页面
                    Intent intent = null;
                    String type = info.getType();
                    if("播种".equals(type)){
                        intent = new Intent(FarmworkFragment.this.getContext(), EditFarmthingBozhongActivity.class);
                    }else if ("灌溉".equals(type)){
                        intent = new Intent(FarmworkFragment.this.getContext(), EditFarmthingGuangaiActivity.class);
                    }else if ("施肥".equals(type)){
                        intent = new Intent(FarmworkFragment.this.getContext(), EditFarmthingShifeiActivity.class);
                    }else if ("收割".equals(type)){
                        intent = new Intent(FarmworkFragment.this.getContext(), EditFarmthingShougeActivity.class);
                    }else if ("整地".equals(type)){
                        intent = new Intent(FarmworkFragment.this.getContext(), EditFarmthingZhengdiActivity.class);
                    }else if ("植保".equals(type)){
                        intent = new Intent(FarmworkFragment.this.getContext(), EditFarmthingZhibaoActivity.class);
                    }


//                    intent.putExtra("fieldinfo_json",fieldinfo_json);
                    intent.putExtra("id",info.getId());
                    intent.putExtra("fieldname",info.getFieldName());
                    FarmworkFragment.this.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class Viewholder extends RecyclerView.ViewHolder{

            View panel;
            TextView farmthingstatus;
            TextView farmthingtype;
            TextView farmthingstarttime;
            TextView farmthingendtime;

            public Viewholder(View itemView) {
                super(itemView);

                panel = itemView.findViewById(R.id.panel);
                farmthingstatus = itemView.findViewById(R.id.farm_thing_status);
                farmthingtype = itemView.findViewById(R.id.farmthingtype);
                farmthingstarttime = itemView.findViewById(R.id.farmthingstarttime);
                farmthingendtime = itemView.findViewById(R.id.farmthingendtime);

            }
        }
    }
}
