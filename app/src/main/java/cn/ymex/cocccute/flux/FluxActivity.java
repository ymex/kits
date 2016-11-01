package cn.ymex.cocccute.flux;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import cn.ymex.cocccute.R;
import cn.ymex.cocccute.base.BaseActivity;
import cn.ymex.cocccute.flux.action.FluxActAction;
import cn.ymex.cocccute.flux.action.RequestAction;
import cn.ymex.cocccute.flux.store.MessageStore;
import cn.ymex.cute.kits.ViewKit;
import cn.ymex.cute.mode.flux.StoreAlter;
import cn.ymex.cute.widget.notice.Toaster;

public class FluxActivity extends BaseActivity {
    private TextView tvPlay;
    private View pbLoading;
    MessageStore store;

    private void init() {
        tvPlay = ViewKit.find(this, R.id.tv_play);
        pbLoading = ViewKit.find(this, R.id.pb_loading);
        ViewKit.findClick(this, R.id.btn_post, this);
        ViewKit.findClick(this, R.id.btn_cancel, this);
    }

    private void showLoading() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flux_hi);
        init();
        initflux();
    }

    private void initflux() {
        store = new MessageStore();
        store.register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        store.unregister(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_post:
                FluxActAction.getTop250Movies(0, 10);
                break;
            case R.id.btn_cancel:
                FluxActAction.cancelHttpRequest();
                break;
        }
    }

    @Subscribe
    public void onAction(StoreAlter event) {
        switch (event.getAction().getType()) {
            case FluxActAction.ACTION_CACEL_REQUEST://取消请求

                break;
            case FluxActAction.ACTION_GET_TOP250_MOVIES:
                if (event.getResult() != null) {
                    Toaster.show(store.getMovieEntity().getTitle());
                    tvPlay.setText(store.getMovieEntity().toString());
                    hideLoading();
                }
                break;
            case RequestAction.START:
                showLoading();
                break;
            case RequestAction.FAILURE:
                Toaster.show("FAILURE");
                hideLoading();
                break;
        }
    }
}
