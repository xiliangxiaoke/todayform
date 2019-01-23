package com.today.todayfarm.pages.zhuji;

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
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.NoteInfo;
import com.today.todayfarm.dom.ResultObj;
//import com.today.todayfarm.pages.menu.MenuActivity;
import com.today.todayfarm.pages.note.EditNoteActivity;
import com.today.todayfarm.pages.selectfarm.SelectFarmActivity;
import com.today.todayfarm.pages.selectfarmthing.SelectFarmThingActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZhujiActivity extends BaseActivity {

    @BindView(R.id.springview)
    SpringView springView;

    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @OnClick(R.id.back)
    public void backclick() {
        this.finish();
    }

    @OnClick(R.id.addnote)
    public void addnote() {
        // 添加新注记
        Intent intent = new Intent();
        intent.setClass(ZhujiActivity.this, EditNoteActivity.class);

        intent.putExtra("pagetype","add");
        ZhujiActivity.this.startActivity(intent);
    }


    int pageidx = 1;
    int pagesize = 20;


    RecyclerviewAdapter adapter = null;
    List<NoteInfo> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuji);
        ButterKnife.bind(this);



        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));

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
                requestlist();
            }

            @Override
            public void onLoadmore() {
                requestlist();

            }
        });

        requestlist();


    }

    private void requestlist() {
        API.findMyScoutingNotes(
                Hawk.get(HawkKey.TOKEN),
                pageidx,
                pagesize,
                new ApiCallBack<NoteInfo>() {
                    @Override
                    public void onResponse(ResultObj<NoteInfo> resultObj) {
                        if (resultObj.getCode()==0) {
                            if (resultObj.getList() != null && resultObj.getList().size() > 0) {
                                listData.addAll(resultObj.getList());
                                pageidx++;
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
        List<NoteInfo> data;

        public RecyclerviewAdapter(Context context, List<NoteInfo> data) {
            this.context = context;
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerviewAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_zhuji,parent,false);
            return new RecyclerviewAdapter.Viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerviewAdapter.Viewholder holder, int position) {
            NoteInfo info = data.get(position);

            holder.zhujiname.setText(info.getScoutingNoteInfo());
            String fieldid = info.getFieldId();
            if (fieldid != null) {
                API.getFieldById(
                        Hawk.get(HawkKey.TOKEN),
                        fieldid,
                        new ApiCallBack<FieldInfo>() {
                            @Override
                            public void onResponse(ResultObj<FieldInfo> resultObj) {
                                if (resultObj.getCode() == 0) {
                                    FieldInfo fieldInfo = resultObj.getObject();
                                    if (fieldInfo != null) {
                                        holder.fieldname.setText(fieldInfo.getFieldName());
                                    }
                                }
                            }

                            @Override
                            public void onError(int code) {

                            }
                        }
                );
            }


            // show pic
            String urls = info.getPhotos();
            if (urls!=null && urls.length()>0){
                String[] urlarr =  urls.split(";");
                List l = Arrays.asList(urlarr);
                List<String> pics = new ArrayList<>(l);
                if (pics != null && pics.size() > 0) {
                    String ps = pics.get(0);
                    Uri uri = Uri.parse(ps);
                    holder.pic.setImageURI(uri);
                }
            }else{

            }



            holder.time.setText(info.getScoutingTime());

            holder.panel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO 打開注记详情页
                    Intent intent = new Intent();
                    intent.setClass(ZhujiActivity.this, EditNoteActivity.class);
                    intent.putExtra("noteinfo_json",new Gson().toJson(info));
                    intent.putExtra("pagetype","edit");
                    ZhujiActivity.this.startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class Viewholder extends RecyclerView.ViewHolder{

            View panel;
            TextView zhujiname;
            TextView fieldname;
            SimpleDraweeView pic;
            TextView time;



            public Viewholder(View itemView) {
                super(itemView);
                zhujiname = itemView.findViewById(R.id.zhujiname);
                fieldname = itemView.findViewById(R.id.fieldname);
                pic = itemView.findViewById(R.id.pic);
                time =itemView.findViewById(R.id.time);
                panel = itemView.findViewById(R.id.panel);

            }
        }
    }
}
