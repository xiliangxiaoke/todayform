package com.today.todayfarm.pages.selectcrop;

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
import com.jaeger.library.StatusBarUtil;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.FieldListActivity;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.pages.createcrop.CreateCropActivity;
import com.today.todayfarm.pages.selectfarm.SelectFarmActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * select crop activity
 */

public class SelectCropActivity extends BaseActivity {

    public static final int RESULT_CODE_SELECT_CROP_ACTIVITY = 2001;


    public static final int REQUEST_CODE_CREATE_CROP_ACTIVITY = 1001;
    public static final int REQUEST_CODE_SELECT_CROP_ACTIVITY = 1002;


    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.springview)
    SpringView springView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.nocroptip)
    View nocroptip;

    @BindView(R.id.farminfo)
    TextView tvfarminfo;


    @OnClick(R.id.back)
    public void back() {

        Intent intent = new Intent();
        intent.putExtra("cropinfo",new Gson().toJson(cropInfo));
        setResult(RESULT_CODE_SELECT_CROP_ACTIVITY,intent);
        this.finish();

    }

    @OnClick(R.id.addCrop)
    public void addcrop() {
        //打开添加新作物页面
        Intent intent = new Intent(SelectCropActivity.this, CreateCropActivity.class);
        intent.putExtra("fieldid", fieldid);
        SelectCropActivity.this.startActivityForResult(intent, REQUEST_CODE_CREATE_CROP_ACTIVITY);
    }


    CropInfo cropInfo = null;

    String fieldid;
    FieldInfo fieldInfo;

    List<CropInfo> croplist = new ArrayList<>();
    CropAdapter adapter;
    List<ListItem> listData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_crop);
        ButterKnife.bind(this);

        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));

        back.setTypeface(MyApplication.iconTypeFace);


        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));

        adapter = new CropAdapter(this, listData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                listData.clear();
                getCropList();
            }

            @Override
            public void onLoadmore() {

            }
        });

        String fieldInfostr = getIntent().getStringExtra("fieldinfo_json");
        fieldid = getIntent().getStringExtra("fieldid");
        fieldInfo = new Gson().fromJson(fieldInfostr,FieldInfo.class);


        if (fieldInfo != null) {
            tvfarminfo.setText(fieldInfo.getFieldName()+"  "+ Common.getAreaStr(fieldInfo.getFieldArea())+"亩");
            fieldid = fieldInfo.getFieldId();
        }else{
            //获取fieldinfo
            API.getFieldById(
                    Hawk.get(HawkKey.TOKEN),
                    fieldid,
                    new ApiCallBack<FieldInfo>() {
                        @Override
                        public void onResponse(ResultObj<FieldInfo> resultObj) {
                            if (resultObj.getCode() == 0) {
                                FieldInfo f = resultObj.getObject();
                                if (f != null) {
                                    fieldInfo = f;


                                }
                            }
                        }

                        @Override
                        public void onError(int code) {

                        }
                    }
            );
        }


        getCropList();
    }

    private void getCropList() {
        API.findCropInfosByFieldId(
                Hawk.get(HawkKey.TOKEN), fieldid,
                new ApiCallBack<CropInfo>() {
                    @Override
                    public void onResponse(ResultObj<CropInfo> resultObj) {
                        if (resultObj.getCode() == 0) {
                            if (resultObj.getList() != null && resultObj.getList().size() > 0) {
                                croplist = resultObj.getList();
                                //显示列表,按照要求构造数据
                                HashMap<String,List<CropInfo>> map = new HashMap();
                                for (int i=0; i<croplist.size();i++) {
                                    CropInfo crop = croplist.get(i);
                                    if (map.containsKey(crop.getPlantYear()+"")){
                                        List<CropInfo> list = (List<CropInfo>) map.get(crop.getPlantYear()+"");
                                        list.add(crop);
                                    }else{
                                        List<CropInfo> list = new ArrayList<>();
                                        list.add(crop);
                                        map.put(crop.getPlantYear()+"", list);
                                    }
                                }
                                listData.clear();
                                for (String key: map.keySet()){
                                    ListItem item = new ListItem();
                                    item.setType(ListItem.TYPE_YEAR);
                                    item.setTxt(key+"年");
                                    listData.add(item);
                                    List<CropInfo> list = map.get(key);
                                    for (int i=0;i<list.size();i++) {
                                        ListItem item2 = new ListItem();
                                        item2.setType(ListItem.TYPE_CROP);
                                        item2.setTxt(list.get(i).getCropName());
                                        item2.setCropInfo(list.get(i));
                                        listData.add(item2);
                                    }
                                }
                                adapter.update(listData);
                            } else {
                                shownocroptip();
                            }


                        } else if (resultObj.getCode() == 2){
                            shownocroptip();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CREATE_CROP_ACTIVITY && resultCode == CreateCropActivity.RESULT_CODE_CREATE_CROP) {
            //刚创建完新的作物，刷新列表显示
            getCropList();
        }
    }

    private void shownocroptip() {
        // 提示无作物，添加新作物
        nocroptip.setVisibility(View.VISIBLE);
        nocroptip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开添加新作物页面
                Intent intent = new Intent(SelectCropActivity.this, CreateCropActivity.class);
                intent.putExtra("fieldid", fieldid);
                SelectCropActivity.this.startActivityForResult(intent, REQUEST_CODE_CREATE_CROP_ACTIVITY);
            }
        });
    }



    class CropAdapter extends RecyclerView.Adapter<CropAdapter.MyHolder>{

        Context context;
        List<ListItem> list;

        public CropAdapter(Context context, List<ListItem> list) {
            this.context = context;
            this.list = list;
        }

        public void update(List<ListItem> list){
            this.list = list;
            notifyDataSetChanged();

        }


        @NonNull
        @Override
        public CropAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view;
            MyHolder myHolder;
            if (viewType == ListItem.TYPE_YEAR) {
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.crop_list_item_year,parent,false
                );
                myHolder = new CropAdapter.YearHolder(view);
            }else{
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.crop_list_item_self,parent,false
                );
                myHolder = new CropAdapter.CropHolder(view);
            }

            return myHolder;
        }

        @Override
        public int getItemViewType(int position) {
            return list.get(position).type;
        }

        @Override
        public void onBindViewHolder(@NonNull CropAdapter.MyHolder holder, int position) {

            ListItem item = list.get(position);
            if (holder instanceof YearHolder) {
                ((YearHolder) holder).tvyear.setText(item.getTxt());
                holder.panel.setOnClickListener(null);
            } else if (holder instanceof CropHolder) {
                ((CropHolder) holder).tvcrop.setText(item.getTxt());
                holder.panel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //选择完作物
                        Intent intent = new Intent();
                        intent.putExtra("cropinfo_json",new Gson().toJson(item.getCropInfo()));
                        SelectCropActivity.this.setResult(RESULT_CODE_SELECT_CROP_ACTIVITY,intent);
                        SelectCropActivity.this.finish();
                    }
                });
            }




        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyHolder extends RecyclerView.ViewHolder{


            View panel;

            public MyHolder(View itemView) {
                super(itemView);

                panel = itemView;

            }
        }

        class YearHolder extends MyHolder{

            TextView tvyear;

            public YearHolder(View itemView) {
                super(itemView);
                tvyear = itemView.findViewById(R.id.year);
            }
        }

        class CropHolder extends MyHolder{

            TextView tvcrop;
            public CropHolder(View itemView) {
                super(itemView);
                tvcrop = itemView.findViewById(R.id.cropname);
            }
        }
    }


    public class ListItem{

        public static final int TYPE_YEAR = 0;
        public static final int TYPE_CROP = 1;

        String txt;
        CropInfo cropInfo;
        int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public CropInfo getCropInfo() {
            return cropInfo;
        }

        public void setCropInfo(CropInfo cropInfo) {
            this.cropInfo = cropInfo;
        }
    }
}
