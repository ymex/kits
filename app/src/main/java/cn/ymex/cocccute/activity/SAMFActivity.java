package cn.ymex.cocccute.activity;

import android.os.Bundle;

import cn.ymex.cocccute.R;
import cn.ymex.cute.kits.Finder;
import cn.ymex.cute.samf.SamfActivity;
import cn.ymex.cute.widget.bottomtab.BottomTab;
import cn.ymex.cute.widget.bottomtab.BottomTabView;


public class SAMFActivity extends SamfActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samf);
        BottomTabView bottomTabView = Finder.find(this,R.id.bt_item);
        BottomTab bottomTab = new BottomTab(R.mipmap.ic_favorites,"标题");
        bottomTabView.setData(bottomTab);
    }
}

