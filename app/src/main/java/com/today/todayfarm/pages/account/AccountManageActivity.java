package com.today.todayfarm.pages.account;

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
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.dom.SubStageInfo;
import com.today.todayfarm.dom.User;
import com.today.todayfarm.pages.suggest.StageDetailActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountManageActivity extends BaseActivity {

    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.back)
            TextView back;

    List<User> datalist = new ArrayList<>();
    RecyclerviewAdapter adapter = null;

    @OnClick(R.id.add)
    public void add() {
        // 添加新账户
        Intent intent = new Intent(AccountManageActivity.this,AccountDetailActivity.class);
        intent.putExtra("fromtype","add");

        AccountManageActivity.this.startActivity(intent);
    }

    @OnClick(R.id.back)
    public void setback() {
        this.finish();
    }

    int currentPage = 1;
    int pageSize = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);
        ButterKnife.bind(this);

        back.setTypeface(MyApplication.iconTypeFace);


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
                currentPage = 1;
                requestData();
            }

            @Override
            public void onLoadmore() {
                currentPage++;
                requestData();
            }
        });


        requestData();
    }

    private void requestData() {

        API.findMyUsers(
                Hawk.get(HawkKey.TOKEN),
                currentPage,
                pageSize,
                new ApiCallBack<User>() {
                    @Override
                    public void onResponse(ResultObj<User> resultObj) {
                        if (resultObj.getCode() == 0) {
                            if (resultObj.getList() != null) {
                                if (currentPage == 1) {
                                    datalist.clear();
                                    datalist.addAll(resultObj.getList());
                                } else {
                                    datalist.addAll(resultObj.getList());
                                }
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


    public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.Viewholder>{

        Context context;



        public RecyclerviewAdapter(Context context) {
            this.context = context;

        }

        @NonNull
        @Override
        public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = null;
            view = LayoutInflater.from(context).inflate(R.layout.item_account_item, parent, false);


            return new Viewholder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull Viewholder holder, int position) {
            User user = datalist.get(position);
            holder.index.setText(""+position);
            holder.name.setText(user.getAliasName());
            holder.phone.setText(user.getPhone());
            if (user.getUserAuth() == 1) {
                holder.auth.setText("管理员");
            }else{
                holder.auth.setText("普通用户");
            }
            holder.panel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // todo 打开账户详情页
                    Intent intent = new Intent(AccountManageActivity.this,AccountDetailActivity.class);
                    intent.putExtra("fromtype","edit");
                    intent.putExtra("user_json",new Gson().toJson(user));
                    AccountManageActivity.this.startActivity(intent);
                }
            });

        }



        @Override
        public int getItemCount() {
            return datalist.size();
        }



        public class Viewholder extends RecyclerView.ViewHolder{



            View panel;
            TextView index;
            TextView name;
            TextView phone;
            TextView auth;




            public Viewholder(View itemView) {
                super(itemView);


                panel = itemView.findViewById(R.id.panel);
                index = itemView.findViewById(R.id.index);
                name = itemView.findViewById(R.id.name);
                phone = itemView.findViewById(R.id.phone);
                auth = itemView.findViewById(R.id.auth);




            }
        }
    }
}
