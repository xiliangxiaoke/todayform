package com.today.todayfarm.pages.tabs.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.Eventbus.MessageEvent;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.dom.User;
import com.today.todayfarm.pages.account.AccountManageActivity;
import com.today.todayfarm.pages.login.LoginActivity;
import com.today.todayfarm.pages.tabs.EditLoginUserinfoActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SettingFragment extends Fragment {

//    private TextView btmenu;
    private SimpleDraweeView pic;
    TextView name;
    TextView phone;
    TextView company;
    TextView address;
    TextView nameicon;
    TextView phoneicon;
    TextView companyicon;
    TextView addressicon;

    TextView account;
    TextView exitaccount;

    View edituserinfo;


    User user = null;

    String headUrl = null;// 头像的地址


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.mainpage_setting_fragment,container,false);
//        btmenu = view.findViewById(R.id.menu);
        pic = view.findViewById(R.id.pic);
        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        company = view.findViewById(R.id.company);
        address = view.findViewById(R.id.address);
        account = view.findViewById(R.id.account);
        nameicon = view.findViewById(R.id.nameicon);
        phoneicon = view.findViewById(R.id.phoneicon);
        companyicon = view.findViewById(R.id.companyicon);
        addressicon = view.findViewById(R.id.addressicon);
        edituserinfo = view.findViewById(R.id.edituserinfo);
        exitaccount = view.findViewById(R.id.exitaccount);



//        btmenu.setTypeface(MyApplication.iconTypeFace);
        nameicon.setTypeface(MyApplication.iconTypeFace);
        phoneicon.setTypeface(MyApplication.iconTypeFace);
        companyicon.setTypeface(MyApplication.iconTypeFace);
        addressicon.setTypeface(MyApplication.iconTypeFace);



//        Uri uri = Uri.parse("res://a/"+R.mipmap.camera);
//        pic.setImageURI(uri);



        initlistener();




        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


        // 获取用户信息
        API.getLoginUserInfo(
                Hawk.get(HawkKey.TOKEN),
                new ApiCallBack<User>() {
                    @Override
                    public void onResponse(ResultObj<User> resultObj) {
                        if (resultObj.getCode() == 0) {
                            user = resultObj.getObject();
                            if (user != null) {
                                name.setText(user.getFullName());
                                phone.setText(user.getPhone());
                                company.setText(user.getOrgName());
                                address.setText(user.getOrgAddress());
                                if (user.getHeadImgUrl() != null && user.getHeadImgUrl().length() > 0) {
                                    Uri uri = Uri.parse(user.getHeadImgUrl());
                                    pic.setImageURI(uri);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }

    private void initlistener() {
//        btmenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EventBus.getDefault().post(new MessageEvent("openMenuActivity",""));
//            }
//        });

        exitaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 退出登陆

                new SweetAlertDialog(SettingFragment.this.getContext())
                        .setTitleText("确定退出登录吗？")
                        .setContentText("退出登陆后将返回登录页面，重新登录。")
                        .showCancelButton(true)
                        .showConfirmButton(true)
                        .setCancelText("取消")
                        .setConfirmText("确定")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Hawk.deleteAll();
                                LoginActivity.afterExitCount = true;
                                Intent intent = new Intent();
                                intent.setClass(SettingFragment.this.getContext(), LoginActivity.class);
                                SettingFragment.this.getContext().startActivity(intent);
                            }
                        })
                        .show();



            }
        });

        edituserinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SettingFragment.this.getActivity(), EditLoginUserinfoActivity.class);
                SettingFragment.this.startActivity(intent);
            }
        });

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 添加新图片
                RxGalleryFinal
                        .with(SettingFragment.this.getContext())
                        .image()
                        .imageLoader(ImageLoaderType.FRESCO)
                        .radio()
                        .subscribe(new RxBusResultDisposable<ImageRadioResultEvent>() {
                            @Override
                            protected void onEvent(ImageRadioResultEvent baseResultEvent) throws Exception {
                                Log.d("sdf","sdf"+baseResultEvent.getResult().getOriginalPath());
                                String picpath = baseResultEvent.getResult().getOriginalPath();

                                final File file = new File(picpath);
                                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);


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
                                                    headUrl = url;
                                                    // 图片上传成功
                                                    Uri uri = Uri.parse(url);
                                                    pic.setImageURI(uri);

                                                    updateHeadImgurl(url);
                                                }
                                            }

                                            @Override
                                            public void onError(int code) {

                                            }
                                        }
                                );

                            }
                        })
                        .openGallery();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开账户管理页面
                Intent intent = new Intent(SettingFragment.this.getContext(), AccountManageActivity.class);
                SettingFragment.this.startActivity(intent);

            }
        });
    }

    private void updateHeadImgurl(String url) {
        API.updateLoginUserInfo(
                Hawk.get(HawkKey.TOKEN),
                user.getFullName(),
                user.getOrgName(),
                user.getOrgAddress(),
                url,
                new ApiCallBack<Object>() {
                    @Override
                    public void onResponse(ResultObj<Object> resultObj) {

                    }

                    @Override
                    public void onError(int code) {

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
}
