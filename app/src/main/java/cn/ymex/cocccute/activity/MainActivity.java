package cn.ymex.cocccute.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.ymex.cocccute.R;
import cn.ymex.cocccute.adapter.ItemViewHolderAdapter;
import cn.ymex.cocccute.base.BaseActivity;
import cn.ymex.cocccute.entity.ItemEntity;
import cn.ymex.cocccute.entity.Student;
import cn.ymex.cocccute.flux.FluxActivity;
import cn.ymex.cute.kits.ViewKit;
import cn.ymex.cute.log.L;
import cn.ymex.cute.widget.notice.Toaster;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ItemViewHolderAdapter adapter;

    private static int toastClickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = ViewKit.find(this, R.id.lv_listview);

        listView.setOnItemClickListener(this);
        adapter = new ItemViewHolderAdapter();
        adapter.appendData(items);
        listView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showToast(position);
        if (position == 3) {
            showLog();
        }
        items.get(position).startActivity(this);
    }

    private void showToast(int position) {
        if (position == 1) {
            toastClickCount++;
            Toaster.show("点击:" + adapter.getItem(position).getTitle() + " " + toastClickCount + "次");
        } else if (position == 2) {
            Toaster.show(Toaster.inflate(R.layout.view_toast));
        }
    }

    private void showLog() {
        L.v("this is v log");
        L.i(new Student("ymex", 12, "hello world !"));
        L.d(new Student("Jay", 32, "Hi mars"));
        L.w(new Gson().toJson(new Student("Mars", 32, "你好")));
        L.e("this error log ");
        L.w(new Exception("--ht"));
    }

    List<ItemEntity> items = new ArrayList() {
        {
            add(new ItemEntity("Flux For Android", "使用Flux模式设计App", FluxActivity.class));
            add(new ItemEntity("默认Toast", "Toast多次弹出，只显示最后一条"));//1
            add(new ItemEntity("定制Toast", "自定义Toast布局，只显示最后一条"));//2
            add(new ItemEntity("Log打印", "举个栗子,在Logcat查看"));//3
            add(new ItemEntity("LoadingView", "view for loading ", LoadingActivity.class));
            add(new ItemEntity("RecyclerViewAdapter", "添加click事件 ,自定义header, footer", RecyclerViewActivity.class));
        }
    };
}
