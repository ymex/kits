package cn.ymex.sample.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import cn.ymex.kits.Metric;
import cn.ymex.kits.ViewExt;
import cn.ymex.kits.widget.Toaster;
import cn.ymex.kits.widget.ToolBarExt;
import cn.ymex.sample.R;
import cn.ymex.sample.adapter.ItemListViewAdapter;
import cn.ymex.sample.base.BaseActivity;
import cn.ymex.sample.entity.ItemEntity;
import cn.ymex.sample.kits.Logger;

public class MainActivity extends BaseActivity
        implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ItemListViewAdapter adapter;


    private static int toastClickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

//        ToolBarExt toolbar = findViewById(R.id.toolbar);
//        toolbar.setPadding(toolbar.getPaddingLeft(),toolbar.getPaddingTop()+getStatusBarHeight(),
//                toolbar.getPaddingRight(),toolbar.getPaddingBottom());


        listView = ViewExt.build(this).find(R.id.lv_listview);

        listView.setOnItemClickListener(this);
        adapter = new ItemListViewAdapter();
        adapter.appendData(items);
        listView.setAdapter(adapter);

    }


    /**
     * 获取状态栏高度
     *
     * @return px
     */
    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        Logger.log(this, "状态栏高度：" +statusBarHeight +"     "+ Metric.px2dip(statusBarHeight) + "dp");
        return statusBarHeight;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        items.get(position).click();
    }


    private void showLog() {

    }


    ArrayList<ItemEntity> items = new ArrayList<ItemEntity>() {
        {
            add(new ItemEntity("默认Toast", "Toast多次弹出，只显示最后一条", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toaster.show("Toast " + toastClickCount);
                    toastClickCount++;
                }
            }));
            add(new ItemEntity("定制Toast", "自定义Toast布局，只显示最后一条", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toaster.show(Toaster.inflate(R.layout.view_toast));
                }
            }));
            add(new ItemEntity("Log打印", "举个栗子,在Logcat查看", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLog();
                }
            }));//3
            add(new ItemEntity("px and dp", "px2dip:" + Metric.px2dip(32) + "  dp2px:" + Metric.dip2px(32)));

        }
    };

}
