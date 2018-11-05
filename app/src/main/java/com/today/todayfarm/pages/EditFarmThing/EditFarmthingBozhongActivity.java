package com.today.todayfarm.pages.EditFarmThing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.CropInfo;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditFarmthingBozhongActivity extends Activity {

    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.edit)
    TextView edit;

    @BindView(R.id.cropSpinner)
    AppCompatSpinner compatSpinner;

    @OnClick(R.id.back)
    public void back(){
        this.finish();
    }

    @OnClick(R.id.edit)
    public void edit() {
        // 保存编辑内容

    }

    String fieldid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_farmthing_bozhong);
        ButterKnife.bind(this);

        back.setTypeface(MyApplication.iconTypeFace);
        edit.setTypeface(MyApplication.iconTypeFace);

        fieldid = getIntent().getStringExtra("fieldid");

        compatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 获取作物列表
        getCroplist();
    }

    private void getCroplist() {

        API.findCropInfosByFieldId(
                Hawk.get(HawkKey.TOKEN), fieldid,
                new ApiCallBack<CropInfo>() {
                    @Override
                    public void onResponse(ResultObj<CropInfo> resultObj) {
                        // TODO: 获取作物列表 加载到spinner
                    }

                    @Override
                    public void onError(int code) {

                    }
                }

        );
    }
}
