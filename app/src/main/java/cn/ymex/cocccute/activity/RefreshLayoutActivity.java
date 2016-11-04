package cn.ymex.cocccute.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.ymex.cocccute.R;
import cn.ymex.cute.kits.ViewKit;

public class RefreshLayoutActivity extends AppCompatActivity {
    TextView tvTitle;
    int i = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_layout);
        tvTitle = ViewKit.findClick(this, R.id.tv_title, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i+=20;
                tvTitle.setTranslationX(10+i);
            }
        });
    }
}
/**
 scrollBy() 与 scrollTo()移动的是view 内容位置，不改变view 自己的位置。
 **/