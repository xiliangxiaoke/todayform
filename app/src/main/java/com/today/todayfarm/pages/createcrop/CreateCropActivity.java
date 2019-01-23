package com.today.todayfarm.pages.createcrop;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.orhanobut.hawk.Hawk;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.constValue.HawkKey;
import com.today.todayfarm.dom.ResultObj;
import com.today.todayfarm.restapi.API;
import com.today.todayfarm.restapi.ApiCallBack;
import com.today.todayfarm.util.ToastUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建添加新的农作物
 */
public class CreateCropActivity extends Activity {


    public static final int RESULT_CODE_CREATE_CROP = 3000;
    @BindView(R.id.cropname)
    EditText etcropname;

    @BindView(R.id.year)
    TextView tvyear;

    @BindView(R.id.croplistbg)
    RelativeLayout croplistbg;

    @BindView(R.id.back)
            TextView back;
    @BindView(R.id.edit)
            TextView edit;

    String fieldid = null;
    String cropname ;
    int cropyear = 0;

    Calendar calendar;

    @OnClick(R.id.selectdefaultcroplist)
    public void selectdefaultcroplist(View view) {
        croplistbg.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.croplistbg)
    public void clickcroplistbg() {
        croplistbg.setVisibility(View.GONE);
    }

    // 玉米 小麦 大豆 水稻 棉花 向日葵
    @OnClick({R.id.cropyumi, R.id.cropxiaomai, R.id.cropdadou, R.id.cropshuidao, R.id.cropmianhua, R.id.cropxiangrikui})
    public void selectcrop(View view) {
        TextView selectview = (TextView) view;
        cropname = selectview.getText().toString();
        etcropname.setText(cropname);
        Hawk.put(HawkKey.LAST_CROP_SELECTED,cropname);
        croplistbg.setVisibility(View.GONE);
    }

    @OnClick(R.id.selectyear)
    public void selectyear(){
        new DatePickerDialog(
                CreateCropActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        cropyear = i;
                        tvyear.setText(cropyear+"年");
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)

        ).show();
    }

    @OnClick(R.id.back)
    public void back() {
        this.finish();
    }

    @OnClick(R.id.edit)
    public void edit() {

        if (cropname == null || cropname.length() == 0) {
            ToastUtil.show(this,"农作物未选定");
            return;
        }
        // 保存作物
        API.saveOrUpdateCropInfo(
                Hawk.get(HawkKey.TOKEN),
                fieldid,
                cropname,
                cropyear + "",
                new ApiCallBack<Object>() {
                    @Override
                    public void onResponse(ResultObj<Object> resultObj) {
                        if (resultObj.getCode()==0){
                            //保存成功
                            ToastUtil.show(CreateCropActivity.this,"保存成功");

                            CreateCropActivity.this.setResult(RESULT_CODE_CREATE_CROP);
                            CreateCropActivity.this.finish();

                        }else{
                            // 保存失败
                        }
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_crop);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.mainTitleColor));


        back.setTypeface(MyApplication.iconTypeFace);
        edit.setTypeface(MyApplication.iconTypeFace);


        //初始设置作物名称和时间
        cropname = Hawk.get(HawkKey.LAST_CROP_SELECTED);
        if (cropname != null && cropname.length()>0) {
            etcropname.setText(cropname);
        }else{
            etcropname.setText("玉米");
            cropname = "玉米";
        }


        fieldid = getIntent().getStringExtra("fieldid");
        calendar = Calendar.getInstance();
        cropyear = calendar.get(Calendar.YEAR);
        tvyear.setText(cropyear+"年");



    }
}
