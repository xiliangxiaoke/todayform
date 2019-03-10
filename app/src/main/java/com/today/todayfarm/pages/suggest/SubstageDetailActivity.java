package com.today.todayfarm.pages.suggest;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.dom.SubStageInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubstageDetailActivity extends Activity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.pic)
    SimpleDraweeView pic;
    @BindView(R.id.article)
    TextView article;



    @OnClick(R.id.back)
    public void setback(){
        this.finish();
    }


    SubStageInfo subStageInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substage_detail);
        ButterKnife.bind(this);

        back.setTypeface(MyApplication.iconTypeFace);


        String jsonstr = getIntent().getStringExtra("substageinfo_json");
        try{
            subStageInfo = new Gson().fromJson(jsonstr,SubStageInfo.class);
            title.setText(subStageInfo.getContentHeading());
            pic.setImageURI(Uri.parse(subStageInfo.getContentLargeImageUrl()));
            article.setText(subStageInfo.getContentDescription());
        }catch (Exception e){

        }



    }
}
