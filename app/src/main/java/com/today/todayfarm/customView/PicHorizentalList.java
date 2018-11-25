package com.today.todayfarm.customView;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.AddNewFieldActivity;
import com.today.todayfarm.FarmListActivity;
import com.today.todayfarm.R;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.FarmInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.Common;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 水平图片控件
 */
public class PicHorizentalList extends RelativeLayout {

    RecyclerView recyclerView;

    String defpic = "res://a/"+R.mipmap.add_pic;

    String dataurls = null;

    PiclistAdapter adapter  = new PiclistAdapter(this.getContext());


    public PicHorizentalList(Context context) {
        super(context);
        init(context);
    }

    public PicHorizentalList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PicHorizentalList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_view_horizental_pic_list,this);

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);



        recyclerView.setAdapter(adapter);


    }


    /**
     * 获取url列表字符串
     * @param urls
     */
    public void initdata(String urls) {
        Log.v("pics","initdata:"+urls);
        dataurls = urls;
        adapter.update(urls);

    }


    /**
     *  获取url列表字符串
     * @return
     */
    public String  geturls() {
        Log.v("pics","geturls-dataurls:"+dataurls);
        return dataurls;
    }




    class PiclistAdapter extends RecyclerView.Adapter<PiclistAdapter.MyHolder>{

        Context context;
        List<String> list = new ArrayList<>();
        String defpic = "res://a/"+R.mipmap.add_pic;

        public PiclistAdapter(Context context){
            this.context = context;


        }

        public void update(String urls){
            if (urls!=null && urls.length()>0){
                String[] urlarr =  urls.split(";");
                List l = Arrays.asList(urlarr);
                list = new ArrayList<>(l);
            }else{

            }

            list.add(defpic);
            notifyDataSetChanged();

        }


        @NonNull
        @Override
        public PiclistAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.pic_farmthing_list_item,parent,false
            );
            PiclistAdapter.MyHolder myHolder = new PiclistAdapter.MyHolder(view);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull PiclistAdapter.MyHolder holder, int position) {

            final String url = list.get(position);
            Uri uri = Uri.parse(url);
            holder.pic.setImageURI(uri);
            holder.pic.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position == list.size() - 1) {
                        // 添加新图片
                        RxGalleryFinal
                                .with(context)
                                .image()
                                .imageLoader(ImageLoaderType.FRESCO)
                                .radio()
                                .subscribe(new RxBusResultDisposable<ImageRadioResultEvent>() {
                                    @Override
                                    protected void onEvent(ImageRadioResultEvent baseResultEvent) throws Exception {
                                        Log.d("sdf","sdf"+baseResultEvent.getResult().getOriginalPath());
                                        String picpath = baseResultEvent.getResult().getOriginalPath();
                                        //上传
//                                        String bytestr = null;
//                                        try {
//
//                                            bytestr = Common.byte2hex(Common.readStream(picpath));
//                                        } catch (Exception e) {
//
//                                        }
                                        final File file = new File(picpath);
                                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);


//                                        if (bytestr != null && bytestr.length() > 0) {
                                            API.uploadPic(
                                                    Hawk.get(HawkKey.TOKEN),
                                                    "farmthingpic",
                                                    file,
                                                    new ApiCallBack<Object>() {
                                                        @Override
                                                        public void onResponse(ResultObj<Object> resultObj) {

                                                            String url = resultObj.getProp().getUrl();
                                                            Log.v("pics","upload success url:"+url);
                                                            if (url != null && url.length() > 0) {
                                                                // 添加到dataurls
                                                                if (dataurls != null && dataurls.length() > 0) {
                                                                    dataurls = dataurls+";"+url;
                                                                }else{
                                                                    dataurls = url;
                                                                }
                                                                Log.v("pics","new dataurls:"+dataurls);
                                                                adapter.update(dataurls);
                                                            }
                                                        }

                                                        @Override
                                                        public void onError(int code) {

                                                        }
                                                    }
                                            );

//                                        }
                                    }
                                })
                                .openGallery();

                    } else {

                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyHolder extends RecyclerView.ViewHolder{

            SimpleDraweeView pic;


            public MyHolder(View itemView){
                super(itemView);

                pic = itemView.findViewById(R.id.pic);
            }
        }
    }

}
