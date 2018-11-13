package com.today.todayfarm.pages.selectfarm;

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
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.pages.pagedetail.FarmDetailActivity;
import com.today.todayfarm.pages.selectfarmthing.SelectFarmThingActivity;
import com.today.todayfarm.pages.tabs.fragments.FarmlandFragment;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectFarmActivity extends Activity {

    @BindView(R.id.back)
    TextView back;

    @OnClick(R.id.back)
    public void back() {
        this.finish();
    }

    @BindView(R.id.springview)
    SpringView springView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;



    int pageidx = 1;
    int pagesize = 20;
    int total = 0;

    RecyclerviewAdapter adapter = null;
    List<FieldInfo> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_farm);
        ButterKnife.bind(this);

        back.setTypeface(MyApplication.iconTypeFace);


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
                requestFieldlist();
            }

            @Override
            public void onLoadmore() {
                requestFieldlist();

            }
        });

        requestFieldlist();
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

//                            DecimalFormat df = new DecimalFormat("#.00");
//                            staticInfo.setText("已添加"+resultObj.getAll()+"块农田，总面积"+df.format(area)+"亩");
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
            View view = LayoutInflater.from(context).inflate(R.layout.item_select_field,parent,false);
            return new RecyclerviewAdapter.Viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerviewAdapter.Viewholder holder, int position) {
            FieldInfo info = data.get(position);


            holder.fieldname.setText(info.getFieldName());

            holder.panel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 进入 选择农事类型页面
                    Intent intent = new Intent(SelectFarmActivity.this, SelectFarmThingActivity.class);
                    intent.putExtra("fieldinfo_json",new Gson().toJson(info));
                    SelectFarmActivity.this.startActivity(intent);
                    SelectFarmActivity.this.finish();
                }
            });
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
