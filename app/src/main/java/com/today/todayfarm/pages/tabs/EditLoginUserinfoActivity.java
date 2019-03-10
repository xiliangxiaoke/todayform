package com.today.todayfarm.pages.tabs;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.dom.User;
import com.today.todayfarm.pages.note.EditNoteActivity;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditLoginUserinfoActivity extends BaseActivity {

    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.editname)
    EditText editname;
    @BindView(R.id.editcomname)
    EditText editcomname;
    @BindView(R.id.editcomaddr)
    EditText editcomaddr;




    @OnClick(R.id.back)
    public void setBack() {
        this.finish();
    }

    @OnClick(R.id.save)
            public void save(){
        API.updateLoginUserInfo(
                Hawk.get(HawkKey.TOKEN),
                editname.getText().toString(),
                editcomname.getText().toString(),
                editcomaddr.getText().toString(),
                user.getHeadImgUrl(),
                new ApiCallBack<Object>() {
                    @Override
                    public void onResponse(ResultObj<Object> resultObj) {
                        // 保存成功
                        new SweetAlertDialog(EditLoginUserinfoActivity.this)
                                .setTitleText("保存成功")
                                .showConfirmButton(true)
                                .setConfirmText("好的")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        EditLoginUserinfoActivity.this.finish();
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


    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_login_userinfo);
        ButterKnife.bind(this);

        back.setTypeface(MyApplication.iconTypeFace);
    }


    @Override
    protected void onResume() {
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
                                editname.setText(user.getFullName());

                                editcomname.setText(user.getOrgName());
                                editcomaddr.setText(user.getOrgAddress());

                            }
                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }
}
