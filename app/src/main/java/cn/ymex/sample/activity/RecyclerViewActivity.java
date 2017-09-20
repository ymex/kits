package cn.ymex.sample.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.ymex.sample.R;
import cn.ymex.sample.adapter.RvAdapter;
import cn.ymex.kits.widget.recycler.RecyclerViewAdapter;
import cn.ymex.kits.Finder;
import cn.ymex.kits.widget.Toaster;
import cn.ymex.kits.widget.recycler.RecyclerViewClickSupport;
import cn.ymex.kits.widget.recycler.RecyclerViewHorizontalDivider;
import cn.ymex.kits.widget.recycler.RecyclerViewVerticalDivider;
import cn.ymex.log.L;

public class RecyclerViewActivity extends AppCompatActivity implements RecyclerViewClickSupport.OnItemClickListener {
    RecyclerView recyclerView;
    SwipeRefreshLayout srlRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        onViewCreated(this,savedInstanceState);
    }


    public void onViewCreated(Activity view, @Nullable Bundle savedInstanceState) {
        initRv(view);
        initSrl(view);


    }

    private void initSrl(Activity view) {
        srlRefresh = Finder.find(view, Finder.resId(R.id.class, "R.id.srl_refresh"));
        srlRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rvAdapter.addHeaderView(R.layout.header_layout);
                        srlRefresh.setRefreshing(false);
                        Toaster.show("=====");
                    }
                }, 3000);
            }
        });
    }


    private RvAdapter rvAdapter;
    private void initRv(Activity view) {
        recyclerView = Finder.find(view, R.id.rv_demos);
        rvAdapter = new RvAdapter();

        rvAdapter.addFooterView(R.layout.footer_layout);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplication(),2));
        recyclerView.addItemDecoration(RecyclerViewHorizontalDivider.def(getApplication()));
        recyclerView.addItemDecoration(RecyclerViewVerticalDivider.def(getApplication()));
        recyclerView.setAdapter(rvAdapter);
        RecyclerViewAdapter.compatGridLayoutManager(recyclerView,rvAdapter);
        RecyclerViewClickSupport.addTo(recyclerView).setOnItemClickListener(this);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            recyclerView.setOnScrollChangeListener(new RecyclerView.OnScrollChangeListener() {

                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                }
            });
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                if (isSlideToBottom(recyclerView)) {
//                    L.d("0------------------!!!");
//                }
                if (!ViewCompat.canScrollVertically(recyclerView, 1)) {//等同isSlideToBottom
                    L.d("0------------------!!!");
                }
            }

            /**
             * 是否到达底部
             * @param recyclerView
             * @return
             */
            protected boolean isSlideToBottom(RecyclerView recyclerView) {
                if (recyclerView == null) return false;
                if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()) {
                    return true;
                }
                return false;
            }
        });
    }



    //http://m.blog.csdn.net/article/details?id=50989549
//    http://www.jianshu.com/p/c138055af5d2
//    http://blog.csdn.net/axuanqq/article/details/51144200
    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {
        Toaster.show("postion:" + position);
        if (position == rvAdapter.getItemCount() - 1) {
            rvAdapter.removeFooterView();
        }
    }
}
