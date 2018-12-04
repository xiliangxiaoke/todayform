package com.today.todayfarm.pages.note;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.today.todayfarm.R;
import com.today.todayfarm.application.MyApplication;
import com.today.todayfarm.base.BaseActivity;
import com.today.todayfarm.restapi.MyApiService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditNoteActivity extends BaseActivity {

    @BindView(R.id.close)
    TextView close;

    @BindView(R.id.save)
    TextView save;

    @OnClick(R.id.close)
    public void setClose() {
        this.finish();
    }

    @OnClick(R.id.save)
    public void setSave() {
        // TODO: 保存注记
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        ButterKnife.bind(this);

        close.setTypeface(MyApplication.iconTypeFace);
        save.setTypeface(MyApplication.iconTypeFace);

    }
}
