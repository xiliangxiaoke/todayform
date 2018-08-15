package com.today.todayfarm;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.dom.FarmInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.restapi.Doapi;
import com.today.todayfarm.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarmListActivity extends AppCompatActivity {

    @OnClick(R.id.back)void back(){
        FarmListActivity.this.finish();
    }

    @OnClick(R.id.add) void addfarm(){
        Intent intent = new Intent();
        intent.setClass(FarmListActivity.this,AddFarmActivity.class);
        FarmListActivity.this.startActivity(intent);

    }

    @BindView(R.id.fieldlist)RecyclerView recyclerView;

    SpringView springView;

    FarmlistAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_list);
        ButterKnife.bind(this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onresume","farmlistactivity onresume");
        //获取农场列表
        getfarmlist();
    }

    private void getfarmlist() {
        Call<ResultObj<FarmInfo>> call = Doapi.instance().getfarms(
                MyApplication.token
        );

        call.enqueue(new Callback<ResultObj<FarmInfo>>() {
            @Override
            public void onResponse(Call<ResultObj<FarmInfo>> call, Response<ResultObj<FarmInfo>> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode()==200){
                        ToastUtil.show(FarmListActivity.this,"获取成功");

                        List<FarmInfo> farms = response.body().getList();
                        Gson gson = new Gson();
                        Log.d("getfarmlist",gson.toJson(farms));

                        if (adapter == null){
                            adapter = new FarmlistAdapter(FarmListActivity.this,farms);
                            recyclerView.setAdapter(adapter);
                        }else{
                            adapter.update(farms);
                        }


                    }
                }else{
                    ToastUtil.show(FarmListActivity.this,"获取列表失败");
                }
            }

            @Override
            public void onFailure(Call<ResultObj<FarmInfo>> call, Throwable t) {
                ToastUtil.show(FarmListActivity.this,"获取列表失败");
            }
        });
    }

    class FarmlistAdapter extends RecyclerView.Adapter<FarmlistAdapter.MyHolder>{

        Context context;
        List<FarmInfo> list;

        public FarmlistAdapter(Context context,List<FarmInfo> list){
            this.context = context;
            this.list = list;
        }

        public void update(List<FarmInfo> list){
            this.list = list;
            notifyDataSetChanged();

        }


        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.farm_list_item,parent,false
            );
            MyHolder myHolder = new MyHolder(view);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {

            final FarmInfo info = list.get(position);
            holder.name.setText(info.getName());
            holder.addr.setText(info.getAddress());
            holder.select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddNewFieldActivity.farmInfo = info;
                    FarmListActivity.this.finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyHolder extends RecyclerView.ViewHolder{
            TextView name;
            TextView addr;
            Button select;

            public MyHolder(View itemView){
                super(itemView);
                name = itemView.findViewById(R.id.farmnameitem);
                addr = itemView.findViewById(R.id.farmaddritem);
                select = itemView.findViewById(R.id.select);
            }
        }
    }
}
