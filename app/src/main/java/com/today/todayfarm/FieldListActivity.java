package com.today.todayfarm;

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

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.dom.FarmInfo;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.restapi.Doapi;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FieldListActivity extends Activity {

    @OnClick(R.id.back)void back(){
        FieldListActivity.this.finish();
    }

    @OnClick(R.id.add) void addfield(){
        Intent intent = new Intent();
        intent.setClass(FieldListActivity.this,AddNewFieldActivity.class);
        FieldListActivity.this.startActivity(intent);

    }

    @BindView(R.id.fieldlist)RecyclerView recyclerView;


    FieldAdatper adatper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_list);

        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    protected void onResume() {
        super.onResume();

        Call<ResultObj<FieldInfo>> call = Doapi.instance().getFields(MyApplication.token);
        call.enqueue(new Callback<ResultObj<FieldInfo>>() {
            @Override
            public void onResponse(Call<ResultObj<FieldInfo>> call, Response<ResultObj<FieldInfo>> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode()==200){
                        List<FieldInfo> list = response.body().getList();

                        if (adatper == null){
                            adatper = new FieldAdatper(FieldListActivity.this,list);
                            recyclerView.setAdapter(adatper);
                        }else{
                            adatper.update(list);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultObj<FieldInfo>> call, Throwable t) {

            }
        });
    }

    class FieldAdatper extends RecyclerView.Adapter<FieldAdatper.MyHolder>{

        Context context;
        List<FieldInfo> list;

        public FieldAdatper(Context context, List<FieldInfo> list) {
            this.context = context;
            this.list = list;
        }

        public void update(List<FieldInfo> list){
            this.list = list;
            notifyDataSetChanged();

        }


        @NonNull
        @Override
        public FieldAdatper.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.field_list_item,parent,false
            );
            MyHolder myHolder = new MyHolder(view);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull FieldAdatper.MyHolder holder, int position) {

            final FieldInfo info = list.get(position);

//            holder.fieldname.setText(info.getName());
//            holder.locationmap.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    MainMapActivity.selectFieldid = info.getFieldid();
//                    FieldListActivity.this.finish();
//                }
//            });
//            holder.editfield.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    EditFieldinfoActivity.fieldInfo = info;
//                    Intent intent = new Intent();
//                    intent.setClass(FieldListActivity.this,EditFieldinfoActivity.class);
//                    FieldListActivity.this.startActivity(intent);
//                }
//            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyHolder extends RecyclerView.ViewHolder{

            TextView fieldname;
            View locationmap;
            View editfield;

            public MyHolder(View itemView) {
                super(itemView);

                fieldname = itemView.findViewById(R.id.fieldname);
                locationmap = itemView.findViewById(R.id.location2map);
                editfield = itemView.findViewById(R.id.editfield);
            }
        }
    }
}
