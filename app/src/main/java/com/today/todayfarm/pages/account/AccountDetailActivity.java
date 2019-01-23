package com.today.todayfarm.pages.account;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.dom.User;
import com.today.todayfarm.pages.regist.RegistActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountDetailActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.niceSpinner)
    NiceSpinner niceSpinner;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.save)
    TextView save;

    @OnClick(R.id.back)
    public void back() {
        this.finish();
    }

    @OnClick(R.id.save)
    public void save() {
        // TODO save
        if ("add".equals(fromtype)) {
            API.addUser(
                    Hawk.get(HawkKey.TOKEN),
                    name.getText().toString(),
                    phone.getText().toString(),
                    niceSpinner.getSelectedIndex() + "",
                    new ApiCallBack<Object>() {
                        @Override
                        public void onResponse(ResultObj<Object> resultObj) {
                            // save success
                            new SweetAlertDialog(AccountDetailActivity.this)
                                    .setTitleText("添加成功")
                                    .showConfirmButton(true)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            AccountDetailActivity.this.finish();
                                        }
                                    })
                                    .show();
                        }

                        @Override
                        public void onError(int code) {

                        }
                    }
            );
        } else if ("edit".equals(fromtype)) {
            API.updateUser(
                    Hawk.get(HawkKey.TOKEN),
                    user.getAssociateUserId(),
                    name.getText().toString(),
                    phone.getText().toString(),
                    niceSpinner.getSelectedIndex()+"",
                    new ApiCallBack<Object>() {
                        @Override
                        public void onResponse(ResultObj<Object> resultObj) {
                            // save success
                            new SweetAlertDialog(AccountDetailActivity.this)
                                    .setTitleText("修改成功")
                                    .showConfirmButton(true)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            AccountDetailActivity.this.finish();
                                        }
                                    })
                                    .show();
                        }

                        @Override
                        public void onError(int code) {

                        }
                    }
            );
        }
    }

    @OnClick(R.id.delete)
    public void setDelete() {
        // todo delete
        API.delUser(
                Hawk.get(HawkKey.TOKEN),
                user.getAssociateUserId(),
                new ApiCallBack<Object>() {
                    @Override
                    public void onResponse(ResultObj<Object> resultObj) {
                        new SweetAlertDialog(AccountDetailActivity.this)
                                .setTitleText("删除成功")
                                .showConfirmButton(true)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        AccountDetailActivity.this.finish();
                                    }
                                })
                                .show();
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }

    String fromtype = "";// add edit
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));

        back.setTypeface(MyApplication.iconTypeFace);
        save.setTypeface(MyApplication.iconTypeFace);


        List<String> dataset = new LinkedList<>(Arrays.asList(
                "普通用户",
                "管理人员"
        ));
        niceSpinner.attachDataSource(dataset);

        fromtype = getIntent().getStringExtra("fromtype");
        if ("add".equals(fromtype)) {
            title.setText("添加用户");
            delete.setVisibility(View.GONE);
        } else if ("edit".equals(fromtype)) {
            title.setText("修改用户");
            delete.setVisibility(View.VISIBLE);
        }
        String userjson = getIntent().getStringExtra("user_json");
        try {
            user = new Gson().fromJson(userjson,User.class);
        } catch (Exception e) {

        }

        if (user != null) {
            phone.setText(user.getPhone());
            name.setText(user.getAliasName());
            niceSpinner.setSelectedIndex(user.getUserAuth());
        }





    }
}
