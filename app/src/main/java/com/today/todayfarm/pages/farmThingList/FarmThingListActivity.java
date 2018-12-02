package com.today.todayfarm.pages.farmThingList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.FieldInfo;
import com.today.todayfarm.dom.FieldThingInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingBozhongActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingGuangaiActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingShifeiActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingShougeActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingZhengdiActivity;
import com.today.todayfarm.pages.EditFarmThing.EditFarmthingZhibaoActivity;

import com.today.todayfarm.pages.tabs.fragments.FarmworkFragment;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.ExamplePagerAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 指定农田的农事记录详情
 */
public class FarmThingListActivity extends BaseActivity {

    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.springview)
    SpringView springView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    @OnClick(R.id.back)
    public void setBack() {
        this.finish();
    }




    private List<String> mDataList = new ArrayList<>();
    private List<CropInfo> croplist = new ArrayList<>();
    String fieldid = null;
    FieldInfo fieldInfo = null;
    int pageindex= 1;
    int pagesize = 5;
    int cropidx = 0;// 作物筛选条件列表的索引


    CommonNavigator commonNavigator;
    private ExamplePagerAdapter mExamplePagerAdapter = new ExamplePagerAdapter(mDataList);


    RecyclerviewAdapter adapter = null;
    List<FieldThingInfo> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_thing_list);
        ButterKnife.bind(this);
        back.setTypeface(MyApplication.iconTypeFace);
        viewPager.setAdapter(mExamplePagerAdapter);


        fieldid= getIntent().getStringExtra("fieldid");
        String fieldinfo_json = getIntent().getStringExtra("fieldinfo_json");
        try {
            fieldInfo = new Gson().fromJson(fieldinfo_json,FieldInfo.class);
        } catch (Exception e) {

        }


        initMagicIndicator1();

        adapter = new RecyclerviewAdapter(this, listData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));


        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageindex = 1;
                listData.clear();
                requestFieldthinglist();
            }

            @Override
            public void onLoadmore() {
                requestFieldthinglist();

            }
        });



        //获取作物清单
        getCropList();




    }



    private void requestFieldthinglist() {

        if (cropidx == 0) {

            showFarmThingsAll();
        } else {
            showFarmThingsByCrop();
        }

//        API.findMyFieldsAllActivity(
//                Hawk.get(HawkKey.TOKEN),
//                pageindex,
//                pagesize,
//                new ApiCallBack<FieldThingInfo>() {
//                    @Override
//                    public void onResponse(ResultObj<FieldThingInfo> resultObj) {
//                        if (resultObj.getCode()==0){
//                            if (resultObj.getList()!=null && resultObj.getList().size()>0){
//                                listData.addAll(resultObj.getList());
//                                pageindex++;
//                                adapter.notifyDataSetChanged();
//                            }
//                            double area=0;
//                            if (resultObj.getProp() != null && resultObj.getProp().getTotalArea() != null && resultObj.getProp().getTotalArea().length() > 0) {
//                                try {
//                                    area = Double.parseDouble(resultObj.getProp().getTotalArea())/666.666;
//                                } catch (Exception e) {
//                                }
//
//
//                            }
//
//                        }
//                        springView.onFinishFreshAndLoad();
//                    }
//
//                    @Override
//                    public void onError(int code) {
//                        springView.onFinishFreshAndLoad();
//                    }
//                }
//        );
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
                                mDataList.add("全部");
                                for (int i=0;i<croplist.size();i++) {
                                    mDataList.add(croplist.get(i).getCropName()+"("+croplist.get(i).getPlantYear()+")");
                                }
                                commonNavigator.notifyDataSetChanged();
                                mExamplePagerAdapter.notifyDataSetChanged();

                                // 默认获取全部农事
                                showFarmThingsAll();
                            } else {

                            }


                        } else if (resultObj.getCode() == 2){

                        }


                    }

                    @Override
                    public void onError(int code) {

                    }
                }

        );
    }

    private void showFarmThingsAll(){
        API.findFiledAllActivity(
                Hawk.get(HawkKey.TOKEN),
                fieldid,
                pageindex,
                pagesize,
                new ApiCallBack<FieldThingInfo>() {
                    @Override
                    public void onResponse(ResultObj<FieldThingInfo> resultObj) {
                        if (resultObj.getCode()==0){
                            if (resultObj.getList()!=null && resultObj.getList().size()>0){
                                listData.addAll(resultObj.getList());
                                pageindex++;
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

    private void showFarmThingsByCrop(){
        API.findFiledAllActivityOfCrop(
                Hawk.get(HawkKey.TOKEN),
                fieldid,
                croplist.get(cropidx - 1).getCropId(),
                croplist.get(cropidx - 1).getPlantYear(),
                pageindex,
                pagesize,
                new ApiCallBack<FieldThingInfo>() {
                    @Override
                    public void onResponse(ResultObj<FieldThingInfo> resultObj) {
                        if (resultObj.getCode()==0){
                            if (resultObj.getList()!=null && resultObj.getList().size()>0){
                                listData.addAll(resultObj.getList());
                                pageindex++;
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


    private void initMagicIndicator1() {

        magicIndicator.setBackgroundColor(Color.parseColor("#d43d3d"));
        commonNavigator = new CommonNavigator(this);
        commonNavigator.setSkimOver(true);
        int padding = UIUtil.getScreenWidth(this) / 2;
        commonNavigator.setRightPadding(padding);
        commonNavigator.setLeftPadding(padding);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(mDataList.get(index));
                clipPagerTitleView.setTextColor(Color.parseColor("#f2c4c4"));
                clipPagerTitleView.setClipColor(Color.WHITE);
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                        cropidx= index;
                        // TODO 根据选择的作物筛选农事记录
                        Log.v("选择的作物：",mDataList.get(index));
                        springView.callFresh();
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
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
                        intent = new Intent(FarmThingListActivity.this, EditFarmthingBozhongActivity.class);
                    }else if ("灌溉".equals(type)){
                        intent = new Intent(FarmThingListActivity.this, EditFarmthingGuangaiActivity.class);
                    }else if ("施肥".equals(type)){
                        intent = new Intent(FarmThingListActivity.this, EditFarmthingShifeiActivity.class);
                    }else if ("收割".equals(type)){
                        intent = new Intent(FarmThingListActivity.this, EditFarmthingShougeActivity.class);
                    }else if ("整地".equals(type)){
                        intent = new Intent(FarmThingListActivity.this, EditFarmthingZhengdiActivity.class);
                    }else if ("植保".equals(type)){
                        intent = new Intent(FarmThingListActivity.this, EditFarmthingZhibaoActivity.class);
                    }


                    intent.putExtra("fieldinfo_json",new Gson().toJson(fieldInfo));
                    intent.putExtra("id",info.getId());
                    intent.putExtra("fieldname",info.getFieldName());
                    FarmThingListActivity.this.startActivity(intent);
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
