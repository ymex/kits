package cn.ymex.cocccute.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import cn.ymex.cocccute.R;
import cn.ymex.cocccute.base.BaseActivity;
import cn.ymex.cute.kits.ViewKit;
import cn.ymex.cute.widget.LoadingLayout;

public class LoadingActivity extends BaseActivity {
    LoadingLayout loadingLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        loadingLayout = ViewKit.find(this,R.id.activity_loading);
        ViewKit.findClick(this, R.id.btn_show_empty, this);
        ViewKit.findClick(this, R.id.btn_show_error, this);
        ViewKit.findClick(this, R.id.btn_show_loading, this);
        ViewKit.findClick(this, R.id.btn_show_content, this);

        CheckBox checkBox =ViewKit.find(this,R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                loadingLayout.setCancelable(isChecked);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_show_empty:
                loadingLayout.showEmpty();
                break;
            case R.id.btn_show_error:
                loadingLayout.showError();
                break;
            case R.id.btn_show_loading:
                loadingLayout.showLoading();
                break;
            case R.id.btn_show_content:
                loadingLayout.showContent();
                break;
        }
    }

}
