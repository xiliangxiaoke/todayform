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
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.pages.AddFarmMap.AddFarm2MapActivity;
import com.today.todayfarm.pages.pagedetail.FarmDetailActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 农田列表
 */
public class FarmlandFragment extends Fragment {
    private TextView btmenu;
    private Button btaddfarm;
    private SpringView springView;
    private RecyclerView recyclerView;
    TextView staticInfo;
    int pageidx = 1;
    int pagesize = 10;
    int total = 0;
    RecyclerviewAdapter adapter = null;
    List<FieldInfo> listData = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.mainpage_farmland_fragment,container,false);
        btmenu = view.findViewById(R.id.menu);
        btmenu.setTypeface(MyApplication.iconTypeFace);
        btaddfarm = view.findViewById(R.id.addfarm);
        btaddfarm.setTypeface(MyApplication.iconTypeFace);
        springView= view.findViewById(R.id.springview);
        recyclerView= view.findViewById(R.id.recyclerView);
        staticInfo = view.findViewById(R.id.staticinfo);

        springView.setHeader(new DefaultHeader(this.getActivity()));
        springView.setFooter(new DefaultFooter(this.getActivity()));

        adapter = new RecyclerviewAdapter(this.getActivity(), listData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);


        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageidx = 1;
                listData.clear();
                requestFieldlist();
            }

            @Override
            public void onLoadmore() {
                requestFieldlist();

            }
        });


        initlistener();

        requestFieldlist();

        return view;
    }

    private void requestFieldlist() {
        if (total<(pageidx-1)*pagesize){
            springView.onFinishFreshAndLoad();
            return;
        }
        API.findMyFields(
                Hawk.get(HawkKey.TOKEN),
                pageidx,
                pagesize,
                new ApiCallBack<FieldInfo>() {
                    @Override
                    public void onResponse(ResultObj<FieldInfo> resultObj) {
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
                            DecimalFormat df = new DecimalFormat("#.00");
                            staticInfo.setText("已添加"+resultObj.getAll()+"块农田，总面积"+df.format(area)+"亩");
                            try {
                                total = Integer.parseInt(resultObj.getAll()) ;
                            } catch (Exception e) {
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

    private void initlistener() {
        btaddfarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FarmlandFragment.this.getContext(), AddFarm2MapActivity.class);
                FarmlandFragment.this.getContext().startActivity(intent);
            }
        });
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
        public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_fieldinfo,parent,false);
            return new Viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Viewholder holder, int position) {
            FieldInfo info = data.get(position);
            holder.owner.setText(info.getFullName()+"的农田");
            holder.fieldname.setText(info.getFieldName());
            double fieldarea =0;
            try {
                fieldarea = Double.parseDouble(info.getFieldArea())/666.666;

            } catch (Exception e) {

            }
            DecimalFormat df = new DecimalFormat("#.00");

            String cropname = "";
            if (info.getFieldcrop()!=null){
                cropname = info.getFieldcrop();
            }

            holder.areacrop.setText(df.format(fieldarea)+"亩 " +cropname);

            holder.panel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String fieldid = info.getFieldId();
                    Intent intent = new Intent();
                    intent.setClass(FarmlandFragment.this.getContext(), FarmDetailActivity.class);
                    intent.putExtra("fieldid", fieldid);
                    FarmlandFragment.this.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class Viewholder extends RecyclerView.ViewHolder{

            View panel;

            TextView owner;
            TextView fieldname;
            TextView areacrop;

            public Viewholder(View itemView) {
                super(itemView);
                owner = itemView.findViewById(R.id.fieldowner);
                fieldname = itemView.findViewById(R.id.fieldname);
                areacrop = itemView.findViewById(R.id.areacrop);
                panel = itemView.findViewById(R.id.panel);

            }
        }
    }
}
